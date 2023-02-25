package org.bedu.ods.service.impl;

import lombok.RequiredArgsConstructor;
import org.bedu.ods.entity.Proyectos;
import org.bedu.ods.entity.dto.ProyectosDTO;
import org.bedu.ods.entity.mapper.ProyectosMapper;
import org.bedu.ods.exception.ResourceNotFoundException;
import org.bedu.ods.repository.IProyectosRepository;
import org.bedu.ods.repository.IUsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProyectosImpl implements IProyectosService {
    private final IProyectosRepository proyectosRepository;

    private final IUsuariosRepository usuariosRepository;

    private final ProyectosMapper proyectosMapper;

    private static final String TXTNOTFOUND = "No se encontró proyecto con id ";


    @Override
    public List<ProyectosDTO> findAll(String nombre) {
        LinkedList<Proyectos> proyectos  = new LinkedList<>();
        LinkedList<ProyectosDTO> proyectosDto;
        if (nombre == null) {
            proyectosRepository.findAll().forEach(proyectos::add);
            proyectosDto = new LinkedList<>(proyectosMapper.listaProyectosToProyectosDto(proyectos));
        }
        else
            proyectosDto = new LinkedList<>(proyectosMapper.listaProyectosToProyectosDto(
                    proyectosRepository.findByNombreContaining(nombre)));

        return proyectosDto;
    }

    @Override
    public ProyectosDTO findById(long id)
    {
        return proyectosMapper.proyectosToProyectosDto(
                proyectosRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(TXTNOTFOUND + id))
        );
    }

    @Override
    public ProyectosDTO save(ProyectosDTO proyectosDto)
    {
        return proyectosMapper.
                proyectosToProyectosDto(proyectosRepository
                        .save(proyectosMapper.proyectosDtoToProyectos(proyectosDto))
                );
    }

    @Override
    public ProyectosDTO update(long id, ProyectosDTO proyectosDto) {
        Proyectos updateProyecto = proyectosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TXTNOTFOUND + id));
        updateProyecto.setNombre(proyectosDto.getNombre());
        updateProyecto.setDescripcion(proyectosDto.getDescripcion());
        updateProyecto.setFechaProyecto(proyectosDto.getFechaProyecto());
        updateProyecto.setFechaCompromiso(proyectosDto.getFechaCompromiso());
        updateProyecto.setFechaEntrega(proyectosDto.getFechaEntrega());
        updateProyecto.setClaseServicio(proyectosDto.getClaseServicio());
        updateProyecto.setLimiteWip(proyectosDto.getLimiteWip());
        updateProyecto.setEstatus(proyectosDto.getEstatus());
        updateProyecto.setNombre(proyectosDto.getNombre());

        return proyectosMapper.
                proyectosToProyectosDto(proyectosRepository
                        .save(updateProyecto)
                );
    }

    @Override
    public void delete(long id) {
        Proyectos proyecto = proyectosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TXTNOTFOUND + id));
        proyectosRepository.delete(proyecto);
    }

    @Override
    public ProyectosDTO findLastProyecto() {
        List<Proyectos> listaProyectos = new ArrayList<>();
        proyectosRepository.findAll().forEach(listaProyectos::add);
        return proyectosMapper.proyectosToProyectosDto(
                listaProyectos.get(listaProyectos.size()-1));
    }

    public ProyectosDTO addUsuarioToProyecto(Long idProyecto, Long idUsuario)
    {
        Proyectos proyecto = proyectosRepository.findById(idProyecto)
                .orElseThrow(() -> new ResourceNotFoundException(TXTNOTFOUND + idProyecto)
                );
        proyecto.getUsuarios().add(usuariosRepository.getReferenceById(idUsuario));

        return proyectosMapper.
                proyectosToProyectosDto(proyectosRepository
                        .save(proyecto)
                );
    }

    public ProyectosDTO deleteUsuarioByProyecto(Long idProyecto, Long idUsuario)
    {
        Proyectos proyecto = proyectosRepository.findById(idProyecto)
                .orElseThrow(() -> new ResourceNotFoundException(TXTNOTFOUND + idProyecto)
                );
        proyecto.getUsuarios().remove(usuariosRepository.getReferenceById(idUsuario));
        return proyectosMapper.
                proyectosToProyectosDto(proyectosRepository
                        .save(proyecto)
                );
    }

}