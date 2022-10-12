package com.cisco.regina.rest;

import java.util.List;

import com.cisco.regina.modelo.Tiposdocumentos;
import com.cisco.regina.servicio.TiposdocumentosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tiposdocumentos")
public class TiposdocumentosControlador {

    @Autowired
    TiposdocumentosServicio tiposdocumentosServicio;

    @GetMapping("/todos")
    ResponseEntity<?> buscarTodos() {
        return new ResponseEntity<List<Tiposdocumentos>>(tiposdocumentosServicio.buscarTodos(), HttpStatus.OK);
    }
}
