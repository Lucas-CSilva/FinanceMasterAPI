package br.com.financemaster.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.financemaster.model.GastoFixo;
import br.com.financemaster.model.Usuario;

@Repository
public interface GastoFixoRepository extends CrudRepository<GastoFixo, Long>{
    List<GastoFixo> findByUsuario(Usuario usuario);
}
