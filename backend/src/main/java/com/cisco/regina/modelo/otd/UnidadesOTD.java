package com.cisco.regina.modelo.otd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnidadesOTD {

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
