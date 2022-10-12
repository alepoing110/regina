package com.cisco.regina.repositorio;

import javax.transaction.Transactional;

import com.cisco.regina.modelo.Usuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UsuariosRepositorio extends JpaRepository<Usuarios, Long>{

    Page<Usuarios> findByUsuarioContainingIgnoreCaseOrderByUsuario(String buscar, Pageable paginacion);

    Usuarios findByUsuario(String usuario);

    @Transactional
    @Modifying
    @Query(value = "update usuarios set clave=crypt(?2,gen_salt('bf', 8)) where idusuario=?1", nativeQuery = true)
    void cambiarClave(Long idusuario, String clave);

}
