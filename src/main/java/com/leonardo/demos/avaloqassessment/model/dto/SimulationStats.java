package com.leonardo.demos.avaloqassessment.model.dto;

import java.io.Serializable;

public class SimulationStats implements Serializable {

    private static final long serialVersionUID = 1L;

    private long dice;
    private long sides;
    private long totalNbrSimulations;
    private long totalRollsMade;

    public SimulationStats() {
    }

    public SimulationStats(long dice, long sides, long totalNbrSimulations, long totalRollsMade) {
        this.dice = dice;
        this.sides = sides;
        this.totalNbrSimulations = totalNbrSimulations;
        this.totalRollsMade = totalRollsMade;
    }

    public long getDice() {
        return dice;
    }

    public void setDice(long dice) {
        this.dice = dice;
    }

    public long getSides() {
        return sides;
    }

    public void setSides(long sides) {
        this.sides = sides;
    }

    public long getTotalNbrSimulations() {
        return totalNbrSimulations;
    }

    public void setTotalNbrSimulations(long totalNbrSimulations) {
        this.totalNbrSimulations = totalNbrSimulations;
    }

    public long getTotalRollsMade() {
        return totalRollsMade;
    }

    public void setTotalRollsMade(long totalRollsMade) {
        this.totalRollsMade = totalRollsMade;
    }
}
