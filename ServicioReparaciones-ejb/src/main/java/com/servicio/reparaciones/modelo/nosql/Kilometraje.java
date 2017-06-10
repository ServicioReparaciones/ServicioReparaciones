/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * @author luis
 */
@Embedded
public class Kilometraje {

    private Double kmRecorridos;
    private Double valorPorKmRecorrido;
    private Double subTotal;
    private String observacionRuta;

    public Kilometraje() {
        this.observacionRuta = "";
        this.kmRecorridos = 0d;
        this.valorPorKmRecorrido = 0d;
        this.subTotal = 0d;
        this.subTotal = 0d;
    }

    public Double getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(Double kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public Double getValorPorKmRecorrido() {
        return valorPorKmRecorrido;
    }

    public void setValorPorKmRecorrido(Double valorPorKmRecorrido) {
        this.valorPorKmRecorrido = valorPorKmRecorrido;
    }

    public Double getSubTotal() {
        return this.getKmRecorridos() * this.getValorPorKmRecorrido();
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getObservacionRuta() {
        return observacionRuta;
    }

    public void setObservacionRuta(String observacionRuta) {
        this.observacionRuta = observacionRuta;
    }
}
