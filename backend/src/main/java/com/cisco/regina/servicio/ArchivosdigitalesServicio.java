package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.Archivosdigitales;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivosdigitalesServicio {

    List<Archivosdigitales> buscarTodos(Long idproyecto);

    Archivosdigitales buscarPorId(Long id);

    void adicionar(Archivosdigitales dato);

    void modificar(Archivosdigitales dato);

    void borrar(Long id);

    public void cargarArchivo(Long id, MultipartFile archivo);

    public Resource descargarArchivo(String nombreArchivo);

}
