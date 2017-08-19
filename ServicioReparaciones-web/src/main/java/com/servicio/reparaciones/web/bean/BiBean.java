/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author luis
 */
@Named(value = "biBean")
@ViewScoped
public class BiBean implements Serializable {

    /*
   if (this.nuevo.getModelo() != null && !this.nuevo.getModelo().equals("")) {
                this.nuevo.setModelo(this.nuevo.getModelo().trim());
                this.nuevo.setModelo(this.nuevo.getModelo().toUpperCase());
            }
            if (this.nuevo.getSerie() != null && !this.nuevo.getSerie().equals("")) {
                this.nuevo.setSerie(this.nuevo.getSerie().trim());
                this.nuevo.setSerie(this.nuevo.getSerie().toUpperCase());
            }
            if (this.nuevo.getPlaca() != null && !this.nuevo.getPlaca().equals("")) {
                this.nuevo.setPlaca(this.nuevo.getPlaca().trim());
                this.nuevo.setPlaca(this.nuevo.getPlaca().toUpperCase());
            }
            if (this.nuevo.getPnc() != null && !this.nuevo.getPnc().equals("")) {
                this.nuevo.setPnc(this.nuevo.getPnc().trim());
                this.nuevo.setPnc(this.nuevo.getPnc().toUpperCase());
            }
     */
    private static final long serialVersionUID = -2821429635055287173L;

}
