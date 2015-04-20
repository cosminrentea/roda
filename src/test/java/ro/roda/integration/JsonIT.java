package ro.roda.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import flexjson.JSONDeserializer;

public class JsonIT {

	private static final String testProperties = "test.properties";

	private static String homepageUrl;

	static Logger log = Logger.getLogger(JsonIT.class);

	@BeforeClass
	public static void beforeClass() throws IOException {

		// Set up a simple configuration that logs on the console
		BasicConfigurator.configure();

		InputStream is = SeleniumServerIT.class.getClassLoader().getResourceAsStream(testProperties);
		Assert.assertNotNull("Not found: " + testProperties, is);

		Properties props = new Properties();
		props.load(is);

		Assert.assertNotNull("Property not set in: " + testProperties,
				homepageUrl = props.getProperty("webdriver.HomepageUrl"));

		log.trace(homepageUrl);
	}

	@AfterClass
	public static void afterClass() {
	}

	public void checkJson(String urlPart) throws IOException {

		log.trace(urlPart);

		URL url = new URL(homepageUrl + urlPart);
		// for complex URL parameters, use:
		// java.net.URLEncoder.encode(parameter, "UTF-8")

		// open connection to the server
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Accept", "application/json");

		conn.connect();

		// HTTP OK response ?
		Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());

		// try to parse the received JSON as a basic Java 'Object'
		try {
			Object jsonObject = new JSONDeserializer<Object>().deserialize(new BufferedReader(new InputStreamReader(
					conn.getInputStream())), Object.class);

			Assert.assertNotNull(jsonObject);
		} catch (Exception e) {
			// log.trace(e);

			// conn = (HttpURLConnection) url.openConnection();
			// conn.setRequestProperty("Accept", "application/json");
			//
			// conn.connect();
			//
			// // HTTP OK response ?
			// Assert.assertEquals(HttpURLConnection.HTTP_OK,
			// conn.getResponseCode());
			//
			// // try to parse the received JSON as a list
			// try {
			// Collection jsonObject = new
			// JSONDeserializer<Collection>().deserialize(new BufferedReader(
			// new InputStreamReader(conn.getInputStream())));
			//
			// Assert.assertNotNull(jsonObject);
			// } catch (Exception e2) {
			// // log.trace(e2);
			// }

		}

		conn.disconnect();

	}

	@Test
	public void testJsonBasic() throws IOException {

		checkJson("/aclclasses");
		checkJson("/aclentrys");
		checkJson("/aclobjectidentitys");
		checkJson("/aclsids");
		checkJson("/addresses");
		checkJson("/authoritieses");
		checkJson("/catalogs");
		checkJson("/catalogstudys");
		checkJson("/catalogtree");
		checkJson("/citys");
		checkJson("/cmsfiles");
		checkJson("/cmsfolders");
		checkJson("/cmslayouts");
		checkJson("/cmslayoutgroups");
		checkJson("/cmspagecontents");
		checkJson("/cmspages");
		checkJson("/cmspagetypes");
		checkJson("/cmssnippets");
		checkJson("/cmssnippetgroups");
		checkJson("/collectionmodeltypes");
		checkJson("/concepts");
		checkJson("/countrys");
		checkJson("/datasourcetypes");
		checkJson("/emails");
		checkJson("/files");
		checkJson("/forms");
		checkJson("/formeditednumbervars");
		checkJson("/formeditedtextvars");
		checkJson("/formselectionvars");
		checkJson("/instances");
		checkJson("/instancedescrs");
		checkJson("/instanceforms");
		checkJson("/instanceorgassocs");
		checkJson("/instanceorgs");
		checkJson("/instancepersonassocs");
		checkJson("/instancepeople");
		checkJson("/instancerights");
		checkJson("/instancerighttargetgroups");
		checkJson("/instancerightvalues");
		checkJson("/instancevariables");
		checkJson("/internets");
		checkJson("/items");
		checkJson("/keywords");
		checkJson("/langs");
		checkJson("/newspieces");
		checkJson("/orgaddresses");
		checkJson("/orgs");
		checkJson("/orgemails");
		checkJson("/orginternets");
		checkJson("/orgphones");
		checkJson("/orgprefixes");
		checkJson("/orgrelationtypes");
		checkJson("/orgrelationses");
		checkJson("/orgsufixes");

		// disabled due to large amount of data (imported)
		// checkJson("/otherstatistics");

		checkJson("/personaddresses");
		checkJson("/people");
		checkJson("/personemails");
		checkJson("/personinternets");
		checkJson("/personlinkses");
		checkJson("/personorgs");
		checkJson("/personphones");
		checkJson("/personroles");
		checkJson("/phones");
		checkJson("/prefixes");
		checkJson("/regions");
		checkJson("/regiontypes");
		checkJson("/samplingprocedures");
		checkJson("/scales");
		checkJson("/selectionvariables");
		checkJson("/selectionvariableitems");
		checkJson("/serieses");
		checkJson("/seriesdescrs");
		checkJson("/settings");
		checkJson("/settinggroups");
		checkJson("/skips");
		checkJson("/sources");
		checkJson("/studiesbycatalog");
		checkJson("/studiesbyseries");
		checkJson("/studiesbyyear");
		checkJson("/studys");
		checkJson("/studydescrs");
		checkJson("/studyinfo");
		checkJson("/studykeywords");
		checkJson("/studyorgassocs");
		checkJson("/studyorgs");
		checkJson("/studypersonassocs");
		checkJson("/studypeople");
		checkJson("/suffixes");
		checkJson("/targetgroups");
		checkJson("/timemethtypes");
		checkJson("/topics");
		checkJson("/translatedtopics");
		checkJson("/unitanalyses");
		checkJson("/userauthlogs");
		checkJson("/usermessages");
		checkJson("/usersettings");
		checkJson("/usersettinggroups");
		checkJson("/usersettingvalues");
		checkJson("/userses");
		checkJson("/values");
		checkJson("/vargroups");
		checkJson("/variables");
		checkJson("/yearstree");

	}

	@Test
	public void testJsonImportedData() throws IOException {

		// TODO check the following commented URLs !

		checkJson("/aclclasses/1");
		checkJson("/aclentrys/1");
		checkJson("/aclobjectidentitys/1");
		checkJson("/aclsids/1");
		checkJson("/addresses/1");

		// checkJson("/authoritieses/1");

		checkJson("/catalogs/1");

		// checkJson("/catalogstudys/1");

		checkJson("/j/catalogtree/1");
		checkJson("/citys/1");

		// checkJson("/cmsfiles/1");
		// checkJson("/cmsfolders/1");
		// checkJson("/cmslayouts/1");
		// checkJson("/cmslayoutgroups/1");
		// checkJson("/cmspagecontents/1");
		// checkJson("/cmspages/1");
		// checkJson("/cmspagetypes/1");
		// checkJson("/cmssnippets/1");
		// checkJson("/cmssnippetgroups/1");

		// checkJson("/collectionmodeltypes/1");
		// checkJson("/concepts/1");
		checkJson("/countrys/1");
		checkJson("/datasourcetypes/1");
		// checkJson("/emails/1");
		checkJson("/files/1");
		// checkJson("/forms/1");
		// checkJson("/formeditednumbervars/1");
		// checkJson("/formeditedtextvars/1");
		// checkJson("/formselectionvars/1");
		checkJson("/instances/1");
		// checkJson("/instancedescrs/1");
		// checkJson("/instanceforms/1");
		// checkJson("/instanceorgassocs/1");
		// checkJson("/instanceorgs/1");
		// checkJson("/instancepersonassocs/1");
		// checkJson("/instancepeople/1");
		// checkJson("/instancerights/1");
		// checkJson("/instancerighttargetgroups/1");
		// checkJson("/instancerightvalues/1");
		// checkJson("/instancevariables/1");
		// checkJson("/internets/1");
		// checkJson("/items/1");
		checkJson("/keywords/1");
		checkJson("/langs/1");
		// checkJson("/newspieces/1");
		// checkJson("/orgaddresses/1");
		// checkJson("/orgs/1");
		// checkJson("/orgemails/1");
		// checkJson("/orginternets/1");
		// checkJson("/orgphones/1");
		// checkJson("/orgprefixes/1");
		// checkJson("/orgrelationtypes/1");
		// checkJson("/orgrelationses/1");
		// checkJson("/orgsufixes/1");
		checkJson("/otherstatistics/1");
		// checkJson("/personaddresses/1");
		// checkJson("/people/1");
		// checkJson("/personemails/1");
		// checkJson("/personinternets/1");
		// checkJson("/personlinkses/1");
		// checkJson("/personorgs/1");
		// checkJson("/personphones/1");
		// checkJson("/personroles/1");
		// checkJson("/phones/1");
		checkJson("/prefixes/1");
		checkJson("/regions/1");
		checkJson("/regiontypes/1");
		checkJson("/samplingprocedures/1");
		// checkJson("/scales/1");
		// checkJson("/selectionvariables/1");
		// checkJson("/selectionvariableitems/1");
		// checkJson("/serieses/1");
		// checkJson("/seriesdescrs/1");
		// checkJson("/settings/1");
		// checkJson("/settinggroups/1");
		// checkJson("/skips/1");
		// checkJson("/sources/1");
		checkJson("/studiesbycatalog/1");
		// checkJson("/studiesbyseries/1");
		// checkJson("/studiesbyyear/1");
		checkJson("/studys/1");
		// checkJson("/studydescrs/1");
		checkJson("/studyinfo/1");
		// checkJson("/studykeywords/1");
		// checkJson("/studyorgassocs/1");
		// checkJson("/studyorgs/1");
		// checkJson("/studypersonassocs/1");
		// checkJson("/studypeople/1");
		checkJson("/suffixes/1");
		// checkJson("/targetgroups/1");
		checkJson("/timemethtypes/1");
		checkJson("/topics/1");
		// checkJson("/translatedtopics/1");
		checkJson("/unitanalyses/1");
		// checkJson("/userauthlogs/1");
		// checkJson("/usermessages/1");
		// checkJson("/usersettings/1");
		// checkJson("/usersettinggroups/1");
		// checkJson("/usersettingvalues/1");
		checkJson("/userses/1");
		// checkJson("/values/1");
		// checkJson("/vargroups/1");
		checkJson("/variables/1");
		// checkJson("/yearstree/1");

	}

}