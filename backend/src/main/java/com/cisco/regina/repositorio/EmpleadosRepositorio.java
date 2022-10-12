package com.cisco.regina.repositorio;

import com.cisco.regina.modelo.Empleados;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadosRepositorio extends JpaRepository<Empleados, Long> {

    Page<Empleados> findByCargoContainingIgnoreCaseOrderByCargo(String buscar, Pageable paginacion);
}
