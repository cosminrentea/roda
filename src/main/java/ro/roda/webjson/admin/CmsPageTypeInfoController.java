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

import ro.roda.domainjson.CmsPageTypeInfo;
import ro.roda.service.CmsPageTypeInfoService;

@RequestMapping("/adminjson/cmspagetypeinfo")
@Controller
public class CmsPageTypeInfoController {

	@Autowired
	CmsPageTypeInfoService cmsPageTypeInfoService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listCmsPageTypeInfoJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CmsPageTypeInfo> result = cmsPageTypeInfoService.findAllCmsPageTypeInfos();
		return new ResponseEntity<String>(CmsPageTypeInfo.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showCmsPageTypeInfoJson(@PathVariable("id") Integer id) {
		CmsPageTypeInfo cmsPageTypeInfo = cmsPageTypeInfoService.findCmsPageTypeInfo(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (cmsPageTypeInfo == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(cmsPageTypeInfo.toJson(), headers, HttpStatus.OK);
	}

}
