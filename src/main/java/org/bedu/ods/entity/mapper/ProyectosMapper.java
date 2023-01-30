package org.bedu.ods.entity.mapper;

import org.bedu.ods.entity.Proyectos;
import org.bedu.ods.entity.dto.ProyectosDTO;
import org.mapstruct.Mapper;

import java.util.LinkedList;

@Mapper( componentModel = "spring")
public interface ProyectosMapper {

    Proyectos proyectosDtoToProyectos(ProyectosDTO proyectosDto);

    LinkedList<ProyectosDTO> listaProyectosToProyectosDto(LinkedList<Proyectos> proyectos);

    ProyectosDTO proyectosToProyectosDto(Proyectos proyectos);
}
