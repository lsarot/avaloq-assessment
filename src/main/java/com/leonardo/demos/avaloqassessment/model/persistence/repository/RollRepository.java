package com.leonardo.demos.avaloqassessment.model.persistence.repository;

import com.leonardo.demos.avaloqassessment.model.persistence.entity.Roll;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.Simulation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RollRepository extends CrudRepository<Roll, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM roll")
    List<Roll> getAll();


    @Query(nativeQuery = true,
            value = "SELECT 0 time_stamp, r.sum, sum(r.count) count\n" +
                    "from roll r\n" +
                    "where time_stamp in\n" +
                    "(select time_stamp from simulation where sides=?1 and dice=?2)\n" +
                    "group by r.sum\n" +
                    "order by r.sum")
    List<Roll> getRollsByCombination(Long sides, Long dice);


    @Query(nativeQuery = true,
            value = "select sum(r.count) total \n" +
                    "from roll r\n" +
                    "where time_stamp in \n" +
                    "(select time_stamp from simulation where sides=?1 and dice=?2)")
    Integer getTotalRollsCountForCombination(Long sides, Long dice);

}
