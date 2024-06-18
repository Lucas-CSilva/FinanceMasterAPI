package br.com.financemaster.model;

public class Orcamento {
    private double totalGastoFixo;
    private double totalGastoVariavel;
    private double totalRendaFixa;
    private double totalRendaVAriavel;
    private double totalRenda;
    private double totalGasto;
    private double totalMensal;

    public double getTotalGastoFixo() {
        return totalGastoFixo;
    }
    public void setTotalGastoFixo(double totalGastoFixo) {
        this.totalGastoFixo = totalGastoFixo;
        setTotalGasto();
    }
    
    public double getTotalGastoVariavel() {
        return totalGastoVariavel;
    }
    public void setTotalGastoVariavel(double totalGastoVariavel) {
        this.totalGastoVariavel = totalGastoVariavel;
        setTotalGasto();
    }

    public double getTotalRendaFixa() {
        return totalRendaFixa;
    }
    public void setTotalRendaFixa(double totalRendaFixa) {
        this.totalRendaFixa = totalRendaFixa;
        setTotalRenda();
    }

    public double getTotalRendaVAriavel() {
        return totalRendaVAriavel;
    }
    public void setTotalRendaVAriavel(double totalRendaVAriavel) {
        this.totalRendaVAriavel = totalRendaVAriavel;
        setTotalRenda();
    }

    public double getTotalRenda() {
        return totalRenda;
    }
    public void setTotalRenda() {
        this.totalRenda = this.totalRendaFixa + this.totalRendaVAriavel;
        setTotalMensal();
    }

    public double getTotalGasto() {
        return totalGasto;
    }
    public void setTotalGasto() {
        this.totalGasto = this.totalGastoFixo + this.totalGastoVariavel;
        setTotalMensal();
    }

    public double getTotalMensal() {
        return totalMensal;
    }
    public void setTotalMensal() {
        this.totalMensal = this.totalRenda - this.totalGasto;
    }
}
