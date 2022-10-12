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
public class PlanillasempleadosOTD {

    private Long idplanillaempleado;
    private Long idplanilla;
    private Long idempleado;
    private Integer dias;
    private Integer horas;
    private BigDecimal costo_total;
    private String nombrecompleto;

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

    public String getNombrecompleto() {
        return this.nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

}
