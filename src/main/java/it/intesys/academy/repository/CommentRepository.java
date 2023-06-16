package it.intesys.academy.repository;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
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
public class CommentRepository {
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;

    public CommentRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
    }
    public  Map<Integer, List<CommentDTO>> getAllComments(List<Integer> issueIds){
        Map<Integer, List<CommentDTO>> messageByIssuetId = new HashMap<>();
        jdbcTemplate.query("SELECT id, comment, author, issueId FROM Comments WHERE issueId in (:issueIds)",
                Map.of("issueIds", issueIds),

                (resultSet) -> {
                    CommentDTO messageDTO = new CommentDTO();

                    messageDTO.setComment(resultSet.getString("comment"));
                    log.info(resultSet.getString("comment"));
                    log.info(messageDTO.getComment());
                    int issueID = resultSet.getInt("issueId");
                    if(Boolean.FALSE.equals(messageByIssuetId.containsKey(issueID))){
                        messageByIssuetId.put(issueID, new ArrayList<>());
                    }
                    messageByIssuetId.get(issueID).add(messageDTO);


                }
        );
        return messageByIssuetId;
    }
    public  List<CommentDTO> getCommentsOfAIssue(Integer issueId){
        List<CommentDTO> comments = jdbcTemplate.query("SELECT id, comment, author, issueId FROM Comments WHERE issueId = :issueId",

                Map.of("issueId", issueId),

                BeanPropertyRowMapper.newInstance(CommentDTO.class));
        return comments;
    }
    public CommentDTO getComment(Integer commentId){
        CommentDTO comment = jdbcTemplate.queryForObject("SELECT id, comment, author, issueId FROM Comments WHERE id = :commentId",

                Map.of("commentId", commentId),

                BeanPropertyRowMapper.newInstance(CommentDTO.class));
        return comment;
    }
    public Integer createComment(CommentDTO commentDTO){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("comment", commentDTO.getComment())
                .addValue("author", commentDTO.getAuthor())
                .addValue("issueId", commentDTO.getIssueId()
                );
        int numberOfInsertedRows = jdbcTemplate.update("INSERT INTO Comments (comment, author, issueId) VALUES (:comment, :author, :issueId)",
                parameterSource, keyHolder
        );


        return keyHolder.getKey().intValue();
    }
    public void updateComment(CommentDTO commentDTO){

        int numberOfInsertedRows = jdbcTemplate.update("UPDATE Comments set comment = :comment, author = :author where issueId = :issueId",
                Map.of("comment", commentDTO.getComment(),
                        "author", commentDTO.getAuthor(),
                        "issueId", commentDTO.getIssueId()
                ));


    }

    public void deleteComment(Integer commentId){
        jdbcTemplate.update("DELETE FROM comments WHERE id = :commentId;", Map.of("commentId", commentId));
    }
}
