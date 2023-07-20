package med.voll.api.domain.consulta.validacoes.impl;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaMinutos = Duration.between(agora, dataConsulta).toMinutes();

        // Comparar a data de agendamento com a atual no min 30 minutos depois

        if(diferencaMinutos < 30) {
            throw new ValidationException("Consulta deve ser agendada com antecêdencia mínima de 30 minutos");
        }
    }
}