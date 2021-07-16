package com.leonardo.demos.avaloqassessment.model.dto;

import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    private int sides = 6;
    private int lastRoll;

    public Dice() {
    }

    public Dice(int sides) {
        this.sides = sides;
    }

    public int roll() {
        this.lastRoll = ThreadLocalRandom.current().nextInt(1, sides+1);
        return this.lastRoll;
    }

    public int getLastRoll() {
        if (lastRoll == 0)
            throw new IllegalStateException("Must call roll method before");
        return lastRoll;
    }
}
