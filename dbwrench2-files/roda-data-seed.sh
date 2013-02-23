#!/bin/bash

RODA_MODEL="../RODA-Model"
SQLFILE=roda-data-seed.sql
LOGFILE=roda-data-seed.log

source database.properties

cp -f rodaconfig.ini ${RODA_MODEL}/config/

# start with an empty DB
./roda-truncate.sh

# check first if Perl model passes all tests

# step 1. with an empty DB
${RODA_MODEL}/script/roda_seeddb.pl
EXITCODE=$?
if [ $EXITCODE -ne 0 ]
then
    exit $EXITCODE
fi

# step 2. with a filled DB
${RODA_MODEL}/script/roda_seeddb.pl
EXITCODE=$?
if [ $EXITCODE -ne 0 ]
then
    exit $EXITCODE
fi

# empty DB
./roda-truncate.sh

# generate the SQL file, in 2 steps (first generate log file = DBIx SQL trace)
${RODA_MODEL}/script/roda_seeddb.pl --debugsql --logfile=${LOGFILE}
${RODA_MODEL}/script/roda_seeddb.pl --gensql --sqlfile=${SQLFILE} --logfile=${LOGFILE}
rm -f ${LOGFILE}

# empty DB
./roda-truncate.sh

# run the generated SQL script
psql -a -w -f ${SQLFILE} -h ${RODA_HOST} ${RODA_DB} ${RODA_USER}
