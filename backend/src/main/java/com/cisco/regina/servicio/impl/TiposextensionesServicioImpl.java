package com.cisco.regina.servicio.impl;

import java.util.List;

import com.cisco.regina.modelo.Tiposextensiones;
import com.cisco.regina.repositorio.TiposextensionesRepositorio;
import com.cisco.regina.servicio.TiposextensionesServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiposextensionesServicioImpl implements TiposextensionesServicio{

    @Autowired
    TiposextensionesRepositorio tiposextensionesRepositorio;

    @Override
    public List<Tiposextensiones> buscarTodos() {
        return tiposextensionesRepositorio.findAll();
    }

}
