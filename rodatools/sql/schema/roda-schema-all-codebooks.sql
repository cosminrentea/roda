--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: Data; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE "Data" (
    "InstanceId" integer NOT NULL,
    "ValueId" bigint NOT NULL,
    "VariableId" integer NOT NULL
);


ALTER TABLE public."Data" OWNER TO roda;

--
-- Name: Selected_Answers; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE "Selected_Answers" (
    "QuestionnaireId" integer NOT NULL,
    "ValueId" integer NOT NULL,
    "VarInStudyId" integer NOT NULL,
    "editedAnswer" character varying(255) NOT NULL
);


ALTER TABLE public."Selected_Answers" OWNER TO roda;

--
-- Name: Variable_Sequences; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE "Variable_Sequences" (
    "Order" integer NOT NULL,
    "VariableId" integer NOT NULL
);


ALTER TABLE public."Variable_Sequences" OWNER TO roda;

--
-- Name: abstract; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE abstract (
    id_ bigint NOT NULL,
    content text,
    date character varying(255),
    id character varying(255),
    studyinfoandscope_id bigint
);


ALTER TABLE public.abstract OWNER TO roda;

--
-- Name: accessplace; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE accessplace (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    uri character varying(255),
    datasetavailability_id bigint
);


ALTER TABLE public.accessplace OWNER TO roda;

--
-- Name: address; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE address (
    id integer NOT NULL,
    address1 character varying(250) NOT NULL,
    address2 character varying(250) NOT NULL,
    entity_id integer NOT NULL,
    entity_type integer NOT NULL,
    postal_code character varying(50) NOT NULL,
    sector character varying(50) NOT NULL,
    city_id integer NOT NULL,
    country_id integer NOT NULL
);


ALTER TABLE public.address OWNER TO roda;

--
-- Name: alternativetitle; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE alternativetitle (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    titlestatement_id bigint
);


ALTER TABLE public.alternativetitle OWNER TO roda;

--
-- Name: answer; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE answer (
    form_id integer NOT NULL,
    instance_id integer NOT NULL,
    variable_id integer NOT NULL
);


ALTER TABLE public.answer OWNER TO roda;

--
-- Name: archivewherestudywasoriginallystored; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE archivewherestudywasoriginallystored (
    id_ bigint NOT NULL,
    content text,
    id character varying(255)
);


ALTER TABLE public.archivewherestudywasoriginallystored OWNER TO roda;

--
-- Name: auth_data; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE auth_data (
    id integer NOT NULL,
    credential_provider character varying(20) NOT NULL,
    field_name character varying(100) NOT NULL,
    field_value character varying(250) NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.auth_data OWNER TO roda;

--
-- Name: authoringentity; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE authoringentity (
    id_ bigint NOT NULL,
    affiliation character varying(255),
    content text,
    id character varying(255),
    responsibilitystatement_id bigint
);


ALTER TABLE public.authoringentity OWNER TO roda;

--
-- Name: bibliographiccitation; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE bibliographiccitation (
    id_ bigint NOT NULL,
    content text,
    format character varying(255),
    id character varying(255)
);


ALTER TABLE public.bibliographiccitation OWNER TO roda;

--
-- Name: catalog; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE catalog (
    id integer NOT NULL,
    added timestamp without time zone NOT NULL,
    name character varying(150) NOT NULL,
    owner integer NOT NULL,
    parent integer NOT NULL
);


ALTER TABLE public.catalog OWNER TO roda;

--
-- Name: catalog_acl; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE catalog_acl (
    aro_id integer NOT NULL,
    aro_type integer NOT NULL,
    delete boolean,
    modacl boolean,
    read boolean,
    update boolean,
    catalog_id integer NOT NULL
);


ALTER TABLE public.catalog_acl OWNER TO roda;

--
-- Name: catalog_study; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE catalog_study (
    catalog_id integer NOT NULL,
    study_id integer NOT NULL,
    added_by integer NOT NULL
);


ALTER TABLE public.catalog_study OWNER TO roda;

--
-- Name: category; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE category (
    id_ bigint NOT NULL,
    id character varying(255),
    missing character varying(255),
    catvalu_id_ bigint,
    variableddi_id bigint
);


ALTER TABLE public.category OWNER TO roda;

--
-- Name: category_category; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE category_category (
    category_id_ bigint NOT NULL,
    catgry_id_ bigint NOT NULL
);


ALTER TABLE public.category_category OWNER TO roda;

--
-- Name: categorystatistic; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE categorystatistic (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    type character varying(255),
    category_id bigint
);


ALTER TABLE public.categorystatistic OWNER TO roda;

--
-- Name: categoryvalue; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE categoryvalue (
    id_ bigint NOT NULL,
    content text,
    id character varying(255)
);


ALTER TABLE public.categoryvalue OWNER TO roda;

--
-- Name: citation; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE citation (
    id_ bigint NOT NULL,
    id character varying(255),
    biblcit_id_ bigint,
    diststmt_id_ bigint,
    prodstmt_id_ bigint,
    relatedmaterials_id bigint,
    rspstmt_id_ bigint,
    serstmt_id_ bigint,
    studydescription_id bigint,
    titlstmt_id_ bigint
);


ALTER TABLE public.citation OWNER TO roda;

--
-- Name: citationrequirement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE citationrequirement (
    id_ bigint NOT NULL,
    content text,
    id character varying(255)
);


ALTER TABLE public.citationrequirement OWNER TO roda;

--
-- Name: city; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE city (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    country_id integer NOT NULL
);


ALTER TABLE public.city OWNER TO roda;

--
-- Name: cms_files; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE cms_files (
    id integer NOT NULL,
    filename character varying(200) NOT NULL,
    filesize integer NOT NULL,
    label character varying(50) NOT NULL,
    md5 character varying(32) NOT NULL,
    mimegroup character varying(50) NOT NULL,
    mimesubgroup character varying(50) NOT NULL,
    folder_id integer NOT NULL
);


ALTER TABLE public.cms_files OWNER TO roda;

--
-- Name: cms_folders; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE cms_folders (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(150) NOT NULL,
    parent integer NOT NULL
);


ALTER TABLE public.cms_folders OWNER TO roda;

--
-- Name: cms_layout; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE cms_layout (
    id integer NOT NULL,
    layout_content character varying(255) NOT NULL,
    name character varying(150) NOT NULL,
    layout_group integer NOT NULL
);


ALTER TABLE public.cms_layout OWNER TO roda;

--
-- Name: cms_layout_group; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE cms_layout_group (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(150) NOT NULL,
    parent integer NOT NULL
);


ALTER TABLE public.cms_layout_group OWNER TO roda;

--
-- Name: cms_page; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE cms_page (
    id integer NOT NULL,
    name character varying(150) NOT NULL,
    navigable boolean NOT NULL,
    owner integer NOT NULL,
    page_type integer NOT NULL,
    url character varying(200) NOT NULL,
    visible boolean NOT NULL,
    layout integer NOT NULL
);


ALTER TABLE public.cms_page OWNER TO roda;

--
-- Name: cms_page_content; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE cms_page_content (
    id integer NOT NULL,
    content_text character varying(255),
    content_title character varying(250) NOT NULL,
    name character varying(150) NOT NULL,
    sqnumber integer NOT NULL,
    page integer
);


ALTER TABLE public.cms_page_content OWNER TO roda;

--
-- Name: cms_snippet; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE cms_snippet (
    id integer NOT NULL,
    name character varying(150) NOT NULL,
    snippet_content character varying(255) NOT NULL,
    snippet_group integer NOT NULL
);


ALTER TABLE public.cms_snippet OWNER TO roda;

--
-- Name: cms_snippet_group; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE cms_snippet_group (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(150) NOT NULL,
    parent integer NOT NULL
);


ALTER TABLE public.cms_snippet_group OWNER TO roda;

--
-- Name: codebook; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE codebook (
    id_ bigint NOT NULL,
    id character varying(255),
    version character varying(255),
    xmllang character varying(255),
    org_id integer
);


ALTER TABLE public.codebook OWNER TO roda;

--
-- Name: codebook_elsstterm; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE codebook_elsstterm (
    codebook_id_ bigint NOT NULL,
    elsstterm_id_ bigint NOT NULL
);


ALTER TABLE public.codebook_elsstterm OWNER TO roda;

--
-- Name: concept; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE concept (
    id integer NOT NULL,
    description character varying(300) NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.concept OWNER TO roda;

--
-- Name: concept_variable; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE concept_variable (
    concept_id integer NOT NULL,
    variable_id integer NOT NULL
);


ALTER TABLE public.concept_variable OWNER TO roda;

--
-- Name: contactperson; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE contactperson (
    id_ bigint NOT NULL,
    affiliation character varying(255),
    content text,
    email character varying(255),
    id character varying(255),
    uri character varying(255),
    distributorstatement_id bigint,
    usestatement_id bigint
);


ALTER TABLE public.contactperson OWNER TO roda;

--
-- Name: copyright; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE copyright (
    id_ bigint NOT NULL,
    content text,
    id character varying(255)
);


ALTER TABLE public.copyright OWNER TO roda;

--
-- Name: country; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE country (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.country OWNER TO roda;

--
-- Name: countryddi; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE countryddi (
    id_ bigint NOT NULL,
    abbr character varying(255),
    content text,
    id character varying(255),
    summarydatadescription_id bigint
);


ALTER TABLE public.countryddi OWNER TO roda;

--
-- Name: dataaccess; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE dataaccess (
    id_ bigint NOT NULL,
    id character varying(255),
    studydescription_id bigint
);


ALTER TABLE public.dataaccess OWNER TO roda;

--
-- Name: datacollectionmethodology; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE datacollectionmethodology (
    id_ bigint NOT NULL,
    id character varying(255),
    methodologyandprocessing_id bigint,
    sources_id_ bigint
);


ALTER TABLE public.datacollectionmethodology OWNER TO roda;

--
-- Name: datacollector; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE datacollector (
    id_ bigint NOT NULL,
    abbr character varying(255),
    affiliation character varying(255),
    content text,
    id character varying(255),
    datacollection_id bigint
);


ALTER TABLE public.datacollector OWNER TO roda;

--
-- Name: datafiledescription; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE datafiledescription (
    id_ bigint NOT NULL,
    id character varying(255),
    dimensns_id_ bigint,
    filename_id_ bigint,
    filetype_id_ bigint,
    datafilesdescription_id bigint,
    spssfile_id_ bigint,
    verstmt_id_ bigint
);


ALTER TABLE public.datafiledescription OWNER TO roda;

--
-- Name: datafilesdescription; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE datafilesdescription (
    id_ bigint NOT NULL,
    id character varying(255),
    uri character varying(255),
    codebook_id bigint
);


ALTER TABLE public.datafilesdescription OWNER TO roda;

--
-- Name: datasetavailability; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE datasetavailability (
    id_ bigint NOT NULL,
    callno character varying(255),
    id character varying(255),
    label character varying(255),
    media character varying(255),
    type character varying(255),
    dataaccess_id bigint,
    origarch_id_ bigint
);


ALTER TABLE public.datasetavailability OWNER TO roda;

--
-- Name: dateofcollection; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE dateofcollection (
    id_ bigint NOT NULL,
    content text,
    cycle character varying(255),
    date character varying(255),
    event character varying(255),
    id character varying(255),
    summarydatadescription_id bigint
);


ALTER TABLE public.dateofcollection OWNER TO roda;

--
-- Name: dateofdeposit; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE dateofdeposit (
    id_ bigint NOT NULL,
    content text,
    date character varying(255),
    id character varying(255),
    distributorstatement_id bigint
);


ALTER TABLE public.dateofdeposit OWNER TO roda;

--
-- Name: dateofproduction; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE dateofproduction (
    id_ bigint NOT NULL,
    content text,
    date character varying(255),
    id character varying(255),
    productionstatement_id bigint
);


ALTER TABLE public.dateofproduction OWNER TO roda;

--
-- Name: demo_scope; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE demo_scope (
    id integer NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE public.demo_scope OWNER TO roda;

--
-- Name: demo_slices; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE demo_slices (
    id integer NOT NULL,
    age_max integer NOT NULL,
    age_min integer NOT NULL,
    sex integer NOT NULL,
    demoscope_id integer NOT NULL,
    geo_scope_id integer NOT NULL
);


ALTER TABLE public.demo_slices OWNER TO roda;

--
-- Name: depositor; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE depositor (
    id_ bigint NOT NULL,
    abbr character varying(255),
    content text,
    id character varying(255),
    distributorstatement_id bigint
);


ALTER TABLE public.depositor OWNER TO roda;

--
-- Name: depositrequirement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE depositrequirement (
    id_ bigint NOT NULL,
    content text,
    id character varying(255)
);


ALTER TABLE public.depositrequirement OWNER TO roda;

--
-- Name: descriptivetext; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE descriptivetext (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    category_id bigint,
    variableddi_id bigint
);


ALTER TABLE public.descriptivetext OWNER TO roda;

--
-- Name: disclaimer; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE disclaimer (
    id_ bigint NOT NULL,
    content text,
    id character varying(255)
);


ALTER TABLE public.disclaimer OWNER TO roda;

--
-- Name: distributor; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE distributor (
    id_ bigint NOT NULL,
    abbr character varying(255),
    affiliation character varying(255),
    content text,
    id character varying(255),
    uri character varying(255),
    distributorstatement_id bigint
);


ALTER TABLE public.distributor OWNER TO roda;

--
-- Name: distributorstatement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE distributorstatement (
    id_ bigint NOT NULL,
    id character varying(255)
);


ALTER TABLE public.distributorstatement OWNER TO roda;

--
-- Name: document_type; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE document_type (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.document_type OWNER TO roda;

--
-- Name: documentationsource; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE documentationsource (
    id_ bigint NOT NULL,
    id character varying(255),
    biblcit_id_ bigint,
    diststmt_id_ bigint,
    documentdescription_id bigint,
    prodstmt_id_ bigint,
    rspstmt_id_ bigint,
    serstmt_id_ bigint,
    titlstmt_id_ bigint
);


ALTER TABLE public.documentationsource OWNER TO roda;

--
-- Name: documentdescription; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE documentdescription (
    id_ bigint NOT NULL,
    id character varying(255),
    citation_id_ bigint,
    codebook_id bigint
);


ALTER TABLE public.documentdescription OWNER TO roda;

--
-- Name: documents; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE documents (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    filename character varying(200) NOT NULL,
    filesize integer NOT NULL,
    mimetype character varying(50) NOT NULL,
    title character varying(250) NOT NULL,
    type_id integer NOT NULL
);


ALTER TABLE public.documents OWNER TO roda;

--
-- Name: documents_acl; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE documents_acl (
    aro_id integer NOT NULL,
    aro_type integer NOT NULL,
    delete boolean NOT NULL,
    read boolean NOT NULL,
    update boolean NOT NULL,
    document_id integer NOT NULL
);


ALTER TABLE public.documents_acl OWNER TO roda;

--
-- Name: edited_variable; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE edited_variable (
    variable_id integer NOT NULL,
    instance_id integer NOT NULL,
    type integer NOT NULL
);


ALTER TABLE public.edited_variable OWNER TO roda;

--
-- Name: elsstlanguage; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE elsstlanguage (
    id_ bigint NOT NULL,
    languagename text
);


ALTER TABLE public.elsstlanguage OWNER TO roda;

--
-- Name: elsstterm; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE elsstterm (
    id_ bigint NOT NULL,
    name text,
    scopenote text,
    broaderterm_id_ bigint,
    preferredterm_id_ bigint
);


ALTER TABLE public.elsstterm OWNER TO roda;

--
-- Name: elsstterm_related; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE elsstterm_related (
    elsstterm_id_ bigint NOT NULL,
    relatedterm_id_ bigint NOT NULL
);


ALTER TABLE public.elsstterm_related OWNER TO roda;

--
-- Name: elsstterm_translated; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE elsstterm_translated (
    elsstterm_id_ bigint NOT NULL,
    translatedterm_id_ bigint NOT NULL,
    translatedterm_key bigint NOT NULL
);


ALTER TABLE public.elsstterm_translated OWNER TO roda;

--
-- Name: emails; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE emails (
    id integer NOT NULL,
    email character varying(200) NOT NULL,
    ismain boolean NOT NULL,
    entity_type integer NOT NULL,
    entity_id integer NOT NULL
);


ALTER TABLE public.emails OWNER TO roda;

--
-- Name: filedimensions; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE filedimensions (
    id_ bigint NOT NULL,
    id character varying(255)
);


ALTER TABLE public.filedimensions OWNER TO roda;

--
-- Name: filename; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE filename (
    id_ bigint NOT NULL,
    content text,
    id character varying(255)
);


ALTER TABLE public.filename OWNER TO roda;

--
-- Name: form_edited_number_var; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE form_edited_number_var (
    id integer NOT NULL,
    instance_id integer,
    value numeric(10,2) NOT NULL,
    variable_id integer NOT NULL
);


ALTER TABLE public.form_edited_number_var OWNER TO roda;

--
-- Name: form_edited_text_var; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE form_edited_text_var (
    id integer NOT NULL,
    instance_id integer,
    text character varying(255) NOT NULL,
    variable_id integer NOT NULL
);


ALTER TABLE public.form_edited_text_var OWNER TO roda;

--
-- Name: frequency; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE frequency (
    id integer NOT NULL,
    value double precision NOT NULL
);


ALTER TABLE public.frequency OWNER TO roda;

--
-- Name: fundingagency; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE fundingagency (
    id_ bigint NOT NULL,
    abbr character varying(255),
    content text,
    id character varying(255),
    role character varying(255),
    productionstatement_id bigint
);


ALTER TABLE public.fundingagency OWNER TO roda;

--
-- Name: geo_scope; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE geo_scope (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    geo_slice_id integer NOT NULL
);


ALTER TABLE public.geo_scope OWNER TO roda;

--
-- Name: geo_slices; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE geo_slices (
    id integer NOT NULL,
    city integer NOT NULL,
    country integer NOT NULL,
    geoscope_id integer NOT NULL
);


ALTER TABLE public.geo_slices OWNER TO roda;

--
-- Name: geographicalcoverage; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE geographicalcoverage (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    summarydatadescription_id bigint
);


ALTER TABLE public.geographicalcoverage OWNER TO roda;

--
-- Name: geographicunit; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE geographicunit (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    summarydatadescription_id bigint
);


ALTER TABLE public.geographicunit OWNER TO roda;

--
-- Name: grantnumber; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE grantnumber (
    id_ bigint NOT NULL,
    agency character varying(255),
    content text,
    id character varying(255),
    role character varying(255),
    productionstatement_id bigint
);


ALTER TABLE public.grantnumber OWNER TO roda;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: roda
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO roda;

--
-- Name: holdings; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE holdings (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    uri character varying(255),
    citation_id bigint,
    documentationsource_id bigint
);


ALTER TABLE public.holdings OWNER TO roda;

--
-- Name: identificationnumber; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE identificationnumber (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    level character varying(255),
    titlestatement_id bigint
);


ALTER TABLE public.identificationnumber OWNER TO roda;

--
-- Name: instance; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE instance (
    id integer NOT NULL,
    dateend timestamp without time zone NOT NULL,
    datestart timestamp without time zone NOT NULL,
    demoscope_id integer NOT NULL,
    study_id integer NOT NULL,
    unit_analysis_id integer NOT NULL
);


ALTER TABLE public.instance OWNER TO roda;

--
-- Name: instance_acl; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE instance_acl (
    aro_id integer NOT NULL,
    aro_type integer NOT NULL,
    delete boolean,
    modacl boolean,
    read boolean,
    update boolean,
    instance_id integer NOT NULL
);


ALTER TABLE public.instance_acl OWNER TO roda;

--
-- Name: instance_descr; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE instance_descr (
    id integer NOT NULL,
    abstract character varying(255) NOT NULL,
    title character varying(100) NOT NULL,
    instance_id integer NOT NULL
);


ALTER TABLE public.instance_descr OWNER TO roda;

--
-- Name: instance_documents; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE instance_documents (
    document_id integer NOT NULL,
    instance_id integer NOT NULL
);


ALTER TABLE public.instance_documents OWNER TO roda;

--
-- Name: instance_org; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE instance_org (
    instance_id integer NOT NULL,
    org_id integer NOT NULL,
    assoc_details character varying(255) NOT NULL,
    assoc_type_id integer NOT NULL
);


ALTER TABLE public.instance_org OWNER TO roda;

--
-- Name: instance_org_assoc; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE instance_org_assoc (
    id integer NOT NULL,
    assoc_description character varying(255) NOT NULL,
    assoc_name character varying(100) NOT NULL
);


ALTER TABLE public.instance_org_assoc OWNER TO roda;

--
-- Name: instance_person; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE instance_person (
    assoc_type_id integer NOT NULL,
    instance_id integer NOT NULL,
    person_id integer NOT NULL
);


ALTER TABLE public.instance_person OWNER TO roda;

--
-- Name: instance_person_assoc; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE instance_person_assoc (
    id integer NOT NULL,
    assoc_description character varying(255) NOT NULL,
    assoc_name character varying(100) NOT NULL
);


ALTER TABLE public.instance_person_assoc OWNER TO roda;

--
-- Name: instance_var_group; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE instance_var_group (
    id integer NOT NULL,
    group_id integer NOT NULL,
    variable_id integer NOT NULL
);


ALTER TABLE public.instance_var_group OWNER TO roda;

--
-- Name: internet; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE internet (
    id integer NOT NULL,
    content character varying(250) NOT NULL,
    entity_type integer NOT NULL,
    internet_type_id integer NOT NULL,
    org_id integer NOT NULL,
    person_id integer NOT NULL
);


ALTER TABLE public.internet OWNER TO roda;

--
-- Name: invalidvaluerange; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE invalidvaluerange (
    id_ bigint NOT NULL,
    id character varying(255),
    variableddi_id bigint
);


ALTER TABLE public.invalidvaluerange OWNER TO roda;

--
-- Name: item; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE item (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.item OWNER TO roda;

--
-- Name: keyword; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE keyword (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    vocab character varying(255),
    vocaburi character varying(255),
    subjectinformation_id bigint
);


ALTER TABLE public.keyword OWNER TO roda;

--
-- Name: label; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE label (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    category_id bigint,
    otherstudyrelatedmaterials_id bigint,
    variableddi_id bigint
);


ALTER TABLE public.label OWNER TO roda;

--
-- Name: literalquestion; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE literalquestion (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    question_id bigint
);


ALTER TABLE public.literalquestion OWNER TO roda;

--
-- Name: location; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE location (
    id_ bigint NOT NULL,
    id character varying(255),
    recsegno character varying(255),
    width character varying(255),
    variableddi_id bigint
);


ALTER TABLE public.location OWNER TO roda;

--
-- Name: methodology; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE methodology (
    id integer NOT NULL,
    mode_collection character varying(200) NOT NULL,
    sampling_procedure character varying(200) NOT NULL,
    time_method character varying(200) NOT NULL,
    weighting character varying(200) NOT NULL,
    instance_id integer NOT NULL
);


ALTER TABLE public.methodology OWNER TO roda;

--
-- Name: methodologyandprocessing; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE methodologyandprocessing (
    id_ bigint NOT NULL,
    id character varying(255),
    studydescription_id bigint
);


ALTER TABLE public.methodologyandprocessing OWNER TO roda;

--
-- Name: modeofdatacollection; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE modeofdatacollection (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    datacollectionmethodology_id bigint
);


ALTER TABLE public.modeofdatacollection OWNER TO roda;

--
-- Name: numberofcases; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE numberofcases (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    filedimensions_id bigint
);


ALTER TABLE public.numberofcases OWNER TO roda;

--
-- Name: numberofvariablesperrecord; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE numberofvariablesperrecord (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    filedimensions_id bigint
);


ALTER TABLE public.numberofvariablesperrecord OWNER TO roda;

--
-- Name: org; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE org (
    id integer NOT NULL,
    fullname character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    org_prefix_id integer NOT NULL,
    org_sufix_id integer NOT NULL
);


ALTER TABLE public.org OWNER TO roda;

--
-- Name: org_address; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE org_address (
    address_id integer NOT NULL,
    org_id integer NOT NULL,
    dateend timestamp without time zone NOT NULL,
    datestart timestamp without time zone NOT NULL
);


ALTER TABLE public.org_address OWNER TO roda;

--
-- Name: org_prefix; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE org_prefix (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.org_prefix OWNER TO roda;

--
-- Name: org_relation_type; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE org_relation_type (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.org_relation_type OWNER TO roda;

--
-- Name: org_relations; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE org_relations (
    org_1_id integer NOT NULL,
    org_2_id integer NOT NULL,
    dateend timestamp without time zone NOT NULL,
    datestart timestamp without time zone NOT NULL,
    details character varying(255) NOT NULL,
    org_relation_type_id integer NOT NULL
);


ALTER TABLE public.org_relations OWNER TO roda;

--
-- Name: org_sufix; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE org_sufix (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.org_sufix OWNER TO roda;

--
-- Name: other_statistics; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE other_statistics (
    id integer NOT NULL,
    instance_id integer,
    statistic_name character varying(30) NOT NULL,
    statistic_value double precision NOT NULL,
    variable_id integer NOT NULL
);


ALTER TABLE public.other_statistics OWNER TO roda;

--
-- Name: otheridentificationacknowledgements; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE otheridentificationacknowledgements (
    id_ bigint NOT NULL,
    affiliation character varying(255),
    id character varying(255),
    role character varying(255),
    type character varying(255),
    otheridentificationacknowledgements_id bigint,
    responsibilitystatement_id bigint
);


ALTER TABLE public.otheridentificationacknowledgements OWNER TO roda;

--
-- Name: otherstudydescriptionmaterials; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE otherstudydescriptionmaterials (
    id_ bigint NOT NULL,
    id character varying(255),
    studydescription_id bigint
);


ALTER TABLE public.otherstudydescriptionmaterials OWNER TO roda;

--
-- Name: otherstudyrelatedmaterials; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE otherstudyrelatedmaterials (
    id_ bigint NOT NULL,
    id character varying(255),
    level character varying(255),
    type character varying(255),
    uri character varying(255),
    citation_id_ bigint,
    codebook_id bigint,
    otherstudyrelatedmaterials_id bigint,
    txt_id_ bigint
);


ALTER TABLE public.otherstudyrelatedmaterials OWNER TO roda;

--
-- Name: paragraph; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE paragraph (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    otheridentificationacknowledgements_id bigint
);


ALTER TABLE public.paragraph OWNER TO roda;

--
-- Name: paralleltitle; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE paralleltitle (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    titlestatement_id bigint
);


ALTER TABLE public.paralleltitle OWNER TO roda;

--
-- Name: person; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE person (
    id integer NOT NULL,
    fname character varying(100) NOT NULL,
    lname character varying(100) NOT NULL,
    mname character varying(100) NOT NULL,
    prefix_id integer,
    suffix_id integer
);


ALTER TABLE public.person OWNER TO roda;

--
-- Name: person_address; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE person_address (
    address_id integer NOT NULL,
    person_id integer NOT NULL,
    dateend timestamp without time zone NOT NULL,
    datestart timestamp without time zone NOT NULL
);


ALTER TABLE public.person_address OWNER TO roda;

--
-- Name: person_org; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE person_org (
    org_id integer NOT NULL,
    person_id integer NOT NULL,
    dateend timestamp without time zone NOT NULL,
    datestart timestamp without time zone NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.person_org OWNER TO roda;

--
-- Name: person_role; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE person_role (
    id integer NOT NULL,
    name character varying(250) NOT NULL
);


ALTER TABLE public.person_role OWNER TO roda;

--
-- Name: placeofproduction; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE placeofproduction (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    productionstatement_id bigint
);


ALTER TABLE public.placeofproduction OWNER TO roda;

--
-- Name: prefix; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE prefix (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.prefix OWNER TO roda;

--
-- Name: producertype; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE producertype (
    id_ bigint NOT NULL,
    abbr character varying(255),
    affiliation character varying(255),
    content text,
    id character varying(255),
    productionstatement_id bigint
);


ALTER TABLE public.producertype OWNER TO roda;

--
-- Name: productionstatement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE productionstatement (
    id_ bigint NOT NULL,
    id character varying(255),
    copyright_id_ bigint
);


ALTER TABLE public.productionstatement OWNER TO roda;

--
-- Name: question; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE question (
    id_ bigint NOT NULL,
    id character varying(255),
    seqno character varying(255),
    variableddi_id bigint
);


ALTER TABLE public.question OWNER TO roda;

--
-- Name: relatedmaterials; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE relatedmaterials (
    id_ bigint NOT NULL,
    callno character varying(255),
    id character varying(255),
    label character varying(255),
    media character varying(255),
    type character varying(255),
    otherstudydescriptionmaterials_id bigint
);


ALTER TABLE public.relatedmaterials OWNER TO roda;

--
-- Name: responsibilitystatement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE responsibilitystatement (
    id_ bigint NOT NULL,
    id character varying(255)
);


ALTER TABLE public.responsibilitystatement OWNER TO roda;

--
-- Name: role; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE role (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(150) NOT NULL
);


ALTER TABLE public.role OWNER TO roda;

--
-- Name: samplingprocedure; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE samplingprocedure (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    datacollectionmethodology_id bigint
);


ALTER TABLE public.samplingprocedure OWNER TO roda;

--
-- Name: selection_variable; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE selection_variable (
    id integer NOT NULL,
    instance_id integer,
    max_count integer NOT NULL,
    min_count integer NOT NULL,
    variable_id integer NOT NULL
);


ALTER TABLE public.selection_variable OWNER TO roda;

--
-- Name: selection_variable_card; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE selection_variable_card (
    instance_id integer NOT NULL,
    item_id integer NOT NULL,
    response_card integer NOT NULL,
    variable_id integer NOT NULL
);


ALTER TABLE public.selection_variable_card OWNER TO roda;

--
-- Name: sequence; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE sequence (
    seq_name character varying(50) NOT NULL,
    seq_count numeric(38,0)
);


ALTER TABLE public.sequence OWNER TO roda;

--
-- Name: seriesinformation; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE seriesinformation (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    seriesstatement_id bigint
);


ALTER TABLE public.seriesinformation OWNER TO roda;

--
-- Name: seriesname; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE seriesname (
    id_ bigint NOT NULL,
    abbr character varying(255),
    content text,
    id character varying(255),
    seriesstatement_id bigint
);


ALTER TABLE public.seriesname OWNER TO roda;

--
-- Name: seriesstatement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE seriesstatement (
    id_ bigint NOT NULL,
    id character varying(255),
    uri character varying(255)
);


ALTER TABLE public.seriesstatement OWNER TO roda;

--
-- Name: setting; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE setting (
    id integer NOT NULL,
    default_value character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(150) NOT NULL,
    predefined_values character varying(255) NOT NULL,
    setting_group integer NOT NULL
);


ALTER TABLE public.setting OWNER TO roda;

--
-- Name: setting_group; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE setting_group (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(150) NOT NULL,
    parent integer NOT NULL
);


ALTER TABLE public.setting_group OWNER TO roda;

--
-- Name: setting_values; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE setting_values (
    setting_id integer NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.setting_values OWNER TO roda;

--
-- Name: skip; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE skip (
    id integer NOT NULL,
    condition character varying(255) NOT NULL,
    instance_id integer,
    next_variable_id integer NOT NULL,
    variable_id integer NOT NULL
);


ALTER TABLE public.skip OWNER TO roda;

--
-- Name: softwareusedinproduction; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE softwareusedinproduction (
    id_ bigint NOT NULL,
    content text,
    date character varying(255),
    id character varying(255),
    version character varying(255),
    productionstatement_id bigint,
    softwareusedinproduction_id bigint
);


ALTER TABLE public.softwareusedinproduction OWNER TO roda;

--
-- Name: sourcesstatement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE sourcesstatement (
    id_ bigint NOT NULL,
    id character varying(255),
    sourcesstatement_id bigint
);


ALTER TABLE public.sourcesstatement OWNER TO roda;

--
-- Name: specialpermissions; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE specialpermissions (
    id_ bigint NOT NULL,
    content text,
    formno character varying(255),
    id character varying(255),
    required character varying(255),
    uri character varying(255)
);


ALTER TABLE public.specialpermissions OWNER TO roda;

--
-- Name: spssdatarecord; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssdatarecord (
    id_ bigint NOT NULL,
    recordindex integer
);


ALTER TABLE public.spssdatarecord OWNER TO roda;

--
-- Name: spssdatarecordvariablevalue; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssdatarecordvariablevalue (
    id_ bigint NOT NULL,
    value character varying(255),
    datarecord_id_ bigint,
    variable_id_ bigint
);


ALTER TABLE public.spssdatarecordvariablevalue OWNER TO roda;

--
-- Name: spssfile; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssfile (
    id_ bigint NOT NULL,
    isbigendian boolean NOT NULL,
    uniqueid character varying(255),
    documentationrecord_id_ bigint,
    floatinformationrecord_id_ bigint,
    inforecord_id_ bigint,
    integerinformationrecord_id_ bigint,
    longvariablenamesrecord_id_ bigint,
    variabledisplayparamsrecord_id_ bigint,
    variablesetsinformationrecord_id_ bigint
);


ALTER TABLE public.spssfile OWNER TO roda;

--
-- Name: spssfile_spssdatarecordvariablevalue; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssfile_spssdatarecordvariablevalue (
    spssfile_id_ bigint NOT NULL,
    values_id_ bigint NOT NULL
);


ALTER TABLE public.spssfile_spssdatarecordvariablevalue OWNER TO roda;

--
-- Name: spssfile_spssvariable; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssfile_spssvariable (
    spssfile_id_ bigint NOT NULL,
    variablemap_id_ bigint NOT NULL,
    variablemap_key integer NOT NULL
);


ALTER TABLE public.spssfile_spssvariable OWNER TO roda;

--
-- Name: spssrecordtype1; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype1 (
    id_ bigint NOT NULL,
    obsperobservation integer NOT NULL,
    compressionbias double precision NOT NULL,
    compressionswitch integer NOT NULL,
    creationdate character varying(255),
    creationtime character varying(255),
    filelabel character varying(255),
    layoutcode integer NOT NULL,
    numberofcases integer NOT NULL,
    productidentification character varying(255),
    recordtypecode character varying(255),
    weightvariableindex integer NOT NULL
);


ALTER TABLE public.spssrecordtype1 OWNER TO roda;

--
-- Name: spssrecordtype2; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype2 (
    id_ bigint NOT NULL,
    haslabel integer NOT NULL,
    label character varying(255),
    labellength integer NOT NULL,
    missingvalue bytea,
    missingvalueformatcode integer NOT NULL,
    name character varying(255),
    printformatcode integer NOT NULL,
    printformatdecimals integer NOT NULL,
    printformattype integer NOT NULL,
    printformatwidth integer NOT NULL,
    printformatzero integer NOT NULL,
    recordtypecode integer NOT NULL,
    variabletypecode integer NOT NULL,
    writeformatcode integer NOT NULL,
    writeformatdecimals integer NOT NULL,
    writeformattype integer NOT NULL,
    writeformatwidth integer NOT NULL,
    writeformatzero integer NOT NULL,
    valuelabelset_id_ bigint
);


ALTER TABLE public.spssrecordtype2 OWNER TO roda;

--
-- Name: spssrecordtype3; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype3 (
    id_ bigint NOT NULL,
    numberoflabels integer NOT NULL,
    recordtypecode integer NOT NULL
);


ALTER TABLE public.spssrecordtype3 OWNER TO roda;

--
-- Name: spssrecordtype4; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype4 (
    id_ bigint NOT NULL,
    numberofvariables integer NOT NULL,
    recordtypecode integer NOT NULL,
    variableindex bytea
);


ALTER TABLE public.spssrecordtype4 OWNER TO roda;

--
-- Name: spssrecordtype6; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype6 (
    id_ bigint NOT NULL,
    numberoflines integer NOT NULL,
    recordtypecode integer NOT NULL
);


ALTER TABLE public.spssrecordtype6 OWNER TO roda;

--
-- Name: spssrecordtype6_line; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype6_line (
    spssrecordtype6_id_ bigint NOT NULL,
    line character varying(255)
);


ALTER TABLE public.spssrecordtype6_line OWNER TO roda;

--
-- Name: spssrecordtype7; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype7 (
    id_ bigint NOT NULL,
    dataelementlength integer NOT NULL,
    numberofdataelements integer NOT NULL,
    recordsubtypecode integer NOT NULL,
    recordtypecode integer NOT NULL
);


ALTER TABLE public.spssrecordtype7 OWNER TO roda;

--
-- Name: spssrecordtype7subtype11; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype7subtype11 (
    id_ bigint NOT NULL,
    dataelementlength integer NOT NULL,
    numberofdataelements integer NOT NULL,
    recordsubtypecode integer NOT NULL,
    recordtypecode integer NOT NULL
);


ALTER TABLE public.spssrecordtype7subtype11 OWNER TO roda;

--
-- Name: spssrecordtype7subtype11_variabledisplayparams; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype7subtype11_variabledisplayparams (
    spssrecordtype7subtype11_id_ bigint NOT NULL,
    variabledisplayparams_id_ bigint NOT NULL
);


ALTER TABLE public.spssrecordtype7subtype11_variabledisplayparams OWNER TO roda;

--
-- Name: spssrecordtype7subtype13; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype7subtype13 (
    id_ bigint NOT NULL,
    dataelementlength integer NOT NULL,
    longnamesstr character varying(255),
    numberofdataelements integer NOT NULL,
    recordsubtypecode integer NOT NULL,
    recordtypecode integer NOT NULL
);


ALTER TABLE public.spssrecordtype7subtype13 OWNER TO roda;

--
-- Name: spssrecordtype7subtype3; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype7subtype3 (
    id_ bigint NOT NULL,
    characterrepresentation integer NOT NULL,
    compressionscheme integer NOT NULL,
    dataelementlength integer NOT NULL,
    endianness integer NOT NULL,
    floatrepresentation integer NOT NULL,
    machinecode integer NOT NULL,
    numberofdataelements integer NOT NULL,
    recordsubtypecode integer NOT NULL,
    recordtypecode integer NOT NULL,
    releasemajor integer NOT NULL,
    releaseminor integer NOT NULL,
    releasespecial integer NOT NULL
);


ALTER TABLE public.spssrecordtype7subtype3 OWNER TO roda;

--
-- Name: spssrecordtype7subtype4; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype7subtype4 (
    id_ bigint NOT NULL,
    dataelementlength integer NOT NULL,
    highest double precision NOT NULL,
    lowest double precision NOT NULL,
    numberofdataelements integer NOT NULL,
    recordsubtypecode integer NOT NULL,
    recordtypecode integer NOT NULL,
    sysmiss double precision NOT NULL
);


ALTER TABLE public.spssrecordtype7subtype4 OWNER TO roda;

--
-- Name: spssrecordtype7subtype5; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssrecordtype7subtype5 (
    id_ bigint NOT NULL,
    dataelementlength integer NOT NULL,
    numberofdataelements integer NOT NULL,
    recordsubtypecode integer NOT NULL,
    recordtypecode integer NOT NULL,
    variablesets character varying(255)
);


ALTER TABLE public.spssrecordtype7subtype5 OWNER TO roda;

--
-- Name: spssvariable; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssvariable (
    id_ bigint NOT NULL,
    alignment integer NOT NULL,
    displaywidth integer NOT NULL,
    measure integer NOT NULL,
    type integer,
    variablename character varying(255),
    variablenumber integer NOT NULL,
    variableshortname character varying(255),
    valuelabelrecord_id_ bigint,
    variablerecord_id_ bigint
);


ALTER TABLE public.spssvariable OWNER TO roda;

--
-- Name: spssvariable_spssvariablecategory; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssvariable_spssvariablecategory (
    spssvariable_id_ bigint NOT NULL,
    categorymap_id_ bigint NOT NULL,
    categorymap_key character varying(255) NOT NULL
);


ALTER TABLE public.spssvariable_spssvariablecategory OWNER TO roda;

--
-- Name: spssvariablecategory; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE spssvariablecategory (
    id_ bigint NOT NULL,
    freq bigint NOT NULL,
    ismissing boolean NOT NULL,
    label character varying(255),
    strvalue character varying(255),
    value double precision NOT NULL,
    wgtfreq bigint NOT NULL
);


ALTER TABLE public.spssvariablecategory OWNER TO roda;

--
-- Name: studiu_person_asoc; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE studiu_person_asoc (
    id integer NOT NULL,
    asoc_description character varying(255) NOT NULL,
    asoc_name character varying(100) NOT NULL
);


ALTER TABLE public.studiu_person_asoc OWNER TO roda;

--
-- Name: study; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE study (
    id integer NOT NULL,
    dateend timestamp without time zone NOT NULL,
    datestart timestamp without time zone NOT NULL
);


ALTER TABLE public.study OWNER TO roda;

--
-- Name: study_acl; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE study_acl (
    aro_id integer NOT NULL,
    aro_type integer NOT NULL,
    delete boolean,
    modacl boolean,
    read boolean,
    update boolean,
    study_id integer NOT NULL
);


ALTER TABLE public.study_acl OWNER TO roda;

--
-- Name: study_descr; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE study_descr (
    id integer NOT NULL,
    abstract character varying(255) NOT NULL,
    title character varying(250) NOT NULL,
    study_id integer NOT NULL
);


ALTER TABLE public.study_descr OWNER TO roda;

--
-- Name: study_documents; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE study_documents (
    study_id integer NOT NULL,
    document_id integer NOT NULL
);


ALTER TABLE public.study_documents OWNER TO roda;

--
-- Name: study_org; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE study_org (
    id integer NOT NULL,
    org_id integer NOT NULL,
    study_id integer NOT NULL,
    assoctype_id integer NOT NULL
);


ALTER TABLE public.study_org OWNER TO roda;

--
-- Name: study_org_acl; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE study_org_acl (
    aro_id integer NOT NULL,
    aro_type integer NOT NULL,
    delete boolean,
    modacl boolean,
    read boolean,
    study_id integer NOT NULL,
    update boolean,
    study_org_id integer NOT NULL
);


ALTER TABLE public.study_org_acl OWNER TO roda;

--
-- Name: study_org_asoc; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE study_org_asoc (
    id integer NOT NULL,
    asoc_description bigint NOT NULL,
    asoc_name character varying(100) NOT NULL,
    assoc_details character varying(255) NOT NULL
);


ALTER TABLE public.study_org_asoc OWNER TO roda;

--
-- Name: study_person; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE study_person (
    id integer NOT NULL,
    person_id integer NOT NULL,
    asoctype_id integer NOT NULL,
    study_id integer NOT NULL
);


ALTER TABLE public.study_person OWNER TO roda;

--
-- Name: study_person_acl; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE study_person_acl (
    aro_id integer NOT NULL,
    aro_type integer NOT NULL,
    delete boolean,
    modacl boolean,
    read boolean,
    update boolean,
    study_person_id integer NOT NULL
);


ALTER TABLE public.study_person_acl OWNER TO roda;

--
-- Name: studydescription; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE studydescription (
    id_ bigint NOT NULL,
    id character varying(255),
    codebook_id bigint
);


ALTER TABLE public.studydescription OWNER TO roda;

--
-- Name: studyinfoandscope; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE studyinfoandscope (
    id_ bigint NOT NULL,
    id character varying(255),
    studydescription_id bigint,
    subject_id_ bigint
);


ALTER TABLE public.studyinfoandscope OWNER TO roda;

--
-- Name: subjectinformation; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE subjectinformation (
    id_ bigint NOT NULL,
    id character varying(255)
);


ALTER TABLE public.subjectinformation OWNER TO roda;

--
-- Name: suffix; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE suffix (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.suffix OWNER TO roda;

--
-- Name: summarydatadescription; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE summarydatadescription (
    id_ bigint NOT NULL,
    id character varying(255),
    studyinfoandscope_id bigint
);


ALTER TABLE public.summarydatadescription OWNER TO roda;

--
-- Name: summarystatistics; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE summarystatistics (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    type character varying(255),
    variableddi_id bigint
);


ALTER TABLE public.summarystatistics OWNER TO roda;

--
-- Name: timemethod; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE timemethod (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    method character varying(255),
    datacollectionmethodology_id bigint
);


ALTER TABLE public.timemethod OWNER TO roda;

--
-- Name: timeperiodcovered; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE timeperiodcovered (
    id_ bigint NOT NULL,
    content text,
    cycle character varying(255),
    date character varying(255),
    event character varying(255),
    id character varying(255),
    summarydatadescription_id bigint
);


ALTER TABLE public.timeperiodcovered OWNER TO roda;

--
-- Name: title; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE title (
    id_ bigint NOT NULL,
    content text,
    id character varying(255)
);


ALTER TABLE public.title OWNER TO roda;

--
-- Name: titlestatement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE titlestatement (
    id_ bigint NOT NULL,
    id character varying(255),
    titl_id_ bigint
);


ALTER TABLE public.titlestatement OWNER TO roda;

--
-- Name: topicclasification; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE topicclasification (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    vocab character varying(255),
    vocaburi character varying(255),
    subjectinformation_id bigint
);


ALTER TABLE public.topicclasification OWNER TO roda;

--
-- Name: txttype_sdatrefs; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE txttype_sdatrefs (
    txttype_id_ bigint NOT NULL,
    sdatrefs character varying(255)
);


ALTER TABLE public.txttype_sdatrefs OWNER TO roda;

--
-- Name: typeoffile; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE typeoffile (
    id_ bigint NOT NULL,
    charset character varying(255),
    content text,
    id character varying(255)
);


ALTER TABLE public.typeoffile OWNER TO roda;

--
-- Name: typeofresearchinstrument; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE typeofresearchinstrument (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    type character varying(255),
    datacollectionmethodology_id bigint
);


ALTER TABLE public.typeofresearchinstrument OWNER TO roda;

--
-- Name: unit_analysis; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE unit_analysis (
    id integer NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.unit_analysis OWNER TO roda;

--
-- Name: unitofanalysis; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE unitofanalysis (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    unit character varying(255),
    summarydatadescription_id bigint
);


ALTER TABLE public.unitofanalysis OWNER TO roda;

--
-- Name: universe; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE universe (
    id_ bigint NOT NULL,
    clusion character varying(255),
    content text,
    id character varying(255),
    level character varying(255),
    summarydatadescription_id bigint,
    variableddi_id bigint
);


ALTER TABLE public.universe OWNER TO roda;

--
-- Name: user_auth_log; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE user_auth_log (
    id integer NOT NULL,
    action character varying(10) NOT NULL,
    credential_identifier character varying(250) NOT NULL,
    credential_provider character varying(20) NOT NULL,
    error_message character varying(250) NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.user_auth_log OWNER TO roda;

--
-- Name: user_messages; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE user_messages (
    id integer NOT NULL,
    message character varying(255) NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.user_messages OWNER TO roda;

--
-- Name: user_role; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE user_role (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_role OWNER TO roda;

--
-- Name: user_setting_value; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE user_setting_value (
    user_id integer NOT NULL,
    user_setting_id integer NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.user_setting_value OWNER TO roda;

--
-- Name: user_settings; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE user_settings (
    id integer NOT NULL,
    default_value character varying(255),
    description character varying(255),
    name character varying(150) NOT NULL,
    predefined_values character varying(255),
    setting_group integer NOT NULL
);


ALTER TABLE public.user_settings OWNER TO roda;

--
-- Name: user_settings_group; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE user_settings_group (
    id integer NOT NULL,
    description character varying(255),
    name character varying(150) NOT NULL
);


ALTER TABLE public.user_settings_group OWNER TO roda;

--
-- Name: users; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    credential_provider character varying(20) NOT NULL
);


ALTER TABLE public.users OWNER TO roda;

--
-- Name: usestatement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE usestatement (
    id_ bigint NOT NULL,
    id character varying(255),
    citreq_id_ bigint,
    dataaccess_id bigint,
    deposreq_id_ bigint,
    disclaimer_id_ bigint,
    specperm_id_ bigint
);


ALTER TABLE public.usestatement OWNER TO roda;

--
-- Name: valueitem; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE valueitem (
    id_ bigint NOT NULL,
    id character varying(255),
    units character varying(255),
    value character varying(255),
    invalidvaluerange_id bigint,
    valuerange_id bigint
);


ALTER TABLE public.valueitem OWNER TO roda;

--
-- Name: valuerange; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE valuerange (
    id_ bigint NOT NULL,
    id character varying(255),
    max character varying(255),
    min character varying(255),
    units character varying(255),
    variableddi_id bigint,
    invalidvaluerange_id bigint,
    valuerange_id bigint
);


ALTER TABLE public.valuerange OWNER TO roda;

--
-- Name: variable_group; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE variable_group (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.variable_group OWNER TO roda;

--
-- Name: variableddi; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE variableddi (
    id_ bigint NOT NULL,
    id character varying(255),
    intrvl character varying(255),
    name character varying(255),
    wgt character varying(255),
    variabledescription_id bigint,
    varformat_id_ bigint
);


ALTER TABLE public.variableddi OWNER TO roda;

--
-- Name: variableddi_elsstterm; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE variableddi_elsstterm (
    variableddi_id_ bigint NOT NULL,
    elsstterm_id_ bigint NOT NULL
);


ALTER TABLE public.variableddi_elsstterm OWNER TO roda;

--
-- Name: variabledescription; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE variabledescription (
    id_ bigint NOT NULL,
    id character varying(255),
    codebook_id bigint
);


ALTER TABLE public.variabledescription OWNER TO roda;

--
-- Name: variabledisplayparams; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE variabledisplayparams (
    id_ bigint NOT NULL,
    alignment integer NOT NULL,
    measure integer NOT NULL,
    width integer NOT NULL
);


ALTER TABLE public.variabledisplayparams OWNER TO roda;

--
-- Name: variableformat; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE variableformat (
    id_ bigint NOT NULL,
    category character varying(255),
    content text,
    formatname character varying(255),
    id character varying(255),
    schema character varying(255),
    type character varying(255)
);


ALTER TABLE public.variableformat OWNER TO roda;

--
-- Name: vartype_files; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE vartype_files (
    vartype_id_ bigint NOT NULL,
    files character varying(255)
);


ALTER TABLE public.vartype_files OWNER TO roda;

--
-- Name: version; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE version (
    id_ bigint NOT NULL,
    content text,
    date character varying(255),
    id character varying(255),
    type character varying(255)
);


ALTER TABLE public.version OWNER TO roda;

--
-- Name: versionresponsibilitystatement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE versionresponsibilitystatement (
    id_ bigint NOT NULL,
    affiliation character varying(255),
    content text,
    id character varying(255)
);


ALTER TABLE public.versionresponsibilitystatement OWNER TO roda;

--
-- Name: versionstatement; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE versionstatement (
    id_ bigint NOT NULL,
    id character varying(255),
    citation_id bigint,
    documentationsource_id bigint,
    variableddi_id bigint,
    verresp_id_ bigint,
    version_id_ bigint
);


ALTER TABLE public.versionstatement OWNER TO roda;

--
-- Name: weight; Type: TABLE; Schema: public; Owner: roda; Tablespace: 
--

CREATE TABLE weight (
    id_ bigint NOT NULL,
    content text,
    id character varying(255),
    datacollectionmethodology_id bigint
);


ALTER TABLE public.weight OWNER TO roda;

--
-- Name: Data_InstanceId_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Data"
    ADD CONSTRAINT "Data_InstanceId_key" UNIQUE ("InstanceId");


--
-- Name: Data_ValueId_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Data"
    ADD CONSTRAINT "Data_ValueId_key" UNIQUE ("ValueId");


--
-- Name: Data_VariableId_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Data"
    ADD CONSTRAINT "Data_VariableId_key" UNIQUE ("VariableId");


--
-- Name: Data_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Data"
    ADD CONSTRAINT "Data_pkey" PRIMARY KEY ("InstanceId", "ValueId", "VariableId");


--
-- Name: Selected_Answers_QuestionnaireId_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Selected_Answers"
    ADD CONSTRAINT "Selected_Answers_QuestionnaireId_key" UNIQUE ("QuestionnaireId");


--
-- Name: Selected_Answers_ValueId_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Selected_Answers"
    ADD CONSTRAINT "Selected_Answers_ValueId_key" UNIQUE ("ValueId");


--
-- Name: Selected_Answers_VarInStudyId_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Selected_Answers"
    ADD CONSTRAINT "Selected_Answers_VarInStudyId_key" UNIQUE ("VarInStudyId");


--
-- Name: Selected_Answers_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Selected_Answers"
    ADD CONSTRAINT "Selected_Answers_pkey" PRIMARY KEY ("QuestionnaireId", "ValueId", "VarInStudyId");


--
-- Name: Variable_Sequences_Order_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Variable_Sequences"
    ADD CONSTRAINT "Variable_Sequences_Order_key" UNIQUE ("Order");


--
-- Name: Variable_Sequences_VariableId_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Variable_Sequences"
    ADD CONSTRAINT "Variable_Sequences_VariableId_key" UNIQUE ("VariableId");


--
-- Name: Variable_Sequences_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY "Variable_Sequences"
    ADD CONSTRAINT "Variable_Sequences_pkey" PRIMARY KEY ("Order", "VariableId");


--
-- Name: abstract_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY abstract
    ADD CONSTRAINT abstract_pkey PRIMARY KEY (id_);


--
-- Name: accessplace_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY accessplace
    ADD CONSTRAINT accessplace_pkey PRIMARY KEY (id_);


--
-- Name: address_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- Name: alternativetitle_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY alternativetitle
    ADD CONSTRAINT alternativetitle_pkey PRIMARY KEY (id_);


--
-- Name: answer_form_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT answer_form_id_key UNIQUE (form_id);


--
-- Name: answer_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT answer_instance_id_key UNIQUE (instance_id);


--
-- Name: answer_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT answer_pkey PRIMARY KEY (form_id, instance_id, variable_id);


--
-- Name: answer_variable_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT answer_variable_id_key UNIQUE (variable_id);


--
-- Name: archivewherestudywasoriginallystored_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY archivewherestudywasoriginallystored
    ADD CONSTRAINT archivewherestudywasoriginallystored_pkey PRIMARY KEY (id_);


--
-- Name: auth_data_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY auth_data
    ADD CONSTRAINT auth_data_pkey PRIMARY KEY (id);


--
-- Name: authoringentity_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY authoringentity
    ADD CONSTRAINT authoringentity_pkey PRIMARY KEY (id_);


--
-- Name: bibliographiccitation_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY bibliographiccitation
    ADD CONSTRAINT bibliographiccitation_pkey PRIMARY KEY (id_);


--
-- Name: catalog_acl_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY catalog_acl
    ADD CONSTRAINT catalog_acl_pkey PRIMARY KEY (aro_id);


--
-- Name: catalog_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY catalog
    ADD CONSTRAINT catalog_pkey PRIMARY KEY (id);


--
-- Name: catalog_study_catalog_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY catalog_study
    ADD CONSTRAINT catalog_study_catalog_id_key UNIQUE (catalog_id);


--
-- Name: catalog_study_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY catalog_study
    ADD CONSTRAINT catalog_study_pkey PRIMARY KEY (catalog_id, study_id);


--
-- Name: catalog_study_study_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY catalog_study
    ADD CONSTRAINT catalog_study_study_id_key UNIQUE (study_id);


--
-- Name: category_category_catgry_id__key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY category_category
    ADD CONSTRAINT category_category_catgry_id__key UNIQUE (catgry_id_);


--
-- Name: category_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id_);


--
-- Name: categorystatistic_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY categorystatistic
    ADD CONSTRAINT categorystatistic_pkey PRIMARY KEY (id_);


--
-- Name: categoryvalue_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY categoryvalue
    ADD CONSTRAINT categoryvalue_pkey PRIMARY KEY (id_);


--
-- Name: citation_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY citation
    ADD CONSTRAINT citation_pkey PRIMARY KEY (id_);


--
-- Name: citationrequirement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY citationrequirement
    ADD CONSTRAINT citationrequirement_pkey PRIMARY KEY (id_);


--
-- Name: city_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY city
    ADD CONSTRAINT city_pkey PRIMARY KEY (id);


--
-- Name: cms_files_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY cms_files
    ADD CONSTRAINT cms_files_pkey PRIMARY KEY (id);


--
-- Name: cms_folders_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY cms_folders
    ADD CONSTRAINT cms_folders_pkey PRIMARY KEY (id);


--
-- Name: cms_layout_group_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY cms_layout_group
    ADD CONSTRAINT cms_layout_group_pkey PRIMARY KEY (id);


--
-- Name: cms_layout_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY cms_layout
    ADD CONSTRAINT cms_layout_pkey PRIMARY KEY (id);


--
-- Name: cms_page_content_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY cms_page_content
    ADD CONSTRAINT cms_page_content_pkey PRIMARY KEY (id);


--
-- Name: cms_page_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY cms_page
    ADD CONSTRAINT cms_page_pkey PRIMARY KEY (id);


--
-- Name: cms_snippet_group_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY cms_snippet_group
    ADD CONSTRAINT cms_snippet_group_pkey PRIMARY KEY (id);


--
-- Name: cms_snippet_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY cms_snippet
    ADD CONSTRAINT cms_snippet_pkey PRIMARY KEY (id);


--
-- Name: codebook_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY codebook
    ADD CONSTRAINT codebook_pkey PRIMARY KEY (id_);


--
-- Name: concept_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY concept
    ADD CONSTRAINT concept_pkey PRIMARY KEY (id);


--
-- Name: concept_variable_concept_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY concept_variable
    ADD CONSTRAINT concept_variable_concept_id_key UNIQUE (concept_id);


--
-- Name: concept_variable_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY concept_variable
    ADD CONSTRAINT concept_variable_pkey PRIMARY KEY (concept_id, variable_id);


--
-- Name: concept_variable_variable_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY concept_variable
    ADD CONSTRAINT concept_variable_variable_id_key UNIQUE (variable_id);


--
-- Name: contactperson_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY contactperson
    ADD CONSTRAINT contactperson_pkey PRIMARY KEY (id_);


--
-- Name: copyright_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY copyright
    ADD CONSTRAINT copyright_pkey PRIMARY KEY (id_);


--
-- Name: country_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY country
    ADD CONSTRAINT country_pkey PRIMARY KEY (id);


--
-- Name: countryddi_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY countryddi
    ADD CONSTRAINT countryddi_pkey PRIMARY KEY (id_);


--
-- Name: dataaccess_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY dataaccess
    ADD CONSTRAINT dataaccess_pkey PRIMARY KEY (id_);


--
-- Name: datacollectionmethodology_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY datacollectionmethodology
    ADD CONSTRAINT datacollectionmethodology_pkey PRIMARY KEY (id_);


--
-- Name: datacollector_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY datacollector
    ADD CONSTRAINT datacollector_pkey PRIMARY KEY (id_);


--
-- Name: datafiledescription_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY datafiledescription
    ADD CONSTRAINT datafiledescription_pkey PRIMARY KEY (id_);


--
-- Name: datafilesdescription_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY datafilesdescription
    ADD CONSTRAINT datafilesdescription_pkey PRIMARY KEY (id_);


--
-- Name: datasetavailability_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY datasetavailability
    ADD CONSTRAINT datasetavailability_pkey PRIMARY KEY (id_);


--
-- Name: dateofcollection_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY dateofcollection
    ADD CONSTRAINT dateofcollection_pkey PRIMARY KEY (id_);


--
-- Name: dateofdeposit_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY dateofdeposit
    ADD CONSTRAINT dateofdeposit_pkey PRIMARY KEY (id_);


--
-- Name: dateofproduction_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY dateofproduction
    ADD CONSTRAINT dateofproduction_pkey PRIMARY KEY (id_);


--
-- Name: demo_scope_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY demo_scope
    ADD CONSTRAINT demo_scope_pkey PRIMARY KEY (id);


--
-- Name: demo_slices_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY demo_slices
    ADD CONSTRAINT demo_slices_pkey PRIMARY KEY (id);


--
-- Name: depositor_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY depositor
    ADD CONSTRAINT depositor_pkey PRIMARY KEY (id_);


--
-- Name: depositrequirement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY depositrequirement
    ADD CONSTRAINT depositrequirement_pkey PRIMARY KEY (id_);


--
-- Name: descriptivetext_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY descriptivetext
    ADD CONSTRAINT descriptivetext_pkey PRIMARY KEY (id_);


--
-- Name: disclaimer_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY disclaimer
    ADD CONSTRAINT disclaimer_pkey PRIMARY KEY (id_);


--
-- Name: distributor_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY distributor
    ADD CONSTRAINT distributor_pkey PRIMARY KEY (id_);


--
-- Name: distributorstatement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY distributorstatement
    ADD CONSTRAINT distributorstatement_pkey PRIMARY KEY (id_);


--
-- Name: document_type_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY document_type
    ADD CONSTRAINT document_type_pkey PRIMARY KEY (id);


--
-- Name: documentationsource_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY documentationsource
    ADD CONSTRAINT documentationsource_pkey PRIMARY KEY (id_);


--
-- Name: documentdescription_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY documentdescription
    ADD CONSTRAINT documentdescription_pkey PRIMARY KEY (id_);


--
-- Name: documents_acl_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY documents_acl
    ADD CONSTRAINT documents_acl_pkey PRIMARY KEY (aro_id);


--
-- Name: documents_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY documents
    ADD CONSTRAINT documents_pkey PRIMARY KEY (id);


--
-- Name: edited_variable_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY edited_variable
    ADD CONSTRAINT edited_variable_pkey PRIMARY KEY (variable_id);


--
-- Name: elsstlanguage_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY elsstlanguage
    ADD CONSTRAINT elsstlanguage_pkey PRIMARY KEY (id_);


--
-- Name: elsstterm_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY elsstterm
    ADD CONSTRAINT elsstterm_pkey PRIMARY KEY (id_);


--
-- Name: elsstterm_translated_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY elsstterm_translated
    ADD CONSTRAINT elsstterm_translated_pkey PRIMARY KEY (elsstterm_id_, translatedterm_key);


--
-- Name: emails_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY emails
    ADD CONSTRAINT emails_pkey PRIMARY KEY (id);


--
-- Name: filedimensions_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY filedimensions
    ADD CONSTRAINT filedimensions_pkey PRIMARY KEY (id_);


--
-- Name: filename_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY filename
    ADD CONSTRAINT filename_pkey PRIMARY KEY (id_);


--
-- Name: form_edited_number_var_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY form_edited_number_var
    ADD CONSTRAINT form_edited_number_var_pkey PRIMARY KEY (id);


--
-- Name: form_edited_text_var_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY form_edited_text_var
    ADD CONSTRAINT form_edited_text_var_pkey PRIMARY KEY (id);


--
-- Name: frequency_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY frequency
    ADD CONSTRAINT frequency_pkey PRIMARY KEY (id);


--
-- Name: fundingagency_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY fundingagency
    ADD CONSTRAINT fundingagency_pkey PRIMARY KEY (id_);


--
-- Name: geo_scope_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY geo_scope
    ADD CONSTRAINT geo_scope_pkey PRIMARY KEY (id);


--
-- Name: geo_slices_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY geo_slices
    ADD CONSTRAINT geo_slices_pkey PRIMARY KEY (id);


--
-- Name: geographicalcoverage_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY geographicalcoverage
    ADD CONSTRAINT geographicalcoverage_pkey PRIMARY KEY (id_);


--
-- Name: geographicunit_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY geographicunit
    ADD CONSTRAINT geographicunit_pkey PRIMARY KEY (id_);


--
-- Name: grantnumber_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY grantnumber
    ADD CONSTRAINT grantnumber_pkey PRIMARY KEY (id_);


--
-- Name: holdings_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY holdings
    ADD CONSTRAINT holdings_pkey PRIMARY KEY (id_);


--
-- Name: identificationnumber_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY identificationnumber
    ADD CONSTRAINT identificationnumber_pkey PRIMARY KEY (id_);


--
-- Name: instance_acl_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_acl
    ADD CONSTRAINT instance_acl_pkey PRIMARY KEY (aro_id);


--
-- Name: instance_descr_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_descr
    ADD CONSTRAINT instance_descr_pkey PRIMARY KEY (id);


--
-- Name: instance_documents_document_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_documents
    ADD CONSTRAINT instance_documents_document_id_key UNIQUE (document_id);


--
-- Name: instance_documents_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_documents
    ADD CONSTRAINT instance_documents_instance_id_key UNIQUE (instance_id);


--
-- Name: instance_documents_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_documents
    ADD CONSTRAINT instance_documents_pkey PRIMARY KEY (document_id, instance_id);


--
-- Name: instance_org_assoc_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_org_assoc
    ADD CONSTRAINT instance_org_assoc_pkey PRIMARY KEY (id);


--
-- Name: instance_org_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_org
    ADD CONSTRAINT instance_org_instance_id_key UNIQUE (instance_id);


--
-- Name: instance_org_org_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_org
    ADD CONSTRAINT instance_org_org_id_key UNIQUE (org_id);


--
-- Name: instance_org_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_org
    ADD CONSTRAINT instance_org_pkey PRIMARY KEY (instance_id, org_id);


--
-- Name: instance_person_assoc_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_person_assoc
    ADD CONSTRAINT instance_person_assoc_pkey PRIMARY KEY (id);


--
-- Name: instance_person_assoc_type_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_person
    ADD CONSTRAINT instance_person_assoc_type_id_key UNIQUE (assoc_type_id);


--
-- Name: instance_person_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_person
    ADD CONSTRAINT instance_person_instance_id_key UNIQUE (instance_id);


--
-- Name: instance_person_person_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_person
    ADD CONSTRAINT instance_person_person_id_key UNIQUE (person_id);


--
-- Name: instance_person_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_person
    ADD CONSTRAINT instance_person_pkey PRIMARY KEY (assoc_type_id, instance_id, person_id);


--
-- Name: instance_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance
    ADD CONSTRAINT instance_pkey PRIMARY KEY (id);


--
-- Name: instance_var_group_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY instance_var_group
    ADD CONSTRAINT instance_var_group_pkey PRIMARY KEY (id);


--
-- Name: internet_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY internet
    ADD CONSTRAINT internet_pkey PRIMARY KEY (id);


--
-- Name: invalidvaluerange_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY invalidvaluerange
    ADD CONSTRAINT invalidvaluerange_pkey PRIMARY KEY (id_);


--
-- Name: item_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- Name: keyword_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY keyword
    ADD CONSTRAINT keyword_pkey PRIMARY KEY (id_);


--
-- Name: label_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY label
    ADD CONSTRAINT label_pkey PRIMARY KEY (id_);


--
-- Name: literalquestion_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY literalquestion
    ADD CONSTRAINT literalquestion_pkey PRIMARY KEY (id_);


--
-- Name: location_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id_);


--
-- Name: methodology_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY methodology
    ADD CONSTRAINT methodology_pkey PRIMARY KEY (id);


--
-- Name: methodologyandprocessing_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY methodologyandprocessing
    ADD CONSTRAINT methodologyandprocessing_pkey PRIMARY KEY (id_);


--
-- Name: modeofdatacollection_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY modeofdatacollection
    ADD CONSTRAINT modeofdatacollection_pkey PRIMARY KEY (id_);


--
-- Name: numberofcases_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY numberofcases
    ADD CONSTRAINT numberofcases_pkey PRIMARY KEY (id_);


--
-- Name: numberofvariablesperrecord_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY numberofvariablesperrecord
    ADD CONSTRAINT numberofvariablesperrecord_pkey PRIMARY KEY (id_);


--
-- Name: org_address_address_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org_address
    ADD CONSTRAINT org_address_address_id_key UNIQUE (address_id);


--
-- Name: org_address_org_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org_address
    ADD CONSTRAINT org_address_org_id_key UNIQUE (org_id);


--
-- Name: org_address_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org_address
    ADD CONSTRAINT org_address_pkey PRIMARY KEY (address_id, org_id);


--
-- Name: org_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org
    ADD CONSTRAINT org_pkey PRIMARY KEY (id);


--
-- Name: org_prefix_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org_prefix
    ADD CONSTRAINT org_prefix_pkey PRIMARY KEY (id);


--
-- Name: org_relation_type_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org_relation_type
    ADD CONSTRAINT org_relation_type_pkey PRIMARY KEY (id);


--
-- Name: org_relations_org_1_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org_relations
    ADD CONSTRAINT org_relations_org_1_id_key UNIQUE (org_1_id);


--
-- Name: org_relations_org_2_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org_relations
    ADD CONSTRAINT org_relations_org_2_id_key UNIQUE (org_2_id);


--
-- Name: org_relations_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org_relations
    ADD CONSTRAINT org_relations_pkey PRIMARY KEY (org_1_id, org_2_id);


--
-- Name: org_sufix_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY org_sufix
    ADD CONSTRAINT org_sufix_pkey PRIMARY KEY (id);


--
-- Name: other_statistics_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY other_statistics
    ADD CONSTRAINT other_statistics_pkey PRIMARY KEY (id);


--
-- Name: otheridentificationacknowledgements_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY otheridentificationacknowledgements
    ADD CONSTRAINT otheridentificationacknowledgements_pkey PRIMARY KEY (id_);


--
-- Name: otherstudydescriptionmaterials_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY otherstudydescriptionmaterials
    ADD CONSTRAINT otherstudydescriptionmaterials_pkey PRIMARY KEY (id_);


--
-- Name: otherstudyrelatedmaterials_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY otherstudyrelatedmaterials
    ADD CONSTRAINT otherstudyrelatedmaterials_pkey PRIMARY KEY (id_);


--
-- Name: paragraph_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY paragraph
    ADD CONSTRAINT paragraph_pkey PRIMARY KEY (id_);


--
-- Name: paralleltitle_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY paralleltitle
    ADD CONSTRAINT paralleltitle_pkey PRIMARY KEY (id_);


--
-- Name: person_address_address_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY person_address
    ADD CONSTRAINT person_address_address_id_key UNIQUE (address_id);


--
-- Name: person_address_person_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY person_address
    ADD CONSTRAINT person_address_person_id_key UNIQUE (person_id);


--
-- Name: person_address_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY person_address
    ADD CONSTRAINT person_address_pkey PRIMARY KEY (address_id, person_id);


--
-- Name: person_org_org_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY person_org
    ADD CONSTRAINT person_org_org_id_key UNIQUE (org_id);


--
-- Name: person_org_person_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY person_org
    ADD CONSTRAINT person_org_person_id_key UNIQUE (person_id);


--
-- Name: person_org_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY person_org
    ADD CONSTRAINT person_org_pkey PRIMARY KEY (org_id, person_id);


--
-- Name: person_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: person_role_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY person_role
    ADD CONSTRAINT person_role_pkey PRIMARY KEY (id);


--
-- Name: placeofproduction_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY placeofproduction
    ADD CONSTRAINT placeofproduction_pkey PRIMARY KEY (id_);


--
-- Name: prefix_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY prefix
    ADD CONSTRAINT prefix_pkey PRIMARY KEY (id);


--
-- Name: producertype_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY producertype
    ADD CONSTRAINT producertype_pkey PRIMARY KEY (id_);


--
-- Name: productionstatement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY productionstatement
    ADD CONSTRAINT productionstatement_pkey PRIMARY KEY (id_);


--
-- Name: question_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY question
    ADD CONSTRAINT question_pkey PRIMARY KEY (id_);


--
-- Name: relatedmaterials_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY relatedmaterials
    ADD CONSTRAINT relatedmaterials_pkey PRIMARY KEY (id_);


--
-- Name: responsibilitystatement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY responsibilitystatement
    ADD CONSTRAINT responsibilitystatement_pkey PRIMARY KEY (id_);


--
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: samplingprocedure_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY samplingprocedure
    ADD CONSTRAINT samplingprocedure_pkey PRIMARY KEY (id_);


--
-- Name: selection_variable_card_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY selection_variable_card
    ADD CONSTRAINT selection_variable_card_instance_id_key UNIQUE (instance_id);


--
-- Name: selection_variable_card_item_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY selection_variable_card
    ADD CONSTRAINT selection_variable_card_item_id_key UNIQUE (item_id);


--
-- Name: selection_variable_card_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY selection_variable_card
    ADD CONSTRAINT selection_variable_card_pkey PRIMARY KEY (instance_id, item_id, response_card, variable_id);


--
-- Name: selection_variable_card_response_card_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY selection_variable_card
    ADD CONSTRAINT selection_variable_card_response_card_key UNIQUE (response_card);


--
-- Name: selection_variable_card_variable_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY selection_variable_card
    ADD CONSTRAINT selection_variable_card_variable_id_key UNIQUE (variable_id);


--
-- Name: selection_variable_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY selection_variable
    ADD CONSTRAINT selection_variable_pkey PRIMARY KEY (id);


--
-- Name: sequence_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY sequence
    ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);


--
-- Name: seriesinformation_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY seriesinformation
    ADD CONSTRAINT seriesinformation_pkey PRIMARY KEY (id_);


--
-- Name: seriesname_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY seriesname
    ADD CONSTRAINT seriesname_pkey PRIMARY KEY (id_);


--
-- Name: seriesstatement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY seriesstatement
    ADD CONSTRAINT seriesstatement_pkey PRIMARY KEY (id_);


--
-- Name: setting_group_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY setting_group
    ADD CONSTRAINT setting_group_pkey PRIMARY KEY (id);


--
-- Name: setting_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY setting
    ADD CONSTRAINT setting_pkey PRIMARY KEY (id);


--
-- Name: setting_values_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY setting_values
    ADD CONSTRAINT setting_values_pkey PRIMARY KEY (setting_id);


--
-- Name: skip_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY skip
    ADD CONSTRAINT skip_pkey PRIMARY KEY (id);


--
-- Name: softwareusedinproduction_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY softwareusedinproduction
    ADD CONSTRAINT softwareusedinproduction_pkey PRIMARY KEY (id_);


--
-- Name: sourcesstatement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY sourcesstatement
    ADD CONSTRAINT sourcesstatement_pkey PRIMARY KEY (id_);


--
-- Name: specialpermissions_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY specialpermissions
    ADD CONSTRAINT specialpermissions_pkey PRIMARY KEY (id_);


--
-- Name: spssdatarecord_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssdatarecord
    ADD CONSTRAINT spssdatarecord_pkey PRIMARY KEY (id_);


--
-- Name: spssdatarecordvariablevalue_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssdatarecordvariablevalue
    ADD CONSTRAINT spssdatarecordvariablevalue_pkey PRIMARY KEY (id_);


--
-- Name: spssfile_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssfile
    ADD CONSTRAINT spssfile_pkey PRIMARY KEY (id_);


--
-- Name: spssfile_spssdatarecordvariablevalue_values_id__key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssfile_spssdatarecordvariablevalue
    ADD CONSTRAINT spssfile_spssdatarecordvariablevalue_values_id__key UNIQUE (values_id_);


--
-- Name: spssfile_spssvariable_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssfile_spssvariable
    ADD CONSTRAINT spssfile_spssvariable_pkey PRIMARY KEY (spssfile_id_, variablemap_key);


--
-- Name: spssrecordtype1_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype1
    ADD CONSTRAINT spssrecordtype1_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype2_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype2
    ADD CONSTRAINT spssrecordtype2_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype3_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype3
    ADD CONSTRAINT spssrecordtype3_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype4_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype4
    ADD CONSTRAINT spssrecordtype4_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype6_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype6
    ADD CONSTRAINT spssrecordtype6_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype7_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype7
    ADD CONSTRAINT spssrecordtype7_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype7subtype11_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype7subtype11
    ADD CONSTRAINT spssrecordtype7subtype11_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype7subtype11_variable_variabledisplayparams_id__key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype7subtype11_variabledisplayparams
    ADD CONSTRAINT spssrecordtype7subtype11_variable_variabledisplayparams_id__key UNIQUE (variabledisplayparams_id_);


--
-- Name: spssrecordtype7subtype13_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype7subtype13
    ADD CONSTRAINT spssrecordtype7subtype13_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype7subtype3_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype7subtype3
    ADD CONSTRAINT spssrecordtype7subtype3_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype7subtype4_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype7subtype4
    ADD CONSTRAINT spssrecordtype7subtype4_pkey PRIMARY KEY (id_);


--
-- Name: spssrecordtype7subtype5_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssrecordtype7subtype5
    ADD CONSTRAINT spssrecordtype7subtype5_pkey PRIMARY KEY (id_);


--
-- Name: spssvariable_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssvariable
    ADD CONSTRAINT spssvariable_pkey PRIMARY KEY (id_);


--
-- Name: spssvariable_spssvariablecategory_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssvariable_spssvariablecategory
    ADD CONSTRAINT spssvariable_spssvariablecategory_pkey PRIMARY KEY (spssvariable_id_, categorymap_key);


--
-- Name: spssvariablecategory_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY spssvariablecategory
    ADD CONSTRAINT spssvariablecategory_pkey PRIMARY KEY (id_);


--
-- Name: studiu_person_asoc_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY studiu_person_asoc
    ADD CONSTRAINT studiu_person_asoc_pkey PRIMARY KEY (id);


--
-- Name: study_acl_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY study_acl
    ADD CONSTRAINT study_acl_pkey PRIMARY KEY (aro_id);


--
-- Name: study_descr_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY study_descr
    ADD CONSTRAINT study_descr_pkey PRIMARY KEY (id);


--
-- Name: study_org_acl_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY study_org_acl
    ADD CONSTRAINT study_org_acl_pkey PRIMARY KEY (aro_id);


--
-- Name: study_org_asoc_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY study_org_asoc
    ADD CONSTRAINT study_org_asoc_pkey PRIMARY KEY (id);


--
-- Name: study_org_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY study_org
    ADD CONSTRAINT study_org_pkey PRIMARY KEY (id);


--
-- Name: study_person_acl_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY study_person_acl
    ADD CONSTRAINT study_person_acl_pkey PRIMARY KEY (aro_id);


--
-- Name: study_person_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY study_person
    ADD CONSTRAINT study_person_pkey PRIMARY KEY (id);


--
-- Name: study_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY study
    ADD CONSTRAINT study_pkey PRIMARY KEY (id);


--
-- Name: studydescription_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY studydescription
    ADD CONSTRAINT studydescription_pkey PRIMARY KEY (id_);


--
-- Name: studyinfoandscope_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY studyinfoandscope
    ADD CONSTRAINT studyinfoandscope_pkey PRIMARY KEY (id_);


--
-- Name: subjectinformation_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY subjectinformation
    ADD CONSTRAINT subjectinformation_pkey PRIMARY KEY (id_);


--
-- Name: suffix_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY suffix
    ADD CONSTRAINT suffix_pkey PRIMARY KEY (id);


--
-- Name: summarydatadescription_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY summarydatadescription
    ADD CONSTRAINT summarydatadescription_pkey PRIMARY KEY (id_);


--
-- Name: summarystatistics_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY summarystatistics
    ADD CONSTRAINT summarystatistics_pkey PRIMARY KEY (id_);


--
-- Name: timemethod_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY timemethod
    ADD CONSTRAINT timemethod_pkey PRIMARY KEY (id_);


--
-- Name: timeperiodcovered_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY timeperiodcovered
    ADD CONSTRAINT timeperiodcovered_pkey PRIMARY KEY (id_);


--
-- Name: title_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY title
    ADD CONSTRAINT title_pkey PRIMARY KEY (id_);


--
-- Name: titlestatement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY titlestatement
    ADD CONSTRAINT titlestatement_pkey PRIMARY KEY (id_);


--
-- Name: topicclasification_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY topicclasification
    ADD CONSTRAINT topicclasification_pkey PRIMARY KEY (id_);


--
-- Name: typeoffile_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY typeoffile
    ADD CONSTRAINT typeoffile_pkey PRIMARY KEY (id_);


--
-- Name: typeofresearchinstrument_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY typeofresearchinstrument
    ADD CONSTRAINT typeofresearchinstrument_pkey PRIMARY KEY (id_);


--
-- Name: unit_analysis_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY unit_analysis
    ADD CONSTRAINT unit_analysis_pkey PRIMARY KEY (id);


--
-- Name: unitofanalysis_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY unitofanalysis
    ADD CONSTRAINT unitofanalysis_pkey PRIMARY KEY (id_);


--
-- Name: universe_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY universe
    ADD CONSTRAINT universe_pkey PRIMARY KEY (id_);


--
-- Name: user_auth_log_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY user_auth_log
    ADD CONSTRAINT user_auth_log_pkey PRIMARY KEY (id);


--
-- Name: user_messages_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY user_messages
    ADD CONSTRAINT user_messages_pkey PRIMARY KEY (id);


--
-- Name: user_setting_value_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY user_setting_value
    ADD CONSTRAINT user_setting_value_pkey PRIMARY KEY (user_id, user_setting_id);


--
-- Name: user_setting_value_user_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY user_setting_value
    ADD CONSTRAINT user_setting_value_user_id_key UNIQUE (user_id);


--
-- Name: user_setting_value_user_setting_id_key; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY user_setting_value
    ADD CONSTRAINT user_setting_value_user_setting_id_key UNIQUE (user_setting_id);


--
-- Name: user_settings_group_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY user_settings_group
    ADD CONSTRAINT user_settings_group_pkey PRIMARY KEY (id);


--
-- Name: user_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY user_settings
    ADD CONSTRAINT user_settings_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: usestatement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY usestatement
    ADD CONSTRAINT usestatement_pkey PRIMARY KEY (id_);


--
-- Name: valueitem_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY valueitem
    ADD CONSTRAINT valueitem_pkey PRIMARY KEY (id_);


--
-- Name: valuerange_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY valuerange
    ADD CONSTRAINT valuerange_pkey PRIMARY KEY (id_);


--
-- Name: variable_group_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY variable_group
    ADD CONSTRAINT variable_group_pkey PRIMARY KEY (id);


--
-- Name: variableddi_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY variableddi
    ADD CONSTRAINT variableddi_pkey PRIMARY KEY (id_);


--
-- Name: variabledescription_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY variabledescription
    ADD CONSTRAINT variabledescription_pkey PRIMARY KEY (id_);


--
-- Name: variabledisplayparams_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY variabledisplayparams
    ADD CONSTRAINT variabledisplayparams_pkey PRIMARY KEY (id_);


--
-- Name: variableformat_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY variableformat
    ADD CONSTRAINT variableformat_pkey PRIMARY KEY (id_);


--
-- Name: version_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY version
    ADD CONSTRAINT version_pkey PRIMARY KEY (id_);


--
-- Name: versionresponsibilitystatement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY versionresponsibilitystatement
    ADD CONSTRAINT versionresponsibilitystatement_pkey PRIMARY KEY (id_);


--
-- Name: versionstatement_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY versionstatement
    ADD CONSTRAINT versionstatement_pkey PRIMARY KEY (id_);


--
-- Name: weight_pkey; Type: CONSTRAINT; Schema: public; Owner: roda; Tablespace: 
--

ALTER TABLE ONLY weight
    ADD CONSTRAINT weight_pkey PRIMARY KEY (id_);


--
-- Name: fk143bf46a11581445; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fk143bf46a11581445 FOREIGN KEY (role_id) REFERENCES role(id);


--
-- Name: fk143bf46ab682d825; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fk143bf46ab682d825 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk14d9be735fced28f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY concept_variable
    ADD CONSTRAINT fk14d9be735fced28f FOREIGN KEY (concept_id) REFERENCES concept(id);


--
-- Name: fk1aee482756c1c; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY org
    ADD CONSTRAINT fk1aee482756c1c FOREIGN KEY (org_sufix_id) REFERENCES org_sufix(id);


--
-- Name: fk1aee497b10578; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY org
    ADD CONSTRAINT fk1aee497b10578 FOREIGN KEY (org_prefix_id) REFERENCES org_prefix(id);


--
-- Name: fk211694953820cc8f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance
    ADD CONSTRAINT fk211694953820cc8f FOREIGN KEY (demoscope_id) REFERENCES demo_scope(id);


--
-- Name: fk2116949592b498cb; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance
    ADD CONSTRAINT fk2116949592b498cb FOREIGN KEY (unit_analysis_id) REFERENCES unit_analysis(id);


--
-- Name: fk2116949599747c2f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance
    ADD CONSTRAINT fk2116949599747c2f FOREIGN KEY (study_id) REFERENCES study(id);


--
-- Name: fk2171844c2b58b80e; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY form_edited_number_var
    ADD CONSTRAINT fk2171844c2b58b80e FOREIGN KEY (variable_id) REFERENCES edited_variable(variable_id);


--
-- Name: fk21b70ff175019a1; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile_spssvariable
    ADD CONSTRAINT fk21b70ff175019a1 FOREIGN KEY (spssfile_id_) REFERENCES spssfile(id_);


--
-- Name: fk21b70ff624cb888; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile_spssvariable
    ADD CONSTRAINT fk21b70ff624cb888 FOREIGN KEY (variablemap_id_) REFERENCES spssvariable(id_);


--
-- Name: fk21e59efd3820cc8f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY demo_slices
    ADD CONSTRAINT fk21e59efd3820cc8f FOREIGN KEY (demoscope_id) REFERENCES demo_scope(id);


--
-- Name: fk21e59efdce007a62; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY demo_slices
    ADD CONSTRAINT fk21e59efdce007a62 FOREIGN KEY (geo_scope_id) REFERENCES geo_scope(id);


--
-- Name: fk21ffc74124e2e38f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY internet
    ADD CONSTRAINT fk21ffc74124e2e38f FOREIGN KEY (org_id) REFERENCES org(id);


--
-- Name: fk21ffc7413688cd25; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY internet
    ADD CONSTRAINT fk21ffc7413688cd25 FOREIGN KEY (person_id) REFERENCES person(id);


--
-- Name: fk23f8b90a3688cd25; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY person_address
    ADD CONSTRAINT fk23f8b90a3688cd25 FOREIGN KEY (person_id) REFERENCES person(id);


--
-- Name: fk23f8b90aa60ed70f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY person_address
    ADD CONSTRAINT fk23f8b90aa60ed70f FOREIGN KEY (address_id) REFERENCES address(id);


--
-- Name: fk250999e326043c34; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY accessplace
    ADD CONSTRAINT fk250999e326043c34 FOREIGN KEY (datasetavailability_id) REFERENCES datasetavailability(id_);


--
-- Name: fk26dd49991110e0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY dateofdeposit
    ADD CONSTRAINT fk26dd49991110e0 FOREIGN KEY (distributorstatement_id) REFERENCES distributorstatement(id_);


--
-- Name: fk2878c6fa24e2e38f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY person_org
    ADD CONSTRAINT fk2878c6fa24e2e38f FOREIGN KEY (org_id) REFERENCES org(id);


--
-- Name: fk2878c6fa3688cd25; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY person_org
    ADD CONSTRAINT fk2878c6fa3688cd25 FOREIGN KEY (person_id) REFERENCES person(id);


--
-- Name: fk2878c6fa9bd24cfa; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY person_org
    ADD CONSTRAINT fk2878c6fa9bd24cfa FOREIGN KEY (role_id) REFERENCES person_role(id);


--
-- Name: fk2b02fbc04b31abc0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY variabledescription
    ADD CONSTRAINT fk2b02fbc04b31abc0 FOREIGN KEY (codebook_id) REFERENCES codebook(id_);


--
-- Name: fk2b0beac373ba9a2b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY dateofcollection
    ADD CONSTRAINT fk2b0beac373ba9a2b FOREIGN KEY (summarydatadescription_id) REFERENCES summarydatadescription(id_);


--
-- Name: fk2da90e0c7bac1363; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY producertype
    ADD CONSTRAINT fk2da90e0c7bac1363 FOREIGN KEY (productionstatement_id) REFERENCES productionstatement(id_);


--
-- Name: fk2e07f7c531f8ded4; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY elsstterm_related
    ADD CONSTRAINT fk2e07f7c531f8ded4 FOREIGN KEY (relatedterm_id_) REFERENCES elsstterm(id_);


--
-- Name: fk2e07f7c5ec365cb6; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY elsstterm_related
    ADD CONSTRAINT fk2e07f7c5ec365cb6 FOREIGN KEY (elsstterm_id_) REFERENCES elsstterm(id_);


--
-- Name: fk2e996b96fa9bcf; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY city
    ADD CONSTRAINT fk2e996b96fa9bcf FOREIGN KEY (country_id) REFERENCES country(id);


--
-- Name: fk32adb5c97acb005a; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY keyword
    ADD CONSTRAINT fk32adb5c97acb005a FOREIGN KEY (subjectinformation_id) REFERENCES subjectinformation(id_);


--
-- Name: fk32bbcf8234788cf5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY modeofdatacollection
    ADD CONSTRAINT fk32bbcf8234788cf5 FOREIGN KEY (datacollectionmethodology_id) REFERENCES datacollectionmethodology(id_);


--
-- Name: fk334698c19ddbfea; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY summarydatadescription
    ADD CONSTRAINT fk334698c19ddbfea FOREIGN KEY (studyinfoandscope_id) REFERENCES studyinfoandscope(id_);


--
-- Name: fk34482be331303ba8; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datacollector
    ADD CONSTRAINT fk34482be331303ba8 FOREIGN KEY (datacollection_id) REFERENCES datacollectionmethodology(id_);


--
-- Name: fk348b7e15bc14321e; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY seriesinformation
    ADD CONSTRAINT fk348b7e15bc14321e FOREIGN KEY (seriesstatement_id) REFERENCES seriesstatement(id_);


--
-- Name: fk383d52b87a973280; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documents
    ADD CONSTRAINT fk383d52b87a973280 FOREIGN KEY (type_id) REFERENCES document_type(id);


--
-- Name: fk3a2c5c9373ba9a2b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY countryddi
    ADD CONSTRAINT fk3a2c5c9373ba9a2b FOREIGN KEY (summarydatadescription_id) REFERENCES summarydatadescription(id_);


--
-- Name: fk3bdddcaa81a8d605; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssrecordtype2
    ADD CONSTRAINT fk3bdddcaa81a8d605 FOREIGN KEY (valuelabelset_id_) REFERENCES spssrecordtype3(id_);


--
-- Name: fk3ec448e526b89a17; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssrecordtype6_line
    ADD CONSTRAINT fk3ec448e526b89a17 FOREIGN KEY (spssrecordtype6_id_) REFERENCES spssrecordtype6(id_);


--
-- Name: fk3eff35cf98b8db85; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY geo_slices
    ADD CONSTRAINT fk3eff35cf98b8db85 FOREIGN KEY (geoscope_id) REFERENCES geo_scope(id);


--
-- Name: fk42250a97175019a1; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile_spssdatarecordvariablevalue
    ADD CONSTRAINT fk42250a97175019a1 FOREIGN KEY (spssfile_id_) REFERENCES spssfile(id_);


--
-- Name: fk42250a97d8c6bbda; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile_spssdatarecordvariablevalue
    ADD CONSTRAINT fk42250a97d8c6bbda FOREIGN KEY (values_id_) REFERENCES spssdatarecordvariablevalue(id_);


--
-- Name: fk4421c0274dc264fd; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY cms_snippet
    ADD CONSTRAINT fk4421c0274dc264fd FOREIGN KEY (snippet_group) REFERENCES cms_snippet_group(id);


--
-- Name: fk45c8ed417380290; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY label
    ADD CONSTRAINT fk45c8ed417380290 FOREIGN KEY (otherstudyrelatedmaterials_id) REFERENCES otherstudyrelatedmaterials(id_);


--
-- Name: fk45c8ed429fc58d4; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY label
    ADD CONSTRAINT fk45c8ed429fc58d4 FOREIGN KEY (category_id) REFERENCES category(id_);


--
-- Name: fk45c8ed483e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY label
    ADD CONSTRAINT fk45c8ed483e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fk46ae14e191110e0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY depositor
    ADD CONSTRAINT fk46ae14e191110e0 FOREIGN KEY (distributorstatement_id) REFERENCES distributorstatement(id_);


--
-- Name: fk478dcd9924e2e38f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY org_address
    ADD CONSTRAINT fk478dcd9924e2e38f FOREIGN KEY (org_id) REFERENCES org(id);


--
-- Name: fk478dcd99a60ed70f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY org_address
    ADD CONSTRAINT fk478dcd99a60ed70f FOREIGN KEY (address_id) REFERENCES address(id);


--
-- Name: fk49c98004a8cc7a06; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY valueitem
    ADD CONSTRAINT fk49c98004a8cc7a06 FOREIGN KEY (invalidvaluerange_id) REFERENCES invalidvaluerange(id_);


--
-- Name: fk49c98004cda41138; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY valueitem
    ADD CONSTRAINT fk49c98004cda41138 FOREIGN KEY (valuerange_id) REFERENCES valuerange(id_);


--
-- Name: fk4a547d75a4f8e65; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_descr
    ADD CONSTRAINT fk4a547d75a4f8e65 FOREIGN KEY (instance_id) REFERENCES instance(id);


--
-- Name: fk4aa09d4983e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY summarystatistics
    ADD CONSTRAINT fk4aa09d4983e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fk4cbce0713131c8d8; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY paralleltitle
    ADD CONSTRAINT fk4cbce0713131c8d8 FOREIGN KEY (titlestatement_id) REFERENCES titlestatement(id_);


--
-- Name: fk4e7b437591110e0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY contactperson
    ADD CONSTRAINT fk4e7b437591110e0 FOREIGN KEY (distributorstatement_id) REFERENCES distributorstatement(id_);


--
-- Name: fk4e7b4375ac25d355; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY contactperson
    ADD CONSTRAINT fk4e7b4375ac25d355 FOREIGN KEY (usestatement_id) REFERENCES usestatement(id_);


--
-- Name: fk532599e96d58bb5b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY otherstudydescriptionmaterials
    ADD CONSTRAINT fk532599e96d58bb5b FOREIGN KEY (studydescription_id) REFERENCES studydescription(id_);


--
-- Name: fk5656c1e27bac1363; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY fundingagency
    ADD CONSTRAINT fk5656c1e27bac1363 FOREIGN KEY (productionstatement_id) REFERENCES productionstatement(id_);


--
-- Name: fk569b2d802cd503ee; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY selection_variable_card
    ADD CONSTRAINT fk569b2d802cd503ee FOREIGN KEY (instance_id, response_card) REFERENCES instance_documents(document_id, instance_id);


--
-- Name: fk56f5b192e2c6772; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssvariable
    ADD CONSTRAINT fk56f5b192e2c6772 FOREIGN KEY (variablerecord_id_) REFERENCES spssrecordtype2(id_);


--
-- Name: fk56f5b19f5761c5a; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssvariable
    ADD CONSTRAINT fk56f5b19f5761c5a FOREIGN KEY (valuelabelrecord_id_) REFERENCES spssrecordtype3(id_);


--
-- Name: fk570c13e1b682d825; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY auth_data
    ADD CONSTRAINT fk570c13e1b682d825 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk57274a9773ba9a2b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY unitofanalysis
    ADD CONSTRAINT fk57274a9773ba9a2b FOREIGN KEY (summarydatadescription_id) REFERENCES summarydatadescription(id_);


--
-- Name: fk57a525f91465992a; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY elsstterm
    ADD CONSTRAINT fk57a525f91465992a FOREIGN KEY (preferredterm_id_) REFERENCES elsstterm(id_);


--
-- Name: fk57a525f9a5817158; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY elsstterm
    ADD CONSTRAINT fk57a525f9a5817158 FOREIGN KEY (broaderterm_id_) REFERENCES elsstterm(id_);


--
-- Name: fk5866f621b682d825; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY user_auth_log
    ADD CONSTRAINT fk5866f621b682d825 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk588616177409ad76; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY user_settings
    ADD CONSTRAINT fk588616177409ad76 FOREIGN KEY (setting_group) REFERENCES user_settings_group(id);


--
-- Name: fk58f8fefc79f8a126; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY elsstterm_translated
    ADD CONSTRAINT fk58f8fefc79f8a126 FOREIGN KEY (translatedterm_key) REFERENCES elsstlanguage(id_);


--
-- Name: fk58f8fefc9ab0a07f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY elsstterm_translated
    ADD CONSTRAINT fk58f8fefc9ab0a07f FOREIGN KEY (translatedterm_id_) REFERENCES elsstterm(id_);


--
-- Name: fk58f8fefcec365cb6; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY elsstterm_translated
    ADD CONSTRAINT fk58f8fefcec365cb6 FOREIGN KEY (elsstterm_id_) REFERENCES elsstterm(id_);


--
-- Name: fk5a56b5c2bc14321e; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY seriesname
    ADD CONSTRAINT fk5a56b5c2bc14321e FOREIGN KEY (seriesstatement_id) REFERENCES seriesstatement(id_);


--
-- Name: fk5a8a2c9d5c1185e4; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssvariable_spssvariablecategory
    ADD CONSTRAINT fk5a8a2c9d5c1185e4 FOREIGN KEY (categorymap_id_) REFERENCES spssvariablecategory(id_);


--
-- Name: fk5a8a2c9d845a51a1; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssvariable_spssvariablecategory
    ADD CONSTRAINT fk5a8a2c9d845a51a1 FOREIGN KEY (spssvariable_id_) REFERENCES spssvariable(id_);


--
-- Name: fk5f91de83812f8a5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documents_acl
    ADD CONSTRAINT fk5f91de83812f8a5 FOREIGN KEY (document_id) REFERENCES documents(id);


--
-- Name: fk66bba25c5995be27; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY org_relations
    ADD CONSTRAINT fk66bba25c5995be27 FOREIGN KEY (org_relation_type_id) REFERENCES org_relation_type(id);


--
-- Name: fk66bba25caf40ab9d; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY org_relations
    ADD CONSTRAINT fk66bba25caf40ab9d FOREIGN KEY (org_1_id) REFERENCES org(id);


--
-- Name: fk66bba25caf411ffc; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY org_relations
    ADD CONSTRAINT fk66bba25caf411ffc FOREIGN KEY (org_2_id) REFERENCES org(id);


--
-- Name: fk68e1eb657bac1363; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY grantnumber
    ADD CONSTRAINT fk68e1eb657bac1363 FOREIGN KEY (productionstatement_id) REFERENCES productionstatement(id_);


--
-- Name: fk693b8f98131c41c5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssrecordtype7subtype11_variabledisplayparams
    ADD CONSTRAINT fk693b8f98131c41c5 FOREIGN KEY (spssrecordtype7subtype11_id_) REFERENCES spssrecordtype7subtype11(id_);


--
-- Name: fk693b8f98ed688473; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssrecordtype7subtype11_variabledisplayparams
    ADD CONSTRAINT fk693b8f98ed688473 FOREIGN KEY (variabledisplayparams_id_) REFERENCES variabledisplayparams(id_);


--
-- Name: fk6b24544219ddbfea; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY abstract
    ADD CONSTRAINT fk6b24544219ddbfea FOREIGN KEY (studyinfoandscope_id) REFERENCES studyinfoandscope(id_);


--
-- Name: fk6dd211e7dacb08d; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY category
    ADD CONSTRAINT fk6dd211e7dacb08d FOREIGN KEY (catvalu_id_) REFERENCES categoryvalue(id_);


--
-- Name: fk6dd211e83e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY category
    ADD CONSTRAINT fk6dd211e83e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fk6e9b9370cc896157; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY codebook_elsstterm
    ADD CONSTRAINT fk6e9b9370cc896157 FOREIGN KEY (codebook_id_) REFERENCES codebook(id_);


--
-- Name: fk6e9b9370ec365cb6; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY codebook_elsstterm
    ADD CONSTRAINT fk6e9b9370ec365cb6 FOREIGN KEY (elsstterm_id_) REFERENCES elsstterm(id_);


--
-- Name: fk6f78d4777bac1363; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY placeofproduction
    ADD CONSTRAINT fk6f78d4777bac1363 FOREIGN KEY (productionstatement_id) REFERENCES productionstatement(id_);


--
-- Name: fk73ad203921d67cb2; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_org_acl
    ADD CONSTRAINT fk73ad203921d67cb2 FOREIGN KEY (study_org_id) REFERENCES study_org(id);


--
-- Name: fk7493e4ef4b31abc0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datafilesdescription
    ADD CONSTRAINT fk7493e4ef4b31abc0 FOREIGN KEY (codebook_id) REFERENCES codebook(id_);


--
-- Name: fk752a03d583e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY location
    ADD CONSTRAINT fk752a03d583e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fk7557d9d229fc58d4; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY categorystatistic
    ADD CONSTRAINT fk7557d9d229fc58d4 FOREIGN KEY (category_id) REFERENCES category(id_);


--
-- Name: fk75a712cb3131c8d8; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY alternativetitle
    ADD CONSTRAINT fk75a712cb3131c8d8 FOREIGN KEY (titlestatement_id) REFERENCES titlestatement(id_);


--
-- Name: fk765f0e50dd07592a; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY setting
    ADD CONSTRAINT fk765f0e50dd07592a FOREIGN KEY (setting_group) REFERENCES setting_group(id);


--
-- Name: fk7a35f3c14b31abc0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documentdescription
    ADD CONSTRAINT fk7a35f3c14b31abc0 FOREIGN KEY (codebook_id) REFERENCES codebook(id_);


--
-- Name: fk7a35f3c1d741f2b3; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documentdescription
    ADD CONSTRAINT fk7a35f3c1d741f2b3 FOREIGN KEY (citation_id_) REFERENCES citation(id_);


--
-- Name: fk7bb166c76d58bb5b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY methodologyandprocessing
    ADD CONSTRAINT fk7bb166c76d58bb5b FOREIGN KEY (studydescription_id) REFERENCES studydescription(id_);


--
-- Name: fk7bc7ffc5b3b7c192; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY cms_page
    ADD CONSTRAINT fk7bc7ffc5b3b7c192 FOREIGN KEY (layout) REFERENCES cms_layout(id);


--
-- Name: fk7dcc90d676527d22; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_person_acl
    ADD CONSTRAINT fk7dcc90d676527d22 FOREIGN KEY (study_person_id) REFERENCES study_person(id);


--
-- Name: fk8635931f98b10015; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY category_category
    ADD CONSTRAINT fk8635931f98b10015 FOREIGN KEY (catgry_id_) REFERENCES category(id_);


--
-- Name: fk8635931fe7c080bb; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY category_category
    ADD CONSTRAINT fk8635931fe7c080bb FOREIGN KEY (category_id_) REFERENCES category(id_);


--
-- Name: fk8b79a91499747c2f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_acl
    ADD CONSTRAINT fk8b79a91499747c2f FOREIGN KEY (study_id) REFERENCES study(id);


--
-- Name: fk8b79df6e24e2e38f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_org
    ADD CONSTRAINT fk8b79df6e24e2e38f FOREIGN KEY (org_id) REFERENCES org(id);


--
-- Name: fk8b79df6e83b8b257; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_org
    ADD CONSTRAINT fk8b79df6e83b8b257 FOREIGN KEY (assoctype_id) REFERENCES study_org_asoc(id);


--
-- Name: fk8b79df6e99747c2f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_org
    ADD CONSTRAINT fk8b79df6e99747c2f FOREIGN KEY (study_id) REFERENCES study(id);


--
-- Name: fk8de12799e4597ce1; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY vartype_files
    ADD CONSTRAINT fk8de12799e4597ce1 FOREIGN KEY (vartype_id_) REFERENCES variableddi(id_);


--
-- Name: fk93df0a0b99747c2f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_descr
    ADD CONSTRAINT fk93df0a0b99747c2f FOREIGN KEY (study_id) REFERENCES study(id);


--
-- Name: fk96775995b7b38330; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY literalquestion
    ADD CONSTRAINT fk96775995b7b38330 FOREIGN KEY (question_id) REFERENCES question(id_);


--
-- Name: fk99a11617354a213f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY titlestatement
    ADD CONSTRAINT fk99a11617354a213f FOREIGN KEY (titl_id_) REFERENCES title(id_);


--
-- Name: fk9a362e9834788cf5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY weight
    ADD CONSTRAINT fk9a362e9834788cf5 FOREIGN KEY (datacollectionmethodology_id) REFERENCES datacollectionmethodology(id_);


--
-- Name: fk9b0ade2e34788cf5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY timemethod
    ADD CONSTRAINT fk9b0ade2e34788cf5 FOREIGN KEY (datacollectionmethodology_id) REFERENCES datacollectionmethodology(id_);


--
-- Name: fk9bb57cbb73ba9a2b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY geographicunit
    ADD CONSTRAINT fk9bb57cbb73ba9a2b FOREIGN KEY (summarydatadescription_id) REFERENCES summarydatadescription(id_);


--
-- Name: fk9cd7db22b58b80e; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY other_statistics
    ADD CONSTRAINT fk9cd7db22b58b80e FOREIGN KEY (variable_id) REFERENCES edited_variable(variable_id);


--
-- Name: fk9dbb5b1a17380290; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY otherstudyrelatedmaterials
    ADD CONSTRAINT fk9dbb5b1a17380290 FOREIGN KEY (otherstudyrelatedmaterials_id) REFERENCES otherstudyrelatedmaterials(id_);


--
-- Name: fk9dbb5b1a4b31abc0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY otherstudyrelatedmaterials
    ADD CONSTRAINT fk9dbb5b1a4b31abc0 FOREIGN KEY (codebook_id) REFERENCES codebook(id_);


--
-- Name: fk9dbb5b1a5e23a3b9; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY otherstudyrelatedmaterials
    ADD CONSTRAINT fk9dbb5b1a5e23a3b9 FOREIGN KEY (txt_id_) REFERENCES descriptivetext(id_);


--
-- Name: fk9dbb5b1ad741f2b3; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY otherstudyrelatedmaterials
    ADD CONSTRAINT fk9dbb5b1ad741f2b3 FOREIGN KEY (citation_id_) REFERENCES citation(id_);


--
-- Name: fk9def6397682516da; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY versionstatement
    ADD CONSTRAINT fk9def6397682516da FOREIGN KEY (citation_id) REFERENCES citation(id_);


--
-- Name: fk9def6397685866df; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY versionstatement
    ADD CONSTRAINT fk9def6397685866df FOREIGN KEY (verresp_id_) REFERENCES versionresponsibilitystatement(id_);


--
-- Name: fk9def639783e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY versionstatement
    ADD CONSTRAINT fk9def639783e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fk9def639785356d51; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY versionstatement
    ADD CONSTRAINT fk9def639785356d51 FOREIGN KEY (documentationsource_id) REFERENCES documentationsource(id_);


--
-- Name: fk9def6397cd931389; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY versionstatement
    ADD CONSTRAINT fk9def6397cd931389 FOREIGN KEY (version_id_) REFERENCES version(id_);


--
-- Name: fk9f76a49334788cf5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY typeofresearchinstrument
    ADD CONSTRAINT fk9f76a49334788cf5 FOREIGN KEY (datacollectionmethodology_id) REFERENCES datacollectionmethodology(id_);


--
-- Name: fka153757c4c0fa35; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY otheridentificationacknowledgements
    ADD CONSTRAINT fka153757c4c0fa35 FOREIGN KEY (otheridentificationacknowledgements_id) REFERENCES otheridentificationacknowledgements(id_);


--
-- Name: fka153757e3ce7624; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY otheridentificationacknowledgements
    ADD CONSTRAINT fka153757e3ce7624 FOREIGN KEY (responsibilitystatement_id) REFERENCES responsibilitystatement(id_);


--
-- Name: fka1a2c54eb682d825; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY user_setting_value
    ADD CONSTRAINT fka1a2c54eb682d825 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fka1a2c54ebc2705b8; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY user_setting_value
    ADD CONSTRAINT fka1a2c54ebc2705b8 FOREIGN KEY (user_setting_id) REFERENCES user_settings(id);


--
-- Name: fka2fc22e06cface21; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY cms_layout
    ADD CONSTRAINT fka2fc22e06cface21 FOREIGN KEY (layout_group) REFERENCES cms_layout_group(id);


--
-- Name: fka34185ce6d58bb5b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY dataaccess
    ADD CONSTRAINT fka34185ce6d58bb5b FOREIGN KEY (studydescription_id) REFERENCES studydescription(id_);


--
-- Name: fka47da23f3688cd25; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_person
    ADD CONSTRAINT fka47da23f3688cd25 FOREIGN KEY (person_id) REFERENCES person(id);


--
-- Name: fka47da23f4d92d256; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_person
    ADD CONSTRAINT fka47da23f4d92d256 FOREIGN KEY (assoc_type_id) REFERENCES instance_person_assoc(id);


--
-- Name: fka47da23f5a4f8e65; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_person
    ADD CONSTRAINT fka47da23f5a4f8e65 FOREIGN KEY (instance_id) REFERENCES instance(id);


--
-- Name: fka4f588617181fec0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY relatedmaterials
    ADD CONSTRAINT fka4f588617181fec0 FOREIGN KEY (otherstudydescriptionmaterials_id) REFERENCES otherstudydescriptionmaterials(id_);


--
-- Name: fkabca3fbe5a4f8e65; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT fkabca3fbe5a4f8e65 FOREIGN KEY (instance_id) REFERENCES instance(id);


--
-- Name: fkac8aff929fc58d4; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY descriptivetext
    ADD CONSTRAINT fkac8aff929fc58d4 FOREIGN KEY (category_id) REFERENCES category(id_);


--
-- Name: fkac8aff983e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY descriptivetext
    ADD CONSTRAINT fkac8aff983e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fkaddc6f0716dd5427; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY citation
    ADD CONSTRAINT fkaddc6f0716dd5427 FOREIGN KEY (rspstmt_id_) REFERENCES responsibilitystatement(id_);


--
-- Name: fkaddc6f0754724cef; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY citation
    ADD CONSTRAINT fkaddc6f0754724cef FOREIGN KEY (titlstmt_id_) REFERENCES titlestatement(id_);


--
-- Name: fkaddc6f076d58bb5b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY citation
    ADD CONSTRAINT fkaddc6f076d58bb5b FOREIGN KEY (studydescription_id) REFERENCES studydescription(id_);


--
-- Name: fkaddc6f07b9f1aee1; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY citation
    ADD CONSTRAINT fkaddc6f07b9f1aee1 FOREIGN KEY (diststmt_id_) REFERENCES distributorstatement(id_);


--
-- Name: fkaddc6f07bfd45843; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY citation
    ADD CONSTRAINT fkaddc6f07bfd45843 FOREIGN KEY (prodstmt_id_) REFERENCES productionstatement(id_);


--
-- Name: fkaddc6f07c9e11d73; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY citation
    ADD CONSTRAINT fkaddc6f07c9e11d73 FOREIGN KEY (biblcit_id_) REFERENCES bibliographiccitation(id_);


--
-- Name: fkaddc6f07d86aabc0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY citation
    ADD CONSTRAINT fkaddc6f07d86aabc0 FOREIGN KEY (relatedmaterials_id) REFERENCES relatedmaterials(id_);


--
-- Name: fkaddc6f07d9b01649; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY citation
    ADD CONSTRAINT fkaddc6f07d9b01649 FOREIGN KEY (serstmt_id_) REFERENCES seriesstatement(id_);


--
-- Name: fkb2872857ad67954f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY emails
    ADD CONSTRAINT fkb2872857ad67954f FOREIGN KEY (entity_type) REFERENCES org(id);


--
-- Name: fkb2872857d78a11d7; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY emails
    ADD CONSTRAINT fkb2872857d78a11d7 FOREIGN KEY (entity_id) REFERENCES person(id);


--
-- Name: fkb2f43fc791110e0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY distributor
    ADD CONSTRAINT fkb2f43fc791110e0 FOREIGN KEY (distributorstatement_id) REFERENCES distributorstatement(id_);


--
-- Name: fkb47433773131c8d8; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY identificationnumber
    ADD CONSTRAINT fkb47433773131c8d8 FOREIGN KEY (titlestatement_id) REFERENCES titlestatement(id_);


--
-- Name: fkb73a9a233dd928ef; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY catalog_study
    ADD CONSTRAINT fkb73a9a233dd928ef FOREIGN KEY (catalog_id) REFERENCES catalog(id);


--
-- Name: fkb73a9a2399747c2f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY catalog_study
    ADD CONSTRAINT fkb73a9a2399747c2f FOREIGN KEY (study_id) REFERENCES study(id);


--
-- Name: fkba4998021da66040; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY softwareusedinproduction
    ADD CONSTRAINT fkba4998021da66040 FOREIGN KEY (softwareusedinproduction_id) REFERENCES datafiledescription(id_);


--
-- Name: fkba4998027bac1363; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY softwareusedinproduction
    ADD CONSTRAINT fkba4998027bac1363 FOREIGN KEY (productionstatement_id) REFERENCES productionstatement(id_);


--
-- Name: fkba499802fc770f1f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY softwareusedinproduction
    ADD CONSTRAINT fkba499802fc770f1f FOREIGN KEY (softwareusedinproduction_id) REFERENCES softwareusedinproduction(id_);


--
-- Name: fkba66f02b58b80e; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY form_edited_text_var
    ADD CONSTRAINT fkba66f02b58b80e FOREIGN KEY (variable_id) REFERENCES edited_variable(variable_id);


--
-- Name: fkbb979bf496fa9bcf; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY address
    ADD CONSTRAINT fkbb979bf496fa9bcf FOREIGN KEY (country_id) REFERENCES country(id);


--
-- Name: fkbb979bf4ee107425; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY address
    ADD CONSTRAINT fkbb979bf4ee107425 FOREIGN KEY (city_id) REFERENCES city(id);


--
-- Name: fkbe5ca00683e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY question
    ADD CONSTRAINT fkbe5ca00683e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fkbfbea7574c1eedf8; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY numberofvariablesperrecord
    ADD CONSTRAINT fkbfbea7574c1eedf8 FOREIGN KEY (filedimensions_id) REFERENCES filedimensions(id_);


--
-- Name: fkc00e096e2d5e3e7; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY productionstatement
    ADD CONSTRAINT fkc00e096e2d5e3e7 FOREIGN KEY (copyright_id_) REFERENCES copyright(id_);


--
-- Name: fkc10693d4457aba4; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssdatarecordvariablevalue
    ADD CONSTRAINT fkc10693d4457aba4 FOREIGN KEY (variable_id_) REFERENCES spssvariable(id_);


--
-- Name: fkc10693d759a2e82; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssdatarecordvariablevalue
    ADD CONSTRAINT fkc10693d759a2e82 FOREIGN KEY (datarecord_id_) REFERENCES spssdatarecord(id_);


--
-- Name: fkc31c8b5516dd5427; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documentationsource
    ADD CONSTRAINT fkc31c8b5516dd5427 FOREIGN KEY (rspstmt_id_) REFERENCES responsibilitystatement(id_);


--
-- Name: fkc31c8b5554724cef; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documentationsource
    ADD CONSTRAINT fkc31c8b5554724cef FOREIGN KEY (titlstmt_id_) REFERENCES titlestatement(id_);


--
-- Name: fkc31c8b55b9f1aee1; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documentationsource
    ADD CONSTRAINT fkc31c8b55b9f1aee1 FOREIGN KEY (diststmt_id_) REFERENCES distributorstatement(id_);


--
-- Name: fkc31c8b55bfd45843; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documentationsource
    ADD CONSTRAINT fkc31c8b55bfd45843 FOREIGN KEY (prodstmt_id_) REFERENCES productionstatement(id_);


--
-- Name: fkc31c8b55c9e11d73; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documentationsource
    ADD CONSTRAINT fkc31c8b55c9e11d73 FOREIGN KEY (biblcit_id_) REFERENCES bibliographiccitation(id_);


--
-- Name: fkc31c8b55ca92e1a3; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documentationsource
    ADD CONSTRAINT fkc31c8b55ca92e1a3 FOREIGN KEY (documentdescription_id) REFERENCES documentdescription(id_);


--
-- Name: fkc31c8b55d9b01649; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY documentationsource
    ADD CONSTRAINT fkc31c8b55d9b01649 FOREIGN KEY (serstmt_id_) REFERENCES seriesstatement(id_);


--
-- Name: fkc3fc53643dd928ef; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY catalog_acl
    ADD CONSTRAINT fkc3fc53643dd928ef FOREIGN KEY (catalog_id) REFERENCES catalog(id);


--
-- Name: fkc4b0cd947acb005a; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY topicclasification
    ADD CONSTRAINT fkc4b0cd947acb005a FOREIGN KEY (subjectinformation_id) REFERENCES subjectinformation(id_);


--
-- Name: fkc4e39b55b7792805; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY person
    ADD CONSTRAINT fkc4e39b55b7792805 FOREIGN KEY (prefix_id) REFERENCES prefix(id);


--
-- Name: fkc4e39b55e6197ba5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY person
    ADD CONSTRAINT fkc4e39b55e6197ba5 FOREIGN KEY (suffix_id) REFERENCES suffix(id);


--
-- Name: fkc849549932384f5d; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile
    ADD CONSTRAINT fkc849549932384f5d FOREIGN KEY (longvariablenamesrecord_id_) REFERENCES spssrecordtype7subtype13(id_);


--
-- Name: fkc84954996694c234; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile
    ADD CONSTRAINT fkc84954996694c234 FOREIGN KEY (documentationrecord_id_) REFERENCES spssrecordtype6(id_);


--
-- Name: fkc84954996cb39ed7; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile
    ADD CONSTRAINT fkc84954996cb39ed7 FOREIGN KEY (variabledisplayparamsrecord_id_) REFERENCES spssrecordtype7subtype11(id_);


--
-- Name: fkc849549994756323; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile
    ADD CONSTRAINT fkc849549994756323 FOREIGN KEY (inforecord_id_) REFERENCES spssrecordtype1(id_);


--
-- Name: fkc8495499ac272c95; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile
    ADD CONSTRAINT fkc8495499ac272c95 FOREIGN KEY (variablesetsinformationrecord_id_) REFERENCES spssrecordtype7subtype5(id_);


--
-- Name: fkc8495499b5e36e65; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile
    ADD CONSTRAINT fkc8495499b5e36e65 FOREIGN KEY (floatinformationrecord_id_) REFERENCES spssrecordtype7subtype4(id_);


--
-- Name: fkc8495499d4b390c2; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY spssfile
    ADD CONSTRAINT fkc8495499d4b390c2 FOREIGN KEY (integerinformationrecord_id_) REFERENCES spssrecordtype7subtype3(id_);


--
-- Name: fkc99d8a635a4f8e65; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY methodology
    ADD CONSTRAINT fkc99d8a635a4f8e65 FOREIGN KEY (instance_id) REFERENCES instance(id);


--
-- Name: fkccdb1fc8606a1848; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY usestatement
    ADD CONSTRAINT fkccdb1fc8606a1848 FOREIGN KEY (dataaccess_id) REFERENCES dataaccess(id_);


--
-- Name: fkccdb1fc86c8fc29b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY usestatement
    ADD CONSTRAINT fkccdb1fc86c8fc29b FOREIGN KEY (disclaimer_id_) REFERENCES disclaimer(id_);


--
-- Name: fkccdb1fc89eba2765; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY usestatement
    ADD CONSTRAINT fkccdb1fc89eba2765 FOREIGN KEY (citreq_id_) REFERENCES citationrequirement(id_);


--
-- Name: fkccdb1fc8ca2ee75b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY usestatement
    ADD CONSTRAINT fkccdb1fc8ca2ee75b FOREIGN KEY (specperm_id_) REFERENCES specialpermissions(id_);


--
-- Name: fkccdb1fc8fe8a4bdb; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY usestatement
    ADD CONSTRAINT fkccdb1fc8fe8a4bdb FOREIGN KEY (deposreq_id_) REFERENCES depositrequirement(id_);


--
-- Name: fkcd4945ffd04047dc; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY cms_page_content
    ADD CONSTRAINT fkcd4945ffd04047dc FOREIGN KEY (page) REFERENCES cms_page(id);


--
-- Name: fkcda03b8383e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY invalidvaluerange
    ADD CONSTRAINT fkcda03b8383e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fkcdf2991ae3ce7624; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY authoringentity
    ADD CONSTRAINT fkcdf2991ae3ce7624 FOREIGN KEY (responsibilitystatement_id) REFERENCES responsibilitystatement(id_);


--
-- Name: fkd0248e7624e2e38f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY codebook
    ADD CONSTRAINT fkd0248e7624e2e38f FOREIGN KEY (org_id) REFERENCES org(id);


--
-- Name: fkd40ce9e05a4f8e65; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_acl
    ADD CONSTRAINT fkd40ce9e05a4f8e65 FOREIGN KEY (instance_id) REFERENCES instance(id);


--
-- Name: fkd40d203a24e2e38f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_org
    ADD CONSTRAINT fkd40d203a24e2e38f FOREIGN KEY (org_id) REFERENCES org(id);


--
-- Name: fkd40d203a5a4f8e65; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_org
    ADD CONSTRAINT fkd40d203a5a4f8e65 FOREIGN KEY (instance_id) REFERENCES instance(id);


--
-- Name: fkd40d203ab2c30327; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_org
    ADD CONSTRAINT fkd40d203ab2c30327 FOREIGN KEY (assoc_type_id) REFERENCES instance_org_assoc(id);


--
-- Name: fkded91a934b31abc0; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY studydescription
    ADD CONSTRAINT fkded91a934b31abc0 FOREIGN KEY (codebook_id) REFERENCES codebook(id_);


--
-- Name: fkdfd854034c1eedf8; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY numberofcases
    ADD CONSTRAINT fkdfd854034c1eedf8 FOREIGN KEY (filedimensions_id) REFERENCES filedimensions(id_);


--
-- Name: fke5264630682516da; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY holdings
    ADD CONSTRAINT fke5264630682516da FOREIGN KEY (citation_id) REFERENCES citation(id_);


--
-- Name: fke526463085356d51; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY holdings
    ADD CONSTRAINT fke526463085356d51 FOREIGN KEY (documentationsource_id) REFERENCES documentationsource(id_);


--
-- Name: fke7785b40b682d825; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY user_messages
    ADD CONSTRAINT fke7785b40b682d825 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fke77877a873ba9a2b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY timeperiodcovered
    ADD CONSTRAINT fke77877a873ba9a2b FOREIGN KEY (summarydatadescription_id) REFERENCES summarydatadescription(id_);


--
-- Name: fkeba1e94ec4c0fa35; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY paragraph
    ADD CONSTRAINT fkeba1e94ec4c0fa35 FOREIGN KEY (otheridentificationacknowledgements_id) REFERENCES otheridentificationacknowledgements(id_);


--
-- Name: fkebccad7993414df3; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY txttype_sdatrefs
    ADD CONSTRAINT fkebccad7993414df3 FOREIGN KEY (txttype_id_) REFERENCES descriptivetext(id_);


--
-- Name: fked2ee33b4f0afd94; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datacollectionmethodology
    ADD CONSTRAINT fked2ee33b4f0afd94 FOREIGN KEY (methodologyandprocessing_id) REFERENCES methodologyandprocessing(id_);


--
-- Name: fked2ee33b736d3149; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datacollectionmethodology
    ADD CONSTRAINT fked2ee33b736d3149 FOREIGN KEY (sources_id_) REFERENCES sourcesstatement(id_);


--
-- Name: fkef14fbad317a1d35; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY variableddi
    ADD CONSTRAINT fkef14fbad317a1d35 FOREIGN KEY (varformat_id_) REFERENCES variableformat(id_);


--
-- Name: fkef14fbada4217fc2; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY variableddi
    ADD CONSTRAINT fkef14fbada4217fc2 FOREIGN KEY (variabledescription_id) REFERENCES variabledescription(id_);


--
-- Name: fkef2cd0a73ba9a2b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY geographicalcoverage
    ADD CONSTRAINT fkef2cd0a73ba9a2b FOREIGN KEY (summarydatadescription_id) REFERENCES summarydatadescription(id_);


--
-- Name: fkefdcd26c83e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY valuerange
    ADD CONSTRAINT fkefdcd26c83e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fkefdcd26ca8cc7a06; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY valuerange
    ADD CONSTRAINT fkefdcd26ca8cc7a06 FOREIGN KEY (invalidvaluerange_id) REFERENCES invalidvaluerange(id_);


--
-- Name: fkefdcd26ccda41138; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY valuerange
    ADD CONSTRAINT fkefdcd26ccda41138 FOREIGN KEY (valuerange_id) REFERENCES valuerange(id_);


--
-- Name: fkf28cae6573ba9a2b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY universe
    ADD CONSTRAINT fkf28cae6573ba9a2b FOREIGN KEY (summarydatadescription_id) REFERENCES summarydatadescription(id_);


--
-- Name: fkf28cae6583e7a348; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY universe
    ADD CONSTRAINT fkf28cae6583e7a348 FOREIGN KEY (variableddi_id) REFERENCES variableddi(id_);


--
-- Name: fkf3ec068c34788cf5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY samplingprocedure
    ADD CONSTRAINT fkf3ec068c34788cf5 FOREIGN KEY (datacollectionmethodology_id) REFERENCES datacollectionmethodology(id_);


--
-- Name: fkf4a4a9b323e3fd33; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datasetavailability
    ADD CONSTRAINT fkf4a4a9b323e3fd33 FOREIGN KEY (origarch_id_) REFERENCES archivewherestudywasoriginallystored(id_);


--
-- Name: fkf4a4a9b3606a1848; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datasetavailability
    ADD CONSTRAINT fkf4a4a9b3606a1848 FOREIGN KEY (dataaccess_id) REFERENCES dataaccess(id_);


--
-- Name: fkf8ed73c2812f8a5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_documents
    ADD CONSTRAINT fkf8ed73c2812f8a5 FOREIGN KEY (document_id) REFERENCES documents(id);


--
-- Name: fkf8ed73c299747c2f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_documents
    ADD CONSTRAINT fkf8ed73c299747c2f FOREIGN KEY (study_id) REFERENCES study(id);


--
-- Name: fkf938a0b747c1514f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY sourcesstatement
    ADD CONSTRAINT fkf938a0b747c1514f FOREIGN KEY (sourcesstatement_id) REFERENCES sourcesstatement(id_);


--
-- Name: fkfa5ed9d43384f031; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY studyinfoandscope
    ADD CONSTRAINT fkfa5ed9d43384f031 FOREIGN KEY (subject_id_) REFERENCES subjectinformation(id_);


--
-- Name: fkfa5ed9d46d58bb5b; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY studyinfoandscope
    ADD CONSTRAINT fkfa5ed9d46d58bb5b FOREIGN KEY (studydescription_id) REFERENCES studydescription(id_);


--
-- Name: fkfb532cde7bac1363; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY dateofproduction
    ADD CONSTRAINT fkfb532cde7bac1363 FOREIGN KEY (productionstatement_id) REFERENCES productionstatement(id_);


--
-- Name: fkfc7c268b3688cd25; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_person
    ADD CONSTRAINT fkfc7c268b3688cd25 FOREIGN KEY (person_id) REFERENCES person(id);


--
-- Name: fkfc7c268b3791e33c; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_person
    ADD CONSTRAINT fkfc7c268b3791e33c FOREIGN KEY (asoctype_id) REFERENCES studiu_person_asoc(id);


--
-- Name: fkfc7c268b99747c2f; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY study_person
    ADD CONSTRAINT fkfc7c268b99747c2f FOREIGN KEY (study_id) REFERENCES study(id);


--
-- Name: fkfcaec401bcfbe38; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY cms_files
    ADD CONSTRAINT fkfcaec401bcfbe38 FOREIGN KEY (folder_id) REFERENCES cms_folders(id);


--
-- Name: fkfcb937b6175019a1; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datafiledescription
    ADD CONSTRAINT fkfcb937b6175019a1 FOREIGN KEY (spssfile_id_) REFERENCES spssfile(id_);


--
-- Name: fkfcb937b62aa87465; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datafiledescription
    ADD CONSTRAINT fkfcb937b62aa87465 FOREIGN KEY (datafilesdescription_id) REFERENCES datafilesdescription(id_);


--
-- Name: fkfcb937b6320fc513; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datafiledescription
    ADD CONSTRAINT fkfcb937b6320fc513 FOREIGN KEY (filename_id_) REFERENCES filename(id_);


--
-- Name: fkfcb937b6570c46cf; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datafiledescription
    ADD CONSTRAINT fkfcb937b6570c46cf FOREIGN KEY (verstmt_id_) REFERENCES versionstatement(id_);


--
-- Name: fkfcb937b66060f71; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datafiledescription
    ADD CONSTRAINT fkfcb937b66060f71 FOREIGN KEY (filetype_id_) REFERENCES typeoffile(id_);


--
-- Name: fkfcb937b66d564633; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY datafiledescription
    ADD CONSTRAINT fkfcb937b66d564633 FOREIGN KEY (dimensns_id_) REFERENCES filedimensions(id_);


--
-- Name: fkfd87eb8e5a4f8e65; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_documents
    ADD CONSTRAINT fkfd87eb8e5a4f8e65 FOREIGN KEY (instance_id) REFERENCES instance(id);


--
-- Name: fkfd87eb8e812f8a5; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY instance_documents
    ADD CONSTRAINT fkfd87eb8e812f8a5 FOREIGN KEY (document_id) REFERENCES documents(id);


--
-- Name: fkfeca27e75517bed; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY variableddi_elsstterm
    ADD CONSTRAINT fkfeca27e75517bed FOREIGN KEY (variableddi_id_) REFERENCES variableddi(id_);


--
-- Name: fkfeca27e7ec365cb6; Type: FK CONSTRAINT; Schema: public; Owner: roda
--

ALTER TABLE ONLY variableddi_elsstterm
    ADD CONSTRAINT fkfeca27e7ec365cb6 FOREIGN KEY (elsstterm_id_) REFERENCES elsstterm(id_);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

