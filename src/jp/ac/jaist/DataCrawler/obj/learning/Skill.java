package jp.ac.jaist.DataCrawler.obj.learning;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.obj.CommonObject;

import java.util.ArrayList;

/**
 * Created by d on 5/23/2016.
 */
public class Skill extends CommonObject {
    private int id;
    private String name = "";
    private String url = "";
    private int numberOfLesson;
    private ArrayList<Integer> lessonIds = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Integer> getLessonIds() {
        return lessonIds;
    }

    public void setLessonIds(ArrayList<Integer> lessonIds) {
        this.lessonIds = lessonIds;
    }

    public int getNumberOfLesson() {
        return numberOfLesson;
    }

    public void setNumberOfLesson(int numberOfLesson) {
        this.numberOfLesson = numberOfLesson;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
