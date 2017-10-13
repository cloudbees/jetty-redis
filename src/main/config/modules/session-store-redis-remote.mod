[description]
Enables session data store in a remote Redis cache

[tags]
session

[provides]
session-store

[depend]
sessions

[files]
http://central.maven.org/maven2/org/apache/commons/commons-pool2/2.4.2/commons-pool2-2.4.2.jar|lib/org/apache/commons/commons-pool2/2.4.2/commons-pool2-2.4.2.jar
http://central.maven.org/maven2/redis/clients/jedis/2.9.0/jedis-2.9.0.jar|lib/redis/clients/jedis/2.9.0/jedis-2.9.0.jar
http://central.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.8.7/jackson-core-2.8.7.jar|lib/com/fasterxml/jackson/core/jackson-core/2.8.7/jackson-core-2.8.7.jar
http://central.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.8.7/jackson-annotations-2.8.7.jar|lib/com/fasterxml/jackson/core/jackson-annotations/2.8.7/jackson-annotations-2.8.7.jar
http://central.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.8.7/jackson-databind-2.8.7.jar|lib/com/fasterxml/jackson/core/jackson-databind/2.8.7/jackson-databind-2.8.7.jar
basehome:modules/session-store-redis-remote/

[xml]
etc/sessions/redis/remote.xml

[lib]
lib/jetty-redis-${project.version}.jar
lib/org/apache/commons/commons-pool2/2.4.2/commons-pool2-2.4.2.jar
lib/redis/clients/jedis/2.9.0/jedis-2.9.0.jar
lib/com/fasterxml/jackson/core/jackson-core/2.8.7/jackson-core-2.8.7.jar
lib/com/fasterxml/jackson/core/jackson-annotations/2.8.7/jackson-annotations-2.8.7.jar
lib/com/fasterxml/jackson/core/jackson-databind/2.8.7/jackson-databind-2.8.7.jar

[ini-template]
#com.cloudbees.analytics.sse.session.redis.host=localhost
#com.cloudbees.analytics.sse.session.redis.port=3679
#com.cloudbees.analytics.sse.session.redis.use.ssl=false
