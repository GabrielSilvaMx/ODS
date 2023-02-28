package org.bedu.ods.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/usuario")
    @PreAuthorize("isAuthenticated()")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/manager")
    @PreAuthorize("isAuthenticated()")
    public String moderatorAccess() {
        return "Manager Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("isAuthenticated()")
    public String adminAccess() {
        return "Admin Board.";
    }
}
