package com.cisco.regina.repositorio;

import java.util.List;

import com.cisco.regina.modelo.Actividades;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadesRepositorio extends JpaRepository<Actividades, Long>{

    List<Actividades> findByIdproyecto(Long idproyecto);

}
