/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "Almacen")
public class Almacen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodigoAlmacen",nullable = false)
    private Integer codigoAlmacen;
    
    @Column(name = "Almacen",nullable = false,length = 150)
    private String almacen;

    public Almacen() {
    }

    public Almacen(Integer codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public Integer getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(Integer codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoAlmacen != null ? codigoAlmacen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Almacen)) {
            return false;
        }
        Almacen other = (Almacen) object;
        if ((this.codigoAlmacen == null && other.codigoAlmacen != null) || (this.codigoAlmacen != null && !this.codigoAlmacen.equals(other.codigoAlmacen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.servicio.reparaciones.modelo.sql.Almacen[ codigoAlmacen=" + codigoAlmacen + " ]";
    }
    
}
