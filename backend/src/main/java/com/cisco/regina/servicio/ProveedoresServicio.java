package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.ProveedoresOTD;

public interface ProveedoresServicio {

    List<ProveedoresOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<ProveedoresOTD> buscarTodos();

    ProveedoresOTD buscarPorId(Long id);

    void adicionar(ProveedoresOTD dato);

    void modificar(ProveedoresOTD dato);

    void borrar(Long id);

    byte[] generaXLS();

    byte[] generaPDF();

}
