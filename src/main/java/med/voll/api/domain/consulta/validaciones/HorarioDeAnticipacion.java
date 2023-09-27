package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos){
        var ahora = LocalDateTime.now();
        var horaDeConsulta = datos.fecha();
        var diferenciaDe30Min = Duration.between(ahora,horaDeConsulta).toMinutes()<30;
        if(diferenciaDe30Min){
            throw new ValidationException("Esta consulta no puede ser realizada, al menos 30 min...");
        }
    }

}
