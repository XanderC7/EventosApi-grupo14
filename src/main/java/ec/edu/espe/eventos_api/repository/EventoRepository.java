package ec.edu.espe.eventos_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.espe.eventos_api.entity.Evento;

// Repositorio para operaciones CRUD y consultas personalizadas de Evento
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Consulta personalizada para buscar eventos por el id de su categoria
    List<Evento> findByCategoriaId(Long categoriaId);

    // Consulta personalizada para buscar eventos que tengan cupos disponibles mayores a un valor
    List<Evento> findByCuposDisponiblesGreaterThan(Integer cupos);
}
