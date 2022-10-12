package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.PlanillasOTD;

public interface PlanillasServicio {

    List<PlanillasOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<PlanillasOTD> buscarTodos();

    PlanillasOTD buscarPorId(Long id);

    void adicionar(PlanillasOTD dato);

    void modificar(PlanillasOTD dato);

    void borrar(Long id);

    byte[] generaXLS();

    byte[] generaPDF();
}
