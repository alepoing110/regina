package com.cisco.regina.servicio;

import java.util.List;

import com.cisco.regina.modelo.otd.PersonasOTD;

public interface PersonasServicio {

    List<PersonasOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad);

    Integer cantidadBuscarPorTermino(String termino);

    List<PersonasOTD> buscarTodos();

    PersonasOTD buscarPorId(Long id);

    void adicionar(PersonasOTD dato);

    void modificar(PersonasOTD dato);

    void borrar(Long id);

    byte[] generaXLS();

    byte[] generaPDF();


}
