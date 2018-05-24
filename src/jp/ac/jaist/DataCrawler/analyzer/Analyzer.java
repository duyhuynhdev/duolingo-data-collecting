package jp.ac.jaist.DataCrawler.analyzer;

import jp.ac.jaist.DataCrawler.database.Database;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by hpduy17 on 6/18/16.
 */
public class Analyzer {
    private RemoteWebDriver driver;
    private static final DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();

    public void run() throws Exception {
        setUp();
        CourseAnalyzer analyzer = new CourseAnalyzer();
        analyzer.run(driver);
        Database.getInstance().prettyWriteEngOnlyAnalyzer(analyzer);
        tearDown();
    }

    public void setUp() throws Exception {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("dom.max_script_run_time", 30);
        desiredCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        firefoxProfile.setPreference("browser.startup.homepage", "about:blank");
        firefoxProfile.setPreference("startup.homepage_welcome_url", "about:blank");
        firefoxProfile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
        try {
            driver = new FirefoxDriver(desiredCapabilities);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    public void tearDown() throws Exception {
        try {
            driver.quit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
