package ro.roda.service;

import java.util.List;

import ro.roda.domain.StudyPerson;
import ro.roda.domain.StudyPersonPK;

public interface StudyPersonService {

	public abstract long countAllStudypeople();

	public abstract void deleteStudyPerson(StudyPerson studyPerson);

	public abstract StudyPerson findStudyPerson(StudyPersonPK id);

	public abstract List<StudyPerson> findAllStudypeople();

	public abstract List<StudyPerson> findStudyPersonEntries(int firstResult, int maxResults);

	public abstract void saveStudyPerson(StudyPerson studyPerson);

	public abstract StudyPerson updateStudyPerson(StudyPerson studyPerson);

}
