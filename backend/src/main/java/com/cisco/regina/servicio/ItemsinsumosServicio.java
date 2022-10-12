package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.ItemsinsumosOTD;

public interface ItemsinsumosServicio {

    List<ItemsinsumosOTD> buscarTodos(Long iditem);

    ItemsinsumosOTD buscarPorId(Long id);

    void adicionar(ItemsinsumosOTD dato);

    void modificar(ItemsinsumosOTD dato);

    void borrar(Long id);

}
