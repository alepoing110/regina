package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.ActividadesitemsOTD;

public interface ActividadesitemsServicio {

    List<ActividadesitemsOTD> buscarTodos(Long idactividad);

    ActividadesitemsOTD buscarPorId(Long id);

    void adicionar(ActividadesitemsOTD dato);

    void modificar(ActividadesitemsOTD dato);

    void borrar(Long id);

}
