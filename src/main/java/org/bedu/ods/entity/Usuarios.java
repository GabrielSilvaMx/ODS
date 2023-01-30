package org.bedu.ods.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private long id;

    @NonNull()
    @ToString.Include
    @Size(min = 8, message = "El nombre debe tener al menos 8 letras.")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NonNull
    @ToString.Include
    @Email(message = "Se debe de utilizar un formato correcto de correo electrónico.")
    @NotEmpty(message = "Es requerido un correo electrónico")
    @Column(name = "correo", nullable = false)
    private String correo;

    @NonNull
    @ToString.Include
    @NotEmpty(message = "El alias es requerido")
    @Size(min = 5, max = 10, message = "El alias debe tener al menos 5 letras y ser menor a 10")
    @Column(name = "alias", nullable = false)
    private String alias;

    @NonNull
    @ToString.Include
    @NotEmpty(message = "La contraseña es requerido")
    @Size(min = 8, message = "La contraseña debe contener al menos 8 caracteres.")
    @Column(name = "password", nullable = false)
    private String password;

    @NonNull
    @ToString.Include
    @Column(name = "rol", nullable = false)
    @NotEmpty(message = "El rol es requerido { admin | user }")
    private String rol;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "usuarios")
    @JsonIgnore
    private Set<Proyectos> proyectos = new HashSet<>();

}
