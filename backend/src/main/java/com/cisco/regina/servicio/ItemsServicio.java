package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.ItemsOTD;

public interface ItemsServicio {

    List<ItemsOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<ItemsOTD> buscarTodos();

    ItemsOTD buscarPorId(Long id);

    void adicionar(ItemsOTD dato);

    void modificar(ItemsOTD dato);

    void borrar(Long id);

    byte[] generaXLS();

    byte[] generaPDF();

}
