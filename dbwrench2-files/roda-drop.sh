#!/bin/bash

source database.properties

# create temporary file for an SQL script
TEMPSCRIPT=`mktemp`
echo "${TEMPSCRIPT}"

# get a list the "DROP" commands - for all the tables
psql -w -t -o ${TEMPSCRIPT} -h ${RODA_HOST}  -c "SELECT 'DROP TABLE IF EXISTS \"' || tablename || '\" cascade;' from pg_tables where schemaname = 'public';" ${RODA_DB} ${RODA_USER}

# execute all the DROP TABLE commands
psql -w -f "${TEMPSCRIPT}" -h ${RODA_HOST} ${RODA_DB} ${RODA_USER}

# get a list the "DROP SEQUENCE" commands - for all the sequences in the database
psql -w -t -o ${TEMPSCRIPT} -h ${RODA_HOST}  -c "SELECT 'DROP SEQUENCE \"' || relname || '\";' FROM pg_class WHERE  relkind = 'S';" ${RODA_DB} ${RODA_USER}

# execute all the DROP SEQUENCE commands
psql -w -f "${TEMPSCRIPT}" -h ${RODA_HOST} ${RODA_DB} ${RODA_USER}

# remove temporary file
rm "${TEMPSCRIPT}"
