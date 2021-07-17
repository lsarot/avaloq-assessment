package com.leonardo.demos.avaloqassessment.service;

import com.leonardo.demos.avaloqassessment.model.dto.Dice;
import com.leonardo.demos.avaloqassessment.model.dto.SimulationStats;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.Roll;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.RollPK;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.Simulation;
import com.leonardo.demos.avaloqassessment.model.persistence.repository.RollRepository;
import com.leonardo.demos.avaloqassessment.model.persistence.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimulatorSvc {
    
    @Autowired SimulationRepository simulationRepository;

    @Autowired RollRepository rollRepository;


    public Map runSimulation(Simulation simulation) {

        simulationRepository.save(simulation);

        Map<Long,Long> results = new TreeMap<>();
        Dice dice = new Dice(simulation.getSides());

        for (int i = 0; i < simulation.getRolls(); i++) {

            int total = 0;
            for (int j = 0; j < simulation.getDice(); j++) {
                total += dice.roll();
            }

            if (results.containsKey(1L*total))
                results.replace(1L*total, results.get(1L*total)+1);
            else
                results.put(1L*total, 1L);
        }

        List<Roll> rolls = new ArrayList<>();
        results.forEach((sum, count) -> {
            Roll roll = new Roll(new RollPK(simulation.getTimestamp(), sum), count);
            rolls.add(roll);
        });

        rollRepository.saveAll(rolls);

        return results;
    }


    public List<SimulationStats> getSimulationsGrouped() {

        List<Simulation> list = simulationRepository.getSimulationGrouped();
        List<SimulationStats> stats = list.stream().map(sim -> new SimulationStats(
                sim.getDice(),
                sim.getSides(),
                sim.getTimestamp(),
                sim.getRolls()))
                .collect(Collectors.toList());
        return stats;
    }


    public List<Simulation> getAllSimulations() {
        return simulationRepository.getAll();
    }


    public List<Roll> getAllRolls() {
        return rollRepository.getAll();
    }


    public Map getRelativeDistributionForCombination(long dice, long sides) {

        long rollsTotalCount = rollRepository.getTotalRollsCountForCombination(sides, dice);

        List<Roll> rolls = rollRepository.getRollsByCombination(sides, dice);

        Map<Long,Float> stats = new HashMap<>();

        rolls.forEach(roll -> {
            stats.put(roll.getRollPK().getRollSum(), 100f*roll.getCount()/rollsTotalCount);
        });

        return stats;
    }
}
