/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.util;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 *
 * @author luis
 */
public class Calendario implements Serializable {

    private static final long serialVersionUID = 7183373318551230873L;

    private GregorianCalendar calendario;

    public Calendario() {
        this.calendario = new GregorianCalendar();
    }

    public GregorianCalendar getCalendario() {
        return calendario;
    }

}
