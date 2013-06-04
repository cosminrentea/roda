#!/bin/bash
set -x

source database.properties

# drop DATABASE and ROLE
psql -a -w -t -c "DROP DATABASE ${RODA_DB};" -h ${RODA_HOST}
psql -a -w -t -c "DROP ROLE ${RODA_USER};" -h ${RODA_HOST}
