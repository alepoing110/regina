package com.cisco.regina.rest;

import java.util.List;

import com.cisco.regina.modelo.Archivosdigitales;
import com.cisco.regina.servicio.ArchivosdigitalesServicio;

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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/archivosdigitales")
public class ArchivosdigitalesControlador {

    @Autowired
    ArchivosdigitalesServicio archivosdigitalesServicio;

    @GetMapping("/todos")
    ResponseEntity<?> buscarTodos(
        @RequestParam(value = "idproyecto", defaultValue = "0") Long idproyecto
    ) {
        return new ResponseEntity<List<Archivosdigitales>>(archivosdigitalesServicio.buscarTodos(idproyecto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return new ResponseEntity<Archivosdigitales>(archivosdigitalesServicio.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> adicionar(@RequestBody Archivosdigitales dato, BindingResult verificado) {
        archivosdigitalesServicio.adicionar(dato);
        return new ResponseEntity<Long>(dato.getIdarchivodigital(), HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<?> modificar(@RequestBody Archivosdigitales dato, BindingResult verificado) {
        archivosdigitalesServicio.modificar(dato);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> borrar(@PathVariable Long id) {
        archivosdigitalesServicio.borrar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cargarArchivo/{id}")
    ResponseEntity<?> cargarArchivo(@PathVariable Long id, @RequestParam("archivo") MultipartFile archivo) {
        archivosdigitalesServicio.cargarArchivo(id, archivo);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
