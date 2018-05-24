package jp.ac.jaist.DataCrawler.obj.learning;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.obj.CommonObject;

import java.util.ArrayList;

/**
 * Created by d on 5/23/2016.
 */
public class LanguageCourse extends CommonObject {
    private int id;
    private eLanguage languageCourse;
    private eLanguage languageSpeak;
    private String languageCourseUrl = "";
    private ArrayList<Integer> stageIds = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public eLanguage getLanguageCourse() {
        return languageCourse;
    }

    public void setLanguageCourse(eLanguage languageCourse) {
        this.languageCourse = languageCourse;
    }

    public eLanguage getLanguageSpeak() {
        return languageSpeak;
    }

    public void setLanguageSpeak(eLanguage languageSpeak) {
        this.languageSpeak = languageSpeak;
    }

    public String getLanguageCourseUrl() {
        return languageCourseUrl;
    }

    public void setLanguageCourseUrl(String languageCourseUrl) {
        this.languageCourseUrl = languageCourseUrl;
    }

    public ArrayList<Integer> getStageIds() {
        return stageIds;
    }

    public void setStageIds(ArrayList<Integer> stageIds) {
        this.stageIds = stageIds;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static enum eLanguage{
        English,
        Vietnamese
    }
}
