package automation.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.testng.Reporter;

import automation.utils.Logger;
import common.automation.framework.util.WebDriverHelper;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "Features",//path to the features
        format = {"automation.reporter.CustomReporter"/*html:target/features_report.html"*/},//what formatters to use
        tags = {"@run"})//what tags to include(@)/exclude(@~)
public class MusicStoreTestRunner {
	
	@BeforeClass
	public static void tearUp() throws Exception {
		Logger.setupLogger(MusicStoreTestRunner.class.getName());
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		Reporter.log("Closing browser and quit webdriver", true);
		WebDriverHelper.getWebDriver().quit();
	}
}