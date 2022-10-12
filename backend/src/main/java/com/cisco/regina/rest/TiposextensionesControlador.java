package com.cisco.regina.rest;

import java.util.List;

import com.cisco.regina.modelo.Tiposextensiones;
import com.cisco.regina.servicio.TiposextensionesServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tiposgextensiones")
public class TiposextensionesControlador {

    @Autowired
    TiposextensionesServicio tiposextensionesServicio;

    @GetMapping("/todos")
    ResponseEntity<?> buscarTodos() {
        return new ResponseEntity<List<Tiposextensiones>>(tiposextensionesServicio.buscarTodos(), HttpStatus.OK);
    }
}