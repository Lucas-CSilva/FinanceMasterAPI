package br.com.financemaster.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.financemaster.model.RendaVariavel;
import br.com.financemaster.model.Usuario;

@Repository
public interface IRendaVariavelRepository extends CrudRepository<RendaVariavel, Long>{
    List<RendaVariavel> findByUsuario(Usuario usuario);
}
