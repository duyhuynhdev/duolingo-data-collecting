package jp.ac.jaist.DataCrawler.obj.learning;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.obj.CommonObject;

import java.util.ArrayList;

/**
 * Created by d on 5/23/2016.
 */
public class Lesson extends CommonObject {
    private int id;
    private String name = "";
    private String description;
    private String url = "";
    private int numberOfQuestion;
    private ArrayList<Integer> questionIds = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public ArrayList<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(ArrayList<Integer> questionIds) {
        this.questionIds = questionIds;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
