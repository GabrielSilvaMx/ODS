package org.bedu.ods.service.impl;

import lombok.RequiredArgsConstructor;
import org.bedu.ods.entity.Proyectos;
import org.bedu.ods.entity.Tareas;
import org.bedu.ods.entity.Usuarios;
import org.bedu.ods.entity.dto.TableroDTO;
import org.bedu.ods.entity.dto.TareasDTO;
import org.bedu.ods.entity.mapper.TareasMapper;
import org.bedu.ods.exception.ResourceNotFoundException;
import org.bedu.ods.repository.IProyectosRepository;
import org.bedu.ods.repository.ITareasRepository;
import org.bedu.ods.repository.IUsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TareasImpl implements ITareasService {

    private final ITareasRepository tareasRepository;

    private final IUsuariosRepository usuariosRepository;

    private final IProyectosRepository proyectosRepository;

    private final TareasMapper tareasMapper;

    @Override
    public TareasDTO findById(long id)
    {
        return tareasMapper.tareasToTareasDto(
                tareasRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarea con id = " + id))
        );
    }

    @Override
    public List<TareasDTO> findTareasByProyecto(Long idProyecto)
    {
        Proyectos proyecto = proyectosRepository.getReferenceById(idProyecto);
        return tareasMapper.listaTareasToTareasDto(
                tareasRepository.findTareasByProyecto(proyecto));
    }

    @Override
    public List<TareasDTO> findTareasByUsuario(Long idUsuario)
    {
        Usuarios usuario = usuariosRepository.getReferenceById(idUsuario);
        return tareasMapper.listaTareasToTareasDto(
                tareasRepository.findTareasByUsuario(usuario));
    }

    @Override
    public List<TareasDTO> findTareasByProyectoAndUsuario(Long idProyecto, Long idUsuario) {
        Proyectos proyecto = proyectosRepository.getReferenceById(idProyecto);

        Usuarios usuario = usuariosRepository.getReferenceById(idUsuario);
        return tareasMapper.listaTareasToTareasDto(
                tareasRepository.findTareasByProyectoAndUsuario(proyecto,usuario)
        );
    }

    @Override
    public TareasDTO addTareaByProyectoAndUsuario(TareasDTO tareasDto, long proyectoId, long usuarioId) {

        Proyectos proyecto = proyectosRepository.findById(proyectoId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el proyecto con id = " + proyectoId));
        Usuarios usuario = usuariosRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id = " + usuarioId));

        Tareas addTarea = tareasMapper.tareasDtoToTareas(tareasDto);
        addTarea.setProyecto(proyecto);
        addTarea.setUsuario(usuario);

        return tareasMapper
                .tareasToTareasDto(tareasRepository
                        .save(addTarea)
                );
    }

    @Override
    public TareasDTO update(long id, TareasDTO tarea) {
        Tareas updateTarea = tareasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarea con id = " + id));
        updateTarea.setCardID(tarea.getCardID());
        updateTarea.setFechaTarea(tarea.getFechaTarea());
        updateTarea.setPrioridad(tarea.getPrioridad());
        updateTarea.setTransicion(tarea.getEstado());
        updateTarea.setEstado(tarea.getEstado());
        updateTarea.setDescripcion(tarea.getDescripcion());
        updateTarea.setTiempoEstimado(tarea.getTiempoEstimado());
        return tareasMapper.
                tareasToTareasDto(tareasRepository
                        .save(updateTarea)
                );
    }

    @Override
    public void delete(long id) {
        Tareas tarea = tareasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró tarea con id = " + id));
        tareasRepository.delete(tarea);
    }

    @Override
    public TareasDTO findLastTarea() {
        List<Tareas> listaTareas = new ArrayList<>();
        tareasRepository.findAll().forEach(listaTareas::add);
        return tareasMapper.tareasToTareasDto(
                listaTareas.get(listaTareas.size()-1));
    }

    @Override
    public Set<TableroDTO> getTableroByProyectoAndUsuario(Long idProyecto, Long idUsuario) {
        Proyectos proyecto = proyectosRepository.getReferenceById(idProyecto);

        Usuarios usuario = usuariosRepository.getReferenceById(idUsuario);
        LinkedList<TareasDTO> tareas = tareasMapper.listaTareasToTareasDto(
                tareasRepository.findTareasByProyectoAndUsuario(proyecto,usuario)
        );

        TableroDTO tableroDto1 = new TableroDTO();
        tableroDto1.setId(1);
        tableroDto1.setName("Qué hacer?");
        tableroDto1.setTasks(tareas);
        TableroDTO tableroDto2 = new TableroDTO();
        tableroDto2.setId(2);
        tableroDto2.setName("¡En progreso!");
        tableroDto2.setTasks(null);
        TableroDTO tableroDto3 = new TableroDTO();
        tableroDto3.setId(3);
        tableroDto3.setTasks(null);
        tableroDto3.setName("¡Está hecho!");
        Set<TableroDTO> tablero = new HashSet < > ();
        tablero.add(tableroDto1);
        tablero.add(tableroDto2);
        tablero.add(tableroDto3);
        return tablero;
    }

}
