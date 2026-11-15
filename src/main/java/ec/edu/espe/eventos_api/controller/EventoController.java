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
import ec.edu.espe.eventos_api.dto.EventoDTO;
import ec.edu.espe.eventos_api.service.EventoService;

// Controlador REST para gestionar las peticiones HTTP de eventos
@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    // Inyeccion del servicio por constructor
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // Endpoint GET para listar todos los eventos
    @GetMapping
    public List<EventoDTO> listarTodos() {
        return eventoService.listarTodos();
    }

    // Endpoint GET para obtener un evento por su id
    @GetMapping("/{id}")
    public EventoDTO obtenerPorId(@PathVariable Long id) {
        return eventoService.obtenerPorId(id);
    }

    // Endpoint GET para consultar eventos por categoria
    @GetMapping("/categoria/{categoriaId}")
    public List<EventoDTO> buscarPorCategoria(@PathVariable Long categoriaId) {
        return eventoService.buscarPorCategoria(categoriaId);
    }

    // Endpoint GET para consultar eventos que tienen cupos disponibles
    @GetMapping("/disponibles")
    public List<EventoDTO> buscarConCuposDisponibles() {
        return eventoService.buscarConCuposDisponibles();
    }

    // Endpoint POST para crear un nuevo evento
    @PostMapping
    public EventoDTO crear(@RequestBody EventoDTO dto) {
        return eventoService.crear(dto);
    }

    // Endpoint PUT para actualizar un evento y su capacidad maxima
    @PutMapping("/{id}")
    public EventoDTO actualizar(@PathVariable Long id, @RequestBody EventoDTO dto) {
        return eventoService.actualizar(id, dto);
    }

    // Endpoint DELETE para eliminar un evento por su id
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);
        return "Evento eliminado correctamente";
    }
}
