package ru.rememberme.views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.rememberme.model.Translate;
import ru.rememberme.storage.ImageStorage;
import ru.rememberme.ui.CustomButton;
import ru.rememberme.ui.CustomLabel;
import ru.rememberme.ui.CustomTab;
import ru.rememberme.ui.CustomTextField;
import java.util.ArrayList;

public class CheckView extends VBox {

    private final CustomTab tab;
    private final ArrayList<Translate> mistakes = new ArrayList<>();
    private final ArrayList<Translate> shuffle;
    private int questionNumber;

    public CheckView(CustomTab tab) {
        this.tab = tab;
        shuffle = tab.exercise.shuffle();
        this.setPadding(new Insets(10, 0, 10, 10));
        learn();
    }

    private void learn() {
        this.getChildren().clear();

        CustomLabel questionLabel = new CustomLabel(
                "Как будет на английском \""
                        + shuffle.get(questionNumber).getTranslate()
                        + "\"?", 20, 310);
        questionLabel.setPadding(new Insets(0, 0, 0, 10));
        CustomTextField answerTextField = new CustomTextField(620, 30);
        repeatFocus(answerTextField);
        answerTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                check(answerTextField.getText());
                repeatFocus(answerTextField);
            }
        });

        EventHandler<ActionEvent> nextQuestionEvent = event -> {
            check(answerTextField.getText());
            repeatFocus(answerTextField);
        };
        CustomButton nextBtn = new CustomButton("", 30, 30, nextQuestionEvent);
        final ImageView nextImageView = new ImageView(ImageStorage.NEXT_IMAGE);
        nextImageView.setFitHeight(24);
        nextImageView.setFitWidth(24);
        nextBtn.setGraphic(nextImageView);

        EventHandler<ActionEvent> cancelEvent = event -> {
            questionNumber = 0;
            tab.exerciseView();
        };
        CustomButton cancelBtn = new CustomButton("", 30, 30, cancelEvent);
        final ImageView cancelImageView = new ImageView(ImageStorage.EXIT_IMAGE);
        cancelImageView.setFitHeight(20);
        cancelImageView.setFitWidth(20);
        cancelBtn.setGraphic(cancelImageView);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(answerTextField, nextBtn, cancelBtn);

        this.getChildren().addAll(questionLabel, hBox);
    }

    private void check(String answer) {
        if (questionNumber < tab.exercise.getTranslates().size() - 1) {
            if (!answer.equalsIgnoreCase(shuffle.get(questionNumber).getWord())) {
                mistakes.add(shuffle.get(questionNumber));
            }
            questionNumber++;
            learn();
        } else {
            if (!answer.equalsIgnoreCase(shuffle.get(questionNumber).getWord())) {
                mistakes.add(shuffle.get(questionNumber));
            }
            questionNumber = 0;
            result();
        }
    }

    private void result() {
        this.getChildren().clear();

        VBox mistakesVBox = new VBox();
        mistakesVBox.setPadding(new Insets(0, 0, 10, 0));
        ScrollPane scrollPane = new ScrollPane(mistakesVBox);
        if (mistakes.size() > 0) {
            CustomLabel mistakesWhereMadeLabel = new CustomLabel(
                    "Допущены следующие ошибки:", 30, 200);
            mistakesWhereMadeLabel.setPadding(new Insets(0, 0, 0, 10));
            this.getChildren().add(mistakesWhereMadeLabel);
            for (Translate translate : mistakes) {
                CustomLabel errorLabel = new CustomLabel(translate.toString(), 30, 200);
                errorLabel.setPadding(new Insets(0, 0, 0, 10));
                mistakesVBox.getChildren().add(errorLabel);
            }
        } else {
            CustomLabel greatLabel = new CustomLabel("Отлично!", 30, 200);
            this.getChildren().add(greatLabel);
        }
        this.getChildren().add(scrollPane);

        EventHandler<ActionEvent> repeatEvent = event -> {
            mistakes.clear();
            learn();
        };
        CustomButton repeatLabel = new CustomButton("Повторить", 100, 30, repeatEvent);

        EventHandler<ActionEvent> closeEvent = event -> {
            mistakes.clear();
            tab.exerciseView();
        };
        CustomButton closeBtn = new CustomButton("Закончить", 100, 30, closeEvent);

        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.setPadding(new Insets(10, 0, 0, 10));
        buttonsHBox.getChildren().addAll(repeatLabel, closeBtn);

        mistakesVBox.getChildren().add(buttonsHBox);
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
