package org.bedu.ods.service.impl;

import org.bedu.ods.entity.dto.ProyectosDTO;

import java.util.List;

public interface IProyectosService {
    List<ProyectosDTO> findAll(String nombre);

    ProyectosDTO findById(long id);

    ProyectosDTO save(ProyectosDTO proyectosDto);

    ProyectosDTO update( long id, ProyectosDTO proyectosDto);

    void delete(long id);

    ProyectosDTO findLastProyecto();

    ProyectosDTO addUsuarioToProyecto(Long idProyecto, Long idUsuario);

    ProyectosDTO deleteUsuarioByProyecto(Long idProyecto, Long idUsuario);

}
