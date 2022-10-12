package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.modelo.otd.UsuariosOTD;

public interface UsuariosServicio {

    List<UsuariosOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<UsuariosOTD> buscarTodos();

    UsuariosOTD buscarPorId(Long id);

    void adicionar(UsuariosOTD dato);

    void modificar(UsuariosOTD dato);

    void borrar(Long id);

    void cambiarClave(Usuarios dato);

    byte[] generaXLS();

    byte[] generaPDF();


}
