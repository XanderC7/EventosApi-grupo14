package ec.edu.espe.eventos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.espe.eventos_api.entity.Categoria;

// Repositorio para operaciones CRUD y consultas de Categoria
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Consulta personalizada para verificar si existe una categoria con ese nombre
    boolean existsByNombre(String nombre);
}
