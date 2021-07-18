package com.leonardo.demos.avaloqassessment.model.persistence.repository;

import com.leonardo.demos.avaloqassessment.model.dto.stats.RelativeDistribution;
import com.leonardo.demos.avaloqassessment.model.dto.stats.SimulationsGrouped;
import com.leonardo.demos.avaloqassessment.model.persistence.dao.RollDao;
import com.leonardo.demos.avaloqassessment.service.JdbcService;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SimulationsRepository {

    private JdbcService jdbcService;

    private RollDao rollDao;


    public SimulationsRepository(JdbcService jdbcService, RollDao rollDao) {
        this.jdbcService = jdbcService;
        this.rollDao = rollDao;
    }


    public List getSimulationsGrouped() {

        String query = "SELECT " +
                "count(time_stamp) total_sim, sum(rolls) rolls, sides, dice " +
                "FROM simulation " +
                "group by sides,dice " +
                "order by sides,dice";

        return jdbcService.executeSelectQuery(query, mapperSimulationsGrouped, null);
    }

    private JdbcService.Mapper mapperSimulationsGrouped = (rs, args) -> {
        List<SimulationsGrouped> stats = new ArrayList<>();
        SimulationsGrouped stat;
        while (rs.next()) {
            stat = new SimulationsGrouped(
                    rs.getLong("dice"),
                    rs.getLong("sides"),
                    rs.getLong("total_sim"),
                    rs.getLong("rolls")
            );
            stats.add(stat);
        }
        return stats;
    };


    public List getRollsByCombination(Long dice, Long sides) {

        Long rollsTotalCount = rollDao.getTotalRollsCountForCombination(sides, dice);

        if (rollsTotalCount == null)
            return Collections.emptyList();

        String query = "select r.roll_sum, sum(r.qty) count " +
                "from roll r " +
                "where time_stamp in " +
                "(select time_stamp from simulation where sides="+sides+" and dice="+dice+") " +
                "group by r.roll_sum " +
                "order by r.roll_sum";

        return jdbcService.executeSelectQuery(query, mapperRollsByCombination, rollsTotalCount);
    }

    private JdbcService.Mapper mapperRollsByCombination = (rs, args) -> {
        List<RelativeDistribution> stats = new ArrayList<>();
        RelativeDistribution stat;
        long rollsTotalCount = (long) Arrays.stream(args).toArray()[0];

        while (rs.next()) {
            stat = new RelativeDistribution(
                    rs.getLong("roll_sum"),
                    rs.getLong("count"),
                    100f*rs.getLong("count")/rollsTotalCount
            );
            stats.add(stat);
        }
        return stats;
    };


}
