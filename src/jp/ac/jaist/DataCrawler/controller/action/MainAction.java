package jp.ac.jaist.DataCrawler.controller.action;

import jp.ac.jaist.DataCrawler.database.Database;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by hpduy17 on 5/24/16.
 */
public abstract class MainAction {

    protected Database database = Database.getInstance();

    abstract public void runBFS(RemoteWebDriver driver);

    abstract public void runDFS(RemoteWebDriver driver);

    public void breathe1second() {
        breathe(1000);
    }

    public void breathe(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public RemoteWebDriver createDriver() throws Exception {
        RemoteWebDriver driver;
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("dom.max_script_run_time", 30);
        desiredCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        firefoxProfile.setPreference("browser.startup.homepage", "about:blank");
        firefoxProfile.setPreference("startup.homepage_welcome_url", "about:blank");
        firefoxProfile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
        driver = new FirefoxDriver(desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }
}
