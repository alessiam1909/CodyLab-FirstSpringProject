package it.intesys.academy.repository;

import it.intesys.academy.dto.UserProjectDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserProjectRepository {


    private final NamedParameterJdbcTemplate jdbcTemplate;


    public UserProjectRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserProjectDTO> getUser(String username){
        return jdbcTemplate.query("SELECT id, username, projectId FROM USER_PROJECTS where username = :username",

                Map.of("username", username),

                BeanPropertyRowMapper.newInstance(UserProjectDTO.class));
    }
}