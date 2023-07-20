package med.voll.api.domain.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public void agendar(DadosAgendamentoConsulta dados){
        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidationException("Id do paciente informado não existe");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            // id do medico é opcional se não for selecionado, buscar outro disponivel
            throw new ValidationException("Id do medico informado não existe");
        }

        // Chamando todos os validadores implementados na interface
        validadores.forEach(v ->v.validar(dados));

        var medico = escolherMedico(dados);
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();

        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null){
            throw new ValidationException("Especialidade é obrigatoria quando médico não for escolhido!");
        }
        return medicoRepository.escolherMedicoLivreNaData(dados.especialidade(), dados.data());
    }
}
