package ro.roda.integration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverIT {

	private static final String testProperties = "test.properties";

	private static WebDriver driver;

	private static int defaultTimeout;

	private static String homepageUrl;

	private static String screenshotFilename;

	@BeforeClass
	public static void beforeClass() throws IOException {

		InputStream is = SeleniumServerIT.class.getClassLoader().getResourceAsStream(testProperties);
		Assert.assertNotNull("Not found: " + testProperties, is);

		Properties props = new Properties();
		props.load(is);

		String firefoxPath, displayNumber, useDisplayNumber;
		Assert.assertNotNull("Property not set in: " + testProperties,
				firefoxPath = props.getProperty("webdriver.FirefoxPath"));
		Assert.assertNotNull("Property not set in: " + testProperties,
				displayNumber = props.getProperty("webdriver.DisplayNumber"));
		Assert.assertNotNull("Property not set in: " + testProperties,
				useDisplayNumber = props.getProperty("webdriver.UseDisplayNumber"));
		Assert.assertNotNull("Property not set in: " + testProperties,
				homepageUrl = props.getProperty("webdriver.HomepageUrl"));
		Assert.assertNotNull("Property not set in: " + testProperties,
				screenshotFilename = props.getProperty("webdriver.ScreenshotFilename"));
		Assert.assertNotNull("Property not set in: " + testProperties, props.getProperty("webdriver.DefaultTimeout"));
		int defaultTimeout = Integer.parseInt(props.getProperty("webdriver.DefaultTimeout"));

		// use a specific Firefox binary
		FirefoxBinary fb = new FirefoxBinary(new File(firefoxPath));

		// when required, set the DISPLAY to be used by Firefox when testing
		// (e.g. when Xvfb is running)
		if ("yes".equalsIgnoreCase(useDisplayNumber)) {
			fb.setEnvironmentProperty("DISPLAY", displayNumber);
		}
		driver = new FirefoxDriver(fb, null);

		// set default timeout
		driver.manage().timeouts().implicitlyWait(defaultTimeout, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void afterClass() {
		driver.close();
	}

	@Test
	public void screenshot() throws Exception {
		driver.get(homepageUrl);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenshotFilename));
	}

	@Test
	public void homepage() throws Exception {
		driver.get(homepageUrl);
		Assert.assertEquals("Welcome to RODA", driver.getTitle());
		Assert.assertNotNull(driver.findElement(By.id("header")));
		Assert.assertNotNull(driver.findElement(By.id("language")));
		Assert.assertNotNull(driver.findElement(By.id("menu")));
	}

	@Test
	public void loginAdmin() throws Exception {
		driver.get(homepageUrl);
		WebElement loginLink = driver.findElement(By.partialLinkText("Login"));
		Assert.assertNotNull(loginLink);
		loginLink.click();
		WebElement submitButton = (new WebDriverWait(driver, defaultTimeout)).until(ExpectedConditions
				.presenceOfElementLocated(By.id("proceed")));
		WebElement usernameInput = driver.findElement(By.id("j_username"));
		WebElement passwordInput = driver.findElement(By.id("j_password"));
		Assert.assertNotNull(usernameInput);
		Assert.assertNotNull(passwordInput);
		usernameInput.sendKeys("admin");
		passwordInput.sendKeys("admin");
		submitButton.click();
		WebElement logoutLink = (new WebDriverWait(driver, defaultTimeout)).until(ExpectedConditions
				.presenceOfElementLocated(By.partialLinkText("Logout")));
		Assert.assertNotNull(logoutLink);
		logoutLink.click();
		loginLink = (new WebDriverWait(driver, defaultTimeout)).until(ExpectedConditions.presenceOfElementLocated(By
				.partialLinkText("Login")));
		Assert.assertNotNull(loginLink);
		Assert.assertEquals(homepageUrl, driver.getCurrentUrl());
	}
}