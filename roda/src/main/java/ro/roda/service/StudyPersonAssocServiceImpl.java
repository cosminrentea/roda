package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.StudyPersonAssoc;

@Service
@Transactional
public class StudyPersonAssocServiceImpl implements StudyPersonAssocService {

	public long countAllStudyPersonAssocs() {
		return StudyPersonAssoc.countStudyPersonAssocs();
	}

	public void deleteStudyPersonAssoc(StudyPersonAssoc studyPersonAssoc) {
		studyPersonAssoc.remove();
	}

	public StudyPersonAssoc findStudyPersonAssoc(Integer id) {
		return StudyPersonAssoc.findStudyPersonAssoc(id);
	}

	public List<StudyPersonAssoc> findAllStudyPersonAssocs() {
		return StudyPersonAssoc.findAllStudyPersonAssocs();
	}

	public List<StudyPersonAssoc> findStudyPersonAssocEntries(int firstResult, int maxResults) {
		return StudyPersonAssoc.findStudyPersonAssocEntries(firstResult, maxResults);
	}

	public void saveStudyPersonAssoc(StudyPersonAssoc studyPersonAssoc) {
		studyPersonAssoc.persist();
	}

	public StudyPersonAssoc updateStudyPersonAssoc(StudyPersonAssoc studyPersonAssoc) {
		return studyPersonAssoc.merge();
	}
}
