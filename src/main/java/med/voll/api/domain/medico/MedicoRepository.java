package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query(value = "SELECT m FROM Medico m WHERE m.ativo = 1 AND m.especialidade = :especialidade AND m.id NOT IN (SELECT c.medico.id FROM Consulta c WHERE c.data = :data and c.motivoCancelamento is null) ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Medico escolherMedicoLivreNaData(Especialidade especialidade, LocalDateTime data);


    @Query("select m.ativo from Medico m where m.id = :id")
    Boolean findAtivoById(Long id);
}
