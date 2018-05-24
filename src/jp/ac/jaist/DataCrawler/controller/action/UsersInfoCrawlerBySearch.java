package jp.ac.jaist.DataCrawler.controller.action;

import jp.ac.jaist.DataCrawler.database.Database;
import jp.ac.jaist.DataCrawler.obj.Duolingo;
import jp.ac.jaist.DataCrawler.obj.userprofile.User;
import jp.ac.jaist.DataCrawler.util.Path;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
public class UsersInfoCrawlerBySearch {
    private final String openSearchBtnXPath = "/html/body/div[3]/main/section[2]/div/div[3]/div/div[4]/ul/li[2]/ul/li[2]/a";
    private final String searchBtnXPath = "/html/body/div[3]/main/section[2]/div/div[3]/div/div[1]/ul/div[1]/div/div[2]/button";
    private final String searchTextBoxXPath = "//*[@id=\"search-learners\"]";
    private final String followingBoardBodyXpath = "//*[@id=\"following-modal\"]/div[2]";
    private final String userListXpath = "//*[@id=\"search-learners-results\"]";
    private final String userNameXpath = "./a[1]";
    private String[] searchKey = {"e","t","a","i","n","o",
            "s","h","r","d","l","u",
            "c","m","f","w","y","g",
            "p","b","v","k","q","j",
            "x","z"};
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
        Thread.sleep(10000);
        // login
        Login login = new Login();
        login.runBFS(driver);
        UsersInfoCrawlerBySearch crawler = new UsersInfoCrawlerBySearch();
        crawler.run(driver);
    }
    public void run(RemoteWebDriver driver) {
        WebElement openSearch = driver.findElement(By.xpath(openSearchBtnXPath));
        openSearch.click();
        breathe2second();
        for(String s:searchKey) {
            WebElement searchBox = driver.findElement(By.xpath(searchTextBoxXPath));
            searchBox.clear();
            searchBox.sendKeys(s);
            WebElement searchBtn = driver.findElement(By.xpath(searchBtnXPath));
            searchBtn.click();
            breathe(5000);
            for(int i = 0 ; i <10; i ++){
                scrollDown(driver);
                breathe(5000);
            }
            WebElement list = driver.findElement(By.xpath(userListXpath));
            List<WebElement> lis = list.findElements(By.xpath("./li"));
            for(WebElement li : lis){
                WebElement a = li.findElement(By.xpath("./a[1]"));
                createUser(a);
            }
        }

    }

    public void scrollDown(RemoteWebDriver driver) {
        WebElement followingBoard = driver.findElement(By.xpath(followingBoardBodyXpath));
//        driver.executeScript("window.scrollTo(0, " + followingBoard.getLocation().getY() + "");
        followingBoard.sendKeys(Keys.PAGE_DOWN);
        breathe1second();
    }

    public void createUser(WebElement a) {
        String userName = a.getAttribute("href");
        User user = new User();
        user.setUserName(userName.substring(userName.indexOf("/"), userName.length()));
        user.setId(userName.substring(userName.indexOf("/"), userName.length()));
        user.setUserProfileUrl(userName);
        if (!Database.getInstance().getUserMap().containsKey(user.getId()))
            Database.getInstance().saveData(Database.eTableType.USER, user);
    }


    public void breathe1second() {
        breathe(1000);
    }

    public void breathe2second() {
        breathe(2000);
    }

    public void breathe(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
