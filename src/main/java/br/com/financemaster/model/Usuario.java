package br.com.financemaster.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import br.com.financemaster.model.systemenums.UserRoleEnum;

@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nome;
    private String login;
    private String password;
    private UserRoleEnum role;
    
    @JsonIgnore
    @OneToMany (fetch = FetchType.EAGER)
    private List<Cartao> cartoes = new ArrayList<Cartao>();

    @JsonIgnore
    @OneToMany (fetch = FetchType.EAGER)
    private List<ContaBancaria> contas = new ArrayList<ContaBancaria>();

    @JsonIgnore
    @OneToMany (fetch = FetchType.EAGER)
    private List<GastoFixo> gastosFixos = new ArrayList<GastoFixo>();

    @JsonIgnore
    @OneToMany (fetch = FetchType.EAGER)
    private List<GastoVariavel> gastosVariaveis = new ArrayList<GastoVariavel>();

    @JsonIgnore
    @OneToMany (fetch = FetchType.EAGER)
    private List<RendaFixa> rendasFixas = new ArrayList<RendaFixa>();

    @JsonIgnore
    @OneToMany (fetch = FetchType.EAGER)
    private List<RendaVariavel> rendasVariaveis = new ArrayList<RendaVariavel>();

    // @JsonIgnore
    // @OneToMany (fetch = FetchType.EAGER)
    // private List<Historico> historicos;

    public Usuario()
    {

    }

    public Usuario(String login, String senha, UserRoleEnum role)
    {
        this.login = login;
        this.password = senha;
        this.role = role;
    }

    public Usuario(String login, String senha, String nome, UserRoleEnum role)
    {
        this.login = login;
        this.password = senha;
        this.nome = nome;
        this.role = role;
    }
    public UserRoleEnum getRole() {
        return role;
    }
    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.role == UserRoleEnum.USER){
            return List.of(            
                new SimpleGrantedAuthority("ROLE_USER")    
                );
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.getLogin();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public List<ContaBancaria> getContas() {
        return contas;
    }

    public void setContas(List<ContaBancaria> contas) {
        this.contas = contas;
    }

    public List<GastoFixo> getGastosFixos() {
        return gastosFixos;
    }

    public void setGastosFixos(List<GastoFixo> gastosFixos) {
        this.gastosFixos = gastosFixos;
    }

    public List<GastoVariavel> getGastosVariaveis() {
        return gastosVariaveis;
    }

    public void setGastosVariaveis(List<GastoVariavel> gastosVariaveis) {
        this.gastosVariaveis = gastosVariaveis;
    }

    public List<RendaFixa> getRendasFixas() {
        return rendasFixas;
    }

    public void setRendasFixas(List<RendaFixa> rendasFixas) {
        this.rendasFixas = rendasFixas;
    }

    public List<RendaVariavel> getRendasVariaveis() {
        return rendasVariaveis;
    }

    public void setRendasVariaveis(List<RendaVariavel> rendasVariaveis) {
        this.rendasVariaveis = rendasVariaveis;
    }
}
