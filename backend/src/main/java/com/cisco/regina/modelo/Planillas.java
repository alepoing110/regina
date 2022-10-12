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
@Table(name = "planillas")
public class Planillas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idplanilla;
    private String planilla;
    private Integer gestion;
    private Integer mes;
    private BigDecimal costo_total;

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

}
