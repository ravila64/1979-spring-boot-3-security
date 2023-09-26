package med.voll.api.domain.consulta;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

//    boolean existsByPacienteIdAndFechaBeetwen(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    // boolean existsByPacienteIdAndDataBeetwen(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

   // boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime fecha);

    boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);

    boolean existsByPacienteIdAndFechaBeetwen(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);
}