#!/bin/sh

# create temporary file for an SQL script
TEMPSCRIPT=`mktemp`
echo $TEMPSCRIPT

# get a list the "DROP" commands - for all the tables
psql -w -t -o $TEMPSCRIPT -c "select 'drop table if exists \"' || tablename || '\" cascade;' from pg_tables where schemaname = 'public';" roda roda

# execute the DROPs
psql -w -f $TEMPSCRIPT roda roda

# remove temporary file
rm $TEMPSCRIPT