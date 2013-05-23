package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.StudyOrgAssoc;

public interface StudyOrgAssocService {

	public abstract long countAllStudyOrgAssocs();

	public abstract void deleteStudyOrgAssoc(StudyOrgAssoc studyOrgAssoc);

	public abstract StudyOrgAssoc findStudyOrgAssoc(Integer id);

	public abstract List<StudyOrgAssoc> findAllStudyOrgAssocs();

	public abstract List<StudyOrgAssoc> findStudyOrgAssocEntries(int firstResult, int maxResults);

	public abstract void saveStudyOrgAssoc(StudyOrgAssoc studyOrgAssoc);

	public abstract StudyOrgAssoc updateStudyOrgAssoc(StudyOrgAssoc studyOrgAssoc);

}
