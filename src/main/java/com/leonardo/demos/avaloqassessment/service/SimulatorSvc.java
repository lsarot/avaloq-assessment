package com.leonardo.demos.avaloqassessment.service;

import com.leonardo.demos.avaloqassessment.model.dto.Dice;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.Roll;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.RollPK;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.Simulation;
import com.leonardo.demos.avaloqassessment.model.persistence.dao.RollDao;
import com.leonardo.demos.avaloqassessment.model.persistence.dao.SimulationDao;
import com.leonardo.demos.avaloqassessment.model.persistence.repository.SimulationsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SimulatorSvc {

    private SimulationsRepository simulationsRepository;

    private SimulationDao simulationDao;

    private RollDao rollDao;


    public SimulatorSvc(SimulationsRepository simulationsRepository, SimulationDao simulationDao, RollDao rollDao) {
        this.simulationsRepository = simulationsRepository;
        this.simulationDao = simulationDao;
        this.rollDao = rollDao;
    }


    public Map runSimulation(Simulation simulation) {

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

        saveSimulationAndRolls(simulation, rolls);

        return results;
    }


    @Transactional
    private void saveSimulationAndRolls(Simulation sim, List<Roll> rolls) {
        simulationDao.save(sim);
        rollDao.saveAll(rolls);
    }


    public List<Simulation> getAllSimulations() {
        return simulationDao.getAll(); //also simulationDao.findAll()
    }


    public List<Roll> getAllRolls() {
        return rollDao.getAll();
    }


    public List getSimulationsGrouped() {

        return simulationsRepository.getSimulationsGrouped();
    }


    public List getRelativeDistributionForCombination(long dice, long sides) {

        return simulationsRepository.getRollsByCombination(dice, sides);
    }
}
