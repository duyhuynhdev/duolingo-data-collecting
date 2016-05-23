package jp.ac.jaist.obj.userprofile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d on 5/23/2016.
 */
public class User {
    private int id;
    private String userName = "";
    private String userProfileUrl = "";
    private List<Integer> learningInfoIds = new ArrayList<>();

    public User() {
    }

    public User(int id, String userName, String userProfileUrl, List<Integer> learningInfoIds) {
        this.id = id;
        this.userName = userName;
        this.userProfileUrl = userProfileUrl;
        this.learningInfoIds = learningInfoIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Integer> getLearningInfoIds() {
        return learningInfoIds;
    }

    public void setLearningInfoIds(List<Integer> learningInfoIds) {
        this.learningInfoIds = learningInfoIds;
    }
}
