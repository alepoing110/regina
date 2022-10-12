package com.cisco.regina.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "usuariosroles")
public class Usuariosroles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuariorol;
    private Long idusuario;
    private Long idrol;

    public Long getIdusuariorol() {
        return this.idusuariorol;
    }

    public void setIdusuariorol(Long idusuariorol) {
        this.idusuariorol = idusuariorol;
    }

    public Long getIdusuario() {
        return this.idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public Long getIdrol() {
        return this.idrol;
    }

    public void setIdrol(Long idrol) {
        this.idrol = idrol;
    }

}
