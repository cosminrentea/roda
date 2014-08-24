package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CatalogStudy;
import ro.roda.domain.File;
import ro.roda.domain.Instance;
import ro.roda.domain.Keyword;
import ro.roda.domain.Org;
import ro.roda.domain.Person;
import ro.roda.domain.Question;
import ro.roda.domain.Series;
import ro.roda.domain.Study;
import ro.roda.domain.StudyDescr;
import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyOrg;
import ro.roda.domain.StudyPerson;
import ro.roda.domain.Variable;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

//TODO serialize all infos; not so many excludes
@Configurable
public class StudyFullInfo extends JsonInfo {

	public static String toJsonArray(Collection<StudyFullInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("leaf", "variables.concepts", "variables.fileId", "variables.formEditedNumberVars",
				"variables.instanceVariables", "variables.operatorInstructions", "variables.orderInQuestion",
				"variables.questionId", "variables.otherStatistics", "variables.selectionVariable", "variables.skips",
				"variables.skips1", "variables.type", "variables.vargroups", "variables.variableType",
				"variables.auditReader", "variables.classAuditReader");
		serializer.exclude("files.content", "files.fullPath", "files.id", "files.instances",
				"files.selectionVariableItems", "files.size", "files.studies1", "files.title", "files.variables",
				"files.auditReader", "files.classAuditReader");
		serializer.exclude("persons.forms", "persons.instancepeople", "persons.personAddresses",
				"persons.personEmails", "persons.personInternets", "persons.personLinkss", "persons.personOrgs",
				"persons.personPhones", "persons.prefixId", "persons.studypeople", "persons.suffixId",
				"persons.auditReader", "persons.classAuditReader");
		serializer.exclude("orgs.instanceOrgs", "orgs.orgAddresses", "orgs.orgEmails", "orgs.orgInternets",
				"orgs.orgPhones", "orgs.orgPrefixId", "orgs.orgRelationss", "orgs.orgRelationss1", "orgs.orgSufixId",
				"orgs.personOrgs", "orgs.shortName", "orgs.studyOrgs", "orgs.auditReader", "orgs.classAuditReader");
		serializer.exclude("keywords.studyKeywords", "keywords.auditReader", "keywords.classAuditReader");

		serializer.include("id", "name", "an", "description", "universe", "geographicCoverage", "unitAnalysis", "type",
				"geographicUnit", "researchInstrument", "weighting", "seriesId");
		serializer.include("variables.id", "variables.name", "variables.label");
		serializer.include("files.name", "files.contentType", "files.url", "files.description");
		serializer.include("persons.id", "persons.lname", "persons.fname", "persons.mname");
		serializer.include("orgs.id", "orgs.fullName");
		serializer.include("keywords.id", "keywords.name");

		serializer.transform(new FieldNameTransformer("date_start"), "startDate");
		serializer.transform(new FieldNameTransformer("date_end"), "endDate");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "unitAnalysis");
		serializer.transform(new FieldNameTransformer("research_instrument"), "researchInstrument");
		serializer.transform(new FieldNameTransformer("geo_unit"), "geographicUnit");
		serializer.transform(new FieldNameTransformer("indice"), "variables.id");
		// serializer.transform(new FieldNameTransformer("series"), "seriesId");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<StudyFullInfo> findAllStudyInfos() {
		List<StudyFullInfo> result = null;
		List<Study> studies = Study.findAllStudys();
		Iterator<Study> it = studies.iterator();
		result = new ArrayList<StudyFullInfo>();
		while (it.hasNext()) {
			Study study = (Study) it.next();
			result.add(new StudyFullInfo(study, false, false, false, false, false));
		}
		return result;
	}

	public static StudyFullInfo findStudyInfo(Integer id) {
		if (id == null)
			return null;
		Study study = Study.findStudy(id);
		StudyFullInfo studyInfo = new StudyFullInfo(study, true, true, true, true, true);

		return studyInfo;
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private Date startDate;

	private Date endDate;

	// private String author;

	private String description;

	private String geographicCoverage;

	private String unitAnalysis;

	private String universe;

	private String weighting;

	private String geographicUnit;

	private String researchInstrument;

	private Boolean leaf = true;

	private Set<Variable> variables;

	private Set<File> files;

	private Set<Person> persons;

	private Set<Org> orgs;

	private Set<Keyword> keywords;

	private Set<DdiEditorPrincipalInvestigator> principalInvestigators;

	private Integer seriesId;

	public StudyFullInfo() {
	}

	public StudyFullInfo(Study study) {
		Series series = null;

		// find the series of a study
		Set<CatalogStudy> catalogs = study.getCatalogStudies();
		if (catalogs != null && catalogs.size() > 0) {
			Iterator<CatalogStudy> catalogsIterator = catalogs.iterator();
			while (series == null && catalogsIterator.hasNext()) {
				series = catalogsIterator.next().getCatalogId().getSeries();
			}
		}

		if (series != null) {
			setType(JsonInfo.SERIES_STUDY_TYPE);
			setSeriesId(series.getCatalogId());
		} else {
			setType(JsonInfo.STUDY_TYPE);
		}
		this.startDate = study.getDateStart();
		this.endDate = study.getDateEnd();
		this.unitAnalysis = study.getUnitAnalysisId().getName();

		setId(study.getId());

		// TODO manage descriptions depending on language
		// for the beginning, assume there is only one description
		StudyDescr studyDescr = null;
		if (study.getStudyDescrs() != null && study.getStudyDescrs().size() > 0) {
			studyDescr = study.getStudyDescrs().iterator().next();
		}
		if (studyDescr != null) {
			setName(studyDescr.getTitle());
			this.description = studyDescr.getAbstract1();
			this.geographicCoverage = studyDescr.getGeographicCoverage();
			this.universe = studyDescr.getUniverse();
			this.weighting = studyDescr.getWeighting();
			this.geographicUnit = studyDescr.getGeographicUnit();
			this.researchInstrument = studyDescr.getResearchInstrument();
		}

	}

	public StudyFullInfo(Study study, boolean hasVariables, boolean hasFiles, boolean hasPersons, boolean hasOrgs,
			boolean hasKeywords) {
		this(study);

		// set the files
		if (hasFiles) {
			this.setFiles(study.getFiles1());
		}

		// set the variables
		// variables of a study are those of its 'main' instance
		if (hasVariables) {

			Map<Integer, Variable> orderVar = new TreeMap<Integer, Variable>();
			Set<Variable> variables = new LinkedHashSet<Variable>();

			if (study.getInstances() != null && study.getInstances().size() > 0) {
				// log.trace("Instances: " + study.getInstances().size());
				for (Instance instance : study.getInstances()) {
					if (instance.isMain() && instance.getQuestions() != null) {
						int orderVariableInInstance = 0;
						for (Question q : instance.getQuestions()) {
							// TODO: add the order of the question in the
							// instance (to be validated) and determine the
							// right position of the variable in the instance
							for (Variable v : q.getVariables()) {
								orderVar.put(orderVariableInInstance++, v);
							}
						}
					}
				}

				SortedSet<Integer> keys = new TreeSet<Integer>(orderVar.keySet());
				for (Integer key : keys) {
					log.trace("Adding variable " + key);
					variables.add(orderVar.get(key));
				}

				// log.trace("Variables: " + variables.size());
			}
			this.setVariables(variables);
		}

		// set the persons and organizations
		if (hasPersons) {
			this.persons = new HashSet<Person>();
			Set<StudyPerson> studyPersons = study.getStudypeople();
			if (studyPersons != null && studyPersons.size() > 0) {
				Iterator<StudyPerson> studyPersonIterator = studyPersons.iterator();
				while (studyPersonIterator.hasNext()) {
					this.persons.add(studyPersonIterator.next().getPersonId());
				}
			}
		}

		if (hasOrgs) {
			this.orgs = new HashSet<Org>();
			Set<StudyOrg> studyOrgs = study.getStudyOrgs();
			if (studyOrgs != null && studyOrgs.size() > 0) {
				Iterator<StudyOrg> studyOrgIterator = studyOrgs.iterator();
				while (studyOrgIterator.hasNext()) {
					this.orgs.add(studyOrgIterator.next().getOrgId());
				}
			}
		}

		if (hasKeywords) {
			this.keywords = new HashSet<Keyword>();
			Set<StudyKeyword> studyKeywords = study.getStudyKeywords();
			if (studyKeywords != null && studyKeywords.size() > 0) {
				Iterator<StudyKeyword> studyKeywordIterator = studyKeywords.iterator();
				while (studyKeywordIterator.hasNext()) {
					this.keywords.add(studyKeywordIterator.next().getKeywordId());
				}
			}
		}

	}

	public String getDescription() {
		return description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getGeographicCoverage() {
		return geographicCoverage;
	}

	public void setGeographicCoverage(String geographicCoverage) {
		this.geographicCoverage = geographicCoverage;
	}

	public String getUniverse() {
		return universe;
	}

	public void setUniverse(String universe) {
		this.universe = universe;
	}

	public String getUnitAnalysis() {
		return unitAnalysis;
	}

	public void setUnitAnalysis(String unitAnalysis) {
		this.unitAnalysis = unitAnalysis;
	}

	public String getGeographicUnit() {
		return geographicUnit;
	}

	public String getWeighting() {
		return weighting;
	}

	public String getResearchInstrument() {
		return researchInstrument;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public Set<Variable> getVariables() {
		return variables;
	}

	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
	}

	public void setGeographicUnit(String geographicUnit) {
		this.geographicUnit = geographicUnit;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	public Set<Org> getOrgs() {
		return orgs;
	}

	public void setOrgs(Set<Org> orgs) {
		this.orgs = orgs;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public Set<DdiEditorPrincipalInvestigator> getPrincipalInvestigators() {
		return principalInvestigators;
	}

	public void setPrincipalInvestigators(Set<DdiEditorPrincipalInvestigator> principalInvestigators) {
		this.principalInvestigators = principalInvestigators;
	}

	public String toJson() {

		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("leaf", "variables.concepts", "variables.fileId", "variables.formEditedNumberVars",
				"variables.instanceVariables", "variables.operatorInstructions", "variables.orderInQuestion",
				"variables.questionId", "variables.otherStatistics", "variables.selectionVariable", "variables.skips",
				"variables.skips1", "variables.type", "variables.vargroups", "variables.variableType",
				"variables.auditReader", "variables.classAuditReader");
		serializer.exclude("files.content", "files.fullPath", "files.id", "files.instances",
				"files.selectionVariableItems", "files.size", "files.studies1", "files.title", "files.variables",
				"files.auditReader", "files.classAuditReader");
		serializer.exclude("persons.forms", "persons.instancepeople", "persons.personAddresses",
				"persons.personEmails", "persons.personInternets", "persons.personLinkss", "persons.personOrgs",
				"persons.personPhones", "persons.prefixId", "persons.studypeople", "persons.suffixId",
				"persons.auditReader", "persons.classAuditReader");
		serializer.exclude("orgs.instanceOrgs", "orgs.orgAddresses", "orgs.orgEmails", "orgs.orgInternets",
				"orgs.orgPhones", "orgs.orgPrefixId", "orgs.orgRelationss", "orgs.orgRelationss1", "orgs.orgSufixId",
				"orgs.personOrgs", "orgs.shortName", "orgs.studyOrgs", "orgs.auditReader", "orgs.classAuditReader");
		serializer.exclude("keywords.studyKeywords", "keywords.auditReader", "keywords.classAuditReader");

		serializer.include("id", "name", "an", "description", "universe", "geographicCoverage", "unitAnalysis", "type",
				"geographicUnit", "researchInstrument", "weighting", "seriesId");
		serializer.include("variables.id", "variables.name", "variables.label");
		serializer.include("files.name", "files.contentType", "files.url", "files.description");
		serializer.include("persons.id", "persons.lname", "persons.fname", "persons.mname");
		serializer.include("orgs.id", "orgs.fullName");
		serializer.include("keywords.id", "keywords.name");

		serializer.transform(new FieldNameTransformer("date_start"), "startDate");
		serializer.transform(new FieldNameTransformer("date_end"), "endDate");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "unitAnalysis");
		serializer.transform(new FieldNameTransformer("research_instrument"), "researchInstrument");
		serializer.transform(new FieldNameTransformer("geo_unit"), "geographicUnit");
		serializer.transform(new FieldNameTransformer("indice"), "variables.id");
		// serializer.transform(new FieldNameTransformer("series"), "seriesId");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
