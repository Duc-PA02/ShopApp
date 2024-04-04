package com.example.shopappbackend.controllers;

import com.example.shopappbackend.models.Role;
import com.example.shopappbackend.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;
    @GetMapping("")
    public ResponseEntity<?> getAllRoles(){
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok().body(roles);
    }
}
