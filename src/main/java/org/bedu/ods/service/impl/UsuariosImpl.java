package org.bedu.ods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bedu.ods.entity.dto.UsuariosDTO;
import org.bedu.ods.entity.Usuarios;
import org.bedu.ods.entity.mapper.UsuariosMapper;
import org.bedu.ods.exception.ResourceNotFoundException;
import org.bedu.ods.repository.IUsuariosRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuariosImpl implements IUsuariosService {

    private final IUsuariosRepository usuariosRepository;
    private final UsuariosMapper usuariosMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<UsuariosDTO> findAll(String nombre) {
        LinkedList<Usuarios> usuarios  = new LinkedList<>();
        LinkedList<UsuariosDTO> usuariosDto;
        if (nombre == null) {
            usuariosRepository.findAll().forEach(usuarios::add);
            usuariosDto = new LinkedList<>(usuariosMapper.listaUsuariosToUsuariosDto(usuarios));
        }
        else
            usuariosDto = new LinkedList<>(usuariosMapper.listaUsuariosToUsuariosDto(
                    usuariosRepository.findByNombreContaining(nombre)));

        return usuariosDto;
    }

    public UsuariosDTO findById(long id)
    {
        return usuariosMapper.usuariosToUsuariosDto(
                usuariosRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No se encontró usuario con id = " + id))
        );
    }

    public List<UsuariosDTO> findUsuariosByProyectosId(Long proyectoId)
    {
        return usuariosMapper.listaUsuariosToUsuariosDto(
                usuariosRepository.findUsuariosByProyectosId(proyectoId));
    }

    public UsuariosDTO findByIdAndProyectosId(Long idUsuario, Long idProyecto)
    {
        return usuariosMapper.usuariosToUsuariosDto(
                usuariosRepository.findByIdAndProyectosId(idUsuario, idProyecto)
        );
    }

    @Override
    public UsuariosDTO save(UsuariosDTO usuarioDto)
    {
        if (usuariosRepository.findByCorreo(usuarioDto.getCorreo()).isPresent()) {
            throw new ResourceNotFoundException("El usuario ya se encuentra registrado.");
        }
        if (!usuarioDto.getPassword().equals(usuarioDto.getRePassword())) {
            throw new ResourceNotFoundException("La contraseña no coincide");
        }
        usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getRePassword()));

        return usuariosMapper.
                usuariosToUsuariosDto(usuariosRepository
                    .save(usuariosMapper.usuariosDtoToUsuarios(usuarioDto))
                );
    }

    @Override
    public UsuariosDTO update(long id, UsuariosDTO usuarioDto) {
        Usuarios _usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró usuario con id = " + id));
                _usuario.setNombre(usuarioDto.getNombre());
                _usuario.setCorreo(usuarioDto.getCorreo());
                _usuario.setRol(usuarioDto.getRol());
                _usuario.setAlias(usuarioDto.getAlias());
                _usuario.setPassword(usuarioDto.getPassword());
        return usuariosMapper.
                usuariosToUsuariosDto(usuariosRepository
                        .save(_usuario)
                );
    }

    @Override
    public void delete(long id) {
        Usuarios _usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró usuario con id = " + id));
            usuariosRepository.delete(_usuario);
    }

    @Override
    public UsuariosDTO findLastUser() {
        List<Usuarios> listaUsuarios = new ArrayList<>();
        usuariosRepository.findAll().forEach(listaUsuarios::add);
        return usuariosMapper.usuariosToUsuariosDto(
                listaUsuarios.get(listaUsuarios.size()-1));
    }

    @Override
    public UsuariosDTO findByCorreo(String correo) {
        return usuariosMapper.usuariosToUsuariosDto(
                usuariosRepository.findByCorreo(correo)
                        .orElseThrow(() -> new ResourceNotFoundException("No se encontró usuario  = " + correo))
        );
    }

    public UserDetails UsersDetailsContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        UserDetails usrDetail = (UserDetails) auth.getPrincipal();
        log.info("USERNAME: {} ", usrDetail.getUsername());
        usrDetail.getAuthorities().forEach( (x) -> log.info("ROLES: {}", x.getAuthority() ) );
        return  usrDetail;
    }

}
