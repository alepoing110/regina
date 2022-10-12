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
@Table(name = "planillasempleados")
public class Planillasempleados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idplanillaempleado;
    private Long idplanilla;
    private Long idempleado;
    private Integer dias;
    private Integer horas;
    private BigDecimal costo_total;

    public Long getIdplanillaempleado() {
        return this.idplanillaempleado;
    }

    public void setIdplanillaempleado(Long idplanillaempleado) {
        this.idplanillaempleado = idplanillaempleado;
    }

    public Long getIdplanilla() {
        return this.idplanilla;
    }

    public void setIdplanilla(Long idplanilla) {
        this.idplanilla = idplanilla;
    }

    public Long getIdempleado() {
        return this.idempleado;
    }

    public void setIdempleado(Long idempleado) {
        this.idempleado = idempleado;
    }

    public Integer getDias() {
        return this.dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getHoras() {
        return this.horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public BigDecimal getCosto_total() {
        return this.costo_total;
    }

    public void setCosto_total(BigDecimal costo_total) {
        this.costo_total = costo_total;
    }

}
