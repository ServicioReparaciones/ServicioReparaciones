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
public class SalidaXml {
    
    private String barcode;
    private String signo;
    private String numeroFactura;
    private String concepto;
    private String cantidad;
    private String articulo;
    private String bodega;
    private String recibe;
    private String entrega;
    private String fechaSalidaArticulo;

    public SalidaXml() {
    }
    
    public String getBarcode() {
        return barcode;
    }

    @XmlElement
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSigno() {
        return signo;
    }

    @XmlElement
    public void setSigno(String signo) {
        this.signo = signo;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    @XmlElement
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getCantidad() {
        return cantidad;
    }

    @XmlElement
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getArticulo() {
        return articulo;
    }

    @XmlElement
    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getBodega() {
        return bodega;
    }

    @XmlElement
    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getRecibe() {
        return recibe;
    }

    @XmlElement
    public void setRecibe(String recibe) {
        this.recibe = recibe;
    }

    public String getEntrega() {
        return entrega;
    }

    @XmlElement
    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getFechaSalidaArticulo() {
        return fechaSalidaArticulo;
    }

    @XmlElement
    public void setFechaSalidaArticulo(String fechaSalidaArticulo) {
        this.fechaSalidaArticulo = fechaSalidaArticulo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
