package ru.rememberme.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.rememberme.ui.CustomButton;
import ru.rememberme.ui.CustomLabel;
import ru.rememberme.views.RootPaneView;

public class DeleteExerciseStage extends Stage {

    public DeleteExerciseStage(Stage rootStage, RootPaneView rootPaneView,
                               String style, String exerciseName) {
        this.setTitle("Удаление списка");
        this.setResizable(false);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(rootStage);

        CustomLabel questionLabel = new CustomLabel(
                "Вы действительно хотите удалить \"" + exerciseName + "\"?",
                20, 230);

        EventHandler<ActionEvent> deleteEvent = event -> {
            rootPaneView.deleteExercise();
            this.close();
        };
        CustomButton deleteBtn = new CustomButton(
                "Удалить", 110, 30, deleteEvent);

        EventHandler<ActionEvent> cancelEvent = event -> this.close();
        CustomButton cancelBtn = new CustomButton(
                "Отмена", 110, 30, cancelEvent);

        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.getChildren().addAll(deleteBtn, cancelBtn);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(questionLabel, buttonsHBox);

        Scene scene = new Scene(vBox, 360, 85, Color.WHITE);
        scene.getStylesheets().add(style);

        this.setScene(scene);
        this.show();
    }
}
