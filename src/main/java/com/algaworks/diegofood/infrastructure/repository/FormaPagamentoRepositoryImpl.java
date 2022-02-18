package com.algaworks.diegofood.infrastructure.repository;

import com.algaworks.diegofood.domain.model.FormaPagamento;
import com.algaworks.diegofood.domain.repository.FormaPagamentoRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<FormaPagamento> listar() {
        return manager.createQuery("from Forma_pagamento", FormaPagamento.class)
           .getResultList();
    }

    @Override
    public FormaPagamento buscar(Long id) {
        return manager.find(FormaPagamento.class, id);
    }

    @Transactional
    @Override
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return manager.merge(formaPagamento);
    }

    @Transactional
    @Override
    public void remover(FormaPagamento formaPagamento) {

        formaPagamento = buscar(formaPagamento.getId());
        manager.remove(formaPagamento);
    }
}