package ru.rememberme.ui;

import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.control.TabPane;

public class CustomTabPane extends TabPane {
    public CustomTabPane(int width, int height) {
        this.setCursor(Cursor.HAND);
        this.setPrefSize(width, height);
        this.setSide(Side.TOP);
        this.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        this.setTabMinHeight(20);
        this.setTabMinWidth(90);
    }
}
