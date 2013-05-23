/************ Update: Tables ***************/

/******************** Add Table: public.acl_class ************************/

/* Build Table Structure */
CREATE TABLE public.acl_class
(
id BIGSERIAL,
	class TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.acl_class ADD CONSTRAINT pkacl_class
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE public.acl_class IS 'Tabel folosit de Spring Security pentru ACL. Uniquely identify any domain object class in the system. The only columns are the ID and the Java class name. Thus, there is a single row for each unique Class we wish to store ACL permissions for.';

/* Add Indexes */
CREATE UNIQUE INDEX "acl_class_class_Idx" ON public.acl_class (class);


/******************** Add Table: public.acl_entry ************************/

/* Build Table Structure */
CREATE TABLE public.acl_entry
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
ALTER TABLE public.acl_entry ADD CONSTRAINT pkacl_entry
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE public.acl_entry IS 'Tabel folosit de Spring Security pentru ACL. Stores the individual permissions assigned to each recipient. Columns include a foreign key to the ACL_OBJECT_IDENTITY, the recipient (ie a foreign key to ACL_SID), whether we''ll be auditing or not, and the integer bit mask that represents the actual permission being granted or denied. We have a single row for every recipient that receives a permission to work with a domain object.';

/* Add Indexes */
CREATE UNIQUE INDEX "acl_entry_acl_object_identity_Idx" ON public.acl_entry (acl_object_identity, ace_order);


/******************** Add Table: public.acl_object_identity ************************/

/* Build Table Structure */
CREATE TABLE public.acl_object_identity
(
id BIGSERIAL,
	object_id_class BIGINT NOT NULL,
	object_id_identity BIGINT NOT NULL,
	parent_object BIGINT NULL,
	owner_sid BIGINT NOT NULL,
	entries_inheriting BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.acl_object_identity ADD CONSTRAINT pkacl_object_identity
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE public.acl_object_identity IS 'Tabel folosit de Spring Security pentru ACL. Stores information for each unique domain object instance in the system. Columns include the ID, a foreign key to the ACL_CLASS table, a unique identifier so we know which ACL_CLASS instance we''re providing information for, the parent, a foreign key to the ACL_SID table to represent the owner of the domain object instance, and whether we allow ACL entries to inherit from any parent ACL. We have a single row for every domain object instance we''re storing ACL permissions for.  ';

/* Add Indexes */
CREATE UNIQUE INDEX "acl_object_identity_object_id_class_Idx" ON public.acl_object_identity (object_id_class, object_id_identity);


/******************** Add Table: public.acl_sid ************************/

/* Build Table Structure */
CREATE TABLE public.acl_sid
(
id BIGSERIAL,
	principal BOOL NOT NULL,
	sid TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.acl_sid ADD CONSTRAINT pkacl_sid
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE public.acl_sid IS 'Tabel folosit de Spring Security pentru ACL. Uniquely identify any principal or authority in the system ("SID" stands for "security identity"). The only columns are the ID, a textual representation of the SID, and a flag to indicate whether the textual representation refers to a principal name or a GrantedAuthority. Thus, there is a single row for each unique principal or GrantedAuthority. When used in the context of receiving a permission, a SID is generally called a "recipient".';

/* Add Indexes */
CREATE UNIQUE INDEX "acl_sid_principal_sid_Idx" ON public.acl_sid (principal, sid);


/******************** Add Table: public.address ************************/

/* Build Table Structure */
CREATE TABLE public.address
(
id SERIAL,
	city_id INTEGER NOT NULL,
	address1 TEXT NOT NULL,
	address2 TEXT NULL,
	subdiv_name VARCHAR(200) NULL,
	subdiv_code VARCHAR(50) NULL,
	postal_code VARCHAR(30) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.address ADD CONSTRAINT pkaddress
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.address.id IS 'Codul adresei retinute';

COMMENT ON COLUMN public.address.city_id IS 'Codul orasului corespunzator adresei (refera atributul id din tabelul city)';

COMMENT ON COLUMN public.address.address1 IS 'Adresa detaliata (strada, numar, bloc, scara, apartament)';

COMMENT ON COLUMN public.address.address2 IS 'Detalii despre adresa (intefon, sala etc.)';

COMMENT ON COLUMN public.address.subdiv_name IS 'Subdiviziunea/Sectorul corespunzator adresei';

COMMENT ON COLUMN public.address.postal_code IS 'Codul Postal';

COMMENT ON TABLE public.address IS 'Tabel unic pentru toate adresele care se gasesc in baza de date';


/******************** Add Table: public.authorities ************************/

/* Build Table Structure */
CREATE TABLE public.authorities
(
	username VARCHAR(64) NOT NULL,
	authority VARCHAR(64) NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.authorities ADD CONSTRAINT pkauthorities
	PRIMARY KEY (username, authority);


/******************** Add Table: public.catalog ************************/

/* Build Table Structure */
CREATE TABLE public.catalog
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	parent_id INTEGER NULL,
	owner INTEGER NOT NULL,
	added TIMESTAMP DEFAULT now() NOT NULL,
	sequencenr INTEGER NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.catalog ADD CONSTRAINT pkcatalog
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.catalog.id IS 'Codul catalogului';

COMMENT ON COLUMN public.catalog.name IS 'Denumirea catalogului';

COMMENT ON COLUMN public.catalog.parent_id IS 'Codul catalogului din care face parte catalogul curent (refera atributul id al tabelului catalog)';

COMMENT ON COLUMN public.catalog.owner IS 'Codul utilizatorului care poseda catalogul (refera atributul id din tabelul users)';

COMMENT ON COLUMN public.catalog.added IS 'Data si timpul cand a fost adaugat catalogul respectiv';

COMMENT ON COLUMN public.catalog.sequencenr IS 'Coloana care permite ordonarea elementelor subordonate aceluiasi parinte';

COMMENT ON COLUMN public.catalog.description IS 'Descrierea catalogului';

COMMENT ON TABLE public.catalog IS 'Tabel ce stocheaza informatii despre cataloagele de studii';


/******************** Add Table: public.catalog_study ************************/

/* Build Table Structure */
CREATE TABLE public.catalog_study
(
	catalog_id INTEGER NOT NULL,
	study_id INTEGER NOT NULL,
	added TIMESTAMP NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.catalog_study ADD CONSTRAINT pkcatalog_study
	PRIMARY KEY (catalog_id, study_id);

/* Add Comments */
COMMENT ON COLUMN public.catalog_study.catalog_id IS 'Codul unui catalog din care face parte studiul referit prin atributul study_id (refera atributul id din tabelul catalog)';

COMMENT ON COLUMN public.catalog_study.study_id IS 'Codul studiului asociat catalogului referit prin atributul catalog_id (refera atributul id din tabelul study)';

COMMENT ON TABLE public.catalog_study IS 'Tabel ce contine asocierile dintre cataloage si studii (implementeaza relatia many-to-many intre catalog si study)';


/******************** Add Table: public.city ************************/

/* Build Table Structure */
CREATE TABLE public.city
(
id SERIAL,
	name TEXT NOT NULL,
	country_id INTEGER NOT NULL,
	city_code VARCHAR(50) NULL,
	city_code_name VARCHAR(100) NULL,
	city_code_sup VARCHAR(100) NULL,
	prefix VARCHAR(50) NULL,
	city_type VARCHAR(50) NULL,
	city_type_system VARCHAR(50) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.city ADD CONSTRAINT pkcity
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.city.id IS 'Codul orasului';

COMMENT ON COLUMN public.city.name IS 'Numele orasului';

COMMENT ON COLUMN public.city.country_id IS 'Codul tarii in care se afla orasul (refera atributul id al tabelului country)';

COMMENT ON TABLE public.city IS 'Tabel unic pentru toate referintele la orase';


/******************** Add Table: public.cms_file ************************/

/* Build Table Structure */
CREATE TABLE public.cms_file
(
id SERIAL,
	filename TEXT NOT NULL,
	label VARCHAR(100) NOT NULL,
	cms_folder_id INTEGER NOT NULL,
	filesize BIGINT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.cms_file ADD CONSTRAINT pkcms_file
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.cms_file.id IS 'Codul unui fisier din sistemul CMS al aplicatiei';

COMMENT ON COLUMN public.cms_file.filename IS 'Numele unui fisier din sistemul CMS al aplicatiei';

COMMENT ON COLUMN public.cms_file.label IS 'Eticheta (alias-ul) unui fisier din sistemul CMS al aplicatiei';

COMMENT ON COLUMN public.cms_file.cms_folder_id IS 'Codul folder-ului din care face parte fisierul curent (refera atributul id din tabelul cms_folder)';

COMMENT ON COLUMN public.cms_file.filesize IS 'Dimensiunea unui fisier din sistemul CMS al aplicatiei, exprimata in bytes';

COMMENT ON TABLE public.cms_file IS 'Tabel ce stocheaza informatii despre fisierele din sistemul CMS al aplicatiei';


/******************** Add Table: public.cms_folder ************************/

/* Build Table Structure */
CREATE TABLE public.cms_folder
(
id SERIAL,
	name TEXT NOT NULL,
	parent_id INTEGER NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.cms_folder ADD CONSTRAINT pkcms_folder
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.cms_folder.id IS 'Codul unui folder din sistemul cms al aplicatiei';

COMMENT ON COLUMN public.cms_folder.name IS 'Denumirea unui folder din sistemul CMS al aplicatiei';

COMMENT ON COLUMN public.cms_folder.parent_id IS 'Codul folder-ului parinte al folder-ului curent (refera atributul id din acelasi tabel)';

COMMENT ON COLUMN public.cms_folder.description IS 'Descrierea unui folder din sistemul CMS al aplicatiei';

COMMENT ON TABLE public.cms_folder IS 'Tabel pentru stocarea informatiilor despre folder-ele din sistemul CMS al aplicatiei';


/******************** Add Table: public.cms_layout ************************/

/* Build Table Structure */
CREATE TABLE public.cms_layout
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	cms_layout_group_id INTEGER NULL,
	layout_content TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.cms_layout ADD CONSTRAINT pkcms_layout
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.cms_layout.id IS 'Codul unui layout pentru o pagina de CMS ';

COMMENT ON COLUMN public.cms_layout.name IS 'Denumirea unui layout pentru o pagina de CMS';

COMMENT ON COLUMN public.cms_layout.cms_layout_group_id IS 'Codul grupului din care face parte un layout de CMS (refera atributul id din tabelul cms_layout_group)';

COMMENT ON COLUMN public.cms_layout.layout_content IS 'Continutul unui layout pentru o pagina de CMS';

COMMENT ON TABLE public.cms_layout IS 'Tabel care stocheaza layout-uri pentru paginile din sistemul CMS al aplicatiei';


/******************** Add Table: public.cms_layout_group ************************/

/* Build Table Structure */
CREATE TABLE public.cms_layout_group
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	parent_id INTEGER NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.cms_layout_group ADD CONSTRAINT pkcms_layout_group
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.cms_layout_group.id IS 'Codul unui grup de layout-uri';

COMMENT ON COLUMN public.cms_layout_group.name IS 'Denumirea unui grup de layout-uri';

COMMENT ON COLUMN public.cms_layout_group.parent_id IS 'Codul parintelui unui grup de layout-uri (refera atributul id din tabelul curent))';

COMMENT ON COLUMN public.cms_layout_group.description IS 'Descrierea unui grup de layout-uri';

COMMENT ON TABLE public.cms_layout_group IS 'Tabel ce stocheaza grupurile pentru layout-uri';


/******************** Add Table: public.cms_page ************************/

/* Build Table Structure */
CREATE TABLE public.cms_page
(
id SERIAL,
	name TEXT NOT NULL,
	cms_layout_id INTEGER NOT NULL,
	cms_page_type_id INTEGER NOT NULL,
	visible BOOL DEFAULT 'true' NOT NULL,
	navigable BOOL DEFAULT 'true' NOT NULL,
	url TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.cms_page ADD CONSTRAINT pkcms_page
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.cms_page.id IS 'Codul unei pagini din sistemul CMS';

COMMENT ON COLUMN public.cms_page.name IS 'Denumirea unei pagini din sistemul CMS';

COMMENT ON COLUMN public.cms_page.cms_layout_id IS 'Codul layout-ului corespunzator paginii din sistemul CMS (refera atributul id din tabelul cms_layout)';

COMMENT ON COLUMN public.cms_page.cms_page_type_id IS 'Codul tipului unei pagini din sistemul CMS';

COMMENT ON COLUMN public.cms_page.visible IS 'Atribut a carui valoare este true daca pagina din sistemul CMS este vizibila (valoarea implicita a acestui atribut este true)';

COMMENT ON COLUMN public.cms_page.navigable IS 'Atribut a carui valoare este true daca pagina din sistemul CMS este navigabila (valoarea implicita a acestui atribut este true)';

COMMENT ON COLUMN public.cms_page.url IS 'URL-ul paginii din sistemul CMS';

COMMENT ON TABLE public.cms_page IS 'Tabel care stocheaza paginile din sistemul CMS al aplicatiei';


/******************** Add Table: public.cms_page_content ************************/

/* Build Table Structure */
CREATE TABLE public.cms_page_content
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	cms_page_id INTEGER NOT NULL,
	content_title VARCHAR(250) NOT NULL,
	content_text TEXT NULL,
	order_in_page INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.cms_page_content ADD CONSTRAINT pkcms_page_content
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.cms_page_content.id IS 'Codul unei pagini de tip content din aplicatie';

COMMENT ON COLUMN public.cms_page_content.name IS 'Denumirea continutului';

COMMENT ON COLUMN public.cms_page_content.cms_page_id IS 'Codul paginii in sistemul CMS al aplicatiei (refera atributul id din tabelul page)';

COMMENT ON COLUMN public.cms_page_content.content_title IS 'Titlul continutului';

COMMENT ON COLUMN public.cms_page_content.content_text IS 'Textul continutului';

COMMENT ON COLUMN public.cms_page_content.order_in_page IS 'Numarul de ordine al continutului curent in cadrul paginii';

COMMENT ON TABLE public.cms_page_content IS 'Tabel pentru stocarea continutului paginilor din aplicatie';

/* Add Indexes */
CREATE UNIQUE INDEX "cms_page_content_seqnumber_Idx" ON public.cms_page_content (order_in_page);


/******************** Add Table: public.cms_page_type ************************/

/* Build Table Structure */
CREATE TABLE public.cms_page_type
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.cms_page_type ADD CONSTRAINT pkcms_page_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE public.cms_page_type IS 'Tabel pentru tipurile de CMS_Page';


/******************** Add Table: public.cms_snippet ************************/

/* Build Table Structure */
CREATE TABLE public.cms_snippet
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	cms_snippet_group_id INTEGER NULL,
	snippet_content TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.cms_snippet ADD CONSTRAINT pkcms_snippet
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.cms_snippet.id IS 'Codul unui snippet (fragment de cod) utilizat in aplicatie';

COMMENT ON COLUMN public.cms_snippet.name IS 'Denumirea unui snippet utilizat in aplicatie';

COMMENT ON COLUMN public.cms_snippet.cms_snippet_group_id IS 'Grupul din care face parte snippet-ul curent (refera atributul id al tabelului cms_snippet_group)';

COMMENT ON COLUMN public.cms_snippet.snippet_content IS 'Continutul snippet-ului curent';

COMMENT ON TABLE public.cms_snippet IS 'Tabel care stocheaza snippeturile (fragmentele de cod) folosite in aplicatie';


/******************** Add Table: public.cms_snippet_group ************************/

/* Build Table Structure */
CREATE TABLE public.cms_snippet_group
(
id SERIAL,
	name VARCHAR(200) NOT NULL,
	parent_id INTEGER NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.cms_snippet_group ADD CONSTRAINT pkcms_snippet_group
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.cms_snippet_group.id IS 'Codul unui grup de snippet-uri din sistemul CMS al aplicatiei';

COMMENT ON COLUMN public.cms_snippet_group.name IS 'Denumirea unui grup de snippet-uri din sistemul CMS al aplicatiei';

COMMENT ON COLUMN public.cms_snippet_group.parent_id IS 'Codul grupului de snippet-uri care este parintele grupului curent (refera atributul id din acelasi tabel)';

COMMENT ON COLUMN public.cms_snippet_group.description IS 'Descrierea grupului de snippet-uri ';

COMMENT ON TABLE public.cms_snippet_group IS 'Tabel care stocheaza grupuri de snippeturi utilizate in aplicatie';


/******************** Add Table: public.collection_model_type ************************/

/* Build Table Structure */
CREATE TABLE public.collection_model_type
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.collection_model_type ADD CONSTRAINT pkcollection_model_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.collection_model_type.id IS 'Codul tipului modelului de colectare a datelor';

COMMENT ON COLUMN public.collection_model_type.name IS 'Denumirea tipului metodei de colectare a datelor';

COMMENT ON TABLE public.collection_model_type IS 'Tabel ce contine tipurile modelelor de colectare a datelor';


/******************** Add Table: public.concept ************************/

/* Build Table Structure */
CREATE TABLE public.concept
(
id BIGSERIAL,
	name TEXT NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.concept ADD CONSTRAINT pkconcept
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.concept.id IS 'Codul conceptului';

COMMENT ON COLUMN public.concept.name IS 'Denumirea conceptului';

COMMENT ON COLUMN public.concept.description IS 'Descrierea detaliata a conceptului';

COMMENT ON TABLE public.concept IS 'Tabel ce stocheaza conceptele definite ';


/******************** Add Table: public.concept_variable ************************/

/* Build Table Structure */
CREATE TABLE public.concept_variable
(
	concept_id BIGINT NOT NULL,
	variable_id BIGINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.concept_variable ADD CONSTRAINT pkconcept_variable
	PRIMARY KEY (concept_id, variable_id);

/* Add Comments */
COMMENT ON COLUMN public.concept_variable.concept_id IS 'Codul conceptului la care se refera variabila identificata prin atributul variable_id, din cadrul instantei instance_id (refera atributul id din tabelul concept)';

COMMENT ON COLUMN public.concept_variable.variable_id IS 'Codul variabilei din instanta instance_id careia ii este asociat conceptul identificat prin atributul concept_id';

COMMENT ON TABLE public.concept_variable IS 'Tabel ce asociaza variabilelor o multime de concepte (implementeaza relatia many-to-many intre tabelele variable si concept)';


/******************** Add Table: public.country ************************/

/* Build Table Structure */
CREATE TABLE public.country
(
id SERIAL,
	name_ro TEXT NULL,
	name_self TEXT NULL,
	name_en TEXT NULL,
	iso3166 CHAR(2) NOT NULL,
	iso3166_alpha3 CHAR(3) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.country ADD CONSTRAINT pkcountry
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.country.id IS 'Codul tarii';

COMMENT ON COLUMN public.country.name_ro IS 'Numele tarii in limba romana';

COMMENT ON COLUMN public.country.name_self IS 'Numele tarii in limba romana';

COMMENT ON TABLE public.country IS 'Tabel unic pentru toate referintele la tari';

/* Add Indexes */
CREATE UNIQUE INDEX "country_iso3166_Idx" ON public.country (iso3166);


/******************** Add Table: public.data_source_type ************************/

/* Build Table Structure */
CREATE TABLE public.data_source_type
(
id SERIAL,
	name TEXT NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.data_source_type ADD CONSTRAINT pkdata_source_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.data_source_type.name IS 'The type of data sources which can be used in a study (controlled vocabulary - Nesstar)';


/******************** Add Table: public.email ************************/

/* Build Table Structure */
CREATE TABLE public.email
(
id SERIAL,
	email VARCHAR(200) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.email ADD CONSTRAINT pkemail
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.email.id IS 'Codul adresei de email in tabel';

COMMENT ON TABLE public.email IS 'Tabel unic pentru toate adresele de e-mail din baza de date';


/******************** Add Table: public.file ************************/

/* Build Table Structure */
CREATE TABLE public.file
(
id SERIAL,
	title TEXT NULL,
	description TEXT NULL,
	name TEXT NOT NULL,
	size BIGINT NULL,
	full_path TEXT NULL,
	content_type VARCHAR(100) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.file ADD CONSTRAINT pkfile
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.file.id IS 'Codul documentului';

COMMENT ON COLUMN public.file.title IS 'Titlul documentului';

COMMENT ON COLUMN public.file.description IS 'Descrierea documentului';

COMMENT ON COLUMN public.file.name IS 'Numele fisierului asociat documentului';

COMMENT ON COLUMN public.file.size IS 'Dimensiunea fisierului (specificata in bytes)';

COMMENT ON COLUMN public.file.full_path IS 'Calea completa (unde este stocat fisierul pe server)';

COMMENT ON TABLE public.file IS 'Tabel ce contine documentele asociate oricarei entitati din baza de date (studiu sau instanta)';


/******************** Add Table: public.form ************************/

/* Build Table Structure */
CREATE TABLE public.form
(
id BIGSERIAL,
	operator_id INTEGER NULL,
	operator_notes TEXT NULL,
	form_filled_at TIMESTAMP NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.form ADD CONSTRAINT pkform
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.form.operator_notes IS 'observatii ale operatorului';

COMMENT ON COLUMN public.form.form_filled_at IS 'momentul completarii acestui chestionar cu raspunsuri de catre operator, pe teren';

COMMENT ON TABLE public.form IS 'Tabel pentru informatiile legate de un chestionarele aplicate (un rand reprezinta o anumita fisa completata pe teren cu raspunsuri)';


/******************** Add Table: public.form_edited_number_var ************************/

/* Build Table Structure */
CREATE TABLE public.form_edited_number_var
(
	form_id BIGINT NOT NULL,
	variable_id BIGINT NOT NULL,
	value NUMERIC(10, 2) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.form_edited_number_var ADD CONSTRAINT pkform_edited_number_var
	PRIMARY KEY (variable_id, form_id);

/* Add Comments */
COMMENT ON COLUMN public.form_edited_number_var.form_id IS 'Codul formularului completat in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN public.form_edited_number_var.variable_id IS 'Codul unei variabile editate de tip numeric in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN public.form_edited_number_var.value IS 'Valoarea numerica furnizata ca raspuns pentru variabila referita prin atributul variable_id din cadrul instantei identificate prin atributul instance_id, asociata formularului specificat prin atributul form_id  ';

COMMENT ON TABLE public.form_edited_number_var IS 'Tabel ce inregistreaza raspunsurile numerice ale variabilelor de tip editat';


/******************** Add Table: public.form_edited_text_var ************************/

/* Build Table Structure */
CREATE TABLE public.form_edited_text_var
(
	variable_id BIGINT NOT NULL,
	form_id BIGINT NOT NULL,
	text TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.form_edited_text_var ADD CONSTRAINT pkform_edited_text_var
	PRIMARY KEY (variable_id, form_id);

/* Add Comments */
COMMENT ON COLUMN public.form_edited_text_var.variable_id IS 'Codul unei variabile editate de tip text corespunzatoare instantei identificate prin atributul instance_id';

COMMENT ON COLUMN public.form_edited_text_var.form_id IS 'Codul formularului completat in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN public.form_edited_text_var.text IS 'Raspunsul furnizat pentru variabila identificata prin atributul variable_id in cadrul instantei referite prin instance_id, pe formularul identificat prin atributul form_id';

COMMENT ON TABLE public.form_edited_text_var IS 'Tabel ce inregistreaza raspunsurile text ale variabilelor de tip editat';


/******************** Add Table: public.form_selection_var ************************/

/* Build Table Structure */
CREATE TABLE public.form_selection_var
(
	form_id BIGINT NOT NULL,
	variable_id BIGINT NOT NULL,
	item_id BIGINT NOT NULL,
	order_of_items_in_response INTEGER NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.form_selection_var ADD CONSTRAINT pkform_selection_var
	PRIMARY KEY (form_id, variable_id, item_id);

/* Add Comments */
COMMENT ON COLUMN public.form_selection_var.form_id IS 'Codul unui formular completat in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN public.form_selection_var.variable_id IS 'Codul unei variabile din cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN public.form_selection_var.item_id IS 'Identificatorul elementului selectat ca raspuns pentru variabila identificata prin atributul variable_id din instanta referita prin atributul instance_id, asociat formularului specificat prin atributul form_id';

COMMENT ON COLUMN public.form_selection_var.order_of_items_in_response IS 'Numarul de ordine al elementului corespunzator variabilei in cadrul raspunsului (relevant pentru raspunsuri care accepta o selectie multipla, in care ordinea este importanta)';

COMMENT ON TABLE public.form_selection_var IS 'Tabel ce inregistreaza raspunsurile la variabilele de selectie (pentru care exista optiuni de raspuns) ';


/******************** Add Table: public.instance ************************/

/* Build Table Structure */
CREATE TABLE public.instance
(
id SERIAL,
	study_id INTEGER NOT NULL,
	added_by INTEGER NOT NULL,
	added TIMESTAMP NOT NULL,
	disseminator_identifier TEXT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.instance ADD CONSTRAINT pkinstance
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.instance.id IS 'Codul instantei';

COMMENT ON COLUMN public.instance.study_id IS 'Codul studiului caruia ii apartine instanta (refera atributul id al tabelului study)';

COMMENT ON COLUMN public.instance.disseminator_identifier IS 'Identification Number/String provided by the disseminator of the Dataset';

COMMENT ON COLUMN public.instance.main IS 'Daca este instanta/Dataset-ul principal al studiului (rezultat dupa importarea tuturor datelor)';

COMMENT ON TABLE public.instance IS 'Tabel ce contine informatiile principale ale instantelor';


/******************** Add Table: public.instance_descr ************************/

/* Build Table Structure */
CREATE TABLE public.instance_descr
(
	instance_id INTEGER NOT NULL,
	lang_id INTEGER NOT NULL,
	access_conditions TEXT NULL,
	notes TEXT NULL,
	title TEXT NOT NULL,
	original_title_language BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.instance_descr ADD CONSTRAINT pkinstance_descr
	PRIMARY KEY (instance_id, lang_id);

/* Add Comments */
COMMENT ON COLUMN public.instance_descr.instance_id IS 'Codul instantei pentru care sunt furnizate elemente descriptive';

COMMENT ON COLUMN public.instance_descr.access_conditions IS 'Details the terms of access for the specific Dataset as issued by the publisher';

COMMENT ON TABLE public.instance_descr IS 'Tabel ce contine elementele descriptive ale instantelor';


/******************** Add Table: public.instance_documents ************************/

/* Build Table Structure */
CREATE TABLE public.instance_documents
(
	instance_id INTEGER NOT NULL,
	document_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.instance_documents ADD CONSTRAINT pkinstance_documents
	PRIMARY KEY (instance_id, document_id);

/* Add Comments */
COMMENT ON COLUMN public.instance_documents.instance_id IS 'Codul instantei care contine documentul specificat prin atributul document_id (refera atributul id al tabelului instance)';

COMMENT ON COLUMN public.instance_documents.document_id IS 'Codul documentului asociat instantei specificate prin atributul instance_id (refera atributul id al tabelului documents)';

COMMENT ON TABLE public.instance_documents IS 'Tabel ce contine asocierile dintre instante si documente (implementeaza relatia many-to-many intre tabelele instance si documents)';


/******************** Add Table: public.instance_form ************************/

/* Build Table Structure */
CREATE TABLE public.instance_form
(
	instance_id INTEGER NOT NULL,
	form_id BIGINT NOT NULL,
	order_form_in_instance INTEGER NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.instance_form ADD CONSTRAINT pkinstance_form
	PRIMARY KEY (instance_id, form_id);

/* Add Indexes */
CREATE UNIQUE INDEX "instance_form_instance_id_Idx" ON public.instance_form (instance_id, order_form_in_instance);


/******************** Add Table: public.instance_org ************************/

/* Build Table Structure */
CREATE TABLE public.instance_org
(
	org_id INTEGER NOT NULL,
	instance_id INTEGER NOT NULL,
	assoc_type_id INTEGER NOT NULL,
	assoc_details TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.instance_org ADD CONSTRAINT pkinstance_org
	PRIMARY KEY (org_id, instance_id, assoc_type_id);

/* Add Comments */
COMMENT ON COLUMN public.instance_org.org_id IS 'Codul organizatiei care se afla in relatia specificata prin atributul assoc_type_id cu instanta identificata prin atributul instance_id (refera atributul id al tabelului org)';

COMMENT ON COLUMN public.instance_org.instance_id IS 'Codul instantei care se afla in relatia specificata prin atributul assoc_type_id cu organizatia identificata prin atributul org_id (refera atributul id al tabelului instance)';

COMMENT ON COLUMN public.instance_org.assoc_type_id IS 'Codul tipului de asociere corespunzator relatiei dintre organizatia identificata prin atributul org_id si instanta specificata prin atributul instance_id (refera atributul id al tabelului instance_org_assoc)';

COMMENT ON COLUMN public.instance_org.assoc_details IS 'Detaliile asocierii dintre  organizatia identificata prin atributul org_id si instanta specificata prin atributul instance_id';

COMMENT ON TABLE public.instance_org IS 'Tabel ce contine asocierile dintre instante si organizatii (implementeaza relatia many-to-many intre tabelele instance si org)';


/******************** Add Table: public.instance_org_assoc ************************/

/* Build Table Structure */
CREATE TABLE public.instance_org_assoc
(
id SERIAL,
	assoc_name TEXT NOT NULL,
	assoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.instance_org_assoc ADD CONSTRAINT pkinstance_org_assoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.instance_org_assoc.id IS 'Codul unui tip de asociere care poate exista intre o instanta si o organizatie';

COMMENT ON COLUMN public.instance_org_assoc.assoc_name IS 'Numele unui tip de asociere care poate exista intre o instanta si o organizatie';

COMMENT ON COLUMN public.instance_org_assoc.assoc_description IS 'Descrierea unui tip de asociere care poate exista intre o instanta si o organizatie';

COMMENT ON TABLE public.instance_org_assoc IS 'Tabel ce contine tipurile de asociere care pot exista intre o instanta si organizatie';


/******************** Add Table: public.instance_person ************************/

/* Build Table Structure */
CREATE TABLE public.instance_person
(
	person_id INTEGER NOT NULL,
	instance_id INTEGER NOT NULL,
	assoc_type_id INTEGER NOT NULL,
	assoc_details TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.instance_person ADD CONSTRAINT pkinstance_person
	PRIMARY KEY (person_id, instance_id, assoc_type_id);

/* Add Comments */
COMMENT ON COLUMN public.instance_person.person_id IS 'Codul persoanei aflate in relatia specificata prin atributul assoc_type_id cu instanta identificata prin atributul instance_id (refera atributul id al tabelului person)';

COMMENT ON COLUMN public.instance_person.instance_id IS 'Codul instantei care se afla in relatia specificata prin atributul assoc_type_id cu instanta identificata prin atributul instance_id (refera atributul id al tabelului instance)';

COMMENT ON COLUMN public.instance_person.assoc_type_id IS 'Codul tipului de asociere care exista intre instanta specificata prin atributul instance_id si persoana identificata prin atributul person_id (refera atributul id al tabelului instance_person_assoc)';

COMMENT ON TABLE public.instance_person IS 'Tabel care stocheaza asocierile intre instante si persoane (implementeaza relatia many-to-many intre tabelele person si instance)';


/******************** Add Table: public.instance_person_assoc ************************/

/* Build Table Structure */
CREATE TABLE public.instance_person_assoc
(
id SERIAL,
	assoc_name TEXT NOT NULL,
	assoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.instance_person_assoc ADD CONSTRAINT pkinstance_person_assoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.instance_person_assoc.id IS 'Codul unui tip de asociere care poate exista intre persoane si instante';

COMMENT ON COLUMN public.instance_person_assoc.assoc_name IS 'Denumirea unui tip de asociere care poate exista intre persoane si instante';

COMMENT ON COLUMN public.instance_person_assoc.assoc_description IS 'Descrierea tipului de asociere identificat prin atributul id';

COMMENT ON TABLE public.instance_person_assoc IS 'Tabel ce contine tipurile de asociere intre instanta si persoana';


/******************** Add Table: public.instance_right ************************/

/* Build Table Structure */
CREATE TABLE public.instance_right
(
id SERIAL,
	name TEXT NOT NULL,
	description TEXT NULL
);

/* Add Primary Key */
ALTER TABLE public.instance_right ADD CONSTRAINT pkinstance_right
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.instance_right.name IS 'Descrierea dreptului (file posession, onsite access, remote access, remote execution etc.)';


/******************** Add Table: public.instance_right_target_group ************************/

/* Build Table Structure */
CREATE TABLE public.instance_right_target_group
(
	instance_id INTEGER NOT NULL,
	instance_right_id INTEGER NOT NULL,
	target_group_id INTEGER NOT NULL,
	instance_right_value_id INTEGER NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.instance_right_target_group ADD CONSTRAINT pkinstance_right_target_group
	PRIMARY KEY (instance_id, instance_right_id, target_group_id);


/******************** Add Table: public.instance_right_value ************************/

/* Build Table Structure */
CREATE TABLE public.instance_right_value
(
id SERIAL,
	value INTEGER NOT NULL,
	description TEXT NULL,
	instance_right_id INTEGER NOT NULL,
	fee INTEGER NULL,
	fee_currency_abbr VARCHAR(3) NULL
);

/* Add Primary Key */
ALTER TABLE public.instance_right_value ADD CONSTRAINT pkinstance_right_value
	PRIMARY KEY (id);


/******************** Add Table: public.instance_sampling_procedure ************************/

/* Build Table Structure */
CREATE TABLE public.instance_sampling_procedure
(
	study_id INTEGER NOT NULL,
	sampling_procedure_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.instance_sampling_procedure ADD CONSTRAINT pkinstance_sampling_procedure
	PRIMARY KEY (study_id, sampling_procedure_id);

/* Add Comments */
COMMENT ON TABLE public.instance_sampling_procedure IS 'Tabel pentru reprezentarea relatiilor NxM intre o anumita "Instance" si o anumita "Sampling_Procedure"';


/******************** Add Table: public.instance_variable ************************/

/* Build Table Structure */
CREATE TABLE public.instance_variable
(
	instance_id INTEGER NOT NULL,
	variable_id BIGINT NOT NULL,
	order_variable_in_instance INTEGER NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.instance_variable ADD CONSTRAINT pkinstance_variable
	PRIMARY KEY (instance_id, variable_id);

/* Add Indexes */
CREATE UNIQUE INDEX "instance_variable_instance_id_Idx" ON public.instance_variable (instance_id, order_variable_in_instance);


/******************** Add Table: public.internet ************************/

/* Build Table Structure */
CREATE TABLE public.internet
(
id SERIAL,
	internet_type VARCHAR(50) NULL,
	internet TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.internet ADD CONSTRAINT pkinternet
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.internet.id IS 'Codul contului pe internet al unui contact';

COMMENT ON COLUMN public.internet.internet_type IS 'Tipul contului ';

COMMENT ON TABLE public.internet IS 'Tabel ce contine toate conturile de pe retelele sociale de pe internet (facebook, twitter, im  si altele)';


/******************** Add Table: public.item ************************/

/* Build Table Structure */
CREATE TABLE public.item
(
id BIGSERIAL,
	name VARCHAR(100) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.item ADD CONSTRAINT pkitem
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.item.id IS 'Identificatorul item-ului';

COMMENT ON COLUMN public.item.name IS 'Numele item-ului';

COMMENT ON TABLE public.item IS 'Tabel ce stocheaza elementele (item-urile) variabilelor de selectie din baza de date';


/******************** Add Table: public.keyword ************************/

/* Build Table Structure */
CREATE TABLE public.keyword
(
id SERIAL,
	name TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.keyword ADD CONSTRAINT pkkeyword
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.keyword.id IS 'Codul cuvantului cheie';

COMMENT ON COLUMN public.keyword.name IS 'Cuvantul cheie';

COMMENT ON TABLE public.keyword IS 'Tabel ce contine cuvintele cheie ale studiilor si instantelor din baza de date';


/******************** Add Table: public.lang ************************/

/* Build Table Structure */
CREATE TABLE public.lang
(
id SERIAL,
	iso639 CHAR(2) NOT NULL,
	name_self VARCHAR(50) NULL,
	name_ro VARCHAR(50) NULL,
	name_en VARCHAR(50) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.lang ADD CONSTRAINT pklang
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.lang.id IS 'Codul unei limbi ce poate fi utilizata pentru un termen din baza de date';

COMMENT ON COLUMN public.lang.iso639 IS 'Codul unei limbi in ISO 639-1 (2 litere)';

COMMENT ON COLUMN public.lang.name_self IS 'Denumirea unei limbi in propria limba';

COMMENT ON COLUMN public.lang.name_ro IS 'Denumirea unei limbi in limba romana';

COMMENT ON COLUMN public.lang.name_en IS 'Denumirea unei limbi in limba engleza';

COMMENT ON TABLE public.lang IS 'Tabel ce contine limbile utilizate pentru unii termeni din baza de date';

/* Add Indexes */
CREATE UNIQUE INDEX "lang_iso639_Idx" ON public.lang (iso639);


/******************** Add Table: public.meth_coll_type ************************/

/* Build Table Structure */
CREATE TABLE public.meth_coll_type
(
	study_id INTEGER NOT NULL,
	collection_model_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.meth_coll_type ADD CONSTRAINT pkmeth_coll_type
	PRIMARY KEY (study_id, collection_model_id);

/* Add Comments */
COMMENT ON COLUMN public.meth_coll_type.study_id IS 'Codul instantei pentru care se asociaza un model de colectare a datelor';

COMMENT ON COLUMN public.meth_coll_type.collection_model_id IS 'Codul tipului de model de colectare a datelor asociat instantei identificate prin atributul instance_id';

COMMENT ON TABLE public.meth_coll_type IS 'Tabel ce stocheaza tipurile modelelor de colectare a datelor utilizate in cadrul unei instante';


/******************** Add Table: public.news ************************/

/* Build Table Structure */
CREATE TABLE public.news
(
id SERIAL,
	added TIMESTAMP NOT NULL,
	visible BOOL NOT NULL,
	title TEXT NOT NULL,
	content TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.news ADD CONSTRAINT pknews
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.news.id IS 'Codul stirii';

COMMENT ON COLUMN public.news.added IS 'Momentul de timp la care stirea a fost adaugata';

COMMENT ON COLUMN public.news.visible IS 'Atribut boolean, a carui valoare este true daca stirea este vizibila';

COMMENT ON COLUMN public.news.title IS 'Titlul stirii';

COMMENT ON COLUMN public.news.content IS 'Continutul stirii';

COMMENT ON TABLE public.news IS 'Tabel ce stocheaza stirile ce vor aparea in interfata aplicatiei';


/******************** Add Table: public.org ************************/

/* Build Table Structure */
CREATE TABLE public.org
(
id SERIAL,
	short_name VARCHAR(100) NOT NULL,
	org_prefix_id INTEGER NULL,
	full_name TEXT NOT NULL,
	org_sufix_id INTEGER NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.org ADD CONSTRAINT pkorg
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.org.id IS 'Codul organizatiei';

COMMENT ON COLUMN public.org.short_name IS 'Denumirea prescurtata a organizatiei (posibil un acronim al acesteia)';

COMMENT ON COLUMN public.org.org_prefix_id IS 'Codul prefixului organizatiei (refera atributul id din tabelul org_prefix)';

COMMENT ON COLUMN public.org.full_name IS 'Denumirea completa a organizatiei ';

COMMENT ON COLUMN public.org.org_sufix_id IS 'Codul sufixului organizatiei (refera atributul id din tabelul org_sufix)';

COMMENT ON TABLE public.org IS 'Tabel ce contine toate organizatiile din baza de date';


/******************** Add Table: public.org_address ************************/

/* Build Table Structure */
CREATE TABLE public.org_address
(
	org_id INTEGER NOT NULL,
	address_id INTEGER NOT NULL,
	date_start DATE NULL,
	date_end DATE NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.org_address ADD CONSTRAINT pkorg_address
	PRIMARY KEY (org_id, address_id);

/* Add Comments */
COMMENT ON COLUMN public.org_address.org_id IS 'Codul organizatiei care detine o adresa identificata prin atributul address_id (refera atributul id din tabelul org) ';

COMMENT ON COLUMN public.org_address.address_id IS 'Codul adresei detinute de organizatia specificata prin atributul org_id (refera atributul id din tabelul org)';

COMMENT ON COLUMN public.org_address.date_start IS 'Data incepand de la care adresa organizatiei referite prin atributul org_id a devenit cea identificata prin atributul address_id';

COMMENT ON COLUMN public.org_address.date_end IS 'Data pana la care adresa organizatiei referite prin org_id a fost cea identificata prin address_id';

COMMENT ON TABLE public.org_address IS 'Tabel ce contine asocierile dintre organizatii si adrese (implementeaza relatia many-to-many intre tabelele org si adresa)';


/******************** Add Table: public.org_email ************************/

/* Build Table Structure */
CREATE TABLE public.org_email
(
	org_id INTEGER NOT NULL,
	email_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.org_email ADD CONSTRAINT pkorg_email
	PRIMARY KEY (org_id, email_id);

/* Add Comments */
COMMENT ON TABLE public.org_email IS 'Tabel de legatura pentru relatia de tip NxM intre "Org" si "Email"';


/******************** Add Table: public.org_internet ************************/

/* Build Table Structure */
CREATE TABLE public.org_internet
(
	org_id INTEGER NOT NULL,
	internet_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.org_internet ADD CONSTRAINT pkorg_internet
	PRIMARY KEY (org_id, internet_id);

/* Add Comments */
COMMENT ON TABLE public.org_internet IS 'Tabel de legatura pentru relatia de tip NxM intre "Org" si "Internet"';


/******************** Add Table: public.org_phone ************************/

/* Build Table Structure */
CREATE TABLE public.org_phone
(
	org_id INTEGER NOT NULL,
	phone_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.org_phone ADD CONSTRAINT pkorg_phone
	PRIMARY KEY (org_id, phone_id);

/* Add Comments */
COMMENT ON TABLE public.org_phone IS 'Tabel de legatura pentru relatia de tip NxM intre "Org" si "Phone"';


/******************** Add Table: public.org_prefix ************************/

/* Build Table Structure */
CREATE TABLE public.org_prefix
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.org_prefix ADD CONSTRAINT pkorg_prefix
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.org_prefix.id IS 'Codul prefixului de organizatie';

COMMENT ON COLUMN public.org_prefix.name IS 'Prefixul de organizatie (SC, alte prefixe)';

COMMENT ON COLUMN public.org_prefix.description IS 'Descrierea prefixului';

COMMENT ON TABLE public.org_prefix IS 'Tabel ce contine prefixele organizatiilor ';


/******************** Add Table: public.org_relation_type ************************/

/* Build Table Structure */
CREATE TABLE public.org_relation_type
(
id SERIAL,
	name VARCHAR(100) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.org_relation_type ADD CONSTRAINT pkorg_relation_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.org_relation_type.id IS 'Codul tipului relatiei intre organizatii';

COMMENT ON COLUMN public.org_relation_type.name IS 'Numele tipului relatiei intre organizatii';

COMMENT ON TABLE public.org_relation_type IS 'Tabel ce contine tipurile relatiilor care pot exista intre organizatii';


/******************** Add Table: public.org_relations ************************/

/* Build Table Structure */
CREATE TABLE public.org_relations
(
	org_1_id INTEGER NOT NULL,
	org_2_id INTEGER NOT NULL,
	date_start DATE NULL,
	date_end DATE NULL,
	org_relation_type_id INTEGER NOT NULL,
	details TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.org_relations ADD CONSTRAINT pkorg_relations
	PRIMARY KEY (org_1_id, org_2_id, org_relation_type_id);

/* Add Comments */
COMMENT ON COLUMN public.org_relations.org_1_id IS 'Codul primei organizatii implicate in relatie';

COMMENT ON COLUMN public.org_relations.org_2_id IS 'Codul celei de-a doua organizatii implicate in relatie';

COMMENT ON COLUMN public.org_relations.date_start IS 'Data de inceput a relatiei specificate prin tipul org_relation_type_id intre organizatiile specificate prin org_1_id si org_2_id';

COMMENT ON COLUMN public.org_relations.date_end IS 'Data de final a relatiei specificate prin tipul org_relation_type intre organizatiile specificate prin org_1_id si org_2_id';

COMMENT ON COLUMN public.org_relations.org_relation_type_id IS 'Codul tipului relatiei demarate la datestart intre organizatiile specificate prin org_1_id si org_2_id ';

COMMENT ON COLUMN public.org_relations.details IS 'Detaliile relatiei demarate la datestart intre organizatiile specificate prin org_1_id si org_2_id';

COMMENT ON TABLE public.org_relations IS 'Tabel ce stocheaza relatiile dintre organizatii (filiala, subsidiare, holding)';


/******************** Add Table: public.org_sufix ************************/

/* Build Table Structure */
CREATE TABLE public.org_sufix
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.org_sufix ADD CONSTRAINT pkorg_sufix
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.org_sufix.id IS 'Codul sufixului de organizatie';

COMMENT ON COLUMN public.org_sufix.name IS 'Denumirea sufixului de organizatie (SRL, etc)';

COMMENT ON COLUMN public.org_sufix.description IS 'Descrierea sufixului de organizatie';

COMMENT ON TABLE public.org_sufix IS 'Tabel ce contine sufixele organizatiilor';


/******************** Add Table: public.other_statistic ************************/

/* Build Table Structure */
CREATE TABLE public.other_statistic
(
	variable_id BIGINT NOT NULL,
	name VARCHAR(100) NOT NULL,
	value REAL NOT NULL,
	description TEXT NULL,
id BIGSERIAL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.other_statistic ADD CONSTRAINT pkother_statistic
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.other_statistic.variable_id IS 'Codul variabilei pentru care sunt stocate statistici';

COMMENT ON COLUMN public.other_statistic.name IS 'Denumirea statisticii';

COMMENT ON COLUMN public.other_statistic.value IS 'Valoarea statisticii';

COMMENT ON TABLE public.other_statistic IS 'Tabel ce contine statistici specifice variabilelor editate';


/******************** Add Table: public.person ************************/

/* Build Table Structure */
CREATE TABLE public.person
(
id SERIAL,
	fname VARCHAR(100) NOT NULL,
	mname VARCHAR(100) NULL,
	lname VARCHAR(100) NOT NULL,
	prefix_id INTEGER NULL,
	suffix_id INTEGER NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.person ADD CONSTRAINT pkperson
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.person.id IS 'Codul persoanei';

COMMENT ON COLUMN public.person.fname IS 'Prenumele persoanei';

COMMENT ON COLUMN public.person.mname IS 'Numele din mijloc al persoanei';

COMMENT ON COLUMN public.person.lname IS 'Numele de familie al persoanei';

COMMENT ON COLUMN public.person.prefix_id IS 'Codul prefixului corespunzator persoanei (refera atributul id din tabelul prefix)';

COMMENT ON COLUMN public.person.suffix_id IS 'Codul sufixului';

COMMENT ON TABLE public.person IS 'Tabel unic pentru toate persoanele din baza de date';


/******************** Add Table: public.person_address ************************/

/* Build Table Structure */
CREATE TABLE public.person_address
(
	person_id INTEGER NOT NULL,
	address_id INTEGER NOT NULL,
	date_start DATE NULL,
	date_end DATE NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.person_address ADD CONSTRAINT pkperson_address
	PRIMARY KEY (person_id, address_id);

/* Add Comments */
COMMENT ON COLUMN public.person_address.person_id IS 'Codul persoanei pentru care este stocata o asociere cu adresa referita prin atributul address_id (refera atributul id din tabelul person)';

COMMENT ON COLUMN public.person_address.address_id IS 'Codul adresei care este asociata persoanei identificate prin atributul person_id (refera atributul id din tabelul address)';

COMMENT ON COLUMN public.person_address.date_start IS 'Data incepand de la care persoana identificata prin atributul person_id a avut adresa referita prin atributul address_id';

COMMENT ON COLUMN public.person_address.date_end IS 'Data pana la care persoana identificata prin atributul person_id a avut adresa referita prin atributul address_id';

COMMENT ON TABLE public.person_address IS 'Tabel ce contine asocierile intre persoane si adrese (implementeaza relatia many-to-many intre tabelele address si person)';


/******************** Add Table: public.person_email ************************/

/* Build Table Structure */
CREATE TABLE public.person_email
(
	person_id INTEGER NOT NULL,
	email_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.person_email ADD CONSTRAINT pkperson_email
	PRIMARY KEY (person_id, email_id);

/* Add Comments */
COMMENT ON TABLE public.person_email IS 'Tabel de legatura pentru relatia de tip NxM intre "Person" si "Email"';


/******************** Add Table: public.person_internet ************************/

/* Build Table Structure */
CREATE TABLE public.person_internet
(
	person_id INTEGER NOT NULL,
	internet_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.person_internet ADD CONSTRAINT pkperson_internet
	PRIMARY KEY (person_id, internet_id);

/* Add Comments */
COMMENT ON TABLE public.person_internet IS 'Tabel de legatura pentru relatia de tip NxM intre "Person" si "Internet"';


/******************** Add Table: public.person_links ************************/

/* Build Table Structure */
CREATE TABLE public.person_links
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
ALTER TABLE public.person_links ADD CONSTRAINT pkperson_links
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.person_links.id IS 'Codul relatiei intre persoana referita prin atributul person_id si utilizatorul identificat prin user_id';

COMMENT ON COLUMN public.person_links.person_id IS 'Codul persoanei (refera atributul id din tabelul person)';

COMMENT ON COLUMN public.person_links.user_id IS 'Codul utilizatorului (refera atributul id din tabelul user)';

COMMENT ON COLUMN public.person_links.simscore IS 'Scorul general de similaritate intre persoana si utilizator (determinat pentru a stabili automat relatia intre persoana si utilizator)';

COMMENT ON COLUMN public.person_links.namescore IS 'Scorul de similaritate bazat pe numele persoanei si al utilizatorului (determinat pentru a stabili automat relatia intre persoana si utilizator)';

COMMENT ON COLUMN public.person_links.emailscore IS 'Scorul de similaritate bazat pe adresa de email (determinat pentru a stabili automat relatia intre persoana si utilizator)';

COMMENT ON COLUMN public.person_links.status IS 'Starea deciziei existentei unei legaturi intre intre persoana si utilizator (confirmat, infirmat, in asteptare)';

COMMENT ON COLUMN public.person_links.status_by IS 'Codul utilizatorului care a actualizat campul status (refera atributul id din tabelul user)';

COMMENT ON COLUMN public.person_links.status_time IS 'Momentul de timp al ultimei actualizari a atributului status';

COMMENT ON TABLE public.person_links IS 'Tabel ce stocheaza relatiile dintre persoane si utilizatorii aplicatiei ';


/******************** Add Table: public.person_org ************************/

/* Build Table Structure */
CREATE TABLE public.person_org
(
	person_id INTEGER NOT NULL,
	org_id INTEGER NOT NULL,
	role_id INTEGER NOT NULL,
	date_start DATE NULL,
	date_end DATE NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.person_org ADD CONSTRAINT pkperson_org
	PRIMARY KEY (person_id, org_id, role_id);

/* Add Comments */
COMMENT ON COLUMN public.person_org.person_id IS 'Identificatorul persoanei care lucreaza in organizatie';

COMMENT ON COLUMN public.person_org.org_id IS 'Idetificatorul organizatiei';

COMMENT ON COLUMN public.person_org.role_id IS 'Identificatorul rolului detinut de persoana in cadrul organizatiei (refera atributul id al tabelului person_role)';

COMMENT ON COLUMN public.person_org.date_start IS 'Data de inceput a apartenentei persoanei la organizatie';

COMMENT ON COLUMN public.person_org.date_end IS 'Data de final a apartenentei persoanei la organizatie';

COMMENT ON TABLE public.person_org IS 'Tabel ce stocheaza asocierile dintre persoane si organizatii (implementeaza relatia many-to-many intre tabelele person si org)';


/******************** Add Table: public.person_phone ************************/

/* Build Table Structure */
CREATE TABLE public.person_phone
(
	person_id INTEGER NOT NULL,
	phone_id INTEGER NOT NULL,
	main BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.person_phone ADD CONSTRAINT pkperson_phone
	PRIMARY KEY (person_id, phone_id);

/* Add Comments */
COMMENT ON TABLE public.person_phone IS 'Tabel de legatura pentru relatia de tip NxM intre "Person" si "Phone"';


/******************** Add Table: public.person_role ************************/

/* Build Table Structure */
CREATE TABLE public.person_role
(
id SERIAL,
	name TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.person_role ADD CONSTRAINT pkperson_role
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.person_role.id IS 'Codul rolului';

COMMENT ON COLUMN public.person_role.name IS 'Denumirea rolului respectiv (director, manager, persoana de contact, etc)';

COMMENT ON TABLE public.person_role IS 'Tabel care contine rolurile pe care le pot detine persoanele in cadrul organizatiilor';


/******************** Add Table: public.phone ************************/

/* Build Table Structure */
CREATE TABLE public.phone
(
id SERIAL,
	phone VARCHAR(30) NOT NULL,
	phone_type VARCHAR(50) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.phone ADD CONSTRAINT pkphone
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE public.phone IS 'Tabel unic ce pastreaza Telefoane';


/******************** Add Table: public.prefix ************************/

/* Build Table Structure */
CREATE TABLE public.prefix
(
id SERIAL,
	name VARCHAR(50) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.prefix ADD CONSTRAINT pkprefix
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.prefix.id IS 'Codul prefixului pentru persoane';

COMMENT ON COLUMN public.prefix.name IS 'Denumirea prefixului care poate fi utilizat pentru persoane (Domnul, Doamna, etc.)';

COMMENT ON TABLE public.prefix IS 'Tabel ce contine prefixele corespunzatoare formulelor de adresare catre persoane';


/******************** Add Table: public.region ************************/

/* Build Table Structure */
CREATE TABLE public.region
(
id SERIAL,
	name TEXT NOT NULL,
	regiontype_id INTEGER NOT NULL,
	country_id INTEGER NOT NULL,
	region_code VARCHAR(50) NULL,
	region_code_name VARCHAR(50) NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.region ADD CONSTRAINT pkregion
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.region.id IS 'Identificatorul regiunii';

COMMENT ON COLUMN public.region.name IS 'Numele regiunii';

COMMENT ON COLUMN public.region.regiontype_id IS 'Tipul regiunii (refera atributul id din tabelul region_type)';

COMMENT ON COLUMN public.region.country_id IS 'Codul tarii corespunzatoare regiunii (refera atributul id din tabelul country)';

COMMENT ON TABLE public.region IS 'Tabel care contine regiunile corespunzatoare tarilor referite in model';


/******************** Add Table: public.region_city ************************/

/* Build Table Structure */
CREATE TABLE public.region_city
(
	region_id INTEGER NOT NULL,
	city_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.region_city ADD CONSTRAINT pkregion_city
	PRIMARY KEY (region_id, city_id);

/* Add Comments */
COMMENT ON COLUMN public.region_city.region_id IS 'Codul regiunii din care face parte un oras';

COMMENT ON COLUMN public.region_city.city_id IS 'Codul orasului asociat regiunii';

COMMENT ON TABLE public.region_city IS 'Tabel ce contine asocierile sintre orase si regiuni ';


/******************** Add Table: public.regiontype ************************/

/* Build Table Structure */
CREATE TABLE public.regiontype
(
id SERIAL,
	name TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.regiontype ADD CONSTRAINT pkregiontype
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.regiontype.id IS 'Identificatorul tipului de regiune';

COMMENT ON COLUMN public.regiontype.name IS 'Denumirea tipului de regiune';

COMMENT ON TABLE public.regiontype IS 'Tabel ce contine tipurile regiunilor corespunzatoare studiilor efectuate';


/******************** Add Table: public.sampling_procedure ************************/

/* Build Table Structure */
CREATE TABLE public.sampling_procedure
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.sampling_procedure ADD CONSTRAINT pksampling_procedure
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON TABLE public.sampling_procedure IS 'Tabel pentru procedurile de esantionare. (cf. DDI Codebook:) The type of sample and sample design used to select the survey respondents to represent the population. These may include one or more of the following: no sampling (total universe); quota sample; simple random sample; one-stage stratified or systematic random sample; one-stage cluster sample; multi-stage stratified random sample; quasi-random (e.g. random walk) sample; purposive selection/case studies; volunteer sample; convenience sample. ';


/******************** Add Table: public.scale ************************/

/* Build Table Structure */
CREATE TABLE public.scale
(
	item_id BIGINT NOT NULL,
	min_value_id BIGINT NOT NULL,
	max_value_id BIGINT NOT NULL,
	units SMALLINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.scale ADD CONSTRAINT pkscale
	PRIMARY KEY (item_id);

/* Add Comments */
COMMENT ON COLUMN public.scale.item_id IS 'Codul item-ului';

COMMENT ON COLUMN public.scale.min_value_id IS 'Codul valorii minime (refera atributul itemId din tabelul value) ';

COMMENT ON COLUMN public.scale.max_value_id IS 'Codul valorii maxime (refera atributul itemId din tabelul value) ';

COMMENT ON COLUMN public.scale.units IS 'Unitatea scalei respective';

COMMENT ON TABLE public.scale IS 'Tabel ce stocheaza elementele de tip scala ale variabilelor de selectie';


/******************** Add Table: public.selection_variable ************************/

/* Build Table Structure */
CREATE TABLE public.selection_variable
(
	variable_id BIGINT NOT NULL,
	min_count SMALLINT NOT NULL,
	max_count SMALLINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.selection_variable ADD CONSTRAINT pkselection_variable
	PRIMARY KEY (variable_id);

/* Add Comments */
COMMENT ON COLUMN public.selection_variable.variable_id IS 'Codul unei variabile de selectie din cadrul instantei identificate prin instance_id';

COMMENT ON COLUMN public.selection_variable.min_count IS 'Numarul minim de selectii asteptate ca raspuns';

COMMENT ON COLUMN public.selection_variable.max_count IS 'Numarul maxim de selectii asteptate ca raspuns';

COMMENT ON TABLE public.selection_variable IS 'Tabel ce contine informatii despre variabilele de selectie din instante';


/******************** Add Table: public.selection_variable_item ************************/

/* Build Table Structure */
CREATE TABLE public.selection_variable_item
(
	variable_id BIGINT NOT NULL,
	item_id BIGINT NOT NULL,
	order_of_item_in_variable INTEGER NOT NULL,
	response_card_file_id INTEGER NULL,
	frequency_value REAL NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.selection_variable_item ADD CONSTRAINT pkselection_variable_item
	PRIMARY KEY (variable_id, item_id);

/* Add Comments */
COMMENT ON COLUMN public.selection_variable_item.variable_id IS 'Codul unei variabile ce apare in cadrul instantei identificate prin atributul instance_id';

COMMENT ON COLUMN public.selection_variable_item.item_id IS 'Codul unui item de raspuns pentru variabila referita prin atributul variable_id';

COMMENT ON COLUMN public.selection_variable_item.order_of_item_in_variable IS 'Numarul de ordine al item-ului referit prin atributul item_id in cadrul variabilei identificate prin atributul variable_id';

COMMENT ON TABLE public.selection_variable_item IS 'Tabel ce contine elementele variabilelor de selectie din cadrul unei instante';

/* Add Indexes */
CREATE UNIQUE INDEX "selection_variable_item_order_Idx" ON public.selection_variable_item (order_of_item_in_variable, variable_id);


/******************** Add Table: public.series ************************/

/* Build Table Structure */
CREATE TABLE public.series
(
	catalog_id INTEGER NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.series ADD CONSTRAINT pkseries
	PRIMARY KEY (catalog_id);


/******************** Add Table: public.series_descr ************************/

/* Build Table Structure */
CREATE TABLE public.series_descr
(
	catalog_id INTEGER NOT NULL,
	lang_id INTEGER NOT NULL,
	notes TEXT NULL,
	title TEXT NOT NULL,
	subtitle TEXT NULL,
	alternative_title TEXT NULL,
	abstract TEXT NULL,
	time_covered TEXT NULL,
	geographic_coverage TEXT NULL
);

/* Add Primary Key */
ALTER TABLE public.series_descr ADD CONSTRAINT pkseries_descr
	PRIMARY KEY (catalog_id, lang_id);

/* Add Comments */
COMMENT ON COLUMN public.series_descr.time_covered IS 'The time period the data in the series refers to. This can correspond to different points/periods in time (if, for example, retrospective questions were asked).';


/******************** Add Table: public.series_topic ************************/

/* Build Table Structure */
CREATE TABLE public.series_topic
(
	catalog_id INTEGER NOT NULL,
	topic_id INTEGER NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.series_topic ADD CONSTRAINT pkseries_topic
	PRIMARY KEY (catalog_id, topic_id);


/******************** Add Table: public.setting ************************/

/* Build Table Structure */
CREATE TABLE public.setting
(
id SERIAL,
	name TEXT NOT NULL,
	setting_group_id INTEGER NOT NULL,
	description TEXT NULL,
	default_value TEXT NULL,
	value TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.setting ADD CONSTRAINT pksetting
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.setting.id IS 'Codul unei setari a aplicatiei';

COMMENT ON COLUMN public.setting.name IS 'Denumirea unei setari a aplicatiei';

COMMENT ON COLUMN public.setting.setting_group_id IS 'Grupul din care setarea face parte (refera atributul id din tabelul setting_group)';

COMMENT ON COLUMN public.setting.description IS 'Descrierea unei setari a aplicatiei';

COMMENT ON COLUMN public.setting.default_value IS 'Valoarea implicita a setarii';

COMMENT ON TABLE public.setting IS 'Tabel care contine setarile aplicatiei';


/******************** Add Table: public.setting_group ************************/

/* Build Table Structure */
CREATE TABLE public.setting_group
(
id SERIAL,
	name TEXT NOT NULL,
	parent_id INTEGER NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.setting_group ADD CONSTRAINT pksetting_group
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.setting_group.id IS 'Codul unui grup de setari ale aplicatiei';

COMMENT ON COLUMN public.setting_group.name IS 'Denumirea unui grup de setari ale aplicatiei';

COMMENT ON COLUMN public.setting_group.parent_id IS 'Codul grupului parinte al grupului de setari ale aplicatiei (refera atributul id al tabelului setting_group)';

COMMENT ON COLUMN public.setting_group.description IS 'Descrierea grupului de setari ale aplicatiei';

COMMENT ON TABLE public.setting_group IS 'Tabel care stocheaza grupurile de setari ale aplicatiei';


/******************** Add Table: public.skip ************************/

/* Build Table Structure */
CREATE TABLE public.skip
(
	variable_id BIGINT NOT NULL,
id BIGSERIAL,
	condition TEXT NOT NULL,
	next_variable_id BIGINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.skip ADD CONSTRAINT pkskip
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.skip.variable_id IS 'Identificatorul variabilei';

COMMENT ON COLUMN public.skip.id IS 'Identificatorul saltului, corespunzator variabilei referite prin atributul variable_id din cadrul instantei identificate prin atributul instance_id ';

COMMENT ON COLUMN public.skip.condition IS 'Conditia specificata ca text, care in urma evaluarii va confirma saltul';

COMMENT ON COLUMN public.skip.next_variable_id IS 'Identificatorul variabilei la care se va face salt';

COMMENT ON TABLE public.skip IS 'Tabel ce contine salturile care pot avea loc de la o variabila la alta';


/******************** Add Table: public.source ************************/

/* Build Table Structure */
CREATE TABLE public.source
(
id SERIAL,
	citation TEXT NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.source ADD CONSTRAINT pksource
	PRIMARY KEY (id);


/******************** Add Table: public.study ************************/

/* Build Table Structure */
CREATE TABLE public.study
(
id SERIAL,
	date_start DATE NULL,
	date_end DATE NULL,
	insertion_status INTEGER NOT NULL,
	added_by INTEGER NOT NULL,
	added TIMESTAMP NOT NULL,
	digitizable BOOL NOT NULL,
	anonymous_usage BOOL NOT NULL,
	unit_analysis_id INTEGER NOT NULL,
	version INTEGER NULL,
	raw_data BOOL NOT NULL,
	raw_metadata BOOL NOT NULL,
	time_meth_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study ADD CONSTRAINT pkstudy
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.study.id IS 'Codul studiului';

COMMENT ON COLUMN public.study.date_start IS 'Data de inceput a studiului';

COMMENT ON COLUMN public.study.date_end IS 'Data de final a studiului';

COMMENT ON COLUMN public.study.insertion_status IS 'Pasul din wizard-ul de introducere a metadatelor - din moment ce introducerea se face prin wizard, fiecare pas trebuie comis in baza de date; pana la finalizarea introducerii intregului studiu e nevoie sa stim ca ele au fost partial introduse.';

COMMENT ON COLUMN public.study.unit_analysis_id IS 'Codul unitatii de analiza specifice instantei (refera atributul id al tabelului unit_analysis)';

COMMENT ON TABLE public.study IS 'Tabel care stocheaza studiile desfasurate, ale caror informatii sunt prezente in baza de date ';


/******************** Add Table: public.study_data_source_type ************************/

/* Build Table Structure */
CREATE TABLE public.study_data_source_type
(
	study_id INTEGER NOT NULL,
	data_source_type_id INTEGER NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.study_data_source_type ADD CONSTRAINT pkstudy_data_source_type
	PRIMARY KEY (study_id, data_source_type_id);


/******************** Add Table: public.study_descr ************************/

/* Build Table Structure */
CREATE TABLE public.study_descr
(
	lang_id INTEGER NOT NULL,
	study_id INTEGER NOT NULL,
	abstract TEXT NULL,
	grant_details TEXT NULL,
	title TEXT NOT NULL,
	notes TEXT NULL,
	weighting TEXT NULL,
	research_instrument TEXT NULL,
	scope TEXT NULL,
	universe TEXT NULL,
	subtitle TEXT NULL,
	alternative_title TEXT NULL,
	original_title_language BOOL NOT NULL,
	time_covered TEXT NULL,
	geographic_coverage TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_descr ADD CONSTRAINT pkstudy_descr
	PRIMARY KEY (study_id, lang_id);

/* Add Comments */
COMMENT ON COLUMN public.study_descr.lang_id IS 'Limba in care este furnizat titlul identificat prin atributul id';

COMMENT ON COLUMN public.study_descr.study_id IS 'Codul studiului caruia ii corespunde titlul (refera atributul id din tabelul study)';

COMMENT ON COLUMN public.study_descr.original_title_language IS 'daca limbajul folosit in aceasta descriere a studiului este cel original (al producatorului)';

COMMENT ON COLUMN public.study_descr.time_covered IS 'The time period the data refers to. This can correspond to different points/periods in time (if, for example, retrospective questions were asked).';

COMMENT ON TABLE public.study_descr IS 'Tabel ce stocheaza informatii despre titlurile studiilor din baza de date';


/******************** Add Table: public.study_documents ************************/

/* Build Table Structure */
CREATE TABLE public.study_documents
(
	study_id INTEGER NOT NULL,
	document_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_documents ADD CONSTRAINT pkstudy_documents
	PRIMARY KEY (study_id, document_id);

/* Add Comments */
COMMENT ON COLUMN public.study_documents.study_id IS 'Codul studiului pentru care este stocat documentul specificat prin atributul document_id (refera atributul id din tabelul study)';

COMMENT ON COLUMN public.study_documents.document_id IS 'Codul unui document care este asociat studiului identificat prin atributul study_id (refera atributul id din tabelul documents)';

COMMENT ON TABLE public.study_documents IS 'Tabel ce implementeaza relatia many-to-many intre studiu si documente';


/******************** Add Table: public.study_file ************************/

/* Build Table Structure */
CREATE TABLE public.study_file
(
	study_id INTEGER NOT NULL,
	file_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_file ADD CONSTRAINT pkstudy_file
	PRIMARY KEY (study_id, file_id);

/* Add Comments */
COMMENT ON COLUMN public.study_file.study_id IS 'Codul studiului pentru care este stocat documentul specificat prin atributul document_id (refera atributul id din tabelul study)';

COMMENT ON COLUMN public.study_file.file_id IS 'Codul unui document care este asociat studiului identificat prin atributul study_id (refera atributul id din tabelul documents)';

COMMENT ON TABLE public.study_file IS 'Tabel ce contine asocierile dintre studii si documente (implementeaza relatia many-to-many intre tabelele study si documents)';


/******************** Add Table: public.study_keyword ************************/

/* Build Table Structure */
CREATE TABLE public.study_keyword
(
	study_id INTEGER NOT NULL,
	keyword_id INTEGER NOT NULL,
	added TIMESTAMP NOT NULL,
	added_by INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_keyword ADD CONSTRAINT pkstudy_keyword
	PRIMARY KEY (study_id, keyword_id, added_by);

/* Add Comments */
COMMENT ON COLUMN public.study_keyword.study_id IS 'Codul studiului caruia ii este asociat cuvantul cheie referit prin atributul keyword_id';

COMMENT ON COLUMN public.study_keyword.keyword_id IS 'Cuvantul cheie asociat studiului identificat prin atributul study_id';

COMMENT ON COLUMN public.study_keyword.added IS 'Momentul de timp la care a fost adaugata o asociere intre un cuvant cheie si un studiu';

COMMENT ON COLUMN public.study_keyword.added_by IS 'Utilizatorul care a adaugat asocierea dintre un studiu si un cuvant cheie';

COMMENT ON TABLE public.study_keyword IS 'Tabel ce stocheaza asocierile dintre studii si cuvintele cheie (implementeaza relatia many-to-many intre tabelele study si keyword)';


/******************** Add Table: public.study_org ************************/

/* Build Table Structure */
CREATE TABLE public.study_org
(
	org_id INTEGER NOT NULL,
	study_id INTEGER NOT NULL,
	assoctype_id INTEGER NOT NULL,
	assoc_details TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_org ADD CONSTRAINT pkstudy_org
	PRIMARY KEY (org_id, study_id, assoctype_id);

/* Add Comments */
COMMENT ON COLUMN public.study_org.org_id IS 'Codul organizatiei care se afla in relatia specificata prin atributul assoctype_id cu studiul identificat prin study_id (refera atributul id al tabelului org)';

COMMENT ON COLUMN public.study_org.study_id IS 'Codul studiului care se afla in relatia specificata prin atributul assoctype_id cu organizatia identificata prin org_id (refera atributul id al tabelului study)';

COMMENT ON COLUMN public.study_org.assoctype_id IS 'Codul tipului de asociere existent intre studiul identificat prin study_id si organizatia referita prin org_id (refera atributul id din tabelul study_org_assoc)';

COMMENT ON TABLE public.study_org IS 'Tabel ce stocheaza toate organizatiile care au legatura cu studiul: finantator, realizator, arhivar, etc. ( implementeaza relatia many-to-many intre studiu si organizatie)';


/******************** Add Table: public.study_org_assoc ************************/

/* Build Table Structure */
CREATE TABLE public.study_org_assoc
(
id SERIAL,
	assoc_name TEXT NOT NULL,
	assoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_org_assoc ADD CONSTRAINT pkstudy_org_assoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.study_org_assoc.id IS 'Codul unei asocieri care poate exista intre un studiu si o organizatie';

COMMENT ON COLUMN public.study_org_assoc.assoc_name IS 'Denumirea unei asocieri care poate exista intre un studiu si o organizatie (producator, finantator etc.)';

COMMENT ON COLUMN public.study_org_assoc.assoc_description IS 'Descrierea asocierii';

COMMENT ON TABLE public.study_org_assoc IS 'Tabel ce contine tipurile de asociere dintre studiu si organizatie (finantare, realizare etc.)';


/******************** Add Table: public.study_person ************************/

/* Build Table Structure */
CREATE TABLE public.study_person
(
	person_id INTEGER NOT NULL,
	study_id INTEGER NOT NULL,
	assoctype_id INTEGER NOT NULL,
	assoc_details TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_person ADD CONSTRAINT pkstudy_person
	PRIMARY KEY (person_id, study_id, assoctype_id);

/* Add Comments */
COMMENT ON COLUMN public.study_person.person_id IS 'Codul persoanei care este in relatia specificata prin atributul asoctype_id cu studiul referit prin atributul study_id (refera atributul id din tabelul person)';

COMMENT ON COLUMN public.study_person.study_id IS 'Codul studiului aflat in relatia identificata prin asoctype_id cu persoana referita prin atributul person_id (refera atributul id din tabelul study)';

COMMENT ON COLUMN public.study_person.assoctype_id IS 'Codul tipului de asociere existent intre persoana identificata prin atributul person_id si studiul referit prin atributul study_id';

COMMENT ON TABLE public.study_person IS 'Tabel ce contine asocierile dintre intre studiu si persoane: realizare, proiectare chestionar etc. (implementeaza relatia many-to-many intre tabelele person si study)';


/******************** Add Table: public.study_person_asoc ************************/

/* Build Table Structure */
CREATE TABLE public.study_person_asoc
(
id SERIAL,
	asoc_name VARCHAR(100) NOT NULL,
	asoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_person_asoc ADD CONSTRAINT pkstudy_person_asoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.study_person_asoc.id IS 'Codul unei asocieri care poate exista intre un studiu si o persoana';

COMMENT ON COLUMN public.study_person_asoc.asoc_name IS 'Numele unei asocieri care poate exista intre un studiu si o persoana';

COMMENT ON COLUMN public.study_person_asoc.asoc_description IS 'Descrierea asocierii';

COMMENT ON TABLE public.study_person_asoc IS 'Tabel ce contine tipurile de asocieri intre studiu si persoane';


/******************** Add Table: public.study_person_assoc ************************/

/* Build Table Structure */
CREATE TABLE public.study_person_assoc
(
id SERIAL,
	asoc_name TEXT NOT NULL,
	asoc_description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_person_assoc ADD CONSTRAINT pkstudy_person_assoc
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.study_person_assoc.id IS 'Codul unei asocieri care poate exista intre un studiu si o persoana';

COMMENT ON COLUMN public.study_person_assoc.asoc_name IS 'Numele unei asocieri care poate exista intre un studiu si o persoana';

COMMENT ON COLUMN public.study_person_assoc.asoc_description IS 'Descrierea asocierii';

COMMENT ON TABLE public.study_person_assoc IS 'Tabel ce contine tipurile de asocieri care pot exista intre studiu si persoane';


/******************** Add Table: public.study_source ************************/

/* Build Table Structure */
CREATE TABLE public.study_source
(
	study_id INTEGER NOT NULL,
	source_id INTEGER NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.study_source ADD CONSTRAINT pkstudy_source
	PRIMARY KEY (study_id, source_id);


/******************** Add Table: public.study_topic ************************/

/* Build Table Structure */
CREATE TABLE public.study_topic
(
	study_id INTEGER NOT NULL,
	topic_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.study_topic ADD CONSTRAINT pkstudy_topic
	PRIMARY KEY (study_id, topic_id);

/* Add Comments */
COMMENT ON COLUMN public.study_topic.study_id IS 'Codul studiului caruia ii este asociat topic-ul referit prin atributul topic_id';

COMMENT ON COLUMN public.study_topic.topic_id IS 'Codul unui topic asociat studiului identificat prin atributul study_id';

COMMENT ON TABLE public.study_topic IS 'Tabel ce stocheaza asocierile dintre studii si topic-uri (implementeaza relatia many-to-many intre tabelele study si topic)';


/******************** Add Table: public.suffix ************************/

/* Build Table Structure */
CREATE TABLE public.suffix
(
id SERIAL,
	name VARCHAR(50) NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.suffix ADD CONSTRAINT pksuffix
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.suffix.id IS 'Codul sufixului';

COMMENT ON COLUMN public.suffix.name IS 'Denumirea sufixului care poate fi utilizat pentru persoane (Jr, Sr, etc)';

COMMENT ON TABLE public.suffix IS 'Tabel ce contine sufixele care pot fi adaugate numelor persoanelor';


/******************** Add Table: public.target_group ************************/

/* Build Table Structure */
CREATE TABLE public.target_group
(
id SERIAL,
	name TEXT NOT NULL
);

/* Add Primary Key */
ALTER TABLE public.target_group ADD CONSTRAINT pktarget_group
	PRIMARY KEY (id);


/******************** Add Table: public.time_meth_type ************************/

/* Build Table Structure */
CREATE TABLE public.time_meth_type
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.time_meth_type ADD CONSTRAINT pktime_meth_type
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.time_meth_type.id IS 'Codul tipului de metoda temporala';

COMMENT ON COLUMN public.time_meth_type.name IS 'Denumirea tipului de metoda temporala (longitudinal, serie temporala, cross section etc.)';

COMMENT ON TABLE public.time_meth_type IS 'Tabel ce contine tipurile de metode temporale';


/******************** Add Table: public.topic ************************/

/* Build Table Structure */
CREATE TABLE public.topic
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL,
	parent_id INTEGER NULL,
	preferred_synonym_topic_id INTEGER NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.topic ADD CONSTRAINT pktopic
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.topic.id IS 'Codul unui topic ce poate fi asociat unui studiu sau unei instante ';

COMMENT ON COLUMN public.topic.name IS 'Numele unui topic ce poate fi asociat unui studiu sau unei instante';

COMMENT ON COLUMN public.topic.description IS 'Descrierea topic-ului ce poate fi asociat unui studiu sau unei instante';

COMMENT ON COLUMN public.topic.parent_id IS 'Codul topic-ului din dreapta, in ierarhia arorescenta creata pentru a mentine legaturile cu topic-urile referite ';

COMMENT ON TABLE public.topic IS 'Tabel ce contine topic-urile ce pot fi asociate unei instante sau unui studiu';


/******************** Add Table: public.translated_topic ************************/

/* Build Table Structure */
CREATE TABLE public.translated_topic
(
	lang_id INTEGER NOT NULL,
	topic_id INTEGER NOT NULL,
	translation TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.translated_topic ADD CONSTRAINT pktranslated_topic
	PRIMARY KEY (lang_id, topic_id);

/* Add Comments */
COMMENT ON COLUMN public.translated_topic.lang_id IS 'Codul limbii in care este tradus topic-ul referit prin atributul topic_id (refera atributul id din tabelul language)';

COMMENT ON COLUMN public.translated_topic.topic_id IS 'Codul topic-ului pentru care exista o traducere in limba identificata prin atributul language_id (refera atributul id din tabelul topic)';

COMMENT ON COLUMN public.translated_topic.translation IS 'Traducerea topic-ului referit prin atributul topic_id in limba identificata prin atributul language_id';

COMMENT ON TABLE public.translated_topic IS 'Tabel ce contine traducerile anumitor topic-uri din baza de date (implementeaza relatia many-to-many intre tabelele topic si language)';


/******************** Add Table: public.unit_analysis ************************/

/* Build Table Structure */
CREATE TABLE public.unit_analysis
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.unit_analysis ADD CONSTRAINT pkunit_analysis
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.unit_analysis.id IS 'Codul unitatii de analiza';

COMMENT ON COLUMN public.unit_analysis.name IS 'Denumirea unitatii de analiza (individ, familie, organizatie, gospodarie etc.)';

COMMENT ON COLUMN public.unit_analysis.description IS 'Descrierea unitatii de analiza';

COMMENT ON TABLE public.unit_analysis IS 'Tabel care stocheaza tipurile de unitati de analiza (individ, gospodarie, etc) ale instantelor';


/******************** Add Table: public.user_auth_log ************************/

/* Build Table Structure */
CREATE TABLE public.user_auth_log
(
	user_id INTEGER NOT NULL,
	auth_attempted_at TIMESTAMP DEFAULT now() NOT NULL,
	"action" VARCHAR(30) NULL,
	credential_provider TEXT NULL,
	credential_identifier TEXT NULL,
	error_message TEXT NULL,
id BIGSERIAL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.user_auth_log ADD CONSTRAINT pkuser_auth_log
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.user_auth_log.user_id IS 'Codul utilizatorului care a incercat sa se autentifice';

COMMENT ON COLUMN public.user_auth_log.auth_attempted_at IS 'Timpul la care incercarea de autentificare a avut loc';

COMMENT ON COLUMN public.user_auth_log."action" IS 'Actiunea login/logout/session expire (in functie de tipul de autentificare)';

COMMENT ON COLUMN public.user_auth_log.credential_provider IS 'Tipul de furnizor de informatii de acces pentru autentificarea respectiva';

COMMENT ON COLUMN public.user_auth_log.credential_identifier IS 'Detalii despre autentificarea utilizatorului';

COMMENT ON COLUMN public.user_auth_log.error_message IS 'Mesajul de eroare aparut ';

COMMENT ON TABLE public.user_auth_log IS 'Tabel ce stocheaza log-ul corespunzator procesului de autentificare a utilizatorilor';


/******************** Add Table: public.user_message ************************/

/* Build Table Structure */
CREATE TABLE public.user_message
(
id SERIAL,
	message TEXT NOT NULL,
	from_user_id INTEGER NOT NULL,
	to_user_id INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.user_message ADD CONSTRAINT pkuser_message
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.user_message.id IS 'Codul mesajului trimis de catre utilizatorul referit prin atributul user_id';

COMMENT ON COLUMN public.user_message.message IS 'Textul mesajului trimis de catre utilizatorul referit prin atributul user_id';

COMMENT ON COLUMN public.user_message.from_user_id IS 'Codul utilizatorului care transmite mesajul (refera atributul id din tabelul users)';

COMMENT ON COLUMN public.user_message.to_user_id IS 'Codul utilizatorului caruia ii este transmis mesajul (refera atributul id din tabelul users)';

COMMENT ON TABLE public.user_message IS 'Tabel care stocheaza mesajele trimise catre utilizatori';


/******************** Add Table: public.user_setting ************************/

/* Build Table Structure */
CREATE TABLE public.user_setting
(
id SERIAL,
	name TEXT NOT NULL,
	user_setting_group_id INTEGER NOT NULL,
	description TEXT NULL,
	default_value TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.user_setting ADD CONSTRAINT pkuser_setting
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.user_setting.id IS 'Codul unei setari asociate utilizatorilor';

COMMENT ON COLUMN public.user_setting.name IS 'Denumirea setarii';

COMMENT ON COLUMN public.user_setting.user_setting_group_id IS 'Grupul de setari din care aceasta face parte (refera atributul id al tabelului user_settings_group)';

COMMENT ON COLUMN public.user_setting.description IS 'Descrierea setarii';

COMMENT ON COLUMN public.user_setting.default_value IS 'Valoarea implicita a setarii';

COMMENT ON TABLE public.user_setting IS 'Tabel care stocheaza setarile pentru utilizatori';


/******************** Add Table: public.user_setting_group ************************/

/* Build Table Structure */
CREATE TABLE public.user_setting_group
(
id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.user_setting_group ADD CONSTRAINT pkuser_setting_group
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.user_setting_group.id IS 'Codul grupului de setari';

COMMENT ON COLUMN public.user_setting_group.name IS 'Denumirea grupului de setari';

COMMENT ON COLUMN public.user_setting_group.description IS 'Descrierea grupului de setari';

COMMENT ON TABLE public.user_setting_group IS 'Tabel care stocheaza grupurile de setari pentru utilizatori';


/******************** Add Table: public.user_setting_value ************************/

/* Build Table Structure */
CREATE TABLE public.user_setting_value
(
	user_setting_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	value TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.user_setting_value ADD CONSTRAINT pkuser_setting_value
	PRIMARY KEY (user_setting_id, user_id);

/* Add Comments */
COMMENT ON COLUMN public.user_setting_value.user_setting_id IS 'Codul setarii corespunzatoare unui utilizator (refera atributul id al tabelului user_settings)';

COMMENT ON COLUMN public.user_setting_value.user_id IS 'Codul utilizatorului caruia ii apartine setarea (refera atributul id al tabelului users)';

COMMENT ON COLUMN public.user_setting_value.value IS 'Valoarea setarii respective';

COMMENT ON TABLE public.user_setting_value IS 'Tabel care stocheaza valorile setarilor utilizatorului';


/******************** Add Table: public.users ************************/

/* Build Table Structure */
CREATE TABLE public.users
(
id SERIAL,
	username VARCHAR(64) NOT NULL,
	password VARCHAR(64) NOT NULL,
	enabled BOOL NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.users ADD CONSTRAINT pkusers
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.users.id IS 'Codul utilizatorului aplicatiei';

COMMENT ON COLUMN public.users.username IS 'Furnizorul de informatii de acces pentru utilizatorul respectiv';

COMMENT ON COLUMN public.users.password IS 'Parola utilizatorului (codificata SHA-256)';

COMMENT ON COLUMN public.users.enabled IS 'Daca utilizatorul este activ sau nu';

COMMENT ON TABLE public.users IS 'Tabel ce contine utilizatorii aplicatiei';

/* Add Indexes */
CREATE UNIQUE INDEX "rodauser_username_Idx" ON public.users (username);


/******************** Add Table: public.value ************************/

/* Build Table Structure */
CREATE TABLE public.value
(
	item_id BIGINT NOT NULL,
	value INTEGER NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.value ADD CONSTRAINT pkvalue
	PRIMARY KEY (item_id);

/* Add Comments */
COMMENT ON COLUMN public.value.item_id IS 'Codul elementului unei variabile de selectie';

COMMENT ON TABLE public.value IS 'Tabel ce stocheaza valorile posibile ale item-urilor variabilelor de selectie';


/******************** Add Table: public.vargroup ************************/

/* Build Table Structure */
CREATE TABLE public.vargroup
(
id BIGSERIAL,
	name TEXT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.vargroup ADD CONSTRAINT pkvargroup
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.vargroup.id IS 'Identificatorul';

COMMENT ON COLUMN public.vargroup.name IS 'Denumirea grupului';

COMMENT ON TABLE public.vargroup IS 'Tabel pentru definirea gruparilor de variabile (pentru o organizare mai buna a lor)';


/******************** Add Table: public.variable ************************/

/* Build Table Structure */
CREATE TABLE public.variable
(
id BIGSERIAL,
	label TEXT NOT NULL,
	type SMALLINT NOT NULL,
	operator_instructions TEXT NULL,
	file_id INTEGER NULL,
	variable_type SMALLINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.variable ADD CONSTRAINT pkvariable
	PRIMARY KEY (id);

/* Add Comments */
COMMENT ON COLUMN public.variable.id IS 'Codul variabilei in cadrul instantei';

COMMENT ON COLUMN public.variable.label IS 'Reprezentarea textuala a variabilei (numele)';

COMMENT ON COLUMN public.variable.type IS 'Tipul de variabila (constanta a unei enumeratii)';

COMMENT ON COLUMN public.variable.operator_instructions IS 'Text care informeaza operatorul ce chestioneaza asupra unor actiuni pe care trebuie sa le faca atunci cand ajunge la variabila aceasta';

COMMENT ON COLUMN public.variable.file_id IS 'Fisierul din care provine variabila';

COMMENT ON COLUMN public.variable.variable_type IS 'Tipul Variabilei: 0=edited, 1=edited_number, 2=selection';

COMMENT ON TABLE public.variable IS 'Tabel care stocheaza variabilele din cadrul instantelor';


/******************** Add Table: public.variable_vargroup ************************/

/* Build Table Structure */
CREATE TABLE public.variable_vargroup
(
	variable_id BIGINT NOT NULL,
	vargroup_id BIGINT NOT NULL
) WITHOUT OIDS;

/* Add Primary Key */
ALTER TABLE public.variable_vargroup ADD CONSTRAINT pkvariable_vargroup
	PRIMARY KEY (vargroup_id, variable_id);

/* Add Comments */
COMMENT ON COLUMN public.variable_vargroup.variable_id IS 'Codul variabilei in cadrul instantei instance_id';

COMMENT ON COLUMN public.variable_vargroup.vargroup_id IS 'Codul grupului caruia ii apartine variabila variable_id din instanta instance_id';

COMMENT ON TABLE public.variable_vargroup IS 'Tabel ce asociaza variabile unor grupuri in cadrul instantelor';





/************ Add Foreign Keys ***************/

/* Add Foreign Key: fk_acl_entry_acl_object_identity */
ALTER TABLE public.acl_entry ADD CONSTRAINT fk_acl_entry_acl_object_identity
	FOREIGN KEY (acl_object_identity) REFERENCES public.acl_object_identity (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_acl_entry_acl_sid */
ALTER TABLE public.acl_entry ADD CONSTRAINT fk_acl_entry_acl_sid
	FOREIGN KEY (sid) REFERENCES public.acl_sid (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_acl_object_identity_acl_class */
ALTER TABLE public.acl_object_identity ADD CONSTRAINT fk_acl_object_identity_acl_class
	FOREIGN KEY (object_id_class) REFERENCES public.acl_class (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_acl_object_identity_acl_object_identity */
ALTER TABLE public.acl_object_identity ADD CONSTRAINT fk_acl_object_identity_acl_object_identity
	FOREIGN KEY (parent_object) REFERENCES public.acl_object_identity (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_acl_object_identity_acl_sid */
ALTER TABLE public.acl_object_identity ADD CONSTRAINT fk_acl_object_identity_acl_sid
	FOREIGN KEY (owner_sid) REFERENCES public.acl_sid (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_address_city */
ALTER TABLE public.address ADD CONSTRAINT fk_address_city
	FOREIGN KEY (city_id) REFERENCES public.city (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_authorities_rodauser */
ALTER TABLE public.authorities ADD CONSTRAINT fk_authorities_rodauser
	FOREIGN KEY (username) REFERENCES public.users (username)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_catalog_catalog */
ALTER TABLE public.catalog ADD CONSTRAINT fk_catalog_catalog
	FOREIGN KEY (parent_id) REFERENCES public.catalog (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_catalog_users */
ALTER TABLE public.catalog ADD CONSTRAINT fk_catalog_users
	FOREIGN KEY (owner) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_catalog_study_catalog */
ALTER TABLE public.catalog_study ADD CONSTRAINT fk_catalog_study_catalog
	FOREIGN KEY (catalog_id) REFERENCES public.catalog (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_catalog_study_study */
ALTER TABLE public.catalog_study ADD CONSTRAINT fk_catalog_study_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_city_country */
ALTER TABLE public.city ADD CONSTRAINT fk_city_country
	FOREIGN KEY (country_id) REFERENCES public.country (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_files_cms_folders */
ALTER TABLE public.cms_file ADD CONSTRAINT fk_cms_files_cms_folders
	FOREIGN KEY (cms_folder_id) REFERENCES public.cms_folder (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_folder_cms_folder */
ALTER TABLE public.cms_folder ADD CONSTRAINT fk_cms_folder_cms_folder
	FOREIGN KEY (parent_id) REFERENCES public.cms_folder (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_layout_cms_layout_group */
ALTER TABLE public.cms_layout ADD CONSTRAINT fk_cms_layout_cms_layout_group
	FOREIGN KEY (cms_layout_group_id) REFERENCES public.cms_layout_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_layout_group_cms_layout_group */
ALTER TABLE public.cms_layout_group ADD CONSTRAINT fk_cms_layout_group_cms_layout_group
	FOREIGN KEY (parent_id) REFERENCES public.cms_layout_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_page_cms_layout */
ALTER TABLE public.cms_page ADD CONSTRAINT fk_cms_page_cms_layout
	FOREIGN KEY (cms_layout_id) REFERENCES public.cms_layout (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_page_cms_page_type */
ALTER TABLE public.cms_page ADD CONSTRAINT fk_cms_page_cms_page_type
	FOREIGN KEY (cms_page_type_id) REFERENCES public.cms_page_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_page_content_cms_page */
ALTER TABLE public.cms_page_content ADD CONSTRAINT fk_cms_page_content_cms_page
	FOREIGN KEY (cms_page_id) REFERENCES public.cms_page (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_snippet_cms_snippet_group */
ALTER TABLE public.cms_snippet ADD CONSTRAINT fk_cms_snippet_cms_snippet_group
	FOREIGN KEY (cms_snippet_group_id) REFERENCES public.cms_snippet_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_cms_snippet_group_cms_snippet_group */
ALTER TABLE public.cms_snippet_group ADD CONSTRAINT fk_cms_snippet_group_cms_snippet_group
	FOREIGN KEY (parent_id) REFERENCES public.cms_snippet_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_Concept_Variables_Concepts */
ALTER TABLE public.concept_variable ADD CONSTRAINT "fk_Concept_Variables_Concepts"
	FOREIGN KEY (concept_id) REFERENCES public.concept (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_concept_variable_variable */
ALTER TABLE public.concept_variable ADD CONSTRAINT fk_concept_variable_variable
	FOREIGN KEY (variable_id) REFERENCES public.variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_person */
ALTER TABLE public.form ADD CONSTRAINT fk_form_person
	FOREIGN KEY (operator_id) REFERENCES public.person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_custom_number_var_custom_variable */
ALTER TABLE public.form_edited_number_var ADD CONSTRAINT fk_form_custom_number_var_custom_variable
	FOREIGN KEY (variable_id) REFERENCES public.variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_edited_number_var_form */
ALTER TABLE public.form_edited_number_var ADD CONSTRAINT fk_form_edited_number_var_form
	FOREIGN KEY (form_id) REFERENCES public.form (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_edited_text_var_form */
ALTER TABLE public.form_edited_text_var ADD CONSTRAINT fk_form_edited_text_var_form
	FOREIGN KEY (form_id) REFERENCES public.form (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_edited_text_var_variable */
ALTER TABLE public.form_edited_text_var ADD CONSTRAINT fk_form_edited_text_var_variable
	FOREIGN KEY (variable_id) REFERENCES public.variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_selection_var_form */
ALTER TABLE public.form_selection_var ADD CONSTRAINT fk_form_selection_var_form
	FOREIGN KEY (form_id) REFERENCES public.form (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_form_selection_var_selection_variable_item */
ALTER TABLE public.form_selection_var ADD CONSTRAINT fk_form_selection_var_selection_variable_item
	FOREIGN KEY (variable_id, item_id) REFERENCES public.selection_variable_item (variable_id, item_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_study */
ALTER TABLE public.instance ADD CONSTRAINT fk_instance_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_user */
ALTER TABLE public.instance ADD CONSTRAINT fk_instance_user
	FOREIGN KEY (added_by) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_descr_instance */
ALTER TABLE public.instance_descr ADD CONSTRAINT fk_instance_descr_instance
	FOREIGN KEY (instance_id) REFERENCES public.instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_descr_lang */
ALTER TABLE public.instance_descr ADD CONSTRAINT fk_instance_descr_lang
	FOREIGN KEY (lang_id) REFERENCES public.lang (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_documents_documents */
ALTER TABLE public.instance_documents ADD CONSTRAINT fk_instance_documents_documents
	FOREIGN KEY (document_id) REFERENCES public.file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_documents_instance */
ALTER TABLE public.instance_documents ADD CONSTRAINT fk_instance_documents_instance
	FOREIGN KEY (instance_id) REFERENCES public.instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_form_form */
ALTER TABLE public.instance_form ADD CONSTRAINT fk_instance_form_form
	FOREIGN KEY (form_id) REFERENCES public.form (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_form_instance */
ALTER TABLE public.instance_form ADD CONSTRAINT fk_instance_form_instance
	FOREIGN KEY (instance_id) REFERENCES public.instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_org_instance */
ALTER TABLE public.instance_org ADD CONSTRAINT fk_instance_org_instance
	FOREIGN KEY (instance_id) REFERENCES public.instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_org_instance_org_assoc */
ALTER TABLE public.instance_org ADD CONSTRAINT fk_instance_org_instance_org_assoc
	FOREIGN KEY (assoc_type_id) REFERENCES public.instance_org_assoc (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_org_org */
ALTER TABLE public.instance_org ADD CONSTRAINT fk_instance_org_org
	FOREIGN KEY (org_id) REFERENCES public.org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_person_instance */
ALTER TABLE public.instance_person ADD CONSTRAINT fk_instance_person_instance
	FOREIGN KEY (instance_id) REFERENCES public.instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_person_instance_person_assoc */
ALTER TABLE public.instance_person ADD CONSTRAINT fk_instance_person_instance_person_assoc
	FOREIGN KEY (assoc_type_id) REFERENCES public.instance_person_assoc (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_person_person */
ALTER TABLE public.instance_person ADD CONSTRAINT fk_instance_person_person
	FOREIGN KEY (person_id) REFERENCES public.person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_dataset_right_target_group_dataset_right */
ALTER TABLE public.instance_right_target_group ADD CONSTRAINT fk_dataset_right_target_group_dataset_right
	FOREIGN KEY (instance_right_id) REFERENCES public.instance_right (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_dataset_right_target_group_dataset_right_value */
ALTER TABLE public.instance_right_target_group ADD CONSTRAINT fk_dataset_right_target_group_dataset_right_value
	FOREIGN KEY (instance_right_value_id) REFERENCES public.instance_right_value (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_dataset_right_target_group_instance */
ALTER TABLE public.instance_right_target_group ADD CONSTRAINT fk_dataset_right_target_group_instance
	FOREIGN KEY (instance_id) REFERENCES public.instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_dataset_right_target_group_target_group */
ALTER TABLE public.instance_right_target_group ADD CONSTRAINT fk_dataset_right_target_group_target_group
	FOREIGN KEY (target_group_id) REFERENCES public.target_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_dataset_right_value_dataset_right */
ALTER TABLE public.instance_right_value ADD CONSTRAINT fk_dataset_right_value_dataset_right
	FOREIGN KEY (instance_right_id) REFERENCES public.instance_right (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_sampling_procedure_sampling_procedure */
ALTER TABLE public.instance_sampling_procedure ADD CONSTRAINT fk_instance_sampling_procedure_sampling_procedure
	FOREIGN KEY (sampling_procedure_id) REFERENCES public.sampling_procedure (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_sampling_procedure_study */
ALTER TABLE public.instance_sampling_procedure ADD CONSTRAINT fk_instance_sampling_procedure_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_variable_instance */
ALTER TABLE public.instance_variable ADD CONSTRAINT fk_instance_variable_instance
	FOREIGN KEY (instance_id) REFERENCES public.instance (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_variable_variable */
ALTER TABLE public.instance_variable ADD CONSTRAINT fk_instance_variable_variable
	FOREIGN KEY (variable_id) REFERENCES public.variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_meth_coll_type_collection_model_type */
ALTER TABLE public.meth_coll_type ADD CONSTRAINT fk_meth_coll_type_collection_model_type
	FOREIGN KEY (collection_model_id) REFERENCES public.collection_model_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_meth_coll_type_study */
ALTER TABLE public.meth_coll_type ADD CONSTRAINT fk_meth_coll_type_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_org_prefix */
ALTER TABLE public.org ADD CONSTRAINT fk_org_org_prefix
	FOREIGN KEY (org_prefix_id) REFERENCES public.org_prefix (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_org_sufix */
ALTER TABLE public.org ADD CONSTRAINT fk_org_org_sufix
	FOREIGN KEY (org_sufix_id) REFERENCES public.org_sufix (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_address_address */
ALTER TABLE public.org_address ADD CONSTRAINT fk_org_address_address
	FOREIGN KEY (address_id) REFERENCES public.address (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_address_org */
ALTER TABLE public.org_address ADD CONSTRAINT fk_org_address_org
	FOREIGN KEY (org_id) REFERENCES public.org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_email_email */
ALTER TABLE public.org_email ADD CONSTRAINT fk_org_email_email
	FOREIGN KEY (email_id) REFERENCES public.email (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_email_org */
ALTER TABLE public.org_email ADD CONSTRAINT fk_org_email_org
	FOREIGN KEY (org_id) REFERENCES public.org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_internet_internet */
ALTER TABLE public.org_internet ADD CONSTRAINT fk_org_internet_internet
	FOREIGN KEY (internet_id) REFERENCES public.internet (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_internet_org */
ALTER TABLE public.org_internet ADD CONSTRAINT fk_org_internet_org
	FOREIGN KEY (org_id) REFERENCES public.org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_phone_org */
ALTER TABLE public.org_phone ADD CONSTRAINT fk_org_phone_org
	FOREIGN KEY (org_id) REFERENCES public.org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_phone_phone */
ALTER TABLE public.org_phone ADD CONSTRAINT fk_org_phone_phone
	FOREIGN KEY (phone_id) REFERENCES public.phone (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_2_relations_org */
ALTER TABLE public.org_relations ADD CONSTRAINT fk_org_2_relations_org
	FOREIGN KEY (org_2_id) REFERENCES public.org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_relations_org */
ALTER TABLE public.org_relations ADD CONSTRAINT fk_org_relations_org
	FOREIGN KEY (org_1_id) REFERENCES public.org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_org_relations_org_relation_type */
ALTER TABLE public.org_relations ADD CONSTRAINT fk_org_relations_org_relation_type
	FOREIGN KEY (org_relation_type_id) REFERENCES public.org_relation_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_other_statistic_variable */
ALTER TABLE public.other_statistic ADD CONSTRAINT fk_other_statistic_variable
	FOREIGN KEY (variable_id) REFERENCES public.variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_prefix */
ALTER TABLE public.person ADD CONSTRAINT fk_person_prefix
	FOREIGN KEY (prefix_id) REFERENCES public.prefix (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_suffix */
ALTER TABLE public.person ADD CONSTRAINT fk_person_suffix
	FOREIGN KEY (suffix_id) REFERENCES public.suffix (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_address_address */
ALTER TABLE public.person_address ADD CONSTRAINT fk_person_address_address
	FOREIGN KEY (address_id) REFERENCES public.address (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_address_person */
ALTER TABLE public.person_address ADD CONSTRAINT fk_person_address_person
	FOREIGN KEY (person_id) REFERENCES public.person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_email_email */
ALTER TABLE public.person_email ADD CONSTRAINT fk_person_email_email
	FOREIGN KEY (email_id) REFERENCES public.email (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_email_person */
ALTER TABLE public.person_email ADD CONSTRAINT fk_person_email_person
	FOREIGN KEY (person_id) REFERENCES public.person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_internet_internet */
ALTER TABLE public.person_internet ADD CONSTRAINT fk_person_internet_internet
	FOREIGN KEY (internet_id) REFERENCES public.internet (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_internet_person */
ALTER TABLE public.person_internet ADD CONSTRAINT fk_person_internet_person
	FOREIGN KEY (person_id) REFERENCES public.person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_links_person */
ALTER TABLE public.person_links ADD CONSTRAINT fk_person_links_person
	FOREIGN KEY (person_id) REFERENCES public.person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_links_users */
ALTER TABLE public.person_links ADD CONSTRAINT fk_person_links_users
	FOREIGN KEY (user_id) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_links_users2 */
ALTER TABLE public.person_links ADD CONSTRAINT fk_person_links_users2
	FOREIGN KEY (status_by) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_org_org */
ALTER TABLE public.person_org ADD CONSTRAINT fk_person_org_org
	FOREIGN KEY (org_id) REFERENCES public.org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_org_person */
ALTER TABLE public.person_org ADD CONSTRAINT fk_person_org_person
	FOREIGN KEY (person_id) REFERENCES public.person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_org_person_role */
ALTER TABLE public.person_org ADD CONSTRAINT fk_person_org_person_role
	FOREIGN KEY (role_id) REFERENCES public.person_role (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_phone_person */
ALTER TABLE public.person_phone ADD CONSTRAINT fk_person_phone_person
	FOREIGN KEY (person_id) REFERENCES public.person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_phone_phone */
ALTER TABLE public.person_phone ADD CONSTRAINT fk_person_phone_phone
	FOREIGN KEY (phone_id) REFERENCES public.phone (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_region_country */
ALTER TABLE public.region ADD CONSTRAINT fk_region_country
	FOREIGN KEY (country_id) REFERENCES public.country (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_region_region_type */
ALTER TABLE public.region ADD CONSTRAINT fk_region_region_type
	FOREIGN KEY (regiontype_id) REFERENCES public.regiontype (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_region_city_city */
ALTER TABLE public.region_city ADD CONSTRAINT fk_region_city_city
	FOREIGN KEY (city_id) REFERENCES public.city (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_region_city_region */
ALTER TABLE public.region_city ADD CONSTRAINT fk_region_city_region
	FOREIGN KEY (region_id) REFERENCES public.region (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_scale_item */
ALTER TABLE public.scale ADD CONSTRAINT fk_scale_item
	FOREIGN KEY (item_id) REFERENCES public.item (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_scale_max_value */
ALTER TABLE public.scale ADD CONSTRAINT fk_scale_max_value
	FOREIGN KEY (max_value_id) REFERENCES public.value (item_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_scale_min_value */
ALTER TABLE public.scale ADD CONSTRAINT fk_scale_min_value
	FOREIGN KEY (min_value_id) REFERENCES public.value (item_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_selection_variable_variable */
ALTER TABLE public.selection_variable ADD CONSTRAINT fk_selection_variable_variable
	FOREIGN KEY (variable_id) REFERENCES public.variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_selection_variable_item_documents */
ALTER TABLE public.selection_variable_item ADD CONSTRAINT fk_selection_variable_item_documents
	FOREIGN KEY (response_card_file_id) REFERENCES public.file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_selection_variable_item_item */
ALTER TABLE public.selection_variable_item ADD CONSTRAINT fk_selection_variable_item_item
	FOREIGN KEY (item_id) REFERENCES public.item (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_selection_variable_item_selection_variable */
ALTER TABLE public.selection_variable_item ADD CONSTRAINT fk_selection_variable_item_selection_variable
	FOREIGN KEY (variable_id) REFERENCES public.selection_variable (variable_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_series_catalog */
ALTER TABLE public.series ADD CONSTRAINT fk_series_catalog
	FOREIGN KEY (catalog_id) REFERENCES public.catalog (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_series_descr_lang */
ALTER TABLE public.series_descr ADD CONSTRAINT fk_series_descr_lang
	FOREIGN KEY (lang_id) REFERENCES public.lang (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_series_descr_series */
ALTER TABLE public.series_descr ADD CONSTRAINT fk_series_descr_series
	FOREIGN KEY (catalog_id) REFERENCES public.series (catalog_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_series_topic_series */
ALTER TABLE public.series_topic ADD CONSTRAINT fk_series_topic_series
	FOREIGN KEY (catalog_id) REFERENCES public.series (catalog_id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_series_topic_topic */
ALTER TABLE public.series_topic ADD CONSTRAINT fk_series_topic_topic
	FOREIGN KEY (topic_id) REFERENCES public.topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_setting_setting_group */
ALTER TABLE public.setting ADD CONSTRAINT fk_setting_setting_group
	FOREIGN KEY (setting_group_id) REFERENCES public.setting_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_setting_group_setting_group */
ALTER TABLE public.setting_group ADD CONSTRAINT fk_setting_group_setting_group
	FOREIGN KEY (parent_id) REFERENCES public.setting_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_skip_next_variable */
ALTER TABLE public.skip ADD CONSTRAINT fk_skip_next_variable
	FOREIGN KEY (next_variable_id) REFERENCES public.variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_skip_variable */
ALTER TABLE public.skip ADD CONSTRAINT fk_skip_variable
	FOREIGN KEY (variable_id) REFERENCES public.variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_time_meth_type */
ALTER TABLE public.study ADD CONSTRAINT fk_study_time_meth_type
	FOREIGN KEY (id) REFERENCES public.time_meth_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_unit_analysis */
ALTER TABLE public.study ADD CONSTRAINT fk_study_unit_analysis
	FOREIGN KEY (unit_analysis_id) REFERENCES public.unit_analysis (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_user */
ALTER TABLE public.study ADD CONSTRAINT fk_study_user
	FOREIGN KEY (added_by) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_data_source_type_data_source_type */
ALTER TABLE public.study_data_source_type ADD CONSTRAINT fk_study_data_source_type_data_source_type
	FOREIGN KEY (data_source_type_id) REFERENCES public.data_source_type (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_data_source_type_study */
ALTER TABLE public.study_data_source_type ADD CONSTRAINT fk_study_data_source_type_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_descr_lang */
ALTER TABLE public.study_descr ADD CONSTRAINT fk_study_descr_lang
	FOREIGN KEY (lang_id) REFERENCES public.lang (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_title_study */
ALTER TABLE public.study_descr ADD CONSTRAINT fk_title_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_documents_documents */
ALTER TABLE public.study_file ADD CONSTRAINT fk_study_documents_documents
	FOREIGN KEY (file_id) REFERENCES public.file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_documents_study */
ALTER TABLE public.study_file ADD CONSTRAINT fk_study_documents_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_keyword_keyword */
ALTER TABLE public.study_keyword ADD CONSTRAINT fk_study_keyword_keyword
	FOREIGN KEY (keyword_id) REFERENCES public.keyword (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_keyword_study */
ALTER TABLE public.study_keyword ADD CONSTRAINT fk_study_keyword_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_keyword_user */
ALTER TABLE public.study_keyword ADD CONSTRAINT fk_study_keyword_user
	FOREIGN KEY (added_by) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_studiu_org_org */
ALTER TABLE public.study_org ADD CONSTRAINT fk_studiu_org_org
	FOREIGN KEY (org_id) REFERENCES public.org (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_studiu_org_studiu_org_asoc */
ALTER TABLE public.study_org ADD CONSTRAINT fk_studiu_org_studiu_org_asoc
	FOREIGN KEY (assoctype_id) REFERENCES public.study_org_assoc (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_studiu_org_study */
ALTER TABLE public.study_org ADD CONSTRAINT fk_studiu_org_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_person_person */
ALTER TABLE public.study_person ADD CONSTRAINT fk_study_person_person
	FOREIGN KEY (person_id) REFERENCES public.person (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_person_studiu_person_asoc */
ALTER TABLE public.study_person ADD CONSTRAINT fk_study_person_studiu_person_asoc
	FOREIGN KEY (assoctype_id) REFERENCES public.study_person_assoc (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_person_study */
ALTER TABLE public.study_person ADD CONSTRAINT fk_study_person_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_source_source */
ALTER TABLE public.study_source ADD CONSTRAINT fk_study_source_source
	FOREIGN KEY (source_id) REFERENCES public.source (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_source_study */
ALTER TABLE public.study_source ADD CONSTRAINT fk_study_source_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_topic_study */
ALTER TABLE public.study_topic ADD CONSTRAINT fk_study_topic_study
	FOREIGN KEY (study_id) REFERENCES public.study (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_study_topic_topic */
ALTER TABLE public.study_topic ADD CONSTRAINT fk_study_topic_topic
	FOREIGN KEY (topic_id) REFERENCES public.topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_topic_topic_parent */
ALTER TABLE public.topic ADD CONSTRAINT fk_topic_topic_parent
	FOREIGN KEY (parent_id) REFERENCES public.topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_topic_topic_preferred */
ALTER TABLE public.topic ADD CONSTRAINT fk_topic_topic_preferred
	FOREIGN KEY (preferred_synonym_topic_id) REFERENCES public.topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_translated_topic_lang */
ALTER TABLE public.translated_topic ADD CONSTRAINT fk_translated_topic_lang
	FOREIGN KEY (lang_id) REFERENCES public.lang (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_translated_topic_topic */
ALTER TABLE public.translated_topic ADD CONSTRAINT fk_translated_topic_topic
	FOREIGN KEY (topic_id) REFERENCES public.topic (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_auth_log_users */
ALTER TABLE public.user_auth_log ADD CONSTRAINT fk_user_auth_log_users
	FOREIGN KEY (user_id) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_message_user */
ALTER TABLE public.user_message ADD CONSTRAINT fk_user_message_user
	FOREIGN KEY (to_user_id) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_messages_users */
ALTER TABLE public.user_message ADD CONSTRAINT fk_user_messages_users
	FOREIGN KEY (from_user_id) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_settings_user_settings_group */
ALTER TABLE public.user_setting ADD CONSTRAINT fk_user_settings_user_settings_group
	FOREIGN KEY (user_setting_group_id) REFERENCES public.user_setting_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_setting_value_user_settings */
ALTER TABLE public.user_setting_value ADD CONSTRAINT fk_user_setting_value_user_settings
	FOREIGN KEY (user_setting_id) REFERENCES public.user_setting (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_user_setting_value_users */
ALTER TABLE public.user_setting_value ADD CONSTRAINT fk_user_setting_value_users
	FOREIGN KEY (user_id) REFERENCES public.users (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_value_item */
ALTER TABLE public.value ADD CONSTRAINT fk_value_item
	FOREIGN KEY (item_id) REFERENCES public.item (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_variable_documents */
ALTER TABLE public.variable ADD CONSTRAINT fk_variable_documents
	FOREIGN KEY (file_id) REFERENCES public.file (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_var_group_variable */
ALTER TABLE public.variable_vargroup ADD CONSTRAINT fk_instance_var_group_variable
	FOREIGN KEY (variable_id) REFERENCES public.variable (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_instance_var_group_variable_group */
ALTER TABLE public.variable_vargroup ADD CONSTRAINT fk_instance_var_group_variable_group
	FOREIGN KEY (vargroup_id) REFERENCES public.vargroup (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;