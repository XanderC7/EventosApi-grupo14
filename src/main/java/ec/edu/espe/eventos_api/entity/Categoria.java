package ec.edu.espe.eventos_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidad que representa la tabla categorias en la base de datos
@Entity
@Table(name = "categorias")
public class Categoria {

    // Clave primaria autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre unico y obligatorio de la categoria
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    // Descripcion de la categoria
    @Column(length = 250)
    private String descripcion;

    // Constructor vacio requerido por JPA
    public Categoria() {
    }

    // Constructor con parametros
    public Categoria(Long id, String nombre, String descripcion) {
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
