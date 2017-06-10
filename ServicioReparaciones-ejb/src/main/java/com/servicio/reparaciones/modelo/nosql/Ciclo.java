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
public class Ciclo {

    @Embedded
    private Estado abierta;
    @Embedded
    private Estado cerrada;
    @Embedded
    private Estado pendiente;
    @Reference
    private Integer flag;

    public Ciclo() {
        this.abierta = new Estado(Boolean.FALSE, "ABIERTA", "", new Usuario());
        this.cerrada = new Estado(Boolean.FALSE, "CERRADA", "", new Usuario());
        this.pendiente = new Estado(Boolean.FALSE, "PENDIENTE", "", new Usuario());
        this.flag = 1;
    }

    public Estado getAbierta() {
        return abierta;
    }

    public void setAbierta(Estado abierta) {
        this.abierta = abierta;
    }

    public Estado getCerrada() {
        return cerrada;
    }

    public void setCerrada(Estado cerrada) {
        this.cerrada = cerrada;
    }

    public Estado getPendiente() {
        return pendiente;
    }

    public void setPendiente(Estado pendiente) {
        this.pendiente = pendiente;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
    
}
