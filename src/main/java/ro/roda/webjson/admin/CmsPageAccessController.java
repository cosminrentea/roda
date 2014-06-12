package ro.roda.webjson.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ro.roda.domainjson.CmsPageAccess;
import ro.roda.service.CmsPageInfoService;

@RequestMapping("/admin/pageaccess")
@Controller
public class CmsPageAccessController {

	@Autowired
	CmsPageInfoService cmsPageInfoService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listCmsPageInfoJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();

		List<CmsPageAccess> result = cmsPageInfoService.findAllCmsPageAccesses(request);
		return new ResponseEntity<String>(CmsPageAccess.toJsonArray(result), headers, HttpStatus.OK);
	}
}
