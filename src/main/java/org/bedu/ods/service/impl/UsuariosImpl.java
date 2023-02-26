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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuariosImpl implements IUsuariosService {

    private final IUsuariosRepository usuariosRepository;
    private final UsuariosMapper usuariosMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String TXTNOTFOUND = "No se encontró usuario con id ";

    @Transactional
    @Override
    public List<UsuariosDTO> findAll(String nombre) {
        LinkedList<Usuarios> usuarios  = new LinkedList<>();
        LinkedList<UsuariosDTO> usuariosDto;

        if (nombre == null) {
            try (Stream<Usuarios> listUsuarios = usuariosRepository.findAll().stream() ) {
                listUsuarios.forEach(usuarios::add);
            }
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
                        .orElseThrow(() -> new ResourceNotFoundException(TXTNOTFOUND + id))
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
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TXTNOTFOUND + id));
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setCorreo(usuarioDto.getCorreo());
        usuario.setRol(usuarioDto.getRol());
        usuario.setAlias(usuarioDto.getAlias());
        usuario.setPassword(usuarioDto.getPassword());
        return usuariosMapper.
                usuariosToUsuariosDto(usuariosRepository
                        .save(usuario)
                );
    }

    @Override
    public void delete(long id) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TXTNOTFOUND + id));
        usuariosRepository.delete(usuario);
    }

    @Override
    public UsuariosDTO findLastUser() {
        List<Usuarios> listaUsuarios = new ArrayList<>();
        try (Stream<Usuarios> listUsuarios = usuariosRepository.findAll().stream() ) {
            listUsuarios.forEach(listaUsuarios::add);
        }
        return usuariosMapper.usuariosToUsuariosDto(
                listaUsuarios.get(listaUsuarios.size()-1));
    }

    @Override
    public UsuariosDTO findByCorreo(String correo) {
        return usuariosMapper.usuariosToUsuariosDto(
                usuariosRepository.findByCorreo(correo)
                        .orElseThrow(() -> new ResourceNotFoundException(TXTNOTFOUND + correo))
        );
    }

    public UserDetails UsersDetailsContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        UserDetails usrDetail = (UserDetails) auth.getPrincipal();
        usrDetail.getAuthorities().forEach( x ->
                log.info("User Details Context: {} ROLES: {} ", usrDetail.getUsername(), x.getAuthority() ) );
        return  usrDetail;
    }

}
