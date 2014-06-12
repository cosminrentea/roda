MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m" mvn -f ../pom.xml -Djava.awt.headless=true -Dspring.profiles.active=productioninit -Dmaven.tomcat.fork=true -X -e clean package tomcat:run
