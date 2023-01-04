package ru.rememberme.model;

public class Translate {

    private final int id;
    private String word;
    private String translate;

    public Translate(int id, String word, String translate) {
        this.id = id;
        this.word = word;
        this.translate = translate;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String toString() {
        return word + " - " + translate;
    }
}
