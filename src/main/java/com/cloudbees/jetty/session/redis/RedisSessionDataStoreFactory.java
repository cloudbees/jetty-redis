package com.cloudbees.jetty.session.redis;

import org.eclipse.jetty.server.session.AbstractSessionDataStoreFactory;
import org.eclipse.jetty.server.session.SessionDataStore;
import org.eclipse.jetty.server.session.SessionHandler;
import redis.clients.jedis.JedisPool;

public class RedisSessionDataStoreFactory extends AbstractSessionDataStoreFactory {
    private JedisPool jedisPool;

    @Override
    public SessionDataStore getSessionDataStore(SessionHandler handler) throws Exception {
        RedisSessionDataStore store = new RedisSessionDataStore(jedisPool.getResource());
        store.setGracePeriodSec(getGracePeriodSec());
        store.setSavePeriodSec(getSavePeriodSec());
        return store;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(final JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
