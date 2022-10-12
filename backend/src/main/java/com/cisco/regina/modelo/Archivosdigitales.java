package com.cisco.regina.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "archivosdigitales")
public class Archivosdigitales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idarchivodigital;
    private Long idproyecto;
    private String descripcion;
    private String enlace;

    public Long getIdarchivodigital() {
        return this.idarchivodigital;
    }

    public void setIdarchivodigital(Long idarchivodigital) {
        this.idarchivodigital = idarchivodigital;
    }

    public Long getIdproyecto() {
        return this.idproyecto;
    }

    public void setIdproyecto(Long idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnlace() {
        return this.enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

}
