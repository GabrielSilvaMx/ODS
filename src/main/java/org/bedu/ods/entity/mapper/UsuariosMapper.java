package org.bedu.ods.entity.mapper;

import org.bedu.ods.entity.dto.UsuariosDTO;
import org.bedu.ods.entity.Usuarios;
import org.mapstruct.Mapper;

import java.util.LinkedList;
import java.util.List;

@Mapper( componentModel = "spring")
public interface UsuariosMapper {

    Usuarios usuariosDtoToUsuarios(UsuariosDTO usuariosDto);

    LinkedList<UsuariosDTO> listaUsuariosToUsuariosDto(LinkedList<Usuarios> usuarios);

    LinkedList<UsuariosDTO> listaUsuariosStreamerToUsuariosDto(List<Usuarios> usuarios);

    UsuariosDTO usuariosToUsuariosDto(Usuarios usuarios);
}
