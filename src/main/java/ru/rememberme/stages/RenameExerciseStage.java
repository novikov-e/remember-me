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
import ru.rememberme.model.Exercise;
import ru.rememberme.ui.CustomButton;
import ru.rememberme.ui.CustomLabel;
import ru.rememberme.ui.CustomTextField;
import ru.rememberme.views.RootPaneView;
import java.sql.SQLException;

public class RenameExerciseStage extends Stage {

    public RenameExerciseStage(Stage rootStage, RootPaneView rootPaneView,
                               String style, Exercise exercise) {
        this.setTitle("Переименовать список");
        this.setResizable(false);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(rootStage);

        CustomLabel nameInputLabel = new CustomLabel(
                "Введите новое название списка:", 20, 230);

        CustomTextField nameTextField = new CustomTextField(250, 20);
        nameTextField.setOnKeyPressed(event -> {
            if (nameTextField.getText().length() > 0) {
                if (event.getCode() == KeyCode.ENTER) {
                    exercise.setName(nameTextField.getText());
                    try {
                        rootPaneView.renameExercise(exercise);
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                    }
                    this.close();
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    this.close();
                }
            }
        });

        EventHandler<ActionEvent> renameExerciseEvent = event -> {
            if (nameTextField.getText().length() > 0) {
                exercise.setName(nameTextField.getText());
                try {
                    rootPaneView.renameExercise(exercise);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                this.close();
            }
        };
        CustomButton newExerciseBtn = new CustomButton(
                "Переименовать", 130, 30, renameExerciseEvent);

        EventHandler<ActionEvent> cancelEvent = event -> this.close();
        CustomButton cancelBtn = new CustomButton(
                "Отмена", 120, 30, cancelEvent);

        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.getChildren().addAll(newExerciseBtn, cancelBtn);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(nameInputLabel, nameTextField, buttonsHBox);

        Scene scene = new Scene(vBox, 280, 120, Color.WHITE);
        scene.getStylesheets().add(style);

        this.setScene(scene);
        this.show();
    }
}
