package br.com.financemaster.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import br.com.financemaster.model.GastoFixo;
import br.com.financemaster.model.GastoVariavel;
import br.com.financemaster.model.Orcamento;
import br.com.financemaster.model.RendaFixa;
import br.com.financemaster.model.RendaVariavel;
import br.com.financemaster.model.Usuario;
import br.com.financemaster.repository.GastoFixoRepository;
import br.com.financemaster.repository.GastoVariavelRepository;
import br.com.financemaster.repository.IRendaFixaRepository;
import br.com.financemaster.repository.IRendaVariavelRepository;
import br.com.financemaster.repository.IUsuarioRepository;
import br.com.financemaster.security.SecurityFilter;
import br.com.financemaster.security.TokenService;

@Controller("OrcamentoController")
@RequestMapping(value = "/orcamento")
@CrossOrigin
public class OrcamentoController {
    
    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    @Autowired
    private GastoFixoRepository gastoFixoRepository;

    @Autowired
    private IRendaFixaRepository rendaFixaRepository;

    @Autowired
    private IRendaVariavelRepository rendaVariavelRepository;

    @Autowired
    private GastoVariavelRepository gastoVariavelRepository;

    @Autowired
    private TokenService tokenService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Orcamento> getOrcamento(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("dataInicio") String dataInicioStr,
            @RequestParam("dataFim") String dataFimStr) 
    {
        try 
        {
            
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            LocalDate dataInicio = LocalDate.parse(dataInicioStr, DateTimeFormatter.ISO_DATE);
            LocalDate dataFim = LocalDate.parse(dataFimStr, DateTimeFormatter.ISO_DATE);

            List<RendaVariavel> rendaVariaveis = (List<RendaVariavel>) this.rendaVariavelRepository.findByUsuario(user);
            List<RendaFixa> rendaFixas = (List<RendaFixa>) this.rendaFixaRepository.findByUsuario(user);
            List<GastoFixo> gastoFixos = (List<GastoFixo>) this.gastoFixoRepository.findByUsuario(user);
            List<GastoVariavel> gastoVariaveis = (List<GastoVariavel>) this.gastoVariavelRepository.findByUsuario(user);
            
            double totalRendaFixa = calcularTotalRendaFixa(rendaFixas, dataInicio, dataFim);
            double totalRendaVariavel = calcularTotalRendaVariavel(rendaVariaveis, dataInicio, dataFim);
            double totalGastoFixo = calcularTotalGastoFixo(gastoFixos, dataInicio, dataFim);
            double totalGastoVariavel = calcularTotalGastoVariavel(gastoVariaveis, dataInicio, dataFim);

            Orcamento orcamento = new Orcamento();

            orcamento.setTotalRendaFixa(totalRendaFixa);
            orcamento.setTotalRendaVAriavel(totalRendaVariavel);
            orcamento.setTotalGastoFixo(totalGastoFixo);
            orcamento.setTotalGastoVariavel(totalGastoVariavel);

            return new ResponseEntity<Orcamento>(orcamento, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private double calcularTotalRendaFixa(List<RendaFixa> rendasFixas, LocalDate dataInicio, LocalDate dataFim) {
        double total = 0.0;
        for (RendaFixa renda : rendasFixas) {
            LocalDate data = renda.getDataCompetencia();
            while (data.isBefore(dataFim) || data.isEqual(dataFim)) {
                if (data.isAfter(dataInicio) || data.isEqual(dataInicio)) {
                    total += renda.getValor();
                }
                data = data.plusMonths(1); // Assumindo que a recorrência é mensal
            }
        }
        return total;
    }

    private double calcularTotalRendaVariavel(List<RendaVariavel> rendasVariaveis, LocalDate dataInicio, LocalDate dataFim) {
        double total = 0.0;
        for (RendaVariavel renda : rendasVariaveis) {
            LocalDate data = renda.getDataInicio();
            while (data.isBefore(renda.getDataFinal()) || data.isEqual(renda.getDataFinal())) {
                if ((data.isAfter(dataInicio) || data.isEqual(dataInicio)) && (data.isBefore(dataFim) || data.isEqual(dataFim))) {
                    total += renda.getValor();
                }
                data = incrementarData(data, renda.getRecorrencia());
            }
        }
        return total;
    }

    private double calcularTotalGastoFixo(List<GastoFixo> gastosFixos, LocalDate dataInicio, LocalDate dataFim) {
        double total = 0.0;
        for (GastoFixo gasto : gastosFixos) {
            LocalDate data = gasto.getDataCompetencia();
            while (data.isBefore(dataFim) || data.isEqual(dataFim)) {
                if (data.isAfter(dataInicio) || data.isEqual(dataInicio)) {
                    total += gasto.getValor();
                }
                data = data.plusMonths(1); 
            }
        }
        return total;
    }

    private double calcularTotalGastoVariavel(List<GastoVariavel> gastosVariaveis, LocalDate dataInicio, LocalDate dataFim) {
        double total = 0.0;
        for (GastoVariavel gasto : gastosVariaveis) {
            LocalDate data = gasto.getDataInicio();
            while (data.isBefore(gasto.getDataFinal()) || data.isEqual(gasto.getDataFinal())) {
                if ((data.isAfter(dataInicio) || data.isEqual(dataInicio)) && (data.isBefore(dataFim) || data.isEqual(dataFim))) {
                    total += gasto.getValor();
                }
                data = incrementarData(data, gasto.getRecorrencia());
            }
        }
        return total;
    }

    private LocalDate incrementarData(LocalDate data, String recorrencia) {
        switch (recorrencia) {
            case "diaria":
                return data.plusDays(1);
            case "semanal":
                return data.plusWeeks(1);
            case "mensal":
                return data.plusMonths(1);
            case "anual":
                return data.plusYears(1);
            default:
                throw new IllegalArgumentException("Recorrência inválida: " + recorrencia);
        }
    }
}
