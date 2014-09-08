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
import ro.roda.domain.QuestionTypeCode;
import ro.roda.domain.QuestionTypeNumeric;
import flexjson.JSONSerializer;

@Configurable
public class QuestionInfo extends JsonInfo {

	public static String toJsonArr(Collection<QuestionInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "leaf", "type");
		serializer.include("indice", "name", "respdomain");
		serializer.include("responses", "missing");

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

				if (respType.equals("code")) {
					Set<QuestionTypeCode> qstnTypeCodes = question.getQuestionTypeCodes();
					Iterator<QuestionTypeCode> iterator = qstnTypeCodes.iterator();

					while (iterator.hasNext()) {
						responseInfos.add(new ResponseInfo(iterator.next()));
					}
				} else if (respType.equals("numeric")) {
					QuestionTypeNumeric qstnTypeNumeric = question.getQuestionTypeNumeric();

					responseInfos.add(new ResponseInfo(qstnTypeNumeric));
				} else if (respType.equals("category")) {
					Set<QuestionTypeCategory> qstnTypeCategories = question.getQuestionTypeCategories();
					Iterator<QuestionTypeCategory> iterator = qstnTypeCategories.iterator();

					while (iterator.hasNext()) {
						responseInfos.add(new ResponseInfo(iterator.next()));
					}
				}

				return new QuestionInfo(question.getId(), question.getName(), respType, responseInfos,
						question.getMissingValues());
			}
		}

		return null;
	}

	private Long indice;

	private String name;

	private String respdomain;

	private Set<ResponseInfo> responses;

	private Set<MissingValue> missing;

	public QuestionInfo(Long indice, String name, String respdomain, Set<ResponseInfo> responses,
			Set<MissingValue> missing) {
		this.indice = indice;
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

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("indice", "name", "respdomain");
		serializer.include("responses", "missing");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
