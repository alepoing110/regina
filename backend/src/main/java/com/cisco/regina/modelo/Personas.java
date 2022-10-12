package com.cisco.regina.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "personas")
public class Personas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpersona;
    private Long idtipogenero;
    private String primerapellido;
    private String segundoapellido;
    private String primernombre;
    private String segundonombre;
    private LocalDate fechanacimiento;
    private String dip;
    private String numerocomplementario;
    private Long idtipodocumento;
    private Long idtipoextension;
    private String direccion;
    private String telefono;
    private String celular;
    private String correo;
    private Boolean estado;

    public Long getIdpersona() {
        return this.idpersona;
    }

    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    public Long getIdtipogenero() {
        return this.idtipogenero;
    }

    public void setIdtipogenero(Long idtipogenero) {
        this.idtipogenero = idtipogenero;
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

    public String getDip() {
        return this.dip;
    }

    public void setDip(String dip) {
        this.dip = dip;
    }

    public String getNumerocomplementario() {
        return this.numerocomplementario;
    }

    public void setNumerocomplementario(String numerocomplementario) {
        this.numerocomplementario = numerocomplementario;
    }

    public Long getIdtipodocumento() {
        return this.idtipodocumento;
    }

    public void setIdtipodocumento(Long idtipodocumento) {
        this.idtipodocumento = idtipodocumento;
    }

    public Long getIdtipoextension() {
        return this.idtipoextension;
    }

    public void setIdtipoextension(Long idtipoextension) {
        this.idtipoextension = idtipoextension;
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

    public Boolean getEstado() {
        return this.estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

}
