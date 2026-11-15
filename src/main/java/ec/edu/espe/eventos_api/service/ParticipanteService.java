package ec.edu.espe.eventos_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.espe.eventos_api.dto.ParticipanteDTO;
import ec.edu.espe.eventos_api.entity.Participante;
import ec.edu.espe.eventos_api.exception.RecursoNoEncontradoException;
import ec.edu.espe.eventos_api.exception.ReglaNegocioException;
import ec.edu.espe.eventos_api.repository.ParticipanteRepository;

// Servicio que contiene la logica de negocio para participantes
@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    // Inyeccion de dependencias por constructor
    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    // Registra un participante validando que el correo no este duplicado
    public ParticipanteDTO registrar(ParticipanteDTO dto) {
        if (participanteRepository.existsByEmail(dto.getEmail())) {
            throw new ReglaNegocioException("Ya existe un participante registrado con el correo: " + dto.getEmail());
        }

        Participante participante = new Participante();
        participante.setNombre(dto.getNombre());
        participante.setApellido(dto.getApellido());
        participante.setEmail(dto.getEmail());
        participante.setTelefono(dto.getTelefono());

        Participante guardado = participanteRepository.save(participante);
        return convertirADTO(guardado);
    }

    // Lista todos los participantes registrados
    public List<ParticipanteDTO> listarTodos() {
        List<Participante> participantes = participanteRepository.findAll();
        List<ParticipanteDTO> respuesta = new ArrayList<>();
        for (Participante p : participantes) {
            respuesta.add(convertirADTO(p));
        }
        return respuesta;
    }

    // Busca un participante por su id
    public ParticipanteDTO obtenerPorId(Long id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Participante no encontrado con id: " + id));
        return convertirADTO(participante);
    }

    // Actualiza los datos de un participante existente
    public ParticipanteDTO actualizar(Long id, ParticipanteDTO dto) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Participante no encontrado con id: " + id));

        if (!participante.getEmail().equals(dto.getEmail()) && participanteRepository.existsByEmail(dto.getEmail())) {
            throw new ReglaNegocioException("El correo ya pertenece a otro participante: " + dto.getEmail());
        }

        participante.setNombre(dto.getNombre());
        participante.setApellido(dto.getApellido());
        participante.setEmail(dto.getEmail());
        participante.setTelefono(dto.getTelefono());

        Participante actualizado = participanteRepository.save(participante);
        return convertirADTO(actualizado);
    }

    // Elimina un participante por su id
    public void eliminar(Long id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Participante no encontrado con id: " + id));
        participanteRepository.delete(participante);
    }

    // Convierte una entidad Participante a ParticipanteDTO
    private ParticipanteDTO convertirADTO(Participante participante) {
        return new ParticipanteDTO(
                participante.getId(),
                participante.getNombre(),
                participante.getApellido(),
                participante.getEmail(),
                participante.getTelefono()
        );
    }
}
