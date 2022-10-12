package com.cisco.regina.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "enlaces")
public class Enlaces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idenlace;
    private Long idenlacepadre;
    private String enlace;
    private String ruta;
    private String iconoenlace;
    private Integer orden;

    public Long getIdenlace() {
        return this.idenlace;
    }

    public void setIdenlace(Long idenlace) {
        this.idenlace = idenlace;
    }

    public Long getIdenlacepadre() {
        return this.idenlacepadre;
    }

    public void setIdenlacepadre(Long idenlacepadre) {
        this.idenlacepadre = idenlacepadre;
    }

    public String getEnlace() {
        return this.enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getRuta() {
        return this.ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getIconoenlace() {
        return this.iconoenlace;
    }

    public void setIconoenlace(String iconoenlace) {
        this.iconoenlace = iconoenlace;
    }

    public Integer getOrden() {
        return this.orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

}