package com.cisco.regina.servicio.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cisco.regina.modelo.Empleados;
import com.cisco.regina.modelo.Insumos;
import com.cisco.regina.modelo.Personas;
import com.cisco.regina.modelo.Planillas;
import com.cisco.regina.modelo.Planillasempleados;
import com.cisco.regina.modelo.otd.PlanillasempleadosOTD;
import com.cisco.regina.repositorio.EmpleadosRepositorio;
import com.cisco.regina.repositorio.InsumosRepositorio;
import com.cisco.regina.repositorio.PersonasRepositorio;
import com.cisco.regina.repositorio.PlanillasRepositorio;
import com.cisco.regina.repositorio.PlanillasempleadosRepositorio;
import com.cisco.regina.servicio.PlanillasempleadosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanillasempleadosServicioImpl implements PlanillasempleadosServicio{

    @Autowired
    PlanillasempleadosRepositorio planillasempleadosRepositorio;

    @Autowired
    EmpleadosRepositorio empleadosRepositorio;

    @Autowired
    InsumosRepositorio insumosRepositorio;

    @Autowired
    PersonasRepositorio personasRepositorio;

    @Autowired
    PlanillasRepositorio planillasRepositorio;

    @Override
    public List<PlanillasempleadosOTD> buscarTodos(Long idplanilla) {
        List<Planillasempleados> entrada = planillasempleadosRepositorio.findByIdplanilla(idplanilla);
        List<PlanillasempleadosOTD> salida = new ArrayList<>();
        for (Planillasempleados _item : entrada) {
            Empleados empleado = empleadosRepositorio.findById(_item.getIdempleado()).orElse(null);
            Personas persona = personasRepositorio.findById(empleado.getIdpersona()).orElse(null);
            PlanillasempleadosOTD dato = new PlanillasempleadosOTD(_item.getIdplanillaempleado(), _item.getIdplanilla(), _item.getIdempleado(), _item.getDias(), _item.getHoras(), _item.getCosto_total(), persona.getPrimerapellido()+ " "+persona.getSegundoapellido()+" "+persona.getPrimernombre()+" "+persona.getSegundonombre());
            salida.add(dato);
        }
        return salida;
    }

    @Override
    public PlanillasempleadosOTD buscarPorId(Long id) {
        Planillasempleados _item = planillasempleadosRepositorio.findById(id).orElse(null);
        Empleados empleado = empleadosRepositorio.findById(_item.getIdempleado()).orElse(null);
        Personas persona = personasRepositorio.findById(empleado.getIdpersona()).orElse(null);
        PlanillasempleadosOTD salida = new PlanillasempleadosOTD(_item.getIdplanillaempleado(), _item.getIdplanilla(), _item.getIdempleado(), _item.getDias(), _item.getHoras(), _item.getCosto_total(), persona.getPrimerapellido()+ " "+persona.getSegundoapellido()+" "+persona.getPrimernombre()+" "+persona.getSegundonombre());
        return salida;
    }

    @Override
    public void adicionar(PlanillasempleadosOTD dato) {
        Planillasempleados entrada = new Planillasempleados();
        Empleados empleado = empleadosRepositorio.findById(dato.getIdempleado()).orElse(null);
        Insumos insumo = insumosRepositorio.findById(empleado.getIdinsumo()).orElse(null);
        entrada.setIdplanilla(dato.getIdplanilla());
        entrada.setIdempleado(dato.getIdempleado());
        entrada.setDias(dato.getDias());
        entrada.setHoras(dato.getHoras());
        entrada.setCosto_total(new BigDecimal(insumo.getCostounitario().floatValue()*dato.getHoras()*dato.getDias()));
        planillasempleadosRepositorio.save(entrada);

        Planillas planilla = planillasRepositorio.findById(entrada.getIdplanilla()).orElse(null);
        List<Planillasempleados> suma = planillasempleadosRepositorio.findByIdplanilla(planilla.getIdplanilla());
        BigDecimal sum = BigDecimal.ZERO;
        for (Planillasempleados _item : suma) {
            sum = sum.add(_item.getCosto_total());
        }
        planilla.setCosto_total(sum);
        planillasRepositorio.save(planilla);
    }

    @Override
    public void modificar(PlanillasempleadosOTD dato) {
        Planillasempleados entrada = planillasempleadosRepositorio.findById(dato.getIdplanilla()).orElse(null);
        Empleados empleado = empleadosRepositorio.findById(dato.getIdempleado()).orElse(null);
        Insumos insumo = insumosRepositorio.findById(empleado.getIdinsumo()).orElse(null);
        entrada.setIdplanilla(dato.getIdplanilla());
        entrada.setIdempleado(dato.getIdempleado());
        entrada.setDias(dato.getDias());
        entrada.setHoras(dato.getHoras());
        entrada.setCosto_total(new BigDecimal(insumo.getCostounitario().floatValue()*dato.getHoras()*dato.getDias()));
        planillasempleadosRepositorio.save(entrada);
        Planillas planilla = planillasRepositorio.findById(entrada.getIdplanilla()).orElse(null);
        List<Planillasempleados> suma = planillasempleadosRepositorio.findByIdplanilla(planilla.getIdplanilla());
        BigDecimal sum = BigDecimal.ZERO;
        for (Planillasempleados _item : suma) {
            sum = sum.add(_item.getCosto_total());
        }
        planilla.setCosto_total(sum);
        planillasRepositorio.save(planilla);
    }

    @Override
    public void borrar(Long id) {
        planillasempleadosRepositorio.deleteById(id);
    }
}
