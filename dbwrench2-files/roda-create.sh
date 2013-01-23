#!/bin/sh

# set filename
if [ -z $1 ]
then
    FILENAME=roda-db-doc.sql
else
    FILENAME=$1
fi

# replace ID types -> SERIALs
perl -p -i -e "s/ID INTEGER NOT NULL/id SERIAL/gi;s/ID BIGINT NOT NULL/id BIGSERIAL/gi" $FILENAME
# cat $1 | sed 's/ID INTEGER NOT NULL/id SERIAL/gI' |  sed 's/ID BIGINT NOT NULL/id BIGSERIAL/gI'  >$2

psql -f $FILENAME roda roda


