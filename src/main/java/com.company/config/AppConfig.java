package com.company.config;

import com.company.dao.*;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@Configuration
@ComponentScan("com.company")
@EnableWebMvc
public class AppConfig {

    private static final Logger logger = Logger.getLogger(AppConfig.class);

    @Bean
    public EntityManager entityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RwrJpa");
        logger.info("Bean EntityManager created");
        return emf.createEntityManager();
    }

    @Bean
    public CandidateDAO candidateDAO() {
        logger.info("Bean CandidateDAOImpl created");
        return new CandidateDAOImpl();
    }

    @Bean
    public ContactDAO contactDAO() {
        logger.info("Bean ContactDAOImpl created");
        return new ContactDAOImpl();
    }

    @Bean
    public SkillDAO skillDAO() {
        logger.info("Bean SkillDAOImpl created");
        return new SkillDAOImpl();
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(1);
        logger.info("Bean UrlBasedViewResolver created");
        return resolver;
    }
}