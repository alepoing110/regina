package com.cisco.regina.modelo.otd;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonasOTD {

    private Long idpersona;
    private String primerapellido;
    private String segundoapellido;
    private String primernombre;
    private String segundonombre;
    private LocalDate fechanacimiento;
    private String nombrecompleto;
    private String dip;
    private Long idtipogenero;
    private String tipogenero;
    private Long idtipodocumento;
    private String tipodocumento;
    private Long idtipoextension;
    private String tipoextension;
    private String direccion;
    private String telefono;
    private String celular;
    private String correo;

    public Long getIdpersona() {
        return this.idpersona;
    }

    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    public String getPrimerapellido() {
        return this.primerapellido;
    }

    public void setPrimerapellido(String primerapellido) {
        this.primerapellido = primerapellido;
    }

    public String getSegundoapellido() {
        return this.segundoapellido;
    }

    public void setSegundoapellido(String segundoapellido) {
        this.segundoapellido = segundoapellido;
    }

    public String getPrimernombre() {
        return this.primernombre;
    }

    public void setPrimernombre(String primernombre) {
        this.primernombre = primernombre;
    }

    public String getSegundonombre() {
        return this.segundonombre;
    }

    public void setSegundonombre(String segundonombre) {
        this.segundonombre = segundonombre;
    }

    public LocalDate getFechanacimiento() {
        return this.fechanacimiento;
    }

    public void setFechanacimiento(LocalDate fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getNombrecompleto() {
        return this.nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getDip() {
        return this.dip;
    }

    public void setDip(String dip) {
        this.dip = dip;
    }

    public Long getIdtipogenero() {
        return this.idtipogenero;
    }

    public void setIdtipogenero(Long idtipogenero) {
        this.idtipogenero = idtipogenero;
    }

    public String getTipogenero() {
        return this.tipogenero;
    }

    public void setTipogenero(String tipogenero) {
        this.tipogenero = tipogenero;
    }

    public Long getIdtipodocumento() {
        return this.idtipodocumento;
    }

    public void setIdtipodocumento(Long idtipodocumento) {
        this.idtipodocumento = idtipodocumento;
    }

    public String getTipodocumento() {
        return this.tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public Long getIdtipoextension() {
        return this.idtipoextension;
    }

    public void setIdtipoextension(Long idtipoextension) {
        this.idtipoextension = idtipoextension;
    }

    public String getTipoextension() {
        return this.tipoextension;
    }

    public void setTipoextension(String tipoextension) {
        this.tipoextension = tipoextension;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public PersonasOTD(
        Long idpersona,
        String nombrecompleto,
        String dip
    ) {
        this.idpersona = idpersona;
        this.nombrecompleto = nombrecompleto;
        this.dip = dip;
    }

    public PersonasOTD(
        Long idpersona,
        String primerapellido,
        String segundoapellido,
        String primernombre,
        String segundonombre,
        String dip,
        String direccion,
        String telefono,
        String celular,
        String correo
    ) {
        this.idpersona = idpersona;
        this.primerapellido=primerapellido;
        this.segundoapellido=segundoapellido;
        this.primernombre=primernombre;
        this.segundonombre=segundonombre;
        this.dip=dip;
        this.direccion=direccion;
        this.telefono=telefono;
        this.celular=celular;
        this.correo=correo;
    }

}


