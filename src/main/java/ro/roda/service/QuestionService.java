package ro.roda.service;

import java.util.List;

import ro.roda.domain.Question;

public interface QuestionService {

	public abstract long countAllQuestions();

	public abstract void deleteQuestion(Question question);

	public abstract Question findQuestion(Long id);

	public abstract List<Question> findAllQuestions();

	public abstract List<Question> findQuestionEntries(int firstResult, int maxResults);

	public abstract void saveQuestion(Question question);

	public abstract Question updateQuestion(Question question);

}
