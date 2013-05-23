package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.StudyOrg;
import ro.roda.domain.StudyOrgPK;


@Service
@Transactional
public class StudyOrgServiceImpl implements StudyOrgService {

	public long countAllStudyOrgs() {
        return StudyOrg.countStudyOrgs();
    }

	public void deleteStudyOrg(StudyOrg studyOrg) {
        studyOrg.remove();
    }

	public StudyOrg findStudyOrg(StudyOrgPK id) {
        return StudyOrg.findStudyOrg(id);
    }

	public List<StudyOrg> findAllStudyOrgs() {
        return StudyOrg.findAllStudyOrgs();
    }

	public List<StudyOrg> findStudyOrgEntries(int firstResult, int maxResults) {
        return StudyOrg.findStudyOrgEntries(firstResult, maxResults);
    }

	public void saveStudyOrg(StudyOrg studyOrg) {
        studyOrg.persist();
    }

	public StudyOrg updateStudyOrg(StudyOrg studyOrg) {
        return studyOrg.merge();
    }
}
