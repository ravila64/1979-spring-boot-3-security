package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
// se utiliza una base de datos externa, y se quita linea si es basedata in memory
// y activeProfile()
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deberia Retornar Nulo cuando el medico se encuetre en consulta con paciente en esa hora")
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {
        // given = dado conjunto de valores
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico=registrarMedico("Jose","j@mail.com","123456",Especialidad.CARDIOLOGIA);
        var paciente= registrarPaciente("antonio","a@mail.com","654321");
        registrarConsulta(medico,paciente,proximoLunes10H);
        // when = cuando se realiza alguna accion
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10H);
        // then = entonces reciba el resultado deseado
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Deberia Retornar un medico cuando haga consulta en esa hora")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {
        // given = dado conjunto de valores
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico=registrarMedico("Jose","j@mail.com","123456",Especialidad.CARDIOLOGIA);
        // when = cuando se realiza alguna accion
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10H);
        // then = entonces reciba el resultado deseado
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha, null));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "61999999999",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "61999999999",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                " loca",
                "azul",
                "acapulpo",
                "321",
                "12"
        );
    }

    private record datosMedico(
            @NotNull String nombre,
            @NotNull String email,
            @NotNull String telefono,
            @NotNull String dni,
            @NotNull Especialidad especialidad
    //        @NotNull Direccion direccion
    ) {
    }

    private record DatosPaciente(
            @NotNull String nombre,
            @NotNull String email,
            @NotNull String documento){
    }

//    void seleccionarMedicoConEspecialidadEnFecha() {
//    }
}