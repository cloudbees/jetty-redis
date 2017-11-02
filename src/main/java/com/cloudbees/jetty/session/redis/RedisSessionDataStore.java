package com.cloudbees.jetty.session.redis;

import org.eclipse.jetty.server.session.*;
import org.eclipse.jetty.util.annotation.*;
import org.eclipse.jetty.util.log.*;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Based on https://github.com/eclipse/jetty.project/blob/jetty-9.4.x/jetty-Redis/src/main/java/org/eclipse/jetty/session/Redis/RedisSessionDataStore.java
 */
@ManagedObject
public class RedisSessionDataStore extends AbstractSessionDataStore {
    private final static Logger LOGGER = Log.getLogger(RedisSessionDataStore.class);

    private Jedis jedis;

    public RedisSessionDataStore(final Jedis jedis) {
        this.jedis = jedis;
    }

    static class ObjectInputStreamWithLoader extends ObjectInputStream
    {
        private ClassLoader loader;

        public ObjectInputStreamWithLoader(InputStream in, ClassLoader loader)
                throws IOException, StreamCorruptedException {

            super(in);
            if (loader == null) {
                throw new IllegalArgumentException("Illegal null argument to ObjectInputStreamWithLoader");
            }
            this.loader = loader;
        }

        protected Class resolveClass(ObjectStreamClass classDesc)
                throws IOException, ClassNotFoundException {

            String cname = classDesc.getName();
            return Class.forName(cname, false, this.loader);
        }
    }

    @Override
    public SessionData load(String id) throws Exception {
        final AtomicReference<SessionData> reference = new AtomicReference<>();
        final AtomicReference<Exception> exception = new AtomicReference<>();

        Runnable load = () -> {
            try {
                LOGGER.debug("Loading session {} from Redis", id);
                final byte[] session = jedis.get(getCacheKey(id).getBytes());
                if (session != null) {
                    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(session);
                         ObjectInputStream objectInputStream = new ObjectInputStreamWithLoader(byteArrayInputStream, _context.getContext().getClassLoader())) {
                        SessionData sd = (SessionData) objectInputStream.readObject();
                        reference.set(sd);
                    }
                }
            } catch (final Exception e) {
                exception.set(e);
            }
        };

        //ensure the load runs in the context classloader scope
        _context.run(load);

        if (exception.get() != null)
            throw exception.get();

        return reference.get();
    }

    @Override
    public boolean delete(String id) throws Exception {
        LOGGER.debug("Deleting session with id {} from Redis", id);
        return (jedis.del(getCacheKey(id)) != null);
    }

    @Override
    public Set<String> doGetExpired(Set<String> candidates) {
        if (candidates == null || candidates.isEmpty())
            return candidates;

        long now = System.currentTimeMillis();

        Set<String> expired = new HashSet<>();

        for (String candidate : candidates) {
            LOGGER.debug("Checking expiry for candidate {}", candidate);
            try {
                SessionData sd = load(candidate);

                //if the session no longer exists
                if (sd == null) {
                    expired.add(candidate);
                    LOGGER.debug("Session {} does not exist in Redis", candidate);
                } else {
                    if (_context.getWorkerName().equals(sd.getLastNode())) {
                        //we are its manager, add it to the expired set if it is expired now
                        if ((sd.getExpiry() > 0) && sd.getExpiry() <= now) {
                            expired.add(candidate);
                            LOGGER.debug("Session {} managed by {} is expired", candidate, _context.getWorkerName());
                        }
                    } else {
                        //if we are not the session's manager, only expire it iff:
                        // this is our first expiryCheck and the session expired a long time ago
                        //or
                        //the session expired at least one graceperiod ago
                        if (_lastExpiryCheckTime <= 0) {
                            if ((sd.getExpiry() > 0) && sd.getExpiry() < (now - (1000L * (3 * _gracePeriodSec))))
                                expired.add(candidate);
                        } else {
                            if ((sd.getExpiry() > 0) && sd.getExpiry() < (now - (1000L * _gracePeriodSec)))
                                expired.add(candidate);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("Error checking if candidate {} is expired", candidate, e);
            }
        }

        return expired;
    }

    @Override
    public void doStore(String id, SessionData data, long lastSaveTime) throws Exception {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(data);
            jedis.set(getCacheKey(id).getBytes(Charset.defaultCharset()), byteArrayOutputStream.toByteArray());
            LOGGER.debug("Session {} saved to Redis, expires {} ", id, data.getExpiry());
        }
    }


    public String getCacheKey(String id) {
        return _context.getCanonicalContextPath() + "_" + _context.getVhost() + "_" + id;
    }

    @ManagedAttribute(value = "does store serialize sessions", readonly = true)
    @Override
    public boolean isPassivating() {
        return true;
    }

    @Override
    public boolean exists(String id) throws Exception {
        final AtomicReference<Boolean> reference = new AtomicReference<>();
        final AtomicReference<Exception> exception = new AtomicReference<>();

        Runnable load = () -> {
            try {
                final boolean exists = jedis.exists(getCacheKey(id));
                if (!exists) {
                    reference.set(Boolean.FALSE);
                    return;
                }

                SessionData sd = load(id);
                if (sd.getExpiry() <= 0)
                    reference.set(Boolean.TRUE);
                else
                    reference.set(sd.getExpiry() > System.currentTimeMillis());
            } catch (Exception e) {
                exception.set(e);
            }
        };

        //ensure the load runs in the context classloader scope
        _context.run(load);

        if (exception.get() != null)
            throw exception.get();

        return reference.get();
    }

    @Override
    public String toString() {
        return String.format("%s[cache=%s]", jedis.clientGetname(), _context == null ? "{}" : jedis.keys(_context.getCanonicalContextPath() + "*"));
    }
}
