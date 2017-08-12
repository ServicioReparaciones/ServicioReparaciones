/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.mongo.persistance.BaseEntity;
import java.util.Objects;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "Inventario", noClassnameStored = true)
public class Inventario extends BaseEntity {

    private String codigo;
    private Double cantidad;
    private Integer flag;

    @Reference
    private Bodega bodega;
    @Reference
    private Articulo articulo;

    public Inventario() {
        this.bodega = new Bodega();
        this.articulo = new Articulo();
        this.cantidad = 0.0;
    }

    public Inventario(String codigo, Double cantidad, Bodega bodega, Articulo articulo) {
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.bodega = bodega;
        this.articulo = articulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inventario other = (Inventario) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inventario{" + "codigo=" + codigo + ", cantidad=" + cantidad + ", flag=" + flag + ", bodega=" + bodega + ", articulo=" + articulo + '}';
    }

}
