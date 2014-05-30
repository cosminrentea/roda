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

import ro.roda.domainjson.CatalogTree;
import ro.roda.service.CatalogTreeService;

@RequestMapping("/admin/catalogtree")
@Controller
public class CatalogTreeController {

	@Autowired
	CatalogTreeService catalogTreeService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CatalogTree> result = catalogTreeService.findAllCatalogTree();
		return new ResponseEntity<String>(CatalogTree.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		CatalogTree catalogTree = catalogTreeService.findCatalogTree(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (catalogTree == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(catalogTree.toJson(), headers, HttpStatus.OK);
	}

}
