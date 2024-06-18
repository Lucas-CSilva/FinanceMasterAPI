package br.com.financemaster.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ContaBancaria {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    private Usuario usuario;

    @JsonIgnore
    @OneToMany (fetch = FetchType.EAGER)
    private List<Cartao> cartoes = new ArrayList<Cartao>();

    private String nome;
    private String banco;
    private String numero;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public List<Cartao> getCartoes() {
        return cartoes;
    }
    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getBanco() {
        return banco;
    }
    public void setBanco(String banco) {
        this.banco = banco;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
}
