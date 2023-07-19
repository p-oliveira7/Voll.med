package med.voll.api.domain.consulta.validacoes;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioAntecedencia {
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