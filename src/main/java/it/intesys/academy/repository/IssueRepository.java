package it.intesys.academy.repository;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class IssueRepository {
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private static NamedParameterJdbcTemplate jdbcTemplate;

    private static SettingsService settingsService;

    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
    }
    public static Map<Integer, List<IssueDTO>> getAllIssues(List<Integer> projectIds ){
        Map<Integer, List<IssueDTO>> issuesByProjectId = new HashMap<>();

        jdbcTemplate.query("SELECT id, name, message, author, projectId FROM Issues WHERE projectId in (:projectIds)",

                Map.of("projectIds", projectIds),

                (resultSet) -> {

                    IssueDTO issueDTO = new IssueDTO();
                    issueDTO.setId(resultSet.getInt("id"));
                    issueDTO.setName(resultSet.getString("name"));
                    issueDTO.setMessage(resultSet.getString("message"));
                    issueDTO.setAuthor(resultSet.getString("author"));

                    // building map projectId --> [issue1, issue2, issue3]
                    int projectId = resultSet.getInt("projectId");
                    if ( Boolean.FALSE.equals(issuesByProjectId.containsKey(projectId)) ) {
                        issuesByProjectId.put(projectId, new ArrayList<>());
                    }
                    issuesByProjectId.get(projectId).add(issueDTO);
                });

        return issuesByProjectId;
    }
    public static List<IssueDTO> getIssuesOfAProject(Integer projectId){
        List<IssueDTO> issues = jdbcTemplate.query("SELECT id, name, message, author, projectId FROM Issues WHERE projectId = :projectIds",

                Map.of("projectId", projectId),

                BeanPropertyRowMapper.newInstance(IssueDTO.class));

        List<Integer> issueIds = new ArrayList<>();
        for(IssueDTO issue : issues){
            issueIds.add(issue.getId());
        }
        Map<Integer, List<CommentDTO>> messageByIssuetId = CommentRepository.getAllComments(issueIds);

        for (IssueDTO issue : issues){
            List<CommentDTO> messageDTOS = messageByIssuetId.get(issue.getId());
            for (CommentDTO message : messageDTOS) {
                issue.setComment(message);
            }
        }

        return issues;

    }
}
