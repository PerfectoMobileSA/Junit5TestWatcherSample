package perfecto;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.test.TestContext;
import org.junit.jupiter.api.*;
import io.appium.java_client.AppiumDriver;

@ExtendWith(PerfectoWatcher.class)
public class TestBase {
	public static RemoteWebDriver driver;
	public static ReportiumClient reportiumClient;

	@BeforeEach
	public void setUp() throws Exception {
		// Replace <<cloud name>> with your perfecto cloud name (e.g. demo) or pass it as maven properties: -DcloudName=<<cloud name>>
		String cloudName = "<<cloud name>>";
		// Replace <<security token>> with your perfecto security token or pass it as  maven properties: -DsecurityToken=<<SECURITY TOKEN>> More info:
		// https://developers.perfectomobile.com/display/PD/Generate+security+tokens
		String securityToken = "<<security token>>";

		
		// A sample perfecto connect appium script to connect with a perfecto android
		// device and perform addition validation in calculator app.
		String browserName = "mobileOS";
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		capabilities.setCapability("securityToken", Utils.fetchSecurityToken(securityToken));
		capabilities.setCapability("model", "Galaxy S.*");
		capabilities.setCapability("enableAppiumBehavior", true);
		capabilities.setCapability("openDeviceTimeout", 2);
		capabilities.setCapability("appPackage", "com.android.settings");
		capabilities.setCapability("appActivity", "com.android.settings.Settings");
		try {
			driver = (RemoteWebDriver) (new AppiumDriver<>(new URL("https://" + Utils.fetchCloudName(cloudName)
					+ ".perfectomobile.com/nexperience/perfectomobile/wd/hub"), capabilities));
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} catch (SessionNotCreatedException e) {
			throw new RuntimeException("Driver not created with capabilities: " + capabilities.toString());
		}
		reportiumClient = Utils.setReportiumClient(driver, reportiumClient); // Creates reportiumClient
	}
	
	@Test
	void settingsTest() {
		reportiumClient.testStart("Junit Watcher Settings Test", new TestContext("tag2", "tag3")); // Starts the reportium test

		reportiumClient.stepStart("Verify Settings App is loaded"); // Starts a reportium step
		driver.findElement(By.xpath(
				"//*[contains(@resource-id,':id/collpasing_app_bar_extended_title') or contains(@resource-id,'settings:id/search')] | //*[contains(@text,'Search')] | //*[@content-desc='Search']"))
				.isDisplayed();
		reportiumClient.stepEnd(); // Stops a reportium step

		reportiumClient.stepStart("Verify Data usage validation");
		driver.findElement(By.xpath("//*[contains(@text,'Data usage')]")).click();
		WebElement data = driver
				.findElement(By.xpath("//*[contains(@resource-id, 'action_bar')]//*[@text='Data usage']"));
		Utils.assertText(data, reportiumClient, "Data usage");
		reportiumClient.stepEnd();

	}
	
	@Test
	@Disabled
	void failTest() {
		// Fail test wantedly ( currently disabled ) 
		reportiumClient.testStart("Junit Watcher Fail Settings Test", new TestContext("fail", "tag3")); 
		reportiumClient.stepStart("Verify Settings App is loaded"); 
		driver.findElement(By.xpath(
				"//*[contains(@resource-id,':id/1111search')]"))
				.isDisplayed();
		reportiumClient.stepEnd(); 
	}
	
	public RemoteWebDriver getDriver() {
        return driver;
    }
	
	public ReportiumClient getReportiumClient() {
        return reportiumClient;
    }

}
