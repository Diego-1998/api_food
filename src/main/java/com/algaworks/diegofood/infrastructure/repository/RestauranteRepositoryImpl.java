package com.algaworks.diegofood.infrastructure.repository;

import com.algaworks.diegofood.domain.model.Restaurante;
import com.algaworks.diegofood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome,
              BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteriaQuery = builder.createQuery(Restaurante.class);
        Root<Restaurante> root =  criteriaQuery.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteFinal != null) {
              predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
             predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        var query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();

    }
}
