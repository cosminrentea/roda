#!/bin/bash
#set -x

source database.properties

SQLFILE=roda-data-seed-csv.sql

# start with an empty DB
./roda-truncate.sh

# empty SQL script
>"${SQLFILE}"

for c in `ls csv/*.csv | sort`
do
    filename=`basename $c`
    tablename=${filename%.csv}
    tablename=${tablename:2}
    echo "\copy ${tablename}(`head -n 1 $c`) FROM stdin DELIMITERS ',' CSV;"  >> ${SQLFILE}
    echo "`tail -n +2 $c`" >> ${SQLFILE}
    echo "\."  >> ${SQLFILE}
    echo  >> ${SQLFILE}
done

# run the generated SQL script
psql -a -w -f ${SQLFILE} -h ${RODA_HOST} ${RODA_DB} ${RODA_USER}
