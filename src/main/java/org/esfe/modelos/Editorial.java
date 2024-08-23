package org.esfe.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "editoriales")
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El país es requerido")
    private String pais;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @OneToMany(mappedBy = "editorial")
    private List<Libro> libros;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre es requerido") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre es requerido") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "El país es requerido") String getPais() {
        return pais;
    }

    public void setPais(@NotBlank(message = "El país es requerido") String pais) {
        this.pais = pais;
    }

    public @NotBlank(message = "El email es requerido") @Email(message = "El email debe ser válido") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "El email es requerido") @Email(message = "El email debe ser válido") String email) {
        this.email = email;
    }

    public @NotBlank(message = "El teléfono es requerido") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotBlank(message = "El teléfono es requerido") String telefono) {
        this.telefono = telefono;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}