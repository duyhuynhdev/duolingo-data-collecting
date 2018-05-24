package jp.ac.jaist.DataCrawler.analyzer.Object;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.obj.CommonObject;

/**
 * Created by hpduy17 on 6/18/16.
 */
public class Course extends CommonObject{
    public String courseTitle = "";
    public String link = "";
    public String numberOfLeaner = "";
    public CourseDetail detail = new CourseDetail();
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
