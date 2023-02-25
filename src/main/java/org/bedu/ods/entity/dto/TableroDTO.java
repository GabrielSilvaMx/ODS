package org.bedu.ods.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import lombok.*;

import java.util.LinkedList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class TableroDTO {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JoinColumn(name = "tasks")
    private LinkedList<TareasDTO> tasks;
}
