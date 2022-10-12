package com.cisco.regina.servicio.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cisco.regina.modelo.Actividades;
import com.cisco.regina.modelo.Proyectos;
import com.cisco.regina.modelo.Unidades;
import com.cisco.regina.modelo.otd.ActividadesOTD;
import com.cisco.regina.repositorio.ActividadesRepositorio;
import com.cisco.regina.repositorio.ProyectosRepositorio;
import com.cisco.regina.repositorio.UnidadesRepositorio;
import com.cisco.regina.servicio.ActividadesServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActividadesServicioImpl implements ActividadesServicio{

    @Autowired
    ActividadesRepositorio actividadesRepositorio;

    @Autowired
    UnidadesRepositorio unidadesRepositorio;

    @Autowired
    ProyectosRepositorio proyectosRepositorio;

    @Override
    public List<ActividadesOTD> buscarTodos(Long idproyecto) {
        List<Actividades> entrada = actividadesRepositorio.findByIdproyecto(idproyecto);
        List<ActividadesOTD> salida = new ArrayList<>();
        for (Actividades _item : entrada) {
            Unidades unidad = unidadesRepositorio.findById(_item.getIdunidad()).orElse(null);
            Proyectos proyecto = proyectosRepositorio.findById(_item.getIdproyecto()).orElse(null);
            ActividadesOTD dato = new ActividadesOTD(_item.getIdactividad(), _item.getIdproyecto(), proyecto.getProyecto(), _item.getIdunidad(), unidad.getUnidad(), _item.getActividad(), _item.getCostounitario());
            salida.add(dato);
        }
        return salida;
    }

    @Override
    public ActividadesOTD buscarPorId(Long id) {
        Actividades _item = actividadesRepositorio.findById(id).orElse(null);
        Unidades unidad = unidadesRepositorio.findById(_item.getIdunidad()).orElse(null);
        Proyectos proyecto = proyectosRepositorio.findById(_item.getIdproyecto()).orElse(null);
        ActividadesOTD salida = new ActividadesOTD(_item.getIdactividad(), _item.getIdproyecto(), proyecto.getProyecto(), _item.getIdunidad(), unidad.getUnidad(), _item.getActividad(), _item.getCostounitario());
        return salida;
    }

    @Override
    public void adicionar(ActividadesOTD dato) {
        Actividades entrada = new Actividades();
        entrada.setIdproyecto(dato.getIdproyecto());
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setActividad(dato.getActividad());
        entrada.setCostounitario(dato.getCostounitario());
        actividadesRepositorio.save(entrada);
        Proyectos proyecto = proyectosRepositorio.findById(entrada.getIdproyecto()).orElse(null);
        List<Actividades> suma = actividadesRepositorio.findByIdproyecto(proyecto.getIdproyecto());
        BigDecimal sum = BigDecimal.ZERO;
        for (Actividades _item : suma) {
            sum = sum.add(_item.getCostounitario());
        }
        proyecto.setCostoejecutado(sum);
        proyectosRepositorio.save(proyecto);

    }

    @Override
    public void modificar(ActividadesOTD dato) {
        Actividades entrada = actividadesRepositorio.findById(dato.getIdproyecto()).orElse(null);
        entrada.setIdproyecto(dato.getIdproyecto());
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setActividad(dato.getActividad());
        entrada.setCostounitario(dato.getCostounitario());
        actividadesRepositorio.save(entrada);
        Proyectos proyecto = proyectosRepositorio.findById(entrada.getIdproyecto()).orElse(null);
        List<Actividades> suma = actividadesRepositorio.findByIdproyecto(proyecto.getIdproyecto());
        BigDecimal sum = BigDecimal.ZERO;
        for (Actividades _item : suma) {
            sum = sum.add(_item.getCostounitario());
        }
        proyecto.setCostoejecutado(sum);
        proyectosRepositorio.save(proyecto);
    }

    @Override
    public void borrar(Long id) {
        actividadesRepositorio.deleteById(id);
    }
}
