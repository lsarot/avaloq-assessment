package com.leonardo.demos.avaloqassessment.model.persistence.dao;

import com.leonardo.demos.avaloqassessment.model.persistence.entity.Simulation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimulationDao extends CrudRepository<Simulation, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM simulation")
    List<Simulation> getAll();


    /**
     * This query could be DAO domain, but:
     * NOT RETRIEVING WHAT WE HAVE ON H2-CONSOLE WITH SAME QUERY
     * so we retrieve data on Repository using pure JDBC
    * */
    /*@Query(nativeQuery = true,
            value = "SELECT count(time_stamp) time_stamp, sum(rolls) rolls, sides, dice\n" +
            "FROM simulation\n" +
            "group by sides,dice\n" +
            "order by sides,dice")
    List<Simulation> getSimulationGrouped();*/




}
