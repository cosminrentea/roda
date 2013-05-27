package ro.roda.service;

import java.util.List;

import ro.roda.domain.StudyDocuments;
import ro.roda.domain.StudyDocumentsPK;

public interface StudyDocumentsService {

	public abstract long countAllStudyDocumentses();

	public abstract void deleteStudyDocuments(StudyDocuments studyDocuments);

	public abstract StudyDocuments findStudyDocuments(StudyDocumentsPK id);

	public abstract List<StudyDocuments> findAllStudyDocumentses();

	public abstract List<StudyDocuments> findStudyDocumentsEntries(int firstResult, int maxResults);

	public abstract void saveStudyDocuments(StudyDocuments studyDocuments);

	public abstract StudyDocuments updateStudyDocuments(StudyDocuments studyDocuments);

}
