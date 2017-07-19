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
public class Movimiento {

    private Entrada entrada;
    private Salida salida;

    public Movimiento() {
        this.entrada = new Entrada();
        this.salida = new Salida();
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Salida getSalida() {
        return salida;
    }

    public void setSalida(Salida salida) {
        this.salida = salida;
    }

}
