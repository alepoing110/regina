package com.cisco.regina.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "proyectos")
public class Proyectos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idproyecto;
    private Long idinstitucion;
    private String proyecto;
    private String detalle;
    private BigDecimal costototal;
    private BigDecimal costoejecutado;
    private String enlace;

    public Long getIdproyecto() {
        return this.idproyecto;
    }

    public void setIdproyecto(Long idproyecto) {
        this.idproyecto = idproyecto;
    }

    public Long getIdinstitucion() {
        return this.idinstitucion;
    }

    public void setIdinstitucion(Long idinstitucion) {
        this.idinstitucion = idinstitucion;
    }

    public String getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getCostototal() {
        return this.costototal;
    }

    public void setCostototal(BigDecimal costototal) {
        this.costototal = costototal;
    }

    public BigDecimal getCostoejecutado() {
        return this.costoejecutado;
    }

    public void setCostoejecutado(BigDecimal costoejecutado) {
        this.costoejecutado = costoejecutado;
    }

    public String getEnlace() {
        return this.enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

}
