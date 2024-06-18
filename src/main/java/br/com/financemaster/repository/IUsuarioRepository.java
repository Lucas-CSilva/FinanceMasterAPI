package br.com.financemaster.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.financemaster.model.Usuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Long>
{
    @Query("select u from Usuario u where u.login = ?1")
    Usuario findByLogin(String login);
}
