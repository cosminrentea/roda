package ro.roda.webjson.admin;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ro.roda.domainjson.AdminJson;
import ro.roda.service.AdminJsonService;

@RequestMapping("/adminjson")
@Controller
public class CatalogsController {

	@Autowired
	AdminJsonService adminJsonService;

	private final Log log = LogFactory.getLog(this.getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/catalogsave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String catalogSave(@RequestParam(value = "catalogname") String catalogname,
			@RequestParam(value = "parent", required = false) Integer parentId,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "added", required = false) Calendar added,
			@RequestParam(value = "sequencenr", required = false) Integer sequencenr,
			@RequestParam(value = "level", required = false) Integer level,
			@RequestParam(value = "series", required = false) Integer seriesId,
			@RequestParam(value = "catalog", required = false) Integer catalogId) {
		AdminJson layoutGroupSave = adminJsonService.catalogSave(parentId, added, catalogname, description, sequencenr,
				level, seriesId, catalogId);
		if (layoutGroupSave == null) {
			return null;
		}
		return layoutGroupSave.toJson();
	}

	@RequestMapping(value = "/catalogdrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String catalogDrop(@RequestParam(value = "catalogid") Integer catalogId) {
		AdminJson catalogDrop = adminJsonService.catalogDrop(catalogId);
		if (catalogDrop == null) {
			return null;
		}
		return catalogDrop.toJson();
	}

}
