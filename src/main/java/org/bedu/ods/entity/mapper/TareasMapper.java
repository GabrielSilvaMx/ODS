package org.bedu.ods.entity.mapper;

import org.bedu.ods.entity.Tareas;
import org.bedu.ods.entity.dto.TareasDTO;
import org.mapstruct.Mapper;

import java.util.LinkedList;

@Mapper( componentModel = "spring")
public interface TareasMapper {

    Tareas tareasDtoToTareas(TareasDTO tareasDto);

    LinkedList<TareasDTO> listaTareasToTareasDto(LinkedList<Tareas> tareas);

    TareasDTO tareasToTareasDto(Tareas tareas);
}
