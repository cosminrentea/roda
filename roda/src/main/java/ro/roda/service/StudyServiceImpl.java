package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Study;

@Service
@Transactional
public class StudyServiceImpl implements StudyService {

	public long countAllStudys() {
		return Study.countStudys();
	}

	public void deleteStudy(Study study) {
		study.remove();
	}

	public Study findStudy(Integer id) {
		return Study.findStudy(id);
	}

	public List<Study> findAllStudys() {
		return Study.findAllStudys();
	}

	public List<Study> findStudyEntries(int firstResult, int maxResults) {
		return Study.findStudyEntries(firstResult, maxResults);
	}

	public void saveStudy(Study study) {
		study.persist();
	}

	public Study updateStudy(Study study) {
		return study.merge();
	}
}
