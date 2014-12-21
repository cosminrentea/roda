package ro.roda.webjson.user;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ro.roda.domainjson.AdminJson;
import ro.roda.service.AdminJsonService;

@RequestMapping("/userjson")
@Controller
public class CmsUserController {

	@Autowired
	AdminJsonService adminJsonService;

	private final Log log = LogFactory.getLog(this.getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/cmsfiledrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fileDrop(@RequestParam(value = "fileid") Integer fileId) {
		AdminJson fileDrop = adminJsonService.fileDrop(fileId);
		if (fileDrop == null) {
			return null;
		}
		return fileDrop.toJson();
	}

	@RequestMapping(value = "/cmsfilesave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fileSave(@RequestParam("folderid") Integer folderId, @RequestParam("content") MultipartFile content,
			@RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "id", required = false) Integer fileId,
			@RequestParam(value = "url", required = false) String url, HttpServletRequest httpServletRequest) {
		log.trace("> fileSave controller: " + folderId + " ; " + fileId + " ; " + alias);
		AdminJson fileSave = adminJsonService.fileSave(folderId, content, fileId, alias, url);
		if (fileSave == null) {
			return null;
		}
		return fileSave.toJsonWithId();
	}

	@RequestMapping(value = "/cmsjsoncreate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String jsonCreate(@RequestBody String jsonString, @RequestParam(value = "name") String name,
			HttpServletRequest httpServletRequest) {
		log.trace("> jsonCreate controller: " + name);
		AdminJson jsonSave = adminJsonService.jsonCreate(jsonString, name);
		if (jsonSave == null) {
			return null;
		}
		return jsonSave.toJsonWithId();
	}

	@RequestMapping(value = "/cmsjsonsave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String jsonSave(@RequestBody String jsonString, @RequestParam(value = "id") Integer id,
			@RequestParam(value = "name", required = false) String name) {
		log.trace("> jsonSave controller: " + id + " ; " + name);
		AdminJson jsonSave = adminJsonService.jsonSave(jsonString, id, name);
		if (jsonSave == null) {
			return null;
		}
		return jsonSave.toJsonWithId();
	}

	@RequestMapping(value = "/cmsjsonimport", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String jsonImport(@RequestParam(value = "id") Integer id) {
		log.trace("> json import controller: " + id);
		AdminJson jsonSave = adminJsonService.jsonImport(id);
		return jsonSave.toJsonWithId();
	}

	@RequestMapping(value = "/cmsstudyimport", method = RequestMethod.POST)
	@ResponseBody
	public String studyImport(@RequestParam(value = "ddi") Integer ddiId, @RequestParam(value = "csv") Integer csvId,
			@RequestParam(value = "file") Integer[] fileIds) {
		log.trace("> study import controller");
		log.trace(ddiId);
		log.trace(csvId);
		for (Integer fileId : fileIds) {
			log.trace(fileId);
		}

		AdminJson jsonSave = adminJsonService.studyImport(ddiId, csvId, fileIds);
		if (jsonSave == null) {
			jsonSave = new AdminJson(false, "Study was not imported");
		}
		return jsonSave.toJsonWithId();

		// return
		// "{\"message\":\"Study imported successfully (DDI, CSV, files)\",\"success\":true}";
	}

}
