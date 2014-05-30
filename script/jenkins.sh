#!/bin/bash

# Script emulating the actions taken by Jenkins (not exactly the same, but similar)

# run up to "verify" phase
MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m" mvn -f ../pom.xml -Djava.awt.headless=true -X -e clean verify

# optionally (if this script is called with any parameter) try to generate documentation + website
if [ -n "$1" ]
then 
	mvn -f ../pom.xml latex:latex site
fi
	