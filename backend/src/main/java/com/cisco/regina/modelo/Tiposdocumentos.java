package com.cisco.regina.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tiposdocumentos")
public class Tiposdocumentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtipodocumento;
    private String tipodocumento;

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

}
