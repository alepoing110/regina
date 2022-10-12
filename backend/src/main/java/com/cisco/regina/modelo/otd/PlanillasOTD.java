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
public class PlanillasOTD {

    private Long idplanilla;
    private String planilla;
    private Integer gestion;
    private Integer mes;
    private BigDecimal costo_total;
    private String detalle;

    public Long getIdplanilla() {
        return this.idplanilla;
    }

    public void setIdplanilla(Long idplanilla) {
        this.idplanilla = idplanilla;
    }

    public String getPlanilla() {
        return this.planilla;
    }

    public void setPlanilla(String planilla) {
        this.planilla = planilla;
    }

    public Integer getGestion() {
        return this.gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public Integer getMes() {
        return this.mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public BigDecimal getCosto_total() {
        return this.costo_total;
    }

    public void setCosto_total(BigDecimal costo_total) {
        this.costo_total = costo_total;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }


}
