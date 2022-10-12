package com.cisco.regina.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tiposextensiones")
public class Tiposextensiones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtipoextension;
    private String tipoextension;
    private String sigla;

    public Long getIdtipoextension() {
        return this.idtipoextension;
    }

    public void setIdtipoextension(Long idtipoextension) {
        this.idtipoextension = idtipoextension;
    }

    public String getTipoextension() {
        return this.tipoextension;
    }

    public void setTipoextension(String tipoextension) {
        this.tipoextension = tipoextension;
    }

    public String getSigla() {
        return this.sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
