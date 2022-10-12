package com.cisco.regina.servicio.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cisco.regina.modelo.Actividades;
import com.cisco.regina.modelo.Actividadesitems;
import com.cisco.regina.modelo.Items;
import com.cisco.regina.modelo.Unidades;
import com.cisco.regina.modelo.otd.ActividadesitemsOTD;
import com.cisco.regina.repositorio.ActividadesRepositorio;
import com.cisco.regina.repositorio.ActividadesitemsRepositorio;
import com.cisco.regina.repositorio.ItemsRepositorio;
import com.cisco.regina.repositorio.UnidadesRepositorio;
import com.cisco.regina.servicio.ActividadesitemsServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActividadesitemsServicioImpl implements ActividadesitemsServicio{

    @Autowired
    ActividadesitemsRepositorio actividadesitemsRepositorio;

    @Autowired
    UnidadesRepositorio unidadesRepositorio;

    @Autowired
    ActividadesRepositorio actividadesRepositorio;

    @Autowired
    ItemsRepositorio itemsRepositorio;

    @Override
    public List<ActividadesitemsOTD> buscarTodos(Long idactividad) {
        List<Actividadesitems> entrada = actividadesitemsRepositorio.findByIdactividad(idactividad);
        List<ActividadesitemsOTD> salida = new ArrayList<>();
        for (Actividadesitems _item : entrada) {
            Unidades unidad = unidadesRepositorio.findById(_item.getIdunidad()).orElse(null);
            Items item = itemsRepositorio.findById(_item.getIditem()).orElse(null);
            ActividadesitemsOTD dato = new ActividadesitemsOTD(_item.getIdactividaditem(), _item.getIdactividad(), _item.getIditem(), _item.getIdunidad(), _item.getCantidad(), _item.getCostounitario(), item.getItem(), unidad.getUnidad());
            salida.add(dato);
        }
        return salida;
    }

    @Override
    public ActividadesitemsOTD buscarPorId(Long id) {
        Actividadesitems _item = actividadesitemsRepositorio.findById(id).orElse(null);
        Unidades unidad = unidadesRepositorio.findById(_item.getIdunidad()).orElse(null);
            Items item = itemsRepositorio.findById(_item.getIditem()).orElse(null);
        ActividadesitemsOTD salida = new ActividadesitemsOTD(_item.getIdactividaditem(), _item.getIdactividad(), _item.getIditem(), _item.getIdunidad(), _item.getCantidad(), _item.getCostounitario(), item.getItem(), unidad.getUnidad());
        return salida;
    }

    @Override
    public void adicionar(ActividadesitemsOTD dato) {
        Actividadesitems entrada = new Actividadesitems();
        entrada.setIdactividad(dato.getIdactividad());
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setIditem(dato.getIditem());
        entrada.setCantidad(dato.getCantidad());
        Items item = itemsRepositorio.findById(dato.getIditem()).orElse(null);
        entrada.setCostounitario(item.getCostounitario());
        actividadesitemsRepositorio.save(entrada);
        Actividades actividad = actividadesRepositorio.findById(entrada.getIdactividad()).orElse(null);
        List<Actividadesitems> suma = actividadesitemsRepositorio.findByIdactividad(actividad.getIdactividad());
        BigDecimal sum = BigDecimal.ZERO;
        for (Actividadesitems _item : suma) {
            sum = sum.add(new BigDecimal(_item.getCostounitario().floatValue()*_item.getCantidad().floatValue()));
        }
        actividad.setCostounitario(sum);
        actividadesRepositorio.save(actividad);

    }

    @Override
    public void modificar(ActividadesitemsOTD dato) {
        Actividadesitems entrada = actividadesitemsRepositorio.findById(dato.getIdactividad()).orElse(null);
        entrada.setIdactividad(dato.getIdactividad());
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setIditem(dato.getIditem());
        entrada.setCantidad(dato.getCantidad());
        Items item = itemsRepositorio.findById(dato.getIditem()).orElse(null);
        entrada.setCostounitario(item.getCostounitario());
        actividadesitemsRepositorio.save(entrada);
        Actividades actividad = actividadesRepositorio.findById(entrada.getIdactividad()).orElse(null);
        List<Actividadesitems> suma = actividadesitemsRepositorio.findByIdactividad(actividad.getIdactividad());
        BigDecimal sum = BigDecimal.ZERO;
        for (Actividadesitems _item : suma) {
            sum = sum.add(new BigDecimal(_item.getCostounitario().floatValue()*_item.getCantidad().floatValue()));
        }
        actividad.setCostounitario(sum);
        actividadesRepositorio.save(actividad);
    }

    @Override
    public void borrar(Long id) {
        actividadesitemsRepositorio.deleteById(id);
    }
}
