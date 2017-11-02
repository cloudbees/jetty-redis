[description]
Enables session data store in a remote Redis cache

[tags]
session

[provides]
session-store

[depend]
sessions

[files]
http://central.maven.org/maven2/org/apache/commons/commons-pool2/${commons-pool2.version}/commons-pool2-${commons-pool2.version}.jar|lib/org/apache/commons/commons-pool2/commons-pool2-${commons-pool2.version}.jar
http://central.maven.org/maven2/redis/clients/jedis/${jedis.version}/jedis-${jedis.version}.jar|lib/redis/clients/jedis/jedis-${jedis.version}.jar
http://central.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/${jackson.version}/jackson-core-${jackson.version}.jar|lib/com/fasterxml/jackson/core/jackson-core/jackson-core-${jackson.version}.jar
http://central.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/${jackson.version}/jackson-annotations-${jackson.version}.jar|lib/com/fasterxml/jackson/core/jackson-annotations/jackson-annotations-${jackson.version}.jar
http://central.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/${jackson.version}/jackson-databind-${jackson.version}.jar|lib/com/fasterxml/jackson/core/jackson-databind/jackson-databind-${jackson.version}.jar
http://central.maven.org/maven2/org/acegisecurity/acegi-security/${acegi-security.version}/acegi-security-${acegi-security.version}.jar|lib/org/acegisecurity/acegi-security/acegi-security-${acegi-security.version}.jar
lib/org/jenkinsci/plugins/pubsub/
lib/org/jenkinsci/plugins/ssegateway/

[xml]
etc/sessions/redis/remote.xml

[lib]
lib/jetty-redis-${project.version}.jar
lib/org/acegisecurity/acegi-security/acegi-security-${acegi-security.version}.jar
lib/org/apache/commons/commons-pool2/commons-pool2-${commons-pool2.version}.jar
lib/redis/clients/jedis/jedis-${jedis.version}.jar
lib/com/fasterxml/jackson/core/jackson-core/jackson-core-${jackson.version}.jar
lib/com/fasterxml/jackson/core/jackson-annotations/jackson-annotations-${jackson.version}.jar
lib/com/fasterxml/jackson/core/jackson-databind/jackson-databind-${jackson.version}.jar
lib/org/jenkinsci/plugins/pubsub/pubsub-light-common-${pubsub-light.version}.jar
lib/org/jenkinsci/plugins/pubsub/pubsub-light-redis-provider-${pubsub-light.version}.jar
lib/org/jenkinsci/plugins/pubsub/pubsub-light-redis-${pubsub-light.version}.jar
lib/org/jenkinsci/plugins/ssegateway/sse-common-${sse-gateway.version}.jar

[ini-template]
#com.cloudbees.analytics.sse.session.redis.host=localhost
#com.cloudbees.analytics.sse.session.redis.port=3679
#com.cloudbees.analytics.sse.session.redis.use.ssl=false
