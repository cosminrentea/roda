package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import ro.roda.scheduler.Execution;
import ro.roda.scheduler.Task;
import ro.roda.service.SchedulerService;

@RequestMapping("/admin/scheduler")
@Controller
public class SchedulerController {

	@Autowired
	SchedulerService schedulerService;

	@RequestMapping(value = "/tasks/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		Task s = schedulerService.findTask(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (s == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(s.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(value="/tasks", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listTasks() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Task> result = schedulerService.findTasksAll();
		return new ResponseEntity<String>(Task.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value="/executions", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listExecutions() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Execution> result = schedulerService.findExecutionsAll();
		return new ResponseEntity<String>(Execution.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
//		AclClass aclClass = AclClass.fromJsonToAclClass(json);
//		aclClassService.saveAclClass(aclClass);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
//		for (AclClass aclClass : AclClass.fromJsonArrayToAclClasses(json)) {
//			aclClassService.saveAclClass(aclClass);
//		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
/*
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		AclClass aclClass = AclClass.fromJsonToAclClass(json);
		if (aclClassService.updateAclClass(aclClass) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (AclClass aclClass : AclClass.fromJsonArrayToAclClasses(json)) {
			if (aclClassService.updateAclClass(aclClass) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
		AclClass aclClass = aclClassService.findAclClass(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (aclClass == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		aclClassService.deleteAclClass(aclClass);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
*/	
}
