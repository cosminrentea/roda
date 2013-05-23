package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.StudyOrg;
import ro.roda.domain.StudyOrgPK;


public interface StudyOrgService {

	public abstract long countAllStudyOrgs();


	public abstract void deleteStudyOrg(StudyOrg studyOrg);


	public abstract StudyOrg findStudyOrg(StudyOrgPK id);


	public abstract List<StudyOrg> findAllStudyOrgs();


	public abstract List<StudyOrg> findStudyOrgEntries(int firstResult, int maxResults);


	public abstract void saveStudyOrg(StudyOrg studyOrg);


	public abstract StudyOrg updateStudyOrg(StudyOrg studyOrg);

}
