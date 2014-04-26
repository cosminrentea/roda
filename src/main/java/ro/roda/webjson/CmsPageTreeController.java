package ro.roda.webjson;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.CmsPageTree;
import ro.roda.service.CmsPageTreeService;

@RequestMapping("/admin/cmspagestree")
@Controller
public class CmsPageTreeController {

	@Autowired
	CmsPageTreeService cmsPageJsonService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listCmsPageTreeJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CmsPageTree> result = cmsPageJsonService.findAllCmsPageTrees();
		return new ResponseEntity<String>(CmsPageTree.toJsonArr(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showCmsPageTreeJson(@PathVariable("id") Integer id) {
		CmsPageTree cmsPageTree = cmsPageJsonService.findCmsPageTree(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (cmsPageTree == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(cmsPageTree.toJson(), headers, HttpStatus.OK);
	}

}
