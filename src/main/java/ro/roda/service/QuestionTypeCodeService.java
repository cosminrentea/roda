package ro.roda.service;

import java.util.List;

import ro.roda.domain.QuestionTypeCode;
import ro.roda.domain.QuestionTypeCodePK;

public interface QuestionTypeCodeService {

	public abstract long countAllQuestionTypeCodes();

	public abstract void deleteQuestionTypeCode(QuestionTypeCode questionTypeCode);

	public abstract QuestionTypeCode findQuestionTypeCode(QuestionTypeCodePK id);

	public abstract List<QuestionTypeCode> findAllQuestionTypeCodes();

	public abstract List<QuestionTypeCode> findQuestionTypeCodeEntries(int firstResult, int maxResults);

	public abstract void saveQuestionTypeCode(QuestionTypeCode questionTypeCode);

	public abstract QuestionTypeCode updateQuestionTypeCode(QuestionTypeCode questionTypeCode);

}
