-- !!! refresh the information !!!
-- shows all the fields that contain NO DATA = only NULLs (null ratio = 1)
VACUUM FULL;
select distinct tablename, attname from pg_stats where schemaname='public' AND null_frac=1.0 order by tablename, attname;