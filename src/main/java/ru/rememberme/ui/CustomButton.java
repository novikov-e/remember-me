package ru.rememberme.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CustomButton extends Button {
    public CustomButton(String name, int width, int height, EventHandler<ActionEvent> event) {
        this.setText(name);
        this.setMaxSize(width, height);
        this.setMinSize(width, height);
        this.setOnAction(event);
    }
}
