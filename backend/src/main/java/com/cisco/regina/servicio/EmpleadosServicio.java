package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.EmpleadosOTD;

public interface EmpleadosServicio {

    List<EmpleadosOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<EmpleadosOTD> buscarTodos();

    EmpleadosOTD buscarPorId(Long id);

    void adicionar(EmpleadosOTD dato);

    void modificar(EmpleadosOTD dato);

    void borrar(Long id);

    byte[] generaXLS();

    byte[] generaPDF();
}
