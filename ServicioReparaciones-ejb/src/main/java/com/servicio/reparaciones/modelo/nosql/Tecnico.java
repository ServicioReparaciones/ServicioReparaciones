/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.servicio.reparaciones.util.entity.BaseEntity;
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
@Entity(value = "Tecnico", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Tecnico extends BaseEntity {

    private Integer codigo;
    private String cargo;
    private Integer flag;

    @Embedded
    private InformacionPersonal datosPersonales;
    @Reference
    private Usuario username;

    public Tecnico() {
        this.datosPersonales = new InformacionPersonal();
        this.username = new Usuario();
    }

    public Tecnico(String cargo) {
        this.cargo = cargo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public InformacionPersonal getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(InformacionPersonal datosPersonales) {
        this.datosPersonales = datosPersonales;
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
        hash = 31 * hash + Objects.hashCode(this.codigo);
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
        final Tecnico other = (Tecnico) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tecnico{" + "codigo=" + codigo + ", cargo=" + cargo + ", flag=" + flag + ", datosPersonales=" + datosPersonales + ", username=" + username + '}';
    }

}
