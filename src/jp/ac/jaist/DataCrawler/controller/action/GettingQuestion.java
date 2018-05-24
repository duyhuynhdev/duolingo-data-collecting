package jp.ac.jaist.DataCrawler.controller.action;

import jp.ac.jaist.DataCrawler.database.Database;
import jp.ac.jaist.DataCrawler.database.IdGenerator;
import jp.ac.jaist.DataCrawler.obj.learning.Question;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hpduy17 on 5/25/16.
 */
public class GettingQuestion extends MainAction {
    private String questionContentTypeXPath = "/html/body/div[1]/div/div[2]/div/div/div[1]/div[2]";
    private final String progressbarXPath = "/html/body/div[1]/div/div[2]/div/div/div[1]/div[1]/div[1]/div[4]/div/div"; // get "style" attribute to check 100%
    private final String answerXPath = "/html/body/div[3]/div[1]/section/footer/div[2]/div/h1/span/bdi";
    private final String nextButtonXPath = "//*[@id=\"next_button\"]";
    private final String skipButtonXPath = "//*[@id=\"skip_button\"]";
    private final String resultXPath = "/html/body/div[3]/div[1]/section/footer/div[2]/div/span/span";

    //sub tag of question content type <div> tag
    private final String questionTitleXPath = "./h1";
    private final String questionContentTagName = "bdi"; // except challenge name
    //challenge-judge-checkbox
    private final String answerCheckBoxQCXPath = "./label/table/tbody/tr/td[3]/bdi"; // sub tag of challenge JudgeCheckBoxQCXPath
    //challenge-listen-url
    private final String urlSpeakerXPath = "./div/div[1]/div/div[1]";
    /**
     * ANSWER
     */
    //answer tree
    public static HashMap<Question.eQuestionType, HashMap<String, String>> answerTree = new HashMap<>();

    //challenge select
    private final String option1XPath = "//*[@id=\"option-1\"]";

    private final String option2XPath = "//*[@id=\"option-2\"]";
    private final String option3XPath = "//*[@id=\"option-3\"]";
    private final String optionContentXPath = "./label/span[2]"; // sub tag of option
    //challenge translation
    private final String answerTextAreaXPath = "//*[@id=\"text-input\"]";

    //challenge name, challenge listen
    private final String answerTextInputXPath = "//*[@id=\"word-input\"]";

    //challenge-judge-checkbox
    private final String checkBoxXPath = "/html/body/div[3]/div[1]/section/div[2]/div[2]/div/div/div[2]/ul/li";
    private final String checkBoxXPathParent = "/html/body/div[3]/div[1]/section/div[2]/div[2]/div/div/div[2]/ul";
    //challenge-judge-selection

    private String selectXPath = "/html/body/div[3]/div[1]/section/div[2]/div[2]/div/h2/label/select";
    //private final String selectXPath = "//*[@id=\"options_8\"]";

    //Local Variable
    private Map<String, Question.eQuestionType> questionTypeMap;


    public GettingQuestion() {
        if (questionTypeMap == null)
            questionTypeMap = new HashMap<>();
        questionTypeMap.put("challenge-select", Question.eQuestionType.Challenge_Select);
        questionTypeMap.put("challenge-translate", Question.eQuestionType.Challenge_Translate);
        questionTypeMap.put("challenge-judge", Question.eQuestionType.Challenge_Judge);
        questionTypeMap.put("challenge-listen", Question.eQuestionType.Challenge_Listen);
        questionTypeMap.put("challenge-name", Question.eQuestionType.Challenge_Name);
    }

    @Override
    public void runBFS(RemoteWebDriver driver) {
        int numberOfQuestion = 0;
        while (!isFinished(driver)) {
            boolean isJudgeSelectBox = false;
            WebElement questionTypeTag = driver.findElement(By.xpath(questionContentTypeXPath));
            WebElement nextButton = driver.findElement(By.xpath(nextButtonXPath));
            WebElement skipButton = driver.findElement(By.xpath(skipButtonXPath));
            Question question = new Question();
            if (questionTypeTag != null) {
                question.setType(recognizeQuestionType(questionTypeTag));
                System.out.println("QUESTION TYPE:" + question.getType());
                //get question title
                question.setQuestionTitle(questionTypeTag.findElement(By.xpath(questionTitleXPath)).getText());
                System.out.println("---Title:" + question.getQuestionTitle());
                //get question content
                switch (question.getType()) {
                    case Challenge_Other:
                        System.out.println("---Content:" + "WARNING!!! OTHER TYPE IS CAUGHT");
                        break;
                    case Challenge_Judge:
                        String removed = "";
                        try {
                            removed = driver.findElement(By.xpath(checkBoxXPathParent)).getText();
                        } catch (Exception ex) {
                            removed = driver.findElement(By.xpath(selectXPath)).getText();
                            isJudgeSelectBox = true;
                        }
                        String content = questionTypeTag.getText();
                        question.setQuestionContent(content.replace(removed, "(answer)"));
                        System.out.println("---Content:" + question.getQuestionContent().replaceAll("\\n", " "));
                        break;
                    case Challenge_Name:
                        question.setQuestionContent("Image");
                        System.out.println("---Content:" + question.getQuestionContent().replaceAll("\\n", " "));
                        break;
                    case Challenge_Listen:
                        String src = questionTypeTag.findElement(By.xpath(urlSpeakerXPath)).getAttribute("src");
                        question.setQuestionContent(src);
                        System.out.println("---Content:" + question.getQuestionContent().replaceAll("\\n", " "));
                        break;
                    default:
                        question.setQuestionContent(questionTypeTag.findElement(By.tagName(questionContentTagName)).getText());
                        System.out.println("---Content:" + question.getQuestionContent().replaceAll("\\n", " "));
                }
                //check question exist base on question tree?
                HashMap<String, String> answers = answerTree.get(question.getType());
                if (answers == null || !answers.containsKey(question.getQuestionTitle() + "|" + question.getQuestionContent())) { // collect question
                    if (answers == null) {
                        answers = new HashMap<>();
                        answerTree.put(question.getType(), answers);
                    }
                    skipButton.click();
                    breathe1second();
                    //get the answer
                    question.setAnswer(driver.findElement(By.xpath(answerXPath)).getText());
                    System.out.println("*****Question do not have an answer --> Find an answer");
                    System.out.println("---Answer found:" + question.getAnswer());
                    //put to answer tree
                    answers.put(question.getQuestionTitle() + "|" + question.getQuestionContent(), question.getAnswer());
                    // save question
                    question.setId(IdGenerator.getQuestionId());
                    database.saveData(Database.eTableType.QUESTION, question);
                } else { // answering question
                    String answer = answers.get(question.getQuestionTitle() + "|" + question.getQuestionContent());
                    System.out.println("*****An answer for this question have found --> Answering...");
                    answering(question.getType(), answer, question, driver, isJudgeSelectBox);
                    nextButton.click();
                    // check result
                    WebElement result = driver.findElement(By.xpath(resultXPath));
                    if(result.getAttribute("class").contains("correct")) {
                        numberOfQuestion++;
                    }else if(result.getAttribute("class").contains("wrong")){
                        question.setAnswer(driver.findElement(By.xpath(answerXPath)).getText());
                        System.out.println("*****Question have a wrong answer --> Update an answer");
                        System.out.println("---Answer updated:" + question.getAnswer());
                        //put to answer tree
                        answers.put(question.getQuestionTitle() + "|" + question.getQuestionContent(), question.getAnswer());
                        // save question
                        question.setId(IdGenerator.getQuestionId());
                        database.saveData(Database.eTableType.QUESTION, question);
                    }else {
                        System.out.println("*****WRONG ANALYSIS-----------------------------------------");
                    }
                    breathe1second();

                }
                nextButton.click();
                breathe1second();
            }
        }
        breathe(5000);
        database.recordData(numberOfQuestion + "\n");
        System.out.println("Finish");
    }

    @Override
    public void runDFS(RemoteWebDriver driver) {

    }

    public void answering(Question.eQuestionType type, String answer, Question question, RemoteWebDriver driver, boolean isJudgeSelectBox) {
        String answerTemp = answer.toLowerCase().trim().replaceAll(" ", "");
        switch (type) {
            case Challenge_Listen:
            case Challenge_Name:
                if (answer.contains(",")) {
                    answer = answer.split(",")[0];
                }
                WebElement input = driver.findElement(By.xpath(answerTextInputXPath));
                input.clear();
                input.sendKeys(answer);
                break;
            case Challenge_Translate:
                WebElement textArea = driver.findElement(By.xpath(answerTextAreaXPath));
                textArea.clear();
                textArea.sendKeys(answer);
                break;
            case Challenge_Select:
                WebElement option1 = driver.findElement(By.xpath(option1XPath));
                if (option1 != null) {
                    WebElement content = option1.findElement(By.xpath(optionContentXPath));
                    String contentString = content.getText().toLowerCase().trim().replaceAll(" ", "");
                    if (contentString.equals(answerTemp)) {
                        option1.click();
                        break;
                    }
                }
                WebElement option2 = driver.findElement(By.xpath(option2XPath));
                if (option2 != null) {
                    WebElement content = option2.findElement(By.xpath(optionContentXPath));
                    String contentString = content.getText().toLowerCase().trim().replaceAll(" ", "");
                    if (contentString.equals(answerTemp)) {
                        option2.click();
                        break;
                    }
                }
                WebElement option3 = driver.findElement(By.xpath(option3XPath));
                if (option3 != null) {
                    WebElement content = option3.findElement(By.xpath(optionContentXPath));
                    String contentString = content.getText().toLowerCase().trim().replaceAll(" ", "");
                    if (contentString.equals(answerTemp)) {
                        option3.click();
                        break;
                    }
                }
            case Challenge_Judge:
                try {// checkbox type
                    if (!isJudgeSelectBox) {
                        List<WebElement> checkAnswers = driver.findElements(By.xpath(checkBoxXPath));
//                        // be careful.
//                        List<String> answers = checkMultipleAnswer(answer);
//                        for (WebElement e : checkAnswers) {
//                            String answerString = e.findElement(By.xpath(answerCheckBoxQCXPath)).getText();
//                            for (String a : answers) {
//                                if (a.startsWith(",")) {
//                                    a = a.substring(1, a.length());
//                                }
//                                if (a.toLowerCase().replaceAll(" ", "")
//                                        .equals(answerString.toLowerCase().replaceAll(" ", ""))) {
//                                    e.click();
//                                    breathe1second();
//                                    break;
//                                }
//                            }
//                        }
                        for (WebElement e : checkAnswers) {
                            String answerString = e.findElement(By.xpath(answerCheckBoxQCXPath)).getText();
                            if (answer.contains(answerString.trim())) {
                                e.click();
                                breathe1second();
                                break;
                            }
                        }
                    } else {
                        // get answer from question
                        String qc = question.getQuestionContent().replaceAll(question.getQuestionTitle() + "\\n", "");
                        String tail = qc.substring(qc.indexOf("(answer") + "(answer)".length()).replaceAll("\\n", "");
                        String head = qc.substring(0, qc.indexOf("(answer)")).replaceAll("\\n", "");
                        String chooseAnswer = answer.substring(head.length(), answer.indexOf(tail)).trim();
                        Select dropdown = new Select(driver.findElement(By.xpath(selectXPath)));
                        dropdown.selectByValue(chooseAnswer);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            default:

        }


    }

    public Question.eQuestionType recognizeQuestionType(WebElement questionTypeTag) {
        String type = questionTypeTag.getAttribute("data-test");
        if (questionTypeMap.containsKey(type)) {
            return questionTypeMap.get(type);
        } else {
            return Question.eQuestionType.Challenge_Other;
        }

    }

    public boolean isFinished(RemoteWebDriver driver) {
        WebElement progressBar = driver.findElement(By.xpath(progressbarXPath));
        String styleAtt = progressBar.getAttribute("style");
        return styleAtt.contains("100%");
    }


    public static List<String> checkMultipleAnswer(String answer) {
        List<String> answers = new ArrayList<>();
        try {
            Pattern myPattern = Pattern.compile(".*?\\.");
            Matcher m = myPattern.matcher(answer);
            while (m.find()) {
                answers.add(m.group(0));
            }
            if (answers.isEmpty()) {
                answers.add(answer);
            }
        } catch (Exception ex) {
            answers.add(answer);
        }
        return answers;
    }

}
