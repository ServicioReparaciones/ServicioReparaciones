/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "Canton")
public class Canton implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CodigoCanton",nullable = false)
    private Integer codigoCanton;
    
    @Column(name = "Canton",nullable = false,length = 300)
    private String canton;
    
    @JoinColumn(name = "CodigoProvincia", referencedColumnName = "CodigoProvincia")
    @ManyToOne(optional = false)
    private Provincia provincia;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "canton")
    private List<Parroquia> parroquiaList;

    public Canton() {
    }

    public Canton(Integer codigoCanton) {
        this.codigoCanton = codigoCanton;
    }

    public Integer getCodigoCanton() {
        return codigoCanton;
    }

    public void setCodigoCanton(Integer codigoCanton) {
        this.codigoCanton = codigoCanton;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public List<Parroquia> getParroquiaList() {
        return parroquiaList;
    }

    public void setParroquiaList(List<Parroquia> parroquiaList) {
        this.parroquiaList = parroquiaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCanton != null ? codigoCanton.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canton)) {
            return false;
        }
        Canton other = (Canton) object;
        if ((this.codigoCanton == null && other.codigoCanton != null) || (this.codigoCanton != null && !this.codigoCanton.equals(other.codigoCanton))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.servicio.reparaciones.modelo.sql.Canton[ codigoCanton=" + codigoCanton + " ]";
    }
    
}
