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
 * @author luis CREAR ENTRADA EN BLANCO CERO
 */
@Entity(value = "Entrada", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Entrada extends BaseEntity {

    private Integer codigo;
    private Double signo;
    private String concepto;
    private String numeroFactura;
    private Double cantidad;
    private Double precioUnit;
    private Double precioTotal;
    private Integer flag;

    @Reference
    private Articulo articulo;
    @Reference
    private Bodega bodega;
    @Reference
    private Usuario username;

    public Entrada() {
        this.codigo = 0;
        this.signo = 1.00;
        this.numeroFactura = "";
        this.cantidad = 0.00;
        this.precioUnit = 0.00;
        this.precioTotal = 0.00;
        this.articulo = new Articulo();
        this.bodega = new Bodega();
        this.username = new Usuario();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
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
        return precioTotal = this.getPrecioUnit() * this.getCantidad();
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

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Double getSigno() {
        return signo;
    }

    public void setSigno(Double signo) {
        this.signo = signo;
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
        hash = 67 * hash + Objects.hashCode(this.codigo);
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
        final Entrada other = (Entrada) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entrada{" + "codigo=" + codigo + ", numeroFactura=" + numeroFactura + ", cantidad=" + cantidad + ", precioUnit=" + precioUnit + ", precioTotal=" + precioTotal + ", flag=" + flag + ", articulo=" + articulo + ", username=" + username + '}';
    }
}
