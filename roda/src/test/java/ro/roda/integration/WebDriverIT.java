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

	private static String loginPageUrl;
	
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
				loginPageUrl = props.getProperty("webdriver.loginPageUrl"));
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
    public void testLogin() throws Exception {  
		log.trace("starting login test");
		driver.get(loginPageUrl);  
        RodaLoginPage loginPage = new RodaLoginPage(driver);  
        loginPage.loginAs("admin", "admin");  
        Assert.assertEquals("Logout",loginPage.getLogoutLink());
    } 
	

	// TODO Sorin: restore following test
	@Test
	public void testCRUD() throws Exception {
		log.trace("starting crud test");
		driver.get(loginPageUrl);  
        RodaLoginPage loginPage = new RodaLoginPage(driver);  
        loginPage.loginAs("admin", "admin");  

        driver.get(homepageUrl);		
        RodaHomePage homePage = new RodaHomePage(driver);
		
		
		Assert.assertTrue("List Catalogs OK", homePage.goesToCRUDTable("List all Catalogs"));
		Assert.assertTrue("List all Catalogs OK", homePage.goesToCRUDTable("List all Catalogs"));
		Assert.assertTrue("List all Catalog Studys OK", homePage.goesToCRUDTable("List all Catalog Studys"));
		Assert.assertTrue("List all Studys OK", homePage.goesToCRUDTable("List all Studys"));
		Assert.assertTrue("List all Instances OK", homePage.goesToCRUDTable("List all Instances"));
		Assert.assertTrue("List all Other Statistics OK", homePage.goesToCRUDTable("List all Other Statistics"));
		Assert.assertTrue("List all Topics OK", homePage.goesToCRUDTable("List all Topics"));
		Assert.assertTrue("List all Study Org Assocs OK", homePage.goesToCRUDTable("List all Study Org Assocs"));
		Assert.assertTrue("List all Form Edited Text Vars OK", homePage.goesToCRUDTable("List all Form Edited Text Vars"));
		Assert.assertTrue("List all Internets OK", homePage.goesToCRUDTable("List all Internets"));
		Assert.assertTrue("List all Scales OK", homePage.goesToCRUDTable("List all Scales"));
		Assert.assertTrue("List all User Setting Values OK", homePage.goesToCRUDTable("List all User Setting Values"));
		Assert.assertTrue("List all Form Edited Number Vars OK", homePage.goesToCRUDTable("List all Form Edited Number Vars"));
		Assert.assertTrue("List all Settings OK", homePage.goesToCRUDTable("List all Settings"));
		Assert.assertTrue("List all Org Prefixes OK", homePage.goesToCRUDTable("List all Org Prefixes"));
		Assert.assertTrue("List all Skips OK", homePage.goesToCRUDTable("List all Skips"));
		Assert.assertTrue("List all Person Orgs OK", homePage.goesToCRUDTable("List all Person Orgs"));
		Assert.assertTrue("List all Study Orgs OK", homePage.goesToCRUDTable("List all Study Orgs"));
		Assert.assertTrue("List all User Auth Logs OK", homePage.goesToCRUDTable("List all User Auth Logs"));
		Assert.assertTrue("List all Items OK", homePage.goesToCRUDTable("List all Items"));
		Assert.assertTrue("List all Studypeople OK", homePage.goesToCRUDTable("List all Studypeople"));
		Assert.assertTrue("List all Selection Variable Items OK", homePage.goesToCRUDTable("List all Selection Variable Items"));
		// TODO Cosmin: fix app using this table
		// Assert.assertTrue("List all Time Meth Types OK",
		// goesToCRUDTable("List all Time Meth Types"));
		Assert.assertTrue("List all Study Person Assocs OK", homePage.goesToCRUDTable("List all Study Person Assocs"));
		Assert.assertTrue("List all Translated Topics OK", homePage.goesToCRUDTable("List all Translated Topics"));
		Assert.assertTrue("List all Person Roles OK", homePage.goesToCRUDTable("List all Person Roles"));
		Assert.assertTrue("List all Org Relation Types OK", homePage.goesToCRUDTable("List all Org Relation Types"));
		Assert.assertTrue("List all Sources OK", homePage.goesToCRUDTable("List all Sources"));
		Assert.assertTrue("List all Regiontypes OK", homePage.goesToCRUDTable("List all Regiontypes"));
		Assert.assertTrue("List all Countrys OK", homePage.goesToCRUDTable("List all Countrys"));
		Assert.assertTrue("List all Suffixes OK", homePage.goesToCRUDTable("List all Suffixes"));
		Assert.assertTrue("List all Study Keywords OK", homePage.goesToCRUDTable("List all Study Keywords"));
		Assert.assertTrue("List all Org Sufixes OK", homePage.goesToCRUDTable("List all Org Sufixes"));
		Assert.assertTrue("List all Instance Person Assocs OK", homePage.goesToCRUDTable("List all Instance Person Assocs"));
		Assert.assertTrue("List all Instance Orgs OK", homePage.goesToCRUDTable("List all Instance Orgs"));
		Assert.assertTrue("List all Person Linkses OK", homePage.goesToCRUDTable("List all Person Linkses"));
		Assert.assertTrue("List all Org Addresses OK", homePage.goesToCRUDTable("List all Org Addresses"));
		Assert.assertTrue("List all Regions OK", homePage.goesToCRUDTable("List all Regions"));
		Assert.assertTrue("List all Selection Variables OK", homePage.goesToCRUDTable("List all Selection Variables"));
		Assert.assertTrue("List all User Messages OK", homePage.goesToCRUDTable("List all User Messages"));
		Assert.assertTrue("List all Addresses OK", homePage.goesToCRUDTable("List all Addresses"));
		Assert.assertTrue("List all Files OK", homePage.goesToCRUDTable("List all Files"));
		Assert.assertTrue("List all Instancepeople OK", homePage.goesToCRUDTable("List all Instancepeople"));
		Assert.assertTrue("List all Values OK", homePage.goesToCRUDTable("List all Values"));
		Assert.assertTrue("List all Emails OK", homePage.goesToCRUDTable("List all Emails"));
		Assert.assertTrue("List all Form Selection Vars OK", homePage.goesToCRUDTable("List all Form Selection Vars"));
		Assert.assertTrue("List all Prefixes OK", homePage.goesToCRUDTable("List all Prefixes"));
		Assert.assertTrue("List all Study Descrs OK", homePage.goesToCRUDTable("List all Study Descrs"));
		Assert.assertTrue("List all Person Addresses OK", homePage.goesToCRUDTable("List all Person Addresses"));
		Assert.assertTrue("List all Keywords OK", homePage.goesToCRUDTable("List all Keywords"));
		Assert.assertTrue("List all User Settings OK", homePage.goesToCRUDTable("List all User Settings"));
		Assert.assertTrue("List all Instance Org Assocs OK", homePage.goesToCRUDTable("List all Instance Org Assocs"));
		Assert.assertTrue("List all Setting Groups OK", homePage.goesToCRUDTable("List all Setting Groups"));
		Assert.assertTrue("List all Variables OK", homePage.goesToCRUDTable("List all Variables"));
		Assert.assertTrue("List all People OK", homePage.goesToCRUDTable("List all People"));
		Assert.assertTrue("List all Forms OK", homePage.goesToCRUDTable("List all Forms"));
		Assert.assertTrue("List all Instance Descrs OK", homePage.goesToCRUDTable("List all Instance Descrs"));
		Assert.assertTrue("List all Citys OK", homePage.goesToCRUDTable("List all Citys"));
		Assert.assertTrue("List all Concepts OK", homePage.goesToCRUDTable("List all Concepts"));
		Assert.assertTrue("List all Unit Analyses OK", homePage.goesToCRUDTable("List all Unit Analyses"));
		Assert.assertTrue("List all Vargroups OK", homePage.goesToCRUDTable("List all Vargroups"));
		Assert.assertTrue("List all Org Relationses OK", homePage.goesToCRUDTable("List all Org Relationses"));
		Assert.assertTrue("List all User Setting Groups OK", homePage.goesToCRUDTable("List all User Setting Groups"));
		Assert.assertTrue("List all Collection Model Types OK", homePage.goesToCRUDTable("List all Collection Model Types"));
		Assert.assertTrue("List all Orgs OK", homePage.goesToCRUDTable("List all Orgs"));
		Assert.assertTrue("List all Langs OK", homePage.goesToCRUDTable("List all Langs"));
		Assert.assertTrue("List all Org Phones OK", homePage.goesToCRUDTable("List all Org Phones"));
		Assert.assertTrue("List all Sampling Procedures OK", homePage.goesToCRUDTable("List all Sampling Procedures"));
		Assert.assertTrue("List all Person Emails OK", homePage.goesToCRUDTable("List all Person Emails"));
		Assert.assertTrue("List all Phones OK", homePage.goesToCRUDTable("List all Phones"));
		Assert.assertTrue("List all Org Internets OK", homePage.goesToCRUDTable("List all Org Internets"));
		Assert.assertTrue("List all Org Emails OK", homePage.goesToCRUDTable("List all Org Emails"));
		Assert.assertTrue("List all Study Person Assocs OK", homePage.goesToCRUDTable("List all Study Person Assocs"));
		Assert.assertTrue("List all Person Internets OK", homePage.goesToCRUDTable("List all Person Internets"));
		Assert.assertTrue("List all Person Phones OK", homePage.goesToCRUDTable("List all Person Phones"));
		Assert.assertTrue("List all News Items OK", homePage.goesToCRUDTable("List all News Items"));
		Assert.assertTrue("List all Cms Snippets OK", homePage.goesToCRUDTable("List all Cms Snippets"));
		Assert.assertTrue("List all Cms Page Types OK", homePage.goesToCRUDTable("List all Cms Page Types"));
		Assert.assertTrue("List all Cms Pages OK", homePage.goesToCRUDTable("List all Cms Pages"));
		Assert.assertTrue("List all Cms Folders OK", homePage.goesToCRUDTable("List all Cms Folders"));
		Assert.assertTrue("List all Cms Layout Groups OK", homePage.goesToCRUDTable("List all Cms Layout Groups"));
		Assert.assertTrue("List all Cms Files OK", homePage.goesToCRUDTable("List all Cms Files"));
		Assert.assertTrue("List all Cms Layouts OK", homePage.goesToCRUDTable("List all Cms Layouts"));
		Assert.assertTrue("List all Cms Page Contents OK", homePage.goesToCRUDTable("List all Cms Page Contents"));
		Assert.assertTrue("List all Cms Snippet Groups OK", homePage.goesToCRUDTable("List all Cms Snippet Groups"));
		Assert.assertTrue("List all Data Source Types OK", homePage.goesToCRUDTable("List all Data Source Types"));
		Assert.assertTrue("List all Series Descrs OK", homePage.goesToCRUDTable("List all Series Descrs"));
		Assert.assertTrue("List all Instance Right Target Groups OK",
				homePage.goesToCRUDTable("List all Instance Right Target Groups"));

		// TODO Cosmin: fix app using this table
		// Assert.assertTrue("List all Series Items OK",
		// homePage.goesToCRUDTable("List all Series Items"));
		Assert.assertTrue("List all Target Groups OK", homePage.goesToCRUDTable("List all Target Groups"));
		Assert.assertTrue("List all Instance Variables OK", homePage.goesToCRUDTable("List all Instance Variables"));
		Assert.assertTrue("List all Instance Right Values OK", homePage.goesToCRUDTable("List all Instance Right Values"));
		Assert.assertTrue("List all Instance Forms OK", homePage.goesToCRUDTable("List all Instance Forms"));
		Assert.assertTrue("List all Instance Rights OK", homePage.goesToCRUDTable("List all Instance Rights"));
		
		Assert.assertTrue("List all Users OK", homePage.goesToCRUDTable("List all Userses"));
		Assert.assertTrue("List all Authorities OK", homePage.goesToCRUDTable("List all Authoritieses"));
		Assert.assertTrue("List all Acl Sids OK", homePage.goesToCRUDTable("List all Acl Sids"));
		Assert.assertTrue("List all Acl Classes OK", homePage.goesToCRUDTable("List all Acl Classes"));
		Assert.assertTrue("List all Acl Object Identitys OK", homePage.goesToCRUDTable("List all Acl Object Identitys"));
		Assert.assertTrue("List all Acl Entrys OK", homePage.goesToCRUDTable("List all Acl Entrys"));
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