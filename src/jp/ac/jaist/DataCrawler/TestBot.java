package jp.ac.jaist.DataCrawler;

import jp.ac.jaist.DataCrawler.controller.action.Login;
import jp.ac.jaist.DataCrawler.obj.Duolingo;
import jp.ac.jaist.DataCrawler.util.Path;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hpduy17 on 5/25/16.
 */
public class TestBot {
    public static void main(String... agrs) throws IOException, InterruptedException {
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
        //run
//        for(int i = 1; i <= 2 ; i++) {
        String url = "https://www.duolingo.com/";
        driver.get(url);
        Thread.sleep(10000);
        List<WebElement> elements = driver.findElements(By.cssSelector("*[data-test=\"skill-tree\"]"));
        for (WebElement element : elements) {
            System.out.println(element.getAttribute("data-test"));
            System.out.println(element.getText());
        }
        driver.close();
//        }
//       List<Integer> a = new ArrayList<>();
//        for(int i = 0 ; i <10; i ++){
//            a.set(i,i+1);
//        }
//        System.out.print("done");
//        Path.buildRoot();
//        HashMap<Integer,List<Double>> Graph = new HashMap<>();
//        List<Course> courses = Database.getInstance().loadAnalyzer();
//        for(Course course : courses){
//            for(int i = 0 ; i < course.detail.totalClass; i ++){
//                List<Double> GRList = Graph.get(course.detail.totalSkillInClass.get(i));
//                if(GRList ==  null){
//                    GRList = new ArrayList<>();
//                    Graph.put(course.detail.totalSkillInClass.get(i),GRList);
//                }
//                GRList.add(course.detail.RFBySkillAndLesson.get(i));
//            }
//
//        }
//        for(int numSkill : Graph.keySet()){
//            System.out.print(numSkill);
//            for(double gf: Graph.get(numSkill)) {
//                System.out.print("," + gf);
//            }
//            System.out.println("");
//        }
//        Database.getInstance().prettyWriteTotal(courses);

    }

    public static List<String> checkMultipleAnswer(String answer) {
        List<String> answers = new ArrayList<>();
        try {

            Pattern myPattern = Pattern.compile(".*?\\.");
            Matcher m = myPattern.matcher(answer);
            while (m.find()) {
                answers.add(m.group(0));
            }
            if (answers.isEmpty()) {
                answers.add(answer);
            }
        } catch (Exception ex) {
            answers.add(answer);
        }
        return answers;
    }


}
