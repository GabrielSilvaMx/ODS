package org.bedu.ods.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bedu.ods.entity.dto.TableroDTO;
import org.bedu.ods.entity.dto.TareasDTO;
import org.bedu.ods.service.impl.ITareasService;
import org.bedu.ods.service.hateoas.TareasModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tareas")
public class TareasController {

    private final ITareasService tareasService;

    private final TareasModelAssembler modelAssembler;

    @GetMapping(path="/{id}")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody ResponseEntity<EntityModel<TareasDTO>> getTareasById(@PathVariable("id") Long id)
    {
        try {
            TareasDTO tarea = tareasService.findById(id);
            EntityModel<TareasDTO> model = modelAssembler
                    .toModel(tarea);
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        catch  (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path="/proyecto/{idProyecto}")
    public @ResponseBody ResponseEntity<CollectionModel<EntityModel<TareasDTO>>> getTareasByProyecto(@PathVariable("idProyecto") Long idProyecto)
    {
        List<EntityModel<TareasDTO>> listaProyectosModel = tareasService.findTareasByProyecto(idProyecto)
                .stream()
                .map(modelAssembler::toModel)
                .collect(Collectors.toList());
        if (listaProyectosModel.stream().count()<=0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        CollectionModel<EntityModel<TareasDTO>> collectionModel = CollectionModel.of(listaProyectosModel);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping(path="/usuario/{idUsuario}")
    public @ResponseBody ResponseEntity<CollectionModel<EntityModel<TareasDTO>>> getTareasByUsuario(@PathVariable("idUsuario") Long idUsuario)
    {

        List<EntityModel<TareasDTO>> listaProyectosModel = tareasService.findTareasByUsuario(idUsuario)
                .stream()
                .map(modelAssembler::toModel)
                .collect(Collectors.toList());
        if (listaProyectosModel.stream().count()<=0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        CollectionModel<EntityModel<TareasDTO>> collectionModel = CollectionModel.of(listaProyectosModel);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping(path="/proyecto/{idProyecto}/usuario/{idUsuario}")
    public @ResponseBody ResponseEntity<CollectionModel<EntityModel<TareasDTO>>> getTareasByProyectoAndUsuario(@PathVariable("idProyecto") Long idProyecto, @PathVariable("idUsuario") Long idUsuario)
    {
        List<EntityModel<TareasDTO>> listaProyectosModel = tareasService.findTareasByProyectoAndUsuario(idProyecto, idUsuario)
                .stream()
                .map(modelAssembler::toModel)
                .collect(Collectors.toList());
        if (listaProyectosModel.stream().count()<=0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        CollectionModel<EntityModel<TareasDTO>> collectionModel = CollectionModel.of(listaProyectosModel);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);

    }

    @GetMapping(path="/proyecto/{idProyecto}/usuario/{idUsuario}/tablero")
    public ResponseEntity<Set<TableroDTO>> getTableroByProyectoAndUsuario(
            @PathVariable("idProyecto") Long idProyecto,
            @PathVariable("idUsuario") Long idUsuario)
    {

        return new ResponseEntity<>(tareasService.
                getTableroByProyectoAndUsuario(idProyecto, idUsuario), HttpStatus.OK);
    }

    @PostMapping("/proyecto/{idProyecto}/usuario/{idUsuario}")
    public @ResponseBody ResponseEntity<EntityModel<TareasDTO>> addTareaByProyectoAndUsuario(
            @PathVariable("idProyecto") long idProyecto,
            @PathVariable("idUsuario") long idUsuario,
            @Valid @RequestBody TareasDTO tareasDTO )
    {
        TareasDTO saveTareasDto = tareasService.addTareaByProyectoAndUsuario(tareasDTO, idProyecto, idUsuario);
        EntityModel<TareasDTO> model = modelAssembler.toModel(saveTareasDto);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TareasDTO>> updateTarea(
            @PathVariable("id") long id,
            @RequestBody TareasDTO tareasDto)
    {
        TareasDTO updateTareasDto = tareasService.update(id, tareasDto);
        EntityModel<TareasDTO> model =  modelAssembler.toModel(updateTareasDto);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTarea(
            @PathVariable("id") long id)
    {
        tareasService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}