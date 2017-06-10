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
public class VisitaXml {
    
    private String fechaVisitaCliente;
    private String fechaEntregaProducto;
    private String fechaLlegadaCliente;
    private String fechaSalidaCliente;
    private String lugarAtencion;
    private String observacionCliente;
    private String posibleFalla;
    private String servicio;

    public VisitaXml() {
    }

    public String getFechaVisitaCliente() {
        return fechaVisitaCliente;
    }

    @XmlElement
    public void setFechaVisitaCliente(String fechaVisitaCliente) {
        this.fechaVisitaCliente = fechaVisitaCliente;
    }

    public String getFechaEntregaProducto() {
        return fechaEntregaProducto;
    }

    @XmlElement
    public void setFechaEntregaProducto(String fechaEntregaProducto) {
        this.fechaEntregaProducto = fechaEntregaProducto;
    }

    public String getFechaLlegadaCliente() {
        return fechaLlegadaCliente;
    }

    @XmlElement
    public void setFechaLlegadaCliente(String fechaLlegadaCliente) {
        this.fechaLlegadaCliente = fechaLlegadaCliente;
    }

    public String getFechaSalidaCliente() {
        return fechaSalidaCliente;
    }

    @XmlElement
    public void setFechaSalidaCliente(String fechaSalidaCliente) {
        this.fechaSalidaCliente = fechaSalidaCliente;
    }

    public String getLugarAtencion() {
        return lugarAtencion;
    }

    @XmlElement
    public void setLugarAtencion(String lugarAtencion) {
        this.lugarAtencion = lugarAtencion;
    }

    public String getObservacionCliente() {
        return observacionCliente;
    }

    @XmlElement
    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }

    public String getPosibleFalla() {
        return posibleFalla;
    }

    @XmlElement
    public void setPosibleFalla(String posibleFalla) {
        this.posibleFalla = posibleFalla;
    }

    public String getServicio() {
        return servicio;
    }

    @XmlElement
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
