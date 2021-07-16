package com.leonardo.demos.avaloqassessment.controller.rest;

import com.leonardo.demos.avaloqassessment.model.dto.Dice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/dice/v1/dice")
public class DiceController {

    /**
     * Roll 3 pieces of 6-sided dice a total of 100 times.
     * a. For every roll sum the rolled number from the dice (the result will be between 3 and 18).
     * b. Count how many times each total has been rolled.
     * c. Return this as a JSON structure.
     * */
    @GetMapping("/sim-default")
    public ResponseEntity simulationDefault() {

        int totalPieces = 3;
        //int totalSidesPerDice = 6; //is 6 by default!
        int totalRolls = 100;

        Map<Integer,Integer> results = new TreeMap<>();
        Dice dice = new Dice();

        for (int i = 0; i < totalRolls; i++) {

            int total = 0;
            for (int j = 0; j < totalPieces; j++) {
                int rollRes = dice.roll();
                total += rollRes;
            }

            if (results.containsKey(total))
                results.replace(total, results.get(total)+1);
            else
                results.put(total, 1);
        }


        return ResponseEntity.ok(results);
    }

}
