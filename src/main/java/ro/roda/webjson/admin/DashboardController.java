package ro.roda.webjson.admin;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/adminjson/dashboard")
@Controller
public class DashboardController {

	@RequestMapping(value = "/browser", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> browser() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String mockResult = "{ \n" + "    \"success\": true,\n" + "    \"data\" : [\n" + "        {\n"
				+ "            \"name\" : \"Google Chrome\",\n" + "            \"value\" : 48\n" + "        },\n"
				+ "        {\n" + "            \"name\" : \"Firefox\",\n" + "            \"value\" : 19\n"
				+ "        },\n" + "        {\n" + "            \"name\" : \"Safari\",\n"
				+ "            \"value\" : 17\n" + "        },\n" + "        {\n"
				+ "            \"name\" : \"Internet Explorer\",\n" + "            \"value\" : 12\n" + "        },\n"
				+ "        {\n" + "            \"name\" : \"Opera\",\n" + "            \"value\" : 3\n"
				+ "        },\n" + "        {\n" + "            \"name\" : \"Others\",\n"
				+ "            \"value\" : 1\n" + "        }\n" + "        ]\n" + "}\n";
		return new ResponseEntity<String>(mockResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/trafic", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> trafic() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String mockResult = "{ \n" + "    \"success\": true,\n" + "    \"data\" : [\n" + "        {\n"
				+ "            \"day\" : \"23-11-2014\",\n" + "            \"visits\" : 72,\n"
				+ "            \"visitors\" : 6,\n" + "            \"views\" : 124,\n" + "        },\n" + "        {\n"
				+ "            \"day\" : \"24-11-2014\",\n" + "            \"visits\" : 76,\n"
				+ "            \"visitors\" : 8,\n" + "            \"views\" : 168,\n" + "        },\n" + "        {\n"
				+ "            \"day\" : \"25-11-2014\",\n" + "            \"visits\" : 85,\n"
				+ "            \"visitors\" : 9,\n" + "            \"views\" : 183,\n" + "        },\n" + "        {\n"
				+ "            \"day\" : \"26-11-2014\",\n" + "            \"visits\" : 98,\n"
				+ "            \"visitors\" : 10,\n" + "            \"views\" : 197,\n" + "        },\n"
				+ "        {\n" + "            \"day\" : \"27-11-2014\",\n" + "            \"visits\" : 127,\n"
				+ "            \"visitors\" : 8,\n" + "            \"views\" : 289,\n" + "        },\n" + "        {\n"
				+ "            \"day\" : \"28-11-2014\",\n" + "            \"visits\" : 40,\n"
				+ "            \"visitors\" : 15,\n" + "            \"views\" : 126,\n" + "        }   \n"
				+ "        ]\n" + "}";
		return new ResponseEntity<String>(mockResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/tempstudies", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> tempstudies() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String mockResult = "{ \n" + "    \"success\": true,\n" + "    \"data\" : [\n" + "        {\n"
				+ "            \"started\" : \"08-11-2014\",\n"
				+ "            \"startedby\" : \"Alexandra Gheondea\",\n" + "            \"title\" : \"Studiu nou\"\n"
				+ "        },\n" + "        {\n" + "            \"started\" : \"09-11-2014\",\n"
				+ "            \"startedby\" : \"Alexandra Ciritel\",\n"
				+ "            \"title\" : \"Studiul temporar\"\n" + "        }\n" + "        ]\n" + "}";
		return new ResponseEntity<String>(mockResult, headers, HttpStatus.OK);
	}

}
