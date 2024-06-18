package br.com.financemaster.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.financemaster.model.RendaFixa;
import br.com.financemaster.model.Usuario;

@Repository
public interface IRendaFixaRepository extends CrudRepository<RendaFixa, Long>{
    List<RendaFixa> findByUsuario(Usuario usuario);
}
