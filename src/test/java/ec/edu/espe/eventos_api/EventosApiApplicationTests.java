package ec.edu.espe.eventos_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import ec.edu.espe.eventos_api.dto.CategoriaDTO;

// Pruebas unitarias basicas con JUnit para verificacion en el pipeline CI
class EventosApiApplicationTests {

    @Test
    void verificarCreacionCategoriaDTO() {
        CategoriaDTO categoria = new CategoriaDTO(1L, "Conferencia", "Eventos academicos");
        assertEquals("Conferencia", categoria.getNombre());
    }
}
