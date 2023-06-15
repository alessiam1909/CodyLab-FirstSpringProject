package it.intesys.academy.service;

import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.repository.UserProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService {

    private static final Logger log = LoggerFactory.getLogger(SettingsService.class);

    private final UserProjectRepository userProjectRepository;

    public SettingsService(UserProjectRepository userProjectRepository) {
        this.userProjectRepository = userProjectRepository;
    }

    public List<Integer> getUserProjects(String username) {
        List<UserProjectDTO> users = userProjectRepository.getUser(username);
        List<Integer> projects = new ArrayList<>();
        if(users!=null){
            for(UserProjectDTO project: users){
                projects.add(project.getProjectId());
            }
        }else{
            throw new RuntimeException("Ooops this username was not found!");
        }
        log.info("ok");
        return projects;
    }


    public boolean canThisUserReadThisProject(String username, int projectId) {

        return ! userProjectRepository.usernameProjectVisibility(username, projectId).isEmpty();

    }

}