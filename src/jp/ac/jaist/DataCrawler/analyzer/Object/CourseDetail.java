package jp.ac.jaist.DataCrawler.analyzer.Object;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.obj.CommonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpduy17 on 6/18/16.
 */
public class CourseDetail extends CommonObject {
    public int totalClass = 0;
    public int totalSkill = 0;
    public int totalLesson = 0;
    public List<Integer> totalLessonInSkill =  new ArrayList<>();
    public List<Integer> totalLessonInClass = new ArrayList<>();
    public List<Integer> totalSkillInClass = new ArrayList<>();
//    public List<Integer> totalQuestionInLesson = new ArrayList<>();
//    public List<Integer> totalQuestionInSkill = new ArrayList<>();
//    public List<Integer> totalQuestionInClass = new ArrayList<>();
    public List<Double> RFBySkillAndLesson = new ArrayList<>();
    public boolean isFalse = false;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
