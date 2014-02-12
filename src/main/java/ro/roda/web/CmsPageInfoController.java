package ro.roda.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.service.CmsPageInfoService;
import ro.roda.transformer.CmsPageInfo;

@RequestMapping("/admin/pageinfo")
@Controller
public class CmsPageInfoController {

	@Autowired
	CmsPageInfoService cmsPageInfoService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listCmsPageInfoJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CmsPageInfo> result = cmsPageInfoService.findAllCmsPageInfos();
		return new ResponseEntity<String>(CmsPageInfo.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showCmsPageInfoJson(@PathVariable("id") Integer id) {
		CmsPageInfo cmsPageInfo = cmsPageInfoService.findCmsPageInfo(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (cmsPageInfo == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(cmsPageInfo.toJson(), headers, HttpStatus.OK);
	}

}
