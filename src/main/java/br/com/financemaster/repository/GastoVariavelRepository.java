package br.com.financemaster.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.financemaster.model.GastoVariavel;
import br.com.financemaster.model.Usuario;


@Repository
public interface GastoVariavelRepository extends CrudRepository<GastoVariavel, Long>{
    List<GastoVariavel> findByUsuario(Usuario usuario);
}
