package com.cisco.regina.repositorio;

import com.cisco.regina.modelo.Instituciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitucionesRepositorio extends JpaRepository<Instituciones, Long> {

    Page<Instituciones> findByInstitucionContainingIgnoreCaseOrderByInstitucion(String buscar, Pageable paginacion);
}
