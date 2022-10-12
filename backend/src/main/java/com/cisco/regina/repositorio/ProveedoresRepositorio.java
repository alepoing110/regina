package com.cisco.regina.repositorio;

import com.cisco.regina.modelo.Proveedores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedoresRepositorio extends JpaRepository<Proveedores, Long>{

    Page<Proveedores> findByProveedorContainingIgnoreCaseOrderByProveedor(String buscar, Pageable paginacion);
}
