package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

  private final SettingsService settingsService;
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final IssueRepository issueRepository;
  private final CommentRepository commentRepository;
  private final ProjectRepository projectRepository;
    public ProjectService(SettingsService settingsService, NamedParameterJdbcTemplate jdbcTemplate, IssueRepository issueRepository, CommentRepository commentRepository, ProjectRepository projectRepository) {
        this.settingsService=settingsService;
        this.jdbcTemplate=jdbcTemplate;
        this.issueRepository = issueRepository;
        this.commentRepository= commentRepository;
        this.projectRepository= projectRepository;
    }

    /**
     * call function in repository, pass projectId and return a list of issues
     * @param projectId
     * @return
     */
    public List<IssueDTO> getIssuesByProjectId(int projectId) {

        return issueRepository.readIssuesFromProjects(projectId);
    }

    /**
     * call function in repository, pass issueId and return a list of comments
     * @param issueId
     * @return
     */
    public List<CommentDTO> getCommentsByIssueId(int issueId) {

        return commentRepository.readCommentsFromIssues(issueId);
    }

    /**
     * call function in repository, pass projectId and return a list of projects:
     * as the project ID is unique, using list is redundant. in the next version try to use ProjectDTO projectDTO
     * @param projectId
     * @return
     */
    public List<ProjectDTO> getProjectById (int projectId){

        return projectRepository.readProjectById(projectId);
    }


/**
 * code below is a previous task (projects-list) that soon will get reshaped to the new framework
 */
//    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    //=============================================================
   // public List<ProjectDTO> readListProjects(String username) {

      //  List<Integer> projectIds = ProjectRepository.readProjects(username).stream()
      //          .map(ProjectDTO::getId)
      //          .toList();
//====//=======================================
//
      //  Map<Integer, List<IssueDTO>> issuesByProjectId = new HashMap<>();
      //  List<Integer> issueIds= new ArrayList<>();
//
      //  jdbcTemplate.query("SELECT id, name, message, author, projectId FROM Issues WHERE projectId in (:projectIds)",
//
      //          Map.of("projectIds", projectIds),
//
      //          (resultSet) -> {
//
      //              IssueDTO issueDTO = new IssueDTO();
      //              issueIds.add(resultSet.getInt("id"));
      //              issueDTO.setId(resultSet.getInt("id"));
      //              issueDTO.setName(resultSet.getString("name"));
      //              issueDTO.setMessage(resultSet.getString("message"));
      //              issueDTO.setAuthor(resultSet.getString("author"));
//
      //              // building map projectId --> [issue1, issue2, issue3]
      //              int projectId = resultSet.getInt("projectId");
      //              if ( Boolean.FALSE.equals(issuesByProjectId.containsKey(projectId)) ) {
      //                  issuesByProjectId.put(projectId, new ArrayList<>());
      //              }
      //              issuesByProjectId.get(projectId).add(issueDTO);
//
      //          });
      //  //__________________________________________________________________
//
      //  Map<Integer, List<CommentDTO>> commentsByIssuesId = new HashMap<>();
      //  jdbcTemplate.query("SELECT id, comment, author, issueId FROM Comments WHERE issueId in (:issueIds)",
//
      //          Map.of("issueIds", issueIds),
//
      //          (resultSet) -> {
//
      //              CommentDTO commentDTO = new CommentDTO();
      //              commentDTO.setId(resultSet.getInt("id"));
      //              commentDTO.setComment(resultSet.getString("comment"));
      //              commentDTO.setAuthor(resultSet.getString("author"));
//
      //              // building map issueId --> [comment1, comment2, comment3]
//
      //              int issueId = resultSet.getInt("issueId");
      //              if (!commentsByIssuesId.containsKey(issueId)) {
      //                  commentsByIssuesId.put(issueId, new ArrayList<>());
      //              }
//
  //    //         //     commentsByIssuesId.get(issueId).add(commentDTO);
  //    //         // });
////____//_________//________________________________________________
  //    //  for (Pr//ojectDTO dto : projects) {
  //    //      Lis//t<IssueDTO> issueDTOS = issuesByProjectId.get(dto.getId());
  //    //      for// (IssueDTO dto1 : issueDTOS) {
  //    //         // List<CommentDTO> commentDTOS = commentsByIssuesId.get(dto1.getId());
  //    //         // if (commentDTOS != null) {
  //    //         //     commentDTOS.forEach(dto1::addComment);
  //    //         // }
  //    //      }//
  //    //      iss//ueDTOS.forEach(dto::addIssue);
  //    //  }//
  //      return //project//s;
//
  //  }

}

