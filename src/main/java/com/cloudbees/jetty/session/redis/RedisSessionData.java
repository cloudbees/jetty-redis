package com.cloudbees.jetty.session.redis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.jetty.server.session.SessionData;

import java.util.*;

public class RedisSessionData extends SessionData {
    public RedisSessionData(final String id, final String cpath, final String vhost, final long created, final long accessed, final long lastAccessed, final long maxInactiveMs) {
        super(id, cpath, vhost, created, accessed, lastAccessed, maxInactiveMs);
    }

    public RedisSessionData(final String id, final String cpath, final String vhost, final long created, final long accessed, final long lastAccessed, final long maxInactiveMs, final Map<String, Object> attributes) {
        super(id, cpath, vhost, created, accessed, lastAccessed, maxInactiveMs, attributes);
    }

    /**
     * The {@link java.util.LinkedHashMap.LinkedKeySet} returned by the base class is immutable and rejected by Jackson upon deser.
     */
    @Override
    @JsonIgnore
    public Set<String> getKeys() {
        final Set<String> keys = _attributes.keySet();
        return new LinkedHashSet<>(keys);
    }
}
