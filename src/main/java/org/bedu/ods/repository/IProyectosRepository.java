package org.bedu.ods.repository;

import org.bedu.ods.entity.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;

public interface IProyectosRepository extends JpaRepository<Proyectos, Long> {
    LinkedList<Proyectos> findByNombreContaining(String nombre);
}
