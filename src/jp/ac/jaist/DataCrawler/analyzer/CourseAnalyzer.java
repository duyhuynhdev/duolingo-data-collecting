package jp.ac.jaist.DataCrawler.analyzer;

import jp.ac.jaist.DataCrawler.analyzer.Object.Course;
import jp.ac.jaist.DataCrawler.analyzer.Object.CourseDetail;
import jp.ac.jaist.DataCrawler.obj.Duolingo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpduy17 on 6/18/16.
 */
public class CourseAnalyzer {
    public String courseUrl = "https://www.duolingo.com/courses/all";
    //courseList
    private final String listCoursesXPath = "//*[@id=\"app\"]/main/section/div[2]/div";
    private final String courseLinkXPath = "./a";
    private final String numberOfLearnerXPath = "./a/div[1]/div[5]";
    private final String courseTitleXPath = "./a/meta[1]";
    private final String changeCourseBtnXPath = "/html/body/div[3]/div/div/div[1]/section/div[2]/div[1]/div/div/button";
    private final String changeCourseBtnXPathNext = "/html/body/div[3]/main/section/div[1]/div[3]/div[2]/button";

    //course
    private final String basicSkillUrlXPath = "/html/body/div[3]/main/section[1]/div[1]/div/div[1]/div[2]/ul/li[1]/a";
    private final String skillTreeXPath = "/html/body/div[3]/main/section[1]/div[1]/ul/li";
    private final String spanTagXPath = "./span";
    private final String numberOfLessonXPath = "./span/span[2]";

    //Basic Skill
    private final String rowsXPath = "/html/body/div[3]/main/section[2]/div[1]/div";
    private final String lessonXPath = "./div";
    private boolean f = false;
    //local var
    public List<Course> courseList = new ArrayList<>();

    public void run(RemoteWebDriver driver) {
        driver.get(courseUrl);
        breathe(2);
        courseListGetter(driver);
        for (Course course : courseList) {
            System.out.println(course.courseTitle + " " + course.numberOfLeaner + " " + course.link);
            accessCourse(driver, course.link);
            getDetail(course.detail, driver);
            //call RF
            int idx = 0;
            for (int numSkill : course.detail.totalSkillInClass) {
                int totalLessonInClass = 0;
                for (int i = 0; i < numSkill; i++) {
                    totalLessonInClass += course.detail.totalLessonInSkill.get(idx);
                    idx++;
                }
                course.detail.totalLessonInClass.add(totalLessonInClass);
                course.detail.RFBySkillAndLesson.add((Math.sqrt((double) numSkill) / totalLessonInClass));
                System.out.println("TotalLessonInClass:" + course.detail.totalLessonInClass);
                System.out.println("RF:" + course.detail.RFBySkillAndLesson);
            }
        }
    }

    public void courseListGetter(RemoteWebDriver driver) {
        List<WebElement> divs = driver.findElements(By.xpath(listCoursesXPath));
        if (divs != null) {
            for (WebElement div : divs) {
                Course course = new Course();
                course.courseTitle = div.findElement(By.xpath(courseTitleXPath)).getAttribute("content");
                course.link = div.findElement(By.xpath(courseLinkXPath)).getAttribute("href");
                course.numberOfLeaner = div.findElement(By.xpath(numberOfLearnerXPath)).getText();
                courseList.add(course);
            }
        }
    }

    public void accessCourse(RemoteWebDriver driver, String url) {
        driver.get(url);
        breathe(2);
        WebElement btn;
        if (!f) {
            btn = driver.findElement(By.xpath(changeCourseBtnXPath));
            f = true;
        } else {
            btn = driver.findElement(By.xpath(changeCourseBtnXPathNext));
        }
        btn.click();
        breathe(2);
        driver.get(Duolingo.homepage);
        breathe(5);
    }

    public void getDetail(CourseDetail detail, RemoteWebDriver driver) {
        String basicLink = driver.findElement(By.xpath(basicSkillUrlXPath)).getAttribute("href");
        List<WebElement> liTags = driver.findElements(By.xpath(skillTreeXPath));
        if (liTags != null) {
            for (int i = 1; i < liTags.size(); i++) {
                WebElement li = liTags.get(i);
                liTagProcessing(li, detail);
            }
            detail.totalClass++;
            System.out.println("Class found");
        }
        int basicLessons = getLessonInBasic(driver, basicLink);
        detail.totalLesson += basicLessons;
        detail.totalSkill++;
        int oldVal = detail.totalSkillInClass.get(0);
        detail.totalSkillInClass.set(0, oldVal + 1);
        detail.totalLessonInSkill.add(0, basicLessons);
        System.out.println("Skill found :" + detail.totalLessonInSkill.get(0));

    }

    private void liTagProcessing(WebElement liTag, CourseDetail detail) {
        String className = liTag.getAttribute("class");
        if (className.contains("shortcut")) { // checkpoint - stage
            System.out.println("Class found");
            detail.totalClass++;
        } else {// skill
            try {
                List<WebElement> spans = liTag.findElements(By.xpath(spanTagXPath));
                for (int i = 0; i < spans.size(); i++) {
                    WebElement s = spans.get(i);
                    WebElement numberOfLessonSpan = s.findElement(By.xpath(numberOfLessonXPath));
                    System.out.print(numberOfLessonSpan.getText() + " ");
                    int lesson = 0;
                    try {
                        String temp[] = numberOfLessonSpan.getText().split("/");
                        lesson = Integer.parseInt(temp[temp.length - 1].trim());
                    } catch (Exception ex) {
                        detail.isFalse = true;
                        ex.printStackTrace();
                    }

                    // num lesson
                    detail.totalLesson += lesson;
                    detail.totalLessonInSkill.add(lesson);
                    System.out.println("Skill found :" + detail.totalLessonInSkill.get(detail.totalSkill));
                    // num skill
                    detail.totalSkill++;
                    if (detail.totalSkillInClass.size() <= detail.totalClass)
                        detail.totalSkillInClass.add(1);
                    else {
                        int oldVal = detail.totalSkillInClass.get(detail.totalClass);
                        detail.totalSkillInClass.set(detail.totalClass, oldVal + 1);
                    }
                    System.out.println("#Class :" + detail.totalClass);
                    System.out.println("Skill in class :" + detail.totalSkillInClass.get(detail.totalClass));

                }
            } catch (Exception ex) {
                detail.isFalse = true;
                ex.printStackTrace();
            }
        }
    }

    /**
     * Lesson getter in basic skill
     */
    public int getLessonInBasic(RemoteWebDriver driver, String url) {
        driver.get(url);
        breathe(2);
        int lesson = 0;
        List<WebElement> rowDivTags = driver.findElements(By.xpath(rowsXPath));
        if (rowDivTags != null) {
            for (WebElement div : rowDivTags) {
                // get all <div> lesson in row
                lesson = div.findElements(By.xpath(lessonXPath)).size();
            }
        }
        return lesson;
    }


    public void breathe(final int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
