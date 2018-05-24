package jp.ac.jaist.DataCrawler.obj;

import com.google.gson.Gson;

/**
 * Created by d on 5/23/2016.
 */
public class Duolingo {
    public static final String homepage = "https://www.duolingo.com/";
    public static final String username = "hpduy17";
    public static final String password = "phuongduy1702";


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
