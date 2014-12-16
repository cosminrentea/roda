package ro.roda.service;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ro.roda.domain.Keyword;
import ro.roda.domain.Study;
import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyKeywordPK;
import ro.roda.domain.Users;
import ro.roda.domainjson.AdminJson;

@Service
@Transactional
public class StudyKeywordServiceImpl implements StudyKeywordService {

	public long countAllStudyKeywords() {
		return StudyKeyword.countStudyKeywords();
	}

	public void deleteStudyKeyword(StudyKeyword studyKeyword) {
		studyKeyword.remove();
	}

	public StudyKeyword findStudyKeyword(StudyKeywordPK id) {
		return StudyKeyword.findStudyKeyword(id);
	}

	public List<StudyKeyword> findAllStudyKeywords() {
		return StudyKeyword.findAllStudyKeywords();
	}

	public List<StudyKeyword> findStudyKeywordEntries(int firstResult, int maxResults) {
		return StudyKeyword.findStudyKeywordEntries(firstResult, maxResults);
	}

	public void saveStudyKeyword(StudyKeyword studyKeyword) {
		studyKeyword.persist();
	}

	public StudyKeyword updateStudyKeyword(StudyKeyword studyKeyword) {
		return studyKeyword.merge();
	}

	public String save(Integer studyId, String keywordName) {

		Study study = Study.findStudy(studyId);
		if (study == null) {
			return new AdminJson(false, "ERROR: Keyword was NOT attached to Study by User").toJson();
		}

		Users user = Users.findUsersByUsernameAndEnabled(
				((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(), true)
				.getSingleResult();

		// find or add the keyword
		Keyword keyword = Keyword.checkKeyword(null, keywordName);

		// we assume: user != null , keyword != null
		StudyKeywordPK skpk = new StudyKeywordPK(studyId, keyword.getId(), user.getId());
		StudyKeyword sk = StudyKeyword.findStudyKeyword(skpk);
		if (sk == null) {
			sk = new StudyKeyword();
			sk.setId(skpk);
			sk.setAdded(new GregorianCalendar());
			sk.setAddedBy(user);
			sk.setStudyId(study);
			sk.setKeywordId(keyword);
			sk.persist();
			sk.flush();
		}
		return new AdminJson(true, "Keyword was attached to Study by User").toJson();
	}

	public String delete(Integer studyId, String keywordName) {

		Study study = Study.findStudy(studyId);
		if (study == null) {
			return new AdminJson(false, "ERROR: Keyword was NOT detached from Study by User").toJson();
		}

		Users user = Users.findUsersByUsernameAndEnabled(
				((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(), true)
				.getSingleResult();

		// TODO only FIND the keyword (not: find or add)
		Keyword keyword = Keyword.checkKeyword(null, keywordName);

		// we assume: user != null , keyword != null
		StudyKeywordPK skpk = new StudyKeywordPK(studyId, keyword.getId(), user.getId());
		StudyKeyword sk = StudyKeyword.findStudyKeyword(skpk);

		if (sk == null) {
			return new AdminJson(false, "ERROR: Keyword was ALREADY detached from Study by User").toJson();
		}

		sk.remove();
		sk.flush();

		return new AdminJson(true, "Keyword was detached from Study by User").toJson();
	}

}