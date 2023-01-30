package org.bedu.ods.service.impl;

import org.bedu.ods.entity.dto.TareasDTO;

import java.util.List;

public interface ITareasService {

    TareasDTO findById(long id);

    List<TareasDTO> findTareasByProyecto(Long idProyecto);

    List<TareasDTO> findTareasByUsuario(Long idUsuario);

    List<TareasDTO> findTareasByProyectoAndUsuario(Long idProyecto, Long idUsuario);

    TareasDTO addTareaByProyectoAndUsuario(TareasDTO tareasDto, long proyectoId, long usuarioId);

    TareasDTO update(long id, TareasDTO tarea);

    void delete(long id);

    TareasDTO findLastTarea();

/*
    List<TareasDTO> findAll(String cardID);

    TareasDTO findById(long id);

    TareasDTO save(TareasDTO tareasDto, long proyectoId, long usuarioId);

    TareasDTO update(long id, TareasDTO tareasDto);

    void delete(long id);

    List<Tareas> findProyecto(long proyectoId);

    List<Tareas> findByUsuario(long usuarioId);

    List<Tareas> findByProyectoYUsuario(long proyectoId, long usuarioId);
    */
}
