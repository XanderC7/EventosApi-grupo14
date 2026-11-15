package ec.edu.espe.eventos_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.espe.eventos_api.dto.EventoDTO;
import ec.edu.espe.eventos_api.entity.Categoria;
import ec.edu.espe.eventos_api.entity.Evento;
import ec.edu.espe.eventos_api.exception.RecursoNoEncontradoException;
import ec.edu.espe.eventos_api.exception.ReglaNegocioException;
import ec.edu.espe.eventos_api.repository.CategoriaRepository;
import ec.edu.espe.eventos_api.repository.EventoRepository;

// Servicio que contiene la logica de negocio para eventos
@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final CategoriaRepository categoriaRepository;

    // Inyeccion de dependencias por constructor
    public EventoService(EventoRepository eventoRepository, CategoriaRepository categoriaRepository) {
        this.eventoRepository = eventoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Crea un nuevo evento validando su capacidad maxima y su categoria
    public EventoDTO crear(EventoDTO dto) {
        if (dto.getCapacidadMaxima() == null || dto.getCapacidadMaxima() <= 0) {
            throw new ReglaNegocioException("La capacidad maxima del evento debe ser mayor a cero");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con id: " + dto.getCategoriaId()));

        Evento evento = new Evento();
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFecha(dto.getFecha());
        evento.setLugar(dto.getLugar());
        evento.setCapacidadMaxima(dto.getCapacidadMaxima());
        evento.setCuposDisponibles(dto.getCapacidadMaxima());
        evento.setCategoria(categoria);

        Evento guardado = eventoRepository.save(evento);
        return convertirADTO(guardado);
    }

    // Lista todos los eventos registrados
    public List<EventoDTO> listarTodos() {
        List<Evento> eventos = eventoRepository.findAll();
        List<EventoDTO> respuesta = new ArrayList<>();
        for (Evento e : eventos) {
            respuesta.add(convertirADTO(e));
        }
        return respuesta;
    }

    // Busca un evento por su id
    public EventoDTO obtenerPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado con id: " + id));
        return convertirADTO(evento);
    }

    // Consulta personalizada de eventos por categoria
    public List<EventoDTO> buscarPorCategoria(Long categoriaId) {
        List<Evento> eventos = eventoRepository.findByCategoriaId(categoriaId);
        List<EventoDTO> respuesta = new ArrayList<>();
        for (Evento e : eventos) {
            respuesta.add(convertirADTO(e));
        }
        return respuesta;
    }

    // Consulta personalizada de eventos que aun tienen cupos disponibles
    public List<EventoDTO> buscarConCuposDisponibles() {
        List<Evento> eventos = eventoRepository.findByCuposDisponiblesGreaterThan(0);
        List<EventoDTO> respuesta = new ArrayList<>();
        for (Evento e : eventos) {
            respuesta.add(convertirADTO(e));
        }
        return respuesta;
    }

    // Actualiza los datos y la capacidad maxima de un evento
    public EventoDTO actualizar(Long id, EventoDTO dto) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado con id: " + id));

        if (dto.getCapacidadMaxima() == null || dto.getCapacidadMaxima() <= 0) {
            throw new ReglaNegocioException("La capacidad maxima del evento debe ser mayor a cero");
        }

        int inscritosActuales = evento.getCapacidadMaxima() - evento.getCuposDisponibles();
        if (dto.getCapacidadMaxima() < inscritosActuales) {
            throw new ReglaNegocioException("La nueva capacidad no puede ser menor a los participantes ya inscritos: " + inscritosActuales);
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con id: " + dto.getCategoriaId()));

        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFecha(dto.getFecha());
        evento.setLugar(dto.getLugar());
        evento.setCapacidadMaxima(dto.getCapacidadMaxima());
        evento.setCuposDisponibles(dto.getCapacidadMaxima() - inscritosActuales);
        evento.setCategoria(categoria);

        Evento actualizado = eventoRepository.save(evento);
        return convertirADTO(actualizado);
    }

    // Elimina un evento por su id
    public void eliminar(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado con id: " + id));
        eventoRepository.delete(evento);
    }

    // Convierte una entidad Evento a EventoDTO
    private EventoDTO convertirADTO(Evento evento) {
        return new EventoDTO(
                evento.getId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getFecha(),
                evento.getLugar(),
                evento.getCapacidadMaxima(),
                evento.getCuposDisponibles(),
                evento.getCategoria().getId(),
                evento.getCategoria().getNombre()
        );
    }
}
