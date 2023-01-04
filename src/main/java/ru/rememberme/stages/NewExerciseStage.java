package ru.rememberme.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.rememberme.ui.CustomButton;
import ru.rememberme.ui.CustomLabel;
import ru.rememberme.ui.CustomTextField;
import ru.rememberme.views.RootPaneView;

public class NewExerciseStage extends Stage {

    public NewExerciseStage(Stage rootStage,
                            RootPaneView rootPaneView,
                            String style) {
        this.setTitle("Создание списка");
        this.setResizable(false);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(rootStage);

        CustomLabel nameInputLabel = new CustomLabel(
                "Введите название списка:", 20, 230);
        CustomTextField nameTextField = new CustomTextField(230, 20);
        nameTextField.setOnKeyPressed(event -> {
            if (nameTextField.getText().length() > 0) {
                if (event.getCode() == KeyCode.ENTER) {
                    rootPaneView.newExercise(nameTextField.getText());
                    this.close();
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    this.close();
                }
            }
        });
        nameInputLabel.setLabelFor(nameTextField);
        nameInputLabel.setMnemonicParsing(true);

        EventHandler<ActionEvent> newExerciseEvent = event -> {
            if (nameTextField.getText().length() > 0) {
                rootPaneView.newExercise(nameTextField.getText());
                this.close();
            }
        };
        CustomButton newExerciseBtn = new CustomButton(
                "Создать", 110, 30, newExerciseEvent);

        EventHandler<ActionEvent> cancelEvent = event -> this.close();
        CustomButton cancelBtn = new CustomButton("Отмена", 110, 30, cancelEvent);

        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.getChildren().addAll(newExerciseBtn, cancelBtn);

        VBox textVBox = new VBox();
        textVBox.getChildren().add(nameTextField);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(nameInputLabel, textVBox, buttonsHBox);

        Scene scene = new Scene(vBox, 250, 120, Color.WHITE);
        scene.getStylesheets().add(style);

        this.setScene(scene);
        this.show();
    }
}
