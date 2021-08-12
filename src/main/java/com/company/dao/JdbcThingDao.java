package com.company.dao;

import com.company.model.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcThingDao implements ThingDao {

    private final String getAllSql = "select * from thing order by thing_id";
    private final String getOneSql = "select * from thing where thing_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final String updateOneSql = "update thing set name=?, avatar=?  where thing_id=?";
    private final String addOneSql = "insert into thing(name, avatar) values(?,?)";
    private final String deleteOneSql = "delete from thing where thing_id=?";

    @Autowired
    public JdbcThingDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Thing getThing(Long id) {
        try {
            return jdbcTemplate.queryForObject(getOneSql, new ThingRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Thing> getAllThings() {
        return jdbcTemplate.query(getAllSql, new ThingRowMapper());
    }

    @Override
    public void updateThing(Thing updatedThing) throws IOException {
        jdbcTemplate.update(updateOneSql, updatedThing.getName(), updatedThing.getAvatar(), updatedThing.getId());
    }

    @Override
    public void addThing(Thing thing) {
        jdbcTemplate.update(addOneSql, thing.getName(), thing.getAvatar());
    }

    @Override
    public void deleteThing(Long id) {
        jdbcTemplate.update(deleteOneSql, id);
    }

    class ThingRowMapper implements RowMapper<Thing> {
        @Override
        public Thing mapRow(ResultSet resultSet, int i) {
            Thing d = new Thing();
            try {
                d.setName(resultSet.getString("name"));
                d.setId(resultSet.getLong("thing_id"));
                d.setAvatar(resultSet.getBytes("avatar"));
                return d;
            } catch (SQLException e) {
                return null;
            }
        }
    }

}


