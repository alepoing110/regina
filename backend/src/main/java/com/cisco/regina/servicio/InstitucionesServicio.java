package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.Instituciones;

public interface InstitucionesServicio {

    List<Instituciones> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<Instituciones> buscarTodos();

    Instituciones buscarPorId(Long id);

    void adicionar(Instituciones dato);

    void modificar(Instituciones dato);

    void borrar(Long id);

    byte[] generaXLS();

    byte[] generaPDF();

}
