package com.cisco.regina.repositorio;

import java.util.List;

import com.cisco.regina.modelo.Usuariosroles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosrolesRepositorio extends JpaRepository<Usuariosroles, Long>{

    List<Usuariosroles> findByIdusuario(Long idusuario);

    void deleteByIdusuario(Long idusuario);
}
