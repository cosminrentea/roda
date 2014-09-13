package ro.roda.webjson;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.StudyInfo;
import ro.roda.service.StudyInfoService;

@RequestMapping("/studyinfo")
@Controller
public class StudyInfoController {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	StudyInfoService studyInfoService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<StudyInfo> result = studyInfoService.findAllStudyInfos();
		return new ResponseEntity<String>(StudyInfo.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {

		log.trace("STUDYINFO: Creating StudyInfo object");

		StudyInfo studyInfo = studyInfoService.findStudyInfo(id);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (studyInfo == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		String studyInfoJson = studyInfo.toJson();

		log.trace("STUDYINFO: Serialized as JSON");

		return new ResponseEntity<String>(studyInfoJson, headers, HttpStatus.OK);
	}

}
