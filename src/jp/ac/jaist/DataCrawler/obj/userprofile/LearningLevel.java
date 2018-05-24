package jp.ac.jaist.DataCrawler.obj.userprofile;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.obj.CommonObject;

/**
 * Created by hpduy17 on 8/9/16.
 */
public class LearningLevel extends CommonObject {
    private String language = "";
    private int level = 0;
    private long exp = 0;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
