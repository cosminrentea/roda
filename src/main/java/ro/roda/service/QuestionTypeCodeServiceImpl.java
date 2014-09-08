package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.QuestionTypeCode;
import ro.roda.domain.QuestionTypeCodePK;

@Service
@Transactional
public class QuestionTypeCodeServiceImpl implements QuestionTypeCodeService {

	public long countAllQuestionTypeCodes() {
		return QuestionTypeCode.countQuestionTypeCodes();
	}

	public void deleteQuestionTypeCode(QuestionTypeCode questionTypeCode) {
		questionTypeCode.remove();
	}

	public QuestionTypeCode findQuestionTypeCode(QuestionTypeCodePK id) {
		return QuestionTypeCode.findQuestionTypeCode(id);
	}

	public List<QuestionTypeCode> findAllQuestionTypeCodes() {
		return QuestionTypeCode.findAllQuestionTypeCodes();
	}

	public List<QuestionTypeCode> findQuestionTypeCodeEntries(int firstResult, int maxResults) {
		return QuestionTypeCode.findQuestionTypeCodeEntries(firstResult, maxResults);
	}

	public void saveQuestionTypeCode(QuestionTypeCode questionTypeCode) {
		questionTypeCode.persist();
	}

	public QuestionTypeCode updateQuestionTypeCode(QuestionTypeCode questionTypeCode) {
		return questionTypeCode.merge();
	}
}
