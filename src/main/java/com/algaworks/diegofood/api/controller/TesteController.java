package com.algaworks.diegofood.api.controller;

import com.algaworks.diegofood.domain.model.Cozinha;
import com.algaworks.diegofood.domain.model.Restaurante;
import com.algaworks.diegofood.domain.repository.CozinhaRepository;
import com.algaworks.diegofood.domain.repository.RestauranteRepository;
import com.algaworks.diegofood.infrastructure.repository.spec.RestauranteComFreteGratisSpec;
import com.algaworks.diegofood.infrastructure.repository.spec.RestauranteComNomeSemelhanteSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(String nome){
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/unico-por-nome")
    public Optional<Cozinha> cozinhaPorNome(String nome){
        return cozinhaRepository.findByNome(nome);
    }

    @GetMapping("/cozinhas/exists")
    public  boolean cozinhaExists(String nome){
        return cozinhaRepository.existsByNome(nome);
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.queryByTaxaFreteBetween(taxaInicial,taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantesPorTaxaFrete(String nome, Long cozinhaId) {
        return restauranteRepository.consultarPorNome(nome,cozinhaId);
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> restaurantesPrimeiroPorNome(String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/top2-por-nome")
    public List<Restaurante> restaurantesTop2PorNome(String nome) {
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/por-nome-e-frete")
    public List<Restaurante> restaurantesPorNomeFrete(String nome,
           BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/restaurantes/count-por-nome")
    public int cozinhaTotal(Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(@RequestParam String nome) {

      var comFreteGratis =  new RestauranteComFreteGratisSpec();
      var comNomeSemelhante =  new RestauranteComNomeSemelhanteSpec(nome);

      return restauranteRepository.findAll(comFreteGratis.and(comNomeSemelhante));

    }

}
