package jp.ac.jaist.DataCrawler.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by d on 5/24/2016.
 */
public class Path {
    private static final String dbPathRootWindows = "C:\\duolingo";
    private static final String dbPathRootUNIX = "/duolingo";
    private static String dataPath, logPath;
    private static String recordsPath, skillFilePath, languageFilePath, lessonFilePath, graphPath,
            questionFilePath, stageFilePath, userFilePath, learningInfoFilePath, analyzerPath,
            analyzerDataPath, engOnlyPath, graphEngOnlyPath, totalPath;

    public static void buildRoot() throws IOException {
        String root;
        if (File.separator.equals("\\"))
            root = dbPathRootWindows;
        else
            root = dbPathRootUNIX;
        // making dir
        dataPath = root + File.separator + "data";
//        logPath = dataPath + File.separator + "log";
        File fileData = new File(dataPath);
//        File fileLog = new File(logPath);
        if (!fileData.exists()) {
            fileData.mkdirs();
        }
//        if (!fileLog.exists()) {
//            fileLog.mkdirs();
//        }

        // making file
        skillFilePath = dataPath + File.separator + "skill.txt";
        languageFilePath = dataPath + File.separator + "language.txt";
        lessonFilePath = dataPath + File.separator + "lesson.txt";
        questionFilePath = dataPath + File.separator + "question.txt";
        stageFilePath = dataPath + File.separator + "stage.txt";
        userFilePath = dataPath + File.separator + "user.txt";
        learningInfoFilePath = dataPath + File.separator + "learning.txt";
        recordsPath = dataPath + File.separator + "records.txt";
        analyzerPath = dataPath + File.separator + "analyzer.txt";
        analyzerDataPath = dataPath + File.separator + "analyzerData.txt";
        engOnlyPath = dataPath + File.separator + "engOnlyData.txt";
        graphPath = dataPath + File.separator + "graph.csv";
        graphEngOnlyPath = dataPath + File.separator + "graphEngOnly.csv";
        totalPath = dataPath + File.separator + "total.csv";

        File skillFile = new File(skillFilePath);
        File languageFile = new File(languageFilePath);
        File lessonFile = new File(lessonFilePath);
        File questionFile = new File(questionFilePath);
        File stageFile = new File(stageFilePath);
        File userFile = new File(userFilePath);
        File learningInfoFile = new File(learningInfoFilePath);
        File recordFile = new File(recordsPath);
        File analyzerFile = new File(analyzerPath);
        File analyzerDataFile = new File(analyzerDataPath);
        File graphDataFile = new File(graphPath);
        File engOnlyDataFile = new File(engOnlyPath);
        File graphEngOnlyDataFile = new File(graphEngOnlyPath);
        File totalFile = new File(totalPath);

        if (!skillFile.exists()) {
            skillFile.createNewFile();
            new FileOutputStream(skillFile, true).close();
        }
        if (!languageFile.exists()) {
            languageFile.createNewFile();
            new FileOutputStream(languageFile, true).close();
        }
        if (!lessonFile.exists()) {
            lessonFile.createNewFile();
            new FileOutputStream(lessonFile, true).close();
        }
        if (!questionFile.exists()) {
            questionFile.createNewFile();
            new FileOutputStream(questionFile, true).close();
        }
        if (!stageFile.exists()) {
            stageFile.createNewFile();
            new FileOutputStream(stageFile, true).close();
        }
        if (!userFile.exists()) {
            userFile.createNewFile();
            new FileOutputStream(userFile, true).close();
        }
        if (!learningInfoFile.exists()) {
            learningInfoFile.createNewFile();
            new FileOutputStream(learningInfoFile, true).close();
        }
        if (!recordFile.exists()) {
            recordFile.createNewFile();
            new FileOutputStream(recordFile, true).close();
        }
        if (!analyzerFile.exists()) {
            analyzerFile.createNewFile();
            new FileOutputStream(analyzerFile, true).close();
        }
        if (!analyzerDataFile.exists()) {
            analyzerDataFile.createNewFile();
            new FileOutputStream(analyzerDataFile, true).close();
        }
        if (!graphDataFile.exists()) {
            graphDataFile.createNewFile();
            new FileOutputStream(graphDataFile, true).close();
        }
        if (!engOnlyDataFile.exists()) {
            engOnlyDataFile.createNewFile();
            new FileOutputStream(engOnlyDataFile, true).close();
        }
        if (!graphEngOnlyDataFile.exists()) {
            graphEngOnlyDataFile.createNewFile();
            new FileOutputStream(graphEngOnlyDataFile, true).close();
        }
        if (!totalFile.exists()) {
            graphEngOnlyDataFile.createNewFile();
            new FileOutputStream(totalFile, true).close();
        }
    }

    public static String getDataPath() {
        return dataPath;
    }

    public static void setDataPath(String dataPath) {
        Path.dataPath = dataPath;
    }

    public static String getLogPath() {
        return logPath;
    }

    public static void setLogPath(String logPath) {
        Path.logPath = logPath;
    }

    public static String getSkillFilePath() {
        return skillFilePath;
    }

    public static void setSkillFilePath(String skillFilePath) {
        Path.skillFilePath = skillFilePath;
    }

    public static String getLanguageFilePath() {
        return languageFilePath;
    }

    public static void setLanguageFilePath(String languageFilePath) {
        Path.languageFilePath = languageFilePath;
    }

    public static String getLessonFilePath() {
        return lessonFilePath;
    }

    public static void setLessonFilePath(String lessonFilePath) {
        Path.lessonFilePath = lessonFilePath;
    }

    public static String getQuestionFilePath() {
        return questionFilePath;
    }

    public static void setQuestionFilePath(String questionFilePath) {
        Path.questionFilePath = questionFilePath;
    }

    public static String getStageFilePath() {
        return stageFilePath;
    }

    public static void setStageFilePath(String stageFilePath) {
        Path.stageFilePath = stageFilePath;
    }

    public static String getUserFilePath() {
        return userFilePath;
    }

    public static void setUserFilePath(String userFilePath) {
        Path.userFilePath = userFilePath;
    }

    public static String getLearningInfoFilePath() {
        return learningInfoFilePath;
    }

    public static void setLearningInfoFilePath(String learningInfoFilePath) {
        Path.learningInfoFilePath = learningInfoFilePath;
    }

    public static String getRecordsPath() {
        return recordsPath;
    }

    public static void setRecordsPath(String recordsPath) {
        Path.recordsPath = recordsPath;
    }

    public static String getAnalyzerPath() {
        return analyzerPath;
    }

    public static String getAnalyzerDataPath() {
        return analyzerDataPath;
    }

    public static String getGraphPath() {
        return graphPath;
    }

    public static String getEngOnlyPath() {
        return engOnlyPath;
    }

    public static String getGraphEngOnlyPath() {
        return graphEngOnlyPath;
    }

    public static String getTotalPath() {
        return totalPath;
    }
}
