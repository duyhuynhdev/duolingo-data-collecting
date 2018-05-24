package jp.ac.jaist.DataCrawler.controller.action;

import jp.ac.jaist.DataCrawler.database.Database;
import jp.ac.jaist.DataCrawler.database.IdGenerator;
import jp.ac.jaist.DataCrawler.obj.learning.Lesson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpduy17 on 5/25/16.
 */
public class GettingLesson extends MainAction {
    private final String rowsXPath = "/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div/div/div/div";
    private final String lessonXPath = "./div";
    //sub tag in lesson
    private final String descriptionXPath = "/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div/div/div/div[1]/div/div/p";
    private final String nameXPath = "/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div/div/div/div[1]/div/div/h3";
    private final String aTagXPath = "./div/footer/a";
    // local variable
    private ArrayList<Integer> lessonIds = new ArrayList<>();
    private boolean locked = false;


    @Override
    public void runBFS(RemoteWebDriver driver) {
        // get all <div> row
        List<WebElement> rowDivTags = driver.findElements(By.xpath(rowsXPath));
        if (rowDivTags != null) {
            int level = 1;
            for (WebElement div : rowDivTags) {
                if (!locked) {
                    lessonDivTagProcessing(driver, driver.getCurrentUrl() + "/" + level);
                    level++;
                }

            }
        }
    }

    @Override
    public void runDFS(RemoteWebDriver driver) {
        // get number of lesson in page
        List<WebElement> rowDivTags = driver.findElements(By.xpath(rowsXPath));
        if (rowDivTags != null) {
            for (WebElement div : rowDivTags) {
                // get all <div> lesson in row
                List<WebElement> lessonDivTags = div.findElements(By.xpath(lessonXPath));
                if (lessonDivTags != null) {
                    for (WebElement ldiv : lessonDivTags) {
                        try {
                            WebElement aTag = ldiv.findElement(By.xpath(aTagXPath));
                            Lesson lesson = new Lesson();
                            lesson.setId(IdGenerator.getLessonId());
                            lesson.setUrl(aTag.getAttribute("href"));
                            lesson.setName(ldiv.findElement(By.xpath(nameXPath)).getText());
                            lesson.setDescription(ldiv.findElement(By.xpath(descriptionXPath)).getText());
                            database.saveData(Database.eTableType.LESSON, lesson);
                            lessonIds.add(lesson.getId());
                            System.out.println(lesson.toString());
                            //do question
                            driver.get(lesson.getUrl());
                            GettingQuestion gettingQuestion = new GettingQuestion();
                            gettingQuestion.runBFS(driver);
                        } catch (Exception ex) {
                            locked = true;
                        }
                    }
                }
            }
        }

    }

    public void lessonDivTagProcessing(RemoteWebDriver divTag, String lessonUrl) {
        if (!locked) {
            try {
                Lesson lesson = new Lesson();
                lesson.setId(IdGenerator.getLessonId());
                lesson.setUrl(lessonUrl);
                lesson.setName(divTag.findElement(By.xpath(nameXPath)).getText());
                lesson.setDescription(divTag.findElement(By.xpath(descriptionXPath)).getText());
                database.saveData(Database.eTableType.LESSON, lesson);
                lessonIds.add(lesson.getId());
                System.out.println(lesson.toString());
            } catch (Exception ex) {
                locked = true;
            }
        }
    }


    public ArrayList<Integer> getLessonIds() {
        return lessonIds;
    }
}
