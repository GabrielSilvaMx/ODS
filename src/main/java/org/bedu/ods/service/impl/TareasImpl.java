package org.bedu.ods.service.impl;

import lombok.RequiredArgsConstructor;
import org.bedu.ods.entity.Proyectos;
import org.bedu.ods.entity.Tareas;
import org.bedu.ods.entity.Usuarios;
import org.bedu.ods.entity.dto.TareasDTO;
import org.bedu.ods.entity.mapper.TareasMapper;
import org.bedu.ods.exception.ResourceNotFoundException;
import org.bedu.ods.repository.IProyectosRepository;
import org.bedu.ods.repository.ITareasRepository;
import org.bedu.ods.repository.IUsuariosRepository;
import org.bedu.ods.service.JpaUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TareasImpl implements ITareasService {

    private final ITareasRepository tareasRepository;

    private final IUsuariosRepository usuariosRepository;

    private final IProyectosRepository proyectosRepository;

    private final TareasMapper tareasMapper;

    private final JpaUserDetailsService jpaUserDetailsService;

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
        Authentication authentication = jpaUserDetailsService.getAuth();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //System.out.println("User has authorities: " + userDetails.getAuthorities());
        System.out.println(userDetails.getUsername());

        Proyectos proyecto = proyectosRepository.getReferenceById(idProyecto);

        Usuarios usuario = usuariosRepository.getReferenceById(idUsuario);
        return tareasMapper.listaTareasToTareasDto(
                tareasRepository.findTareasByProyectoAndUsuario(proyecto,usuario)
        );
    }

    @Override
    public TareasDTO addTareaByProyectoAndUsuario(TareasDTO tareasDto, long proyectoId, long usuarioId) {
        //Proyectos proyecto = proyectosRespository.getReferenceById(idProyecto);
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

    /*
    @Override
    public List<Tareas> findAll(String cardID) {
        List<Tareas> tareas = new ArrayList<>();

        if (cardID == null)
            tareasRepository.findAll().forEach(tareas::add);
        else
            tareas.addAll(tareasRepository.findByCardIDContaining(cardID));

        return tareas;
    }
     */
}
