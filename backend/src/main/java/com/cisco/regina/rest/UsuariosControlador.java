package com.cisco.regina.rest;

import java.util.List;

import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.modelo.otd.UsuariosOTD;
import com.cisco.regina.servicio.UsuariosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuariosControlador {

    @Autowired
    UsuariosServicio usuariosServicio;

    @PutMapping("/cambiarclave")
    ResponseEntity<?> cambiarclave(@RequestBody Usuarios usuario){
        usuariosServicio.cambiarClave(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<?> buscarPorTermino(
        @RequestParam(value = "termino", defaultValue = "") String termino,
        @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
        @RequestParam(value = "cantidad", defaultValue = "5") Integer cantidad
    ) {
        return new ResponseEntity<List<UsuariosOTD>>(usuariosServicio.buscarPorTermino(termino, pagina, cantidad), HttpStatus.OK);
    }

    @GetMapping("/cantidad")
    ResponseEntity<?> cantidadBuscarPorTermino(
        @RequestParam(value = "termino", defaultValue = "") String termino
    ) {
        return new ResponseEntity<Integer>(usuariosServicio.cantidadBuscarPorTermino(termino), HttpStatus.OK);
    }

    @GetMapping("/todos")
    ResponseEntity<?> buscarTodos() {
        return new ResponseEntity<List<UsuariosOTD>>(usuariosServicio.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return new ResponseEntity<UsuariosOTD>(usuariosServicio.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> adicionar(@RequestBody UsuariosOTD dato, BindingResult verificado) {
        usuariosServicio.adicionar(dato);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Modificar")
    ResponseEntity<?> modificar(@RequestBody UsuariosOTD dato, BindingResult verificado) {
        usuariosServicio.modificar(dato);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar")
    ResponseEntity<?> borrar(@PathVariable Long id) {
        usuariosServicio.borrar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/generaXLS", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<?> generaXLS() {
        return new ResponseEntity<byte[]>(usuariosServicio.generaXLS(), HttpStatus.OK);
    }


    @GetMapping(value = "/generaPDF", produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<?> generaPDF() {
        return new ResponseEntity<byte[]>(usuariosServicio.generaPDF(), HttpStatus.OK);
    }


}
