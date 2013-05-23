package ro.roda.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.domain.File;
import ro.roda.service.FileService;
import ro.roda.service.InstanceService;
import ro.roda.service.SelectionVariableItemService;
import ro.roda.service.StudyService;
import ro.roda.service.VariableService;

@RequestMapping("/files")
@Controller
public class FileController {

	@Autowired
	FileService fileService;

	private final Log log = LogFactory.getLog(this.getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid File file, BindingResult bindingResult, Model uiModel,
			@RequestParam("content") MultipartFile content, HttpServletRequest httpServletRequest) {
		log.debug("> create");
		uiModel.asMap().clear();
		fileService.saveFile(file, content);
		return "redirect:/files/" + encodeUrlPathSegment(file.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		log.debug("> show");
		File file = fileService.findFile(id);
		file.setUrl("showfile/" + id);
		uiModel.addAttribute("file", fileService.findFile(id));
		uiModel.addAttribute("itemId", id);
		return "files/show";
	}

	@RequestMapping(value = "/showfile/{id}", method = RequestMethod.GET)
	public String showfile(@PathVariable("id") Integer id, HttpServletResponse response, Model model) {
		log.debug("> showfile");
		File file = fileService.findFile(id);
		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" + file.getName() + "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(file.getContentType());
			IOUtils.copy(new FileInputStream(file.getFullPath()), out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		File file = fileService.findFile(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (file == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(file.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<File> result = fileService.findAllFiles();
		return new ResponseEntity<String>(File.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		File file = File.fromJsonToFile(json);
		fileService.saveFile(file);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (File file : File.fromJsonArrayToFiles(json)) {
			fileService.saveFile(file);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		File file = File.fromJsonToFile(json);
		if (fileService.updateFile(file) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (File file : File.fromJsonArrayToFiles(json)) {
			if (fileService.updateFile(file) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		File file = fileService.findFile(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (file == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		fileService.deleteFile(file);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	InstanceService instanceService;

	@Autowired
	SelectionVariableItemService selectionVariableItemService;

	@Autowired
	StudyService studyService;

	@Autowired
	VariableService variableService;

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new File());
		return "files/create";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("files", fileService.findFileEntries(firstResult, sizeNo));
			float nrOfPages = (float) fileService.countAllFiles() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("files", fileService.findAllFiles());
		}
		return "files/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		File file = fileService.findFile(id);
		fileService.deleteFile(file);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/files";
	}

	void populateEditForm(Model uiModel, File file) {
		uiModel.addAttribute("file", file);
		uiModel.addAttribute("instances", instanceService.findAllInstances());
		uiModel.addAttribute("selectionvariableitems", selectionVariableItemService.findAllSelectionVariableItems());
		uiModel.addAttribute("studys", studyService.findAllStudys());
		uiModel.addAttribute("variables", variableService.findAllVariables());
	}

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
