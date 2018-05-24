package jp.ac.jaist.Simulation.obj;

/**
 * Created by hpduy17 on 9/9/16.
 */
public class Structure {
    public static int[] milestone = {8, 8, 12, 12, 15};
    public static int[] skills = {3, 3, 3, 4, 2, 2, 2, 1,
            2, 7, 2, 2, 1, 4, 8, 3,
            4, 9, 7, 8, 9, 10, 3, 7, 4, 7, 10, 5,
            10, 9, 5, 10, 7, 9, 9, 1, 4, 10, 1, 6,
            7, 4, 4, 3, 7, 10, 7, 9, 7, 2, 5, 1, 2, 2, 2};
    public static int[] milestone1 = {8, 8, 6, 6, 12, 15};
    public static int[] skills1 = {3, 3, 3, 4, 2, 2, 2, 1,
            2, 7, 2, 2, 1, 4, 8, 3,
            4, 9, 7, 8, 9, 10,
            3, 7, 4, 7, 10, 5,
            10, 9, 5, 10, 7, 9, 9, 1, 4, 10, 1, 6,
            7, 4, 4, 3, 7, 10, 7, 9, 7, 2, 5, 1, 2, 2, 2};



    public int learn(User user) {
        int numlesson = 0;
        int idx = 0;
        for (int s : milestone) {
            for (int i = 0; i < s; i++) {
                int l = skills[idx];
                double ex = Param.exValLesson;
                for (int j = 0; j < l; j++) {
                    user.passedLessons++;
                    for (int r = 0; r < Param.reviewLessonRatio; r++) {
                        if (j != 0 || r != 0)
                            ex = ex * Param.exRise;
                        //finish lesson
                        numlesson++;
                        user.update(-ex);
                        if (user.isPassThreshold())
                            return numlesson;
                    }
                }
                //finish skill
                idx++;
                user.update(Param.exValSkill);
                if (user.isPassThreshold())
                    return numlesson;
            }
            //finish milestone
            user.update(Param.exValMilestone);
            if (user.isPassThreshold())
                return numlesson;
        }
        numlesson += (int) ((user.challengingThreshold - user.exval)*-1)/Param.exValLesson;
        return numlesson;
    }
    public int learnNewModel(User user) {
        user.exval = 0;
        int numlesson = 0;
        int idx = 0;
        for (int s : milestone1) {
            for (int i = 0; i < s; i++) {
                int l = skills1[idx];
                double ex = Param.exValLesson;
                for (int j = 0; j < l; j++) {
                    user.passedLessonsNew++;
                    for (int r = 0; r < Param.reviewLessonRatio; r++) {
                        if (j != 0 || r != 0)
                            ex = ex * Param.exRise;
                        //finish lesson
                        numlesson++;
                        user.update(-ex);
                        if (user.isPassThreshold())
                            return numlesson;
                    }
                }
                //finish skill
                idx++;
                user.update(Param.exValSkill);
                if (user.isPassThreshold())
                    return numlesson;
            }
            //finish milestone
            user.update(Param.exValMilestone);
            if (user.isPassThreshold())
                return numlesson;
        }
        numlesson += (int) ((user.challengingThreshold - user.exval)*-1)/Param.exValLesson;
        return numlesson;
    }

    public void getThreshold(User user, int xp) {
        if (xp == 0)
            return;
        int idx = 0;
        for (int s : milestone) {
            for (int i = 0; i < s; i++) {
                int l = skills[idx];
                double ex = Param.exValLesson;
                for (int j = 0; j < l; j++) {
                    for (int r = 0; r < Param.reviewLessonRatio; r++) {
                        if (j != 0 || r != 0)
                            ex = ex * Param.exRise;
                        //finish lesson
                        user.challengingThreshold -= ex;
                        xp -= Param.xpEachLesson;
                        if (xp <= 0)
                            return;
                    }
                }
                //finish skill
                idx++;
                user.challengingThreshold += Param.exValSkill;
            }
            //finish milestone
            user.challengingThreshold += Param.exValMilestone;
        }
        user.challengingThreshold -= (xp / Param.xpEachLesson) * Param.exValLesson;
    }

}
