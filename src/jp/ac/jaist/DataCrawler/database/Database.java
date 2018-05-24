package jp.ac.jaist.DataCrawler.database;

import com.google.gson.Gson;
import jp.ac.jaist.DataCrawler.analyzer.CourseAnalyzer;
import jp.ac.jaist.DataCrawler.analyzer.Object.Course;
import jp.ac.jaist.DataCrawler.analyzer.Object.CourseDetail;
import jp.ac.jaist.DataCrawler.controller.action.GettingQuestion;
import jp.ac.jaist.DataCrawler.obj.CommonObject;
import jp.ac.jaist.DataCrawler.obj.learning.*;
import jp.ac.jaist.DataCrawler.obj.userprofile.LearningLevel;
import jp.ac.jaist.DataCrawler.obj.userprofile.User;
import jp.ac.jaist.DataCrawler.util.Path;

import java.io.*;
import java.util.*;

/**
 * Created by hpduy17 on 5/24/16.
 */
public class Database {
    private static Database database;

    private Database() {
    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
            database.loadDatabase();
        }
        return database;
    }

    //TABLE
    private Map<Integer, LanguageCourse> languageCourseMap = new HashMap<>();
    private Map<Integer, Stage> stageMap = new HashMap<>();
    private Map<Integer, Skill> skillMap = new HashMap<>();
    private Map<Integer, Lesson> lessonMap = new HashMap<>();
    private Map<Integer, Question> questionMap = new HashMap<>();
    private Map<String, User> userMap = new HashMap<>();
    private HashSet< LearningLevel> learningLevels = new HashSet<>();


    //FUNCTIONS
    public void saveData(eTableType tableType, CommonObject object) {
        try {
            switch (tableType) {
                case LANGUAGE_COURSE:
                    LanguageCourse languageCourse = (LanguageCourse) object;
                    recordLine(languageCourse.toString(), Path.getLanguageFilePath());
                    languageCourseMap.put(languageCourse.getId(), languageCourse);
                    break;
                case LESSON:
                    Lesson lesson = (Lesson) object;
                    recordLine(lesson.toString(), Path.getLessonFilePath());
                    lessonMap.put(lesson.getId(), lesson);
                    break;
                case QUESTION:
                    Question question = (Question) object;
                    recordLine(question.toString(), Path.getQuestionFilePath());
                    questionMap.put(question.getId(), question);
                    break;
                case SKILL:
                    Skill skill = (Skill) object;
                    recordLine(skill.toString(), Path.getSkillFilePath());
                    skillMap.put(skill.getId(), skill);
                    break;
                case STAGE:
                    Stage stage = (Stage) object;
                    recordLine(stage.toString(), Path.getStageFilePath());
                    stageMap.put(stage.getId(), stage);
                    break;
                case USER:
                    User user = (User) object;
                    recordLine(user.toString(), Path.getUserFilePath());
                    userMap.put(user.getId(), user);
                    break;
                case LEARNING_INFO:
                    LearningLevel learningLevel = (LearningLevel) object;
                    recordLine(learningLevel.toString(), Path.getLearningInfoFilePath());
                    learningLevels.add(learningLevel);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void prettyWriteAnalyzer(CourseAnalyzer analyzer) {
        try {
            recordLine("Total: " + analyzer.courseList.size() + " language courses", Path.getAnalyzerPath());
            for (Course course : analyzer.courseList) {

                CourseDetail detail = course.detail;
                recordLine("Language Course Title: " + course.courseTitle, Path.getAnalyzerPath());
                recordLine("Number Of Learners: " + course.numberOfLeaner, Path.getAnalyzerPath());
                recordLine("Course Link: " + course.link, Path.getAnalyzerPath());
                if (!detail.isFalse) {
                    recordLine("Number Of Classes: " + detail.totalClass, Path.getAnalyzerPath());
                    recordLine("Number Of Skills: " + detail.totalSkill, Path.getAnalyzerPath());
                    recordLine("Number Of Lessons: " + detail.totalLesson, Path.getAnalyzerPath());
                    recordLine("Number Of Skills in Class: " + detail.totalSkillInClass, Path.getAnalyzerPath());
                    recordLine("Number Of Lessons in Class: " + detail.totalLessonInClass, Path.getAnalyzerPath());
                    recordLine("Number Of Lessons in Skill: " + detail.totalLessonInSkill, Path.getAnalyzerPath());
                    recordLine("GRF: " + detail.RFBySkillAndLesson, Path.getAnalyzerPath());
                } else {
                    recordLine("**Analyzing has failed**", Path.getAnalyzerPath());
                }
                recordLine("------------------------------------------------------", Path.getAnalyzerPath());
                recordLine("------------------------------------------------------", Path.getAnalyzerPath());
                // save data
                recordLine(course.toString(), Path.getAnalyzerDataPath());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void prettyWriteEngOnlyAnalyzer(CourseAnalyzer analyzer) {
        try {
            int count = 0;
            double[] totalLes = new double[10];
            double[] numCourse = new double[10];
            for (Course course : analyzer.courseList) {
                if (course.courseTitle.contains("Learn English")) {

                    CourseDetail detail = course.detail;
                    recordLine("Language Course Title: " + course.courseTitle, Path.getEngOnlyPath());
                    recordLine("Number Of Learners: " + course.numberOfLeaner, Path.getEngOnlyPath());
                    recordLine("Course Link: " + course.link, Path.getEngOnlyPath());
                    if (!detail.isFalse) {
                        recordLine("Number Of Classes: " + detail.totalClass, Path.getEngOnlyPath());
                        recordLine("Number Of Skills: " + detail.totalSkill, Path.getEngOnlyPath());
                        recordLine("Number Of Lessons: " + detail.totalLesson, Path.getEngOnlyPath());
                        recordLine("Number Of Skills in Class: " + detail.totalSkillInClass, Path.getEngOnlyPath());
                        recordLine("Number Of Lessons in Class: " + detail.totalLessonInClass, Path.getEngOnlyPath());
                        recordLine("Number Of Lessons in Skill: " + detail.totalLessonInSkill, Path.getEngOnlyPath());
                        recordLine("GRF: " + detail.RFBySkillAndLesson, Path.getEngOnlyPath());
                        // more

                    } else {
                        recordLine("**Analyzing has failed**", Path.getEngOnlyPath());
                    }
                    recordLine("------------------------------------------------------", Path.getEngOnlyPath());
                    recordLine("------------------------------------------------------", Path.getEngOnlyPath());
                    // save data
                    recordLine(course.toString(), Path.getEngOnlyPath());
                    count++;
                }
            }
            recordLine("Total: " + count + " english courses", Path.getEngOnlyPath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void prettyWriteEngGraph(List<Course> courseList) {
        try {
            for (Course course : courseList) {
                if (course.courseTitle.contains("Learn English")) {
                    CourseDetail detail = course.detail;
                    if (!detail.isFalse) {
                        recordLine(course.courseTitle, Path.getGraphEngOnlyPath());
                        recordLine(("Number Of Skills in Class," + detail.totalSkillInClass).replaceAll("\\[", "").replaceAll("\\]", ""), Path.getGraphEngOnlyPath());
//                    recordLine(("Number Of Lessons in Class," + detail.totalLessonInClass).replaceAll("\\[","").replaceAll("\\]",""), Path.getGraphPath());
//                    recordLine(("Number Of Lessons in Skill," + detail.totalLessonInSkill).replaceAll("\\[","").replaceAll("\\]",""), Path.getGraphPath());
                        recordLine(("GFR," + detail.RFBySkillAndLesson).replaceAll("\\[", "").replaceAll("\\]", ""), Path.getGraphEngOnlyPath());
                        recordLine("------------------------------------------------------", Path.getGraphEngOnlyPath());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void prettyWriteTotal(List<Course> courseList) {
        try {
            List<String> learnLang = new ArrayList<>();
            List<String> fromLang = new ArrayList<>();
            double[][] RFTotal = new double[50][50];
            double[][] STotal = new double[50][50];
            double[][] LTotal = new double[50][50];
            double[][] numberOfLearnerTotal = new double[50][50];
            for (Course course : courseList) {
                if(!course.numberOfLeaner.contains("Hatching")&&!course.numberOfLeaner.contains("Beta")) {
                    int i, j;
                    String[] langs = course.courseTitle.split("from");
                    String llang = langs[0].replace("Learn", "").trim();
                    String flang = langs[1].trim();
                    String num = "0";
                    int ex = 1000;
                    if(course.numberOfLeaner.contains("K")) {
                        num = course.numberOfLeaner.split("K")[0].trim();
                    }else {
                        num = course.numberOfLeaner.split("M")[0].trim();
                        ex = 1000000;
                    }
                    if (learnLang.contains(llang)) {
                        i = learnLang.indexOf(llang);
                    } else {
                        i = learnLang.size();
                        learnLang.add(llang);
                    }
                    if (fromLang.contains(flang)) {
                        j = fromLang.indexOf(flang);
                    } else {
                        j = fromLang.size();
                        fromLang.add(flang);
                    }
                    STotal[i][j] = course.detail.totalSkill;
                    LTotal[i][j] = course.detail.totalLesson;
                    RFTotal[i][j] = Math.sqrt(course.detail.totalSkill) / course.detail.totalLesson;
                    numberOfLearnerTotal[i][j] = Double.parseDouble(num)*ex;
                }
            }
            int i, j;
            for(int loop = 0; loop < 4 ; loop++) {
                String name ;
                double[][] temp = new double[50][50];
                if(loop == 0){
                    name = "Skill";
                    temp = STotal;
                }else if(loop == 1){
                    name = "Lesson";
                    temp = LTotal;
                }else if(loop == 2) {
                    name = "RF";
                    temp = RFTotal;
                }else {
                    name = "Num";
                    temp = numberOfLearnerTotal;
                }
                recordLine(name, Path.getTotalPath());
                i = 0;
                //print header
                String header = "";
                for (String f : fromLang) {
                    header += "," + f;
                }
                recordLine(header, Path.getTotalPath());
                for (String l : learnLang) {
                    String line = l;
                    j = 0;
                    for (String f : fromLang) {
                        line += "," + temp[i][j];
                        j++;
                    }
                    recordLine(line, Path.getTotalPath());
                    i++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void prettyWriteGraph(List<Course> courseList) {
        try {
            for (Course course : courseList) {

                CourseDetail detail = course.detail;
                if (!detail.isFalse) {
                    recordLine(course.courseTitle, Path.getGraphPath());
                    recordLine(("Number Of Skills in Class," + detail.totalSkillInClass).replaceAll("\\[", "").replaceAll("\\]", ""), Path.getGraphPath());
//                    recordLine(("Number Of Lessons in Class," + detail.totalLessonInClass).replaceAll("\\[","").replaceAll("\\]",""), Path.getGraphPath());
//                    recordLine(("Number Of Lessons in Skill," + detail.totalLessonInSkill).replaceAll("\\[","").replaceAll("\\]",""), Path.getGraphPath());
                    recordLine(("GFR," + detail.RFBySkillAndLesson).replaceAll("\\[", "").replaceAll("\\]", ""), Path.getGraphPath());
                    recordLine("------------------------------------------------------", Path.getGraphPath());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void recordLine(String json, String path) throws IOException {
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

    public void recordData(String record) {
        try {
            File file = new File(Path.getRecordsPath());
            if (!file.exists()) {
                file.createNewFile();
                new FileOutputStream(file, true).close();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(record);
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void loadDatabase() {
        try {
            load(eTableType.LANGUAGE_COURSE, Path.getLanguageFilePath());
            load(eTableType.LESSON, Path.getLessonFilePath());
            load(eTableType.QUESTION, Path.getQuestionFilePath());
            load(eTableType.SKILL, Path.getSkillFilePath());
            load(eTableType.STAGE, Path.getStageFilePath());
            load(eTableType.USER, Path.getUserFilePath());
            load(eTableType.LEARNING_INFO, Path.getLearningInfoFilePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void load(eTableType type, String path) throws FileNotFoundException {
        Gson gson = new Gson();
        File file = new File(path);
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    // put to db
                    switch (type) {
                        case LANGUAGE_COURSE:
                            LanguageCourse languageCourse = gson.fromJson(currentLine, LanguageCourse.class);
                            languageCourseMap.put(languageCourse.getId(), languageCourse);
                            break;
                        case LESSON:
                            Lesson lesson = gson.fromJson(currentLine, Lesson.class);
                            lessonMap.put(lesson.getId(), lesson);
                            break;
                        case QUESTION:
                            Question question = gson.fromJson(currentLine, Question.class);
                            questionMap.put(question.getId(), question);
                            //put to answer tree
                            HashMap<String, String> answers = GettingQuestion.answerTree.get(question.getType());
                            if (answers == null) {
                                answers = new HashMap<>();
                                GettingQuestion.answerTree.put(question.getType(), answers);
                            }
                            answers.put(question.getQuestionTitle() + "|" + question.getQuestionContent(), question.getAnswer());
                            break;
                        case SKILL:
                            Skill skill = gson.fromJson(currentLine, Skill.class);
                            skillMap.put(skill.getId(), skill);
                            break;
                        case STAGE:
                            Stage stage = gson.fromJson(currentLine, Stage.class);
                            stageMap.put(stage.getId(), stage);
                            break;
                        case USER:
                            User user = gson.fromJson(currentLine, User.class);
                            userMap.put(user.getId(), user);
                            break;
                        case LEARNING_INFO:
                            LearningLevel learningLevel = gson.fromJson(currentLine, LearningLevel.class);
                            learningLevels.add(learningLevel);
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Course> loadAnalyzer() throws FileNotFoundException {
        Gson gson = new Gson();
        File file = new File(Path.getAnalyzerDataPath());
        List<Course> courses = new ArrayList<>();
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    // put to db
                    Course course = gson.fromJson(currentLine, Course.class);
                    courses.add(course);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return courses;
    }

    public Map<Integer, LanguageCourse> getLanguageCourseMap() {
        return languageCourseMap;
    }

    public Map<Integer, Stage> getStageMap() {
        return stageMap;
    }

    public Map<Integer, Skill> getSkillMap() {
        return skillMap;
    }

    public Map<Integer, Lesson> getLessonMap() {
        return lessonMap;
    }

    public Map<Integer, Question> getQuestionMap() {
        return questionMap;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public HashSet<LearningLevel> getLearningLevels() {
        return learningLevels;
    }

    public void setLearningLevels(HashSet<LearningLevel> learningLevels) {
        this.learningLevels = learningLevels;
    }

    public enum eTableType {
        LANGUAGE_COURSE,
        LESSON,
        QUESTION,
        SKILL,
        STAGE,
        USER,
        LEARNING_INFO
    }
}
