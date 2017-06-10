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
public class ClienteXml {

    private String apellidos;
    private String nombres;
    private String cedula;
    private String provincia;
    private String canton;
    private String parroquia;
    private String direccion;
    private String referencia;
    private String telefono;
    private String movil;

    public ClienteXml() {
    }

    public String getApellidos() {
        return apellidos;
    }

    @XmlElement
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    @XmlElement
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCedula() {
        return cedula;
    }

    @XmlElement
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getProvincia() {
        return provincia;
    }

    @XmlElement
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    @XmlElement
    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getParroquia() {
        return parroquia;
    }

    @XmlElement
    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getDireccion() {
        return direccion;
    }

    @XmlElement
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    @XmlElement
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTelefono() {
        return telefono;
    }

    @XmlElement
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    @XmlElement
    public void setMovil(String movil) {
        this.movil = movil;
    }
}
