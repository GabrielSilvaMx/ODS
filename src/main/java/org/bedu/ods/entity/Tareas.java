package org.bedu.ods.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "tareas")
public class Tareas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private long id;

    @NonNull
    @ToString.Include
    @Size(min = 10, message = "La CARD ID debe ser por lo menos de 10 caracteres.")
    @Column(name = "card_id", nullable = false)
    private String cardID;

    @NonNull
    @ToString.Include
    @Column(name = "fecha", nullable = false)
    @Future(message = "La fecha no puede ser en una fecha en el pasado.")
    private Date fechaTarea;

    @NonNull
    @ToString.Include
    @Column(name = "prioridad", nullable = false)
    private int prioridad;

    @NonNull
    @ToString.Include
    @Column(name = "transicion", nullable = false)
    private String transicion;

    @NonNull
    @ToString.Include
    @Column(name = "estado", nullable = false)
    private String estado;

    @NonNull
    @ToString.Include
    @Size(min = 15, message = "La descripción debe ser por lo menos de 15 caracteres.")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NonNull
    @ToString.Include
    @Column(name = "tiempo_estimado", nullable = false)
    private String tiempoEstimado;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proyecto_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Proyectos proyecto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Usuarios usuario;
}
