package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.Proyectos;

public interface ProyectosServicio {

    List<Proyectos> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<Proyectos> buscarTodos();

    Proyectos buscarPorId(Long id);

    void adicionar(Proyectos dato);

    void modificar(Proyectos dato);

    void borrar(Long id);

    byte[] generaXLS();

    byte[] generaPDF();

}
