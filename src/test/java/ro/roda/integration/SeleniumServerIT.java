package ro.roda.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;

public class SeleniumServerIT {

	private static final String testProperties = "test.properties";

	private static String homepageUrl;

	private static DefaultSelenium s;

	static Logger log = Logger.getLogger(SeleniumServerIT.class);

	@BeforeClass
	public static void beforeClass() throws IOException {

		// Set up a simple configuration that logs on the console
		BasicConfigurator.configure();

		InputStream is = SeleniumServerIT.class.getClassLoader().getResourceAsStream(testProperties);
		Assert.assertNotNull("Not found: " + testProperties, is);

		Properties props = new Properties();
		props.load(is);

		String serverHost, browserOptions;
		Assert.assertNotNull("Property not set in: " + testProperties,
				serverHost = props.getProperty("selenium.ServerHost"));
		Assert.assertNotNull("Property not set in: " + testProperties, props.getProperty("selenium.ServerPort"));
		Assert.assertNotNull("Property not set in: " + testProperties,
				browserOptions = props.getProperty("selenium.BrowserOptions"));
		Assert.assertNotNull("Property not set in: " + testProperties,
				homepageUrl = props.getProperty("selenium.HomepageUrl"));
		int serverPort = Integer.parseInt(props.getProperty("selenium.ServerPort"));

		log.trace(serverHost);
		log.trace(serverPort);
		log.trace(browserOptions);
		log.trace(homepageUrl);

		s = new DefaultSelenium(serverHost, serverPort, browserOptions, homepageUrl);
		s.start();
	}

	@AfterClass
	public static void afterClass() {
		if (s != null) {
			s.stop();
		}
	}

	@Test
	public void homepage() throws Exception {
		s.open(homepageUrl);
		Assert.assertTrue(s.isTextPresent("RODA"));
		Assert.assertTrue("Welcome to RODA".equals(s.getTitle()));
	}

}