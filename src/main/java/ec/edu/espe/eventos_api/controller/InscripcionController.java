package ec.edu.espe.eventos_api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ec.edu.espe.eventos_api.dto.InscripcionDTO;
import ec.edu.espe.eventos_api.dto.ParticipanteDTO;
import ec.edu.espe.eventos_api.service.InscripcionService;

// Controlador REST para gestionar las inscripciones a los eventos
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    // Inyeccion del servicio por constructor
    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    // Endpoint POST para inscribir un participante en un evento
    @PostMapping
    public InscripcionDTO inscribir(@RequestBody InscripcionDTO dto) {
        return inscripcionService.inscribir(dto);
    }

    // Endpoint GET para listar todas las inscripciones de un evento
    @GetMapping("/evento/{eventoId}")
    public List<InscripcionDTO> listarPorEvento(@PathVariable Long eventoId) {
        return inscripcionService.listarPorEvento(eventoId);
    }

    // Endpoint GET para consultar los participantes inscritos en un evento
    @GetMapping("/evento/{eventoId}/participantes")
    public List<ParticipanteDTO> listarParticipantesPorEvento(@PathVariable Long eventoId) {
        return inscripcionService.listarParticipantesPorEvento(eventoId);
    }

    // Endpoint PUT para cancelar una inscripcion y liberar el cupo
    @PutMapping("/{id}/cancelar")
    public InscripcionDTO cancelar(@PathVariable Long id) {
        return inscripcionService.cancelarInscripcion(id);
    }
}
