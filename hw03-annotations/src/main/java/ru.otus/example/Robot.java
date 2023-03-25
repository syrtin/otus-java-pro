package ru.otus.example;

import lombok.Getter;
import lombok.Setter;
import ru.otus.exception.TestFailException;

@Getter
@Setter
public class Robot {
    private String name;
    private int energy;
    private boolean isAssembled;

    private static final String NOT_ENOUGH_ENERGY = "Not Enough energy, Please charge the Robot.";
    private static final String ASSEMBLE_NEEDED = "Robot needs to be assembled. Please RTFM.";

    public Robot(String name) {
        this.name = name;
    }

    public void talk() {
        if (!isAssembled())
            throw new TestFailException(ASSEMBLE_NEEDED);
        if (energy >= 25) {
            System.out.printf("%s says: %s", getName(), RoboSpeech.getRandom().toString());
            System.out.println();
            energy =-25;
        } else throw new TestFailException(NOT_ENOUGH_ENERGY);
    }

    public void move() {
        if (!isAssembled())
            throw new TestFailException(ASSEMBLE_NEEDED);
        if (energy >= 50) {
            System.out.printf("%s moves", getName());
            System.out.println();

            energy =-50;
        } else throw new TestFailException(NOT_ENOUGH_ENERGY);
    }

    public void fullyCharge() {
        setEnergy(100);
    }
}