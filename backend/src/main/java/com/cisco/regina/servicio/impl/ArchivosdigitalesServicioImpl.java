package com.cisco.regina.servicio.impl;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.cisco.regina.modelo.Archivosdigitales;
import com.cisco.regina.repositorio.ArchivosdigitalesRepositorio;
import com.cisco.regina.servicio.ArchivosdigitalesServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArchivosdigitalesServicioImpl implements ArchivosdigitalesServicio{

    @Autowired
    ArchivosdigitalesRepositorio archivosdigitalesRepositorio;

    @Value("${ruta.archivos}")
    private String rutaprincipal;


    @Override
    public List<Archivosdigitales> buscarTodos(Long idproyecto) {
        return archivosdigitalesRepositorio.findByIdproyecto(idproyecto);
    }

    @Override
    public Archivosdigitales buscarPorId(Long id) {
        return archivosdigitalesRepositorio.findById(id).orElse(null);
    }

    @Override
    public void adicionar(Archivosdigitales dato) {
        archivosdigitalesRepositorio.save(dato);

    }

    @Override
    public void modificar(Archivosdigitales dato) {
        archivosdigitalesRepositorio.save(dato);
    }

    @Override
    public void borrar(Long id) {
        archivosdigitalesRepositorio.deleteById(id);
    }

    @Override
    public void cargarArchivo(Long id, MultipartFile archivo) {
        if (!archivo.isEmpty()) {
            String ruta = rutaprincipal+"/documentos";
            String nombreArchivo = UUID.randomUUID().toString()+"."+StringUtils.getFilenameExtension(archivo.getOriginalFilename());

            Archivosdigitales entrada =  archivosdigitalesRepositorio.findById(id).orElse(null);
            entrada.setEnlace(nombreArchivo);
            archivosdigitalesRepositorio.save(entrada);

            Path rutaArchivo = Paths.get(ruta).resolve(nombreArchivo).toAbsolutePath();
            Path rutaArchivoAnterior = Paths.get(ruta).resolve(nombreArchivo).toAbsolutePath();
            try {
                Files.deleteIfExists(rutaArchivoAnterior);
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (Exception e) {
                throw new RuntimeException("No se pudo almacenar el archivo. Error: " + e.getMessage());
            }
        }

    }

    @Override
    public Resource descargarArchivo(String nombreArchivo) {
        String ruta = rutaprincipal+"/documentos";
        Path rutaArchivo = Paths.get(ruta).resolve(nombreArchivo).toAbsolutePath();
        Resource recurso = null;
        try {
            recurso = new UrlResource(rutaArchivo.toUri());
            if (recurso.exists() || recurso.isReadable()) {
                return recurso;
            } else {
                recurso = null;
                return recurso;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return recurso;

    }

}
