package br.com.financemaster.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
// @DiscriminatorValue("RendaVariavel")
public class RendaVariavel extends Renda{

    @ManyToOne (fetch = FetchType.EAGER)
    private Usuario usuario;

    private LocalDate dataInicio;
    private LocalDate dataFinal;

    private String recorrencia;
    
    public String getRecorrencia() {
        return recorrencia;
    }
    public void setRecorrencia(String recorrencia) {
        this.recorrencia = recorrencia;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    public LocalDate getDataFinal() {
        return dataFinal;
    }
    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }
    

}
