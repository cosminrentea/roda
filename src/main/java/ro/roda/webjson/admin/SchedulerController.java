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

import ro.roda.domain.SchedExecution;
import ro.roda.domain.SchedTask;
import ro.roda.service.SchedulerService;

@RequestMapping("/adminjson/scheduler")
@Controller
public class SchedulerController {

	@Autowired
	SchedulerService schedulerService;

	@RequestMapping(value = "/tasks", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listTasks() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SchedTask> result = schedulerService.findTasksAll();
		String mockResult = "{\n" + "	\"success\": true,\n" + "    \"data\":[\n" + "            {\n"
				+ "				\"id\" : \"1\",\n" + "				\"name\" : \"Vacuum Database\",\n"
				+ "				\"description\" : \"Task for Postgresql VACUUM\",\n" + "				\"iconCls\" : \"task\",\n"
				+ "				\"classname\" : \"ro.roda.scheduler.tasks.Vacuum\",\n" + "				\"cron\" : \"0 0 */1 * * *\", \n"
				+ "				\"enabled\" : true,\n" + "				\"timestamp_last_execution\": \"2013-04-03 12:00:00\",\n"
				+ "				\"timestamp_next_execution\": \"2013-04-03 13:00:00\",\n" + "				\"leaf\" : true\n"
				+ "			}, {\n" + "				\"id\" : \"2\",\n" + "				\"name\" : \"Backup Database\",\n"
				+ "				\"description\" : \"Task for automated backup\",\n" + "				\"iconCls\" : \"task\",\n"
				+ "				\"classname\" : \"ro.roda.scheduler.tasks.Backup\",\n" + "				\"cron\" : \"0 */30 * * * *\", \n"
				+ "				\"timestamp_last_execution\": \"2013-04-03 12:00:00\",				\n"
				+ "				\"timestamp_next_execution\": \"2013-04-03 13:30:00\",\n" + "				\"enabled\" : true,\n"
				+ "				\"leaf\" : true\n" + "			}, {\n" + "				\"id\" : \"3\",\n"
				+ "				\"name\" : \"Clean User Sessions\",\n"
				+ "				\"description\" : \"Cleans up all expired user sessions\",\n" + "				\"iconCls\" : \"task\",\n"
				+ "				\"classname\" : \"ro.roda.scheduler.tasks.SessionCleanUp\",\n"
				+ "				\"cron\" : \"0 */30 * * * *\", \n"
				+ "				\"timestamp_last_execution\": \"2013-04-03 12:00:00\",				\n"
				+ "				\"timestamp_next_execution\": \"2013-04-03 13:30:00\",\n" + "				\"enabled\" : false,\n"
				+ "				\"leaf\" : true\n" + "			},{\n" + "				\"id\" : \"4\",\n"
				+ "				\"name\" : \"Backup CMS FileStore\",\n"
				+ "				\"description\" : \"Exports main content repository in interchangeable format\",\n"
				+ "				\"iconCls\" : \"task\",\n" + "				\"classname\" : \"ro.roda.scheduler.tasks.ExportCR\",\n"
				+ "				\"cron\" : \"0 */30 * * * *\", \n"
				+ "				\"timestamp_last_execution\": \"2013-04-03 12:00:00\",				\n"
				+ "				\"timestamp_next_execution\": \"2013-04-03 13:30:00\",\n" + "				\"enabled\" : true,\n"
				+ "				\"leaf\" : true\n" + "			}, {\n" + "				\"id\" : \"5\",\n"
				+ "				\"name\" : \"Clean Page Previews\",\n"
				+ "				\"description\" : \"Cleans page previews folder\",\n" + "				\"iconCls\" : \"task\",\n"
				+ "				\"classname\" : \"ro.roda.scheduler.tasks.CleanPagePreviews\",\n"
				+ "				\"cron\" : \"0 */30 * * * *\", \n"
				+ "				\"timestamp_last_execution\": \"2013-04-03 12:00:00\",				\n"
				+ "				\"timestamp_next_execution\": \"2013-04-03 13:30:00\",\n" + "				\"enabled\" : true,\n"
				+ "				\"leaf\" : true\n" + "			}\n" + "			]\n" + "}\n";
		return new ResponseEntity<String>(mockResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/tasks/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showTask(@PathVariable("id") Integer id) {
		SchedTask s = schedulerService.findTask(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (s == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(s.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/executions", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listExecutions() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SchedExecution> result = schedulerService.findExecutionsAll();
		return new ResponseEntity<String>(SchedExecution.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/executions/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showExecution(@PathVariable("id") Integer id) {
		SchedExecution e = schedulerService.findExecution(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (e == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(e.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/executionsbytask/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showExecutionsByTask(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// SchedExecution e = schedulerService.findExecution(id);
		// if (e == null) {
		// return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		// }
		String mockResult = "{\n" + "	\"success\": true,\n" + "    \"data\": [{\n" + "				\"id\" : \"1\",\n"
				+ "				\"type\": \"manual\",\n" + "				\"result\" :  0,\n" + "				\"iconCls\" : \"execution\",\n"
				+ "				\"timestamp_start\" : \"2013-04-03 12:30:00\", \n" + "				\"duration\" : \"5000\",\n"
				+ "				\"leaf\" : true\n" + "			}, {\n" + "				\"id\" : \"2\",\n" + "				\"type\": \"scheduled\",\n"
				+ "				\"result\" :  1,\n" + "				\"iconCls\" : \"execution\",\n"
				+ "				\"timestamp_start\" : \"2013-04-03 12:31:00\", \n" + "				\"duration\" : \"300\",\n"
				+ "				\"leaf\" : true\n" + "			}, {\n" + "				\"id\" : \"3\",\n" + "				\"type\": \"scheduled\",\n"
				+ "				\"result\" :  0,\n" + "				\"iconCls\" : \"execution\",\n"
				+ "				\"timestamp_start\" : \"2013-04-03 12:32:00\", \n" + "				\"duration\" : \"300\",\n"
				+ "				\"leaf\" : true\n" + "			}]\n" + "}";
		return new ResponseEntity<String>(mockResult, headers, HttpStatus.OK);
	}

}
