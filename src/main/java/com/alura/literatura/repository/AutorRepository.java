package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.añoDeNacimiento <= :año AND (a.añoDeFallecimiento IS NULL OR a.añoDeFallecimiento > :año)")
    List<Autor> findAutoresVivosEnAño(@Param("año") Integer año);

}
