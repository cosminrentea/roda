DROP USER roda;
DROP DATABASE roda;

CREATE USER roda;
alter user roda with password 'RodaAdor';

CREATE DATABASE roda WITH ENCODING 'UTF8';
ALTER DATABASE roda OWNER TO roda;

CREATE DATABASE rodaext WITH encoding 'UTF8';
ALTER DATABASE roda OWNER TO roda;
