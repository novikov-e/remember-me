package ru.rememberme.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import ru.rememberme.model.Translate;
import ru.rememberme.storage.ImageStorage;
import ru.rememberme.ui.CustomButton;
import ru.rememberme.ui.CustomLabel;
import ru.rememberme.ui.CustomTextField;


public class TranslateView extends HBox {

    private final ExerciseView exerciseView;
    public final Translate translate;

    private CustomLabel translateLabel;
    private CustomButton editBtn;
    private CustomButton deleteBtn;

    private CustomTextField wordTextField;
    private CustomTextField translateTextField;
    private CustomButton makeChangesBtn;

    public TranslateView(ExerciseView exerciseView, Translate translate) {
        this.exerciseView = exerciseView;
        this.translate = translate;
        this.setPadding(new Insets(0, 0, 10, 10));
        this.setSpacing(10);
        createView();
    }

    private void createView() {
        if (this.getChildren().size() == 0) {
            translateLabel = new CustomLabel(translate.toString(), 30, 630);

            EventHandler<ActionEvent> editEvent = event -> buttonEditPressed();
            editBtn = new CustomButton("", 30, 30, editEvent);
            ImageView editImageView = new ImageView(ImageStorage.EDIT_IMAGE);
            editImageView.setFitHeight(19);
            editImageView.setFitWidth(19);
            editBtn.setGraphic(editImageView);

            EventHandler<ActionEvent> deleteEvent = event -> exerciseView.deleteTranslateEvent(this);
            deleteBtn = new CustomButton("", 30, 30, deleteEvent);
            ImageView deleteImageView = new ImageView(ImageStorage.DELETE_IMAGE);
            deleteImageView.setFitHeight(20);
            deleteImageView.setFitWidth(20);
            deleteBtn.setGraphic(deleteImageView);

            this.getChildren().add(0, translateLabel);
            this.getChildren().add(1, editBtn);
            this.getChildren().add(2, deleteBtn);
        } else {
            translateLabel.setText(translate.toString());

            this.getChildren().set(0, translateLabel);
            this.getChildren().set(1, editBtn);
            this.getChildren().set(2, deleteBtn);
        }
    }

    private void buttonEditPressed() {
        if (wordTextField == null) {
            wordTextField = new CustomTextField(330, 30);
            translateTextField = new CustomTextField(330, 30);

            EventHandler<ActionEvent> makeChangesEvent = event -> {
                translate.setWord(wordTextField.getText());
                translate.setTranslate(translateTextField.getText());
                exerciseView.editTranslateEvent(translate);
                createView();
            };
            makeChangesBtn = new CustomButton("", 30, 30, makeChangesEvent);
            ImageView makeChangesImageView = new ImageView(ImageStorage.CHECK_IMAGE);
            makeChangesImageView.setFitHeight(22);
            makeChangesImageView.setFitWidth(22);
            makeChangesBtn.setGraphic(makeChangesImageView);
        }
        wordTextField.setText(translate.getWord());
        translateTextField.setText(translate.getTranslate());

        this.getChildren().set(0, wordTextField);
        this.getChildren().set(1, translateTextField);
        this.getChildren().set(2, makeChangesBtn);
    }
}
