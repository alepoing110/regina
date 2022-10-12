package com.cisco.regina.servicio.impl;

import java.util.List;

import com.cisco.regina.modelo.Roles;
import com.cisco.regina.repositorio.RolesRepositorio;
import com.cisco.regina.servicio.RolesServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesServicioImpl implements RolesServicio{

    @Autowired
    RolesRepositorio rolesRepositorio;

    @Override
    public List<Roles> buscarTodos() {
        return rolesRepositorio.findAll();
    }

}
