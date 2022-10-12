package com.cisco.regina.repositorio;

import com.cisco.regina.modelo.Unidades;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadesRepositorio extends JpaRepository<Unidades, Long>{

    Page<Unidades> findByUnidadContainingIgnoreCaseOrderByUnidad(String buscar, Pageable paginacion);

}
