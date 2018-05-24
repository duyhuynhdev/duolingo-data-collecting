package jp.ac.jaist.DataCrawler.obj.learning;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.obj.CommonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d on 5/23/2016.
 */
public class Stage extends CommonObject {
    private int id;
    private String name = "";
    private List<Integer> skillIds = new ArrayList<>();

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

    public List<Integer> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(List<Integer> skillIds) {
        this.skillIds = skillIds;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
