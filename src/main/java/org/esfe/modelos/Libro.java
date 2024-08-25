package org.esfe.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El título es requerido")
    private String titulo;

    @NotBlank(message = "La descripcion es requerida")
    private String descripcion;

    @NotNull(message = "La fecha de publicación es requerida")
    private LocalDate fechaDePublicacion;


    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "El título es requerido") String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NotBlank(message = "El título es requerido") String titulo) {
        this.titulo = titulo;
    }

    public @NotBlank(message = "La descripcion es requerida") String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NotBlank(message = "La descripcion es requerida") String descripcion) {
        this.descripcion = descripcion;
    }

    public @NotNull(message = "La fecha de publicación es requerida") LocalDate getFechaDePublicacion() {
        return fechaDePublicacion;
    }

    public void setFechaDePublicacion(@NotNull(message = "La fecha de publicación es requerida") LocalDate fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }


}