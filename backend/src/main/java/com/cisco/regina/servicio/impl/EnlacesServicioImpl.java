package com.cisco.regina.servicio.impl;

import java.util.ArrayList;
import java.util.List;

import com.cisco.regina.modelo.Enlaces;
import com.cisco.regina.modelo.otd.MenusOTD;
import com.cisco.regina.modelo.otd.SubmenusOTD;
import com.cisco.regina.repositorio.EnlacesRepositorio;
import com.cisco.regina.servicio.EnlacesServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EnlacesServicioImpl implements EnlacesServicio {

    @Autowired
    EnlacesRepositorio enlacesRepositorio;

    @Override
    public List<MenusOTD> generarMenuPorUsuario() {
        Authentication autentificado = SecurityContextHolder.getContext().getAuthentication();
        
        List<Long> enlacespadres =  enlacesRepositorio.findByIdEnlacepadre(Long.parseLong(autentificado.getName()));
        
        List<MenusOTD> salida = new ArrayList<MenusOTD>();

        for (Long idenlacep : enlacespadres) {

            Enlaces enlacep = enlacesRepositorio.findById(idenlacep).orElse(null);
            
            List<Long> enlaceshijos = enlacesRepositorio.findByIdEnlace(Long.parseLong(autentificado.getName()), enlacep.getIdenlace());
            List<SubmenusOTD> salidas = new ArrayList<SubmenusOTD>();
            for (Long idenlace : enlaceshijos) {
                
                Enlaces enlace = enlacesRepositorio.findById(idenlace).orElse(null);
                
                SubmenusOTD salidasub =  new SubmenusOTD(enlace.getEnlace(), enlace.getIconoenlace(), enlace.getRuta());
                salidas.add(salidasub);
                
            }
            MenusOTD salidamenu = new MenusOTD(enlacep.getEnlace(), enlacep.getIconoenlace(), enlacep.getRuta(), salidas);
            salida.add(salidamenu);

        }
        return salida;

    }

}
