package jp.ac.jaist.Simulation;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.database.Database;
import jp.ac.jaist.DataCrawler.obj.userprofile.LearningLevel;
import jp.ac.jaist.DataCrawler.util.Path;
import jp.ac.jaist.Simulation.obj.Param;
import jp.ac.jaist.Simulation.obj.Structure;
import jp.ac.jaist.Simulation.obj.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hpduy17 on 9/10/16.
 */
public class Statistic {
    private static final String path = "/duolingo/simulation/eng.txt";
    private static final String pathUser = "/duolingo/simulation/user.txt";

    public static void main(String... args) throws IOException {
        Structure structure = new Structure();
        List<LearningLevel> data = load();
        List<User> users = new ArrayList<>();
        HashMap<Integer,Integer> levelCount = new HashMap<>();
        HashMap<Integer,Integer> levelUserCount = new HashMap<>();
        HashMap<Integer,Integer> passedLessonUserCount = new HashMap<>();
        HashMap<Integer,Integer> increasedLessonUserCount = new HashMap<>();
        int id = 1;
        for (LearningLevel l : data) {
            if(l.getExp()==0)
                continue;
            int count = 0;
            if(levelCount.containsKey(l.getLevel()))
                count = levelCount.get(l.getLevel());
            levelCount.put(l.getLevel(),count+1);
            User user = new User();
            user.id = id;
            user.rawxp = l.getExp();
            structure.getThreshold(user, (int) (l.getExp()/10)*10);
            user.boringThreshold = Math.abs(user.challengingThreshold) / 2;
            users.add(user);
            id++;
//            System.out.println(l.getExp() + "---" + user.toString());
//            recordLine(user.toString(),pathUser);
        }
//        System.out.println("REAL DATA ---------");
//        for(int l : levelCount.keySet())
//            System.out.println("level " + l + ": " +levelCount.get(l));
//        System.out.println("-------------------");
//         Test threshold
//        users = loadUser();
        for(User user : users){
            //old
            int numlesson = structure.learn(user);
            user.xp = numlesson* Param.xpEachLesson;
            int count = 0;
            int lessCount = 0;
            if(levelUserCount.containsKey(user.level()))
                count = levelUserCount.get(user.level());
            levelUserCount.put(user.level(),count+1);

            if(passedLessonUserCount.containsKey(user.passedLessons))
                lessCount = passedLessonUserCount.get(user.passedLessons);
            passedLessonUserCount.put(user.passedLessons,lessCount+1);

            //new
            int numlessonnew = structure.learnNewModel(user);
            user.xpNew = numlessonnew* Param.xpEachLesson;
            user.increaseLesson = user.passedLessonsNew - user.passedLessons;
            int countNew = 0;
            if(increasedLessonUserCount.containsKey(user.increaseLesson))
                countNew = increasedLessonUserCount.get(user.increaseLesson);
            increasedLessonUserCount.put(user.increaseLesson,countNew+1);

        }
        System.out.println("LEVEL DATA ---------");
        for(int l : levelUserCount.keySet())
            System.out.println("level " + l + ": " +levelUserCount.get(l));
        System.out.println("-------------------");

        System.out.println("LESSON DATA ---------");
        for(int l : passedLessonUserCount.keySet())
            System.out.println("lesson " + l + ": " + passedLessonUserCount.get(l));
        System.out.println("-------------------");

        System.out.println("INCREASED LESSON DATA ---------");
        for(int l : increasedLessonUserCount.keySet())
            System.out.println("num increased lesson - " + l + ": " + increasedLessonUserCount.get(l));
        System.out.println("-------------------");
    }



    public static List<LearningLevel> getRealData() {
        try {
            Path.buildRoot();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<LearningLevel> result = new ArrayList<>();
        for (LearningLevel learningLevel : Database.getInstance().getLearningLevels()) {
            if (learningLevel.getLanguage().equals("TIẾNG ANH"))
                result.add(learningLevel);
        }
        return result;
    }

    public static void recordLine(String json, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            new FileOutputStream(file, true).close();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(json);
        writer.newLine();
        writer.flush();
        writer.close();
    }

    private static List<LearningLevel> load() throws FileNotFoundException {
        List<LearningLevel> learningLevels = new ArrayList<>();
        Gson gson = new Gson();
        File file = new File(path);
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    // put to db
                    LearningLevel learningLevel = gson.fromJson(currentLine, LearningLevel.class);
                    learningLevels.add(learningLevel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return learningLevels;
    }
    private static List<User> loadUser() throws FileNotFoundException {
        List<User> users = new ArrayList<>();
        Gson gson = new Gson();
        File file = new File(pathUser);
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    // put to db
                    User learningLevel = gson.fromJson(currentLine, User.class);
                    users.add(learningLevel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
}
