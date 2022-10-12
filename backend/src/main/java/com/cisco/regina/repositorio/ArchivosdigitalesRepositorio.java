package com.cisco.regina.repositorio;

import java.util.List;

import com.cisco.regina.modelo.Archivosdigitales;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivosdigitalesRepositorio extends JpaRepository<Archivosdigitales, Long> {

    List<Archivosdigitales> findByIdproyecto(Long idproyecto);
}
