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

import ro.roda.domainjson.SnippetGroupInfo;
import ro.roda.service.SnippetGroupInfoService;

@RequestMapping("/admin/cmssnippetgroupinfo")
@Controller
public class SnippetGroupInfoController {

	@Autowired
	SnippetGroupInfoService snippetGroupInfoService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SnippetGroupInfo> result = snippetGroupInfoService.findAllSnippetGroupInfos();
		return new ResponseEntity<String>(SnippetGroupInfo.toJsonArr(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		SnippetGroupInfo snippetGroupInfo = snippetGroupInfoService.findSnippetGroupInfo(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (snippetGroupInfo == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(snippetGroupInfo.toJson(), headers, HttpStatus.OK);
	}

}
