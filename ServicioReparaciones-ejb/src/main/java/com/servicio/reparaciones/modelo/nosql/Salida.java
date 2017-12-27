/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.servicio.reparaciones.util.entity.BaseEntity;
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
    private String code;
    private Double signo;
    private String concepto;
    private String numeroFactura;
    private Double cantidad;
    private Double precioUnit;
    private Double precioTotal;
    private String url;
    private Integer flag;

    @Reference
    private Articulo articulo;
    @Reference
    private Bodega bodega;
    @Reference
    private Tecnico quienRecibe;
    @Reference
    private Usuario username;

    public Salida() {
        this.numeroFactura = "";
        this.codigo = 0;
        this.signo = -1.00;
        this.cantidad = 0.00;
        this.precioUnit = 0.00;
        this.precioTotal = 0.00;
        this.articulo = new Articulo();
        this.bodega = new Bodega();
        this.quienRecibe = new Tecnico();
        this.username = new Usuario();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Tecnico getQuienRecibe() {
        return quienRecibe;
    }

    public void setQuienRecibe(Tecnico quienRecibe) {
        this.quienRecibe = quienRecibe;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
