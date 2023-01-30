package org.bedu.ods.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UsuariosDTO extends RepresentationModel<UsuariosDTO>  {

    @JsonProperty("id")
    private long id;

    @JsonProperty("usuario")
    private String nombre;

    @JsonProperty("correo")
    private String correo;

    @NonNull
    @JsonProperty("alias")
    private String alias;

    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NonNull
    @JsonProperty("rol")
    private String rol;

    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String rePassword = "";

    private Set<String> authorities=new HashSet<>();

}
