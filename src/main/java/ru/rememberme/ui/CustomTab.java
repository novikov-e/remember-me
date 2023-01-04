package ru.rememberme.ui;

import javafx.scene.control.Tab;
import ru.rememberme.model.Exercise;
import ru.rememberme.views.*;

public class CustomTab extends Tab {

    public Exercise exercise;

    public CustomTab(Exercise exercise) {
        this.setText(exercise.getName());
        this.exercise = exercise;
        this.setContent(new ExerciseView(this));
    }

    public void exerciseView() {
        this.setContent(new ExerciseView(this));
    }

    public void learn() {
        if (exercise.getTranslates().size() > 0) {
            this.setContent(new CheckView(this));
        }
    }
}
