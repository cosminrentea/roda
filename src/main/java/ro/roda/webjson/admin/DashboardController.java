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
				+ "            \"day\" : \"12-09-2014\",\n" + "            \"visits\" : 112,\n"
				+ "            \"visitors\" : 96,\n" + "            \"views\" : 124,\n" + "        },\n"
				+ "        {\n" + "            \"day\" : \"13-09-2014\",\n" + "            \"visits\" : 128,\n"
				+ "            \"visitors\" : 100,\n" + "            \"views\" : 168,\n" + "        },\n"
				+ "        {\n" + "            \"day\" : \"14-09-2014\",\n" + "            \"visits\" : 145,\n"
				+ "            \"visitors\" : 112,\n" + "            \"views\" : 183,\n" + "        },\n"
				+ "        {\n" + "            \"day\" : \"15-09-2014\",\n" + "            \"visits\" : 110,\n"
				+ "            \"visitors\" : 99,\n" + "            \"views\" : 172,\n" + "        },\n"
				+ "        {\n" + "            \"day\" : \"16-09-2014\",\n" + "            \"visits\" : 101,\n"
				+ "            \"visitors\" : 88,\n" + "            \"views\" : 189,\n" + "        },\n"
				+ "        {\n" + "            \"day\" : \"17-09-2014\",\n" + "            \"visits\" : 98,\n"
				+ "            \"visitors\" : 72,\n" + "            \"views\" : 111,\n" + "        }   \n"
				+ "        ]\n" + "}";
		return new ResponseEntity<String>(mockResult, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/tempstudies", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> tempstudies() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String mockResult = "{ \n" + "    \"success\": true,\n" + "    \"data\" : [\n" + "        {\n"
				+ "            \"started\" : \"11-09-2014\",\n" + "            \"startedby\" : \"Irina Cristescu\",\n"
				+ "            \"title\" : \"Studiul meu\"\n" + "        },\n" + "        {\n"
				+ "            \"started\" : \"12-09-2014\",\n"
				+ "            \"startedby\" : \"Alexandra Ciritel\",\n"
				+ "            \"title\" : \"Studiul temporar\"\n" + "        }\n" + "        ]\n" + "}";
		return new ResponseEntity<String>(mockResult, headers, HttpStatus.OK);
	}

}
