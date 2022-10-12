package com.cisco.regina.modelo.otd;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenusOTD {

    private String enlace;
    private String imagen;
    private String ruta;
    private List<SubmenusOTD> enlaces;

    public String getEnlace() {
        return this.enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getRuta() {
        return this.ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public List<SubmenusOTD> getEnlaces() {
        return this.enlaces;
    }

    public void setEnlaces(List<SubmenusOTD> enlaces) {
        this.enlaces = enlaces;
    }

}
