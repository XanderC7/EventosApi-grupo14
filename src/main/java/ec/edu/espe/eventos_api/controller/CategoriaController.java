package ec.edu.espe.eventos_api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ec.edu.espe.eventos_api.dto.CategoriaDTO;
import ec.edu.espe.eventos_api.service.CategoriaService;

// Controlador REST para gestionar las peticiones HTTP de categorias
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    // Inyeccion del servicio por constructor
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Endpoint GET para listar todas las categorias
    @GetMapping
    public List<CategoriaDTO> listarTodas() {
        return categoriaService.listarTodas();
    }

    // Endpoint GET para buscar una categoria por su id
    @GetMapping("/{id}")
    public CategoriaDTO obtenerPorId(@PathVariable Long id) {
        return categoriaService.obtenerPorId(id);
    }

    // Endpoint POST para registrar una nueva categoria
    @PostMapping
    public CategoriaDTO crear(@RequestBody CategoriaDTO dto) {
        return categoriaService.crear(dto);
    }

    // Endpoint DELETE para eliminar una categoria por su id
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return "Categoria eliminada correctamente";
    }
}
