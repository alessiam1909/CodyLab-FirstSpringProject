package it.intesys.academy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfiguration {

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate (DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
