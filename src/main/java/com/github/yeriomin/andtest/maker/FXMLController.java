package com.github.yeriomin.andtest.maker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author yeriomin <yeriomin@gmail.com>
 */
public class FXMLController implements Initializable {

    @FXML
    private TextField textSource;

    @FXML
    private TextField textTarget;

    @FXML
    private CheckBox checkboxAppend;

    @FXML
    private void handleButtonSource(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose source file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Plain text files", "*.txt"),
            new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File file = fileChooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
        if (file != null) {
            textSource.setText(file.toString());
            textTarget.setText(FileUtils.replaceExtension(file.toString()));
        }
    }

    @FXML
    private void handleButtonTarget(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose target file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JSON files", "*.json"),
            new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File file = fileChooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
        if (file != null) {
            textTarget.setText(file.toString());
        }
    }

    @FXML
    private void handleButtonGo(ActionEvent event) {
        String message = "Questions parsed and saved successfully";
        AlertType type = AlertType.INFORMATION;
        try {
            Maker.convert(textSource.getText(), textTarget.getText(), checkboxAppend.isSelected());
        } catch (Exception e) {
            message = e.getMessage();
            type = AlertType.ERROR;
        }
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
