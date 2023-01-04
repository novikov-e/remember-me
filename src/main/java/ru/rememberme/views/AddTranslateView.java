package ru.rememberme.views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.rememberme.storage.ImageStorage;
import ru.rememberme.ui.CustomButton;
import ru.rememberme.ui.CustomLabel;
import ru.rememberme.ui.CustomTextField;

public class AddTranslateView extends VBox {

    public AddTranslateView(ExerciseView exerciseView) {
        this.setPadding(new Insets(10));
        this.setSpacing(10);

        CustomLabel wordLabel = new CustomLabel("Слово/фраза:", 20, 325);
        CustomLabel translateLabel = new CustomLabel("Перевод:", 20, 325);
        HBox labelsHBox = new HBox();
        labelsHBox.setSpacing(10);
        labelsHBox.setPadding(new Insets(0, 0, 0, 10));
        labelsHBox.getChildren().addAll(wordLabel, translateLabel);

        final CustomTextField wordTextField = new CustomTextField(330, 30);
        repeatFocus(wordTextField);

        final CustomTextField translateTextField = new CustomTextField(330, 30);
        translateTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (wordTextField.getText().length() > 0 && translateTextField.getText().length() > 0) {
                    exerciseView.newTranslateEvent(wordTextField.getText(), translateTextField.getText());
                    wordTextField.clear();
                    translateTextField.clear();
                    repeatFocus(wordTextField);
                }
            }
        });

        EventHandler<ActionEvent> addTranslateEwent = event -> {
            if (wordTextField.getText().length() > 0 && translateTextField.getText().length() > 0) {
                exerciseView.newTranslateEvent(wordTextField.getText(), translateTextField.getText());
                wordTextField.clear();
                translateTextField.clear();
                repeatFocus(wordTextField);
            }
        };
        CustomButton addTranslateBtn = new CustomButton("", 30, 30, addTranslateEwent);
        final ImageView addTranslateImageView = new ImageView(ImageStorage.ADD_IMAGE);
        addTranslateImageView.setFitHeight(24);
        addTranslateImageView.setFitWidth(24);
        addTranslateBtn.setGraphic(addTranslateImageView);

        HBox textFieldsHBox = new HBox();
        textFieldsHBox.setSpacing(10);
        textFieldsHBox.getChildren().addAll(wordTextField, translateTextField, addTranslateBtn);

        this.getChildren().addAll(labelsHBox, textFieldsHBox);
    }

    private void repeatFocus(Node node) {
        Platform.runLater(() -> {
            if (!node.isFocused()) {
                node.requestFocus();
                repeatFocus(node);
            }
        });
    }
}
