package com.cisco.regina.repositorio;

import com.cisco.regina.modelo.Planillas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanillasRepositorio extends JpaRepository<Planillas, Long> {

    Page<Planillas> findByPlanillaContainingIgnoreCaseOrderByPlanilla(String buscar, Pageable paginacion);
}
