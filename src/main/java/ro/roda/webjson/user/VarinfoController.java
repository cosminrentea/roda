package ro.roda.webjson.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/userjson/varinfo")
@Controller
public class VarinfoController {

	@RequestMapping(value = "/13", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> browser() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String mockResult = "{   \"success\" : true,\n" + "    \"data\":{\n" + "        \"indice\" : \"10693\",\n"
				+ "	    \"label\" : \"Asigurarea caldurii pe timpul iernii\",\n" + "	    \"name\" : \"II1_D\",\n"
				+ "        \"respdomain\" : \"Code\",\n" + "        \"coderesponses\" : [\n" + "            {\n"
				+ "            \"label\" : \"da\",\n" + "            \"value\" : 1,\n" + "            \"id\" : 1234\n"
				+ "            },\n" + "            {\n" + "            \"label\" : \"nu\",\n"
				+ "            \"value\" : 3,\n" + "            \"id\" : 1235\n" + "            },\n"
				+ "            ],\n" + "        \"missing\" : [\n" + "                {\n"
				+ "                    \"id\" : 122,\n" + "                    \"label\": \"nu stiu\",\n"
				+ "                    \"value\" : 99\n" + "                },\n" + "                {\n"
				+ "                    \"id\" : 123,\n" + "                    \"label\": \"nu raspund\",\n"
				+ "                    \"value\" : 98\n" + "                }\n" + "            ]            \n"
				+ "        }\n" + "}\n";
		return new ResponseEntity<String>(mockResult, headers, HttpStatus.OK);
	}

}
