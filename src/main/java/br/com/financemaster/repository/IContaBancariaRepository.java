package br.com.financemaster.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.financemaster.model.ContaBancaria;
import br.com.financemaster.model.Usuario;

import java.util.List;

@Repository
public interface IContaBancariaRepository extends CrudRepository<ContaBancaria, Long>{
    List<ContaBancaria> findByUsuario(Usuario usuario);
}
