package med.voll.api.domain.consulta.validacoes.impl;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {

        if (dados.idPaciente() == null){
            return;
        }
        var pacienteAtivo = repository.findAtivoById(dados.idMedico());
        if (!pacienteAtivo){
            throw new ValidationException("Consulta não pode ser agendade com paciente inativo");
        }
    }
}
