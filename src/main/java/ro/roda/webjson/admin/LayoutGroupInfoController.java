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

import ro.roda.domainjson.LayoutGroupInfo;
import ro.roda.service.LayoutGroupInfoService;

@RequestMapping("/adminjson/cmslayoutgroupinfo")
@Controller
public class LayoutGroupInfoController {

	@Autowired
	LayoutGroupInfoService layoutGroupInfoService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<LayoutGroupInfo> result = layoutGroupInfoService.findAllLayoutGroupInfos();
		return new ResponseEntity<String>(LayoutGroupInfo.toJsonArr(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		LayoutGroupInfo layoutGroupInfo = layoutGroupInfoService.findLayoutGroupInfo(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (layoutGroupInfo == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(layoutGroupInfo.toJson(), headers, HttpStatus.OK);
	}

}
