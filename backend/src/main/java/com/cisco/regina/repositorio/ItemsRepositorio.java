package com.cisco.regina.repositorio;

import com.cisco.regina.modelo.Items;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepositorio extends JpaRepository<Items,  Long>{

    Page<Items> findByItemContainingIgnoreCaseOrderByItem(String buscar, Pageable paginacion);

}
