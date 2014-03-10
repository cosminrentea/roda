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

import ro.roda.filestore.CmsFileStoreService;
import ro.roda.service.FileListService;
import ro.roda.transformer.FileList;

@RequestMapping("/admin/cmsfilelist")
@Controller
public class FileListController {

	@Autowired
	CmsFileStoreService cmsFileStoreService;

	@Autowired
	FileListService fileListService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<FileList> result = fileListService.findAllFileLists();
		return new ResponseEntity<String>(FileList.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		FileList fileList = fileListService.findFileList(id);
		// fileList.setFileproperties(cmsFileStoreService.getFileProperties(CmsFile.findCmsFile(id)));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (fileList == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		// return new ResponseEntity<String>(fileList.toJson(), headers,
		// HttpStatus.OK);
		return new ResponseEntity<String>(fileList.toJsonDetailed(), headers, HttpStatus.OK);
	}

}
