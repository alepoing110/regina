package com.cisco.regina.rest;

import java.util.List;

import com.cisco.regina.modelo.otd.ActividadesitemsOTD;
import com.cisco.regina.servicio.ActividadesitemsServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/actividadesitems")
public class ActividadesitemsControlador {

    @Autowired
    ActividadesitemsServicio actividadesitemsServicio;

    @GetMapping("/todos")
    ResponseEntity<?> buscarTodos(
        @RequestParam(value = "iditem", defaultValue = "0") Long iditem
    ) {
        return new ResponseEntity<List<ActividadesitemsOTD>>(actividadesitemsServicio.buscarTodos(iditem), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return new ResponseEntity<ActividadesitemsOTD>(actividadesitemsServicio.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> adicionar(@RequestBody ActividadesitemsOTD dato, BindingResult verificado) {
        actividadesitemsServicio.adicionar(dato);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<?> modificar(@RequestBody ActividadesitemsOTD dato, BindingResult verificado) {
        actividadesitemsServicio.modificar(dato);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> borrar(@PathVariable Long id) {
        actividadesitemsServicio.borrar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
