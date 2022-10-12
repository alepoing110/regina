package com.cisco.regina.repositorio;

import java.util.List;

import com.cisco.regina.modelo.Planillasempleados;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanillasempleadosRepositorio extends JpaRepository<Planillasempleados, Long>{

    List<Planillasempleados> findByIdplanilla(Long idplanilla);

}
