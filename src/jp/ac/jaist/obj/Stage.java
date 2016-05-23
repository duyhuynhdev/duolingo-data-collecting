package jp.ac.jaist.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d on 5/23/2016.
 */
public class Stage {
    private int id;
    private String name = "";
    private List<Skill> skills = new ArrayList<>();

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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
