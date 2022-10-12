package com.cisco.regina.modelo.otd;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActividadesOTD {
    
    private Long idactividad;
    private Long idproyecto;
    private String proyecto;
    private Long idunidad;
    private String unidad;
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

    public String getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public Long getIdunidad() {
        return this.idunidad;
    }

    public void setIdunidad(Long idunidad) {
        this.idunidad = idunidad;
    }

    public String getUnidad() {
        return this.unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
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
