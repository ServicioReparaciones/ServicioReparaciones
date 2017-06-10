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
public class ItemRepuesto {

    private Integer index;
    private Integer cantidad;
    private Double subTotal;

    @Reference
    private Repuesto repuesto;

    public ItemRepuesto() {
        this.cantidad = 0;
        this.subTotal = 0d;
        this.repuesto = new Repuesto();
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubTotal() {
        return this.getCantidad() * this.repuesto.getPrecio();
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
