
>services.roo

for f in ../src/main/java/ro/roda/*.java
do
#service --interface ~.service.OrgSufixService --entity ~.OrgSufix
    cname=`basename ${f%.java}`
    echo "service --interface ~.service.${cname}Service --entity ~.${cname}" >>services.roo

done