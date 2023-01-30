package org.bedu.ods.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "proyectos")
public class Proyectos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private long id;

    @NonNull
    @ToString.Include
    @Size(min = 15, message = "El nombre del proyecto debe ser por lo menos de 15 caracteres.")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NonNull
    @Column(name = "descripcion")
    private String descripcion;

    @NonNull
    @Future(message = "La fecha no puede ser en una fecha en el pasado.")
    @Column(name = "fecha", nullable = false)
    private Date fechaProyecto;

    @NonNull
    @Future(message = "La fecha no puede ser en una fecha en el pasado.")
    @Column(name = "fecha_compromiso", nullable = false)
    private Date fechaCompromiso;

    @NonNull
    @Future(message = "La fecha no puede ser en una fecha en el pasado.")
    @Column(name = "fecha_entrega", nullable = false)
    private Date fechaEntrega;

    @NonNull
    @Column(name = "clase_servicio", nullable = false)
    private String claseServicio;

    @NonNull
    @Column(name = "limite_wip", nullable = false)
    private int limiteWip;

    @NonNull
    @Column(name = "estatus", nullable = false)
    private String estatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(name = "proyecto_usuario",
            joinColumns = { @JoinColumn(name = "proyecto_id") },
            inverseJoinColumns = { @JoinColumn(name = "usuario_id") })
    private Set<Usuarios> usuarios = new HashSet<>();

}
