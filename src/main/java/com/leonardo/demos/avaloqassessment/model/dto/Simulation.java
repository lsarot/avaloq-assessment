package com.leonardo.demos.avaloqassessment.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.TreeMap;

public class Simulation {

    @NotNull(message = "Dice can not be null")
    private Dice dice;

    @Min(value = 1, message = "The number of dice must be at least 1")
    private long totalPieces = 1;

    @Min(value = 1, message = "The number of rolls must be at least 1")
    private long totalRolls = 1;

    public Simulation(Dice dice, long totalPieces, long totalRolls) {
        this.dice = dice;
        this.totalPieces = totalPieces;
        this.totalRolls = totalRolls;
    }

    public Dice getDice() {
        return dice;
    }

    public long getTotalPieces() {
        return totalPieces;
    }

    public long getTotalRolls() {
        return totalRolls;
    }

    public Map runSimulation() {

        Map<Integer,Integer> results = new TreeMap<>();

        for (int i = 0; i < totalRolls; i++) {

            int total = 0;
            for (int j = 0; j < totalPieces; j++) {
                total += dice.roll();
            }

            if (results.containsKey(total))
                results.replace(total, results.get(total)+1);
            else
                results.put(total, 1);
        }

        return results;
    }
}
