#!/bin/bash

source database.properties

# create temporary file for an SQL script
TEMPSCRIPT=`mktemp`
echo "${TEMPSCRIPT}"

# get a list the "DROP" commands - for all the tables
psql -w -t -o ${TEMPSCRIPT} -h ${RODA_HOST}  -c "select 'drop table if exists \"' || tablename || '\" cascade;' from pg_tables where schemaname = 'public';" ${RODA_DB} ${RODA_USER}

# execute the DROPs
psql -w -f "${TEMPSCRIPT}" -h ${RODA_HOST} ${RODA_DB} ${RODA_USER}

# remove temporary file
rm "${TEMPSCRIPT}"
