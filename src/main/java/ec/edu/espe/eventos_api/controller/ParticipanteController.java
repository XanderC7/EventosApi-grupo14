package ec.edu.espe.eventos_api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ec.edu.espe.eventos_api.dto.ParticipanteDTO;
import ec.edu.espe.eventos_api.service.ParticipanteService;

// Controlador REST para gestionar las peticiones HTTP de participantes
@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    // Inyeccion del servicio por constructor
    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    // Endpoint GET para listar todos los participantes
    @GetMapping
    public List<ParticipanteDTO> listarTodos() {
        return participanteService.listarTodos();
    }

    // Endpoint GET para obtener un participante por su id
    @GetMapping("/{id}")
    public ParticipanteDTO obtenerPorId(@PathVariable Long id) {
        return participanteService.obtenerPorId(id);
    }

    // Endpoint POST para registrar un nuevo participante
    @PostMapping
    public ParticipanteDTO registrar(@RequestBody ParticipanteDTO dto) {
        return participanteService.registrar(dto);
    }

    // Endpoint PUT para actualizar los datos de un participante
    @PutMapping("/{id}")
    public ParticipanteDTO actualizar(@PathVariable Long id, @RequestBody ParticipanteDTO dto) {
        return participanteService.actualizar(id, dto);
    }

    // Endpoint DELETE para eliminar un participante por su id
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        participanteService.eliminar(id);
        return "Participante eliminado correctamente";
    }
}
