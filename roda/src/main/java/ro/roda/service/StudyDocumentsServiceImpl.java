package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.StudyDocuments;
import ro.roda.domain.StudyDocumentsPK;

@Service
@Transactional
public class StudyDocumentsServiceImpl implements StudyDocumentsService {

	public long countAllStudyDocumentses() {
		return StudyDocuments.countStudyDocumentses();
	}

	public void deleteStudyDocuments(StudyDocuments studyDocuments) {
		studyDocuments.remove();
	}

	public StudyDocuments findStudyDocuments(StudyDocumentsPK id) {
		return StudyDocuments.findStudyDocuments(id);
	}

	public List<StudyDocuments> findAllStudyDocumentses() {
		return StudyDocuments.findAllStudyDocumentses();
	}

	public List<StudyDocuments> findStudyDocumentsEntries(int firstResult, int maxResults) {
		return StudyDocuments.findStudyDocumentsEntries(firstResult, maxResults);
	}

	public void saveStudyDocuments(StudyDocuments studyDocuments) {
		studyDocuments.persist();
	}

	public StudyDocuments updateStudyDocuments(StudyDocuments studyDocuments) {
		return studyDocuments.merge();
	}
}
