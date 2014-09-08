package ro.roda.service;

import java.util.List;

import ro.roda.domain.MissingValue;
import ro.roda.domain.MissingValuePK;

public interface MissingValueService {

	public abstract long countAllMissingValues();

	public abstract void deleteMissingValue(MissingValue questionTypeCategory);

	public abstract MissingValue findMissingValue(MissingValuePK id);

	public abstract List<MissingValue> findAllQuestionTypeCategories();

	public abstract List<MissingValue> findMissingValueEntries(int firstResult, int maxResults);

	public abstract void saveMissingValue(MissingValue questionTypeCategory);

	public abstract MissingValue updateMissingValue(MissingValue questionTypeCategory);

}
