package ec.edu.espe.eventos_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidad que representa la tabla participantes en la base de datos
@Entity
@Table(name = "participantes")
public class Participante {

    // Clave primaria autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre obligatorio del participante
    @Column(nullable = false, length = 100)
    private String nombre;

    // Apellido obligatorio del participante
    @Column(nullable = false, length = 100)
    private String apellido;

    // Correo electronico unico y obligatorio
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    // Numero de telefono del participante
    @Column(length = 20)
    private String telefono;

    // Constructor vacio requerido por JPA
    public Participante() {
    }

    // Constructor con parametros
    public Participante(Long id, String nombre, String apellido, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
