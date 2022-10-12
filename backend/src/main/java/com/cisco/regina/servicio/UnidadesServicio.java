package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.UnidadesOTD;

public interface UnidadesServicio {

    List<UnidadesOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<UnidadesOTD> buscarTodos();

    UnidadesOTD buscarPorId(Long id);

    void adicionar(UnidadesOTD dato);

    void modificar(UnidadesOTD dato);

    void borrar(Long id);

    byte[] generaXLS();

    byte[] generaPDF();


}
