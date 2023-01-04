package ru.rememberme.ui;

import javafx.scene.Cursor;
import javafx.scene.control.TextField;

public class CustomTextField extends TextField {
    public CustomTextField(int width, int height) {
        this.setCursor(Cursor.TEXT);
        this.setPrefSize(width, height);
        this.setEditable(true);
    }
}
