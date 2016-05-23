package jp.ac.jaist.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d on 5/23/2016.
 */
public class Skill {
    private int id;
    private String name = "";
    private String url = "";
    private String testLink;
    private List<Lesson> lessons = new ArrayList<>();

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

    public String getTestLink() {
        return testLink;
    }

    public void setTestLink(String testLink) {
        this.testLink = testLink;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
