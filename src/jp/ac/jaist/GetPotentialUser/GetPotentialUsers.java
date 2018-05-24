package jp.ac.jaist.GetPotentialUser;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hpduy17 on 10/29/16.
 */
public class GetPotentialUsers {
    private RemoteWebDriver driver;
    private final DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
    public HashSet<String> userList = new HashSet<>();

    public static void main(String... args) throws Exception {
        new GetPotentialUsers().run();
    }

    public void run() throws Exception {
        setUp();
        /*Main content*/
        login();
        getTopics(1);
        getTopics(0);
        System.out.println("#Users:" + userList.size());
        saveFile();
        /*--END--*/

        tearDown();
    }

    /**
     * INIT CRAWLER FUNC
     */
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

    /**
     * COMMON FUNC
     */

    public void tearDown() throws Exception {
        try {
            driver.quit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void breathe(final int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /****************** *
     * PROCESS FUNC     *
     * ******************/

    /**
     * LOGIN
     */
    public final String homepage = "https://www.duolingo.com/";
    public final String username = "hpduy17";
    public final String password = "phuongduy1702";
    public final String usernameTextBoxXPath = "//*[@id=\"top_login\"]";
    public final String passwordTextBoxXPath = "//*[@id=\"top_password\"]";
    public final String loginButtonXPath = "//*[@id=\"login-button\"]";
    public final String openLoginDialogButtonXPath = "//*[@id=\"sign-in-btn\"]";

    public void login() {
        driver.get(homepage);
        breathe(2);
        WebElement usernameTextBox = driver.findElement(By.xpath(usernameTextBoxXPath));
        WebElement passwordTextBox = driver.findElement(By.xpath(passwordTextBoxXPath));
        WebElement loginButton = driver.findElement(By.xpath(loginButtonXPath));
        WebElement openLoginDialogButton = driver.findElement(By.xpath(openLoginDialogButtonXPath));
        openLoginDialogButton.click();
        breathe(1);
        //input username
        usernameTextBox.clear();
        usernameTextBox.sendKeys(username);
        breathe(1);
        //input password
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
        breathe(1);
        //click login button
        loginButton.click();
        breathe(1);
    }

    /**
     * SCROLL DOWN
     */

    public void scrollDown(WebElement window) {
        window.sendKeys(Keys.PAGE_DOWN);
        breathe(1);
    }

    /**
     * GET TOPIC
     */
    public final String forumURL = "https://www.duolingo.com/topic/subscriptions/";
    public final String mainPath = "/html/body/div[3]/main";

    //type = 1: hot,  0: new
    public void getTopics(int type) {
        String url = (type == 1 ? forumURL + "hot" : forumURL + "new");
        driver.get(url);
        breathe(2);
        WebElement mainElement = driver.findElement(By.xpath(mainPath));
        for (int i = 0; i < 100; i++)
            scrollDown(mainElement);
        //scroll to get more
        List<WebElement> elements = driver.findElements(By.className("comment"));
        List<String> urls = new ArrayList<>();
        for (WebElement element : elements) {
            urls.add(element.getAttribute("href"));
        }
        System.out.println("#Hot Topics:" + urls.size());
        for (String u : urls) {
            try {
                System.out.println(u);
                getUsersInTopic(u);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * GET USERS IN TOPIC
     */

    public void getUsersInTopic(String url) {
        driver.get(url);
        breathe(2);
        List<WebElement> elements = driver.findElements(By.className("username"));
        for (WebElement element : elements) {
            String u = element.getText();
            userList.add(u);
            System.out.println(u);
        }
    }

    public final String filePath = "/Users/hpduy17/Dropbox/Work/SharingWorkspace/data/userlist.txt";

    /**
     * WRITE FILE
     */
    public void saveFile() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
            new FileOutputStream(file, true).close();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        for (String u : userList) {
            writer.write(u);
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }
}
