package ec.edu.espe.eventos_api.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Entidad que representa la tabla eventos en la base de datos
@Entity
@Table(name = "eventos")
public class Evento {

    // Clave primaria autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre obligatorio del evento
    @Column(nullable = false, length = 150)
    private String nombre;

    // Descripcion general del evento
    @Column(length = 300)
    private String descripcion;

    // Fecha en la que se realiza el evento
    @Column(nullable = false)
    private LocalDate fecha;

    // Lugar donde se realiza el evento
    @Column(nullable = false, length = 150)
    private String lugar;

    // Capacidad maxima de participantes permitidos
    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima;

    // Cupos actuales disponibles para inscripcion
    @Column(name = "cupos_disponibles", nullable = false)
    private Integer cuposDisponibles;

    // Relacion muchos a uno con categoria mediante clave foranea
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // Constructor vacio requerido por JPA
    public Evento() {
    }

    // Constructor con parametros
    public Evento(Long id, String nombre, String descripcion, LocalDate fecha, String lugar,
                  Integer capacidadMaxima, Integer cuposDisponibles, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.lugar = lugar;
        this.capacidadMaxima = capacidadMaxima;
        this.cuposDisponibles = cuposDisponibles;
        this.categoria = categoria;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Integer getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(Integer capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public Integer getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(Integer cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
