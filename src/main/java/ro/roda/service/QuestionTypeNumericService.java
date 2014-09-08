package ro.roda.service;

import java.util.List;

import ro.roda.domain.QuestionTypeNumeric;

public interface QuestionTypeNumericService {

	public abstract long countAllQuestionTypeNumerics();

	public abstract void deleteQuestionTypeNumeric(QuestionTypeNumeric questionTypeNumeric);

	public abstract QuestionTypeNumeric findQuestionTypeNumeric(Long id);

	public abstract List<QuestionTypeNumeric> findAllQuestionTypeNumerics();

	public abstract List<QuestionTypeNumeric> findQuestionTypeNumericEntries(int firstResult, int maxResults);

	public abstract void saveQuestionTypeNumeric(QuestionTypeNumeric questionTypeNumeric);

	public abstract QuestionTypeNumeric updateQuestionTypeNumeric(QuestionTypeNumeric questionTypeNumeric);

}
