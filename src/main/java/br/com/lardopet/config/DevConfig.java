package br.com.lardopet.config;

import br.com.lardopet.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

/**
 * Configura ambiente de dev
 */
@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    /**
     * Popula banco de dados. (Em ambiente de dev, popula o banco MySql)
     *
     */
    @Bean
    public boolean instantiateDataBase() throws ParseException {
        if(!"create".equals(strategy)) {
            return false;
        }
        dbService.instantiateDataBase();
        return true;
    }
}
