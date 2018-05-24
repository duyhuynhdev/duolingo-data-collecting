package jp.ac.jaist.DataCrawler.analyzer;

import jp.ac.jaist.DataCrawler.database.Database;
import jp.ac.jaist.DataCrawler.obj.userprofile.LearningLevel;
import jp.ac.jaist.DataCrawler.obj.userprofile.User;
import jp.ac.jaist.DataCrawler.util.Path;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by hpduy17 on 6/18/16.
 */
public class UserAnalyzer {
    private RemoteWebDriver driver;
    private static final DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();

    public static void main(String... args) throws Exception {
        Path.buildRoot();
        HashMap<String, HashMap<Integer, Integer>> analyzer = new HashMap<>();
        long [] temp = {440,1590,3930,8360,8490};
        long [] nums = {0,0,0,0,0};
        for (LearningLevel learningLevel : Database.getInstance().getLearningLevels()) {
            HashMap<Integer, Integer> levelList = analyzer.get(learningLevel.getLanguage());

            if (levelList == null) {
                levelList = new HashMap<>();
                analyzer.put(learningLevel.getLanguage(), levelList);
            }
            int num = 0;
            if (levelList.containsKey(learningLevel.getLevel())) {
                num = levelList.get(learningLevel.getLevel());
            }
            if(learningLevel.getLanguage().equals("TIẾNG ANH")) {
                if (learningLevel.getExp() < temp[0] && learningLevel.getExp() >= 0) {
                    nums[0]++;
                } else if (learningLevel.getExp() > temp[0] && learningLevel.getExp() <= temp[1]) {
                    nums[1]++;
                } else if (learningLevel.getExp() > temp[1] && learningLevel.getExp() <= temp[2]) {
                    nums[2]++;
                } else if (learningLevel.getExp() > temp[2] && learningLevel.getExp() <= temp[3]) {
                    nums[3]++;
                } else if (learningLevel.getExp() > temp[3] ) {
                    nums[4]++;
                }
                System.out.println(learningLevel.getExp());
            }
            num++;
            levelList.put(learningLevel.getLevel(), num);
        }
//        for (String language : analyzer.keySet()) {
        String language = "TIẾNG ANH";
            HashMap<Integer, Integer> levelList = analyzer.get(language);
            System.out.println(language);
            for (int lvl : levelList.keySet()) {
                System.out.print(lvl + ":" + levelList.get(lvl) + " -- ");
            }
            System.out.println();
//        }
        System.out.println(nums[0]);
        System.out.println(nums[1]);
        System.out.println(nums[2]);
        System.out.println(nums[3]);
        System.out.println(nums[4]);
        int totalUser = 0;
        for(User user : Database.getInstance().getUserMap().values()){
            if(user.getLearningInfo()!= null && user.getLearningInfo().size()> 0 )
                totalUser++;
        }
        int size = Database.getInstance().getUserMap().size();
        System.out.println("TOTAL:"+totalUser+"/"+size+ "(completed "+((float)totalUser/size)*100+"%)");

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
