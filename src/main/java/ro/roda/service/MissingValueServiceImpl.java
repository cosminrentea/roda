package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.MissingValue;
import ro.roda.domain.MissingValuePK;

@Service
@Transactional
public class MissingValueServiceImpl implements MissingValueService {

	public long countAllMissingValues() {
		return MissingValue.countMissingValues();
	}

	public void deleteMissingValue(MissingValue missingValue) {
		missingValue.remove();
	}

	public MissingValue findMissingValue(MissingValuePK id) {
		return MissingValue.findMissingValue(id);
	}

	public List<MissingValue> findAllQuestionTypeCategories() {
		return MissingValue.findAllMissingValues();
	}

	public List<MissingValue> findMissingValueEntries(int firstResult, int maxResults) {
		return MissingValue.findMissingValueEntries(firstResult, maxResults);
	}

	public void saveMissingValue(MissingValue questionTypeCategory) {
		questionTypeCategory.persist();
	}

	public MissingValue updateMissingValue(MissingValue questionTypeCategory) {
		return questionTypeCategory.merge();
	}
}
