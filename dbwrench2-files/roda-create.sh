#!/bin/bash

source database.properties

# set filename, if no parameter is given use the default
if [ -z $1 ]
then
    SCRIPTNAME=${RODA_DEFAULT_SCRIPT}
else
    SCRIPTNAME=$1
fi

# replace ID types -> SERIALs
perl -p -i -e "s/^[[:space:]]+ID INTEGER NOT NULL/id SERIAL/gi;s/^[[:space:]]+ID BIGINT NOT NULL/id BIGSERIAL/gi" ${SCRIPTNAME}

# run script
psql -a -f ${SCRIPTNAME} -h ${RODA_HOST} ${RODA_DB} ${RODA_USER}


