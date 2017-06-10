/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "Parroquia")
public class Parroquia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CodigoParroquia",nullable = false)
    private Integer codigoParroquia;

    @Column(name = "Parroquia",nullable = false,length = 300)
    private String parroquia;
    
    @JoinColumn(name = "CodigoCanton", referencedColumnName = "CodigoCanton")
    @ManyToOne(optional = false)
    private Canton canton;

    public Parroquia() {
    }

    public Parroquia(Integer codigoParroquia) {
        this.codigoParroquia = codigoParroquia;
    }

    public Integer getCodigoParroquia() {
        return codigoParroquia;
    }

    public void setCodigoParroquia(Integer codigoParroquia) {
        this.codigoParroquia = codigoParroquia;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public Canton getCanton() {
        return canton;
    }

    public void setCanton(Canton canton) {
        this.canton = canton;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoParroquia != null ? codigoParroquia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parroquia)) {
            return false;
        }
        Parroquia other = (Parroquia) object;
        if ((this.codigoParroquia == null && other.codigoParroquia != null) || (this.codigoParroquia != null && !this.codigoParroquia.equals(other.codigoParroquia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.servicio.reparaciones.modelo.sql.Parroquia[ codigoParroquia=" + codigoParroquia + " ]";
    }
    
}
