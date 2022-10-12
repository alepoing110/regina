package com.cisco.regina.repositorio;

import com.cisco.regina.modelo.Personas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonasRepositorio extends JpaRepository<Personas, Long>{

    Page<Personas> findByPrimerapellidoContainingIgnoreCaseOrderByPrimerapellido(String buscar, Pageable paginacion);

}
