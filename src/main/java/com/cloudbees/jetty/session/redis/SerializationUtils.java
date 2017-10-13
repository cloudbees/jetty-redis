package com.cloudbees.jetty.session.redis;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationUtils {
    private static final ObjectMapper OBJECT_MAPPER = newMapper();

    public static ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    public static ObjectMapper newMapper() {
        return new ObjectMapper();
    }
}
