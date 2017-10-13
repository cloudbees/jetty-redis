# jetty-redis

## Usage

* From jetty.base, `java -jar $JETTY_HOME/start.jar --add-to-start=session-store-redis-remote`
* Update start.ini as needed
* `java -jar $JETTY_HOME/start.jar`

Based largely on Jetty infinispan session provider:  https://github.com/eclipse/jetty.project/tree/jetty-9.4.x/jetty-infinispan

See also:  
* https://www.eclipse.org/jetty/documentation/9.4.x/configuring-sessions-infinispan.html
* http://www.eclipse.org/jetty/documentation/9.4.x/custom-modules.html 