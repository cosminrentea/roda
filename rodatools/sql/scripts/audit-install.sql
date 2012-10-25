-- Cosmin: needs checking / testing
select audit.audit_table(pgt.tablename::regclass) from pg_tables pgt WHERE schemaname='public';
