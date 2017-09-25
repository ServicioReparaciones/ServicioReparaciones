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
 * @author luis
 */
@Entity(value = "Falla", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Falla extends BaseEntity {

    private Integer codigo;
    private String code;
    private String marca;
    private String descripcion;
    private Integer flag;

    @Reference
    private Usuario username;

    public Falla() {
    }

    public Falla(Integer codigo, String code, String marca, String descripcion) {
        this.codigo = codigo;
        this.code = code;
        this.marca = marca;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Usuario getUsername() {
        return username;
    }

    public void setUsername(Usuario username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.codigo);
        hash = 79 * hash + Objects.hashCode(this.code);
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
        final Falla other = (Falla) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Falla{" + "codigo=" + codigo + ", code=" + code + ", marca=" + marca + ", descripcion=" + descripcion + '}';
    }
}
