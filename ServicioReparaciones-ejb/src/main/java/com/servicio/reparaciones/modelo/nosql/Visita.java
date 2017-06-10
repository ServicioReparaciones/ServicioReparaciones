/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.mongo.persistance.BaseEntity;
import java.util.Date;
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
@Entity(value = "Visita", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Visita extends BaseEntity {

    private Integer codigo;
    private String unique;
    private Date fechaVisitaCliente;
    private Date fechaEntregaProducto;
    private Date fechaLlegadaCliente;
    private Date fechaSalidaCliente;
    private String lugarAtencion;
    private String observacionCliente;
    private String posibleFalla;
    private Integer flag;

    @Reference
    private Cliente cliente;
    @Reference
    private Producto producto;
    @Reference
    private Servicio servicio;
    @Reference
    private Usuario username;

    public Visita() {
        this.lugarAtencion = "";
        this.observacionCliente = "";
        this.posibleFalla = "";
        this.cliente = new Cliente();
        this.producto = new Producto();
        this.servicio = new Servicio();
        this.username = new Usuario();
    }

    public Visita(String unique) {
        this.unique = unique;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public Date getFechaVisitaCliente() {
        return fechaVisitaCliente;
    }

    public void setFechaVisitaCliente(Date fechaVisitaCliente) {
        this.fechaVisitaCliente = fechaVisitaCliente;
    }

    public Date getFechaEntregaProducto() {
        return fechaEntregaProducto;
    }

    public void setFechaEntregaProducto(Date fechaEntregaProducto) {
        this.fechaEntregaProducto = fechaEntregaProducto;
    }

    public Date getFechaLlegadaCliente() {
        return fechaLlegadaCliente;
    }

    public void setFechaLlegadaCliente(Date fechaLlegadaCliente) {
        this.fechaLlegadaCliente = fechaLlegadaCliente;
    }

    public Date getFechaSalidaCliente() {
        return fechaSalidaCliente;
    }

    public void setFechaSalidaCliente(Date fechaSalidaCliente) {
        this.fechaSalidaCliente = fechaSalidaCliente;
    }

    public String getLugarAtencion() {
        return lugarAtencion;
    }

    public void setLugarAtencion(String lugarAtencion) {
        this.lugarAtencion = lugarAtencion;
    }

    public String getObservacionCliente() {
        return observacionCliente;
    }

    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }

    public String getPosibleFalla() {
        return posibleFalla;
    }

    public void setPosibleFalla(String posibleFalla) {
        this.posibleFalla = posibleFalla;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Usuario getUsername() {
        return username;
    }

    public void setUsername(Usuario username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.codigo);
        hash = 59 * hash + Objects.hashCode(this.unique);
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
        final Visita other = (Visita) obj;
        if (!Objects.equals(this.unique, other.unique)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Visita{" + "codigo=" + codigo + ", unique=" + unique + ", fechaVisitaCliente=" + fechaVisitaCliente + ", fechaEntregaProducto=" + fechaEntregaProducto + ", fechaLlegadaCliente=" + fechaLlegadaCliente + ", fechaSalidaCliente=" + fechaSalidaCliente + ", lugarAtencion=" + lugarAtencion + ", observacionCliente=" + observacionCliente + ", posibleFalla=" + posibleFalla + ", flag=" + flag + ", cliente=" + cliente + ", producto=" + producto + ", servicio=" + servicio + ", username=" + username + '}';
    }
}
