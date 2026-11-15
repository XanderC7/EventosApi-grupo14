package ec.edu.espe.eventos_api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import ec.edu.espe.eventos_api.dto.InscripcionDTO;
import ec.edu.espe.eventos_api.dto.ParticipanteDTO;
import ec.edu.espe.eventos_api.entity.Evento;
import ec.edu.espe.eventos_api.entity.Inscripcion;
import ec.edu.espe.eventos_api.entity.Participante;
import ec.edu.espe.eventos_api.exception.CupoMaximoAlcanzadoException;
import ec.edu.espe.eventos_api.exception.RecursoNoEncontradoException;
import ec.edu.espe.eventos_api.exception.ReglaNegocioException;
import ec.edu.espe.eventos_api.repository.EventoRepository;
import ec.edu.espe.eventos_api.repository.InscripcionRepository;
import ec.edu.espe.eventos_api.repository.ParticipanteRepository;

// Servicio que gestiona la inscripcion de participantes en eventos
@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;

    // Inyeccion de dependencias por constructor
    public InscripcionService(InscripcionRepository inscripcionRepository,
            EventoRepository eventoRepository,
            ParticipanteRepository participanteRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.eventoRepository = eventoRepository;
        this.participanteRepository = participanteRepository;
    }

    // Inscribe un participante en un evento controlando el cupo disponible y duplicados
    public InscripcionDTO inscribir(InscripcionDTO dto) {
        Evento evento = eventoRepository.findById(dto.getEventoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado con id: " + dto.getEventoId()));

        Participante participante = participanteRepository.findById(dto.getParticipanteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Participante no encontrado con id: " + dto.getParticipanteId()));

        // Regla de negocio para impedir inscripcion duplicada en el mismo evento
        if (inscripcionRepository.existsByEventoIdAndParticipanteIdAndEstado(evento.getId(), participante.getId(), "CONFIRMADA")) {
            throw new ReglaNegocioException("El participante ya se encuentra inscrito en este evento");
        }

        // Regla de negocio para impedir inscripciones cuando se alcanza el cupo maximo
        if (evento.getCuposDisponibles() <= 0) {
            throw new CupoMaximoAlcanzadoException("Cupo maximo alcanzado para el evento: " + evento.getNombre());
        }

        // Descuenta un cupo disponible en el evento
        evento.setCuposDisponibles(evento.getCuposDisponibles() - 1);
        eventoRepository.save(evento);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstado("CONFIRMADA");
        inscripcion.setEvento(evento);
        inscripcion.setParticipante(participante);

        Inscripcion guardada = inscripcionRepository.save(inscripcion);

        // Ejecuta la notificacion en segundo plano de forma no bloqueante
        enviarNotificacionAsincrona(participante.getEmail(), evento.getNombre());

        return convertirADTO(guardada);
    }

    // Consulta todas las inscripciones pertenecientes a un evento
    public List<InscripcionDTO> listarPorEvento(Long eventoId) {
        if (!eventoRepository.existsById(eventoId)) {
            throw new RecursoNoEncontradoException("Evento no encontrado con id: " + eventoId);
        }
        List<Inscripcion> inscripciones = inscripcionRepository.findByEventoId(eventoId);
        List<InscripcionDTO> respuesta = new ArrayList<>();
        for (Inscripcion i : inscripciones) {
            respuesta.add(convertirADTO(i));
        }
        return respuesta;
    }

    // Consulta los participantes confirmados de un evento especifico
    public List<ParticipanteDTO> listarParticipantesPorEvento(Long eventoId) {
        if (!eventoRepository.existsById(eventoId)) {
            throw new RecursoNoEncontradoException("Evento no encontrado con id: " + eventoId);
        }
        List<Inscripcion> inscripciones = inscripcionRepository.findByEventoId(eventoId);
        List<ParticipanteDTO> participantes = new ArrayList<>();
        for (Inscripcion i : inscripciones) {
            if ("CONFIRMADA".equals(i.getEstado())) {
                Participante p = i.getParticipante();
                participantes.add(new ParticipanteDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getApellido(),
                        p.getEmail(),
                        p.getTelefono()
                ));
            }
        }
        return participantes;
    }

    // Cancela una inscripcion y libera el cupo en el evento
    public InscripcionDTO cancelarInscripcion(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inscripcion no encontrada con id: " + id));

        if ("CANCELADA".equals(inscripcion.getEstado())) {
            throw new ReglaNegocioException("La inscripcion ya se encuentra cancelada");
        }

        inscripcion.setEstado("CANCELADA");
        Inscripcion actualizada = inscripcionRepository.save(inscripcion);

        Evento evento = inscripcion.getEvento();
        evento.setCuposDisponibles(evento.getCuposDisponibles() + 1);
        eventoRepository.save(evento);

        return convertirADTO(actualizada);
    }

    // Proceso asincrono no bloqueante con CompletableFuture para no detener el hilo principal
    private void enviarNotificacionAsincrona(String email, String nombreEvento) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Notificacion asincrona enviada a " + email + " para el evento " + nombreEvento);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        System.out.println("Inscripcion registrada, el hilo principal continua sin bloquearse");
    }

    // Convierte una entidad Inscripcion a InscripcionDTO
    private InscripcionDTO convertirADTO(Inscripcion inscripcion) {
        return new InscripcionDTO(
                inscripcion.getId(),
                inscripcion.getEvento().getId(),
                inscripcion.getEvento().getNombre(),
                inscripcion.getParticipante().getId(),
                inscripcion.getParticipante().getNombre() + " " + inscripcion.getParticipante().getApellido(),
                inscripcion.getFechaInscripcion(),
                inscripcion.getEstado()
        );
    }
}
