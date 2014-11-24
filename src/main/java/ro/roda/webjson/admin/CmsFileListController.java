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

import ro.roda.domainjson.FileList;
import ro.roda.service.FileListService;

@RequestMapping("/adminjson/cmsfilelist")
@Controller
public class CmsFileListController {

	@Autowired
	FileListService fileListService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listFiles() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<FileList> result = fileListService.findAllFileLists();
		return new ResponseEntity<String>(FileList.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showFileDetails(@PathVariable("id") Integer id) {
		FileList fileList = fileListService.findFileList(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (fileList == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(fileList.toJsonDetailed(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/json", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<FileList> result = fileListService.findAllFileListsJson();
		return new ResponseEntity<String>(FileList.toJsonArrayDetailed(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/json/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJsonDetails(@PathVariable("id") Integer id) {
		FileList fileList = fileListService.findFileList(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (fileList == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(fileList.toJsonDetailed(), headers, HttpStatus.OK);
	}

}
