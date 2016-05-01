package com.github.yeriomin.andtest.maker;

import com.github.yeriomin.andtest.core.Question;
import com.github.yeriomin.andtest.core.QuestionMultipleChoice;
import com.github.yeriomin.andtest.core.Test;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author yeriomin <yeriomin@gmail.com>
 */
public class TestWriter {

    private final String TEST_HTML = "test.html";
    private final ArrayList<QuestionMultipleChoice> newQuestions;

    public TestWriter(ArrayList<QuestionMultipleChoice> questions) {
        this.newQuestions = questions;
    }

    public void write(String file, boolean append) throws IOException {
        File f = new File(file);
        ArrayList<Question> allQuestions = new ArrayList<>();
        if (f.exists() && !f.isDirectory()) {
            if (!append) {
                f.delete();
            } else {
                String jsonString = FileUtils.read(file);
                Test test = new Test(new JSONObject(jsonString));
                ArrayList<Question> existingQuestions = test.getQuestions();
                System.out.println("Found " + existingQuestions.size() + " questions in "
                        + file + ", new questions are going to be appended");
                allQuestions.addAll(existingQuestions);
            }
        } else {
            System.out.println("File " + file + " not found and will be created");
        }
        allQuestions.addAll(this.newQuestions);
        System.out.println("Writing " + this.newQuestions.size()
                + " new questions, " + allQuestions.size() + " total");
        Test test = new Test();
        test.setQuestions(allQuestions);
        test.setTimeLimit(0);
        test.setDescription(file);
        FileUtils.write(test.toJSONString(), file);

        String fileHtml = (f.getParent() == null ? "." : f.getParent())
                + File.separator + TEST_HTML;
        File fHtml = new File(fileHtml);
        if (!fHtml.exists()) {
            this.ExportTestHtml(fileHtml);
        }
    }

    private void ExportTestHtml(String file) throws IOException {
        String testHtmlContent;
        try (InputStream streamIn = TestWriter.class.getResourceAsStream("/" + TEST_HTML)) {
            testHtmlContent = FileUtils.read(streamIn);
        }
        FileUtils.write(testHtmlContent, file);
    }
}
