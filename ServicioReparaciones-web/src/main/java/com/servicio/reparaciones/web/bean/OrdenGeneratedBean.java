/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Orden;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author luis
 */
@Named(value = "ordenGeneratedBean")
@ViewScoped
public class OrdenGeneratedBean implements Serializable{

    private static final long serialVersionUID = 1823172210245701587L;
    
    private Orden abierta;
    private Orden cerrada;
    private Orden pendiente;

    @PostConstruct
    public void init(){
        this.abierta = new Orden();
        this.cerrada = new Orden();
        this.pendiente = new Orden();
    }

    public Orden getAbierta() {
        return abierta;
    }

    public void setAbierta(Orden abierta) {
        this.abierta = abierta;
    }

    public Orden getCerrada() {
        return cerrada;
    }

    public void setCerrada(Orden cerrada) {
        this.cerrada = cerrada;
    }

    public Orden getPendiente() {
        return pendiente;
    }

    public void setPendiente(Orden pendiente) {
        this.pendiente = pendiente;
    }
}
