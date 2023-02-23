package org.bedu.ods.controller;

import lombok.RequiredArgsConstructor;
import org.bedu.ods.Config.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    // ResponseEntity<Map<Object, Object>>
    @PostMapping("/signin")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody AuthenticationRequest data) {
        try {
            String rol = "MANAGER";
            String username = data.getUsername();
            //System.out.println(data.getUsername());
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(authentication);
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("roles", rol);
            return new ResponseEntity<>(model,HttpStatus.OK);
            //return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

}
