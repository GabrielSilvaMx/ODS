package org.bedu.ods.repository;

import org.bedu.ods.entity.Proyectos;
import org.bedu.ods.entity.Tareas;
import org.bedu.ods.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;


public interface ITareasRepository  extends JpaRepository<Tareas, Long> {

    LinkedList<Tareas> findTareasByProyecto(Proyectos proyecto);

    LinkedList<Tareas> findTareasByUsuario(Usuarios usuario);

    LinkedList<Tareas> findTareasByProyectoAndUsuario(Proyectos proyectos, Usuarios usuario);

/*
    List<Tareas> findByCardIDContaining(String cardID);

    List<Tareas> findByProyecto(long proyectoId);

    List<Tareas> findByUsuario(long usuarioId);

    List<Tareas> findByProyectoAndUsuario(long proyectoId, long usuarioId);

    @Transactional
    void deleteByUsuarioId(long usuarioId);

    @Transactional
    void deleteByProyectoId(long proyectoId);

 */
}
