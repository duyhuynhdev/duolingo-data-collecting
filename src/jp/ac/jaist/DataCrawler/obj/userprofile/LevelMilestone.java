package jp.ac.jaist.DataCrawler.obj.userprofile;

import com.google.gson.Gson;

/**
 * Created by d on 5/23/2016.
 */
public class LevelMilestone {
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

