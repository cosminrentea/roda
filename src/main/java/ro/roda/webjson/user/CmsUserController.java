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
import ro.roda.service.filestore.CmsFileStoreService;

@RequestMapping("/userjson")
@Controller
public class CmsUserController {

	@Autowired
	AdminJsonService adminJsonService;

	@Autowired
	CmsFileStoreService cmsFileStoreService;

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
		log.trace("> jsonImport controller: " + id);
		AdminJson jsonSave = adminJsonService.jsonImport(id);
		if (jsonSave == null) {
			return null;
		} else {

		}
		return jsonSave.toJson();
	}

	@RequestMapping(value = "/cmsfilemove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fileMove(@RequestParam(value = "folder") Integer folderId, @RequestParam(value = "id") Integer fileId) {
		AdminJson fileMove = adminJsonService.fileMove(folderId, fileId);
		if (fileMove == null) {
			return null;
		}
		return fileMove.toJson();

	}

	@RequestMapping(value = "/cmsfoldermove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String folderMove(@RequestParam(value = "parent") Integer parentGroupId,
			@RequestParam(value = "folder") Integer folderId) {
		AdminJson folderMove = adminJsonService.folderMove(parentGroupId, folderId);
		if (folderMove == null) {
			return null;
		}
		return folderMove.toJson();
	}

}
