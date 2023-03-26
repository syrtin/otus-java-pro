package ru.otus.example;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class RoboTest {
    private final Robot robot = new Robot(RoboName.getRandom().toString());

    @Before
    public void chargeRobot() {
        robot.setEnergy(50);
    }

    @Before
    public void assemble() {
        robot.setAssembled(true);
    }

    @Test
    public void fullyChargeTest() {
        robot.fullyCharge();
    }

    @Test
    public void talkSuccessTest() {
        robot.talk();
    }


    @Test
    public void talkAtDisassembleStateFailTest() {
        robot.setAssembled(false);
        robot.talk();
    }


    @Test
    public void moveSuccessTest() {
        robot.move();
    }

    @Test
    public void moveToMuchWithNotEnoughEnergyFailTest() {
        robot.move();
        robot.move();
        robot.move();
    }

    @After
    public void disassembleRobot() {
        robot.setAssembled(false);
    }

    @After
    public void dischargeRobot() {
        robot.setEnergy(0);
    }
}