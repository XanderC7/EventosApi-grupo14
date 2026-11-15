package ec.edu.espe.eventos_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.espe.eventos_api.entity.Inscripcion;

// Repositorio para operaciones CRUD y consultas personalizadas de Inscripcion
@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    // Consulta personalizada para obtener todas las inscripciones de un evento especifico
    List<Inscripcion> findByEventoId(Long eventoId);

    // Consulta personalizada para verificar si un participante ya esta inscrito en un evento
    boolean existsByEventoIdAndParticipanteIdAndEstado(Long eventoId, Long participanteId, String estado);
}
