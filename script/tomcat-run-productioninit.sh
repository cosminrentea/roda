export R_HOME="/usr/lib64/R"

# Ubuntu Cosmin
export LD_LIBRARY_PATH="/usr/lib/jvm/java-7-oracle/jre/lib/i386/client:/usr/lib64/R/lib:/usr/lib64/R/library/rJava/jri:/usr/lib64/R/library/rJava:/usr/lib64/R/library"

# Kubuntu Sorin
#export LD_LIBRARY_PATH="/usr/lib/jvm/java-1.7.0-openjdk-amd64/jre/lib/amd64/server/:/usr/lib/R/lib:/usr/local/lib/R/site-library/rJava/jri:/usr/local/lib/R/site-library/rJava/libs"

MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m" mvn -f ../pom.xml -Djava.awt.headless=true -DR_HOME=/usr/lib64/R -Djava.library.path="${LD_LIBRARY_PATH}" -Dspring.profiles.active=productioninit -Dmaven.tomcat.fork=true -X -e clean package tomcat:run
