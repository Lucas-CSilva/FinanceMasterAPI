package br.com.financemaster.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
// @DiscriminatorValue("RendaFixa")
public class RendaFixa extends Renda{
 
    private LocalDate dataCompetencia;

    @ManyToOne (fetch = FetchType.EAGER)
    private Usuario usuario;

    // @ManyToMany
    // @JoinTable (name = "historico_rendafixa",
    //             uniqueConstraints = @UniqueConstraint
    //             (
    //                 columnNames = {"historicoId", "rendafixaId"},
    //                 name = "unique_historico_rendafixa"
    //             ),
    //             joinColumns = @JoinColumn
    //             (
    //                 name = "rendafixaId",
    //                 referencedColumnName = "id",
    //                 table = "rendafixa",
    //                 unique = false
    //             ),
    //             inverseJoinColumns = @JoinColumn
    //             (
    //                 name = "historicoId",
    //                 referencedColumnName = "id",
    //                 table = "historico",
    //                 unique = false
    //             )
    //             )
    // private List<Historico> historicos = new ArrayList<Historico>();

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

    // public List<Historico> getHistoricos() {
    //     return historicos;
    // }

    // public void setHistoricos(List<Historico> historicos) {
    //     this.historicos = historicos;
    // }

    
}
