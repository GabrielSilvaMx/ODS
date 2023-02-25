package org.bedu.ods.service.impl;

import org.bedu.ods.entity.dto.TableroDTO;
import org.bedu.ods.entity.dto.TareasDTO;

import java.util.List;
import java.util.Set;

public interface ITareasService {

    TareasDTO findById(long id);

    List<TareasDTO> findTareasByProyecto(Long idProyecto);

    List<TareasDTO> findTareasByUsuario(Long idUsuario);

    List<TareasDTO> findTareasByProyectoAndUsuario(Long idProyecto, Long idUsuario);

    TareasDTO addTareaByProyectoAndUsuario(TareasDTO tareasDto, long proyectoId, long usuarioId);

    TareasDTO update(long id, TareasDTO tarea);

    void delete(long id);

    TareasDTO findLastTarea();

    Set<TableroDTO> getTableroByProyectoAndUsuario(Long idProyecto, Long idUsuario);

}
