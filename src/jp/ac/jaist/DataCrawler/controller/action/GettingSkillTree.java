package jp.ac.jaist.DataCrawler.controller.action;

import jp.ac.jaist.DataCrawler.database.Database;
import jp.ac.jaist.DataCrawler.database.IdGenerator;
import jp.ac.jaist.DataCrawler.obj.learning.Skill;
import jp.ac.jaist.DataCrawler.obj.learning.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpduy17 on 5/24/16.
 */
public class GettingSkillTree extends MainAction {
    private final String skillTreeXPath = "/html/body/div[1]/div/div[2]/div/div[2]/div/div/div/div[2]";
    //sub tag in li
    private final String spanTagXPath = "./span";
    private final String aTagXPath = "./a";
    private final String subSpanTagXPath = "./span";
    private final String skillBagNameXPath = "./span[2]";
    //local variable
    private ArrayList<Integer> skillIds = new ArrayList<>();
    private boolean locked = false;
    private int currentStageIdx = 0;
    //global variable

    @Override
    public void runBFS(RemoteWebDriver driver) {
        // get all <li> skills
        List<WebElement> liTags = driver.findElements(By.className("_2GJb6"));
        if (liTags != null) {
            for (WebElement li : liTags) {
                if (!locked)
                    liTagProcessing(li);
            }
            // last stage
            Stage stage = new Stage();
            stage.setId(IdGenerator.getStageId());
            stage.setName("The End");
            stage.setSkillIds(skillIds.subList(currentStageIdx, skillIds.size()));
            database.saveData(Database.eTableType.STAGE, stage);

        }

    }

    @Override
    public void runDFS(RemoteWebDriver driver) {
        List<WebElement> liTags = driver.findElements(By.className("_2GJb6"));
        if (liTags != null) {
            for (WebElement li : liTags) {
                WebElement aTag = li.findElement(By.xpath("./a"));
                if (aTag.getText().contains("Exonérate de")) { // checkpoint - stage
                    System.out.println("CheckPoint");
                    Stage stage = new Stage();
                    stage.setId(IdGenerator.getStageId());
                    stage.setName(li.getText());
                    stage.setSkillIds(skillIds.subList(currentStageIdx, skillIds.size()));
                    database.saveData(Database.eTableType.STAGE, stage);
                    currentStageIdx = skillIds.size();
                } else {// skill
                    if (!locked) {
                        try {
                            Skill skill = new Skill();
                            skill.setId(IdGenerator.getSkillId());
                            skill.setUrl(aTag.getAttribute("href"));
                            skill.setName(aTag.getText());
                            database.saveData(Database.eTableType.SKILL, skill);
                            skillIds.add(skill.getId());
                            System.out.println(skill.toString());
                            driver.get(skill.getUrl());
                            breathe(2000);
                            // get Lesson;
                            GettingLesson gettingLesson = new GettingLesson();
                            gettingLesson.runDFS(driver);
                        } catch (Exception ex) {
                            locked = true;
                        }
                    }
                }
            }
            // last stage
            Stage stage = new Stage();
            stage.setId(IdGenerator.getStageId());
            stage.setName("The End");
            stage.setSkillIds(skillIds.subList(currentStageIdx, skillIds.size()));
            database.saveData(Database.eTableType.STAGE, stage);

        }

    }

    private void liTagProcessing(WebElement liTag) {
        if (!locked) {
            try {
                WebElement aTag = liTag.findElement(By.xpath("./a"));
                if (aTag.getText().contains("Exonérate de")) { // checkpoint - stage
                    System.out.println("CheckPoint");
                    Stage stage = new Stage();
                    stage.setId(IdGenerator.getStageId());
                    stage.setName(liTag.getText());
                    stage.setSkillIds(skillIds.subList(currentStageIdx, skillIds.size()));
                    database.saveData(Database.eTableType.STAGE, stage);
                    currentStageIdx = skillIds.size();
                } else {// skill

                    Skill skill = new Skill();
                    skill.setId(IdGenerator.getSkillId());
                    skill.setUrl(aTag.getAttribute("href"));
                    skill.setName(aTag.getText());
                    database.saveData(Database.eTableType.SKILL, skill);
                    skillIds.add(skill.getId());
                    System.out.println(skill.toString());
                    
                }
            } catch (Exception ex) {
                locked = true;
            }
        }
    }

    private void enterSkillProcessing(WebElement aTag) {
        aTag.click();
        breathe1second();
    }

    public ArrayList<Integer> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(ArrayList<Integer> skillIds) {
        this.skillIds = skillIds;
    }
}
