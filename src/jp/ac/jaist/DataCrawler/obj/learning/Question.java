package jp.ac.jaist.DataCrawler.obj.learning;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.obj.CommonObject;

/**
 * Created by d on 5/23/2016.
 */
public class Question extends CommonObject {
    private int id;
    private String questionTitle = "";
    private String questionContent = "";
    private eQuestionType type;
    private String answer = "";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public eQuestionType getType() {
        return type;
    }

    public void setType(eQuestionType type) {
        this.type = type;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static enum eQuestionType{
        Challenge_Select,
        Challenge_Translate,
        Challenge_Judge,
        Challenge_Listen,
        Challenge_Name,
        Challenge_Other
    }


}
