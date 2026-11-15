package ec.edu.espe.eventos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.espe.eventos_api.entity.Participante;

// Repositorio para operaciones CRUD y consultas de Participante
@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    // Consulta personalizada para verificar si un correo electronico ya esta registrado
    boolean existsByEmail(String email);
}
