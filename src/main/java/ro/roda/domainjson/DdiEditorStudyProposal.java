package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.StudySaved;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
// TODO serielize correctly, including StudyFullInfo
public class DdiEditorStudyProposal extends StudyFullInfo {

	public static String toJsonArr(Collection<DdiEditorStudyProposal> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");

		serializer.transform(new FieldNameTransformer("date_start"), "dateStart");
		serializer.transform(new FieldNameTransformer("date_end"), "dateEnd");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "unitAnalysis");
		serializer.transform(new FieldNameTransformer("geo_unit"), "geographicUnit");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<DdiEditorStudyProposal> findAllDdiEditorStudyProposals() {
		List<DdiEditorStudyProposal> result = null;
		List<StudySaved> studies = StudySaved.findAllStudySaveds();
		Iterator<StudySaved> it = studies.iterator();
		result = new ArrayList<DdiEditorStudyProposal>();
		while (it.hasNext()) {
			StudySaved studySaved = (StudySaved) it.next();
			result.add(new DdiEditorStudyProposal(studySaved));
		}
		return result;
	}

	public static DdiEditorStudyProposal findDdiEditorStudyProposal(Integer id) {
		if (id == null)
			return null;
		StudySaved studySaved = StudySaved.findStudySaved(id);
		DdiEditorStudyProposal studyProposal = new DdiEditorStudyProposal(studySaved);

		return studyProposal;
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private Boolean singleDate;

	public DdiEditorStudyProposal(StudySaved studySaved) {

		if (studySaved.getContentType().toLowerCase().contains("json")) {
			String stContent = studySaved.getContent();

			Map<String, StudyFullInfo> deserialized = new JSONDeserializer<Map<String, Map<String, StudyFullInfo>>>()
					.use("values.values", StudyFullInfo.class).deserialize(stContent).get("data");

			StudyFullInfo tmpStudy = deserialized.get(studySaved.getName());
			setStartDate(tmpStudy.getStartDate());
			setEndDate(tmpStudy.getEndDate());

			if (getEndDate() == null) {
				singleDate = true;
			} else {
				singleDate = false;
			}

			// TODO all!
			setUnitAnalysis(tmpStudy.getUnitAnalysis());
			setDescription(tmpStudy.getDescription());
			setGeographicCoverage(tmpStudy.getGeographicCoverage());
			setUniverse(tmpStudy.getUniverse());
			setGeographicUnit(tmpStudy.getGeographicUnit());

			// get principal investigators

			setPrincipalInvestigators(tmpStudy.getPrincipalInvestigators());
		}

	}

	public Boolean getSingleDate() {
		return singleDate;
	}

	public void setSingleDate(Boolean singleDate) {
		this.singleDate = singleDate;
	}

	public String toJson() {

		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");

		serializer.transform(new FieldNameTransformer("date_start"), "dateStart");
		serializer.transform(new FieldNameTransformer("date_end"), "dateEnd");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "unitAnalysis");
		serializer.transform(new FieldNameTransformer("geo_unit"), "geographicUnit");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
