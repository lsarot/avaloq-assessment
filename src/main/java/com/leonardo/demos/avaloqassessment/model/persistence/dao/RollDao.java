package com.leonardo.demos.avaloqassessment.model.persistence.dao;

import com.leonardo.demos.avaloqassessment.model.persistence.entity.Roll;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RollDao extends CrudRepository<Roll, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM roll")
    List<Roll> getAll();


    @Query(nativeQuery = true,
            value = "select sum(r.qty) total \n" +
                    "from roll r\n" +
                    "where time_stamp in \n" +
                    "(select time_stamp from simulation where sides=?1 and dice=?2)")
    Long getTotalRollsCountForCombination(Long sides, Long dice);

}
