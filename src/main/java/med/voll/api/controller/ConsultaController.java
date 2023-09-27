package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
//@SuppressWarnings("all")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService agendaDeConsultaService;   // estaba service

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        var response = agendaDeConsultaService.agendar(datos);
        return ResponseEntity.ok(response);
        // ret ResponseEntity.ok(new DatosDetalleConsulta(null,null,null,null));
    }
}