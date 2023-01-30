package org.bedu.ods.service.impl;

import org.bedu.ods.entity.dto.UsuariosDTO;

import java.util.List;

public interface IUsuariosService {

    List<UsuariosDTO> findAll(String nombre);

    UsuariosDTO findById(long id);

    List<UsuariosDTO> findUsuariosByProyectosId(Long proyectoId);

    UsuariosDTO findByIdAndProyectosId(Long idUsuario, Long idProyecto);

    UsuariosDTO save(UsuariosDTO usuarioDto);

    UsuariosDTO update(long id, UsuariosDTO usuarioDto);

    void delete(long id);

    UsuariosDTO findLastUser();

    UsuariosDTO findByCorreo(String correo);

}
