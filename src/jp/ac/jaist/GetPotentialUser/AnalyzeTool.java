package jp.ac.jaist.GetPotentialUser;

import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hpduy17 on 10/30/16.
 */
public class AnalyzeTool {
    public static HashMap<String, JSONObject> streakdata = new HashMap<>();
    public static HashMap<String, JSONObject> old_streakdata = new HashMap<>();
    public static HashMap<String, Integer> knownskill = new HashMap<>();
    public static HashMap<String, Integer> old_knownskill = new HashMap<>();
    public static List<UserStreak> userStreaks = new ArrayList<>();

    public static void main(String... args) throws FileNotFoundException {
        AnalyzeTool a = new AnalyzeTool();
        a.load();
        a.createUserProfile();
        System.out.println();
        System.out.println();
        a.comparedAnalysis();
//        System.out.println();
//        System.out.println();
////        for(UserStreak u : userStreaks){
////            System.out.println(u.toString());
////        }
////        System.out.println("total:"+userStreaks.size());
//        a.analyzing(streakdata, knownskill);
//        System.out.println();
//        System.out.println();
//        a.analyzing(old_streakdata, old_knownskill);
//        System.out.println();
//        System.out.println();
//        a.streakDataComparing(old_streakdata, old_knownskill);
    }

    public void streakDataComparing(HashMap<String, JSONObject> streakdata, HashMap<String, Integer> knownskill) {
        int[] numskillmilestone = new int[6];
        int[] countskillmilestone = new int[6];
        int[] numskillmilestoneS = new int[6];
        int[] countskillmilestoneS = new int[6];
        HashMap<Integer, Integer> inactive = new HashMap<>();
        int numskillstreak = 0;
        int countskillstreak = 0;
        int numskills = 0;
        int countskill = 0;
        for (String key : streakdata.keySet()) {
            if (knownskill.containsKey(key)) {
                JSONObject data = streakdata.get(key);
                int streak = data.getInt("site_streak");
                int numskill = knownskill.get(key);
                int idx;
                if (numskill < 10) {
                    idx = 1;
                } else if (numskill < 22) {
                    idx = 2;
                } else if (numskill < 37) {
                    idx = 3;
                } else if (numskill < 51) {
                    idx = 4;
                } else if (numskill < 59) {
                    idx = 5;
                } else {
                    idx = 6;
//                    continue;
                }
                if (streak > 0) {
                    numskillstreak += numskill;
                    countskillstreak++;
                    numskillmilestoneS[idx - 1] += numskill;
                    countskillmilestoneS[idx - 1]++;
                } else {
                    numskillmilestone[idx - 1] += numskill;
                    countskillmilestone[idx - 1]++;
                    numskills += numskill;
                    countskill++;
                }
            }


        }

        System.out.println("avg skill streak " + ((float) numskillstreak / countskillstreak));
        System.out.println("avg skill non streak " + ((float) numskills / countskill));

        for (int i = 0; i < 6; i++) {
            System.out.println("Milestone " + (i + 1) + ":");
            System.out.println("\tavg skill streak " + ((float) numskillmilestoneS[i] / countskillmilestoneS[i]));
            System.out.println("\tavg skill non streak " + ((float) numskillmilestone[i] / countskillmilestone[i]));
        }

    }

    public void analyzing(HashMap<String, JSONObject> streakdata, HashMap<String, Integer> knownskill) {
        HashMap<Integer, List<Integer>> temp = new HashMap<>();
        HashMap<Integer, Integer> inactive = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            temp.put(i, new ArrayList<Integer>());
            inactive.put(i, 0);
        }
        for (String key : streakdata.keySet()) {
            if (knownskill.containsKey(key)) {
                JSONObject data = streakdata.get(key);
                int streak = data.getInt("site_streak");
                int numskill = knownskill.get(key);
                int idx;
                if (numskill < 10) {
                    idx = 1;
                } else if (numskill < 22) {
                    idx = 2;
                } else if (numskill < 36) {
                    idx = 3;
                } else if (numskill < 49) {
                    idx = 4;
                } else if (numskill < 64) {
                    idx = 5;
                } else {
                    idx = 6;
//                    continue;
                }
                if (streak > 0) {
                    if (idx == 6) {
                        idx = 5;
                        streak = streak / 2;
                    }
                    List<Integer> avg = temp.get(idx);
                    avg.add(streak);
                } else {
                    if (idx == 6)
                        continue;
                    inactive.put(idx, inactive.get(idx) + 1);
                }
            }

        }

        //show result
        for (int key : temp.keySet()) {
            double mean = getMean(temp.get(key));
            System.out.println("milestone " + key + " - avg streak: " + mean + " - dont keep progress number: " + inactive.get(key));
        }

    }

    public double getMean(List<Integer> set) {
        System.out.println("size:" + set.size());
        int sum = 0;
        for (int i : set)
            sum += i;
        return (double) sum / set.size();
    }

    public void load() throws FileNotFoundException {
        String filePath1 = "/Users/hpduy17/Dropbox/Work/SharingWorkspace/data/streak_info.txt";
        String filePathOld1 = "/Users/hpduy17/Dropbox/Work/SharingWorkspace/data/30_Oct/streak_info.txt";
        String filePath2 = "/Users/hpduy17/Dropbox/Work/SharingWorkspace/data/known_skill.txt";
        String filePathOld2 = "/Users/hpduy17/Dropbox/Work/SharingWorkspace/data/30_Oct/known_skill.txt";

        File file = new File(filePath1);
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    JSONObject jsonObject = new JSONObject(currentLine);
                    streakdata.put(jsonObject.getString("key").replaceAll("\\n", ""), jsonObject.getJSONObject("data"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        file = new File(filePathOld1);
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    JSONObject jsonObject = new JSONObject(currentLine);
                    old_streakdata.put(jsonObject.getString("key").replaceAll("\\n", ""), jsonObject.getJSONObject("data"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        file = new File(filePath2);
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(currentLine);
                        knownskill.put(jsonObject.getString("key").replaceAll("\\n", ""), jsonObject.getJSONArray("data").length());
                    } catch (Exception ex) {
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        file = new File(filePathOld2);
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(currentLine);
                        old_knownskill.put(jsonObject.getString("key").replaceAll("\\n", ""), jsonObject.getJSONArray("data").length());
                    } catch (Exception ex) {
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("streak_new_ size:" + streakdata.size());
        System.out.println("known_skill_new_ size:" + knownskill.size());

        System.out.println("streak_old_ size:" + old_streakdata.size());
        System.out.println("known_skill_old_ size:" + old_knownskill.size());

    }

    public void createUserProfile() {
        // for each user in old
        for (String key : old_streakdata.keySet()) {
            UserStreak user = new UserStreak();
            if (old_knownskill.containsKey(key)) {
                JSONObject data = old_streakdata.get(key);
                user.numOldStreak = data.getInt("site_streak");
                user.numOldSkill = old_knownskill.get(key);
            } else {
                continue;
            }
            if (streakdata.containsKey(key)) {
                if (knownskill.containsKey(key)) {
                    JSONObject data = streakdata.get(key);
                    user.numStreak = data.getInt("site_streak");
                    user.numSkill = knownskill.get(key);
                }
            } else {
                user.changeLang = true;
            }
            userStreaks.add(user);
        }
    }

    public void comparedAnalysis() {
        int totalSkillGottenByStreaking = 0;
        int countSkillGottenByStreaking = 0;
        int totalSkillGottenWithoutStreaking = 0;
        int countSkillGottenWithoutStreaking = 0;
        int numberOfActiveUsers = 0;
        int numberOfInactiveUsers = 0;
        int numberOfFinishedUsers = 0;
        int numberOfActiveUsersWithStreak = 0;

        int numberOfUserFinishByStreak = 0;

        int  numberUserHasOldStreak= 0;
        int  numberUserHasOldStreakGU = 0;
        int  numberUser = 0;
        int  numberUserGU = 0;

        for (UserStreak u : userStreaks) {
            if (!u.changeLang) {
                if (u.numStreak > 0 || u.numSkill > u.numOldSkill) {
                    numberOfActiveUsers++;
                    if (u.numStreak > 0) {
                        if (u.numSkill > u.numOldSkill) {
                            totalSkillGottenByStreaking += u.numSkill - u.numOldSkill;
                            countSkillGottenByStreaking++;
                        }
                        numberOfActiveUsersWithStreak++;
                    } else {
                        if (u.numSkill > u.numOldSkill) {
                            totalSkillGottenWithoutStreaking += u.numSkill - u.numOldSkill;
                            countSkillGottenWithoutStreaking++;
                        }
                    }
                    if (u.numSkill >= 64 ) {
                        numberOfFinishedUsers++;
                        if (u.numOldStreak > 0 || u.numStreak > 0)
                            numberOfUserFinishByStreak++;
                    }
                } else if (u.numStreak == 0) {
                    if (u.numSkill >= 64) {
                        numberOfFinishedUsers++;
                        if (u.numOldStreak > 0)
                            numberOfUserFinishByStreak++;
                    }
                    else {
                        numberOfInactiveUsers++;
                    }
                }

                if(u.numSkill< 64) {
                    if (u.numOldStreak > 0) {
                        numberUserHasOldStreak++;
                        if (u.numOldSkill == u.numSkill)
                            numberUserHasOldStreakGU++;
                    } else {
                        numberUser++;
                        if (u.numOldSkill == u.numSkill)
                            numberUserGU++;
                    }
                }
            }
        }
    System.out.println("avg skill gotten of " + countSkillGottenByStreaking + " users by streaking: " + ((double) totalSkillGottenByStreaking / countSkillGottenByStreaking));
       System.out.println("avg skill gotten of " + countSkillGottenWithoutStreaking + " users without streaking: " + ((double) totalSkillGottenWithoutStreaking / countSkillGottenWithoutStreaking));
//        System.out.println("avg old streak of " + countStreakKeepStreaking + " users keep streaking: " + ((double) totalStreakKeepStreaking / countStreakKeepStreaking));
//        System.out.println("avg old streak of " + countStreakGiveUp + " users give up streaking: " + ((double) totalStreakGiveUp / countStreakGiveUp));
        System.out.println("number of active users:" + numberOfActiveUsers);
        System.out.println("number of inactive users:" + numberOfInactiveUsers);
        System.out.println("number of finished users:" + numberOfFinishedUsers);
        System.out.println("number of finished users by streak:" + numberOfUserFinishByStreak);
        System.out.println("number of active users with streak:" + numberOfActiveUsersWithStreak);

        System.out.println("number of users with streak:" + numberUserHasOldStreak);
        System.out.println("number of users with streak give up:" + numberUserHasOldStreakGU);
        System.out.println("number of users without streak:" + numberUser);
        System.out.println("number of users without streak give up:" + numberUserGU);

    }
}
