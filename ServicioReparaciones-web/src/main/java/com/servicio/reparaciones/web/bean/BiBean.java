/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.servicio.BiOrdenService;
import com.servicio.reparaciones.servicio.OrdenServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@Named(value = "biBean")
@ViewScoped
public class BiBean implements Serializable {

    private static final long serialVersionUID = -2821429635055287173L;
    private static final Logger LOG = Logger.getLogger(BiBean.class.getName());

    @Inject
    private OrdenServicio ordenService;

    public void loadData(ActionEvent evt) {
//        List<Orden> odenes = new ArrayList<>();
//        odenes = this.ordenService.ObtenerListaOrdens();
//        LOG.log(Level.INFO, "ordenes :" + odenes.size());
//        for (Orden ord : odenes) {
//            if (ord.getMovimientosInternos() != null && !ord.getMovimientosInternos().isEmpty()) {
//                LOG.log(Level.INFO, "ordenes >>>>>>  :" + ord.getCodigo());
//            }
//        }
//        LOG.log(Level.INFO, "ordenes finazilo con exito");

    }

}
