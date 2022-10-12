package com.cisco.regina.repositorio;

import java.util.List;

import com.cisco.regina.modelo.Enlaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnlacesRepositorio extends JpaRepository<Enlaces, Long>{

    @Query(
        nativeQuery = true,
        value = "select idenlacepadre as idenlace from enlaces join enlacesroles using(idenlace) where idrol in (select idrol from usuariosroles where idusuario=?1) group by idenlacepadre"
    )
    List<Long> findByIdEnlacepadre(Long idusuario);

    @Query(
        nativeQuery = true,
        value = "select idenlace from enlaces join enlacesroles using(idenlace) where idrol in (select idrol from usuariosroles where idusuario=?1) and idenlacepadre=?2 group by idenlace, orden order by idenlace"
    )
    List<Long> findByIdEnlace(Long idusuario, Long idenlace);

}
