package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.StudyPerson;
import ro.roda.domain.StudyPersonPK;

@Service
@Transactional
public class StudyPersonServiceImpl implements StudyPersonService {

	public long countAllStudypeople() {
		return StudyPerson.countStudypeople();
	}

	public void deleteStudyPerson(StudyPerson studyPerson) {
		studyPerson.remove();
	}

	public StudyPerson findStudyPerson(StudyPersonPK id) {
		return StudyPerson.findStudyPerson(id);
	}

	public List<StudyPerson> findAllStudypeople() {
		return StudyPerson.findAllStudypeople();
	}

	public List<StudyPerson> findStudyPersonEntries(int firstResult, int maxResults) {
		return StudyPerson.findStudyPersonEntries(firstResult, maxResults);
	}

	public void saveStudyPerson(StudyPerson studyPerson) {
		studyPerson.persist();
	}

	public StudyPerson updateStudyPerson(StudyPerson studyPerson) {
		return studyPerson.merge();
	}
}
