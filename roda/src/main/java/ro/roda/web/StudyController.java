package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ro.roda.domain.Study;
import ro.roda.service.CatalogStudyService;
import ro.roda.service.CollectionModelTypeService;
import ro.roda.service.DataSourceTypeService;
import ro.roda.service.FileService;
import ro.roda.service.InstanceService;
import ro.roda.service.SamplingProcedureService;
import ro.roda.service.SourceService;
import ro.roda.service.StudyDescrService;
import ro.roda.service.StudyKeywordService;
import ro.roda.service.StudyOrgService;
import ro.roda.service.StudyPersonService;
import ro.roda.service.StudyService;
import ro.roda.service.TimeMethService;
import ro.roda.service.TopicService;
import ro.roda.service.UnitAnalysisService;
import ro.roda.service.UsersService;

@RequestMapping("/studys")
@Controller
public class StudyController {

	@Autowired
	StudyService studyService;

	@Autowired
	CatalogStudyService catalogStudyService;

	@Autowired
	CollectionModelTypeService collectionModelTypeService;

	@Autowired
	DataSourceTypeService dataSourceTypeService;

	@Autowired
	FileService fileService;

	@Autowired
	InstanceService instanceService;

	@Autowired
	SamplingProcedureService samplingProcedureService;

	@Autowired
	SourceService sourceService;

	@Autowired
	StudyDescrService studyDescrService;

	@Autowired
	StudyKeywordService studyKeywordService;

	@Autowired
	StudyOrgService studyOrgService;

	@Autowired
	StudyPersonService studyPersonService;

	@Autowired
	TimeMethService timeMethTypeService;

	@Autowired
	TopicService topicService;

	@Autowired
	UnitAnalysisService unitAnalysisService;

	@Autowired
	UsersService usersService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Study study, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, study);
			return "studys/create";
		}
		uiModel.asMap().clear();
		studyService.saveStudy(study);
		return "redirect:/studys/"
				+ encodeUrlPathSegment(study.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Study());
		return "studys/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("study", studyService.findStudy(id));
		uiModel.addAttribute("itemId", id);
		return "studys/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute("studys",
					studyService.findStudyEntries(firstResult, sizeNo));
			float nrOfPages = (float) studyService.countAllStudys() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("studys", studyService.findAllStudys());
		}
		addDateTimeFormatPatterns(uiModel);
		return "studys/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Study study, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, study);
			return "studys/update";
		}
		uiModel.asMap().clear();
		studyService.updateStudy(study);
		return "redirect:/studys/"
				+ encodeUrlPathSegment(study.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, studyService.findStudy(id));
		return "studys/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		Study study = studyService.findStudy(id);
		studyService.deleteStudy(study);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/studys";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"study_datestart_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"study_dateend_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"study_added_date_format",
				DateTimeFormat.patternForStyle("MM",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, Study study) {
		uiModel.addAttribute("study", study);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("catalogstudys",
				catalogStudyService.findAllCatalogStudys());
		uiModel.addAttribute("collectionmodeltypes",
				collectionModelTypeService.findAllCollectionModelTypes());
		uiModel.addAttribute("datasourcetypes",
				dataSourceTypeService.findAllDataSourceTypes());
		uiModel.addAttribute("files", fileService.findAllFiles());
		uiModel.addAttribute("instances", instanceService.findAllInstances());
		uiModel.addAttribute("samplingprocedures",
				samplingProcedureService.findAllSamplingProcedures());
		uiModel.addAttribute("sources", sourceService.findAllSources());
		uiModel.addAttribute("studydescrs",
				studyDescrService.findAllStudyDescrs());
		uiModel.addAttribute("studykeywords",
				studyKeywordService.findAllStudyKeywords());
		uiModel.addAttribute("studyorgs", studyOrgService.findAllStudyOrgs());
		uiModel.addAttribute("studypeople",
				studyPersonService.findAllStudypeople());
		uiModel.addAttribute("timemethtypes",
				timeMethTypeService.findAllTimeMeths());
		uiModel.addAttribute("topics", topicService.findAllTopics());
		uiModel.addAttribute("unitanalyses",
				unitAnalysisService.findAllUnitAnalyses());
		uiModel.addAttribute("userses", usersService.findAllUserses());
	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
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

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		Study study = studyService.findStudy(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (study == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(study.toJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Study> result = studyService.findAllStudys();
		return new ResponseEntity<String>(Study.toJsonArray(result), headers,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Study study = Study.fromJsonToStudy(json);
		studyService.saveStudy(study);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Study study : Study.fromJsonArrayToStudys(json)) {
			studyService.saveStudy(study);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Study study = Study.fromJsonToStudy(json);
		if (studyService.updateStudy(study) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Study study : Study.fromJsonArrayToStudys(json)) {
			if (studyService.updateStudy(study) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		Study study = studyService.findStudy(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (study == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		studyService.deleteStudy(study);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
