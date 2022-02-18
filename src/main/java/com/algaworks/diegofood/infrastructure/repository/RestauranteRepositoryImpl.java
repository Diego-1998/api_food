package com.algaworks.diegofood.infrastructure.repository;

import com.algaworks.diegofood.domain.model.Restaurante;
import com.algaworks.diegofood.domain.repository.RestauranteRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> listarTodos() {
        return manager.createQuery("from Restaurante" , Restaurante.class)
             .getResultList();
    }

    @Override
    public Restaurante buscar(Long id) {
        return manager.find( Restaurante.class,id);
    }

    @Transactional
    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }

    @Transactional
    @Override
    public void remover(Restaurante restaurante) {

        restaurante = buscar(restaurante.getId());
        manager.remove(restaurante);
    }
}