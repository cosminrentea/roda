package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.QuestionTypeNumeric;

@Service
@Transactional
public class QuestionTypeNumericServiceImpl implements QuestionTypeNumericService {

	public long countAllQuestionTypeNumerics() {
		return QuestionTypeNumeric.countQuestionTypeNumerics();
	}

	public void deleteQuestionTypeNumeric(QuestionTypeNumeric questionTypeNumeric) {
		questionTypeNumeric.remove();
	}

	public QuestionTypeNumeric findQuestionTypeNumeric(Long id) {
		return QuestionTypeNumeric.findQuestionTypeNumeric(id);
	}

	public List<QuestionTypeNumeric> findAllQuestionTypeNumerics() {
		return QuestionTypeNumeric.findAllQuestionTypeNumerics();
	}

	public List<QuestionTypeNumeric> findQuestionTypeNumericEntries(int firstResult, int maxResults) {
		return QuestionTypeNumeric.findQuestionTypeNumericEntries(firstResult, maxResults);
	}

	public void saveQuestionTypeNumeric(QuestionTypeNumeric questionTypeNumeric) {
		questionTypeNumeric.persist();
	}

	public QuestionTypeNumeric updateQuestionTypeNumeric(QuestionTypeNumeric questionTypeNumeric) {
		return questionTypeNumeric.merge();
	}
}
