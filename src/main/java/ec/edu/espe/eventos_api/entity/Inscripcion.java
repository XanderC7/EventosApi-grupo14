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

// Entidad que representa la tabla inscripciones en la base de datos
@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    // Clave primaria autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha en la que se realiza la inscripcion
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    // Estado de la inscripcion como CONFIRMADA o CANCELADA
    @Column(nullable = false, length = 30)
    private String estado;

    // Relacion muchos a uno con evento mediante clave foranea
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    // Relacion muchos a uno con participante mediante clave foranea
    @ManyToOne
    @JoinColumn(name = "participante_id", nullable = false)
    private Participante participante;

    // Constructor vacio requerido por JPA
    public Inscripcion() {
    }

    // Constructor con parametros
    public Inscripcion(Long id, LocalDate fechaInscripcion, String estado, Evento evento, Participante participante) {
        this.id = id;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
        this.evento = evento;
        this.participante = participante;
    }

    // Metodos getter y setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }
}
