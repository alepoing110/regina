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
@Table(name = "actividadesitems")
public class Actividadesitems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idactividaditem;
    private Long idactividad;
    private Long iditem;
    private Long idunidad;
    private BigDecimal cantidad;
    private BigDecimal costounitario;

    public Long getIdactividaditem() {
        return this.idactividaditem;
    }

    public void setIdactividaditem(Long idactividaditem) {
        this.idactividaditem = idactividaditem;
    }

    public Long getIdactividad() {
        return this.idactividad;
    }

    public void setIdactividad(Long idactividad) {
        this.idactividad = idactividad;
    }

    public Long getIditem() {
        return this.iditem;
    }

    public void setIditem(Long iditem) {
        this.iditem = iditem;
    }

    public Long getIdunidad() {
        return this.idunidad;
    }

    public void setIdunidad(Long idunidad) {
        this.idunidad = idunidad;
    }

    public BigDecimal getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostounitario() {
        return this.costounitario;
    }

    public void setCostounitario(BigDecimal costounitario) {
        this.costounitario = costounitario;
    }

}
