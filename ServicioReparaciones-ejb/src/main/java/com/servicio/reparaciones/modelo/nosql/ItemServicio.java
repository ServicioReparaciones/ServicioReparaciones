/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class ItemServicio {

    private Integer index;
    private Integer cantidad;
    private Double subTotal;

    @Reference
    private Servicio servicio;

    public ItemServicio() {
        this.cantidad = 0;
        this.subTotal = 0d;
        this.servicio = new Servicio();
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubTotal() {
        return this.getCantidad() * this.servicio.getPrecio();
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
