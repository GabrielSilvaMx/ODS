package org.bedu.ods.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.bedu.ods.entity.Proyectos;
import org.bedu.ods.entity.Usuarios;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class TareasDTO extends RepresentationModel<TareasDTO> {

    @JsonProperty("id")
    private long id;

    @JsonProperty("cardID")
    private String cardID;

    @JsonProperty("date")
    private Date fechaTarea;

    @JsonProperty("priority")
    private String prioridad;

    @JsonProperty("transicion")
    private String transicion;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("description")
    private String descripcion;

    @JsonProperty("tiempoEstimado")
    private String tiempoEstimado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "proyecto_id")
    private Proyectos proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;
}
