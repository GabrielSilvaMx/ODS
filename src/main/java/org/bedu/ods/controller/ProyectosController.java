package org.bedu.ods.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bedu.ods.entity.dto.ProyectosDTO;
import org.bedu.ods.entity.dto.UsuariosDTO;
import org.bedu.ods.service.hateoas.UsuariosModelAssembler;
import org.bedu.ods.service.impl.IProyectosService;
import org.bedu.ods.service.hateoas.ProyectosModelAssembler;
import org.bedu.ods.service.impl.UsuariosImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proyectos")
public class ProyectosController {

    private final IProyectosService proyectosService;
    private final UsuariosImpl usuariosService;

    private final ProyectosModelAssembler proyectosModelAssembler;

    private final UsuariosModelAssembler usuariosModelAssembler;

    @GetMapping(path="/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ROLE_MANAGER')")
    public @ResponseBody ResponseEntity<EntityModel<ProyectosDTO>> getProyectosById(
            @PathVariable("id") Long id)
    {
        try {
            ProyectosDTO proyectos = proyectosService.findById(id);
            EntityModel<ProyectosDTO> model = proyectosModelAssembler
                    .toModel(proyectos);
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        catch  (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ROLE_MANAGER')")
    public @ResponseBody ResponseEntity<CollectionModel<EntityModel<ProyectosDTO>>> getAllProyectos(
            @RequestParam(required = false) String nombre)
    {
        List<EntityModel<ProyectosDTO>> listaProyectosModel = proyectosService.findAll(nombre)
                .stream()
                .map(proyectosModelAssembler::toModel)
                .toList();
        if ((long) listaProyectosModel.size() <=0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        CollectionModel<EntityModel<ProyectosDTO>> collectionModel = CollectionModel.of(listaProyectosModel);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{idProyecto}/usuarios")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ROLE_MANAGER', 'USER', 'ROLE_USER')")
    public @ResponseBody ResponseEntity<CollectionModel<EntityModel<UsuariosDTO>>> getAllUsuariosByProyecto(
            @PathVariable("idProyecto") long idProyecto)
    {
        List<EntityModel<UsuariosDTO>> listaUsuariosModel = usuariosService.findUsuariosByProyectosId(idProyecto)
                .stream()
                .map(usuariosModelAssembler::toModel)
                .toList();
        if ((long) listaUsuariosModel.size() <=0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        CollectionModel<EntityModel<UsuariosDTO>> collectionModel = CollectionModel.of(listaUsuariosModel);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{idProyecto}/usuarios/{idUsuario}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ROLE_MANAGER', 'USER', 'ROLE_USER')")
    public @ResponseBody ResponseEntity<EntityModel<UsuariosDTO>> getUsuarioByProyecto(
            @PathVariable("idProyecto") long idProyecto,
            @PathVariable("idUsuario") long idUsuario)
    {
        try {
            UsuariosDTO usuario = usuariosService.findByIdAndProyectosId(idUsuario, idProyecto);
            EntityModel<UsuariosDTO> model = usuariosModelAssembler
                    .toModel(usuario);
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        catch  (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/{idProyecto}/usuarios/{idUsuario}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ROLE_MANAGER', 'USER', 'ROLE_USER')")
    public @ResponseBody ResponseEntity<EntityModel<ProyectosDTO>> addUsuarioByProyecto(
            @PathVariable("idProyecto") long idProyecto,
            @PathVariable("idUsuario") long idUsuario)
    {
        ProyectosDTO saveProyectoDto = proyectosService.addUsuarioToProyecto(idProyecto, idUsuario);
        EntityModel<ProyectosDTO> model = proyectosModelAssembler.toModel(saveProyectoDto);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping("/{idProyecto}/usuarios/{idUsuario}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EntityModel<ProyectosDTO>> deleteUsuarioByProyecto(
            @PathVariable("idProyecto") long idProyecto,
            @PathVariable("idUsuario") long idUsuario)
    {
        ProyectosDTO updateProyectoDto = proyectosService.deleteUsuarioByProyecto(idProyecto, idUsuario);
        EntityModel<ProyectosDTO> model = proyectosModelAssembler.toModel(updateProyectoDto);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ROLE_MANAGER')")
    public ResponseEntity<EntityModel<ProyectosDTO>> createProyecto(
            @Valid @RequestBody ProyectosDTO proyectoDto)
    {
        ProyectosDTO saveProyectoDto = proyectosService.save(proyectoDto);
        EntityModel<ProyectosDTO> model = proyectosModelAssembler.toModel(saveProyectoDto);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ROLE_MANAGER')")
    public ResponseEntity<EntityModel<ProyectosDTO>> updateProyecto(
            @PathVariable("id") long id,
            @RequestBody ProyectosDTO proyectoDto)
    {
        ProyectosDTO updateProyectoDto = proyectosService.update(id, proyectoDto);
        EntityModel<ProyectosDTO> model = proyectosModelAssembler.toModel(updateProyectoDto);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HttpStatus> deleteProyecto(
            @PathVariable("id") long id)
    {
        proyectosService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
