package org.bedu.ods.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
public class SecurityUser implements UserDetails {

    private transient final Usuarios usuarios;

    public SecurityUser(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getUsername() {
        return usuarios.getCorreo();
    }

    @Override
    public String getPassword() {
        return usuarios.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("Security getAuthorities Roles: " + usuarios.getRol().toUpperCase());
        return Arrays.stream(usuarios
                        .getRol().toUpperCase()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
