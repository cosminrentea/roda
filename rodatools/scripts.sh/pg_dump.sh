# dump schema first
pg_dump -f ../sql/schema/roda-schema-all-codebooks.sql -n public -s -U roda roda

# dump all data (and schema)
pg_dump -f roda-all-codebooks.sql -n public -U roda roda

# archive all data for minimum storage
tar --remove-files -c -z -f ../sql/dump/roda-all-codebooks.tgz roda-all-codebooks.sql

