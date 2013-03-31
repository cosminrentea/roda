#!/bin/bash

ROOSCRIPT=selenium-test.roo

>${ROOSCRIPT}

for f in ../src/main/java/ro/roda/web/*.java
do
    cname=`basename ${f%.java}`
    echo "selenium test --controller ~.web.${cname}" >>${ROOSCRIPT}
done
