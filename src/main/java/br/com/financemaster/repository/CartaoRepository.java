package br.com.financemaster.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.financemaster.model.Cartao;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, Long>{

}
