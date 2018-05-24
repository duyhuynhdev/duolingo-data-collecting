package jp.ac.jaist.DataCrawler.controller;

import jp.ac.jaist.DataCrawler.controller.action.*;
import jp.ac.jaist.DataCrawler.obj.Duolingo;
import jp.ac.jaist.DataCrawler.obj.learning.Lesson;
import jp.ac.jaist.DataCrawler.obj.learning.Skill;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by hpduy17 on 5/24/16.
 */
public class Crawler extends MainAction {
    private RemoteWebDriver driver;
    private static final DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
    boolean setup = false;

    public void run() throws Exception {
        if (!setup)
            setUp();
        runBFS(driver);
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
            setup = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void runBFS(RemoteWebDriver driver) {
        // access homepage
        driver.get(Duolingo.homepage);
        breathe(2000);
        // login
        Login login = new Login();
        login.runBFS(driver);
        while (true) {
            try {
                driver.get(Duolingo.homepage);
                GettingSkillTree gettingSkillTree = new GettingSkillTree();
                gettingSkillTree.runBFS(driver);
                for (int i = 0; i < gettingSkillTree.getSkillIds().size(); i++) {
                    Skill skill = database.getSkillMap().get(gettingSkillTree.getSkillIds().get(i));
                    database.recordData(skill.getName() + "\n");
                    driver.get(skill.getUrl());
                    breathe(2000);
                    // get Lesson;
                    GettingLesson gettingLesson = new GettingLesson();
                    gettingLesson.runBFS(driver);
                    skill.setLessonIds(gettingLesson.getLessonIds());
                    //get question
                    for (Integer j : gettingLesson.getLessonIds()) {
                        Lesson lesson = database.getLessonMap().get(j);
                        database.recordData("+++" + lesson.getName() + ":");
                        driver.get(lesson.getUrl());
                        GettingQuestion gettingQuestion = new GettingQuestion();
                        gettingQuestion.runBFS(driver);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void runDFS(RemoteWebDriver driver) {
        // access homepage
        driver.get(Duolingo.homepage);
        breathe(2000);
        // login
        Login login = new Login();
        login.runBFS(driver);
        GettingSkillTree gettingSkillTree = new GettingSkillTree();
        gettingSkillTree.runDFS(driver);
    }
}
