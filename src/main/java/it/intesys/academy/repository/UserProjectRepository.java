package it.intesys.academy.repository;

import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.service.SettingsService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserProjectRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public UserProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService, UserRepository userRepository) {

        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public Optional<UserProjectDTO> usernameProjectVisibility(String username, Integer projectId) {
        int userId = userRepository.getUserId(username);

        try {
            UserProjectDTO project = jdbcTemplate.queryForObject("SELECT id FROM User_Projects where projectId = (:projectId) and userId = (:userId)",

                    Map.of("projectId", projectId, "userId", userId),

                    BeanPropertyRowMapper.newInstance(UserProjectDTO.class));

            return Optional.ofNullable(project);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    public List<Integer> getUserProjects(String username) {

        return jdbcTemplate.queryForList("SELECT projectId FROM User_Projects where username = :username",
                Map.of("username", username),
                Integer.class);
    }

    public void createUserProject(String username, Integer projectId) {

        Map<String, Object> parameterSource = Map.of("username", username, "projectId", projectId);
        jdbcTemplate.update("INSERT INTO User_Projects (username, projectId) VALUES (:username, :projectId)",
                parameterSource
        );

    }

}
