package ru.rememberme.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import ru.rememberme.ui.CustomButton;
import ru.rememberme.ui.CustomTab;

public class LearnView extends HBox {
    public LearnView(CustomTab tab) {
        EventHandler<ActionEvent> checkEvent = event -> tab.learn();
        CustomButton checkBtn = new CustomButton(
                "Тренировка", 110, 30, checkEvent);
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setPadding(new Insets(0, 0, 10, 0));
        this.getChildren().add(checkBtn);
    }
}