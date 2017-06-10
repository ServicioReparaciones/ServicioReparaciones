/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.xml.modelo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@XmlRootElement
public class ProductoXml {

    private String artefacto;
    private String marca;
    private String modelo;
    private String serie;

    public ProductoXml() {
    }

    public String getArtefacto() {
        return artefacto;
    }

    @XmlElement
    public void setArtefacto(String artefacto) {
        this.artefacto = artefacto;
    }

    public String getMarca() {
        return marca;
    }

    @XmlElement
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    @XmlElement
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    @XmlElement
    public void setSerie(String serie) {
        this.serie = serie;
    }
}
