package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Study;
import ro.roda.domain.StudyDescr;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class StudyProposal extends JsonInfo {

	public static String toJsonArray(Collection<StudyProposal> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");

		serializer.transform(new FieldNameTransformer("geo_coverage"), "geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "unitAnalysis");
		serializer.transform(new FieldNameTransformer("geo_unit"), "geographicUnit");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<StudyProposal> findAllStudyProposals() {
		List<StudyProposal> result = null;
		List<Study> studies = Study.findAllStudys();
		Iterator<Study> it = studies.iterator();
		result = new ArrayList<StudyProposal>();
		while (it.hasNext()) {
			Study study = (Study) it.next();
			result.add(new StudyProposal(study));
		}
		return result;
	}

	public static StudyProposal findStudyProposal(Integer id) {
		if (id == null)
			return null;
		Study study = Study.findStudy(id);
		StudyProposal studyInfo = new StudyProposal(study);

		return studyInfo;
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private Boolean singleDate;

	private Date dateStart;

	private Date dateEnd;

	private Set<PrincipalInvestigator> principalInvestigators;

	private String geographicCoverage;

	private String unitAnalysis;

	private String universe;

	private String geographicUnit;

	public StudyProposal(Study study) {

		this.dateStart = study.getDateStart();
		this.dateEnd = study.getDateEnd();

		if (this.dateEnd == null) {
			singleDate = true;
		} else {
			singleDate = false;
		}

		this.unitAnalysis = study.getUnitAnalysisId().getName();

		setId(study.getId());

		StudyDescr studyDescr = null;
		if (study.getStudyDescrs() != null && study.getStudyDescrs().size() > 0) {
			studyDescr = study.getStudyDescrs().iterator().next();
		}
		if (studyDescr != null) {
			setName(studyDescr.getTitle());
			this.geographicCoverage = studyDescr.getGeographicCoverage();
			this.universe = studyDescr.getUniverse();
			this.geographicUnit = studyDescr.getGeographicUnit();
		}

		this.principalInvestigators.addAll(PrincipalInvestigator.findAllPrincipalInvestigators(study));

	}

	public String getGeographicCoverage() {
		return geographicCoverage;
	}

	public String getGeographicUnit() {
		return geographicUnit;
	}

	public String getUniverse() {
		return universe;
	}

	public String getUnitAnalysis() {
		return unitAnalysis;
	}

	public Boolean getSingleDate() {
		return singleDate;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public Set<PrincipalInvestigator> getPrincipalInvestigators() {
		return principalInvestigators;
	}

	public void setGeographicCoverage(String geographicCoverage) {
		this.geographicCoverage = geographicCoverage;
	}

	public void setUniverse(String universe) {
		this.universe = universe;
	}

	public void setUnitAnalysis(String unitAnalysis) {
		this.unitAnalysis = unitAnalysis;
	}

	public void setSingleDate(Boolean singleDate) {
		this.singleDate = singleDate;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setPrincipalInvestigators(Set<PrincipalInvestigator> principalInvestigators) {
		this.principalInvestigators = principalInvestigators;
	}

	public void setGeographicUnit(String geographicUnit) {
		this.geographicUnit = geographicUnit;
	}

	public String toJson() {

		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");

		serializer.transform(new FieldNameTransformer("geo_coverage"), "geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "unitAnalysis");
		serializer.transform(new FieldNameTransformer("geo_unit"), "geographicUnit");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
