package jp.ac.jaist.obj;

import java.util.ArrayList;

/**
 * Created by d on 5/23/2016.
 */
public class LanguageCourse {
    private int id;
    private int languageCourseId;
    private int languageSpeakId;
    private String languageCourseUrl = "";
    private ArrayList<Stage> stages = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLanguageCourseId() {
        return languageCourseId;
    }

    public void setLanguageCourseId(int languageCourseId) {
        this.languageCourseId = languageCourseId;
    }

    public int getLanguageSpeakId() {
        return languageSpeakId;
    }

    public void setLanguageSpeakId(int languageSpeakId) {
        this.languageSpeakId = languageSpeakId;
    }

    public String getLanguageCourseUrl() {
        return languageCourseUrl;
    }

    public void setLanguageCourseUrl(String languageCourseUrl) {
        this.languageCourseUrl = languageCourseUrl;
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public void setStages(ArrayList<Stage> stages) {
        this.stages = stages;
    }
}
