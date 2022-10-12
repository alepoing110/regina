package com.cisco.regina.rest;

import com.cisco.regina.servicio.ArchivosdigitalesServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/archivos")
public class ArchivosControlador {

    @Autowired
    ArchivosdigitalesServicio archivosdigitalesServicio;

    @GetMapping("/descargarArchivo/{nombreArchivo}")
    ResponseEntity<?> descargarImagen(@PathVariable String nombreArchivo) {
        Resource archivo = archivosdigitalesServicio.descargarArchivo(nombreArchivo);
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ archivo.getFilename() + "\"");
        return new ResponseEntity<Resource>(archivo, cabecera, HttpStatus.OK);
    }

}
