package com.cisco.regina.servicio.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cisco.regina.modelo.Insumos;
import com.cisco.regina.modelo.Items;
import com.cisco.regina.modelo.Itemsinsumos;
import com.cisco.regina.modelo.Unidades;
import com.cisco.regina.modelo.otd.ItemsinsumosOTD;
import com.cisco.regina.repositorio.InsumosRepositorio;
import com.cisco.regina.repositorio.ItemsRepositorio;
import com.cisco.regina.repositorio.ItemsinsumosRepositorio;
import com.cisco.regina.repositorio.UnidadesRepositorio;
import com.cisco.regina.servicio.ItemsinsumosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsinsumosServicioImpl implements ItemsinsumosServicio{

    @Autowired
    ItemsinsumosRepositorio itemsinsumosRepositorio;

    @Autowired
    InsumosRepositorio insumosRepositorio;

    @Autowired
    UnidadesRepositorio unidadesRepositorio;

    @Autowired
    ItemsRepositorio itemsRepositorio;

    @Override
    public List<ItemsinsumosOTD> buscarTodos(Long iditem) {
        List<Itemsinsumos> entrada = itemsinsumosRepositorio.findByIditem(iditem);
        List<ItemsinsumosOTD> salida = new ArrayList<>();
        for (Itemsinsumos iteminsumo : entrada) {
            Insumos insumo =  insumosRepositorio.findById(iteminsumo.getIdinsumo()).orElse(null);
            Unidades unidad = unidadesRepositorio.findById(iteminsumo.getIdunidad()).orElse(null);
            ItemsinsumosOTD dato = new ItemsinsumosOTD(iteminsumo.getIditeminsumo(), iteminsumo.getIdunidad(), iteminsumo.getIdinsumo(), iditem, iteminsumo.getCostounitario(), unidad.getUnidad(), insumo.getInsumo(), insumo.getTipo());
            salida.add(dato);
        }
        return salida;
    }

    @Override
    public ItemsinsumosOTD buscarPorId(Long id) {
        Itemsinsumos entrada = itemsinsumosRepositorio.findById(id).orElse(null);
        Insumos insumo =  insumosRepositorio.findById(entrada.getIdinsumo()).orElse(null);
        Unidades unidad = unidadesRepositorio.findById(entrada.getIdunidad()).orElse(null);
        ItemsinsumosOTD salida = new ItemsinsumosOTD(entrada.getIditeminsumo(), entrada.getIdunidad(), entrada.getIdinsumo(), entrada.getIditem(), entrada.getCostounitario(), unidad.getUnidad(), insumo.getInsumo(), insumo.getTipo());
        return salida;
    }

    @Override
    public void adicionar(ItemsinsumosOTD dato) {
        Itemsinsumos entrada = new Itemsinsumos();
        entrada.setIdinsumo(dato.getIdinsumo());
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setIditem(dato.getIditem());
        entrada.setCostounitario(dato.getCostounitario());
        itemsinsumosRepositorio.save(entrada);
        Items item = itemsRepositorio.findById(entrada.getIditem()).orElse(null);
        List<Itemsinsumos> suma = itemsinsumosRepositorio.findByIditem(item.getIditem());
        BigDecimal sum = BigDecimal.ZERO;
        for (Itemsinsumos iteminsumo : suma) {
            sum = sum.add(iteminsumo.getCostounitario());
        }
        item.setCostounitario(sum);
        itemsRepositorio.save(item);

    }

    @Override
    public void modificar(ItemsinsumosOTD dato) {
        Itemsinsumos entrada = itemsinsumosRepositorio.findById(dato.getIditeminsumo()).orElse(null);
        entrada.setIdinsumo(dato.getIdinsumo());
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setIditem(dato.getIditem());
        entrada.setCostounitario(dato.getCostounitario());
        itemsinsumosRepositorio.save(entrada);
        Items item = itemsRepositorio.findById(entrada.getIditem()).orElse(null);
        List<Itemsinsumos> suma = itemsinsumosRepositorio.findByIditem(item.getIditem());
        BigDecimal sum = BigDecimal.ZERO;
        for (Itemsinsumos iteminsumo : suma) {
            sum = sum.add(iteminsumo.getCostounitario());
        }
        item.setCostounitario(sum);
        itemsRepositorio.save(item);
    }

    @Override
    public void borrar(Long id) {
        itemsinsumosRepositorio.deleteById(id);
    }
}
