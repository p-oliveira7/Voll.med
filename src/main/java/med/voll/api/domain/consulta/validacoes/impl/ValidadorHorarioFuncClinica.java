package med.voll.api.domain.consulta.validacoes.impl;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class ValidadorHorarioFuncClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();

        // funciona de 7 as 19 com 1 hora de duracao por consulta fixa

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() <7;
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() <18;
        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica){
            throw new ValidationException("Consulta fora do horrario de funcionamento");
        }

    }
}
