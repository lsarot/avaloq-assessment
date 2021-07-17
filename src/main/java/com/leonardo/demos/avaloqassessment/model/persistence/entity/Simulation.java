package com.leonardo.demos.avaloqassessment.model.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity(name = "simulation")
//@Table(name = "simulation")
public class Simulation {

    @Id
    @Column(name = "time_stamp")
    private Long timestamp;

    @Min(value = 4, message = "Sides of a dice must be at least 4")
    private Long sides = 6L;

    @Min(value = 1, message = "The number of dice must be at least 1")
    private Long dice = 1L;

    @Min(value = 1, message = "The number of rolls must be at least 1")
    private Long rolls = 1L;

    public Simulation() {
    }

    public Simulation(Long timestamp, Long sides, Long dice, Long rolls) {
        this.timestamp = timestamp;
        this.sides = sides;
        this.dice = dice;
        this.rolls = rolls;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getSides() {
        return sides;
    }

    public Long getDice() {
        return dice;
    }

    public Long getRolls() {
        return rolls;
    }
}
