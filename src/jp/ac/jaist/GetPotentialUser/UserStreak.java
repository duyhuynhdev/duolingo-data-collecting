package jp.ac.jaist.GetPotentialUser;

import com.google.gson.Gson;

/**
 * Name: Huynh Phuong Duy
 * Id: 1610161
 * Date 10:32 PM 4/11/17.
 */
public class UserStreak {
    public int numStreak;
    public int numOldStreak;
    public int numSkill;
    public int numOldSkill;
    public boolean changeLang = false;
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
