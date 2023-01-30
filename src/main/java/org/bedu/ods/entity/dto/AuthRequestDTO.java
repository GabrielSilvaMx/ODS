package org.bedu.ods.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDTO(@NotNull @Email String username, @NotNull String password)
{
    public AuthRequestDTO() {
        this(null, null);
    }
}
