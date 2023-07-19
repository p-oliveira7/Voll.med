package med.voll.api.domain.consulta.validacoes;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.DayOfWeek;

public class ValidadorHorarioFuncClinica {

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
