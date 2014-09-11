package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.QuestionTypeString;
import ro.roda.domain.QuestionTypeStringPK;

@Service
@Transactional
public class QuestionTypeCodeServiceImpl implements QuestionTypeCodeService {

	public long countAllQuestionTypeCodes() {
		return QuestionTypeString.countQuestionTypeCodes();
	}

	public void deleteQuestionTypeCode(QuestionTypeString questionTypeCode) {
		questionTypeCode.remove();
	}

	public QuestionTypeString findQuestionTypeCode(QuestionTypeStringPK id) {
		return QuestionTypeString.findQuestionTypeCode(id);
	}

	public List<QuestionTypeString> findAllQuestionTypeCodes() {
		return QuestionTypeString.findAllQuestionTypeCodes();
	}

	public List<QuestionTypeString> findQuestionTypeCodeEntries(int firstResult, int maxResults) {
		return QuestionTypeString.findQuestionTypeCodeEntries(firstResult, maxResults);
	}

	public void saveQuestionTypeCode(QuestionTypeString questionTypeCode) {
		questionTypeCode.persist();
	}

	public QuestionTypeString updateQuestionTypeCode(QuestionTypeString questionTypeCode) {
		return questionTypeCode.merge();
	}
}
