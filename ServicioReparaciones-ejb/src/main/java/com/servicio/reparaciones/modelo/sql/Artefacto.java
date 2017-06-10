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
@Table(name = "Artefacto")
public class Artefacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodigoArtefacto",nullable = false)
    private Integer codigoArtefacto;
    
    @Column(name = "Artefacto",nullable = false,length = 100)
    private String artefacto;

    public Artefacto() {
    }

    public Artefacto(Integer codigoArtefacto) {
        this.codigoArtefacto = codigoArtefacto;
    }

    public Integer getCodigoArtefacto() {
        return codigoArtefacto;
    }

    public void setCodigoArtefacto(Integer codigoArtefacto) {
        this.codigoArtefacto = codigoArtefacto;
    }

    public String getArtefacto() {
        return artefacto;
    }

    public void setArtefacto(String artefacto) {
        this.artefacto = artefacto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoArtefacto != null ? codigoArtefacto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artefacto)) {
            return false;
        }
        Artefacto other = (Artefacto) object;
        if ((this.codigoArtefacto == null && other.codigoArtefacto != null) || (this.codigoArtefacto != null && !this.codigoArtefacto.equals(other.codigoArtefacto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.servicio.reparaciones.modelo.sql.Artefacto[ codigoArtefacto=" + codigoArtefacto + " ]";
    }
    
}
