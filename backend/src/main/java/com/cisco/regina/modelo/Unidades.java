package com.cisco.regina.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "unidades")
public class Unidades {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idunidad;
  private String unidad;
  private String sigla;

  public Long getIdunidad() {
    return this.idunidad;
  }

  public void setIdunidad(Long idunidad) {
    this.idunidad = idunidad;
  }

  public String getUnidad() {
    return this.unidad;
  }

  public void setUnidad(String unidad) {
    this.unidad = unidad;
  }

  public String getSigla() {
    return this.sigla;
  }

  public void setSigla(String sigla) {
    this.sigla = sigla;
  }

}
