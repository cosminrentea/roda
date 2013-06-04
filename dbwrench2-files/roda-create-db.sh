#!/bin/bash
set -x

source database.properties

# set filename; if no parameter is given use the default
if [ -z $1 ]
then
    SCRIPTNAME=${RODA_DEFAULT_SCRIPT}
else
    SCRIPTNAME=$1
fi

# create ROLE and DATABASE
psql -a -w -t  -h ${RODA_HOST} -c "CREATE USER ${RODA_USER} PASSWORD '${RODA_PASSWORD}';"
psql -a -w -t  -h ${RODA_HOST} -c "CREATE DATABASE ${RODA_DB} OWNER ${RODA_USER} ENCODING 'UTF8';"

# create additional SCHEMAs
psql -a -w -t -c "CREATE SCHEMA audit; CREATE SCHEMA ddi;" -h ${RODA_HOST} ${RODA_DB} ${RODA_USER}

