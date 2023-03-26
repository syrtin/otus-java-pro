package ru.otus.example;

public enum RoboName {
    R2_D2("R2D2"),
    BENDER("Bender"),
    OPTIMUS_PRIME("Optimus Prime"),
    WALL_E("Wall-E"),
    T1000("T-1000");

    private final String name;

    RoboName(final String text) {
        this.name = text;
    }

    @Override
    public String toString() {
        return name;
    }

    public static RoboName getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}