package ru.rememberme.model;

import java.util.ArrayList;
import java.util.Collections;

public class Exercise {

    private final int id;
    private String name;
    private ArrayList<Translate> translates;

    public Exercise(int id, String name) {
        this.id = id;
        this.name = name;
        translates = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Translate> getTranslates() {
        return translates;
    }

    public void addTranslate(Translate translate) {
        this.translates.add(translate);
    }

    public Translate getTranslate(int index) {
        return this.translates.get(index);
    }

    public void removeTranslate(Translate translate) {
        this.translates.remove(translate);
    }

    public ArrayList<Translate> shuffle() {
        ArrayList<Translate> result = new ArrayList<>(translates);
        Collections.shuffle(result);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
