package jp.ac.jaist.DataCrawler.obj.userprofile;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.obj.CommonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d on 5/23/2016.
 */
public class User extends CommonObject {
    private String id;
    private String userName = "";
    private String userProfileUrl = "";
    private List<LearningLevel> learningInfo = new ArrayList<>();

    public User() {
    }

    public User(String id, String userName, String userProfileUrl, List<LearningLevel> learningInfo) {
        this.id = id;
        this.userName = userName;
        this.userProfileUrl = userProfileUrl;
        this.learningInfo = learningInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public List<LearningLevel> getLearningInfo() {
        return learningInfo;
    }

    public void setLearningInfo(List<LearningLevel> learningInfo) {
        this.learningInfo = learningInfo;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
