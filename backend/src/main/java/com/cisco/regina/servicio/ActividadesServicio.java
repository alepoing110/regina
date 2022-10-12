package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.ActividadesOTD;

public interface ActividadesServicio {

    List<ActividadesOTD> buscarTodos(Long idproyecto);

    ActividadesOTD buscarPorId(Long id);

    void adicionar(ActividadesOTD dato);

    void modificar(ActividadesOTD dato);

    void borrar(Long id);
}
