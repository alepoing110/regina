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
@Table(name = "itemsinsumos")
public class Itemsinsumos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iditeminsumo;
    private Long idunidad;
    private Long idinsumo;
    private Long iditem;
    private BigDecimal costounitario;

    public Long getIditeminsumo() {
        return this.iditeminsumo;
    }

    public void setIditeminsumo(Long iditeminsumo) {
        this.iditeminsumo = iditeminsumo;
    }

    public Long getIdunidad() {
        return this.idunidad;
    }

    public void setIdunidad(Long idunidad) {
        this.idunidad = idunidad;
    }

    public Long getIdinsumo() {
        return this.idinsumo;
    }

    public void setIdinsumo(Long idinsumo) {
        this.idinsumo = idinsumo;
    }

    public Long getIditem() {
        return this.iditem;
    }

    public void setIditem(Long iditem) {
        this.iditem = iditem;
    }

    public BigDecimal getCostounitario() {
        return this.costounitario;
    }

    public void setCostounitario(BigDecimal costounitario) {
        this.costounitario = costounitario;
    }

}
