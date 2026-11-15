package ec.edu.espe.eventos_api.dto;

// Objeto de transferencia de datos para Categoria
public class CategoriaDTO {

    private Long id;
    private String nombre;
    private String descripcion;

    // Constructor vacio
    public CategoriaDTO() {
    }

    // Constructor con parametros
    public CategoriaDTO(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Metodos getter y setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
