package com.github.yeriomin.andtest.maker;

import com.github.yeriomin.andtest.core.QuestionMultipleChoice;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author yeriomin <yeriomin@gmail.com>
 */
public class Maker extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Java OCP Test Maker");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        } else {
            String source = args[0];
            String target = args.length == 2 ? args[1] : FileUtils.replaceExtension(source);
            try {
                Maker.convert(source, target, true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void convert(String source, String target, boolean append) throws Exception {
        ArrayList<QuestionMultipleChoice> questions;
        TestReader r = new TestReader(source);
        try {
            questions = r.getQuestions();
            System.out.println("Successfully read from " + source);
        } catch (IOException e) {
            throw new IOException("Could not read from " + source + ": " + e.getMessage());
        }
        TestWriter w = new TestWriter(questions);
        try {
            w.write(target, append);
            System.out.println("Successfully written to " + target);
        } catch (IOException e) {
            throw new IOException("Could not write to " + target + ": " + e.getMessage());
        }
    }

}
