package jp.ac.jaist.DataCrawler.controller.action;

import jp.ac.jaist.DataCrawler.obj.Duolingo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by hpduy17 on 5/24/16.
 */
public class Login extends MainAction {
    private String usernameTextBoxXPath = "//*[@id=\"top_login\"]";
    private String passwordTextBoxXPath = "//*[@id=\"top_password\"]";
    private String loginButtonXPath = "//*[@id=\"login-button\"]";
    private String openLoginDialogButtonXPath = "//*[@id=\"sign-in-btn\"]";

    @Override
    public void runBFS(RemoteWebDriver driver) {
        WebElement usernameTextBox = driver.findElement(By.xpath(usernameTextBoxXPath));
        WebElement passwordTextBox = driver.findElement(By.xpath(passwordTextBoxXPath));
        WebElement loginButton = driver.findElement(By.xpath(loginButtonXPath));
        WebElement openLoginDialogButton = driver.findElement(By.xpath(openLoginDialogButtonXPath));
        openLoginDialogButton.click();
        breathe1second();
        //input username
        usernameTextBox.clear();
        usernameTextBox.sendKeys(Duolingo.username);
        breathe1second();
        //input password
        passwordTextBox.clear();
        passwordTextBox.sendKeys(Duolingo.password);
        breathe1second();
        //click login button
        loginButton.click();
        breathe1second();
    }

    @Override
    public void runDFS(RemoteWebDriver driver) {
        runBFS(driver);
    }
}
