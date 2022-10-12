package com.cisco.regina.repositorio;

import java.util.List;

import com.cisco.regina.modelo.Actividadesitems;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadesitemsRepositorio extends JpaRepository<Actividadesitems, Long> {

    List<Actividadesitems> findByIdactividad(Long idactividad);
}
