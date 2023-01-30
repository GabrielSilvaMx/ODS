package org.bedu.ods.service.hateoas;

import org.bedu.ods.controller.ProyectosController;
import org.bedu.ods.controller.TareasController;
import org.bedu.ods.controller.UsuariosController;
import org.bedu.ods.entity.dto.TareasDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TareasModelAssembler
        implements RepresentationModelAssembler<TareasDTO, EntityModel<TareasDTO>>
{

    @Override
    public EntityModel<TareasDTO> toModel(TareasDTO entity) {

        EntityModel<TareasDTO> tareasModel = EntityModel.of(entity);

        tareasModel.add(linkTo(methodOn(TareasController.class)
                .getTareasById(entity.getId()))
                .withSelfRel());
        tareasModel.add(linkTo(methodOn(ProyectosController.class)
                .getProyectosById(entity.getProyecto().getId()))
                .withRel("proyecto"));
        tareasModel.add(linkTo(methodOn(UsuariosController.class)
                .getByIdUsuario(entity.getUsuario().getId()))
                .withRel("usuario"));
        tareasModel.add(linkTo(methodOn(TareasController.class)
                .getTareasByProyecto(entity.getProyecto().getId()))
                .withRel(IanaLinkRelations.COLLECTION));

        return tareasModel;
    }

}
