package com.leonardo.demos.avaloqassessment.model.dto;

import javax.validation.constraints.Min;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    @Min(value = 4, message = "Sides of a dice must be at least 4")
    private long sides = 6;

    private long lastRoll;

    public Dice() {
    }

    public Dice(long sides) {
        this.sides = sides;
    }

    public long roll() {
        this.lastRoll = ThreadLocalRandom.current().nextLong(1, sides+1);
        return this.lastRoll;
    }

    public long getLastRoll() {
        if (lastRoll == 0)
            throw new IllegalStateException("Must call roll method before");
        return lastRoll;
    }
}
