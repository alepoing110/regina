package com.cisco.regina.rest;

import java.util.List;

import com.cisco.regina.modelo.Tiposgeneros;
import com.cisco.regina.servicio.TiposgenerosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tiposgeneros")
public class TiposgenerosControlador {

    @Autowired
    TiposgenerosServicio tiposgenerosServicio;

    @GetMapping("/todos")
    ResponseEntity<?> buscarTodos() {
        return new ResponseEntity<List<Tiposgeneros>>(tiposgenerosServicio.buscarTodos(), HttpStatus.OK);
    }
}
