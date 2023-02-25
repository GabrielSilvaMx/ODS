package org.bedu.ods.service.hateoas;

import org.bedu.ods.controller.UsuariosController;
import org.bedu.ods.entity.dto.UsuariosDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuariosModelAssembler
        implements RepresentationModelAssembler<UsuariosDTO, EntityModel<UsuariosDTO>>
{

    @Override
    public EntityModel<UsuariosDTO> toModel(UsuariosDTO entity) {

        EntityModel<UsuariosDTO> usuariosModel = EntityModel.of(entity);

        usuariosModel.add(linkTo(methodOn(UsuariosController.class)
                .getByIdUsuario(entity.getId()))
                .withSelfRel());
        usuariosModel.add(linkTo(methodOn(UsuariosController.class)
                .getAllUsuarios(null))
                .withRel(IanaLinkRelations.COLLECTION));

        return usuariosModel;
    }
}
