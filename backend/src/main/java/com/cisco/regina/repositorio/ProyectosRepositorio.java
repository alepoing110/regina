package com.cisco.regina.repositorio;

import com.cisco.regina.modelo.Proyectos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectosRepositorio extends JpaRepository<Proyectos, Long> {

    Page<Proyectos> findByProyectoContainingIgnoreCaseOrderByProyecto(String buscar, Pageable paginacion);
}
