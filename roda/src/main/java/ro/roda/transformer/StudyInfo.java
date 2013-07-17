package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.File;
import ro.roda.domain.Instance;
import ro.roda.domain.InstanceVariable;
import ro.roda.domain.Study;
import ro.roda.domain.StudyDescr;
import ro.roda.domain.Variable;
import flexjson.JSONSerializer;

@Configurable
public class StudyInfo {

	public static String toJsonArray(Collection<StudyInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("variables.id", "variables.concepts", "variables.fileId", "variables.formEditedNumberVars",
				"variables.instanceVariables", "variables.operatorInstructions", "variables.otherStatistics",
				"variables.selectionVariable", "variables.skips", "variables.skips1", "variables.type",
				"variables.vargroups", "variables.variableType");
		serializer.exclude("files.content", "files.fullPath", "files.id", "files.instances",
				"files.selectionVariableItems", "files.size", "files.studies1", "files.title", "files.variables");

		serializer.include("id", "name", "an", "description", "universe", "geographicCoverage", "unitAnalysis");
		serializer.include("variables.name", "variables.label");
		serializer.include("files.name", "files.contentType", "files.url", "files.description");

		serializer.transform(new FieldNameTransformer("geo_coverage"), "geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "unitAnalysis");
		// TODO transform the fields name in variables and files

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<StudyInfo> findAllStudyInfos() {
		List<StudyInfo> result = null;
		List<Study> studies = Study.findAllStudys();
		Iterator<Study> it = studies.iterator();
		result = new ArrayList<StudyInfo>();
		while (it.hasNext()) {
			Study study = (Study) it.next();
			result.add(new StudyInfo(study));
		}
		return result;
	}

	public static StudyInfo findStudyInfo(Integer id) {
		if (id == null)
			return null;
		return new StudyInfo(Study.entityManager().find(Study.class, id));
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private Integer id;

	private String name;

	private Integer an;

	// private String author;

	private String description;

	private String geographicCoverage;

	private String unitAnalysis;

	private String universe;

	private Set<Variable> variables;

	private Set<File> files;

	public StudyInfo(Study study) {
		this.id = study.getId();
		this.an = study.getYearStart();
		this.unitAnalysis = study.getUnitAnalysisId().getName();
		this.files = study.getFiles1();

		// the variables of a study are those of its 'main' instance
		this.variables = new HashSet<Variable>();
		if (study.getInstances() != null) {
			log.trace("Instances: " + study.getInstances().size());
			for (Instance instance : study.getInstances()) {
				if (instance.isMain()) {
					for (InstanceVariable iv : instance.getInstanceVariables()) {
						this.variables.add(iv.getVariableId());
					}
				}
			}
			log.trace("Variables: " + variables.size());
		}

		// TODO manage descriptions depending on language
		// for the beginning, suppose there is only one description
		StudyDescr studyDescr = null;
		if (study.getStudyDescrs() != null && study.getStudyDescrs().size() > 0) {
			studyDescr = study.getStudyDescrs().iterator().next();
		}
		if (studyDescr != null) {
			this.name = studyDescr.getTitle();
			this.description = studyDescr.getAbstract1();
			this.geographicCoverage = studyDescr.getGeographicCoverage();
			this.universe = studyDescr.getUniverse();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		this.name = pName;
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

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
