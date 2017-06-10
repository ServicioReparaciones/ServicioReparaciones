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
public class OrdenXml {

    private String codigo;
    private String barcode;
    private String creationDate;
    private String numeroOrden;
    private String numeroTicket;
    private String url;
    private VisitaXml visita;
    private ClienteXml cliente;
    private ProductoXml producto;

    public OrdenXml() {
        this.visita = new VisitaXml();
        this.cliente = new ClienteXml();
        this.producto = new ProductoXml();
    }

    public String getCodigo() {
        return codigo;
    }

    @XmlElement
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getBarcode() {
        return barcode;
    }

    @XmlElement
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    @XmlElement
    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getNumeroTicket() {
        return numeroTicket;
    }

    @XmlElement
    public void setNumeroTicket(String numeroTicket) {
        this.numeroTicket = numeroTicket;
    }

    public String getUrl() {
        return url;
    }

    @XmlElement
    public void setUrl(String url) {
        this.url = url;
    }

    public VisitaXml getVisita() {
        return visita;
    }

    @XmlElement
    public void setVisita(VisitaXml visita) {
        this.visita = visita;
    }

    public ClienteXml getCliente() {
        return cliente;
    }

    @XmlElement
    public void setCliente(ClienteXml cliente) {
        this.cliente = cliente;
    }

    public ProductoXml getProducto() {
        return producto;
    }

    @XmlElement
    public void setProducto(ProductoXml producto) {
        this.producto = producto;
    }

    public String getCreationDate() {
        return creationDate;
    }

    @XmlElement
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
