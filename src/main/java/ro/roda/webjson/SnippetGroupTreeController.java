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

import ro.roda.domainjson.SnippetGroupTree;
import ro.roda.service.SnippetGroupTreeService;

@RequestMapping("/admin/cmssnippetgrouptree")
@Controller
public class SnippetGroupTreeController {

	@Autowired
	SnippetGroupTreeService snippetGroupTreeService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SnippetGroupTree> result = snippetGroupTreeService.findAllSnippetGroupTrees();
		return new ResponseEntity<String>(SnippetGroupTree.toJsonArr(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		SnippetGroupTree snippetGroupTree = snippetGroupTreeService.findSnippetGroupTree(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (snippetGroupTree == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(snippetGroupTree.toJson(), headers, HttpStatus.OK);
	}

}
