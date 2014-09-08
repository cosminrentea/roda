package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.QuestionTypeCategory;
import ro.roda.domain.QuestionTypeCategoryPK;

@Service
@Transactional
public class QuestionTypeCategoryServiceImpl implements QuestionTypeCategoryService {

	public long countAllQuestionTypeCategories() {
		return QuestionTypeCategory.countQuestionTypeCategories();
	}

	public void deleteQuestionTypeCategory(QuestionTypeCategory questionTypeCategory) {
		questionTypeCategory.remove();
	}

	public QuestionTypeCategory findQuestionTypeCategory(QuestionTypeCategoryPK id) {
		return QuestionTypeCategory.findQuestionTypeCategory(id);
	}

	public List<QuestionTypeCategory> findAllQuestionTypeCategories() {
		return QuestionTypeCategory.findAllQuestionTypeCategories();
	}

	public List<QuestionTypeCategory> findQuestionTypeCategoryEntries(int firstResult, int maxResults) {
		return QuestionTypeCategory.findQuestionTypeCategoryEntries(firstResult, maxResults);
	}

	public void saveQuestionTypeCategory(QuestionTypeCategory questionTypeCategory) {
		questionTypeCategory.persist();
	}

	public QuestionTypeCategory updateQuestionTypeCategory(QuestionTypeCategory questionTypeCategory) {
		return questionTypeCategory.merge();
	}
}
