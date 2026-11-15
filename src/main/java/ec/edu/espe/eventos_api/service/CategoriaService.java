package ec.edu.espe.eventos_api.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ec.edu.espe.eventos_api.dto.CategoriaDTO;
import ec.edu.espe.eventos_api.entity.Categoria;
import ec.edu.espe.eventos_api.exception.RecursoNoEncontradoException;
import ec.edu.espe.eventos_api.exception.ReglaNegocioException;
import ec.edu.espe.eventos_api.repository.CategoriaRepository;

// Servicio que contiene la logica de negocio para categorias
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    // Inyeccion de dependencias por constructor
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Registra una nueva categoria validando que el nombre no se repita
    public CategoriaDTO crear(CategoriaDTO dto) {
        if (categoriaRepository.existsByNombre(dto.getNombre())) {
            throw new ReglaNegocioException("Ya existe una categoria con el nombre: " + dto.getNombre());
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria guardada = categoriaRepository.save(categoria);
        return convertirADTO(guardada);
    }

    // Obtiene el listado de todas las categorias
    public List<CategoriaDTO> listarTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDTO> respuesta = new ArrayList<>();
        for (Categoria c : categorias) {
            respuesta.add(convertirADTO(c));
        }
        return respuesta;
    }

    // Busca una categoria por su id o lanza excepcion si no existe
    public CategoriaDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con id: " + id));
        return convertirADTO(categoria);
    }

    // Elimina una categoria por su id
    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con id: " + id));
        categoriaRepository.delete(categoria);
    }

    // Convierte una entidad Categoria a CategoriaDTO
    private CategoriaDTO convertirADTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}
