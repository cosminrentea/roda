package ro.roda.webjson.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.SnippetInfo;
import ro.roda.service.SnippetInfoService;

@RequestMapping("/admin/cmssnippetinfo")
@Controller
public class SnippetInfoController {

	@Autowired
	SnippetInfoService snippetInfoService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SnippetInfo> result = snippetInfoService.findAllSnippetInfos();
		return new ResponseEntity<String>(SnippetInfo.toJsonArr(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		SnippetInfo snippetInfo = snippetInfoService.findSnippetInfo(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (snippetInfo == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(snippetInfo.toJson(), headers, HttpStatus.OK);
	}

}
