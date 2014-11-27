package ro.roda.webjson.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domain.Keyword;
import ro.roda.domain.Study;
import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyKeywordPK;
import ro.roda.domain.Users;
import ro.roda.domainjson.AdminJson;
import ro.roda.service.StudyKeywordService;

@RequestMapping("/userjson/studykeyword")
@Controller
public class UserStudyKeywordController {

	@Autowired
	StudyKeywordService studyKeywordService;

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String add(@RequestParam(value = "study") Integer studyId,
			@RequestParam(value = "keyword") String keywordName) {

		Study study = Study.findStudy(studyId);
		if (study == null) {
			return new AdminJson(false, "ERROR: Keyword was NOT attached to Study by User").toJson();
		}

		Users user = Users.findUsersesByUsernameLike(
				((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.getSingleResult();

		// find or add the keyword
		Keyword keyword = Keyword.checkKeyword(null, keywordName);

		// we assume: user != null , keyword != null
		StudyKeywordPK skpk = new StudyKeywordPK(studyId, keyword.getId(), user.getId());
		StudyKeyword sk = studyKeywordService.findStudyKeyword(skpk);
		if (sk == null) {
			sk = new StudyKeyword();
			sk.setId(skpk);
			sk.setAdded(null);
			sk.setAddedBy(user);
			sk.setStudyId(study);
			sk.setKeywordId(keyword);
			sk.persist();
		}
		return new AdminJson(true, "Keyword was attached to Study by User").toJson();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String delete(@RequestParam(value = "study") Integer studyId,
			@RequestParam(value = "keyword") Integer keywordId) {

		// TODO implement this
		return null;
	}

}
