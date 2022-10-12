package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.InsumosOTD;

public interface InsumosServicio {

    List<InsumosOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<InsumosOTD> buscarTodos();

    List<InsumosOTD> buscarTodosManoObra();

    InsumosOTD buscarPorId(Long id);

    void adicionar(InsumosOTD dato);

    void modificar(InsumosOTD dato);

    void borrar(Long id);

    byte[] generaXLS();

    byte[] generaPDF();


}
