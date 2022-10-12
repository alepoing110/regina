package com.cisco.regina.repositorio;

import java.util.List;

import com.cisco.regina.modelo.Insumos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsumosRepositorio extends JpaRepository<Insumos, Long>{

    Page<Insumos> findByInsumoContainingIgnoreCaseOrderByInsumo(String buscar, Pageable paginacion);

    List<Insumos> findAllByOrderByInsumoAsc();

    List<Insumos> findByTipoOrderByInsumoAsc(String tipo);

}
