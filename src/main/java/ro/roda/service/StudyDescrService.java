package ro.roda.service;

import java.util.List;

import ro.roda.domain.StudyDescr;
import ro.roda.domain.StudyDescrPK;

public interface StudyDescrService {

	public abstract long countAllStudyDescrs();

	public abstract void deleteStudyDescr(StudyDescr studyDescr);

	public abstract StudyDescr findStudyDescr(StudyDescrPK id);

	public abstract List<StudyDescr> findAllStudyDescrs();

	public abstract List<StudyDescr> findStudyDescrEntries(int firstResult, int maxResults);

	public abstract void saveStudyDescr(StudyDescr studyDescr);

	public abstract StudyDescr updateStudyDescr(StudyDescr studyDescr);

}
