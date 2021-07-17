package com.leonardo.demos.avaloqassessment.model.persistence.repository;

import com.leonardo.demos.avaloqassessment.model.persistence.entity.Simulation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimulationRepository extends CrudRepository<Simulation, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM simulation")
    List<Simulation> getAll();


    @Query(nativeQuery = true,
            value = "SELECT count(time_stamp) time_stamp, sum(rolls) rolls, sides, dice\n" +
            "FROM simulation\n" +
            "group by sides,dice\n" +
            "order by sides,dice")
    List<Simulation> getSimulationGrouped();




}
