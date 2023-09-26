package med.voll.api.domain.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository repository;
    public void validar(DatosAgendarConsulta datos){
        var medidoConConsulta = repository.existsByMedicoIdAndFecha(datos.idMedico(),datos.fecha());
        if(medidoConConsulta){
            throw new ValidationException("Este medico ya tiene una consulta en ese horario...");
        }
    }
}
