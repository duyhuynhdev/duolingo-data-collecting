package jp.ac.jaist.obj.userprofile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by d on 5/23/2016.
 */
public class LearningInfo {
    private int id;
    private int level;
    private int userId;
    private int languageCourseId;
    private int totalExp;
    private ArrayList<ArrayList<Integer>> earnedSkillMap = new ArrayList<>(); // <skillId> skills in each level
    private ArrayList<Double> errorRates = new ArrayList<>(); // error ratio in each level

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLanguageCourseId() {
        return languageCourseId;
    }

    public void setLanguageCourseId(int languageCourseId) {
        this.languageCourseId = languageCourseId;
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public ArrayList<ArrayList<Integer>> getEarnedSkillMap() {
        return earnedSkillMap;
    }

    public void setEarnedSkillMap(ArrayList<ArrayList<Integer>> earnedSkillMap) {
        this.earnedSkillMap = earnedSkillMap;
    }

    public ArrayList<Double> getErrorRates() {
        return errorRates;
    }

    public void setErrorRates(ArrayList<Double> errorRates) {
        this.errorRates = errorRates;
    }
}
