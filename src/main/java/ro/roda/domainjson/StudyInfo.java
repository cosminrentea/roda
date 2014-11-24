package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.i18n.LocaleContextHolder;

import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CmsFile;
import ro.roda.domain.Instance;
import ro.roda.domain.Keyword;
import ro.roda.domain.Lang;
import ro.roda.domain.Org;
import ro.roda.domain.Person;
import ro.roda.domain.Series;
import ro.roda.domain.Study;
import ro.roda.domain.StudyDescr;
import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyOrg;
import ro.roda.domain.StudyPerson;
import ro.roda.domain.Topic;
import ro.roda.domain.TranslatedTopic;
import ro.roda.domain.TranslatedTopicPK;
import ro.roda.domain.Variable;
import ro.roda.transformer.FieldNameTransformer;
import ro.roda.transformer.FlatQuestionMinInfoTransformer;
import flexjson.JSONSerializer;

@Configurable
public class StudyInfo extends JsonInfo {

	public static String toJsonArray(Collection<StudyInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		// serializer.exclude("*.class");
		// serializer.exclude("leaf", "variables.concepts", "variables.fileId",
		// "variables.formEditedNumberVars",
		// "variables.instanceVariables", "variables.operatorInstructions",
		// "variables.orderInQuestion",
		// "variables.questionId", "variables.otherStatistics",
		// "variables.selectionVariable", "variables.skips",
		// "variables.skips1", "variables.type", "variables.vargroups",
		// "variables.variableType",
		// "variables.auditReader", "variables.classAuditReader");
		// serializer.exclude("files.content", "files.fullPath", "files.id",
		// "files.instances",
		// "files.selectionVariableItems", "files.size", "files.studies1",
		// "files.title", "files.variables",
		// "files.auditReader", "files.classAuditReader");
		// serializer.exclude("persons.forms", "persons.instancepeople",
		// "persons.personAddresses",
		// "persons.personEmails", "persons.personInternets",
		// "persons.personLinkss", "persons.personOrgs",
		// "persons.personPhones", "persons.prefixId", "persons.studypeople",
		// "persons.suffixId",
		// "persons.auditReader", "persons.classAuditReader");
		// serializer.exclude("orgs.instanceOrgs", "orgs.orgAddresses",
		// "orgs.orgEmails", "orgs.orgInternets",
		// "orgs.orgPhones", "orgs.orgPrefixId", "orgs.orgRelationss",
		// "orgs.orgRelationss1", "orgs.orgSufixId",
		// "orgs.personOrgs", "orgs.shortName", "orgs.studyOrgs",
		// "orgs.auditReader", "orgs.classAuditReader");
		// serializer.exclude("keywords.studyKeywords", "keywords.auditReader",
		// "keywords.classAuditReader");

		serializer.include("id", "name", "an", "description", "universe", "geographicCoverage", "unitAnalysis", "type",
				"geographicUnit", "researchInstrument", "weighting", "seriesId");
		serializer.include("variables.id", "variables.name", "variables.label", "variables.questionId.id",
				"variables.questionId.statement");
		serializer.include("files.name", "files.contentType", "files.url", "files.description");
		serializer.include("persons.id", "persons.lname", "persons.fname", "persons.mname");
		serializer.include("orgs.id", "orgs.fullName");
		serializer.include("keywords.id", "keywords.name");

		// exclude ALL other fields
		serializer.exclude("*");
		serializer.exclude("*.*");

		serializer.transform(new FlatQuestionMinInfoTransformer(""), "variables.questionId");

		serializer.transform(new FieldNameTransformer("geo_coverage"), "geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "unitAnalysis");
		serializer.transform(new FieldNameTransformer("research_instrument"), "researchInstrument");
		serializer.transform(new FieldNameTransformer("geo_unit"), "geographicUnit");
		serializer.transform(new FieldNameTransformer("indice"), "variables.id");

		return serializer.rootName("data").serialize(collection);
	}

	public static List<StudyInfo> findAllStudyInfos() {
		List<StudyInfo> result = null;
		List<Study> studies = Study.findAllStudys();
		Iterator<Study> it = studies.iterator();
		result = new ArrayList<StudyInfo>();
		while (it.hasNext()) {
			Study study = (Study) it.next();
			result.add(new StudyInfo(study, false, false, false, false, false, false));
		}
		return result;
	}

	public static StudyInfo findStudyInfo(Integer id) {
		if (id == null) {
			return null;
		}
		return new StudyInfo(Study.findStudy(id), true, true, true, true, true, true);
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private Integer an;

	// private String author;

	private String description;

	private String geographicCoverage;

	private String unitAnalysis;

	private String universe;

	private String weighting;

	private String geographicUnit;

	private String researchInstrument;

	private Boolean leaf = true;

	private List<Variable> variables;

	private Set<CmsFile> files;

	private Set<Person> persons;

	private Set<Org> orgs;

	private Set<Keyword> keywords;

	private Set<TranslatedTopic> translatedTopics;

	private Integer seriesId;

	public StudyInfo(Study study) {
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
		this.an = study.getYearStart();
		this.unitAnalysis = study.getUnitAnalysisId().getName();

		setId(study.getId());

		// setting of files and variables should be invoked separately (in find
		// methods), due to
		// the fact that the constructor of this class is invoked by classes
		// that should not provide files and variables from the StudyInfo
		// objects
		// this.files = study.getFiles1();

		// the variables of a study are those of its 'main' instance
		// this.variables = new HashSet<Variable>();

		// if (study.getInstances() != null) {
		// // log.trace("Instances: " + study.getInstances().size());
		// for (Instance instance : study.getInstances()) {
		// if (instance.isMain() && instance.getInstanceVariables() != null) {
		// for (InstanceVariable iv : instance.getInstanceVariables()) {
		// this.variables.add(iv.getVariableId());
		// }
		// }
		// }
		// // log.trace("Variables: " + variables.size());
		// }

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

	public StudyInfo(Study study, boolean hasVariables, boolean hasFiles, boolean hasPersons, boolean hasOrgs,
			boolean hasKeywords, boolean hasTopics) {
		this(study);

		// set the variables
		// variables of a study are all those of its 'main' instance

		// if (hasVariables) {
		//
		// Map<Integer, Variable> orderVar = new TreeMap<Integer, Variable>();
		// Set<Variable> variables = new LinkedHashSet<Variable>();
		//
		// if (study.getInstances() != null) {
		// // log.trace("Instances: " + study.getInstances().size());
		// for (Instance instance : study.getInstances()) {
		// if (instance.isMain() && instance.getQuestions() != null) {
		// int orderVariableInInstance = 0;
		// for (Question q : instance.getQuestions()) {
		// // TODO: add the order of the question in the
		// // instance (to be validated) and determine the
		// // right position of the variable in the instance
		// for (Variable v : q.getVariables()) {
		// orderVar.put(orderVariableInInstance++, v);
		// }
		// }
		// }
		// }
		//
		// SortedSet<Integer> keys = new TreeSet<Integer>(orderVar.keySet());
		// for (Integer key : keys) {
		// log.trace("Adding variable " + key);
		// variables.add(orderVar.get(key));
		// }
		//
		// // log.trace("Variables: " + variables.size());
		// }
		// this.setVariables(variables);
		// }

		if (hasVariables && (study.getInstances() != null)) {

			// get all the variables of the study's 'main' instance
			for (Instance instance : study.getInstances()) {
				if (instance.isMain()) {
					// this.setVariables(Variable
					// .entityManager()
					// .createQuery(
					// "SELECT v FROM Variable v WHERE v.questionId.instanceId = :instanceId ORDER BY v.id",
					// Variable.class).setParameter("instanceId",
					// instance).getResultList());
					this.setVariables(Variable
							.entityManager()
							.createQuery(
									"SELECT v FROM Variable v INNER JOIN v.questionId q INNER JOIN q.instances i WHERE i=:instanceId ORDER BY v.id",
									Variable.class).setParameter("instanceId", instance).getResultList());
					break; // because there is only one 'main' instance
				}
			}
		}

		// set the files
		if (hasFiles) {
			this.setFiles(study.getCmsFiles());
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

		if (hasTopics) {
			this.translatedTopics = new HashSet<TranslatedTopic>();
			Set<Topic> topicSet = study.getTopics();
			Integer currentLanguageId = Lang.findLang(LocaleContextHolder.getLocale().getLanguage()).getId();
			for (Topic topic : topicSet) {
				TranslatedTopic tt = TranslatedTopic.findTranslatedTopic(new TranslatedTopicPK(currentLanguageId, topic
						.getId()));
				this.translatedTopics.add(tt);
			}
		}
	}

	public Integer getAn() {
		return an;
	}

	public void setAn(Integer an) {
		this.an = an;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Set<CmsFile> getFiles() {
		return files;
	}

	public void setFiles(Set<CmsFile> files) {
		this.files = files;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
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

	public Set<TranslatedTopic> getTranslatedTopics() {
		return translatedTopics;
	}

	public void setTranslatedTopics(Set<TranslatedTopic> tt) {
		this.translatedTopics = tt;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String toJson() {

		JSONSerializer serializer = new JSONSerializer();

		// serializer.exclude("*.class");

		// serializer.exclude("leaf", "variables.concepts", "variables.fileId",
		// "variables.formEditedNumberVars",
		// "variables.instanceVariables", "variables.operatorInstructions",
		// "variables.orderInQuestion",
		// "variables.questionId", "variables.otherStatistics",
		// "variables.selectionVariable", "variables.skips",
		// "variables.skips1", "variables.type", "variables.vargroups",
		// "variables.variableType",
		// "variables.auditReader", "variables.classAuditReader");
		// serializer.exclude("files.content", "files.fullPath", "files.id",
		// "files.instances",
		// "files.selectionVariableItems", "files.size", "files.studies1",
		// "files.title", "files.variables",
		// "files.auditReader", "files.classAuditReader");
		// serializer.exclude("persons.forms", "persons.instancepeople",
		// "persons.personAddresses",
		// "persons.personEmails", "persons.personInternets",
		// "persons.personLinkss", "persons.personOrgs",
		// "persons.personPhones", "persons.prefixId", "persons.studypeople",
		// "persons.suffixId",
		// "persons.auditReader", "persons.classAuditReader");
		// serializer.exclude("orgs.instanceOrgs", "orgs.orgAddresses",
		// "orgs.orgEmails", "orgs.orgInternets",
		// "orgs.orgPhones", "orgs.orgPrefixId", "orgs.orgRelationss",
		// "orgs.orgRelationss1", "orgs.orgSufixId",
		// "orgs.personOrgs", "orgs.shortName", "orgs.studyOrgs",
		// "orgs.auditReader", "orgs.classAuditReader");
		// serializer.exclude("keywords.studyKeywords", "keywords.auditReader",
		// "keywords.classAuditReader");

		// include ONLY what is needed
		serializer.include("id", "name", "an", "description", "universe", "geographicCoverage", "unitAnalysis", "type",
				"geographicUnit", "researchInstrument", "weighting", "seriesId");
		serializer.include("variables.id", "variables.name", "variables.label", "variables.questionId.id",
				"variables.questionId.statement");
		serializer.include("files.filename", "files.contentType", "files.url", "files.description");
		serializer.include("persons.id", "persons.lname", "persons.fname", "persons.mname");
		serializer.include("orgs.id", "orgs.fullName");
		serializer.include("keywords.id", "keywords.name");
		serializer.include("translatedTopics.translation");
		serializer.include("translatedTopics.indice");

		// exclude ALL other fields
		serializer.exclude("*");
		serializer.exclude("*.*");

		serializer.transform(new FlatQuestionMinInfoTransformer(""), "variables.questionId");

		serializer.transform(new FieldNameTransformer("geo_coverage"), "geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "unitAnalysis");
		serializer.transform(new FieldNameTransformer("research_instrument"), "researchInstrument");
		serializer.transform(new FieldNameTransformer("geo_unit"), "geographicUnit");
		serializer.transform(new FieldNameTransformer("indice"), "variables.id");
		String result = serializer.rootName("data").serialize(this);
		// log.trace(result);
		return result;
	}
}
