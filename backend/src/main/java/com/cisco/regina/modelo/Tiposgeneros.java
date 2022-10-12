package com.cisco.regina.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tiposgeneros")
public class Tiposgeneros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtipogenero;
    private String tipogenero;

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

}
