package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyKeywordPK;

@Service
@Transactional
public class StudyKeywordServiceImpl implements StudyKeywordService {

	public long countAllStudyKeywords() {
		return StudyKeyword.countStudyKeywords();
	}

	public void deleteStudyKeyword(StudyKeyword studyKeyword) {
		studyKeyword.remove();
	}

	public StudyKeyword findStudyKeyword(StudyKeywordPK id) {
		return StudyKeyword.findStudyKeyword(id);
	}

	public List<StudyKeyword> findAllStudyKeywords() {
		return StudyKeyword.findAllStudyKeywords();
	}

	public List<StudyKeyword> findStudyKeywordEntries(int firstResult, int maxResults) {
		return StudyKeyword.findStudyKeywordEntries(firstResult, maxResults);
	}

	public void saveStudyKeyword(StudyKeyword studyKeyword) {
		studyKeyword.persist();
	}

	public StudyKeyword updateStudyKeyword(StudyKeyword studyKeyword) {
		return studyKeyword.merge();
	}
}
