package ro.roda.service;

import java.util.List;

import ro.roda.domain.StudyPersonAssoc;

public interface StudyPersonAssocService {

	public abstract long countAllStudyPersonAssocs();

	public abstract void deleteStudyPersonAssoc(StudyPersonAssoc studyPersonAssoc);

	public abstract StudyPersonAssoc findStudyPersonAssoc(Integer id);

	public abstract List<StudyPersonAssoc> findAllStudyPersonAssocs();

	public abstract List<StudyPersonAssoc> findStudyPersonAssocEntries(int firstResult, int maxResults);

	public abstract void saveStudyPersonAssoc(StudyPersonAssoc studyPersonAssoc);

	public abstract StudyPersonAssoc updateStudyPersonAssoc(StudyPersonAssoc studyPersonAssoc);

}
