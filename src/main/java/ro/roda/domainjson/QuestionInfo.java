package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.MissingValue;
import ro.roda.domain.Question;
import ro.roda.domain.QuestionTypeCategory;
import ro.roda.domain.QuestionTypeNumeric;
import ro.roda.domain.QuestionTypeString;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class QuestionInfo extends JsonInfo {

	public static String toJsonArr(Collection<QuestionInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "leaf", "type", "id");
		serializer.include("indice", "label", "name", "respdomain");

		String respdom = collection.iterator().next().getRespdomain();

		if (respdom.equals("Code")) {

			serializer.include("responses.label", "responses.numberValue", "responses.id");

			// serializer.exclude("responses.dataType", "responses.high",
			// "responses.interpretation", "responses.low",
			// "responses.numericValues", "responses.respType",
			// "responses.stringValue", "responses.type");

			// serializer.transform(new FieldNameTransformer("value"),
			// "responses.numberValue");
			serializer.transform(new FieldNameTransformer("coderesponses"), "responses");

		} else if (respdom.equals("Numeric")) {

			serializer.include("numericresponses.type", "numericresponses.high", "numericresponses.low");

			// serializer.exclude("responses.id", "responses.label",
			// "responses.interpretation", "responses.name",
			// "responses.numberValue", "responses.numericValues",
			// "responses.respType", "responses.stringValue",
			// "responses.type");

			// serializer.transform(new FieldNameTransformer("type"),
			// "responses.dataType");
			serializer.transform(new FieldNameTransformer("numericresponses"), "responses");

		} else if (respdom.equals("Category")) {

			serializer.include("responses.label", "responses.id");

			// serializer.exclude("responses.dataType", "responses.high",
			// "responses.interpretation", "responses.low",
			// "responses.name", "responses.numberValue",
			// "responses.numericValues", "responses.respType",
			// "responses.stringValue", "responses.type");

			// serializer.transform(new FieldNameTransformer("value"),
			// "responses.numberValue");

			// TODO: replace with: "categoryresponses"
			serializer.transform(new FieldNameTransformer("coderesponses"), "responses");

		}

		serializer.include("missing");

		// serializer.transform(new FlatPageTypeTransformer("pagetype"),
		// "questionUsage.PageTypeId");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<QuestionInfo> findAllQuestionInfos() {
		List<QuestionInfo> result = new ArrayList<QuestionInfo>();

		List<Question> questions = Question.findAllQuestions();

		if (questions != null && questions.size() > 0) {

			Iterator<Question> questionsIterator = questions.iterator();
			while (questionsIterator.hasNext()) {
				result.add(findQuestionInfo(questionsIterator.next().getId()));
			}
		}

		return result;
	}

	public static QuestionInfo findQuestionInfo(Long id) {

		Question question = Question.findQuestion(id);

		if (question != null) {
			Set<ResponseInfo> responseInfos = new HashSet<ResponseInfo>();

			if (question.getQuestionTypeId() != null) {
				String respType = question.getQuestionTypeId().getName();

				if (respType.equals("Code")) {
					Set<QuestionTypeString> qstnTypeCodes = question.getQuestionTypeCodes();
					Iterator<QuestionTypeString> iterator = qstnTypeCodes.iterator();

					while (iterator.hasNext()) {
						responseInfos.add(new ResponseInfo(iterator.next()));
					}
				} else if (respType.equals("Numeric")) {
					QuestionTypeNumeric qstnTypeNumeric = question.getQuestionTypeNumeric();

					responseInfos.add(new ResponseInfo(qstnTypeNumeric));
				} else if (respType.equals("Category")) {
					Set<QuestionTypeCategory> qstnTypeCategories = question.getQuestionTypeCategories();
					Iterator<QuestionTypeCategory> iterator = qstnTypeCategories.iterator();

					while (iterator.hasNext()) {
						responseInfos.add(new ResponseInfo(iterator.next()));
					}
				}

				return new QuestionInfo(question.getId(), question.getStatement(), question.getName(), respType,
						responseInfos, question.getMissingValues());
			}
		}

		return null;
	}

	private Long indice;

	private String name;

	private String label;

	private String respdomain;

	private Set<ResponseInfo> responses;

	private Set<MissingValue> missing;

	public QuestionInfo(Long indice, String label, String name, String respdomain, Set<ResponseInfo> responses,
			Set<MissingValue> missing) {
		this.indice = indice;
		this.label = label;
		this.name = name;
		this.respdomain = respdomain;
		this.responses = responses;
		this.missing = missing;
	}

	public Long getIndice() {
		return indice;
	}

	public void setIndice(Long indice) {
		this.indice = indice;
	}

	public String getName() {
		return name;
	}

	public String getRespdomain() {
		return respdomain;
	}

	public Set<ResponseInfo> getResponses() {
		return responses;
	}

	public Set<MissingValue> getMissing() {
		return missing;
	}

	public String getLabel() {
		return label;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRespdomain(String respdomain) {
		this.respdomain = respdomain;
	}

	public void setResponses(Set<ResponseInfo> responses) {
		this.responses = responses;
	}

	public void setMissing(Set<MissingValue> missing) {
		this.missing = missing;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf", "id");
		serializer.include("indice", "name", "respdomain");
		serializer.include("missing", "responses");

		if (respdomain.equals("Code")) {

			serializer.include("responses.label", "responses.numberValue", "responses.id");

			serializer.exclude("responses.name", "responses.type", "responses.respType", "responses.dataType",
					"responses.stringValue", "responses.high", "responses.low", "responses.interpretation",
					"responses.numericValues");

			serializer.transform(new FieldNameTransformer("value"), "responses.numberValue");

			// TODO: how to change a collection's name in flexjson?
			// serializer.transform(new FieldNameTransformer("coderesponses"),
			// "responses");

		} else if (respdomain.equals("Numeric")) {

			serializer.include("responses.dataType", "responses.high", "responses.low");

			serializer.exclude("responses.name", "responses.type", "responses.id", "responses.label",
					"responses.interpretation", "responses.numberValue", "responses.numericValues",
					"responses.respType", "responses.stringValue");

			serializer.transform(new FieldNameTransformer("type"), "responses.dataType");

			// TODO: how to change a collection's name in flexjson?
			// serializer.transform(new
			// FieldNameTransformer("numericresponses"), "responses");

		} else if (respdomain.equals("Category")) {

			serializer.include("responses.label", "responses.id");

			serializer.exclude("responses.name", "responses.type", "responses.dataType", "responses.high",
					"responses.interpretation", "responses.low", "responses.numberValue", "responses.numericValues",
					"responses.respType", "responses.stringValue");

			// TODO: how to change a collection's name in flexjson?
			// serializer.transform(new
			// FieldNameTransformer("categoryresponses"), "responses");

		}

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
