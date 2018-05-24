package jp.ac.jaist.DataCrawler.database;

import java.util.Set;

/**
 * Created by hpduy17 on 5/24/16.
 */
public class IdGenerator {
    private static Database database = Database.getInstance();
    private static int languageCourseId = 0;
    private static int lessonId = 0;
    private static int questionId = 0;
    private static int skillId = 0;
    private static int stageId = 0;
    private static int userId = 0;
    private static int learningInfoId = 0;

    public synchronized static int getLanguageCourseId() {
        Set<Integer> idSet = database.getLanguageCourseMap().keySet();
        do {
            languageCourseId++;
        }
        while (idSet.contains(languageCourseId));
        return languageCourseId;
    }

    public synchronized static int getLessonId() {
        Set<Integer> idSet = database.getLessonMap().keySet();
        do {
            lessonId++;
        }
        while (idSet.contains(lessonId));
        return lessonId;
    }

    public synchronized static int getQuestionId() {
        Set<Integer> idSet = database.getQuestionMap().keySet();
        do {
            questionId++;
        }
        while (idSet.contains(questionId));
        return questionId;
    }

    public synchronized static int getSkillId() {
        Set<Integer> idSet = database.getSkillMap().keySet();
        do {
            skillId++;
        }
        while (idSet.contains(skillId));
        return skillId;
    }

    public synchronized static int getStageId() {
        Set<Integer> idSet = database.getStageMap().keySet();
        do {
            stageId++;
        }
        while (idSet.contains(stageId));
        return stageId;
    }

//    public synchronized static int getUserId() {
//        Set<Integer> idSet = database.getUserMap().keySet();
//        do {
//            userId++;
//        }
//        while (idSet.contains(userId));
//        return userId;
//    }
//
//    public synchronized static int getLearningInfoId() {
//        Set<Integer> idSet = database.getLearningInfoMap().keySet();
//        do {
//            learningInfoId++;
//        }
//        while (idSet.contains(learningInfoId));
//        return learningInfoId;
//    }

}
