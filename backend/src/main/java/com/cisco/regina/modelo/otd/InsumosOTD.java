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
public class InsumosOTD {

    private Long idinsumo;
    private Long idunidad;
    private String insumo;
    private String tipo;
    private BigDecimal costounitario;
    private String unidad;

    public Long getIdinsumo() {
        return this.idinsumo;
    }

    public void setIdinsumo(Long idinsumo) {
        this.idinsumo = idinsumo;
    }

    public Long getIdunidad() {
        return this.idunidad;
    }

    public void setIdunidad(Long idunidad) {
        this.idunidad = idunidad;
    }

    public String getInsumo() {
        return this.insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCostounitario() {
        return this.costounitario;
    }

    public void setCostounitario(BigDecimal costounitario) {
        this.costounitario = costounitario;
    }

    public String getUnidad() {
        return this.unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

}
