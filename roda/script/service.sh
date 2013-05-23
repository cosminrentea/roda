#!/bin/bash

ROOSCRIPT=service.roo

>${ROOSCRIPT}

for f in ../src/main/java/ro/roda/domain/*.java
do
    cname=`basename ${f%.java}`
    echo "service --interface ~.service.${cname}Service --entity ~.domain.${cname}" >>${ROOSCRIPT}
done
