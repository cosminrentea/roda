package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.StudyDescr;
import ro.roda.domain.StudyDescrPK;

@Service
@Transactional
public class StudyDescrServiceImpl implements StudyDescrService {

	public long countAllStudyDescrs() {
		return StudyDescr.countStudyDescrs();
	}

	public void deleteStudyDescr(StudyDescr studyDescr) {
		studyDescr.remove();
	}

	public StudyDescr findStudyDescr(StudyDescrPK id) {
		return StudyDescr.findStudyDescr(id);
	}

	public List<StudyDescr> findAllStudyDescrs() {
		return StudyDescr.findAllStudyDescrs();
	}

	public List<StudyDescr> findStudyDescrEntries(int firstResult, int maxResults) {
		return StudyDescr.findStudyDescrEntries(firstResult, maxResults);
	}

	public void saveStudyDescr(StudyDescr studyDescr) {
		studyDescr.persist();
	}

	public StudyDescr updateStudyDescr(StudyDescr studyDescr) {
		return studyDescr.merge();
	}
}
