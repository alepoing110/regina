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
@Table(name = "insumos")
public class Insumos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idinsumo;
    private Long idunidad;
    private String insumo;
    private String tipo;
    private BigDecimal costounitario;

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

}
