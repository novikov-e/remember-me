package ru.rememberme.ui;

import javafx.scene.control.Label;

public class CustomLabel extends Label {

    public CustomLabel(String name, int minHeight, int minWidth) {
        this.setText(name);
        this.setMinHeight(minHeight);
        this.setMinWidth(minWidth);
    }
}
