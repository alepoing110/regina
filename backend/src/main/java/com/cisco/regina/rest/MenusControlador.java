package com.cisco.regina.rest;

import java.util.List;

import com.cisco.regina.modelo.otd.MenusOTD;
import com.cisco.regina.servicio.EnlacesServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menus")
public class MenusControlador {

    @Autowired
    EnlacesServicio enlacesServicio;

    @GetMapping
    ResponseEntity<?> generarMenuPorUsuario() {
        return new ResponseEntity<List<MenusOTD>>(enlacesServicio.generarMenuPorUsuario(), HttpStatus.OK);
    }

}
