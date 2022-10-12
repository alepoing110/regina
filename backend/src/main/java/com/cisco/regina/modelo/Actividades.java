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
@Table(name = "actividades")
public class Actividades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idactividad;
    private Long idproyecto;
    private Long idunidad;
    private String actividad;
    private BigDecimal costounitario;

    public Long getIdactividad() {
        return this.idactividad;
    }

    public void setIdactividad(Long idactividad) {
        this.idactividad = idactividad;
    }

    public Long getIdproyecto() {
        return this.idproyecto;
    }

    public void setIdproyecto(Long idproyecto) {
        this.idproyecto = idproyecto;
    }

    public Long getIdunidad() {
        return this.idunidad;
    }

    public void setIdunidad(Long idunidad) {
        this.idunidad = idunidad;
    }

    public String getActividad() {
        return this.actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public BigDecimal getCostounitario() {
        return this.costounitario;
    }

    public void setCostounitario(BigDecimal costounitario) {
        this.costounitario = costounitario;
    }

}
