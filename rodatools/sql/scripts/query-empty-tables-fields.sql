
-- !!! refresh the information !!!
VACUUM FULL;

﻿-- shows all the tables that actually contain SOME DATA / rows
select distinct tablename from pg_stats where schemaname='public' order by tablename;

-- shows all the fields that contain NO DATA = only NULLs (null ratio = 1)
select distinct tablename, attname from pg_stats where schemaname='public' AND null_frac=1 order by tablename, attname;