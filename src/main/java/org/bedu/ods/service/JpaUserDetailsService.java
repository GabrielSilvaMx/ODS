package org.bedu.ods.service;

import lombok.AllArgsConstructor;
import org.bedu.ods.entity.SecurityUser;
import org.bedu.ods.repository.IUsuariosRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final IUsuariosRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("get details: "+username);
        return userRepository
                .findByCorreo(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    public boolean isAuthorized(String rol) {
        Authentication auth = getAuth();
        return (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(rol)));
    }

    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
