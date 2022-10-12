package com.cisco.regina.rest;

import java.util.List;

import com.cisco.regina.modelo.Roles;
import com.cisco.regina.servicio.RolesServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RolesControlador {

    @Autowired
    RolesServicio rolesServicio;

    @GetMapping("/todos")
    ResponseEntity<?> buscarTodos() {
        return new ResponseEntity<List<Roles>>(rolesServicio.buscarTodos(), HttpStatus.OK);
    }
}
