package com.leonardo.demos.avaloqassessment.controller.rest;

import com.leonardo.demos.avaloqassessment.model.dto.Dice;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.Roll;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.Simulation;
import com.leonardo.demos.avaloqassessment.model.persistence.repository.RollRepository;
import com.leonardo.demos.avaloqassessment.model.persistence.repository.SimulationRepository;
import com.leonardo.demos.avaloqassessment.service.SimulatorSvc;
import com.leonardo.demos.avaloqassessment.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dice/v1/simulations")
public class SimulationController {


    @Autowired ValidatorService validatorService;

    @Autowired SimulatorSvc simulatorSvc;


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
            @RequestParam(required = false, name = "dn") Long totalPieces,
            @RequestParam(required = false, name = "ds") Long totalSidesPerDice,
            @RequestParam(required = false, name = "tr") Long totalRolls) {

        //Defaults if not set
        if (totalPieces == null)
            totalPieces = 1L;
        if (totalSidesPerDice == null)
            totalSidesPerDice = 4L;
        if (totalRolls == null)
            totalRolls = 1L;

        Dice dice = new Dice(totalSidesPerDice);
        Simulation simulation = new Simulation(
                Instant.now().toEpochMilli(),
                totalSidesPerDice,
                totalPieces,
                totalRolls);

        List<String> violationMsgs = validatorService.validate(dice, simulation);
        if (violationMsgs.size() > 0)
            return ResponseEntity.badRequest().body(violationMsgs);

        Map<Integer,Integer> results = simulatorSvc.runSimulation(simulation);

        return ResponseEntity.ok(results);
    }


    /**
     * 1. Return the total number of simulations and total rolls made,
     * grouped by all existing dice number–dice side combinations.
     * a. Eg. if there were two calls to the REST endpoint for 3 pieces of 6 sided dice, once with a total number of
     * rolls of 100 and once with a total number of rolls of 200, then there were a total of 2 simulations, with a
     * total of 300 rolls for this combination.
     */
    @GetMapping("/stats-grouped")
    public ResponseEntity getSimulationsGrouped() {

        return ResponseEntity.ok(simulatorSvc.getSimulationsGrouped());
    }


    /**
     * 2. For a given dice number–dice side combination, return the relative distribution,
     * compared to the total rolls, for all the simulations.
     * In case of a total of 300 rolls,
     * a. If the sum „3” was rolled 4 times, that would be 1.33%.
     * b. If the sum „4” was rolled 3 times, that would be 1%.
     * c. If the total „5” was rolled 11 times, that would be 3.66%. Etc...
     * */
    public ResponseEntity getRelativeDistribution(
            @RequestParam(required = false, name = "dn") Long totalPieces,
            @RequestParam(required = false, name = "ds") Long totalSidesPerDice
    ) {

        return ResponseEntity.ok(
                simulatorSvc.getRelativeDistributionForCombination(
                        totalPieces, totalSidesPerDice));
    }



    @GetMapping("/all")
    public ResponseEntity getSimulations() {

        List<Simulation> list = simulatorSvc.getAllSimulations();
        return ResponseEntity.ok(list);
    }


    @GetMapping("/rolls/all")
    public ResponseEntity getRolls() {

        List<Roll> list = simulatorSvc.getAllRolls();
        return ResponseEntity.ok(list);
    }

}
