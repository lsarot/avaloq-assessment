package com.leonardo.demos.avaloqassessment.controller.rest;

import com.leonardo.demos.avaloqassessment.service.SimulatorSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dice/v1/stats")
public class StatsController {

    private SimulatorSvc simulatorSvc;


    public StatsController(SimulatorSvc simulatorSvc) {
        this.simulatorSvc = simulatorSvc;
    }


    /**
     * 1. Return the total number of simulations and total rolls made,
     * grouped by all existing dice number–dice side combinations.
     * a. Eg. if there were two calls to the REST endpoint for 3 pieces of 6 sided dice,
     * once with a total number of rolls of 100 and
     * once with a total number of rolls of 200,
     * then there were a total of 2 simulations, with a total of 300 rolls for this combination.
     */
    @GetMapping("/grouped")
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
    @GetMapping("/distribution")
    public ResponseEntity getRelativeDistribution(
            @RequestParam(required = false, name = "dn") Long totalPieces,
            @RequestParam(required = false, name = "ds") Long totalSidesPerDice
    ) {

        return ResponseEntity.ok(
                simulatorSvc.getRelativeDistributionForCombination(
                        totalPieces, totalSidesPerDice));
    }
}
