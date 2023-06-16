package it.intesys.academy.repository;

import it.intesys.academy.dto.CommentDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;


@Repository
public class CommentRepository {


    private final NamedParameterJdbcTemplate jdbcTemplate;


    public CommentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CommentDTO> getComments(Integer id){
        return jdbcTemplate.query("SELECT id, description, author, issueId FROM Comments WHERE issueId = (:issue)",
                Map.of("issue", id),
                BeanPropertyRowMapper.newInstance(CommentDTO.class));
    }


}
