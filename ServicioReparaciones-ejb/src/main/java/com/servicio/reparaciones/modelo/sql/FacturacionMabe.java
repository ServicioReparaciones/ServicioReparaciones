/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "FacturacionMabe")
public class FacturacionMabe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "NOTIFICACION", nullable = false)
    private String notificacion;
    @Column(name = "TALLER_AUTORIZADO", nullable = false, length = 1)
    private String tallerAutorizado;
    @Column(name = "FECHA_DE_CIERRE_SISTEMA", nullable = false, length = 100)
    private String fechaDeCierreSistema;
    @Column(name = "CODIGO_DE_TALLER", nullable = false, length = 100)
    private String codigoDeTaller;
    @Column(name = "VALOR_FIJO", nullable = false, length = 100)
    private String valorFijo;
    @Column(name = "INDEX", nullable = false)
    private Integer index;
    @Column(name = "FLAG", nullable = false)
    private Integer flag;

    public FacturacionMabe() {
    }

    public FacturacionMabe(String notificacion) {
        this.notificacion = notificacion;
    }

    public String getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    public String getTallerAutorizado() {
        return tallerAutorizado;
    }

    public void setTallerAutorizado(String tallerAutorizado) {
        this.tallerAutorizado = tallerAutorizado;
    }

    public String getFechaDeCierreSistema() {
        return fechaDeCierreSistema;
    }

    public void setFechaDeCierreSistema(String fechaDeCierreSistema) {
        this.fechaDeCierreSistema = fechaDeCierreSistema;
    }

    public String getCodigoDeTaller() {
        return codigoDeTaller;
    }

    public void setCodigoDeTaller(String codigoDeTaller) {
        this.codigoDeTaller = codigoDeTaller;
    }

    public String getValorFijo() {
        return valorFijo;
    }

    public void setValorFijo(String valorFijo) {
        this.valorFijo = valorFijo;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificacion != null ? notificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturacionMabe)) {
            return false;
        }
        FacturacionMabe other = (FacturacionMabe) object;
        if ((this.notificacion == null && other.notificacion != null) || (this.notificacion != null && !this.notificacion.equals(other.notificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "map.xlsx.mysql.map.FacturacionMabe[ notificacion=" + notificacion + " ]";
    }

}
