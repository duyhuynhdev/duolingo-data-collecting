package jp.ac.jaist.DataCrawler.controller.action;

import jp.ac.jaist.DataCrawler.database.Database;
import jp.ac.jaist.DataCrawler.obj.Duolingo;
import jp.ac.jaist.DataCrawler.obj.userprofile.LearningLevel;
import jp.ac.jaist.DataCrawler.obj.userprofile.User;
import jp.ac.jaist.DataCrawler.util.Path;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hpduy17 on 8/9/16.
 */
public class UsersInfoUpdateCrawler {
    private final static String profileLanguagesListXPath = "/html/body/div[3]/main/section[1]/div/div[1]/ul[2]/li";
    private final static String languagesXPath = "./div/div[2]/div";

    public static void main(String... args) throws IOException, InterruptedException {
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
        Path.buildRoot();
        // access homepage
        driver.get(Duolingo.homepage);
        Thread.sleep(2000);
        // login
        Login login = new Login();
        login.runBFS(driver);
        for (User user : Database.getInstance().getUserMap().values()) {
            if (user.getLearningInfo().isEmpty())
                new UsersInfoUpdateCrawler().run(driver, user);
        }

    }

    public void run(RemoteWebDriver driver, User user) {
        driver.get(user.getUserProfileUrl());
        breathe(2000);
        List<WebElement> list = driver.findElements(By.xpath(profileLanguagesListXPath));
        for (WebElement li : list) {
            try {
                List<WebElement> webElements = li.findElements(By.xpath(languagesXPath));
                String lang = webElements.get(0).getText();
                String xp = webElements.get(webElements.size()-1).getText();
                LearningLevel learningLevel = new LearningLevel();
                String[] temp = lang.split("-");
                learningLevel.setLanguage(temp[0].trim());
                learningLevel.setLevel(Integer.parseInt(temp[1].replaceAll("Cấp độ", "").trim()));
                temp = xp.split(":");
                learningLevel.setExp(Long.parseLong(temp[1].replaceAll("KN", "").trim()));
                Database.getInstance().saveData(Database.eTableType.LEARNING_INFO, learningLevel);
                System.out.println(learningLevel.toString());
                user.getLearningInfo().add(learningLevel);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        //update
        Database.getInstance().saveData(Database.eTableType.USER, user);
        System.out.println(user.toString());
    }

    public static void breathe1second() {
        breathe(1000);
    }

    public static void breathe2second() {
        breathe(2000);
    }

    public static void breathe(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
