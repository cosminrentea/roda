#!/bin/bash

source ./database.properties

# create temporary file for an SQL script
TEMPSCRIPT=`mktemp roda-truncate.XXXXXX`
echo "${TEMPSCRIPT}"

# get a list of the "TRUNCATE" commands - for all the tables
psql -a -w -t -o ${TEMPSCRIPT} -h ${RODA_HOST}  -c "SELECT 'TRUNCATE TABLE \"' || tablename || '\" RESTART IDENTITY CASCADE;' from pg_tables where schemaname = 'public';" ${RODA_DB} ${RODA_USER}

# execute all the DROP TABLE commands
psql -a -w -f "${TEMPSCRIPT}" -h ${RODA_HOST} ${RODA_DB} ${RODA_USER}

# remove temporary file
rm "${TEMPSCRIPT}"
