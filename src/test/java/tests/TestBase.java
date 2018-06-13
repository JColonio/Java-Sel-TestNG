package tests;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.aeonbits.owner.ConfigFactory;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class TestBase {

    public String Username = System.getenv("SAUCE_USERNAME");

    public String Accesskey = System.getenv("SAUCE_ACCESS_KEY");

	protected WebDriver Driver;

	protected Environments Environment;

	// ThreadLocal variable which contains the Sauce Job Id.
	private ThreadLocal<String> SessionId = new ThreadLocal<String>();

	//browser, setup, and environment params are stored in TestSuite.xml (TestNG)
	@Parameters({ "browser", "setup", "environment" })
	@BeforeMethod
	public void driverSetup(String browser, String setup, String environment, Method method) throws Exception {
		// Creates WebDriver using "createDriver" method
		createDriver(browser, setup, method.getName());
		// Setup Environment specific variables (URL, Specific names, etc)
		ConfigFactory.setProperty("env", environment); // Set param to choose the env
		Environment = ConfigFactory.create(Environments.class); // property imported!
		// The start of every test (navigate to env, login, and maximize screen)
		Driver.get(Environment.URL());
		Driver.manage().window().maximize();
	}

	protected void createDriver(String browser, String setup, String methodName) throws MalformedURLException {
		ThreadLocal<WebDriver> webdriver = new ThreadLocal<WebDriver>();

		DesiredCapabilities caps = null;
		if (setup.equalsIgnoreCase("sauce")) {
			if (browser.equalsIgnoreCase("Firefox")) {
				caps = DesiredCapabilities.firefox();
				caps.setCapability("platform", "Windows 7");
				caps.setCapability("version", "latest");
			} else if (browser.equalsIgnoreCase("Chrome")) {
				caps = DesiredCapabilities.chrome();
				caps.setCapability("platform", "Windows 7");
				caps.setCapability("version", "latest");
			} else if (browser.equalsIgnoreCase("IE")) {
				caps = DesiredCapabilities.internetExplorer(); // sets the browser
				caps.setCapability("platform", "Windows 7");
				caps.setCapability("version", "11.0");

			}

			// common settings
			caps.setCapability("name", methodName);
			caps.setCapability("screenResolution", "1920x1080");
			caps.setCapability("recordScreenshots", true);
			caps.setCapability("maxDuration", 900);
			caps.setCapability("commandTimeout", 180);
			caps.setCapability("idleTimeout", 90);
			caps.setCapability("seleniumVersion", "3.12.0");

			// Launch remote browser and set it as the current thread
			webdriver.set(new RemoteWebDriver(
					new URL("https://" + Username + ":" + Accesskey + "@ondemand.saucelabs.com:443/wd/hub"), caps));

			// set current sessionId
			String id = ((RemoteWebDriver) webdriver.get()).getSessionId().toString();
			SessionId.set(id);
		} else if (setup.equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver", "driver//geckodriver.exe");
				caps = DesiredCapabilities.firefox();
				caps.setCapability("name", methodName);
				webdriver.set(new FirefoxDriver());
			} else if (browser.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", "driver//chromedriver.exe");
				caps = DesiredCapabilities.chrome();
				caps.setCapability("name", methodName);
				webdriver.set(new ChromeDriver());
			} else if (browser.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", "driver//IEDriverServer.exe");
				caps = DesiredCapabilities.internetExplorer();
				caps.setCapability("name", methodName);
				webdriver.set(new InternetExplorerDriver());
			}
		}

		Driver = webdriver.get();
	}

	// Method that gets invoked after test.
	@Parameters({ "setup" })
	@AfterMethod
	public void tearDown(String setup, ITestResult result) throws Exception {
		// Gets browser logs if available.
		// Updates saucelab with the passed or failed results
		if (setup.equalsIgnoreCase("sauce")) {
			((JavascriptExecutor) Driver)
					.executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
		}
		// Closes the browser
		Driver.quit();
	}

}