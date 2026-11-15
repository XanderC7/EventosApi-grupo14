package ec.edu.espe.eventos_api.dto;

import java.time.LocalDate;

// Objeto de transferencia de datos para Evento
public class EventoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private String lugar;
    private Integer capacidadMaxima;
    private Integer cuposDisponibles;
    private Long categoriaId;
    private String categoriaNombre;

    // Constructor vacio
    public EventoDTO() {
    }

    // Constructor con parametros
    public EventoDTO(Long id, String nombre, String descripcion, LocalDate fecha, String lugar,
                     Integer capacidadMaxima, Integer cuposDisponibles, Long categoriaId, String categoriaNombre) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.lugar = lugar;
        this.capacidadMaxima = capacidadMaxima;
        this.cuposDisponibles = cuposDisponibles;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }
}
