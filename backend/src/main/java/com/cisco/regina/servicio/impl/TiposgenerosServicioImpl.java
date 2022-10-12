package com.cisco.regina.servicio.impl;

import java.util.List;

import com.cisco.regina.modelo.Tiposgeneros;
import com.cisco.regina.repositorio.TiposgenerosRepositorio;
import com.cisco.regina.servicio.TiposgenerosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiposgenerosServicioImpl implements TiposgenerosServicio{

    @Autowired
    TiposgenerosRepositorio tiposgenerosRepositorio;

    @Override
    public List<Tiposgeneros> buscarTodos() {
        return tiposgenerosRepositorio.findAll();
    }

}
