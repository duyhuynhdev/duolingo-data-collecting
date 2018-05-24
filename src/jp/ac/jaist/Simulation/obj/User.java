package jp.ac.jaist.Simulation.obj;

import com.google.gson.Gson;

/**
 * Created by hpduy17 on 9/8/16.
 */
public class User {
    public int id;
    public double boringThreshold;
    public double challengingThreshold;
    public double ability;
    public double exval;
    public long xp;
    public long rawxp;
    public int passedLessons;
    //new
    public int passedLessonsNew;
    public long xpNew;
    //distance
    public int increaseLesson;

    public User() {
    }

    public User(int id, double boringThreshold, double challengingThreshold, double ability) {
        this.id = id;
        this.boringThreshold = boringThreshold;
        this.challengingThreshold = challengingThreshold;
        this.ability = ability;
        this.exval = 0;
        this.passedLessons = 0;
    }

    public int level() {
        int[] levelRages = {0, 60, 120, 200, 300,
                450, 750, 1125, 1650, 2250,
                3000, 3900, 4900, 6000, 7500,
                9000, 10500, 12000, 13500, 15000,
                17000, 19000, 22500, 26000, 30000};
        for (int i = 0; i < 24; i++) {
            if (this.xp >= levelRages[i] && this.xp < levelRages[i + 1])
                return i + 1;
        }
        return 25;
    }

    public void update(double value) {
        this.exval += value;
        this.xp += Param.xpEachLesson;
    }

    public boolean isPassThreshold() {
        return  exval <= challengingThreshold;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
