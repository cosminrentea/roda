package ro.roda.webjson.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.service.StudyKeywordService;

@RequestMapping("/userjson/studykeyword")
@Controller
public class UserStudyKeywordController {

	@Autowired
	StudyKeywordService studyKeywordService;

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String save(@RequestParam(value = "study") Integer studyId,
			@RequestParam(value = "keyword") String keywordName) {
		return studyKeywordService.save(studyId, keywordName);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String delete(@RequestParam(value = "study") Integer studyId,
			@RequestParam(value = "keyword") String keywordName) {
		return studyKeywordService.delete(studyId, keywordName);
	}

}
