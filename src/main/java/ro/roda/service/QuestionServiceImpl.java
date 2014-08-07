package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Question;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	public long countAllQuestions() {
		return Question.countQuestions();
	}

	public void deleteQuestion(Question question) {
		question.remove();
	}

	public Question findQuestion(Long id) {
		return Question.findQuestion(id);
	}

	public List<Question> findAllQuestions() {
		return Question.findAllQuestions();
	}

	public List<Question> findQuestionEntries(int firstResult, int maxResults) {
		return Question.findQuestionEntries(firstResult, maxResults);
	}

	public void saveQuestion(Question question) {
		question.persist();
	}

	public Question updateQuestion(Question question) {
		return question.merge();
	}
}
