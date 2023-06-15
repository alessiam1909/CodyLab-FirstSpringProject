package it.intesys.academy.repository;
import it.intesys.academy.dto.UserDTO;
import it.intesys.academy.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;



    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Integer> getUserProjects(String username){

        log.info(username);
        List<Integer> userProjects = new ArrayList<>();

        UserDTO userDTO = new UserDTO();

                jdbcTemplate.query("SELECT id FROM Users WHERE name = :username",
                Map.of("username",  username),
                (ResultSet)-> {
                     userDTO.setId(ResultSet.getInt("id"));
                });
        int userId = userDTO.getId();
        List<Integer> projectIds = jdbcTemplate.queryForList("SELECT projectId FROM User_projects WHERE userId = :userId",
                Map.of("userId", userId), Integer.class);
        log.info(projectIds.toString());
        return projectIds;
    }
    public Integer getUserId(String username){
        UserDTO userDTO = new UserDTO();

        jdbcTemplate.query("SELECT id FROM Users WHERE name = :username",
                Map.of("username",  username),
                (ResultSet)-> {
                    userDTO.setId(ResultSet.getInt("id"));
                });
        return userDTO.getId();
    }
}
