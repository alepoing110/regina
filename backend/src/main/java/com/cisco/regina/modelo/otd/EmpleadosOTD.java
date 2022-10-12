package com.cisco.regina.modelo.otd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadosOTD {

    private Long idempleado;
    private Long idpersona;
    private Long idinsumo;
    private String nombrecompleto;
    private String cargo;
    private String insumo;

    public Long getIdempleado() {
        return this.idempleado;
    }

    public void setIdempleado(Long idempleado) {
        this.idempleado = idempleado;
    }

    public Long getIdpersona() {
        return this.idpersona;
    }

    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    public Long getIdinsumo() {
        return this.idinsumo;
    }

    public void setIdinsumo(Long idinsumo) {
        this.idinsumo = idinsumo;
    }

    public String getNombrecompleto() {
        return this.nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getCargo() {
        return this.cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getInsumo() {
        return this.insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

}
