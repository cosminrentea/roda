MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m" mvn -f ../pom.xml -Djava.awt.headless=true -Dspring.profiles.active=production -Dmaven.tomcat.fork=false -X -e clean package tomcat:run
