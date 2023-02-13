package org.bedu.ods.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bedu.ods.entity.dto.ProyectosDTO;
import org.bedu.ods.entity.dto.TareasDTO;
import org.bedu.ods.service.impl.ITareasService;
import org.bedu.ods.service.hateoas.TareasModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tareas")
public class TareasController {

    private final ITareasService tareasService;

    private final TareasModelAssembler modelAssembler;

    @GetMapping(path="/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ROLE_MANAGER','USER','ROLE_USER')")
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
    @PreAuthorize("hasAnyRole('MANAGER','ROLE_MANAGER','USER','ROLE_USER')")
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
    @PreAuthorize("hasAnyRole('MANAGER','ROLE_MANAGER','USER','ROLE_USER')")
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
    @PreAuthorize("hasAnyRole('MANAGER','ROLE_MANAGER','USER','ROLE_USER')")
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

    @PostMapping("/proyecto/{idProyecto}/usuario/{idUsuario}")
    @PreAuthorize("hasAnyRole('MANAGER','ROLE_MANAGER','USER','ROLE_USER')")
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
    @PreAuthorize("hasAnyRole('MANAGER','ROLE_MANAGER','USER','ROLE_USER')")
    public ResponseEntity<EntityModel<TareasDTO>> updateTarea(
            @PathVariable("id") long id,
            @RequestBody TareasDTO tareasDto)
    {
        TareasDTO updateTareasDto = tareasService.update(id, tareasDto);
        EntityModel<TareasDTO> model =  modelAssembler.toModel(updateTareasDto);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ROLE_MANAGER','USER','ROLE_USER')")
    public ResponseEntity<HttpStatus> deleteTarea(
            @PathVariable("id") long id)
    {
        tareasService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
/*

    @GetMapping("/tareas/proyecto/{proyectoId}/usuario/{usuarioId}")
    public ResponseEntity<List<Tareas>> getAllTareasByProyectoYUsuario(@PathVariable("proyectoId") long proyectoId, @PathVariable("usuarioId") long usuarioId)
    {
        List<Tareas> _tareas = tareasService.findByProyectoYUsuario(proyectoId, usuarioId);
        if (_tareas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(_tareas, HttpStatus.OK);
    }

    @GetMapping("/tareas/proyecto/{proyectoId}")
    public ResponseEntity<List<Tareas>> getAllTareasByProyecto(@PathVariable("proyectoId") long proyectoId) {
        List<Tareas> _tareas = tareasService.findProyecto(proyectoId);
        if (_tareas.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(_tareas, HttpStatus.OK);
    }

    @GetMapping("/tareas/usuario/{usuarioId}")
    public ResponseEntity<List<Tareas>> getAllTareasByUsuario(@PathVariable("usuarioId") long usuarioId) {
        List<Tareas> _tareas = tareasService.findByUsuario(usuarioId);
        if (_tareas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(_tareas, HttpStatus.OK);
    }

    @PostMapping("/tareas/proyecto/{proyectoId}/usuario/{usuarioId}")
    public ResponseEntity<Tareas> createTareas(@PathVariable("proyectoId") long proyectoId, @PathVariable("usuarioId") long usuarioId, @RequestBody Tareas tarea) {
        return new ResponseEntity<>(tareasService.save(tarea,proyectoId,usuarioId), HttpStatus.CREATED);
    }

    @PutMapping("/tareas/{id}")
    public ResponseEntity<Tareas> updateTareas(@PathVariable("id") long id, @RequestBody Tareas tarea) {
        return new ResponseEntity<>(tareasService.update(id, tarea), HttpStatus.OK);
    }

    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<HttpStatus> deleteTareas(@PathVariable("id") long id) {
        tareasService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
*/
}