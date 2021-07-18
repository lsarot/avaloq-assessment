package com.leonardo.demos.avaloqassessment.controller.rest;

import com.leonardo.demos.avaloqassessment.model.persistence.entity.Roll;
import com.leonardo.demos.avaloqassessment.service.SimulatorSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dice/v1/rolls")
public class RollController {

    private SimulatorSvc simulatorSvc;


    public RollController(SimulatorSvc simulatorSvc) {
        this.simulatorSvc = simulatorSvc;
    }


    @GetMapping("/all")
    public ResponseEntity getRolls() {

        List<Roll> list = simulatorSvc.getAllRolls();
        return ResponseEntity.ok(list);
    }

}
