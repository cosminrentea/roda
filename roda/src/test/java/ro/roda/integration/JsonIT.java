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

	public void checkJsonURL(URL url) throws IOException {

		// open connection to the server
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();

		// HTTP OK response ?
		Assert.assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());

		// try to parse the received JSON as a basic Java 'Object'
		Object jsonObject = new JSONDeserializer<Object>().deserialize(
				new BufferedReader(new InputStreamReader(conn.getInputStream())), Object.class);

		Assert.assertNotNull(jsonObject);
	}

	@Test
	public void testJsonBasic() throws IOException {

		checkJsonURL(new URL(homepageUrl + "/catalogtree"));

		checkJsonURL(new URL(homepageUrl + "/studiesbycatalog"));

	}

	@Test
	public void testJsonImportedData() throws IOException {
		// for complex URL parameters, use:
		// java.net.URLEncoder.encode(parameter, "UTF-8")

		checkJsonURL(new URL(homepageUrl + "/catalogtree/1"));

		checkJsonURL(new URL(homepageUrl + "/studiesbycatalog/1"));

	}

}