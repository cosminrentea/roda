package ro.roda.service;

import java.util.List;

import ro.roda.domain.QuestionTypeCategory;
import ro.roda.domain.QuestionTypeCategoryPK;

public interface QuestionTypeCategoryService {

	public abstract long countAllQuestionTypeCategories();

	public abstract void deleteQuestionTypeCategory(QuestionTypeCategory questionTypeCategory);

	public abstract QuestionTypeCategory findQuestionTypeCategory(QuestionTypeCategoryPK id);

	public abstract List<QuestionTypeCategory> findAllQuestionTypeCategories();

	public abstract List<QuestionTypeCategory> findQuestionTypeCategoryEntries(int firstResult, int maxResults);

	public abstract void saveQuestionTypeCategory(QuestionTypeCategory questionTypeCategory);

	public abstract QuestionTypeCategory updateQuestionTypeCategory(QuestionTypeCategory questionTypeCategory);

}
