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
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by hpduy17 on 8/9/16.
 */
public class UsersInfoCrawler {
    private final static String myInfoUrl = "https://www.duolingo.com/hpduy17";
    private final String followerXPath = "//*[@id=\"followers\"]";
    private final String followingXPath = "//*[@id=\"following\"]";
    private final String followerTabXPath = "/html/body/div[3]/main/section[1]/div/div[2]/div/ul[1]/li[2]/a";
    private final String followingTabXPath = "/html/body/div[3]/main/section[1]/div/div[2]/div/ul[1]/li[1]/a";
    private final String moreXPath = "/html/body/div[3]/main/section[1]/div/div[2]/div/ul[3]/li[8]/a";
    private final String followingBoardXpath = "//*[@id=\"following-modal\"]";
    private final String followingBoardBodyXpath = "//*[@id=\"following-modal\"]/div[2]";
    private final String userListXpath = "//*[@id=\"following-modal\"]/div[2]/ul/li";

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
        UsersInfoCrawler crawler = new UsersInfoCrawler();
//        driver.get(myInfoUrl);
//        breathe(10000);
//        crawler.run(driver);
        List<User> temp = new ArrayList<>(Database.getInstance().getUserMap().values());
        Collections.shuffle(temp);
        for(User u : temp){
            try {
                crawler = new UsersInfoCrawler();
                driver.get(u.getUserProfileUrl());
                breathe(10000);
                crawler.run(driver);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    public void run(RemoteWebDriver driver) {
        int numUser = Integer.parseInt(driver.findElement(By.xpath(followingTabXPath)).getText().split(":")[1].trim());
        if(numUser <= 0)
            return;
        WebElement followingList = driver.findElement(By.xpath(followingXPath));
        List<WebElement> followings = followingList.findElements(By.xpath("./li"));
        if (followings.size() >= 8) {
            WebElement moreBtn = followings.get(7);
            moreBtn.click();
            breathe(1000);
            for (int i = 0; i <= numUser/5; i++) {
                scrollDown(driver);
                breathe(1000);
            }
            List<WebElement> list = driver.findElements(By.xpath(userListXpath));
            for(WebElement li : list){
                createUser(li.findElement(By.xpath("./a[1]")));
            }
        } else {
            for (WebElement l : followings) {
                createUser(l.findElement(By.xpath("./a[1]")));
            }
        }
//
//        WebElement followerList = driver.findElement(By.xpath(followerXPath));
//        List<WebElement> followers = followerList.findElements(By.tagName("li"));
//
//        if (followers.size() >= 8) {
//            WebElement tab = driver.findElement(By.xpath(followerTabXPath));
//            tab.click();
//            int numUser = Integer.parseInt(tab.getText().split(":")[1].trim());
//            WebElement moreBtn = followers.get(7);
//            moreBtn.click();
//            breathe(1000);
//            for (int i = 0; i <= numUser/5; i++) {
//                scrollDown(driver);
//                breathe(2000);
//            }
//            List<WebElement> list = driver.findElements(By.xpath(userListXpath));
//            for(WebElement li : list){
//                createUser(li.findElement(By.xpath("./a[1]")));
//            }
//        } else {
//            for (WebElement l : followers) {
//                createUser(l.findElement(By.xpath("./a[1]")));
//            }
//        }
    }

    public void scrollDown(RemoteWebDriver driver) {
        WebElement followingBoard = driver.findElement(By.xpath(followingBoardBodyXpath));
        followingBoard.sendKeys(Keys.PAGE_DOWN);
        breathe1second();
    }

    public void createUser(WebElement li) {
        String userName = li.getAttribute("href");
        User user = new User();
        user.setUserName(userName.split("/")[3]);
        user.setId(userName.split("/")[3]);
        user.setUserProfileUrl(userName);
        if (!Database.getInstance().getUserMap().containsKey(user.getId())) {
            Database.getInstance().saveData(Database.eTableType.USER, user);
            System.out.println(user.toString());
        }
    }


    public static void breathe1second() {
        breathe(1000);
    }

    public static void breathe2second() {
        breathe(2000);
    }

    public static void  breathe(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
