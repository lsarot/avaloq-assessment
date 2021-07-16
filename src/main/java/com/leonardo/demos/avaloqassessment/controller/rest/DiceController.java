package com.leonardo.demos.avaloqassessment.controller.rest;

import com.leonardo.demos.avaloqassessment.model.dto.Dice;
import com.leonardo.demos.avaloqassessment.model.dto.Simulation;
import com.leonardo.demos.avaloqassessment.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/dice/v1/dice")
public class DiceController {


    @Autowired ValidatorService validatorService;


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
                total += dice.roll();
            }

            if (results.containsKey(total))
                results.replace(total, results.get(total)+1);
            else
                results.put(total, 1);
        }


        return ResponseEntity.ok(results);
    }


    /**
     * 2. Make the number of dice, the sides of the dice and the total number of rolls configurable through query parameters.
     * 3. Add input validation:
     *      a. The number of dice and the total number of rolls must be at least 1.
     *      b. The sides of a dice must be at least 4.
     * */
    @GetMapping("/sim-custom")
    public ResponseEntity simulationCustom(
            @RequestParam(required = false, name = "tp") Long totalPieces,
            @RequestParam(required = false, name = "ts") Long totalSidesPerDice,
            @RequestParam(required = false, name = "tr") Long totalRolls) {

        //Defaults if not set
        if (totalPieces == null)
            totalPieces = 1L;
        if (totalSidesPerDice == null)
            totalSidesPerDice = 4L;
        if (totalRolls == null)
            totalRolls = 1L;

        Dice dice = new Dice(totalSidesPerDice);
        Simulation simulation = new Simulation(dice, totalPieces, totalRolls);

        List<String> violationMsgs = validatorService.validate(dice, simulation);
        if (violationMsgs.size() > 0)
            return ResponseEntity.badRequest().body(violationMsgs);

        Map<Integer,Integer> results = simulation.runSimulation();

        return ResponseEntity.ok(results);
    }

}
