-- !!! refresh the information !!!
-- shows all the tables that actually contain SOME DATA / rows
VACUUM FULL;
﻿select distinct tablename from pg_stats where schemaname='public' order by tablename;
