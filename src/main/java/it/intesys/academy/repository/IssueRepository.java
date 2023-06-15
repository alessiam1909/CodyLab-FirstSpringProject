package it.intesys.academy.repository;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class IssueRepository {
    private  final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;
    private final CommentRepository commentRepository;

    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService, CommentRepository commentRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
        this.commentRepository = commentRepository;
    }
    public  Map<Integer, List<IssueDTO>> getAllIssues(List<Integer> projectIds ){
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
    public  List<IssueDTO> getIssuesOfAProject(Integer projectId){
        List<IssueDTO> issues = jdbcTemplate.query("SELECT id, name, message, author, projectId FROM Issues WHERE projectId = :projectId",
                Map.of("projectId", projectId),
                BeanPropertyRowMapper.newInstance(IssueDTO.class));

        return issues;

    }
    public IssueDTO getIssue(Integer issueId){
        IssueDTO issue = jdbcTemplate.queryForObject("SELECT id, name, message, author, projectId FROM Issues where id = :issueId",

                Map.of("issueId", issueId),

                BeanPropertyRowMapper.newInstance(IssueDTO.class));

        log.info(issue.toString());

        return issue;
    }
    public Integer createIssue(IssueDTO issueDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", issueDTO.getName())
                .addValue("message", issueDTO.getMessage())
                .addValue("projectId", issueDTO.getProjectId())
                .addValue("author", issueDTO.getAuthor()
                );
        int numberOfInsertedRows = jdbcTemplate.update("INSERT INTO Issues (name, message, projectId, author) VALUES (:name, :message, :projectId, :author)",
                parameterSource, keyHolder
        );


        return keyHolder.getKey().intValue();
    }
    public void updateIssue(IssueDTO issueDTO) {
        jdbcTemplate.update("update Issues set name = :name, message = :message , author = :author where id = :issueId",
                Map.of("name", issueDTO.getName(),
                        "message", issueDTO.getMessage(),
                        "issueId", issueDTO.getId(),
                        "author", issueDTO.getAuthor()
                ));
    }
}
