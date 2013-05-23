package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyKeywordPK;


public interface StudyKeywordService {

	public abstract long countAllStudyKeywords();


	public abstract void deleteStudyKeyword(StudyKeyword studyKeyword);


	public abstract StudyKeyword findStudyKeyword(StudyKeywordPK id);


	public abstract List<StudyKeyword> findAllStudyKeywords();


	public abstract List<StudyKeyword> findStudyKeywordEntries(int firstResult, int maxResults);


	public abstract void saveStudyKeyword(StudyKeyword studyKeyword);


	public abstract StudyKeyword updateStudyKeyword(StudyKeyword studyKeyword);

}
