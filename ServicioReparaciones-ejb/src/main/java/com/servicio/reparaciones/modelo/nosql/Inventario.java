/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.mongo.persistance.BaseEntity;
import java.util.Objects;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "Inventario", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Inventario extends BaseEntity {

    private Integer codigo;
    private Integer signo; // 1 entrada -1 salida
    private Double cantidad;
    private Double precioUnit;
    private Double precioTotal;
    private Integer flag;

    @Embedded
    private Movimiento movieminto;
    @Reference
    private Articulo articulo;
    @Reference
    private Bodega bodega;
    @Reference
    private Usuario username;

    public Inventario() {
        this.cantidad = 0.00;
        this.precioUnit = 0.00;
        this.movieminto = new Movimiento();
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

    public Integer getSigno() {
        return signo;
    }

    public void setSigno(Integer signo) {
        this.signo = signo;
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

    public Movimiento getMovieminto() {
        return movieminto;
    }

    public void setMovieminto(Movimiento movieminto) {
        this.movieminto = movieminto;
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

    public Usuario getUsername() {
        return username;
    }

    public void setUsername(Usuario username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.codigo);
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
        return "Inventario{" + "codigo=" + codigo + ", signo=" + signo + ", cantidad=" + cantidad + ", precioUnit=" + precioUnit + ", precioTotal=" + precioTotal + ", flag=" + flag + ", movieminto=" + movieminto + ", articulo=" + articulo + ", bodega=" + bodega + ", username=" + username + '}';
    }

}
