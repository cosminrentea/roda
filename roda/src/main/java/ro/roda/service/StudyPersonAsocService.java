package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.StudyPersonAsoc;


public interface StudyPersonAsocService {

	public abstract long countAllStudyPersonAsocs();


	public abstract void deleteStudyPersonAsoc(StudyPersonAsoc studyPersonAsoc);


	public abstract StudyPersonAsoc findStudyPersonAsoc(Integer id);


	public abstract List<StudyPersonAsoc> findAllStudyPersonAsocs();


	public abstract List<StudyPersonAsoc> findStudyPersonAsocEntries(int firstResult, int maxResults);


	public abstract void saveStudyPersonAsoc(StudyPersonAsoc studyPersonAsoc);


	public abstract StudyPersonAsoc updateStudyPersonAsoc(StudyPersonAsoc studyPersonAsoc);

}
