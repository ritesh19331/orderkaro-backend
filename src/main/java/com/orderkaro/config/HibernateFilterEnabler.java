package com.orderkaro.config;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class HibernateFilterEnabler {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void enableFilter() {
        entityManager
            .unwrap(Session.class)
            .enableFilter("softDeleteFilter")
            .setParameter("isDeleted", false);
    }
}