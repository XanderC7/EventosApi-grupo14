package ec.edu.espe.eventos_api.dto;

import java.time.LocalDate;

// Objeto de transferencia de datos para Inscripcion
public class InscripcionDTO {

    private Long id;
    private Long eventoId;
    private String eventoNombre;
    private Long participanteId;
    private String participanteNombre;
    private LocalDate fechaInscripcion;
    private String estado;

    // Constructor vacio
    public InscripcionDTO() {
    }

    // Constructor con parametros
    public InscripcionDTO(Long id, Long eventoId, String eventoNombre, Long participanteId,
                          String participanteNombre, LocalDate fechaInscripcion, String estado) {
        this.id = id;
        this.eventoId = eventoId;
        this.eventoNombre = eventoNombre;
        this.participanteId = participanteId;
        this.participanteNombre = participanteNombre;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }

    // Metodos getter y setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public String getEventoNombre() {
        return eventoNombre;
    }

    public void setEventoNombre(String eventoNombre) {
        this.eventoNombre = eventoNombre;
    }

    public Long getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Long participanteId) {
        this.participanteId = participanteId;
    }

    public String getParticipanteNombre() {
        return participanteNombre;
    }

    public void setParticipanteNombre(String participanteNombre) {
        this.participanteNombre = participanteNombre;
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
}
