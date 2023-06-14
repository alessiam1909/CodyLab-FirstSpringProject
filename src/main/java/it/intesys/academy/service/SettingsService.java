package it.intesys.academy.service;

import it.intesys.academy.IssueTracker;
import it.intesys.academy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class SettingsService {

    private final DataSource dataSource;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(IssueTracker.class);

    public SettingsService(DataSource dataSource, UserRepository userRepository) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
    }

    public List<Integer> getUserProjects(String username) {
        log.info(username);

        return userRepository.getUserProjects(username);
    }
}
