package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.StudyOrgAssoc;

@Service
@Transactional
public class StudyOrgAssocServiceImpl implements StudyOrgAssocService {

	public long countAllStudyOrgAssocs() {
		return StudyOrgAssoc.countStudyOrgAssocs();
	}

	public void deleteStudyOrgAssoc(StudyOrgAssoc studyOrgAssoc) {
		studyOrgAssoc.remove();
	}

	public StudyOrgAssoc findStudyOrgAssoc(Integer id) {
		return StudyOrgAssoc.findStudyOrgAssoc(id);
	}

	public List<StudyOrgAssoc> findAllStudyOrgAssocs() {
		return StudyOrgAssoc.findAllStudyOrgAssocs();
	}

	public List<StudyOrgAssoc> findStudyOrgAssocEntries(int firstResult, int maxResults) {
		return StudyOrgAssoc.findStudyOrgAssocEntries(firstResult, maxResults);
	}

	public void saveStudyOrgAssoc(StudyOrgAssoc studyOrgAssoc) {
		studyOrgAssoc.persist();
	}

	public StudyOrgAssoc updateStudyOrgAssoc(StudyOrgAssoc studyOrgAssoc) {
		return studyOrgAssoc.merge();
	}
}
