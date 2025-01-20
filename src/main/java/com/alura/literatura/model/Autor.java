package com.alura.literatura.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true)

    @JsonProperty("name")
    private String nombre;

    @JsonProperty("birth_year")
    private Integer añoDeNacimiento;

    @JsonProperty("death_year")
    private Integer añoDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(){}

    //Constructor: Recepcion datos del autor
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.añoDeNacimiento = datosAutor.añoDeNacimiento();
        this.añoDeFallecimiento = datosAutor.añoDeFallecimiento();
    }

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

    public Integer getAñoDeNacimiento() {
        return añoDeNacimiento;
    }

    public void setAñoDeNacimiento(Integer añoDeNacimiento) {
        this.añoDeNacimiento = añoDeNacimiento;
    }

    public Integer getAñoDeFallecimiento() {
        return añoDeFallecimiento;
    }

    public void setAñoDeFallecimiento(Integer añoDeFallecimiento) {
        this.añoDeFallecimiento = añoDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibro(Libro libro) {
        this.libros.add (libro);
        libro.setAutor(this);
        addLibro(libro);
    }

    private void addLibro(Libro libro) {
        this.libros.add(libro);
        libro.setAutor(this);
    }

    @Override
    public String toString() {
        StringBuilder librosString = new StringBuilder();
        for (int i = 0; i < libros.size(); i++) {
            librosString.append(libros.get(i).getTitulo());
            if (i < libros.size() - 1) {
                librosString.append(", ");
            }
        }
        return "Autor: " + nombre + "\n" +
                "Año de nacimiento: " + añoDeNacimiento + "\n" +
                "Año de fallecimiento: " + añoDeFallecimiento + "\n" +
                "Libros: " + librosString.toString() + "\n";
    }
}
