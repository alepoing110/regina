package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.PlanillasempleadosOTD;

public interface PlanillasempleadosServicio {

    List<PlanillasempleadosOTD> buscarTodos(Long idproyecto);

    PlanillasempleadosOTD buscarPorId(Long id);

    void adicionar(PlanillasempleadosOTD dato);

    void modificar(PlanillasempleadosOTD dato);

    void borrar(Long id);

}
