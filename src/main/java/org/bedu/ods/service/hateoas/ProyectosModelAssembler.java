package org.bedu.ods.service.hateoas;

import org.bedu.ods.controller.ProyectosController;
import org.bedu.ods.entity.dto.ProyectosDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProyectosModelAssembler
        implements RepresentationModelAssembler<ProyectosDTO, EntityModel<ProyectosDTO>>
{

    @Override
    public EntityModel<ProyectosDTO> toModel(ProyectosDTO entity) {

        EntityModel<ProyectosDTO> proyectosModel = EntityModel.of(entity);

        proyectosModel.add(linkTo(methodOn(ProyectosController.class)
                .getProyectosById(entity.getId()))
                .withSelfRel());

        proyectosModel.add(linkTo(methodOn(ProyectosController.class)
                .getAllUsuariosByProyecto(entity.getId()))
                .withRel("usuarios"));

        proyectosModel.add(linkTo(methodOn(ProyectosController.class)
                .getAllProyectos(null))
                .withRel(IanaLinkRelations.COLLECTION));

        return proyectosModel;
    }

}
