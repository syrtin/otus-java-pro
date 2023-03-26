package ru.otus.example;

public enum RoboSpeech {
    SAY_HI("Hello, leather bag"),
    HOW_R_YA("01110101 01100110 01010010 10010101 10000101"),
    I_AM_COOL("I'm made of metal, My circuits gleam"),
    ATTENTION("Beep-beep"),
    ARNOLD("I'll be back");

    private final String text;


    RoboSpeech(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static RoboSpeech getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}