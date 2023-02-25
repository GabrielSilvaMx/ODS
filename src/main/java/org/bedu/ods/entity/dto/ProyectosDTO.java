package org.bedu.ods.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ProyectosDTO  extends RepresentationModel<ProyectosDTO> {

    @JsonProperty("id")
    private long id;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("fechaProyecto")
    private Date fechaProyecto;

    @JsonProperty("fechaCompromiso")
    private Date fechaCompromiso;

    @JsonProperty("fechaEntrega")
    private Date fechaEntrega;

    @JsonProperty("claseServicio")
    private String claseServicio;

    @JsonProperty("limiteWip")
    private int limiteWip;

    @JsonProperty("estatus")
    private String estatus;

}
