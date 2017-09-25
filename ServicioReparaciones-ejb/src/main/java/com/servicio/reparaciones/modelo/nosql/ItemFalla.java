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
public class ItemFalla {

    private Integer index;
    private Falla falla;

    public ItemFalla() {
    }

    public ItemFalla(Integer index, Falla falla) {
        this.index = index;
        this.falla = falla;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Falla getFalla() {
        return falla;
    }

    public void setFalla(Falla falla) {
        this.falla = falla;
    }

}
