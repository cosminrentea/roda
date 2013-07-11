package ro.roda.integration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
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

	private static String dataBrowserUrl;

	private static String screenshotFilename;

	static Logger log = Logger.getLogger(WebDriverIT.class);

	@BeforeClass
	public static void beforeClass() throws IOException {

		// Set up a simple configuration that logs on the console
		BasicConfigurator.configure();

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
		Assert.assertNotNull("Property not set in: " + testProperties,
				dataBrowserUrl = props.getProperty("webdriver.DataBrowserUrl"));
		Assert.assertNotNull("Property not set in: " + testProperties, props.getProperty("webdriver.DefaultTimeout"));
		int defaultTimeout = Integer.parseInt(props.getProperty("webdriver.DefaultTimeout"));

		log.trace(firefoxPath);
		log.trace(displayNumber);
		log.trace(useDisplayNumber);
		log.trace(homepageUrl);
		log.trace(screenshotFilename);
		log.trace(dataBrowserUrl);
		log.trace(defaultTimeout);

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

	private boolean goesToCRUDTable(String lname) {
		try {
			WebElement lclink = driver.findElement(By.linkText(lname));
			lclink.click();
			WebElement element = driver.findElement(By.className("dijitTitlePaneTextNode"));
			String elcontent = element.getText();
			return elcontent.contains(lname);
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// TODO Sorin: restore following test
	// @Test
	public void testCRUD() throws Exception {
		driver.get(homepageUrl);
		Assert.assertTrue("List Catalogs OK", goesToCRUDTable("List all Catalogs"));
		Assert.assertTrue("List all Catalogs OK", goesToCRUDTable("List all Catalogs"));
		Assert.assertTrue("List all Catalog Studys OK", goesToCRUDTable("List all Catalog Studys"));
		Assert.assertTrue("List all Studys OK", goesToCRUDTable("List all Studys"));
		Assert.assertTrue("List all Instances OK", goesToCRUDTable("List all Instances"));
		Assert.assertTrue("List all Other Statistics OK", goesToCRUDTable("List all Other Statistics"));
		Assert.assertTrue("List all Topics OK", goesToCRUDTable("List all Topics"));
		Assert.assertTrue("List all Study Org Assocs OK", goesToCRUDTable("List all Study Org Assocs"));
		Assert.assertTrue("List all Form Edited Text Vars OK", goesToCRUDTable("List all Form Edited Text Vars"));
		Assert.assertTrue("List all Internets OK", goesToCRUDTable("List all Internets"));
		Assert.assertTrue("List all Scales OK", goesToCRUDTable("List all Scales"));
		Assert.assertTrue("List all User Setting Values OK", goesToCRUDTable("List all User Setting Values"));
		Assert.assertTrue("List all Form Edited Number Vars OK", goesToCRUDTable("List all Form Edited Number Vars"));
		Assert.assertTrue("List all Settings OK", goesToCRUDTable("List all Settings"));
		Assert.assertTrue("List all Org Prefixes OK", goesToCRUDTable("List all Org Prefixes"));
		Assert.assertTrue("List all Skips OK", goesToCRUDTable("List all Skips"));
		Assert.assertTrue("List all Person Orgs OK", goesToCRUDTable("List all Person Orgs"));
		Assert.assertTrue("List all Study Orgs OK", goesToCRUDTable("List all Study Orgs"));
		Assert.assertTrue("List all User Auth Logs OK", goesToCRUDTable("List all User Auth Logs"));
		Assert.assertTrue("List all Items OK", goesToCRUDTable("List all Items"));
		Assert.assertTrue("List all Studypeople OK", goesToCRUDTable("List all Studypeople"));
		Assert.assertTrue("List all Selection Variable Items OK", goesToCRUDTable("List all Selection Variable Items"));
		// TODO Cosmin: fix app using this table
		// Assert.assertTrue("List all Time Meth Types OK",
		// goesToCRUDTable("List all Time Meth Types"));
		Assert.assertTrue("List all Study Person Assocs OK", goesToCRUDTable("List all Study Person Assocs"));
		Assert.assertTrue("List all Translated Topics OK", goesToCRUDTable("List all Translated Topics"));
		Assert.assertTrue("List all Person Roles OK", goesToCRUDTable("List all Person Roles"));
		Assert.assertTrue("List all Org Relation Types OK", goesToCRUDTable("List all Org Relation Types"));
		Assert.assertTrue("List all Sources OK", goesToCRUDTable("List all Sources"));
		Assert.assertTrue("List all Regiontypes OK", goesToCRUDTable("List all Regiontypes"));
		Assert.assertTrue("List all Countrys OK", goesToCRUDTable("List all Countrys"));
		Assert.assertTrue("List all Suffixes OK", goesToCRUDTable("List all Suffixes"));
		Assert.assertTrue("List all Study Keywords OK", goesToCRUDTable("List all Study Keywords"));
		Assert.assertTrue("List all Org Sufixes OK", goesToCRUDTable("List all Org Sufixes"));
		Assert.assertTrue("List all Instance Person Assocs OK", goesToCRUDTable("List all Instance Person Assocs"));
		Assert.assertTrue("List all Instance Orgs OK", goesToCRUDTable("List all Instance Orgs"));
		Assert.assertTrue("List all Person Linkses OK", goesToCRUDTable("List all Person Linkses"));
		Assert.assertTrue("List all Org Addresses OK", goesToCRUDTable("List all Org Addresses"));
		Assert.assertTrue("List all Regions OK", goesToCRUDTable("List all Regions"));
		Assert.assertTrue("List all Selection Variables OK", goesToCRUDTable("List all Selection Variables"));
		Assert.assertTrue("List all User Messages OK", goesToCRUDTable("List all User Messages"));
		Assert.assertTrue("List all Addresses OK", goesToCRUDTable("List all Addresses"));
		Assert.assertTrue("List all Files OK", goesToCRUDTable("List all Files"));
		Assert.assertTrue("List all Instancepeople OK", goesToCRUDTable("List all Instancepeople"));
		Assert.assertTrue("List all Values OK", goesToCRUDTable("List all Values"));
		Assert.assertTrue("List all Emails OK", goesToCRUDTable("List all Emails"));
		Assert.assertTrue("List all Form Selection Vars OK", goesToCRUDTable("List all Form Selection Vars"));
		Assert.assertTrue("List all Prefixes OK", goesToCRUDTable("List all Prefixes"));
		Assert.assertTrue("List all Study Descrs OK", goesToCRUDTable("List all Study Descrs"));
		Assert.assertTrue("List all Person Addresses OK", goesToCRUDTable("List all Person Addresses"));
		Assert.assertTrue("List all Keywords OK", goesToCRUDTable("List all Keywords"));
		Assert.assertTrue("List all User Settings OK", goesToCRUDTable("List all User Settings"));
		Assert.assertTrue("List all Instance Org Assocs OK", goesToCRUDTable("List all Instance Org Assocs"));
		Assert.assertTrue("List all Setting Groups OK", goesToCRUDTable("List all Setting Groups"));
		Assert.assertTrue("List all Variables OK", goesToCRUDTable("List all Variables"));
		Assert.assertTrue("List all People OK", goesToCRUDTable("List all People"));
		Assert.assertTrue("List all Forms OK", goesToCRUDTable("List all Forms"));
		Assert.assertTrue("List all Instance Descrs OK", goesToCRUDTable("List all Instance Descrs"));
		Assert.assertTrue("List all Citys OK", goesToCRUDTable("List all Citys"));
		Assert.assertTrue("List all Concepts OK", goesToCRUDTable("List all Concepts"));
		Assert.assertTrue("List all Unit Analyses OK", goesToCRUDTable("List all Unit Analyses"));
		Assert.assertTrue("List all Vargroups OK", goesToCRUDTable("List all Vargroups"));
		Assert.assertTrue("List all Org Relationses OK", goesToCRUDTable("List all Org Relationses"));
		Assert.assertTrue("List all User Setting Groups OK", goesToCRUDTable("List all User Setting Groups"));
		Assert.assertTrue("List all Collection Model Types OK", goesToCRUDTable("List all Collection Model Types"));
		Assert.assertTrue("List all Orgs OK", goesToCRUDTable("List all Orgs"));
		Assert.assertTrue("List all Langs OK", goesToCRUDTable("List all Langs"));
		Assert.assertTrue("List all Org Phones OK", goesToCRUDTable("List all Org Phones"));
		Assert.assertTrue("List all Sampling Procedures OK", goesToCRUDTable("List all Sampling Procedures"));
		Assert.assertTrue("List all Person Emails OK", goesToCRUDTable("List all Person Emails"));
		Assert.assertTrue("List all Phones OK", goesToCRUDTable("List all Phones"));
		Assert.assertTrue("List all Org Internets OK", goesToCRUDTable("List all Org Internets"));
		Assert.assertTrue("List all Org Emails OK", goesToCRUDTable("List all Org Emails"));
		Assert.assertTrue("List all Study Person Assocs OK", goesToCRUDTable("List all Study Person Assocs"));
		Assert.assertTrue("List all Person Internets OK", goesToCRUDTable("List all Person Internets"));
		Assert.assertTrue("List all Person Phones OK", goesToCRUDTable("List all Person Phones"));
		Assert.assertTrue("List all News Items OK", goesToCRUDTable("List all News Items"));
		Assert.assertTrue("List all Cms Snippets OK", goesToCRUDTable("List all Cms Snippets"));
		Assert.assertTrue("List all Cms Page Types OK", goesToCRUDTable("List all Cms Page Types"));
		Assert.assertTrue("List all Cms Pages OK", goesToCRUDTable("List all Cms Pages"));
		Assert.assertTrue("List all Cms Folders OK", goesToCRUDTable("List all Cms Folders"));
		Assert.assertTrue("List all Cms Layout Groups OK", goesToCRUDTable("List all Cms Layout Groups"));
		Assert.assertTrue("List all Cms Files OK", goesToCRUDTable("List all Cms Files"));
		Assert.assertTrue("List all Cms Layouts OK", goesToCRUDTable("List all Cms Layouts"));
		Assert.assertTrue("List all Cms Page Contents OK", goesToCRUDTable("List all Cms Page Contents"));
		Assert.assertTrue("List all Cms Snippet Groups OK", goesToCRUDTable("List all Cms Snippet Groups"));
		Assert.assertTrue("List all Data Source Types OK", goesToCRUDTable("List all Data Source Types"));
		Assert.assertTrue("List all Series Descrs OK", goesToCRUDTable("List all Series Descrs"));
		Assert.assertTrue("List all Instance Right Target Groups OK",
				goesToCRUDTable("List all Instance Right Target Groups"));

		// TODO Cosmin: fix app using this table
		// Assert.assertTrue("List all Series Items OK",
		// goesToCRUDTable("List all Series Items"));
		Assert.assertTrue("List all Target Groups OK", goesToCRUDTable("List all Target Groups"));
		Assert.assertTrue("List all Instance Variables OK", goesToCRUDTable("List all Instance Variables"));
		Assert.assertTrue("List all Instance Right Values OK", goesToCRUDTable("List all Instance Right Values"));
		Assert.assertTrue("List all Instance Forms OK", goesToCRUDTable("List all Instance Forms"));
		Assert.assertTrue("List all Instance Rights OK", goesToCRUDTable("List all Instance Rights"));
	}

	@Test
	public void testAdminCRUD() throws Exception {

		// Verifica elementele CRUD care nu sunt visibile decat dupa
		// autentificare

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
		Assert.assertTrue("List all Users OK", goesToCRUDTable("List all Userses"));
		Assert.assertTrue("List all Authorities OK", goesToCRUDTable("List all Authoritieses"));
		Assert.assertTrue("List all Acl Sids OK", goesToCRUDTable("List all Acl Sids"));
		Assert.assertTrue("List all Acl Classes OK", goesToCRUDTable("List all Acl Classes"));
		Assert.assertTrue("List all Acl Object Identitys OK", goesToCRUDTable("List all Acl Object Identitys"));
		Assert.assertTrue("List all Acl Entrys OK", goesToCRUDTable("List all Acl Entrys"));

		WebElement logoutLink = (new WebDriverWait(driver, defaultTimeout)).until(ExpectedConditions
				.presenceOfElementLocated(By.partialLinkText("Logout")));
		Assert.assertNotNull(logoutLink);
		logoutLink.click();

	}

	@Test
	public void testDataBrowser() throws Exception {

		// dat fiind ca data browserul se deschide intr-o noua fereastra,
		// deocamdata mergem direct la url-ul lui
		// la un moment dat o sa vedem cum putem sari de la fereastra principala
		// a roda, unde se face loginul, in fereastra lui

		driver.get(dataBrowserUrl);

		// verifica structura generala a panelului

		WebElement dbTitle = driver.findElement(By.id("ext-comp-1009_header"))
				.findElement(By.id("ext-comp-1009_header-body")).findElement(By.id("ext-comp-1009_header-innerCt"))
				.findElement(By.id("ext-comp-1009_header-targetEl")).findElement(By.id("ext-comp-1009_header_hd"))
				.findElement(By.id("ext-comp-1009_header_hd-textEl"));
		Assert.assertTrue("Data Browser panel structure OK", dbTitle.getText().contains("RODA - Data Browser"));

		// cataloage - verifica ce scrie pe buton si incarcarea containerului
		// pentru tree la click

		WebElement catalogLabel = driver.findElement(By.id("CatalogsTabConfig-btnInnerEl"));
		Assert.assertTrue("Catalog Button OK", catalogLabel.getText().contains("Cataloage"));

		WebElement catalogHandler = driver.findElement(By.id("CatalogsTabConfig"));
		catalogHandler.click();
		Assert.assertTrue("Catalog tree panel loaded ok", driver.findElement(By.id("CatalogsTreeView")).isDisplayed());

		// ani - verifica ce scrie pe buton si incarcarea containerului pentru
		// tree la click

		WebElement aniLabel = driver.findElement(By.id("YearsTabConfig-btnEl"));
		Assert.assertTrue("Years Button OK", aniLabel.getText().contains("Ani"));

		WebElement aniHandler = driver.findElement(By.id("YearsTabConfig"));
		aniHandler.click();
		Assert.assertTrue("Years tree panel loaded ok", driver.findElement(By.id("YearsTreeView")).isDisplayed());

		// utilizatori - verifica ce scrie pe buton si incarcarea containerului
		// pentru tree la click

		WebElement utLabel = driver.findElement(By.id("UsersTabConfig-btnEl"));
		Assert.assertTrue("Users Button OK", utLabel.getText().contains("Utilizatori"));

		WebElement utHandler = driver.findElement(By.id("UsersTabConfig"));
		utHandler.click();
		Assert.assertTrue("Users grid panel loaded ok", driver.findElement(By.id("UsersGridView")).isDisplayed());

	}

}