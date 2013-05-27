package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.StudyPersonAsoc;

@Service
@Transactional
public class StudyPersonAsocServiceImpl implements StudyPersonAsocService {

	public long countAllStudyPersonAsocs() {
		return StudyPersonAsoc.countStudyPersonAsocs();
	}

	public void deleteStudyPersonAsoc(StudyPersonAsoc studyPersonAsoc) {
		studyPersonAsoc.remove();
	}

	public StudyPersonAsoc findStudyPersonAsoc(Integer id) {
		return StudyPersonAsoc.findStudyPersonAsoc(id);
	}

	public List<StudyPersonAsoc> findAllStudyPersonAsocs() {
		return StudyPersonAsoc.findAllStudyPersonAsocs();
	}

	public List<StudyPersonAsoc> findStudyPersonAsocEntries(int firstResult, int maxResults) {
		return StudyPersonAsoc.findStudyPersonAsocEntries(firstResult, maxResults);
	}

	public void saveStudyPersonAsoc(StudyPersonAsoc studyPersonAsoc) {
		studyPersonAsoc.persist();
	}

	public StudyPersonAsoc updateStudyPersonAsoc(StudyPersonAsoc studyPersonAsoc) {
		return studyPersonAsoc.merge();
	}
}
