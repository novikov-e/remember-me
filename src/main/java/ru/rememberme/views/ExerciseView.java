package ru.rememberme.views;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ru.rememberme.model.Translate;
import ru.rememberme.storage.StorageSQL;
import ru.rememberme.ui.CustomTab;
import java.sql.SQLException;

public class ExerciseView extends VBox {

    private final CustomTab tab;
    private final VBox translatesVBox;
    private final LearnView learnView;

    public ExerciseView(CustomTab tab) {
        this.tab = tab;
        AddTranslateView addTranslateView = new AddTranslateView(this);
        translatesVBox = new VBox();
        for (int i = 0; i < tab.exercise.getTranslates().size(); i++) {
            translatesVBox.getChildren().add(
                    new TranslateView(this, tab.exercise.getTranslate(i)));
        }
        learnView = new LearnView(tab);
        if (tab.exercise.getTranslates().size() == 0) {
            learnView.setVisible(false);
        }
        VBox translatesVBox = new VBox();
        translatesVBox.setSpacing(10);
        translatesVBox.setPadding(new Insets(10, 0, 10 ,0));
        translatesVBox.getChildren().addAll(this.translatesVBox, learnView);

        this.getChildren().addAll(addTranslateView, new ScrollPane(translatesVBox));
    }

    public void newTranslateEvent(String wordStr, String translateStr) {
        try {
            Translate translate = StorageSQL.getInstance()
                    .newTranslate(tab.exercise.getId(), wordStr, translateStr);
            tab.exercise.addTranslate(translate);
            translatesVBox.getChildren()
                    .add(new TranslateView(this, translate));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        learnView.setVisible(true);
    }

    public void deleteTranslateEvent(TranslateView translateView) {
        try {
            StorageSQL.getInstance().deleteTranslate(translateView.translate.getId());
            tab.exercise.removeTranslate(translateView.translate);
            translatesVBox.getChildren().remove(translateView);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        if (tab.exercise.getTranslates().isEmpty()) {
            learnView.setVisible(false);
        }
    }

    public void editTranslateEvent(Translate translate) {
        try {
            StorageSQL.getInstance().updateTranslate(translate);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
