/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.mongo.persistance.BaseEntity;
import java.util.Objects;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis CREAR SALIDA EN BLANCO CERO
 */
@Entity(value = "Salida", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Salida extends BaseEntity {

    private Integer codigo;
    private Double cantidad;
    private Double precioUnit;
    private Double precioTotal;
    private Integer flag;

    @Reference
    private Articulo articulo;
    @Reference
    private Tecnico recibe;
    @Reference
    private Usuario username;

    public Salida() {
        this.cantidad = 0.00;
        this.precioUnit = 0.00;
        this.precioTotal = 0.00;
        this.articulo = new Articulo();
        this.recibe = new Tecnico();
        this.username = new Usuario();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(Double precioUnit) {
        this.precioUnit = precioUnit;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Tecnico getRecibe() {
        return recibe;
    }

    public void setRecibe(Tecnico recibe) {
        this.recibe = recibe;
    }

    public Usuario getUsername() {
        return username;
    }

    public void setUsername(Usuario username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.codigo);
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
        final Salida other = (Salida) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Salida{" + "codigo=" + codigo + ", cantidad=" + cantidad + ", precioUnit=" + precioUnit + ", precioTotal=" + precioTotal + ", flag=" + flag + ", articulo=" + articulo + ", username=" + username + '}';
    }

}
