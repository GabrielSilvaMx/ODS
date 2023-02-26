package org.bedu.ods.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bedu.ods.entity.dto.UsuariosDTO;
import org.bedu.ods.service.hateoas.UsuariosModelAssembler;
import org.bedu.ods.service.impl.UsuariosImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/usuarios")
public class UsuariosController {

    private final UsuariosImpl usuariosService;

    private final UsuariosModelAssembler modelAssembler;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/registro")
    public ResponseEntity<EntityModel<UsuariosDTO>> createUsuario(@Valid @RequestBody UsuariosDTO usuarioDto) {
        UsuariosDTO saveUsuarioDto = usuariosService.save(usuarioDto);
        EntityModel<UsuariosDTO> model = modelAssembler.toModel(saveUsuarioDto);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/welcome")
    public @ResponseBody ResponseEntity<String> saludo() {
        UserDetails usrDetailcontext = usuariosService.UsersDetailsContext();
        return new ResponseEntity<>("Bienvenido " + usrDetailcontext.getUsername(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path="/{id}")
    public @ResponseBody ResponseEntity<EntityModel<UsuariosDTO>> getByIdUsuario(@PathVariable("id") Long id)
    {
        try {
            UsuariosDTO usuarios = usuariosService.findById(id);
            EntityModel<UsuariosDTO> model = modelAssembler
                    .toModel(usuarios);
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        catch  (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public @ResponseBody ResponseEntity<CollectionModel<EntityModel<UsuariosDTO>>> getAllUsuarios(@RequestParam(required = false) String nombre)
    {

        List<EntityModel<UsuariosDTO>> listaUsuariosModel = usuariosService.findAll(nombre)
                .stream()
                .map(modelAssembler::toModel)
                .toList();

        if ((long) listaUsuariosModel.size() <= 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        CollectionModel<EntityModel<UsuariosDTO>> collectionModel = CollectionModel.of(listaUsuariosModel);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<UsuariosDTO>> updateUsuario(@PathVariable("id") long id, @RequestBody UsuariosDTO usuarioDto) {
        UsuariosDTO updateUsuarioDto = usuariosService.update(id, usuarioDto);
        EntityModel<UsuariosDTO> model = modelAssembler.toModel(updateUsuarioDto);
        return new ResponseEntity<>(model, HttpStatus.CREATED); // https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/PUT
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable("id") long id) {
        usuariosService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/204
    }

}
