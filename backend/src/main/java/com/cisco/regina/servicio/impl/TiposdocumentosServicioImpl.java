package com.cisco.regina.servicio.impl;

import java.util.List;

import com.cisco.regina.modelo.Tiposdocumentos;
import com.cisco.regina.repositorio.TiposdocumentosRepositorio;
import com.cisco.regina.servicio.TiposdocumentosServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiposdocumentosServicioImpl implements TiposdocumentosServicio{

    @Autowired
    TiposdocumentosRepositorio tiposdocumentosRepositorio;

    @Override
    public List<Tiposdocumentos> buscarTodos() {
        return tiposdocumentosRepositorio.findAll();
    }

}
