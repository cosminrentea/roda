package ro.roda.service;

import java.util.List;

import ro.roda.domain.QuestionTypeString;
import ro.roda.domain.QuestionTypeStringPK;

public interface QuestionTypeCodeService {

	public abstract long countAllQuestionTypeCodes();

	public abstract void deleteQuestionTypeCode(QuestionTypeString questionTypeCode);

	public abstract QuestionTypeString findQuestionTypeCode(QuestionTypeStringPK id);

	public abstract List<QuestionTypeString> findAllQuestionTypeCodes();

	public abstract List<QuestionTypeString> findQuestionTypeCodeEntries(int firstResult, int maxResults);

	public abstract void saveQuestionTypeCode(QuestionTypeString questionTypeCode);

	public abstract QuestionTypeString updateQuestionTypeCode(QuestionTypeString questionTypeCode);

}
