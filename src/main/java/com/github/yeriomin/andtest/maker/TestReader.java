package com.github.yeriomin.andtest.maker;

import com.github.yeriomin.andtest.core.QuestionMultipleChoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 *
 * @author yeriomin <yeriomin@gmail.com>
 */
public class TestReader {

    private final String file;
    private final int aOrd = (int) 'a';

    public TestReader(String file) {
        this.file = file;
    }

    public ArrayList<QuestionMultipleChoice> getQuestions() throws IOException, Exception {
        String stringRaw = FileUtils.read(this.file);
        stringRaw = stringRaw.replaceAll("\\n[0-9]+\\n", "\n");
        stringRaw = stringRaw.replaceAll("\\n.*■.*\\n", "\n");
        ArrayList<QuestionMultipleChoice> questions;
        String[] questionsAndExplanations = stringRaw.split("Answers:");
        if (questionsAndExplanations.length != 2) {
            throw new Exception("No answers section found");
        }
        questions = this.parseQuestions(questionsAndExplanations[0]);
        questions = this.parseExplanations(questionsAndExplanations[1], questions);
        return questions;
    }

    private ArrayList<QuestionMultipleChoice> parseQuestions(String questionsRaw) {
        ArrayList<QuestionMultipleChoice> questions = new ArrayList<>();
        String delimiter = "(?m)^[0-9]+\\.";
        String[] questionsSplit = questionsRaw.replaceFirst(delimiter, "").split(delimiter);
        System.out.println(questionsSplit.length + " questions found");
        for (String questionString: questionsSplit) {
            questions.add(constructQuestion(questionString.trim()));
        }
        return questions;
    }

    private QuestionMultipleChoice constructQuestion(String questionString) {
        QuestionMultipleChoice question = new QuestionMultipleChoice();
        ArrayList<String> answers = new ArrayList<>(Arrays.asList(questionString.split("\\n[a-zA-Z]\\.")));
        question.setQuestion(answers.remove(0).trim());
        ArrayList<String> choices = answers.stream().map(String::trim).collect(Collectors.toCollection(ArrayList::new));
        question.setChoices(choices);
        return question;
    }

    private ArrayList<QuestionMultipleChoice> parseExplanations(String explanationsRaw, ArrayList<QuestionMultipleChoice> questions) {
        String delimiter = "(?m)^[0-9]+\\.";
        String[] explanationsSplit = explanationsRaw.trim().replaceFirst(delimiter, "").split(delimiter);
        System.out.println(explanationsSplit.length + " explanations found");
        for (int i = 0; i < questions.size(); i++) {
            questions.set(i, this.addExplanation(explanationsSplit[i].trim(), questions.get(i)));
        }
        return questions;
    }

    private QuestionMultipleChoice addExplanation(String explanationString, QuestionMultipleChoice question) {
        int dotIndex = explanationString.indexOf(".");
        String answersRaw = explanationString.substring(0, dotIndex);
        HashSet<Integer> correct = new HashSet<>();
        String[] answersSplit = answersRaw.split(",");
        for (String answerRaw: answersSplit) {
            char letter = answerRaw.trim().toLowerCase().charAt(0);
            correct.add(letter - aOrd);
        }
        question.setCorrect(correct);
        question.setExplanation(explanationString.substring(dotIndex + 1).trim());
        return question;
    }

}
