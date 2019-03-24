package br.com.bonnepet.config;

import br.com.bonnepet.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configura ambiente de teste
 */
@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    /**
     * Popula banco de dados. (Em ambiente de teste, popula o banco em memoria H2)
     *
     */
    @Bean
    public boolean instantiateDataBase() {
        dbService.instantiateDataBase();
        return true;
    }
}