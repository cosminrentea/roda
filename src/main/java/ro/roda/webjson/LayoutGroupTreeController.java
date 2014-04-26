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

import ro.roda.domainjson.LayoutGroupTree;
import ro.roda.service.LayoutGroupTreeService;

@RequestMapping("/admin/cmslayoutgrouptree")
@Controller
public class LayoutGroupTreeController {

	@Autowired
	LayoutGroupTreeService layoutGroupTreeService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<LayoutGroupTree> result = layoutGroupTreeService.findAllLayoutGroupTrees();
		return new ResponseEntity<String>(LayoutGroupTree.toJsonArr(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		LayoutGroupTree layoutGroupTree = layoutGroupTreeService.findLayoutGroupTree(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (layoutGroupTree == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(layoutGroupTree.toJson(), headers, HttpStatus.OK);
	}

}
