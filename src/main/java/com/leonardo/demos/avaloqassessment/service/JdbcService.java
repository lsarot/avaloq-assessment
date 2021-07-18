package com.leonardo.demos.avaloqassessment.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

@Service
public class JdbcService {

    private DataSource dataSource;


    public JdbcService(@Qualifier("MyDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public interface Mapper {
        Object mapResultSetToObjectList(ResultSet rs, Object... args) throws SQLException;
    }


    public <T> T executeSelectQuery(String query, Mapper mapper, Object... args) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery()
        ) {
            return (T) mapper.mapResultSetToObjectList(rs, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (T) Collections.emptyList();
    }

}
