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
public class ActividadesitemsOTD {

    private Long idactividaditem;
    private Long idactividad;
    private Long iditem;
    private Long idunidad;
    private BigDecimal cantidad;
    private BigDecimal costounitario;
    private String item;
    private String unidad;

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

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUnidad() {
        return this.unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

}
