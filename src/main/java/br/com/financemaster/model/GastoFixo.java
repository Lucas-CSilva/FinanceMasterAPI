package br.com.financemaster.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;


@Entity
// @DiscriminatorValue("GastoFixo")
public class GastoFixo extends Gasto {
    
    private LocalDate dataCompetencia;

    @ManyToOne (fetch = FetchType.EAGER)
    private Usuario usuario;

    public LocalDate getDataCompetencia() {
        return dataCompetencia;
    }

    public void setDataCompetencia(LocalDate dataCompetencia) {
        this.dataCompetencia = dataCompetencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }   
}
