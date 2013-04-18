#!/bin/bash

mvn -f ../pom.xml javadoc:javadoc
htmldoc --webpage -f ../target/site/roda-javadoc.pdf `find ../target/site/ -name "*.html"`
