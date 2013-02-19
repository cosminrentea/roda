/************ Add: Sequences ***************/

CREATE SEQUENCE acl_class_id_seq INCREMENT BY 1;

CREATE SEQUENCE acl_entry_id_seq INCREMENT BY 1;

CREATE SEQUENCE acl_object_identity_id_seq INCREMENT BY 1;

CREATE SEQUENCE acl_sid_id_seq INCREMENT BY 1;

CREATE SEQUENCE address_id_seq INCREMENT BY 1;

CREATE SEQUENCE audit_field_id_seq INCREMENT BY 1;

CREATE SEQUENCE audit_id_seq INCREMENT BY 1;

CREATE SEQUENCE audit_log_action_id_seq INCREMENT BY 1;

CREATE SEQUENCE audit_log_change_id_seq INCREMENT BY 1;

CREATE SEQUENCE audit_log_changeset_id_seq INCREMENT BY 1;

CREATE SEQUENCE audit_log_field_id_seq INCREMENT BY 1;

CREATE SEQUENCE audit_log_table_id_seq INCREMENT BY 1;

CREATE SEQUENCE audit_row_id_id_seq INCREMENT BY 1;

CREATE SEQUENCE catalog_id_seq INCREMENT BY 1;

CREATE SEQUENCE city_id_seq INCREMENT BY 1;

CREATE SEQUENCE cms_file_id_seq INCREMENT BY 1;

CREATE SEQUENCE cms_folder_id_seq INCREMENT BY 1;

CREATE SEQUENCE cms_layout_group_id_seq INCREMENT BY 1;

CREATE SEQUENCE cms_layout_id_seq INCREMENT BY 1;

CREATE SEQUENCE cms_page_content_id_seq INCREMENT BY 1;

CREATE SEQUENCE cms_page_id_seq INCREMENT BY 1;

CREATE SEQUENCE cms_page_type_id_seq INCREMENT BY 1;

CREATE SEQUENCE cms_snippet_group_id_seq INCREMENT BY 1;

CREATE SEQUENCE cms_snippet_id_seq INCREMENT BY 1;

CREATE SEQUENCE collection_model_type_id_seq INCREMENT BY 1;

CREATE SEQUENCE concept_id_seq INCREMENT BY 1;

CREATE SEQUENCE email_id_seq INCREMENT BY 1;

CREATE SEQUENCE file_id_seq INCREMENT BY 1;

CREATE SEQUENCE form_id_seq INCREMENT BY 1;

CREATE SEQUENCE instance_id_seq INCREMENT BY 1;

CREATE SEQUENCE instance_org_assoc_id_seq INCREMENT BY 1;

CREATE SEQUENCE instance_person_assoc_id_seq INCREMENT BY 1;

CREATE SEQUENCE internet_id_seq INCREMENT BY 1;

CREATE SEQUENCE item_id_seq INCREMENT BY 1;

CREATE SEQUENCE keyword_id_seq INCREMENT BY 1;

CREATE SEQUENCE news_id_seq INCREMENT BY 1;

CREATE SEQUENCE org_id_seq INCREMENT BY 1;

CREATE SEQUENCE org_prefix_id_seq INCREMENT BY 1;

CREATE SEQUENCE org_relation_type_id_seq INCREMENT BY 1;

CREATE SEQUENCE org_sufix_id_seq INCREMENT BY 1;

CREATE SEQUENCE other_statistic_id_seq INCREMENT BY 1;

CREATE SEQUENCE person_id_seq INCREMENT BY 1;

CREATE SEQUENCE person_links_id_seq INCREMENT BY 1;

CREATE SEQUENCE person_role_id_seq INCREMENT BY 1;

CREATE SEQUENCE phone_id_seq INCREMENT BY 1;

CREATE SEQUENCE prefix_id_seq INCREMENT BY 1;

CREATE SEQUENCE property_name_id_seq INCREMENT BY 1;

CREATE SEQUENCE property_value_id_seq INCREMENT BY 1;

CREATE SEQUENCE region_id_seq INCREMENT BY 1;

CREATE SEQUENCE regiontype_id_seq INCREMENT BY 1;

CREATE SEQUENCE rodauser_id_seq INCREMENT BY 1;

CREATE SEQUENCE role_id_seq INCREMENT BY 1;

CREATE SEQUENCE sampling_procedure_id_seq INCREMENT BY 1;

CREATE SEQUENCE setting_group_id_seq INCREMENT BY 1;

CREATE SEQUENCE setting_id_seq INCREMENT BY 1;

CREATE SEQUENCE skip_id_seq INCREMENT BY 1;

CREATE SEQUENCE source_contact_method_id_seq INCREMENT BY 1;

CREATE SEQUENCE source_contacts_id_seq INCREMENT BY 1;

CREATE SEQUENCE sourcestudy_id_seq INCREMENT BY 1;

CREATE SEQUENCE sourcestudy_type_history_id_seq INCREMENT BY 1;

CREATE SEQUENCE sourcestudy_type_id_seq INCREMENT BY 1;

CREATE SEQUENCE sourcetype_history_id_seq INCREMENT BY 1;

CREATE SEQUENCE sourcetype_id_seq INCREMENT BY 1;

CREATE SEQUENCE study_id_seq INCREMENT BY 1;

CREATE SEQUENCE study_org_assoc_id_seq INCREMENT BY 1;

CREATE SEQUENCE study_person_asoc_id_seq INCREMENT BY 1;

CREATE SEQUENCE study_person_assoc_id_seq INCREMENT BY 1;

CREATE SEQUENCE suffix_id_seq INCREMENT BY 1;

CREATE SEQUENCE time_meth_type_id_seq INCREMENT BY 1;

CREATE SEQUENCE title_type_id_seq INCREMENT BY 1;

CREATE SEQUENCE topic_id_seq INCREMENT BY 1;

CREATE SEQUENCE unit_analysis_id_seq INCREMENT BY 1;

CREATE SEQUENCE user_message_id_seq INCREMENT BY 1;

CREATE SEQUENCE user_setting_group_id_seq INCREMENT BY 1;

CREATE SEQUENCE user_setting_id_seq INCREMENT BY 1;

CREATE SEQUENCE vargroup_id_seq INCREMENT BY 1;

CREATE SEQUENCE variable_id_seq INCREMENT BY 1;



/************ Update: Tables ***************/

/******************** Add Table: acl_class ************************/

/* Build Table Structure */
CREATE TABLE acl_class
(
id BIGSERIAL,
	class TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE acl_class ADD CONSTRAINT pkacl_class
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE acl_class IS 'Tabel folosit de Spring Security pentru ACL. Uniquely identify any domain object class in the system. The only columns are the ID and the Java class name. Thus, there is a single row for each unique Class we wish to store ACL permissions for.';

/* Add Indexes */
CREATE UNIQUE INDEX "acl_class_class_Idx" ON acl_class (class);


/******************** Add Table: acl_entry ************************/

/* Build Table Structure */
CREATE TABLE acl_entry
(
id BIGSERIAL,
	acl_object_identity BIGINT NOT NULL,
	ace_order INTEGER NOT NULL,
	sid BIGINT NOT NULL,
	mask INTEGER NOT NULL,
	granting BOOL NOT NULL,
	audit_success BOOL NOT NULL,
	audit_failure BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE acl_entry ADD CONSTRAINT pkacl_entry
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE acl_entry IS 'Tabel folosit de Spring Security pentru ACL. Stores the individual permissions assigned to each recipient. Columns include a foreign key to the ACL_OBJECT_IDENTITY, the recipient (ie a foreign key to ACL_SID), whether we''ll be auditing or not, and the integer bit mask that represents the actual permission being granted or denied. We have a single row for every recipient that receives a permission to work with a domain object.';

/* Add Indexes */
CREATE INDEX "acl_entry_acl_object_identity_Idx" ON acl_entry (acl_object_identity, ace_order);


/******************** Add Table: acl_object_identity ************************/

/* Build Table Structure */
CREATE TABLE acl_object_identity
(
id BIGSERIAL,
	object_id_class BIGINT NOT NULL,
	object_id_identity BIGINT NOT NULL,
	parent_object BIGINT NOT NULL,
	owner_sid BIGINT NOT NULL,
	entries_inheriting BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE acl_object_identity ADD CONSTRAINT pkacl_object_identity
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE acl_object_identity IS 'Tabel folosit de Spring Security pentru ACL. Stores information for each unique domain object instance in the system. Columns include the ID, a foreign key to the ACL_CLASS table, a unique identifier so we know which ACL_CLASS instance we''re providing information for, the parent, a foreign key to the ACL_SID table to represent the owner of the domain object instance, and whether we allow ACL entries to inherit from any parent ACL. We have a single row for every domain object instance we''re storing ACL permissions for.  ';

/* Add Indexes */
CREATE UNIQUE INDEX "acl_object_identity_object_id_class_Idx" ON acl_object_identity (object_id_class, object_id_identity);


/******************** Add Table: acl_sid ************************/

/* Build Table Structure */
CREATE TABLE acl_sid
(
id BIGSERIAL,
	principal BOOL NOT NULL,
	sid TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE acl_sid ADD CONSTRAINT pkacl_sid
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE acl_sid IS 'Tabel folosit de Spring Security pentru ACL. Uniquely identify any principal or authority in the system ("SID" stands for "security identity"). The only columns are the ID, a textual representation of the SID, and a flag to indicate whether the textual representation refers to a principal name or a GrantedAuthority. Thus, there is a single row for each unique principal or GrantedAuthority. When used in the context of receiving a permission, a SID is generally called a "recipient".';

/* Add Indexes */
CREATE UNIQUE INDEX "acl_sid_principal_Idx" ON acl_sid (principal, sid);


/******************** Add Table: address ************************/

/* Build Table Structure */
CREATE TABLE address
(
id SERIAL,
	country_id CHAR(2) NOT NULL,
	city_id INTEGER NOT NULL,
	address1 TEXT NOT NULL,
	address2 TEXT NULL,
	subdiv_name VARCHAR(200) NULL,
	subdiv_code VARCHAR(50) NULL,
	postal_code VARCHAR(30) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE address ADD CONSTRAINT pkaddress
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN address.id IS 'Codul adresei retinute';

COMMENT ON COLUMN address.country_id IS 'Codul tarii corespunzatoare adresei (refera atributul id din tabelul country)';

COMMENT ON COLUMN address.city_id IS 'Codul orasului corespunzator adresei (refera atributul id din tabelul city)';

COMMENT ON COLUMN address.address1 IS 'Adresa detaliata (strada, numar, bloc, scara, apartament)';

COMMENT ON COLUMN address.address2 IS 'Detalii despre adresa (intefon, sala etc.)';

COMMENT ON COLUMN address.subdiv_name IS 'Subdiviziunea/Sectorul corespunzator adresei';

COMMENT ON COLUMN address.postal_code IS 'Codul Postal';

COMMENT ON TABLE address IS 'Tabel unic pentru toate adresele care se gasesc in baza de date';


/******************** Add Table: audit_log_action ************************/

/* Build Table Structure */
CREATE TABLE audit_log_action
(
	id INTEGER DEFAULT nextval('audit_log_action_id_seq'::regclass) NOT NULL,
	changeset INTEGER NOT NULL,
	audited_table INTEGER NOT NULL,
	audited_row VARCHAR(50) NULL,
	type VARCHAR(10) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE audit_log_action ADD CONSTRAINT audit_log_action_pkey
	PRIMARY KEY (id);

/* Add Indexes */
CREATE INDEX audit_log_action_idx_audited_table ON audit_log_action (audited_table);

CREATE INDEX audit_log_action_idx_changeset ON audit_log_action (changeset);


/******************** Add Table: audit_log_change ************************/

/* Build Table Structure */
CREATE TABLE audit_log_change
(
	id INTEGER DEFAULT nextval('audit_log_change_id_seq'::regclass) NOT NULL,
	"action" INTEGER NOT NULL,
	field INTEGER NOT NULL,
	old_value VARCHAR(255) NULL,
	new_value VARCHAR(255) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE audit_log_change ADD CONSTRAINT audit_log_change_pkey
	PRIMARY KEY (id);

/* Add Indexes */
CREATE INDEX audit_log_change_idx_action ON audit_log_change ("action");

CREATE INDEX audit_log_change_idx_field ON audit_log_change (field);


/******************** Add Table: audit_log_changeset ************************/

/* Build Table Structure */
CREATE TABLE audit_log_changeset
(
	id INTEGER DEFAULT nextval('audit_log_changeset_id_seq'::regclass) NOT NULL,
	description VARCHAR(255) NULL,
	"timestamp" TIMESTAMP DEFAULT now() NOT NULL,
	rodauser INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE audit_log_changeset ADD CONSTRAINT audit_log_changeset_pkey
	PRIMARY KEY (id);

/* Add Indexes */
CREATE INDEX audit_log_changeset_idx_rodauser ON audit_log_changeset (rodauser);


/******************** Add Table: audit_log_field ************************/

/* Build Table Structure */
CREATE TABLE audit_log_field
(
	id INTEGER DEFAULT nextval('audit_log_field_id_seq'::regclass) NOT NULL,
	audited_table INTEGER NOT NULL,
	name VARCHAR(40) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE audit_log_field ADD CONSTRAINT audit_log_field_pkey
	PRIMARY KEY (id);

/* Add Indexes */
CREATE INDEX audit_log_field_idx_audited_table ON audit_log_field (audited_table);


/******************** Add Table: audit_log_table ************************/

/* Build Table Structure */
CREATE TABLE audit_log_table
(
	id INTEGER DEFAULT nextval('audit_log_table_id_seq'::regclass) NOT NULL,
	name VARCHAR(40) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE audit_log_table ADD CONSTRAINT audit_log_table_pkey
	PRIMARY KEY (id);

/* Add Unique Constraints */
ALTER TABLE audit_log_table
	ADD CONSTRAINT audit_log_table_name UNIQUE (name);


/******************** Add Table: auth_data ************************/

/* Build Table Structure */
CREATE TABLE auth_data
(
	user_id INTEGER NOT NULL,
	credential_provider TEXT NOT NULL,
	field_name TEXT NOT NULL,
	field_value TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE auth_data ADD CONSTRAINT pkauth_data
	PRIMARY KEY (user_id);

/* Add Comments */
COMMENT ON COLUMN auth_data.user_id IS 'Codul utilizatorului (refera atributul id al tabelului users)';

COMMENT ON COLUMN auth_data.credential_provider IS 'Furnizorul de informatii de acces';

COMMENT ON COLUMN auth_data.field_name IS 'Denumirea campului de autentificare (de exemplu: username)';

COMMENT ON COLUMN auth_data.field_value IS 'Valoarea campului specificat prin atributul field_name pentru utilizatorul referit prin atributul user_id';

COMMENT ON TABLE auth_data IS 'Tabel ce stocheaza datele de autentificare ale utilizatorilor';


/******************** Add Table: catalog ************************/

/* Build Table Structure */
CREATE TABLE catalog
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	parent_id INTEGER DEFAULT 0 NULL,
	owner INTEGER NOT NULL,
	added TIMESTAMP DEFAULT now() NOT NULL,
	sequencenr INTEGER NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE catalog ADD CONSTRAINT pkcatalog
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN catalog.id IS 'Codul catalogului';

COMMENT ON COLUMN catalog.name IS 'Denumirea catalogului';

COMMENT ON COLUMN catalog.parent_id IS 'Codul catalogului din care face parte catalogul curent (refera atributul id al tabelului catalog)';

COMMENT ON COLUMN catalog.owner IS 'Codul utilizatorului care poseda catalogul (refera atributul id din tabelul users)';

COMMENT ON COLUMN catalog.added IS 'Data si timpul cand a fost adaugat catalogul respectiv';

COMMENT ON COLUMN catalog.sequencenr IS 'Coloana care permite ordonarea elementelor subordonate aceluiasi parinte';

COMMENT ON COLUMN catalog.description IS 'Descrierea catalogului';

COMMENT ON TABLE catalog IS 'Tabel ce stocheaza informatii despre cataloagele de studii';


/******************** Add Table: catalog_acl ************************/

/* Build Table Structure */
CREATE TABLE catalog_acl
(
	catalog_id INTEGER NOT NULL,
	aro_id INTEGER NOT NULL,
	aro_type INTEGER NOT NULL,
	read BOOL NULL,
	"update" BOOL NULL,
	"delete" BOOL NULL,
	modacl BOOL NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE catalog_acl ADD CONSTRAINT pkcatalog_acl
	PRIMARY KEY (catalog_id, aro_id, aro_type);

/* Add Comments */
COMMENT ON COLUMN catalog_acl.catalog_id IS 'Codul catalogului (refera atributul id din tabelul catalog)';

COMMENT ON COLUMN catalog_acl.aro_id IS 'Codul unui obiect care solicita acces';

COMMENT ON COLUMN catalog_acl.aro_type IS 'Tipul unui obiect care solicita acces';

COMMENT ON COLUMN catalog_acl.read IS 'Atribut boolean avand valoare true daca este acordat drept de citire asupra catalogului; false, altfel';

COMMENT ON COLUMN catalog_acl."update" IS 'Atribut boolean avand valoare true daca este acordat drept de modificare asupra catalogului; false, altfel';

COMMENT ON COLUMN catalog_acl."delete" IS 'Atribut boolean avand valoare true daca este acordat drept de stergere asupra catalogului; false, altfel';

COMMENT ON COLUMN catalog_acl.modacl IS 'Atribut boolean, a carui valoare este true daca drepturile pot fi modificate; altfel, valoarea atributului este false';

COMMENT ON TABLE catalog_acl IS 'Tabel ce stocheaza drepturile de acces asupra unui catalog';


/******************** Add Table: catalog_study ************************/

/* Build Table Structure */
CREATE TABLE catalog_study
(
	catalog_id INTEGER NOT NULL,
	study_id INTEGER NOT NULL,
	added TIMESTAMP NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE catalog_study ADD CONSTRAINT pkcatalog_study
	PRIMARY KEY (catalog_id, study_id);

/* Add Comments */
COMMENT ON COLUMN catalog_study.catalog_id IS 'Codul unui catalog din care face parte studiul referit prin atributul study_id (refera atributul id din tabelul catalog)';

COMMENT ON COLUMN catalog_study.study_id IS 'Codul studiului asociat catalogului referit prin atributul catalog_id (refera atributul id din tabelul study)';

COMMENT ON TABLE catalog_study IS 'Tabel ce contine asocierile dintre cataloage si studii (implementeaza relatia many-to-many intre catalog si study)';


/******************** Add Table: city ************************/

/* Build Table Structure */
CREATE TABLE city
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	country_id CHAR(2) NOT NULL,
	city_code VARCHAR(50) NULL,
	city_code_name VARCHAR(100) NULL,
	city_code_sup VARCHAR(100) NULL,
	prefix VARCHAR(50) NULL,
	city_type VARCHAR(50) NULL,
	city_type_system VARCHAR(50) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE city ADD CONSTRAINT pkcity
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN city.id IS 'Codul orasului';

COMMENT ON COLUMN city.name IS 'Numele orasului';

COMMENT ON COLUMN city.country_id IS 'Codul tarii in care se afla orasul (refera atributul id al tabelului country)';

COMMENT ON TABLE city IS 'Tabel unic pentru toate referintele la orase';


/******************** Add Table: cms_file ************************/

/* Build Table Structure */
CREATE TABLE cms_file
(
id SERIAL,
	filename TEXT NOT NULL,
	label VARCHAR(100) NOT NULL,
	cms_folder_id INTEGER NOT NULL,
	filesize BIGINT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_file ADD CONSTRAINT pkcms_file
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN cms_file.id IS 'Codul unui fisier din sistemul CMS al aplicatiei';

COMMENT ON COLUMN cms_file.filename IS 'Numele unui fisier din sistemul CMS al aplicatiei';

COMMENT ON COLUMN cms_file.label IS 'Eticheta (alias-ul) unui fisier din sistemul CMS al aplicatiei';

COMMENT ON COLUMN cms_file.cms_folder_id IS 'Codul folder-ului din care face parte fisierul curent (refera atributul id din tabelul cms_folder)';

COMMENT ON COLUMN cms_file.filesize IS 'Dimensiunea unui fisier din sistemul CMS al aplicatiei, exprimata in bytes';

COMMENT ON TABLE cms_file IS 'Tabel ce stocheaza informatii despre fisierele din sistemul CMS al aplicatiei';


/******************** Add Table: cms_file_property_name_value ************************/

/* Build Table Structure */
CREATE TABLE cms_file_property_name_value
(
	cms_file_id INTEGER NOT NULL,
	property_name_id INTEGER NOT NULL,
	property_value_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_file_property_name_value ADD CONSTRAINT pkcms_file_property_name_value
	PRIMARY KEY (cms_file_id, property_name_id, property_value_id);

/* Add Comments */
COMMENT ON TABLE cms_file_property_name_value IS 'Tabel ce pastreaza asocierile intre CMS_File si proprietatile fisierului respectiv (name+value)';


/******************** Add Table: cms_folder ************************/

/* Build Table Structure */
CREATE TABLE cms_folder
(
id SERIAL,
	name TEXT NOT NULL,
	parent_id INTEGER DEFAULT 0 NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_folder ADD CONSTRAINT pkcms_folder
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN cms_folder.id IS 'Codul unui folder din sistemul cms al aplicatiei';

COMMENT ON COLUMN cms_folder.name IS 'Denumirea unui folder din sistemul CMS al aplicatiei';

COMMENT ON COLUMN cms_folder.parent_id IS 'Codul folder-ului parinte al folder-ului curent (refera atributul id din acelasi tabel)';

COMMENT ON COLUMN cms_folder.description IS 'Descrierea unui folder din sistemul CMS al aplicatiei';

COMMENT ON TABLE cms_folder IS 'Tabel pentru stocarea informatiilor despre folder-ele din sistemul CMS al aplicatiei';


/******************** Add Table: cms_layout ************************/

/* Build Table Structure */
CREATE TABLE cms_layout
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	cms_layout_group_id INTEGER NULL,
	layout_content TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_layout ADD CONSTRAINT pkcms_layout
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN cms_layout.id IS 'Codul unui layout pentru o pagina de CMS ';

COMMENT ON COLUMN cms_layout.name IS 'Denumirea unui layout pentru o pagina de CMS';

COMMENT ON COLUMN cms_layout.cms_layout_group_id IS 'Codul grupului din care face parte un layout de CMS (refera atributul id din tabelul cms_layout_group)';

COMMENT ON COLUMN cms_layout.layout_content IS 'Continutul unui layout pentru o pagina de CMS';

COMMENT ON TABLE cms_layout IS 'Tabel care stocheaza layout-uri pentru paginile din sistemul CMS al aplicatiei';


/******************** Add Table: cms_layout_group ************************/

/* Build Table Structure */
CREATE TABLE cms_layout_group
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	parent_id INTEGER NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_layout_group ADD CONSTRAINT pkcms_layout_group
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN cms_layout_group.id IS 'Codul unui grup de layout-uri';

COMMENT ON COLUMN cms_layout_group.name IS 'Denumirea unui grup de layout-uri';

COMMENT ON COLUMN cms_layout_group.parent_id IS 'Codul parintelui unui grup de layout-uri (refera atributul id din tabelul curent))';

COMMENT ON COLUMN cms_layout_group.description IS 'Descrierea unui grup de layout-uri';

COMMENT ON TABLE cms_layout_group IS 'Tabel ce stocheaza grupurile pentru layout-uri';


/******************** Add Table: cms_page ************************/

/* Build Table Structure */
CREATE TABLE cms_page
(
id SERIAL,
	name TEXT NOT NULL,
	cms_layout_id INTEGER NOT NULL,
	cms_page_type_id INTEGER NOT NULL,
	visible BOOL DEFAULT 'true' NOT NULL,
	navigable BOOL DEFAULT 'true' NOT NULL,
	owner_id INTEGER NOT NULL,
	url TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_page ADD CONSTRAINT pkcms_page
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN cms_page.id IS 'Codul unei pagini din sistemul CMS';

COMMENT ON COLUMN cms_page.name IS 'Denumirea unei pagini din sistemul CMS';

COMMENT ON COLUMN cms_page.cms_layout_id IS 'Codul layout-ului corespunzator paginii din sistemul CMS (refera atributul id din tabelul cms_layout)';

COMMENT ON COLUMN cms_page.cms_page_type_id IS 'Codul tipului unei pagini din sistemul CMS';

COMMENT ON COLUMN cms_page.visible IS 'Atribut a carui valoare este true daca pagina din sistemul CMS este vizibila (valoarea implicita a acestui atribut este true)';

COMMENT ON COLUMN cms_page.navigable IS 'Atribut a carui valoare este true daca pagina din sistemul CMS este navigabila (valoarea implicita a acestui atribut este true)';

COMMENT ON COLUMN cms_page.owner_id IS 'Codul utilizatorului care detine pagina din sistemul CMS (refera atributul id din tabelul users)';

COMMENT ON COLUMN cms_page.url IS 'URL-ul paginii din sistemul CMS';

COMMENT ON TABLE cms_page IS 'Tabel care stocheaza paginile din sistemul CMS al aplicatiei';


/******************** Add Table: cms_page_content ************************/

/* Build Table Structure */
CREATE TABLE cms_page_content
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	cms_page_id INTEGER NOT NULL,
	content_title VARCHAR(250) NOT NULL,
	content_text TEXT NULL,
	order_in_page INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_page_content ADD CONSTRAINT pkcms_page_content
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN cms_page_content.id IS 'Codul unei pagini de tip content din aplicatie';

COMMENT ON COLUMN cms_page_content.name IS 'Denumirea continutului';

COMMENT ON COLUMN cms_page_content.cms_page_id IS 'Codul paginii in sistemul CMS al aplicatiei (refera atributul id din tabelul page)';

COMMENT ON COLUMN cms_page_content.content_title IS 'Titlul continutului';

COMMENT ON COLUMN cms_page_content.content_text IS 'Textul continutului';

COMMENT ON COLUMN cms_page_content.order_in_page IS 'Numarul de ordine al continutului curent in cadrul paginii';

COMMENT ON TABLE cms_page_content IS 'Tabel pentru stocarea continutului paginilor din aplicatie';

/* Add Indexes */
CREATE UNIQUE INDEX "cms_page_content_seqnumber_Idx" ON cms_page_content (order_in_page);


/******************** Add Table: cms_page_type ************************/

/* Build Table Structure */
CREATE TABLE cms_page_type
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_page_type ADD CONSTRAINT pkcms_page_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE cms_page_type IS 'Tabel pentru tipurile de CMS_Page';


/******************** Add Table: cms_snippet ************************/

/* Build Table Structure */
CREATE TABLE cms_snippet
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	cms_snippet_group_id INTEGER NULL,
	snippet_content TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_snippet ADD CONSTRAINT pkcms_snippet
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN cms_snippet.id IS 'Codul unui snippet (fragment de cod) utilizat in aplicatie';

COMMENT ON COLUMN cms_snippet.name IS 'Denumirea unui snippet utilizat in aplicatie';

COMMENT ON COLUMN cms_snippet.cms_snippet_group_id IS 'Grupul din care face parte snippet-ul curent (refera atributul id al tabelului cms_snippet_group)';

COMMENT ON COLUMN cms_snippet.snippet_content IS 'Continutul snippet-ului curent';

COMMENT ON TABLE cms_snippet IS 'Tabel care stocheaza snippeturile (fragmentele de cod) folosite in aplicatie';


/******************** Add Table: cms_snippet_group ************************/

/* Build Table Structure */
CREATE TABLE cms_snippet_group
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	parent_id INTEGER NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE cms_snippet_group ADD CONSTRAINT pkcms_snippet_group
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN cms_snippet_group.id IS 'Codul unui grup de snippet-uri din sistemul CMS al aplicatiei';

COMMENT ON COLUMN cms_snippet_group.name IS 'Denumirea unui grup de snippet-uri din sistemul CMS al aplicatiei';

COMMENT ON COLUMN cms_snippet_group.parent_id IS 'Codul grupului de snippet-uri care este parintele grupului curent (refera atributul id din acelasi tabel)';

COMMENT ON COLUMN cms_snippet_group.description IS 'Descrierea grupului de snippet-uri ';

COMMENT ON TABLE cms_snippet_group IS 'Tabel care stocheaza grupuri de snippeturi utilizate in aplicatie';


/******************** Add Table: collection_model_type ************************/

/* Build Table Structure */
CREATE TABLE collection_model_type
(
id SERIAL,
	name TEXT NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE collection_model_type ADD CONSTRAINT pkcollection_model_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN collection_model_type.id IS 'Codul tipului modelului de colectare a datelor';

COMMENT ON COLUMN collection_model_type.name IS 'Denumirea tipului metodei de colectare a datelor';

COMMENT ON TABLE collection_model_type IS 'Tabel ce contine tipurile modelelor de colectare a datelor';


/******************** Add Table: concept ************************/

/* Build Table Structure */
CREATE TABLE concept
(
id BIGSERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE concept ADD CONSTRAINT pkconcept
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN concept.id IS 'Codul conceptului';

COMMENT ON COLUMN concept.name IS 'Denumirea conceptului';

COMMENT ON COLUMN concept.description IS 'Descrierea detaliata a conceptului';

COMMENT ON TABLE concept IS 'Tabel ce stocheaza conceptele definite ';


/******************** Add Table: concept_variable ************************/

/* Build Table Structure */
CREATE TABLE concept_variable
(
	concept_id BIGINT NOT NULL,
	variable_id BIGINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE concept_variable ADD CONSTRAINT pkconcept_variable
	PRIMARY KEY (concept_id, variable_id);

/* Add Comments */
COMMENT ON COLUMN concept_variable.concept_id IS 'Codul conceptului la care se refera variabila identificata prin atributul variable_id, din cadrul instantei instance_id (refera atributul id din tabelul concept)';

COMMENT ON COLUMN concept_variable.variable_id IS 'Codul variabilei din instanta instance_id careia ii este asociat conceptul identificat prin atributul concept_id';

COMMENT ON TABLE concept_variable IS 'Tabel ce asociaza variabilelor o multime de concepte (implementeaza relatia many-to-many intre tabelele variable si concept)';


/******************** Add Table: country ************************/

/* Build Table Structure */
CREATE TABLE country
(
	id CHAR(2) NOT NULL,
	name VARCHAR(100) NOT NULL,
	alpha3 CHAR(3) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE country ADD CONSTRAINT pkcountry
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN country.id IS 'Codul tarii';

COMMENT ON COLUMN country.name IS 'Numele tarii';

COMMENT ON TABLE country IS 'Tabel unic pentru toate referintele la tari';

/* Add Indexes */
CREATE UNIQUE INDEX "country_alpha3_Idx" ON country (alpha3);

CREATE UNIQUE INDEX "country_name_Idx" ON country (name);


/******************** Add Table: email ************************/

/* Build Table Structure */
CREATE TABLE email
(
id SERIAL,
	email VARCHAR(200) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE email ADD CONSTRAINT pkemail
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN email.id IS 'Codul adresei de email in tabel';

COMMENT ON TABLE email IS 'Tabel unic pentru toate adresele de e-mail din baza de date';


/******************** Add Table: file ************************/

/* Build Table Structure */
CREATE TABLE file
(
id SERIAL,
	title TEXT NOT NULL,
	description TEXT NULL,
	filetype_id INTEGER NOT NULL,
	name TEXT NOT NULL,
	size BIGINT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE file ADD CONSTRAINT pkfile
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN file.id IS 'Codul documentului';

COMMENT ON COLUMN file.title IS 'Titlul documentului';

COMMENT ON COLUMN file.description IS 'Descrierea documentului';

COMMENT ON COLUMN file.filetype_id IS 'Codul tipului documentului (refera atributul id al tabelului document_type)';

COMMENT ON COLUMN file.name IS 'Numele fisierului asociat documentului';

COMMENT ON COLUMN file.size IS 'Dimensiunea fisierului (specificata in bytes)';

COMMENT ON TABLE file IS 'Tabel ce contine documentele asociate oricarei entitati din baza de date (studiu sau instanta)';


/******************** Add Table: file_acl ************************/

/* Build Table Structure */
CREATE TABLE file_acl
(
	document_id INTEGER NOT NULL,
	aro_id INTEGER NOT NULL,
	aro_type INTEGER NOT NULL,
	read BOOL NOT NULL,
	"update" BOOL NOT NULL,
	"delete" BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE file_acl ADD CONSTRAINT pkfile_acl
	PRIMARY KEY (document_id, aro_id, aro_type);

/* Add Comments */
COMMENT ON COLUMN file_acl.document_id IS 'Codul documentului asupra caruia sunt definite drepturi de acces';

COMMENT ON COLUMN file_acl.aro_id IS 'Codul obiectului care solicita drepturi de acces';

COMMENT ON COLUMN file_acl.aro_type IS 'Tipul obiectului care solicita drepturi de acces';

COMMENT ON COLUMN file_acl.read IS 'Atribut boolean, ce va avea valoarea true daca exista dreptul de citire; altfel, valoarea atributului va fi false';

COMMENT ON COLUMN file_acl."update" IS 'Atribut boolean, ce va avea valoarea true daca exista dreptul de modificare; altfel, valoarea atributului va fi false';

COMMENT ON COLUMN file_acl."delete" IS 'Atribut boolean, ce va avea valoarea true daca exista dreptul de stergere; altfel, valoarea atributului va fi false';

COMMENT ON TABLE file_acl IS 'Tabel ce contine drepturile de acces asupra documentelor';


/******************** Add Table: file_property_name_value ************************/

/* Build Table Structure */
CREATE TABLE file_property_name_value
(
	property_name_id INTEGER NOT NULL,
	property_value_id INTEGER NOT NULL,
	file_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE file_property_name_value ADD CONSTRAINT pkfile_property_name_value
	PRIMARY KEY (property_name_id, property_value_id, file_id);

/* Add Comments */
COMMENT ON TABLE file_property_name_value IS 'Tabel ce pastreaza asocierile intre un "File" (din tabelul respectiv) si proprietatile fisierului respectiv (name+value)';


/******************** Add Table: form ************************/

/* Build Table Structure */
CREATE TABLE form
(
id BIGSERIAL,
	instance_id INTEGER NOT NULL,
	order_in_instance INTEGER NOT NULL,
	operator_id INTEGER NULL,
	operator_notes TEXT NULL,
	fill_time TIMESTAMP NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE form ADD CONSTRAINT pkform
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE form IS 'Tabel pentru informatiile legate de un chestionarele aplicate (un rand reprezinta o anumita fisa completata pe teren cu raspunsuri)';

/* Add Indexes */
CREATE UNIQUE INDEX "form_instance_id_Idx" ON form (instance_id, order_in_instance);


/******************** Add Table: form_edited_number_var ************************/

/* Build Table Structure */
CREATE TABLE form_edited_number_var
(
	form_id BIGINT NOT NULL,
	variable_id BIGINT NOT NULL,
	value NUMERIC(10, 2) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE form_edited_number_var ADD CONSTRAINT pkform_edited_number_var
	PRIMARY KEY (variable_id, form_id);

/* Add Comments */
COMMENT ON COLUMN form_edited_number_var.form_id IS 'Codul formularului completat in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN form_edited_number_var.variable_id IS 'Codul unei variabile editate de tip numeric in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN form_edited_number_var.value IS 'Valoarea numerica furnizata ca raspuns pentru variabila referita prin atributul variable_id din cadrul instantei identificate prin atributul instance_id, asociata formularului specificat prin atributul form_id  ';

COMMENT ON TABLE form_edited_number_var IS 'Tabel ce inregistreaza raspunsurile numerice ale variabilelor de tip editat';


/******************** Add Table: form_edited_text_var ************************/

/* Build Table Structure */
CREATE TABLE form_edited_text_var
(
	variable_id BIGINT NOT NULL,
	form_id BIGINT NOT NULL,
	text TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE form_edited_text_var ADD CONSTRAINT pkform_edited_text_var
	PRIMARY KEY (variable_id, form_id);

/* Add Comments */
COMMENT ON COLUMN form_edited_text_var.variable_id IS 'Codul unei variabile editate de tip text corespunzatoare instantei identificate prin atributul instance_id';

COMMENT ON COLUMN form_edited_text_var.form_id IS 'Codul formularului completat in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN form_edited_text_var.text IS 'Raspunsul furnizat pentru variabila identificata prin atributul variable_id in cadrul instantei referite prin instance_id, pe formularul identificat prin atributul form_id';

COMMENT ON TABLE form_edited_text_var IS 'Tabel ce inregistreaza raspunsurile text ale variabilelor de tip editat';


/******************** Add Table: form_selection_var ************************/

/* Build Table Structure */
CREATE TABLE form_selection_var
(
	form_id BIGINT NOT NULL,
	variable_id BIGINT NOT NULL,
	item_id BIGINT NOT NULL,
	order_of_items_in_response INTEGER NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE form_selection_var ADD CONSTRAINT pkform_selection_var
	PRIMARY KEY (form_id, variable_id, item_id);

/* Add Comments */
COMMENT ON COLUMN form_selection_var.form_id IS 'Codul unui formular completat in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN form_selection_var.variable_id IS 'Codul unei variabile din cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN form_selection_var.item_id IS 'Identificatorul elementului selectat ca raspuns pentru variabila identificata prin atributul variable_id din instanta referita prin atributul instance_id, asociat formularului specificat prin atributul form_id';

COMMENT ON COLUMN form_selection_var.order_of_items_in_response IS 'Numarul de ordine al elementului corespunzator variabilei in cadrul raspunsului (relevant pentru raspunsuri care accepta o selectie multipla, in care ordinea este importanta)';

COMMENT ON TABLE form_selection_var IS 'Tabel ce inregistreaza raspunsurile la variabilele de selectie (pentru care exista optiuni de raspuns) ';


/******************** Add Table: instance ************************/

/* Build Table Structure */
CREATE TABLE instance
(
id SERIAL,
	study_id INTEGER NOT NULL,
	datestart TIMESTAMP NULL,
	dateend TIMESTAMP NULL,
	unit_analysis_id INTEGER NOT NULL,
	version INTEGER NOT NULL,
	insertion_status INTEGER NOT NULL,
	raw_data BOOL NOT NULL,
	raw_metadata BOOL NOT NULL,
	added_by INTEGER NOT NULL,
	added TIMESTAMP NOT NULL,
	time_meth_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance ADD CONSTRAINT pkinstance
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN instance.id IS 'Codul instantei';

COMMENT ON COLUMN instance.study_id IS 'Codul studiului caruia ii apartine instanta (refera atributul id al tabelului study)';

COMMENT ON COLUMN instance.datestart IS 'Data de inceput a instantei';

COMMENT ON COLUMN instance.dateend IS 'Data de incheiere a instantei';

COMMENT ON COLUMN instance.unit_analysis_id IS 'Codul unitatii de analiza specifice instantei (refera atributul id al tabelului unit_analysis)';

COMMENT ON COLUMN instance.version IS 'Versiunea instantei';

COMMENT ON COLUMN instance.insertion_status IS 'Pasul din wizard-ul de introducere a metadatelor - din moment ce introducerea se face prin wizard, fiecare pas trebuie comis in baza de dat; pana la finalizarea introducerii intregii instante e nevoie sa stim ca ele au fost partial introduse.';

COMMENT ON COLUMN instance.raw_data IS 'daca datele sunt in forma digitizata (YES) sau in forma de fisiere procesabile/editabile (NO)';

COMMENT ON COLUMN instance.raw_metadata IS 'daca metadatele sunt in forma digitizata (YES) sau in forma de fisiere procesabile/editabile (NO)';

COMMENT ON TABLE instance IS 'Tabel ce contine informatiile principale ale instantelor';


/******************** Add Table: instance_acl ************************/

/* Build Table Structure */
CREATE TABLE instance_acl
(
	instance_id INTEGER NOT NULL,
	aro_id INTEGER NOT NULL,
	aro_type INTEGER NOT NULL,
	read BOOL NULL,
	"update" BOOL NULL,
	"delete" BOOL NULL,
	modacl BOOL NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_acl ADD CONSTRAINT pkinstance_acl
	PRIMARY KEY (instance_id, aro_id, aro_type);

/* Add Comments */
COMMENT ON COLUMN instance_acl.instance_id IS 'Codul instantei asupra careia vor fi definite drepturi de acces ';

COMMENT ON COLUMN instance_acl.aro_id IS 'Codul unui obiect care solicita drepturi de acces';

COMMENT ON COLUMN instance_acl.aro_type IS 'Tipul unui obiect care solicita drepturi de acces';

COMMENT ON COLUMN instance_acl.read IS 'Atribut a carui valoare este true daca exista drept de citire; altfel, valoarea sa este false.';

COMMENT ON COLUMN instance_acl."update" IS 'Atribut a carui valoare este true daca exista drept de actualizare; altfel, valoarea sa este false.';

COMMENT ON COLUMN instance_acl."delete" IS 'Atribut a carui valoare este true daca exista drept de stergere; altfel, valoarea sa este false.';

COMMENT ON COLUMN instance_acl.modacl IS 'Atribut boolean, a carui valoare este true daca drepturile pot fi modificate; altfel, valoarea atributului este false';

COMMENT ON TABLE instance_acl IS 'Tabel ce contine listele pentru controlul accesului la nivel de instanta';


/******************** Add Table: instance_descr ************************/

/* Build Table Structure */
CREATE TABLE instance_descr
(
	instance_id INTEGER NOT NULL,
	lang_id CHAR(2) NOT NULL,
	weighting TEXT NULL,
	research_instrument TEXT NULL,
	scope TEXT NULL,
	universe TEXT NULL,
	abstract TEXT NULL,
	title TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_descr ADD CONSTRAINT pkinstance_descr
	PRIMARY KEY (instance_id, lang_id);

/* Add Comments */
COMMENT ON COLUMN instance_descr.instance_id IS 'Codul instantei pentru care sunt furnizate elemente descriptive';

COMMENT ON COLUMN instance_descr.title IS 'Titlul instantei';

COMMENT ON TABLE instance_descr IS 'Tabel ce contine elementele descriptive ale instantelor';


/******************** Add Table: instance_documents ************************/

/* Build Table Structure */
CREATE TABLE instance_documents
(
	instance_id INTEGER NOT NULL,
	document_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_documents ADD CONSTRAINT pkinstance_documents
	PRIMARY KEY (instance_id, document_id);

/* Add Comments */
COMMENT ON COLUMN instance_documents.instance_id IS 'Codul instantei care contine documentul specificat prin atributul document_id (refera atributul id al tabelului instance)';

COMMENT ON COLUMN instance_documents.document_id IS 'Codul documentului asociat instantei specificate prin atributul instance_id (refera atributul id al tabelului documents)';

COMMENT ON TABLE instance_documents IS 'Tabel ce contine asocierile dintre instante si documente (implementeaza relatia many-to-many intre tabelele instance si documents)';


/******************** Add Table: instance_keyword ************************/

/* Build Table Structure */
CREATE TABLE instance_keyword
(
	instance_id INTEGER NOT NULL,
	keyword_id INTEGER NOT NULL,
	added TIMESTAMP NOT NULL,
	added_by INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_keyword ADD CONSTRAINT pkinstance_keyword
	PRIMARY KEY (instance_id, keyword_id, added_by);

/* Add Comments */
COMMENT ON COLUMN instance_keyword.instance_id IS 'Codul instantei careia ii este asociat cuvantul cheie referit prin atributul keyword_id';

COMMENT ON COLUMN instance_keyword.keyword_id IS 'Codul unui cuvant cheie asociat instantei identificate prin atributul instance_id';

COMMENT ON COLUMN instance_keyword.added IS 'Momentul de timp la care a fost adaugata o asociere intre o instanta si un cuvant cheie';

COMMENT ON COLUMN instance_keyword.added_by IS 'Utilizatorul care a adaugat asocierea dintre o instanta si un cuvant cheie';

COMMENT ON TABLE instance_keyword IS 'Tabel ce stocheaza asocierile dintre cuvinte cheie si instante (implementeaza relatia many-to-many intre tabelele instance si keyword)';


/******************** Add Table: instance_org ************************/

/* Build Table Structure */
CREATE TABLE instance_org
(
	org_id INTEGER NOT NULL,
	instance_id INTEGER NOT NULL,
	assoc_type_id INTEGER NOT NULL,
	assoc_details TEXT NULL,
	citation TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_org ADD CONSTRAINT pkinstance_org
	PRIMARY KEY (org_id, instance_id, assoc_type_id);

/* Add Comments */
COMMENT ON COLUMN instance_org.org_id IS 'Codul organizatiei care se afla in relatia specificata prin atributul assoc_type_id cu instanta identificata prin atributul instance_id (refera atributul id al tabelului org)';

COMMENT ON COLUMN instance_org.instance_id IS 'Codul instantei care se afla in relatia specificata prin atributul assoc_type_id cu organizatia identificata prin atributul org_id (refera atributul id al tabelului instance)';

COMMENT ON COLUMN instance_org.assoc_type_id IS 'Codul tipului de asociere corespunzator relatiei dintre organizatia identificata prin atributul org_id si instanta specificata prin atributul instance_id (refera atributul id al tabelului instance_org_assoc)';

COMMENT ON COLUMN instance_org.assoc_details IS 'Detaliile asocierii dintre  organizatia identificata prin atributul org_id si instanta specificata prin atributul instance_id';

COMMENT ON COLUMN instance_org.citation IS 'Modalitatea de citare in cadrul instantei identificate prin atributul instance_id realizate de catre organizatia referita prin atributul org_id';

COMMENT ON TABLE instance_org IS 'Tabel ce contine asocierile dintre instante si organizatii (implementeaza relatia many-to-many intre tabelele instance si org)';


/******************** Add Table: instance_org_assoc ************************/

/* Build Table Structure */
CREATE TABLE instance_org_assoc
(
id SERIAL,
	assoc_name VARCHAR(100) NOT NULL,
	assoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_org_assoc ADD CONSTRAINT pkinstance_org_assoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN instance_org_assoc.id IS 'Codul unui tip de asociere care poate exista intre o instanta si o organizatie';

COMMENT ON COLUMN instance_org_assoc.assoc_name IS 'Numele unui tip de asociere care poate exista intre o instanta si o organizatie';

COMMENT ON COLUMN instance_org_assoc.assoc_description IS 'Descrierea unui tip de asociere care poate exista intre o instanta si o organizatie';

COMMENT ON TABLE instance_org_assoc IS 'Tabel ce contine tipurile de asociere care pot exista intre o instanta si organizatie';


/******************** Add Table: instance_person ************************/

/* Build Table Structure */
CREATE TABLE instance_person
(
	person_id INTEGER NOT NULL,
	instance_id INTEGER NOT NULL,
	assoc_type_id INTEGER NOT NULL,
	assoc_details TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_person ADD CONSTRAINT pkinstance_person
	PRIMARY KEY (person_id, instance_id, assoc_type_id);

/* Add Comments */
COMMENT ON COLUMN instance_person.person_id IS 'Codul persoanei aflate in relatia specificata prin atributul assoc_type_id cu instanta identificata prin atributul instance_id (refera atributul id al tabelului person)';

COMMENT ON COLUMN instance_person.instance_id IS 'Codul instantei care se afla in relatia specificata prin atributul assoc_type_id cu instanta identificata prin atributul instance_id (refera atributul id al tabelului instance)';

COMMENT ON COLUMN instance_person.assoc_type_id IS 'Codul tipului de asociere care exista intre instanta specificata prin atributul instance_id si persoana identificata prin atributul person_id (refera atributul id al tabelului instance_person_assoc)';

COMMENT ON TABLE instance_person IS 'Tabel care stocheaza asocierile intre instante si persoane (implementeaza relatia many-to-many intre tabelele person si instance)';


/******************** Add Table: instance_person_assoc ************************/

/* Build Table Structure */
CREATE TABLE instance_person_assoc
(
id SERIAL,
	assoc_name VARCHAR(100) NOT NULL,
	assoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_person_assoc ADD CONSTRAINT pkinstance_person_assoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN instance_person_assoc.id IS 'Codul unui tip de asociere care poate exista intre persoane si instante';

COMMENT ON COLUMN instance_person_assoc.assoc_name IS 'Denumirea unui tip de asociere care poate exista intre persoane si instante';

COMMENT ON COLUMN instance_person_assoc.assoc_description IS 'Descrierea tipului de asociere identificat prin atributul id';

COMMENT ON TABLE instance_person_assoc IS 'Tabel ce contine tipurile de asociere intre instanta si persoana';


/******************** Add Table: instance_sampling_procedure ************************/

/* Build Table Structure */
CREATE TABLE instance_sampling_procedure
(
	instance_id INTEGER NOT NULL,
	sampling_procedure_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_sampling_procedure ADD CONSTRAINT pkinstance_sampling_procedure
	PRIMARY KEY (instance_id, sampling_procedure_id);

/* Add Comments */
COMMENT ON TABLE instance_sampling_procedure IS 'Tabel pentru reprezentarea relatiilor NxM intre o anumita "Instance" si o anumita "Sampling_Procedure"';


/******************** Add Table: instance_topic ************************/

/* Build Table Structure */
CREATE TABLE instance_topic
(
	instance_id INTEGER NOT NULL,
	topic_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE instance_topic ADD CONSTRAINT pkinstance_topic
	PRIMARY KEY (instance_id, topic_id);

/* Add Comments */
COMMENT ON COLUMN instance_topic.instance_id IS 'Codul unei instante careia i se asociaza topic-ul specificat prin atributul topic_id (refera atributul id din tabelul instance)';

COMMENT ON COLUMN instance_topic.topic_id IS 'Codul topic-ului care este asociat instantei specificate prin atributul instance_id (refera atributul id din tabelul topic)';

COMMENT ON TABLE instance_topic IS 'Tabel ce stocheaza asocierile dintre instante si topic-uri (implementeaza relatia many-to-many intre aceste tabele)';


/******************** Add Table: internet ************************/

/* Build Table Structure */
CREATE TABLE internet
(
id SERIAL,
	internet_type VARCHAR(50) NULL,
	internet TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE internet ADD CONSTRAINT pkinternet
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN internet.id IS 'Codul contului pe internet al unui contact';

COMMENT ON COLUMN internet.internet_type IS 'Tipul contului ';

COMMENT ON TABLE internet IS 'Tabel ce contine toate conturile de pe retelele sociale de pe internet (facebook, twitter, im  si altele)';


/******************** Add Table: item ************************/

/* Build Table Structure */
CREATE TABLE item
(
id BIGSERIAL,
	name VARCHAR(100) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE item ADD CONSTRAINT pkitem
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN item.id IS 'Identificatorul item-ului';

COMMENT ON COLUMN item.name IS 'Numele item-ului';

COMMENT ON TABLE item IS 'Tabel ce stocheaza elementele (item-urile) variabilelor de selectie din baza de date';


/******************** Add Table: keyword ************************/

/* Build Table Structure */
CREATE TABLE keyword
(
id SERIAL,
	name TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE keyword ADD CONSTRAINT pkkeyword
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN keyword.id IS 'Codul cuvantului cheie';

COMMENT ON COLUMN keyword.name IS 'Cuvantul cheie';

COMMENT ON TABLE keyword IS 'Tabel ce contine cuvintele cheie ale studiilor si instantelor din baza de date';


/******************** Add Table: lang ************************/

/* Build Table Structure */
CREATE TABLE lang
(
	id CHAR(2) NOT NULL,
	name VARCHAR(50) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE lang ADD CONSTRAINT pklang
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN lang.id IS 'Codul unei limbi ce poate fi utilizata pentru un termen din baza de date';

COMMENT ON COLUMN lang.name IS 'Denumirea unei limbi';

COMMENT ON TABLE lang IS 'Tabel ce contine limbile utilizate pentru unii termeni din baza de date';

/* Add Indexes */
CREATE UNIQUE INDEX "lang_name_Idx" ON lang (name);


/******************** Add Table: meth_coll_type ************************/

/* Build Table Structure */
CREATE TABLE meth_coll_type
(
	instance_id INTEGER NOT NULL,
	collection_model_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE meth_coll_type ADD CONSTRAINT pkmeth_coll_type
	PRIMARY KEY (instance_id, collection_model_id);

/* Add Comments */
COMMENT ON COLUMN meth_coll_type.instance_id IS 'Codul instantei pentru care se asociaza un model de colectare a datelor';

COMMENT ON COLUMN meth_coll_type.collection_model_id IS 'Codul tipului de model de colectare a datelor asociat instantei identificate prin atributul instance_id';

COMMENT ON TABLE meth_coll_type IS 'Tabel ce stocheaza tipurile modelelor de colectare a datelor utilizate in cadrul unei instante';


/******************** Add Table: news ************************/

/* Build Table Structure */
CREATE TABLE news
(
id SERIAL,
	added_by INTEGER NOT NULL,
	added TIMESTAMP NOT NULL,
	visible BOOL NOT NULL,
	title TEXT NOT NULL,
	content TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE news ADD CONSTRAINT pknews
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN news.id IS 'Codul stirii';

COMMENT ON COLUMN news.added_by IS 'Codul utilizatorului care a introdus stirea';

COMMENT ON COLUMN news.added IS 'Momentul de timp la care stirea a fost adaugata';

COMMENT ON COLUMN news.visible IS 'Atribut boolean, a carui valoare este true daca stirea este vizibila';

COMMENT ON COLUMN news.title IS 'Titlul stirii';

COMMENT ON COLUMN news.content IS 'Continutul stirii';

COMMENT ON TABLE news IS 'Tabel ce stocheaza stirile ce vor aparea in interfata aplicatiei';


/******************** Add Table: org ************************/

/* Build Table Structure */
CREATE TABLE org
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	org_prefix_id INTEGER NULL,
	fullname VARCHAR(100) NOT NULL,
	org_sufix_id INTEGER NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE org ADD CONSTRAINT pkorg
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN org.id IS 'Codul organizatiei';

COMMENT ON COLUMN org.name IS 'Denumirea prescurtata a organizatiei (posibil un acronim al acesteia)';

COMMENT ON COLUMN org.org_prefix_id IS 'Codul prefixului organizatiei (refera atributul id din tabelul org_prefix)';

COMMENT ON COLUMN org.fullname IS 'Denumirea completa a organizatiei ';

COMMENT ON COLUMN org.org_sufix_id IS 'Codul sufixului organizatiei (refera atributul id din tabelul org_sufix)';

COMMENT ON TABLE org IS 'Tabel ce contine toate organizatiile din baza de date';


/******************** Add Table: org_address ************************/

/* Build Table Structure */
CREATE TABLE org_address
(
	org_id INTEGER NOT NULL,
	address_id INTEGER NOT NULL,
	datestart TIMESTAMP NULL,
	dateend TIMESTAMP NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE org_address ADD CONSTRAINT pkorg_address
	PRIMARY KEY (org_id, address_id);

/* Add Comments */
COMMENT ON COLUMN org_address.org_id IS 'Codul organizatiei care detine o adresa identificata prin atributul address_id (refera atributul id din tabelul org) ';

COMMENT ON COLUMN org_address.address_id IS 'Codul adresei detinute de organizatia specificata prin atributul org_id (refera atributul id din tabelul org)';

COMMENT ON COLUMN org_address.datestart IS 'Data incepand de la care adresa organizatiei referite prin atributul org_id a devenit cea identificata prin atributul address_id';

COMMENT ON COLUMN org_address.dateend IS 'Data pana la care adresa organizatiei referite prin org_id a fost cea identificata prin address_id';

COMMENT ON TABLE org_address IS 'Tabel ce contine asocierile dintre organizatii si adrese (implementeaza relatia many-to-many intre tabelele org si adresa)';


/******************** Add Table: org_email ************************/

/* Build Table Structure */
CREATE TABLE org_email
(
	org_id INTEGER NOT NULL,
	email_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE org_email ADD CONSTRAINT pkorg_email
	PRIMARY KEY (org_id, email_id);

/* Add Comments */
COMMENT ON TABLE org_email IS 'Tabel de legatura pentru relatia de tip NxM intre "Org" si "Email"';


/******************** Add Table: org_internet ************************/

/* Build Table Structure */
CREATE TABLE org_internet
(
	org_id INTEGER NOT NULL,
	internet_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE org_internet ADD CONSTRAINT pkorg_internet
	PRIMARY KEY (org_id, internet_id);

/* Add Comments */
COMMENT ON TABLE org_internet IS 'Tabel de legatura pentru relatia de tip NxM intre "Org" si "Internet"';


/******************** Add Table: org_phone ************************/

/* Build Table Structure */
CREATE TABLE org_phone
(
	org_id INTEGER NOT NULL,
	phone_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE org_phone ADD CONSTRAINT pkorg_phone
	PRIMARY KEY (org_id, phone_id);

/* Add Comments */
COMMENT ON TABLE org_phone IS 'Tabel de legatura pentru relatia de tip NxM intre "Org" si "Phone"';


/******************** Add Table: org_prefix ************************/

/* Build Table Structure */
CREATE TABLE org_prefix
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE org_prefix ADD CONSTRAINT pkorg_prefix
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN org_prefix.id IS 'Codul prefixului de organizatie';

COMMENT ON COLUMN org_prefix.name IS 'Prefixul de organizatie (SC, alte prefixe)';

COMMENT ON COLUMN org_prefix.description IS 'Descrierea prefixului';

COMMENT ON TABLE org_prefix IS 'Tabel ce contine prefixele organizatiilor ';


/******************** Add Table: org_relation_type ************************/

/* Build Table Structure */
CREATE TABLE org_relation_type
(
id SERIAL,
	name VARCHAR(100) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE org_relation_type ADD CONSTRAINT pkorg_relation_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN org_relation_type.id IS 'Codul tipului relatiei intre organizatii';

COMMENT ON COLUMN org_relation_type.name IS 'Numele tipului relatiei intre organizatii';

COMMENT ON TABLE org_relation_type IS 'Tabel ce contine tipurile relatiilor care pot exista intre organizatii';


/******************** Add Table: org_relations ************************/

/* Build Table Structure */
CREATE TABLE org_relations
(
	org_1_id INTEGER NOT NULL,
	org_2_id INTEGER NOT NULL,
	datestart TIMESTAMP NULL,
	dateend TIMESTAMP NULL,
	org_relation_type_id INTEGER NOT NULL,
	details TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE org_relations ADD CONSTRAINT pkorg_relations
	PRIMARY KEY (org_1_id, org_2_id, org_relation_type_id);

/* Add Comments */
COMMENT ON COLUMN org_relations.org_1_id IS 'Codul primei organizatii implicate in relatie';

COMMENT ON COLUMN org_relations.org_2_id IS 'Codul celei de-a doua organizatii implicate in relatie';

COMMENT ON COLUMN org_relations.datestart IS 'Data de inceput a relatiei specificate prin tipul org_relation_type_id intre organizatiile specificate prin org_1_id si org_2_id';

COMMENT ON COLUMN org_relations.dateend IS 'Data de final a relatiei specificate prin tipul org_relation_type intre organizatiile specificate prin org_1_id si org_2_id';

COMMENT ON COLUMN org_relations.org_relation_type_id IS 'Codul tipului relatiei demarate la datestart intre organizatiile specificate prin org_1_id si org_2_id ';

COMMENT ON COLUMN org_relations.details IS 'Detaliile relatiei demarate la datestart intre organizatiile specificate prin org_1_id si org_2_id';

COMMENT ON TABLE org_relations IS 'Tabel ce stocheaza relatiile dintre organizatii (filiala, subsidiare, holding)';


/******************** Add Table: org_sufix ************************/

/* Build Table Structure */
CREATE TABLE org_sufix
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE org_sufix ADD CONSTRAINT pkorg_sufix
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN org_sufix.id IS 'Codul sufixului de organizatie';

COMMENT ON COLUMN org_sufix.name IS 'Denumirea sufixului de organizatie (SRL, etc)';

COMMENT ON COLUMN org_sufix.description IS 'Descrierea sufixului de organizatie';

COMMENT ON TABLE org_sufix IS 'Tabel ce contine sufixele organizatiilor';


/******************** Add Table: other_statistic ************************/

/* Build Table Structure */
CREATE TABLE other_statistic
(
	variable_id BIGINT NOT NULL,
	name VARCHAR(100) NOT NULL,
	value REAL NOT NULL,
	description TEXT NULL,
id BIGSERIAL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE other_statistic ADD CONSTRAINT pkother_statistic
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN other_statistic.variable_id IS 'Codul variabilei pentru care sunt stocate statistici';

COMMENT ON COLUMN other_statistic.name IS 'Denumirea statisticii';

COMMENT ON COLUMN other_statistic.value IS 'Valoarea statisticii';

COMMENT ON TABLE other_statistic IS 'Tabel ce contine statistici specifice variabilelor editate';


/******************** Add Table: person ************************/

/* Build Table Structure */
CREATE TABLE person
(
id SERIAL,
	fname VARCHAR(100) NOT NULL,
	mname VARCHAR(100) NULL,
	lname VARCHAR(100) NOT NULL,
	prefix_id INTEGER NULL,
	suffix_id INTEGER NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE person ADD CONSTRAINT pkperson
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN person.id IS 'Codul persoanei';

COMMENT ON COLUMN person.fname IS 'Prenumele persoanei';

COMMENT ON COLUMN person.mname IS 'Numele din mijloc al persoanei';

COMMENT ON COLUMN person.lname IS 'Numele de familie al persoanei';

COMMENT ON COLUMN person.prefix_id IS 'Codul prefixului corespunzator persoanei (refera atributul id din tabelul prefix)';

COMMENT ON COLUMN person.suffix_id IS 'Codul sufixului';

COMMENT ON TABLE person IS 'Tabel unic pentru toate persoanele din baza de date';


/******************** Add Table: person_address ************************/

/* Build Table Structure */
CREATE TABLE person_address
(
	person_id INTEGER NOT NULL,
	address_id INTEGER NOT NULL,
	datestart TIMESTAMP NULL,
	dateend TIMESTAMP NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE person_address ADD CONSTRAINT pkperson_address
	PRIMARY KEY (person_id, address_id);

/* Add Comments */
COMMENT ON COLUMN person_address.person_id IS 'Codul persoanei pentru care este stocata o asociere cu adresa referita prin atributul address_id (refera atributul id din tabelul person)';

COMMENT ON COLUMN person_address.address_id IS 'Codul adresei care este asociata persoanei identificate prin atributul person_id (refera atributul id din tabelul address)';

COMMENT ON COLUMN person_address.datestart IS 'Data incepand de la care persoana identificata prin atributul person_id a avut adresa referita prin atributul address_id';

COMMENT ON COLUMN person_address.dateend IS 'Data pana la care persoana identificata prin atributul person_id a avut adresa referita prin atributul address_id';

COMMENT ON TABLE person_address IS 'Tabel ce contine asocierile intre persoane si adrese (implementeaza relatia many-to-many intre tabelele address si person)';


/******************** Add Table: person_email ************************/

/* Build Table Structure */
CREATE TABLE person_email
(
	person_id INTEGER NOT NULL,
	email_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE person_email ADD CONSTRAINT pkperson_email
	PRIMARY KEY (person_id, email_id);

/* Add Comments */
COMMENT ON TABLE person_email IS 'Tabel de legatura pentru relatia de tip NxM intre "Person" si "Email"';


/******************** Add Table: person_internet ************************/

/* Build Table Structure */
CREATE TABLE person_internet
(
	person_id INTEGER NOT NULL,
	internet_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE person_internet ADD CONSTRAINT pkperson_internet
	PRIMARY KEY (person_id, internet_id);

/* Add Comments */
COMMENT ON TABLE person_internet IS 'Tabel de legatura pentru relatia de tip NxM intre "Person" si "Internet"';


/******************** Add Table: person_links ************************/

/* Build Table Structure */
CREATE TABLE person_links
(
id SERIAL,
	person_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	simscore NUMERIC(10, 2) NOT NULL,
	namescore NUMERIC(10, 2) NOT NULL,
	emailscore NUMERIC(10, 2) NOT NULL,
	status INTEGER NOT NULL,
	status_by INTEGER NOT NULL,
	status_time TIMESTAMP NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE person_links ADD CONSTRAINT pkperson_links
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN person_links.id IS 'Codul relatiei intre persoana referita prin atributul person_id si utilizatorul identificat prin user_id';

COMMENT ON COLUMN person_links.person_id IS 'Codul persoanei (refera atributul id din tabelul person)';

COMMENT ON COLUMN person_links.user_id IS 'Codul utilizatorului (refera atributul id din tabelul user)';

COMMENT ON COLUMN person_links.simscore IS 'Scorul general de similaritate intre persoana si utilizator (determinat pentru a stabili automat relatia intre persoana si utilizator)';

COMMENT ON COLUMN person_links.namescore IS 'Scorul de similaritate bazat pe numele persoanei si al utilizatorului (determinat pentru a stabili automat relatia intre persoana si utilizator)';

COMMENT ON COLUMN person_links.emailscore IS 'Scorul de similaritate bazat pe adresa de email (determinat pentru a stabili automat relatia intre persoana si utilizator)';

COMMENT ON COLUMN person_links.status IS 'Starea deciziei existentei unei legaturi intre intre persoana si utilizator (confirmat, infirmat, in asteptare)';

COMMENT ON COLUMN person_links.status_by IS 'Codul utilizatorului care a actualizat campul status (refera atributul id din tabelul user)';

COMMENT ON COLUMN person_links.status_time IS 'Momentul de timp al ultimei actualizari a atributului status';

COMMENT ON TABLE person_links IS 'Tabel ce stocheaza relatiile dintre persoane si utilizatorii aplicatiei ';


/******************** Add Table: person_org ************************/

/* Build Table Structure */
CREATE TABLE person_org
(
	person_id INTEGER NOT NULL,
	org_id INTEGER NOT NULL,
	role_id INTEGER NOT NULL,
	datestart TIMESTAMP NULL,
	dateend TIMESTAMP NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE person_org ADD CONSTRAINT pkperson_org
	PRIMARY KEY (person_id, org_id, role_id);

/* Add Comments */
COMMENT ON COLUMN person_org.person_id IS 'Identificatorul persoanei care lucreaza in organizatie';

COMMENT ON COLUMN person_org.org_id IS 'Idetificatorul organizatiei';

COMMENT ON COLUMN person_org.role_id IS 'Identificatorul rolului detinut de persoana in cadrul organizatiei (refera atributul id al tabelului person_role)';

COMMENT ON COLUMN person_org.datestart IS 'Data de inceput a apartenentei persoanei la organizatie';

COMMENT ON COLUMN person_org.dateend IS 'Data de final a apartenentei persoanei la organizatie';

COMMENT ON TABLE person_org IS 'Tabel ce stocheaza asocierile dintre persoane si organizatii (implementeaza relatia many-to-many intre tabelele person si org)';


/******************** Add Table: person_phone ************************/

/* Build Table Structure */
CREATE TABLE person_phone
(
	person_id INTEGER NOT NULL,
	phone_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE person_phone ADD CONSTRAINT pkperson_phone
	PRIMARY KEY (person_id, phone_id);

/* Add Comments */
COMMENT ON TABLE person_phone IS 'Tabel de legatura pentru relatia de tip NxM intre "Person" si "Phone"';


/******************** Add Table: person_role ************************/

/* Build Table Structure */
CREATE TABLE person_role
(
id SERIAL,
	name TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE person_role ADD CONSTRAINT pkperson_role
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN person_role.id IS 'Codul rolului';

COMMENT ON COLUMN person_role.name IS 'Denumirea rolului respectiv (director, manager, persoana de contact, etc)';

COMMENT ON TABLE person_role IS 'Tabel care contine rolurile pe care le pot detine persoanele in cadrul organizatiilor';


/******************** Add Table: phone ************************/

/* Build Table Structure */
CREATE TABLE phone
(
id SERIAL,
	phone VARCHAR(30) NOT NULL,
	phone_type VARCHAR(50) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE phone ADD CONSTRAINT pkphone
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE phone IS 'Tabel unic ce pastreaza Telefoane';


/******************** Add Table: prefix ************************/

/* Build Table Structure */
CREATE TABLE prefix
(
id SERIAL,
	name VARCHAR(50) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE prefix ADD CONSTRAINT pkprefix
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN prefix.id IS 'Codul prefixului pentru persoane';

COMMENT ON COLUMN prefix.name IS 'Denumirea prefixului care poate fi utilizat pentru persoane (Domnul, Doamna, etc.)';

COMMENT ON TABLE prefix IS 'Tabel ce contine prefixele corespunzatoare formulelor de adresare catre persoane';


/******************** Add Table: property_name ************************/

/* Build Table Structure */
CREATE TABLE property_name
(
id SERIAL,
	name TEXT NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE property_name ADD CONSTRAINT pkproperty_name
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE property_name IS 'Tabel folosit pentru Proprietati (stocheaza numele acestora)';


/******************** Add Table: property_value ************************/

/* Build Table Structure */
CREATE TABLE property_value
(
id SERIAL,
	value TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE property_value ADD CONSTRAINT pkproperty_value
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE property_value IS 'Tabel folosit pentru Proprietati (stocheaza valorile acestora)';


/******************** Add Table: region ************************/

/* Build Table Structure */
CREATE TABLE region
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	regiontype_id INTEGER NOT NULL,
	country_id CHAR(2) NOT NULL,
	region_code VARCHAR(50) NULL,
	region_code_name VARCHAR(50) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE region ADD CONSTRAINT pkregion
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN region.id IS 'Identificatorul regiunii';

COMMENT ON COLUMN region.name IS 'Numele regiunii';

COMMENT ON COLUMN region.regiontype_id IS 'Tipul regiunii (refera atributul id din tabelul region_type)';

COMMENT ON COLUMN region.country_id IS 'Codul tarii corespunzatoare regiunii (refera atributul id din tabelul country)';

COMMENT ON TABLE region IS 'Tabel care contine regiunile corespunzatoare tarilor referite in model';


/******************** Add Table: region_city ************************/

/* Build Table Structure */
CREATE TABLE region_city
(
	region_id INTEGER NOT NULL,
	city_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE region_city ADD CONSTRAINT pkregion_city
	PRIMARY KEY (region_id, city_id);

/* Add Comments */
COMMENT ON COLUMN region_city.region_id IS 'Codul regiunii din care face parte un oras';

COMMENT ON COLUMN region_city.city_id IS 'Codul orasului asociat regiunii';

COMMENT ON TABLE region_city IS 'Tabel ce contine asocierile sintre orase si regiuni ';


/******************** Add Table: regiontype ************************/

/* Build Table Structure */
CREATE TABLE regiontype
(
id SERIAL,
	name VARCHAR(150) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE regiontype ADD CONSTRAINT pkregiontype
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN regiontype.id IS 'Identificatorul tipului de regiune';

COMMENT ON COLUMN regiontype.name IS 'Denumirea tipului de regiune';

COMMENT ON TABLE regiontype IS 'Tabel ce contine tipurile regiunilor corespunzatoare studiilor efectuate';


/******************** Add Table: rodauser ************************/

/* Build Table Structure */
CREATE TABLE rodauser
(
id SERIAL,
	credential_provider TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE rodauser ADD CONSTRAINT pkrodauser
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN rodauser.id IS 'Codul utilizatorului aplicatiei';

COMMENT ON COLUMN rodauser.credential_provider IS 'Furnizorul de informatii de acces pentru utilizatorul respectiv';

COMMENT ON TABLE rodauser IS 'Tabel ce contine utilizatorii aplicatiei';


/******************** Add Table: "role" ************************/

/* Build Table Structure */
CREATE TABLE "role"
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE "role" ADD CONSTRAINT pkrole
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN "role".id IS 'Codul unui role al aplicatiei ';

COMMENT ON COLUMN "role".name IS 'Denumirea role-ului aplicatiei';

COMMENT ON COLUMN "role".description IS 'Descrierea role-ului';

COMMENT ON TABLE "role" IS 'Tabel care stocheaza role-urile care pot fi asociate utilizatorilor aplicatiei';


/******************** Add Table: sampling_procedure ************************/

/* Build Table Structure */
CREATE TABLE sampling_procedure
(
id SERIAL,
	name TEXT NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE sampling_procedure ADD CONSTRAINT pksampling_procedure
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE sampling_procedure IS 'Tabel pentru procedurile de esantionare. (cf. DDI Codebook:) The type of sample and sample design used to select the survey respondents to represent the population. These may include one or more of the following: no sampling (total universe); quota sample; simple random sample; one-stage stratified or systematic random sample; one-stage cluster sample; multi-stage stratified random sample; quasi-random (e.g. random walk) sample; purposive selection/case studies; volunteer sample; convenience sample. ';


/******************** Add Table: scale ************************/

/* Build Table Structure */
CREATE TABLE scale
(
	item_id BIGINT NOT NULL,
	"minValue_id" BIGINT NOT NULL,
	"maxValue_id" BIGINT NOT NULL,
	units SMALLINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE scale ADD CONSTRAINT pkscale
	PRIMARY KEY (item_id);

/* Add Comments */
COMMENT ON COLUMN scale.item_id IS 'Codul item-ului';

COMMENT ON COLUMN scale."minValue_id" IS 'Codul valorii minime (refera atributul itemId din tabelul value) ';

COMMENT ON COLUMN scale."maxValue_id" IS 'Codul valorii maxime (refera atributul itemId din tabelul value) ';

COMMENT ON COLUMN scale.units IS 'Unitatea scalei respective';

COMMENT ON TABLE scale IS 'Tabel ce stocheaza elementele de tip scala ale variabilelor de selectie';


/******************** Add Table: selection_variable ************************/

/* Build Table Structure */
CREATE TABLE selection_variable
(
	variable_id BIGINT NOT NULL,
	min_count SMALLINT NOT NULL,
	max_count SMALLINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE selection_variable ADD CONSTRAINT pkselection_variable
	PRIMARY KEY (variable_id);

/* Add Comments */
COMMENT ON COLUMN selection_variable.variable_id IS 'Codul unei variabile de selectie din cadrul instantei identificate prin instance_id';

COMMENT ON COLUMN selection_variable.min_count IS 'Numarul minim de selectii asteptate ca raspuns';

COMMENT ON COLUMN selection_variable.max_count IS 'Numarul maxim de selectii asteptate ca raspuns';

COMMENT ON TABLE selection_variable IS 'Tabel ce contine informatii despre variabilele de selectie din instante';


/******************** Add Table: selection_variable_item ************************/

/* Build Table Structure */
CREATE TABLE selection_variable_item
(
	variable_id BIGINT NOT NULL,
	item_id BIGINT NOT NULL,
	order_of_item_in_variable INTEGER NOT NULL,
	response_card_file_id INTEGER NULL,
	frequency_value REAL NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE selection_variable_item ADD CONSTRAINT pkselection_variable_item
	PRIMARY KEY (variable_id, item_id);

/* Add Comments */
COMMENT ON COLUMN selection_variable_item.variable_id IS 'Codul unei variabile ce apare in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN selection_variable_item.item_id IS 'Codul unui item de raspuns pentru variabila referita prin atributul variable_id';

COMMENT ON COLUMN selection_variable_item.order_of_item_in_variable IS 'Numarul de ordine al item-ului referit prin atributul item_id in cadrul variabilei identificate prin atributul variable_id';

COMMENT ON TABLE selection_variable_item IS 'Tabel ce contine elementele variabilelor de selectie din cadrul unei instante';

/* Add Indexes */
CREATE UNIQUE INDEX "selection_variable_item_order_Idx" ON selection_variable_item (order_of_item_in_variable, variable_id);


/******************** Add Table: setting ************************/

/* Build Table Structure */
CREATE TABLE setting
(
id SERIAL,
	name VARCHAR(150) NOT NULL,
	setting_group INTEGER NOT NULL,
	description TEXT NULL,
	predefined_values TEXT NOT NULL,
	default_value TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE setting ADD CONSTRAINT pksetting
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN setting.id IS 'Codul unei setari a aplicatiei';

COMMENT ON COLUMN setting.name IS 'Denumirea unei setari a aplicatiei';

COMMENT ON COLUMN setting.setting_group IS 'Grupul din care setarea face parte (refera atributul id din tabelul setting_group)';

COMMENT ON COLUMN setting.description IS 'Descrierea unei setari a aplicatiei';

COMMENT ON COLUMN setting.predefined_values IS 'Valori predefinite ale setarii';

COMMENT ON COLUMN setting.default_value IS 'Valoarea implicita a setarii';

COMMENT ON TABLE setting IS 'Tabel care contine setarile aplicatiei';


/******************** Add Table: setting_group ************************/

/* Build Table Structure */
CREATE TABLE setting_group
(
id SERIAL,
	name VARCHAR(150) NOT NULL,
	parent INTEGER NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE setting_group ADD CONSTRAINT pksetting_group
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN setting_group.id IS 'Codul unui grup de setari ale aplicatiei';

COMMENT ON COLUMN setting_group.name IS 'Denumirea unui grup de setari ale aplicatiei';

COMMENT ON COLUMN setting_group.parent IS 'Codul grupului parinte al grupului de setari ale aplicatiei (refera atributul id al tabelului setting_group)';

COMMENT ON COLUMN setting_group.description IS 'Descrierea grupului de setari ale aplicatiei';

COMMENT ON TABLE setting_group IS 'Tabel care stocheaza grupurile de setari ale aplicatiei';


/******************** Add Table: setting_value ************************/

/* Build Table Structure */
CREATE TABLE setting_value
(
	setting_id INTEGER NOT NULL,
	value TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE setting_value ADD CONSTRAINT pksetting_value
	PRIMARY KEY (setting_id);

/* Add Comments */
COMMENT ON COLUMN setting_value.setting_id IS 'Codul setarii a carei valoare este inregistrata (refera atributul id din tabelul setting)';

COMMENT ON COLUMN setting_value.value IS 'Valoarea setarii referite prin atributul setting_id';

COMMENT ON TABLE setting_value IS 'Tabel care contine valorile setarilor aplicatiei';


/******************** Add Table: skip ************************/

/* Build Table Structure */
CREATE TABLE skip
(
	variable_id BIGINT NOT NULL,
id BIGSERIAL,
	condition TEXT NOT NULL,
	next_variable_id BIGINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE skip ADD CONSTRAINT pkskip
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN skip.variable_id IS 'Identificatorul variabilei';

COMMENT ON COLUMN skip.id IS 'Identificatorul saltului, corespunzator variabilei referite prin atributul variable_id din cadrul instantei identificate prin atributul instance_id ';

COMMENT ON COLUMN skip.condition IS 'Conditia specificata ca text, care in urma evaluarii va confirma saltul';

COMMENT ON COLUMN skip.next_variable_id IS 'Identificatorul variabilei la care se va face salt';

COMMENT ON TABLE skip IS 'Tabel ce contine salturile care pot avea loc de la o variabila la alta';


/******************** Add Table: source ************************/

/* Build Table Structure */
CREATE TABLE source
(
	org_id INTEGER NOT NULL,
	sourcetype_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE source ADD CONSTRAINT pksource
	PRIMARY KEY (org_id);

/* Add Comments */
COMMENT ON COLUMN source.org_id IS 'Codul organizatiei care reprezinta o sursa a studiilor colectate';

COMMENT ON COLUMN source.sourcetype_id IS 'Codul tipului sursei (refera atributul id din tabelul sourcetype)';

COMMENT ON TABLE source IS 'Tabel ce stocheaza sursele (organizatiile) care pot oferi studii pentru partea de colectare';


/******************** Add Table: source_contact_method ************************/

/* Build Table Structure */
CREATE TABLE source_contact_method
(
id SERIAL,
	name VARCHAR(150) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE source_contact_method ADD CONSTRAINT pksource_contact_method
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN source_contact_method.id IS 'Codul metodei de contact';

COMMENT ON COLUMN source_contact_method.name IS 'Denumirea metodei de contact (telefon, email etc.)';

COMMENT ON TABLE source_contact_method IS 'Tabel ce contine metodele prin care persoana de contact al unui posibil furnizor de studii poate fi contactata';


/******************** Add Table: source_contacts ************************/

/* Build Table Structure */
CREATE TABLE source_contacts
(
id SERIAL,
	person_id INTEGER NOT NULL,
	contact_date TIMESTAMP NOT NULL,
	synopsis TEXT NOT NULL,
	followup INTEGER NOT NULL,
	source_contact_method_id INTEGER NOT NULL,
	source_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE source_contacts ADD CONSTRAINT pksource_contacts
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN source_contacts.id IS 'Codul contactului';

COMMENT ON COLUMN source_contacts.person_id IS 'Codul persoanei (refera atributul id din tabelul person)';

COMMENT ON COLUMN source_contacts.contact_date IS 'Data la care persoana identificata prin atributul person_id a fost contactata';

COMMENT ON COLUMN source_contacts.source_contact_method_id IS 'Metoda prin care persoana a fost contactata (refera atributul id din tabelul source_contact_method)';

COMMENT ON COLUMN source_contacts.source_id IS 'Codul organizatiei careia ii aunt asociate informatiile de contact (refera atributul org_id din tabelul sources)';

COMMENT ON TABLE source_contacts IS 'Datele de contact ale unei surse ';


/******************** Add Table: sourcestudy ************************/

/* Build Table Structure */
CREATE TABLE sourcestudy
(
id SERIAL,
	name VARCHAR(150) NOT NULL,
	details TEXT NULL,
	org_id INTEGER NOT NULL,
	type INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE sourcestudy ADD CONSTRAINT pksourcestudy
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN sourcestudy.id IS 'Codul studiului pe care il poate furniza o organizatie';

COMMENT ON COLUMN sourcestudy.name IS 'Denumirea studiului';

COMMENT ON COLUMN sourcestudy.details IS 'Detalii asupra studiului care poate fi furnizat';

COMMENT ON COLUMN sourcestudy.org_id IS 'Codul organizatiei care poate pune la dispozitie studiul respectiv (refera atributul org_id din tabelul sources)';

COMMENT ON COLUMN sourcestudy.type IS 'Tipul sursei unui studiu';

COMMENT ON TABLE sourcestudy IS 'Tabel ce stocheaza informatii despre studiile pe care le poate furniza o organizatie ';


/******************** Add Table: sourcestudy_type ************************/

/* Build Table Structure */
CREATE TABLE sourcestudy_type
(
id SERIAL,
	name VARCHAR(150) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE sourcestudy_type ADD CONSTRAINT pksourcestudy_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN sourcestudy_type.id IS 'Codului tipului (starii) unui studiu care poate fi furnizat de catre o sursa';

COMMENT ON COLUMN sourcestudy_type.name IS 'Denumirea tipului (starii) in care se poate afla un studiu furnizat de catre o sursa';

COMMENT ON COLUMN sourcestudy_type.description IS 'Descrierea tipului (starii) unui studiu care poate fi furnizat de catre o sursa';

COMMENT ON TABLE sourcestudy_type IS 'Tabel ce stocheaza tipul (starea) studiilor pe care le poate furniza o organizatie';


/******************** Add Table: sourcestudy_type_history ************************/

/* Build Table Structure */
CREATE TABLE sourcestudy_type_history
(
id SERIAL,
	datestart TIMESTAMP NULL,
	dateend TIMESTAMP NULL,
	sourcestudy_type_id INTEGER NOT NULL,
	added_by INTEGER NOT NULL,
	sourcesstudy_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE sourcestudy_type_history ADD CONSTRAINT pksourcestudy_type_history
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN sourcestudy_type_history.id IS 'Codul liniei referitoare la istoricul unui studiu care poate fi furnizat de catre o sursa';

COMMENT ON COLUMN sourcestudy_type_history.datestart IS 'Data de inceput';

COMMENT ON COLUMN sourcestudy_type_history.dateend IS 'Data de final';

COMMENT ON COLUMN sourcestudy_type_history.sourcestudy_type_id IS 'Codul tipului (starii) studiului respectiv intre datele datestart si dateend';

COMMENT ON COLUMN sourcestudy_type_history.added_by IS 'Codul utilizatorului care a adaugat informatia referitoare la istoricul unui studiu';

COMMENT ON COLUMN sourcestudy_type_history.sourcesstudy_id IS 'Codul studiului care poate fi furnizat de catre o sursa';

COMMENT ON TABLE sourcestudy_type_history IS 'Tabel ce stocheaza istoricul tipului (starii) surselor studiilor';


/******************** Add Table: sourcetype ************************/

/* Build Table Structure */
CREATE TABLE sourcetype
(
id SERIAL,
	name VARCHAR(150) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE sourcetype ADD CONSTRAINT pksourcetype
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN sourcetype.id IS 'Codul sursei';

COMMENT ON COLUMN sourcetype.name IS 'Denumirea tipului sursei (a starii in care se afla sursa: in curs de prospectare, de recuperare a datelor, contactata etc.)';

COMMENT ON COLUMN sourcetype.description IS 'Descrierea tipului sursei';

COMMENT ON TABLE sourcetype IS 'Tabel ce contine tipurile de surse ';


/******************** Add Table: sourcetype_history ************************/

/* Build Table Structure */
CREATE TABLE sourcetype_history
(
id SERIAL,
	datestart TIMESTAMP NULL,
	dateend TIMESTAMP NULL,
	org_id INTEGER NOT NULL,
	sourcetype_id INTEGER NOT NULL,
	added_by INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE sourcetype_history ADD CONSTRAINT pksourcetype_history
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN sourcetype_history.id IS 'Codul liniei referitoare la istoricul surselor';

COMMENT ON COLUMN sourcetype_history.datestart IS 'Data de inceput a starii unei surse';

COMMENT ON COLUMN sourcetype_history.dateend IS 'Data de final a starii unei surse';

COMMENT ON COLUMN sourcetype_history.org_id IS 'Codul organizatiei (sursei)careia ii corespunde o stare intre cele doua date calendaristice';

COMMENT ON COLUMN sourcetype_history.sourcetype_id IS 'Codul tipului sursei dintre cele doua date';

COMMENT ON COLUMN sourcetype_history.added_by IS 'Codul utilizatorului care a adaugat informatia referitoare la istoricul unei surse';

COMMENT ON TABLE sourcetype_history IS 'Tabel ce stocheaza istoricul tipului surselor';


/******************** Add Table: study ************************/

/* Build Table Structure */
CREATE TABLE study
(
id SERIAL,
	datestart TIMESTAMP NULL,
	dateend TIMESTAMP NULL,
	insertion_status INTEGER NOT NULL,
	added_by INTEGER NOT NULL,
	added TIMESTAMP NOT NULL,
	can_digitize BOOL NOT NULL,
	can_use_anonymous BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study ADD CONSTRAINT pkstudy
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN study.id IS 'Codul studiului';

COMMENT ON COLUMN study.datestart IS 'Data de inceput a studiului';

COMMENT ON COLUMN study.dateend IS 'Data de final a studiului';

COMMENT ON COLUMN study.insertion_status IS 'Pasul din wizard-ul de introducere a metadatelor - din moment ce introducerea se face prin wizard, fiecare pas trebuie comis in baza de date; pana la finalizarea introducerii intregului studiu e nevoie sa stim ca ele au fost partial introduse.';

COMMENT ON TABLE study IS 'Tabel care stocheaza studiile desfasurate, ale caror informatii sunt prezente in baza de date ';


/******************** Add Table: study_acl ************************/

/* Build Table Structure */
CREATE TABLE study_acl
(
	study_id INTEGER NOT NULL,
	aro_id INTEGER NOT NULL,
	aro_type INTEGER NOT NULL,
	read BOOL NULL,
	"update" BOOL NULL,
	"delete" BOOL NULL,
	modacl BOOL NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_acl ADD CONSTRAINT pkstudy_acl
	PRIMARY KEY (study_id, aro_id, aro_type);

/* Add Comments */
COMMENT ON COLUMN study_acl.study_id IS 'Codul studiului ce va detine drepturi de acces pentru obiectul identificat prin atributul aro_id';

COMMENT ON COLUMN study_acl.aro_id IS 'Codul unui obiect care solicita drepturi ';

COMMENT ON COLUMN study_acl.aro_type IS 'Tipul unui obiect care solicita drepturi';

COMMENT ON COLUMN study_acl.read IS 'Atribut a carui valoare este true daca exista drept de citire; altfel, valoarea sa este false.';

COMMENT ON COLUMN study_acl."update" IS 'Atribut a carui valoare este true daca exista drept de actualizare; altfel, valoarea sa este false.';

COMMENT ON COLUMN study_acl."delete" IS 'Atribut a carui valoare este true daca exista drept de stergere; altfel, valoarea sa este false.';

COMMENT ON COLUMN study_acl.modacl IS 'Atribut boolean, a carui valoare este true daca drepturile pot fi modificate; altfel, valoarea atributului este false';

COMMENT ON TABLE study_acl IS 'Tabel ce contine listele pentru controlul accesului la nivel de studiu';


/******************** Add Table: study_descr ************************/

/* Build Table Structure */
CREATE TABLE study_descr
(
	lang_id CHAR(2) NOT NULL,
	title_type_id INTEGER NOT NULL,
	study_id INTEGER NOT NULL,
	abstract TEXT NULL,
	grant_details TEXT NULL,
	title VARCHAR(300) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_descr ADD CONSTRAINT pkstudy_descr
	PRIMARY KEY (study_id, lang_id);

/* Add Comments */
COMMENT ON COLUMN study_descr.lang_id IS 'Limba in care este furnizat titlul identificat prin atributul id';

COMMENT ON COLUMN study_descr.title_type_id IS 'Tipul titlului (refera atributul id din tabelul table_type)';

COMMENT ON COLUMN study_descr.study_id IS 'Codul studiului caruia ii corespunde titlul (refera atributul id din tabelul study)';

COMMENT ON TABLE study_descr IS 'Tabel ce stocheaza informatii despre titlurile studiilor din baza de date';


/******************** Add Table: study_documents ************************/

/* Build Table Structure */
CREATE TABLE study_documents
(
	study_id INTEGER NOT NULL,
	document_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_documents ADD CONSTRAINT pkstudy_documents
	PRIMARY KEY (study_id, document_id);

/* Add Comments */
COMMENT ON COLUMN study_documents.study_id IS 'Codul studiului pentru care este stocat documentul specificat prin atributul document_id (refera atributul id din tabelul study)';

COMMENT ON COLUMN study_documents.document_id IS 'Codul unui document care este asociat studiului identificat prin atributul study_id (refera atributul id din tabelul documents)';

COMMENT ON TABLE study_documents IS 'Tabel ce implementeaza relatia many-to-many intre studiu si documente';


/******************** Add Table: study_file ************************/

/* Build Table Structure */
CREATE TABLE study_file
(
	study_id INTEGER NOT NULL,
	file_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_file ADD CONSTRAINT pkstudy_file
	PRIMARY KEY (study_id, file_id);

/* Add Comments */
COMMENT ON COLUMN study_file.study_id IS 'Codul studiului pentru care este stocat documentul specificat prin atributul document_id (refera atributul id din tabelul study)';

COMMENT ON COLUMN study_file.file_id IS 'Codul unui document care este asociat studiului identificat prin atributul study_id (refera atributul id din tabelul documents)';

COMMENT ON TABLE study_file IS 'Tabel ce contine asocierile dintre studii si documente (implementeaza relatia many-to-many intre tabelele study si documents)';


/******************** Add Table: study_keyword ************************/

/* Build Table Structure */
CREATE TABLE study_keyword
(
	study_id INTEGER NOT NULL,
	keyword_id INTEGER NOT NULL,
	added TIMESTAMP NOT NULL,
	added_by INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_keyword ADD CONSTRAINT pkstudy_keyword
	PRIMARY KEY (study_id, keyword_id, added_by);

/* Add Comments */
COMMENT ON COLUMN study_keyword.study_id IS 'Codul studiului caruia ii este asociat cuvantul cheie referit prin atributul keyword_id';

COMMENT ON COLUMN study_keyword.keyword_id IS 'Cuvantul cheie asociat studiului identificat prin atributul study_id';

COMMENT ON COLUMN study_keyword.added IS 'Momentul de timp la care a fost adaugata o asociere intre un cuvant cheie si un studiu';

COMMENT ON COLUMN study_keyword.added_by IS 'Utilizatorul care a adaugat asocierea dintre un studiu si un cuvant cheie';

COMMENT ON TABLE study_keyword IS 'Tabel ce stocheaza asocierile dintre studii si cuvintele cheie (implementeaza relatia many-to-many intre tabelele study si keyword)';


/******************** Add Table: study_org ************************/

/* Build Table Structure */
CREATE TABLE study_org
(
	org_id INTEGER NOT NULL,
	study_id INTEGER NOT NULL,
	assoctype_id INTEGER NOT NULL,
	citation TEXT NULL,
	assoc_details TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_org ADD CONSTRAINT pkstudy_org
	PRIMARY KEY (org_id, study_id, assoctype_id);

/* Add Comments */
COMMENT ON COLUMN study_org.org_id IS 'Codul organizatiei care se afla in relatia specificata prin atributul assoctype_id cu studiul identificat prin study_id (refera atributul id al tabelului org)';

COMMENT ON COLUMN study_org.study_id IS 'Codul studiului care se afla in relatia specificata prin atributul assoctype_id cu organizatia identificata prin org_id (refera atributul id al tabelului study)';

COMMENT ON COLUMN study_org.assoctype_id IS 'Codul tipului de asociere existent intre studiul identificat prin study_id si organizatia referita prin org_id (refera atributul id din tabelul study_org_assoc)';

COMMENT ON COLUMN study_org.citation IS 'Modalitatea de citare in cadrul studiului identificat prin atributul study_id realizat de catre organizatia referita prin atributul org_id';

COMMENT ON TABLE study_org IS 'Tabel ce stocheaza toate organizatiile care au legatura cu studiul: finantator, realizator, arhivar, etc. ( implementeaza relatia many-to-many intre studiu si organizatie)';


/******************** Add Table: study_org_assoc ************************/

/* Build Table Structure */
CREATE TABLE study_org_assoc
(
id SERIAL,
	assoc_name VARCHAR(100) NOT NULL,
	assoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_org_assoc ADD CONSTRAINT pkstudy_org_assoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN study_org_assoc.id IS 'Codul unei asocieri care poate exista intre un studiu si o organizatie';

COMMENT ON COLUMN study_org_assoc.assoc_name IS 'Denumirea unei asocieri care poate exista intre un studiu si o organizatie (producator, finantator etc.)';

COMMENT ON COLUMN study_org_assoc.assoc_description IS 'Descrierea asocierii';

COMMENT ON TABLE study_org_assoc IS 'Tabel ce contine tipurile de asociere dintre studiu si organizatie (finantare, realizare etc.)';


/******************** Add Table: study_person ************************/

/* Build Table Structure */
CREATE TABLE study_person
(
	person_id INTEGER NOT NULL,
	study_id INTEGER NOT NULL,
	assoctype_id INTEGER NOT NULL,
	assoc_details TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_person ADD CONSTRAINT pkstudy_person
	PRIMARY KEY (person_id, study_id, assoctype_id);

/* Add Comments */
COMMENT ON COLUMN study_person.person_id IS 'Codul persoanei care este in relatia specificata prin atributul asoctype_id cu studiul referit prin atributul study_id (refera atributul id din tabelul person)';

COMMENT ON COLUMN study_person.study_id IS 'Codul studiului aflat in relatia identificata prin asoctype_id cu persoana referita prin atributul person_id (refera atributul id din tabelul study)';

COMMENT ON COLUMN study_person.assoctype_id IS 'Codul tipului de asociere existent intre persoana identificata prin atributul person_id si studiul referit prin atributul study_id';

COMMENT ON TABLE study_person IS 'Tabel ce contine asocierile dintre intre studiu si persoane: realizare, proiectare chestionar etc. (implementeaza relatia many-to-many intre tabelele person si study)';


/******************** Add Table: study_person_asoc ************************/

/* Build Table Structure */
CREATE TABLE study_person_asoc
(
id SERIAL,
	asoc_name VARCHAR(100) NOT NULL,
	asoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_person_asoc ADD CONSTRAINT pkstudy_person_asoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN study_person_asoc.id IS 'Codul unei asocieri care poate exista intre un studiu si o persoana';

COMMENT ON COLUMN study_person_asoc.asoc_name IS 'Numele unei asocieri care poate exista intre un studiu si o persoana';

COMMENT ON COLUMN study_person_asoc.asoc_description IS 'Descrierea asocierii';

COMMENT ON TABLE study_person_asoc IS 'Tabel ce contine tipurile de asocieri intre studiu si persoane';


/******************** Add Table: study_person_assoc ************************/

/* Build Table Structure */
CREATE TABLE study_person_assoc
(
id SERIAL,
	asoc_name VARCHAR(100) NOT NULL,
	asoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_person_assoc ADD CONSTRAINT pkstudy_person_assoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN study_person_assoc.id IS 'Codul unei asocieri care poate exista intre un studiu si o persoana';

COMMENT ON COLUMN study_person_assoc.asoc_name IS 'Numele unei asocieri care poate exista intre un studiu si o persoana';

COMMENT ON COLUMN study_person_assoc.asoc_description IS 'Descrierea asocierii';

COMMENT ON TABLE study_person_assoc IS 'Tabel ce contine tipurile de asocieri care pot exista intre studiu si persoane';


/******************** Add Table: study_topic ************************/

/* Build Table Structure */
CREATE TABLE study_topic
(
	study_id INTEGER NOT NULL,
	topic_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE study_topic ADD CONSTRAINT pkstudy_topic
	PRIMARY KEY (study_id, topic_id);

/* Add Comments */
COMMENT ON COLUMN study_topic.study_id IS 'Codul studiului caruia ii este asociat topic-ul referit prin atributul topic_id';

COMMENT ON COLUMN study_topic.topic_id IS 'Codul unui topic asociat studiului identificat prin atributul study_id';

COMMENT ON TABLE study_topic IS 'Tabel ce stocheaza asocierile dintre studii si topic-uri (implementeaza relatia many-to-many intre tabelele study si topic)';


/******************** Add Table: suffix ************************/

/* Build Table Structure */
CREATE TABLE suffix
(
id SERIAL,
	name VARCHAR(50) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE suffix ADD CONSTRAINT pksuffix
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN suffix.id IS 'Codul sufixului';

COMMENT ON COLUMN suffix.name IS 'Denumirea sufixului care poate fi utilizat pentru persoane (Jr, Sr, etc)';

COMMENT ON TABLE suffix IS 'Tabel ce contine sufixele care pot fi adaugate numelor persoanelor';


/******************** Add Table: time_meth_type ************************/

/* Build Table Structure */
CREATE TABLE time_meth_type
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE time_meth_type ADD CONSTRAINT pktime_meth_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN time_meth_type.id IS 'Codul tipului de metoda temporala';

COMMENT ON COLUMN time_meth_type.name IS 'Denumirea tipului de metoda temporala (longitudinal, serie temporala, cross section etc.)';

COMMENT ON TABLE time_meth_type IS 'Tabel ce contine tipurile de metode temporale';


/******************** Add Table: title_type ************************/

/* Build Table Structure */
CREATE TABLE title_type
(
id SERIAL,
	name VARCHAR(50) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE title_type ADD CONSTRAINT pktitle_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN title_type.id IS 'Codul tipului titlului';

COMMENT ON COLUMN title_type.name IS 'Denumirea tipului titlului (principal, paralel, alternativ)';

COMMENT ON TABLE title_type IS 'Tabel ce stocheaza tipurile titlurilor din baza de date';


/******************** Add Table: topic ************************/

/* Build Table Structure */
CREATE TABLE topic
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL,
	parent_topic_id INTEGER NULL,
	preferred_synonym_topic_id INTEGER NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE topic ADD CONSTRAINT pktopic
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN topic.id IS 'Codul unui topic ce poate fi asociat unui studiu sau unei instante ';

COMMENT ON COLUMN topic.name IS 'Numele unui topic ce poate fi asociat unui studiu sau unei instante';

COMMENT ON COLUMN topic.description IS 'Descrierea topic-ului ce poate fi asociat unui studiu sau unei instante';

COMMENT ON COLUMN topic.parent_topic_id IS 'Codul topic-ului din dreapta, in ierarhia arorescenta creata pentru a mentine legaturile cu topic-urile referite ';

COMMENT ON TABLE topic IS 'Tabel ce contine topic-urile ce pot fi asociate unei instante sau unui studiu';


/******************** Add Table: translated_topic ************************/

/* Build Table Structure */
CREATE TABLE translated_topic
(
	lang_id CHAR(2) NOT NULL,
	topic_id INTEGER NOT NULL,
	translation TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE translated_topic ADD CONSTRAINT pktranslated_topic
	PRIMARY KEY (lang_id, topic_id);

/* Add Comments */
COMMENT ON COLUMN translated_topic.lang_id IS 'Codul limbii in care este tradus topic-ul referit prin atributul topic_id (refera atributul id din tabelul language)';

COMMENT ON COLUMN translated_topic.topic_id IS 'Codul topic-ului pentru care exista o traducere in limba identificata prin atributul language_id (refera atributul id din tabelul topic)';

COMMENT ON COLUMN translated_topic.translation IS 'Traducerea topic-ului referit prin atributul topic_id in limba identificata prin atributul language_id';

COMMENT ON TABLE translated_topic IS 'Tabel ce contine traducerile anumitor topic-uri din baza de date (implementeaza relatia many-to-many intre tabelele topic si language)';


/******************** Add Table: unit_analysis ************************/

/* Build Table Structure */
CREATE TABLE unit_analysis
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE unit_analysis ADD CONSTRAINT pkunit_analysis
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN unit_analysis.id IS 'Codul unitatii de analiza';

COMMENT ON COLUMN unit_analysis.name IS 'Denumirea unitatii de analiza (individ, familie, organizatie, gospodarie etc.)';

COMMENT ON COLUMN unit_analysis.description IS 'Descrierea unitatii de analiza';

COMMENT ON TABLE unit_analysis IS 'Tabel care stocheaza tipurile de unitati de analiza (individ, gospodarie, etc) ale instantelor';


/******************** Add Table: user_auth_log ************************/

/* Build Table Structure */
CREATE TABLE user_auth_log
(
	user_id INTEGER NOT NULL,
	"timestamp" TIMESTAMP NOT NULL,
	"action" VARCHAR(30) NOT NULL,
	credential_provider TEXT NOT NULL,
	credential_identifier TEXT NOT NULL,
	error_message TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE user_auth_log ADD CONSTRAINT pkuser_auth_log
	PRIMARY KEY (user_id, "timestamp");

/* Add Comments */
COMMENT ON COLUMN user_auth_log.user_id IS 'Codul utilizatorului care a incercat sa se autentifice';

COMMENT ON COLUMN user_auth_log."timestamp" IS 'Timpul la care incercarea de autentificare a avut loc';

COMMENT ON COLUMN user_auth_log."action" IS 'Actiunea login/logout/session expire (in functie de tipul de autentificare)';

COMMENT ON COLUMN user_auth_log.credential_provider IS 'Tipul de furnizor de informatii de acces pentru autentificarea respectiva';

COMMENT ON COLUMN user_auth_log.credential_identifier IS 'Detalii despre autentificarea utilizatorului';

COMMENT ON COLUMN user_auth_log.error_message IS 'Mesajul de eroare aparut ';

COMMENT ON TABLE user_auth_log IS 'Tabel ce stocheaza log-ul corespunzator procesului de autentificare a utilizatorilor';


/******************** Add Table: user_message ************************/

/* Build Table Structure */
CREATE TABLE user_message
(
id SERIAL,
	message TEXT NOT NULL,
	from_user_id INTEGER NOT NULL,
	to_user_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE user_message ADD CONSTRAINT pkuser_message
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN user_message.id IS 'Codul mesajului trimis de catre utilizatorul referit prin atributul user_id';

COMMENT ON COLUMN user_message.message IS 'Textul mesajului trimis de catre utilizatorul referit prin atributul user_id';

COMMENT ON COLUMN user_message.from_user_id IS 'Codul utilizatorului care transmite mesajul (refera atributul id din tabelul users)';

COMMENT ON COLUMN user_message.to_user_id IS 'Codul utilizatorului caruia ii este transmis mesajul (refera atributul id din tabelul users)';

COMMENT ON TABLE user_message IS 'Tabel care stocheaza mesajele trimise catre utilizatori';


/******************** Add Table: user_profile ************************/

/* Build Table Structure */
CREATE TABLE user_profile
(
	user_id INTEGER NOT NULL,
	f_name VARCHAR(100) NOT NULL,
	l_name VARCHAR(100) NOT NULL,
	email VARCHAR(200) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE user_profile ADD CONSTRAINT pkuser_profile
	PRIMARY KEY (user_id);

/* Add Comments */
COMMENT ON COLUMN user_profile.user_id IS 'Codul utilizatorului';

COMMENT ON COLUMN user_profile.f_name IS 'Prenumele utilizatorului';

COMMENT ON COLUMN user_profile.l_name IS 'Numele utilizatorului';

COMMENT ON COLUMN user_profile.email IS 'Adresa de email a utilizatorului';

COMMENT ON TABLE user_profile IS 'Tabel ce stocheaza profilurile utilizatorilor aplicatiei';


/******************** Add Table: user_role ************************/

/* Build Table Structure */
CREATE TABLE user_role
(
	user_id INTEGER NOT NULL,
	role_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE user_role ADD CONSTRAINT pkuser_role
	PRIMARY KEY (role_id, user_id);

/* Add Comments */
COMMENT ON COLUMN user_role.user_id IS 'Codul utilizatorului asociat role-ului referit prin atributul role_id';

COMMENT ON COLUMN user_role.role_id IS 'Codul role-ului corespunzator utilizatorului referit prin user_id';

COMMENT ON TABLE user_role IS 'Tabel ce stocheaza asocierile dintre utilizatori si role-uri';


/******************** Add Table: user_setting ************************/

/* Build Table Structure */
CREATE TABLE user_setting
(
id SERIAL,
	name TEXT NOT NULL,
	setting_group INTEGER NOT NULL,
	description TEXT NULL,
	predefined_values TEXT NULL,
	default_value TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE user_setting ADD CONSTRAINT pkuser_setting
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN user_setting.id IS 'Codul unei setari asociate utilizatorilor';

COMMENT ON COLUMN user_setting.name IS 'Denumirea setarii';

COMMENT ON COLUMN user_setting.setting_group IS 'Grupul de setari din care aceasta face parte (refera atributul id al tabelului user_settings_group)';

COMMENT ON COLUMN user_setting.description IS 'Descrierea setarii';

COMMENT ON COLUMN user_setting.predefined_values IS 'Valorile predefinite ale setarii';

COMMENT ON COLUMN user_setting.default_value IS 'Valoarea implicita a setarii';

COMMENT ON TABLE user_setting IS 'Tabel care stocheaza setarile pentru utilizatori';


/******************** Add Table: user_setting_group ************************/

/* Build Table Structure */
CREATE TABLE user_setting_group
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE user_setting_group ADD CONSTRAINT pkuser_setting_group
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN user_setting_group.id IS 'Codul grupului de setari';

COMMENT ON COLUMN user_setting_group.name IS 'Denumirea grupului de setari';

COMMENT ON COLUMN user_setting_group.description IS 'Descrierea grupului de setari';

COMMENT ON TABLE user_setting_group IS 'Tabel care stocheaza grupurile de setari pentru utilizatori';


/******************** Add Table: user_setting_value ************************/

/* Build Table Structure */
CREATE TABLE user_setting_value
(
	user_setting_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	value TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE user_setting_value ADD CONSTRAINT pkuser_setting_value
	PRIMARY KEY (user_setting_id, user_id);

/* Add Comments */
COMMENT ON COLUMN user_setting_value.user_setting_id IS 'Codul setarii corespunzatoare unui utilizator (refera atributul id al tabelului user_settings)';

COMMENT ON COLUMN user_setting_value.user_id IS 'Codul utilizatorului caruia ii apartine setarea (refera atributul id al tabelului users)';

COMMENT ON COLUMN user_setting_value.value IS 'Valoarea setarii respective';

COMMENT ON TABLE user_setting_value IS 'Tabel care stocheaza valorile setarilor utilizatorului';


/******************** Add Table: value ************************/

/* Build Table Structure */
CREATE TABLE value
(
	item_id BIGINT NOT NULL,
	value INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE value ADD CONSTRAINT pkvalue
	PRIMARY KEY (item_id);

/* Add Comments */
COMMENT ON COLUMN value.item_id IS 'Codul elementului unei variabile de selectie';

COMMENT ON TABLE value IS 'Tabel ce stocheaza valorile posibile ale item-urilor variabilelor de selectie';


/******************** Add Table: vargroup ************************/

/* Build Table Structure */
CREATE TABLE vargroup
(
id BIGSERIAL,
	name TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE vargroup ADD CONSTRAINT pkvargroup
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN vargroup.id IS 'Identificatorul';

COMMENT ON COLUMN vargroup.name IS 'Denumirea grupului';

COMMENT ON TABLE vargroup IS 'Tabel pentru definirea gruparilor de variabile (pentru o organizare mai buna a lor)';


/******************** Add Table: variable ************************/

/* Build Table Structure */
CREATE TABLE variable
(
	instance_id INTEGER NOT NULL,
id BIGSERIAL,
	label TEXT NOT NULL,
	type SMALLINT NOT NULL,
	order_in_instance INTEGER NOT NULL,
	operator_instructions TEXT NULL,
	file_id INTEGER NULL,
	type_edited_text BOOL NOT NULL,
	type_edited_number BOOL NOT NULL,
	type_selection BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE variable ADD CONSTRAINT pkvariable
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN variable.instance_id IS 'Codul instantei in care este definita variabila';

COMMENT ON COLUMN variable.id IS 'Codul variabilei in cadrul instantei';

COMMENT ON COLUMN variable.label IS 'Reprezentarea textuala a variabilei (numele)';

COMMENT ON COLUMN variable.type IS 'Tipul de variabila (constanta a unei enumeratii)';

COMMENT ON COLUMN variable.order_in_instance IS 'Intregul ordinal reprezentand pozitia variabilei in secventa de variabile care definesc instanta';

COMMENT ON COLUMN variable.operator_instructions IS 'Text care informeaza operatorul ce chestioneaza asupra unor actiuni pe care trebuie sa le faca atunci cand ajunge la variabila aceasta';

COMMENT ON COLUMN variable.file_id IS 'Fisierul din care provine variabila';

COMMENT ON TABLE variable IS 'Tabel care stocheaza variabilele din cadrul instantelor';

/* Add Indexes */
CREATE UNIQUE INDEX "Variables_QuestionnaireId_Order_Idx" ON variable (instance_id, order_in_instance);


/******************** Add Table: variable_vargroup ************************/

/* Build Table Structure */
CREATE TABLE variable_vargroup
(
	variable_id BIGINT NOT NULL,
	vargroup_id BIGINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE variable_vargroup ADD CONSTRAINT pkvariable_vargroup
	PRIMARY KEY (vargroup_id, variable_id);

/* Add Comments */
COMMENT ON COLUMN variable_vargroup.variable_id IS 'Codul variabilei in cadrul instantei instance_id';

COMMENT ON COLUMN variable_vargroup.vargroup_id IS 'Codul grupului caruia ii apartine variabila variable_id din instanta instance_id';

COMMENT ON TABLE variable_vargroup IS 'Tabel ce asociaza variabile unor grupuri in cadrul instantelor';





/************ Add Foreign Keys ***************/

/* Add Foreign Key: fk_acl_entry_acl_object_identity */
ALTER TABLE acl_entry ADD CONSTRAINT fk_acl_entry_acl_object_identity
	FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_acl_entry_acl_sid */
ALTER TABLE acl_entry ADD CONSTRAINT fk_acl_entry_acl_sid
	FOREIGN KEY (sid) REFERENCES acl_sid (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_acl_object_identity_acl_class */
ALTER TABLE acl_object_identity ADD CONSTRAINT fk_acl_object_identity_acl_class
	FOREIGN KEY (object_id_class) REFERENCES acl_class (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_acl_object_identity_acl_object_identity */
ALTER TABLE acl_object_identity ADD CONSTRAINT fk_acl_object_identity_acl_object_identity
	FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_acl_object_identity_acl_sid */
ALTER TABLE acl_object_identity ADD CONSTRAINT fk_acl_object_identity_acl_sid
	FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_address_city */
ALTER TABLE address ADD CONSTRAINT fk_address_city
	FOREIGN KEY (city_id) REFERENCES city (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: audit_log_action_fk_audited_table */
ALTER TABLE audit_log_action ADD CONSTRAINT audit_log_action_fk_audited_table
	FOREIGN KEY (audited_table) REFERENCES audit_log_table (id)
	ON UPDATE CASCADE ON DELETE CASCADE;

/* Add Foreign Key: audit_log_action_fk_changeset */
ALTER TABLE audit_log_action ADD CONSTRAINT audit_log_action_fk_changeset
	FOREIGN KEY (changeset) REFERENCES audit_log_changeset (id)
	ON UPDATE CASCADE ON DELETE CASCADE;

/* Add Foreign Key: audit_log_change_fk_action */
ALTER TABLE audit_log_change ADD CONSTRAINT audit_log_change_fk_action
	FOREIGN KEY ("action") REFERENCES audit_log_action (id)
	ON UPDATE CASCADE ON DELETE CASCADE;

/* Add Foreign Key: audit_log_change_fk_field */
ALTER TABLE audit_log_change ADD CONSTRAINT audit_log_change_fk_field
	FOREIGN KEY (field) REFERENCES audit_log_field (id)
	ON UPDATE CASCADE ON DELETE CASCADE;

/* Add Foreign Key: fk_audit_log_changeset_rodauser */
ALTER TABLE audit_log_changeset ADD CONSTRAINT fk_audit_log_changeset_rodauser
	FOREIGN KEY (rodauser) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: audit_log_field_fk_audited_table */
ALTER TABLE audit_log_field ADD CONSTRAINT audit_log_field_fk_audited_table
	FOREIGN KEY (audited_table) REFERENCES audit_log_table (id)
	ON UPDATE CASCADE ON DELETE CASCADE;

/* Add Foreign Key: fk_auth_data_users */
ALTER TABLE auth_data ADD CONSTRAINT fk_auth_data_users
	FOREIGN KEY (user_id) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_catalog_users */
ALTER TABLE catalog ADD CONSTRAINT fk_catalog_users
	FOREIGN KEY (owner) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_catalog_acl_catalog */
ALTER TABLE catalog_acl ADD CONSTRAINT fk_catalog_acl_catalog
	FOREIGN KEY (catalog_id) REFERENCES catalog (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_catalog_study_catalog */
ALTER TABLE catalog_study ADD CONSTRAINT fk_catalog_study_catalog
	FOREIGN KEY (catalog_id) REFERENCES catalog (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_catalog_study_study */
ALTER TABLE catalog_study ADD CONSTRAINT fk_catalog_study_study
	FOREIGN KEY (study_id) REFERENCES study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_city_country */
ALTER TABLE city ADD CONSTRAINT fk_city_country
	FOREIGN KEY (country_id) REFERENCES country (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_files_cms_folders */
ALTER TABLE cms_file ADD CONSTRAINT fk_cms_files_cms_folders
	FOREIGN KEY (cms_folder_id) REFERENCES cms_folder (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_file_property_name_value_cms_file */
ALTER TABLE cms_file_property_name_value ADD CONSTRAINT fk_cms_file_property_name_value_cms_file
	FOREIGN KEY (cms_file_id) REFERENCES cms_file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_file_property_name_value_property_name */
ALTER TABLE cms_file_property_name_value ADD CONSTRAINT fk_cms_file_property_name_value_property_name
	FOREIGN KEY (property_name_id) REFERENCES property_name (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_file_property_name_value_property_value */
ALTER TABLE cms_file_property_name_value ADD CONSTRAINT fk_cms_file_property_name_value_property_value
	FOREIGN KEY (property_value_id) REFERENCES property_value (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_layout_cms_layout_group */
ALTER TABLE cms_layout ADD CONSTRAINT fk_cms_layout_cms_layout_group
	FOREIGN KEY (cms_layout_group_id) REFERENCES cms_layout_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_page_cms_layout */
ALTER TABLE cms_page ADD CONSTRAINT fk_cms_page_cms_layout
	FOREIGN KEY (cms_layout_id) REFERENCES cms_layout (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_page_cms_page_type */
ALTER TABLE cms_page ADD CONSTRAINT fk_cms_page_cms_page_type
	FOREIGN KEY (cms_page_type_id) REFERENCES cms_page_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_page_users */
ALTER TABLE cms_page ADD CONSTRAINT fk_cms_page_users
	FOREIGN KEY (owner_id) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_page_content_cms_page */
ALTER TABLE cms_page_content ADD CONSTRAINT fk_cms_page_content_cms_page
	FOREIGN KEY (cms_page_id) REFERENCES cms_page (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_snippet_cms_snippet_group */
ALTER TABLE cms_snippet ADD CONSTRAINT fk_cms_snippet_cms_snippet_group
	FOREIGN KEY (cms_snippet_group_id) REFERENCES cms_snippet_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_Concept_Variables_Concepts */
ALTER TABLE concept_variable ADD CONSTRAINT "fk_Concept_Variables_Concepts"
	FOREIGN KEY (concept_id) REFERENCES concept (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_concept_variable_variable */
ALTER TABLE concept_variable ADD CONSTRAINT fk_concept_variable_variable
	FOREIGN KEY (variable_id) REFERENCES variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_documents_acl_documents */
ALTER TABLE file_acl ADD CONSTRAINT fk_documents_acl_documents
	FOREIGN KEY (document_id) REFERENCES file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_file_property_name_value_file */
ALTER TABLE file_property_name_value ADD CONSTRAINT fk_file_property_name_value_file
	FOREIGN KEY (file_id) REFERENCES file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_file_property_name_value_property_name */
ALTER TABLE file_property_name_value ADD CONSTRAINT fk_file_property_name_value_property_name
	FOREIGN KEY (property_name_id) REFERENCES property_name (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_file_property_name_value_property_value */
ALTER TABLE file_property_name_value ADD CONSTRAINT fk_file_property_name_value_property_value
	FOREIGN KEY (property_value_id) REFERENCES property_value (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_instance */
ALTER TABLE form ADD CONSTRAINT fk_form_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_person */
ALTER TABLE form ADD CONSTRAINT fk_form_person
	FOREIGN KEY (operator_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_custom_number_var_custom_variable */
ALTER TABLE form_edited_number_var ADD CONSTRAINT fk_form_custom_number_var_custom_variable
	FOREIGN KEY (variable_id) REFERENCES variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_edited_number_var_form */
ALTER TABLE form_edited_number_var ADD CONSTRAINT fk_form_edited_number_var_form
	FOREIGN KEY (form_id) REFERENCES form (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_edited_text_var_form */
ALTER TABLE form_edited_text_var ADD CONSTRAINT fk_form_edited_text_var_form
	FOREIGN KEY (form_id) REFERENCES form (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_edited_text_var_variable */
ALTER TABLE form_edited_text_var ADD CONSTRAINT fk_form_edited_text_var_variable
	FOREIGN KEY (variable_id) REFERENCES variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_selection_var_form */
ALTER TABLE form_selection_var ADD CONSTRAINT fk_form_selection_var_form
	FOREIGN KEY (form_id) REFERENCES form (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_selection_var_selection_variable_item */
ALTER TABLE form_selection_var ADD CONSTRAINT fk_form_selection_var_selection_variable_item
	FOREIGN KEY (variable_id, item_id) REFERENCES selection_variable_item (variable_id, item_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_study */
ALTER TABLE instance ADD CONSTRAINT fk_instance_study
	FOREIGN KEY (study_id) REFERENCES study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_time_meth_type */
ALTER TABLE instance ADD CONSTRAINT fk_instance_time_meth_type
	FOREIGN KEY (time_meth_id) REFERENCES time_meth_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_unit_analysis */
ALTER TABLE instance ADD CONSTRAINT fk_instance_unit_analysis
	FOREIGN KEY (unit_analysis_id) REFERENCES unit_analysis (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_user */
ALTER TABLE instance ADD CONSTRAINT fk_instance_user
	FOREIGN KEY (added_by) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_acl_instance */
ALTER TABLE instance_acl ADD CONSTRAINT fk_instance_acl_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_descr_instance */
ALTER TABLE instance_descr ADD CONSTRAINT fk_instance_descr_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_descr_lang */
ALTER TABLE instance_descr ADD CONSTRAINT fk_instance_descr_lang
	FOREIGN KEY (lang_id) REFERENCES lang (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_documents_documents */
ALTER TABLE instance_documents ADD CONSTRAINT fk_instance_documents_documents
	FOREIGN KEY (document_id) REFERENCES file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_documents_instance */
ALTER TABLE instance_documents ADD CONSTRAINT fk_instance_documents_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_keyword_instance */
ALTER TABLE instance_keyword ADD CONSTRAINT fk_instance_keyword_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_keyword_keyword */
ALTER TABLE instance_keyword ADD CONSTRAINT fk_instance_keyword_keyword
	FOREIGN KEY (keyword_id) REFERENCES keyword (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_keyword_user */
ALTER TABLE instance_keyword ADD CONSTRAINT fk_instance_keyword_user
	FOREIGN KEY (added_by) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_org_instance */
ALTER TABLE instance_org ADD CONSTRAINT fk_instance_org_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_org_instance_org_assoc */
ALTER TABLE instance_org ADD CONSTRAINT fk_instance_org_instance_org_assoc
	FOREIGN KEY (assoc_type_id) REFERENCES instance_org_assoc (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_org_org */
ALTER TABLE instance_org ADD CONSTRAINT fk_instance_org_org
	FOREIGN KEY (org_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_person_instance */
ALTER TABLE instance_person ADD CONSTRAINT fk_instance_person_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_person_instance_person_assoc */
ALTER TABLE instance_person ADD CONSTRAINT fk_instance_person_instance_person_assoc
	FOREIGN KEY (assoc_type_id) REFERENCES instance_person_assoc (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_person_person */
ALTER TABLE instance_person ADD CONSTRAINT fk_instance_person_person
	FOREIGN KEY (person_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_sampling_procedure_instance */
ALTER TABLE instance_sampling_procedure ADD CONSTRAINT fk_instance_sampling_procedure_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_sampling_procedure_sampling_procedure */
ALTER TABLE instance_sampling_procedure ADD CONSTRAINT fk_instance_sampling_procedure_sampling_procedure
	FOREIGN KEY (sampling_procedure_id) REFERENCES sampling_procedure (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_topic_instance */
ALTER TABLE instance_topic ADD CONSTRAINT fk_instance_topic_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_topic_topic */
ALTER TABLE instance_topic ADD CONSTRAINT fk_instance_topic_topic
	FOREIGN KEY (topic_id) REFERENCES topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_meth_coll_type_collection_model_type */
ALTER TABLE meth_coll_type ADD CONSTRAINT fk_meth_coll_type_collection_model_type
	FOREIGN KEY (collection_model_id) REFERENCES collection_model_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_meth_coll_type_instance */
ALTER TABLE meth_coll_type ADD CONSTRAINT fk_meth_coll_type_instance
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_news_users */
ALTER TABLE news ADD CONSTRAINT fk_news_users
	FOREIGN KEY (added_by) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_org_prefix */
ALTER TABLE org ADD CONSTRAINT fk_org_org_prefix
	FOREIGN KEY (org_prefix_id) REFERENCES org_prefix (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_org_sufix */
ALTER TABLE org ADD CONSTRAINT fk_org_org_sufix
	FOREIGN KEY (org_sufix_id) REFERENCES org_sufix (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_address_address */
ALTER TABLE org_address ADD CONSTRAINT fk_org_address_address
	FOREIGN KEY (address_id) REFERENCES address (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_address_org */
ALTER TABLE org_address ADD CONSTRAINT fk_org_address_org
	FOREIGN KEY (org_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_email_email */
ALTER TABLE org_email ADD CONSTRAINT fk_org_email_email
	FOREIGN KEY (email_id) REFERENCES email (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_email_org */
ALTER TABLE org_email ADD CONSTRAINT fk_org_email_org
	FOREIGN KEY (org_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_internet_internet */
ALTER TABLE org_internet ADD CONSTRAINT fk_org_internet_internet
	FOREIGN KEY (internet_id) REFERENCES internet (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_internet_org */
ALTER TABLE org_internet ADD CONSTRAINT fk_org_internet_org
	FOREIGN KEY (org_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_phone_org */
ALTER TABLE org_phone ADD CONSTRAINT fk_org_phone_org
	FOREIGN KEY (org_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_phone_phone */
ALTER TABLE org_phone ADD CONSTRAINT fk_org_phone_phone
	FOREIGN KEY (phone_id) REFERENCES phone (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_2_relations_org */
ALTER TABLE org_relations ADD CONSTRAINT fk_org_2_relations_org
	FOREIGN KEY (org_2_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_relations_org */
ALTER TABLE org_relations ADD CONSTRAINT fk_org_relations_org
	FOREIGN KEY (org_1_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_relations_org_relation_type */
ALTER TABLE org_relations ADD CONSTRAINT fk_org_relations_org_relation_type
	FOREIGN KEY (org_relation_type_id) REFERENCES org_relation_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_other_statistic_variable */
ALTER TABLE other_statistic ADD CONSTRAINT fk_other_statistic_variable
	FOREIGN KEY (variable_id) REFERENCES variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_prefix */
ALTER TABLE person ADD CONSTRAINT fk_person_prefix
	FOREIGN KEY (prefix_id) REFERENCES prefix (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_suffix */
ALTER TABLE person ADD CONSTRAINT fk_person_suffix
	FOREIGN KEY (suffix_id) REFERENCES suffix (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_address_address */
ALTER TABLE person_address ADD CONSTRAINT fk_person_address_address
	FOREIGN KEY (address_id) REFERENCES address (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_address_person */
ALTER TABLE person_address ADD CONSTRAINT fk_person_address_person
	FOREIGN KEY (person_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_email_email */
ALTER TABLE person_email ADD CONSTRAINT fk_person_email_email
	FOREIGN KEY (email_id) REFERENCES email (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_email_person */
ALTER TABLE person_email ADD CONSTRAINT fk_person_email_person
	FOREIGN KEY (person_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_internet_internet */
ALTER TABLE person_internet ADD CONSTRAINT fk_person_internet_internet
	FOREIGN KEY (internet_id) REFERENCES internet (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_internet_person */
ALTER TABLE person_internet ADD CONSTRAINT fk_person_internet_person
	FOREIGN KEY (person_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_links_person */
ALTER TABLE person_links ADD CONSTRAINT fk_person_links_person
	FOREIGN KEY (person_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_links_users */
ALTER TABLE person_links ADD CONSTRAINT fk_person_links_users
	FOREIGN KEY (user_id) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_links_users2 */
ALTER TABLE person_links ADD CONSTRAINT fk_person_links_users2
	FOREIGN KEY (status_by) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_org_org */
ALTER TABLE person_org ADD CONSTRAINT fk_person_org_org
	FOREIGN KEY (org_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_org_person */
ALTER TABLE person_org ADD CONSTRAINT fk_person_org_person
	FOREIGN KEY (person_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_org_person_role */
ALTER TABLE person_org ADD CONSTRAINT fk_person_org_person_role
	FOREIGN KEY (role_id) REFERENCES person_role (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_phone_person */
ALTER TABLE person_phone ADD CONSTRAINT fk_person_phone_person
	FOREIGN KEY (person_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_phone_phone */
ALTER TABLE person_phone ADD CONSTRAINT fk_person_phone_phone
	FOREIGN KEY (phone_id) REFERENCES phone (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_region_country */
ALTER TABLE region ADD CONSTRAINT fk_region_country
	FOREIGN KEY (country_id) REFERENCES country (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_region_region_type */
ALTER TABLE region ADD CONSTRAINT fk_region_region_type
	FOREIGN KEY (regiontype_id) REFERENCES regiontype (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_region_city_city */
ALTER TABLE region_city ADD CONSTRAINT fk_region_city_city
	FOREIGN KEY (city_id) REFERENCES city (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_region_city_region */
ALTER TABLE region_city ADD CONSTRAINT fk_region_city_region
	FOREIGN KEY (region_id) REFERENCES region (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_scale_item */
ALTER TABLE scale ADD CONSTRAINT fk_scale_item
	FOREIGN KEY (item_id) REFERENCES item (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_scale_max_value */
ALTER TABLE scale ADD CONSTRAINT fk_scale_max_value
	FOREIGN KEY ("maxValue_id") REFERENCES value (item_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_scale_min_value */
ALTER TABLE scale ADD CONSTRAINT fk_scale_min_value
	FOREIGN KEY ("minValue_id") REFERENCES value (item_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_selection_variable_variable */
ALTER TABLE selection_variable ADD CONSTRAINT fk_selection_variable_variable
	FOREIGN KEY (variable_id) REFERENCES variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_selection_variable_item_documents */
ALTER TABLE selection_variable_item ADD CONSTRAINT fk_selection_variable_item_documents
	FOREIGN KEY (response_card_file_id) REFERENCES file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_selection_variable_item_item */
ALTER TABLE selection_variable_item ADD CONSTRAINT fk_selection_variable_item_item
	FOREIGN KEY (item_id) REFERENCES item (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_selection_variable_item_selection_variable */
ALTER TABLE selection_variable_item ADD CONSTRAINT fk_selection_variable_item_selection_variable
	FOREIGN KEY (variable_id) REFERENCES selection_variable (variable_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_setting_setting_group */
ALTER TABLE setting ADD CONSTRAINT fk_setting_setting_group
	FOREIGN KEY (setting_group) REFERENCES setting_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_setting_group_setting_group */
ALTER TABLE setting_group ADD CONSTRAINT fk_setting_group_setting_group
	FOREIGN KEY (parent) REFERENCES setting_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_setting_values_setting */
ALTER TABLE setting_value ADD CONSTRAINT fk_setting_values_setting
	FOREIGN KEY (setting_id) REFERENCES setting (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_skip_next_variable */
ALTER TABLE skip ADD CONSTRAINT fk_skip_next_variable
	FOREIGN KEY (next_variable_id) REFERENCES variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_skip_variable */
ALTER TABLE skip ADD CONSTRAINT fk_skip_variable
	FOREIGN KEY (variable_id) REFERENCES variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_sources_org */
ALTER TABLE source ADD CONSTRAINT fk_sources_org
	FOREIGN KEY (org_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_sources_sourcetype */
ALTER TABLE source ADD CONSTRAINT fk_sources_sourcetype
	FOREIGN KEY (sourcetype_id) REFERENCES sourcetype (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_source_contacts_person */
ALTER TABLE source_contacts ADD CONSTRAINT fk_source_contacts_person
	FOREIGN KEY (person_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_source_contacts_source_contact_method */
ALTER TABLE source_contacts ADD CONSTRAINT fk_source_contacts_source_contact_method
	FOREIGN KEY (source_contact_method_id) REFERENCES source_contact_method (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_source_contacts_sources */
ALTER TABLE source_contacts ADD CONSTRAINT fk_source_contacts_sources
	FOREIGN KEY (source_id) REFERENCES source (org_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_sourcestudy_sources */
ALTER TABLE sourcestudy ADD CONSTRAINT fk_sourcestudy_sources
	FOREIGN KEY (org_id) REFERENCES source (org_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_sourcestudy_sourcestudy_type */
ALTER TABLE sourcestudy ADD CONSTRAINT fk_sourcestudy_sourcestudy_type
	FOREIGN KEY (type) REFERENCES sourcestudy_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_sourcestudy_type_history_sourcestudy */
ALTER TABLE sourcestudy_type_history ADD CONSTRAINT fk_sourcestudy_type_history_sourcestudy
	FOREIGN KEY (sourcesstudy_id) REFERENCES sourcestudy (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_sourcestudy_type_history_sourcestudy_type */
ALTER TABLE sourcestudy_type_history ADD CONSTRAINT fk_sourcestudy_type_history_sourcestudy_type
	FOREIGN KEY (sourcestudy_type_id) REFERENCES sourcestudy_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_sourcestudy_type_history_user */
ALTER TABLE sourcestudy_type_history ADD CONSTRAINT fk_sourcestudy_type_history_user
	FOREIGN KEY (added_by) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_source_type_history_sources */
ALTER TABLE sourcetype_history ADD CONSTRAINT fk_source_type_history_sources
	FOREIGN KEY (org_id) REFERENCES source (org_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_source_type_history_sourcetype */
ALTER TABLE sourcetype_history ADD CONSTRAINT fk_source_type_history_sourcetype
	FOREIGN KEY (sourcetype_id) REFERENCES sourcetype (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_sourcetype_history_user */
ALTER TABLE sourcetype_history ADD CONSTRAINT fk_sourcetype_history_user
	FOREIGN KEY (added_by) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_user */
ALTER TABLE study ADD CONSTRAINT fk_study_user
	FOREIGN KEY (added_by) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_acl_study */
ALTER TABLE study_acl ADD CONSTRAINT fk_study_acl_study
	FOREIGN KEY (study_id) REFERENCES study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_descr_lang */
ALTER TABLE study_descr ADD CONSTRAINT fk_study_descr_lang
	FOREIGN KEY (lang_id) REFERENCES lang (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_title_study */
ALTER TABLE study_descr ADD CONSTRAINT fk_title_study
	FOREIGN KEY (study_id) REFERENCES study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_title_title_type */
ALTER TABLE study_descr ADD CONSTRAINT fk_title_title_type
	FOREIGN KEY (title_type_id) REFERENCES title_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_documents_documents */
ALTER TABLE study_file ADD CONSTRAINT fk_study_documents_documents
	FOREIGN KEY (file_id) REFERENCES file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_documents_study */
ALTER TABLE study_file ADD CONSTRAINT fk_study_documents_study
	FOREIGN KEY (study_id) REFERENCES study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_keyword_keyword */
ALTER TABLE study_keyword ADD CONSTRAINT fk_study_keyword_keyword
	FOREIGN KEY (keyword_id) REFERENCES keyword (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_keyword_study */
ALTER TABLE study_keyword ADD CONSTRAINT fk_study_keyword_study
	FOREIGN KEY (study_id) REFERENCES study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_keyword_user */
ALTER TABLE study_keyword ADD CONSTRAINT fk_study_keyword_user
	FOREIGN KEY (added_by) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_studiu_org_org */
ALTER TABLE study_org ADD CONSTRAINT fk_studiu_org_org
	FOREIGN KEY (org_id) REFERENCES org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_studiu_org_studiu_org_asoc */
ALTER TABLE study_org ADD CONSTRAINT fk_studiu_org_studiu_org_asoc
	FOREIGN KEY (assoctype_id) REFERENCES study_org_assoc (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_studiu_org_study */
ALTER TABLE study_org ADD CONSTRAINT fk_studiu_org_study
	FOREIGN KEY (study_id) REFERENCES study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_person_person */
ALTER TABLE study_person ADD CONSTRAINT fk_study_person_person
	FOREIGN KEY (person_id) REFERENCES person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_person_studiu_person_asoc */
ALTER TABLE study_person ADD CONSTRAINT fk_study_person_studiu_person_asoc
	FOREIGN KEY (assoctype_id) REFERENCES study_person_assoc (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_person_study */
ALTER TABLE study_person ADD CONSTRAINT fk_study_person_study
	FOREIGN KEY (study_id) REFERENCES study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_topic_study */
ALTER TABLE study_topic ADD CONSTRAINT fk_study_topic_study
	FOREIGN KEY (study_id) REFERENCES study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_topic_topic */
ALTER TABLE study_topic ADD CONSTRAINT fk_study_topic_topic
	FOREIGN KEY (topic_id) REFERENCES topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_topic_topic_parent */
ALTER TABLE topic ADD CONSTRAINT fk_topic_topic_parent
	FOREIGN KEY (parent_topic_id) REFERENCES topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_topic_topic_preferred */
ALTER TABLE topic ADD CONSTRAINT fk_topic_topic_preferred
	FOREIGN KEY (preferred_synonym_topic_id) REFERENCES topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_translated_topic_lang */
ALTER TABLE translated_topic ADD CONSTRAINT fk_translated_topic_lang
	FOREIGN KEY (lang_id) REFERENCES lang (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_translated_topic_topic */
ALTER TABLE translated_topic ADD CONSTRAINT fk_translated_topic_topic
	FOREIGN KEY (topic_id) REFERENCES topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_auth_log_users */
ALTER TABLE user_auth_log ADD CONSTRAINT fk_user_auth_log_users
	FOREIGN KEY (user_id) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_message_user */
ALTER TABLE user_message ADD CONSTRAINT fk_user_message_user
	FOREIGN KEY (to_user_id) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_messages_users */
ALTER TABLE user_message ADD CONSTRAINT fk_user_messages_users
	FOREIGN KEY (from_user_id) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_profile_users */
ALTER TABLE user_profile ADD CONSTRAINT fk_user_profile_users
	FOREIGN KEY (user_id) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_role_role */
ALTER TABLE user_role ADD CONSTRAINT fk_user_role_role
	FOREIGN KEY (role_id) REFERENCES "role" (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_role_users */
ALTER TABLE user_role ADD CONSTRAINT fk_user_role_users
	FOREIGN KEY (user_id) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_settings_user_settings_group */
ALTER TABLE user_setting ADD CONSTRAINT fk_user_settings_user_settings_group
	FOREIGN KEY (setting_group) REFERENCES user_setting_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_setting_value_user_settings */
ALTER TABLE user_setting_value ADD CONSTRAINT fk_user_setting_value_user_settings
	FOREIGN KEY (user_setting_id) REFERENCES user_setting (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_setting_value_users */
ALTER TABLE user_setting_value ADD CONSTRAINT fk_user_setting_value_users
	FOREIGN KEY (user_id) REFERENCES rodauser (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_value_item */
ALTER TABLE value ADD CONSTRAINT fk_value_item
	FOREIGN KEY (item_id) REFERENCES item (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_formsSelectionVar_selection_variable_item */
ALTER TABLE variable ADD CONSTRAINT "fk_formsSelectionVar_selection_variable_item"
	FOREIGN KEY (instance_id) REFERENCES instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_variable_documents */
ALTER TABLE variable ADD CONSTRAINT fk_variable_documents
	FOREIGN KEY (file_id) REFERENCES file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_var_group_variable */
ALTER TABLE variable_vargroup ADD CONSTRAINT fk_instance_var_group_variable
	FOREIGN KEY (variable_id) REFERENCES variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_var_group_variable_group */
ALTER TABLE variable_vargroup ADD CONSTRAINT fk_instance_var_group_variable_group
	FOREIGN KEY (vargroup_id) REFERENCES vargroup (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;