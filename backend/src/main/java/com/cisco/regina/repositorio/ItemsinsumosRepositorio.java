package com.cisco.regina.repositorio;

import java.util.List;

import com.cisco.regina.modelo.Itemsinsumos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsinsumosRepositorio extends JpaRepository<Itemsinsumos, Long>{

    List<Itemsinsumos> findByIditem(Long iditem);
}
