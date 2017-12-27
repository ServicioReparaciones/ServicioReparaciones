/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.servicio.reparaciones.util.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
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
@Entity(value = "Orden", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Orden extends BaseEntity {

    private Integer codigo;
    private String barcode;
    private String numeroOrden;
    private String numeroTicket;
    private Double subTotalRepuestos;
    private Double subTotalServicios;
    private Double subTotalKilometraje;
    private String url;
    private Integer flag;

    @Embedded
    private Ciclo ciclo;
    @Reference
    private Cliente cliente;
    @Reference
    private Visita visita;
    @Reference
    private Producto producto;
    @Reference
    private Tecnico tecnico;
    @Embedded
    private Kilometraje kilometrosRuta;
    @Embedded
    private List<ItemRepuesto> detalleRepuestos;
    @Embedded
    private List<ItemServicio> detalleServicios;
    @Embedded
    private List<ItemFalla> listaFallas;
    @Embedded
    private TrabajoFinalEjecutado trabajoFinalEjecutado;
    @Embedded
    private List<Comentario> movimientosInternos;

    @Reference
    private Usuario username;

    public Orden() {
        this.numeroOrden = "";
        this.numeroTicket = "";
        this.subTotalRepuestos = 0d;
        this.subTotalServicios = 0d;
        this.subTotalKilometraje = 0d;
        this.ciclo = new Ciclo();
        this.cliente = new Cliente();
        this.visita = new Visita();
        this.producto = new Producto();
        this.tecnico = new Tecnico();
        this.kilometrosRuta = new Kilometraje();
        this.detalleRepuestos = new ArrayList<>();
        this.detalleServicios = new ArrayList<>();
        this.listaFallas = new ArrayList<>();
        this.trabajoFinalEjecutado = new TrabajoFinalEjecutado();
        this.movimientosInternos = new ArrayList<>();
        this.username = new Usuario();
    }

    public Orden(Integer codigo, String barcode, String numeroOrden, String numeroTicket) {
        this.codigo = codigo;
        this.barcode = barcode;
        this.numeroOrden = numeroOrden;
        this.numeroTicket = numeroTicket;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getNumeroTicket() {
        return numeroTicket;
    }

    public void setNumeroTicket(String numeroTicket) {
        this.numeroTicket = numeroTicket;
    }

    public Double getSubTotalRepuestos() {
        return subTotalRepuestos;
    }

    public void setSubTotalRepuestos(Double subTotalRepuestos) {
        this.subTotalRepuestos = subTotalRepuestos;
    }

    public Double getSubTotalServicios() {
        return subTotalServicios;
    }

    public void setSubTotalServicios(Double subTotalServicios) {
        this.subTotalServicios = subTotalServicios;
    }

    public Double getSubTotalKilometraje() {
        return subTotalKilometraje;
    }

    public void setSubTotalKilometraje(Double subTotalKilometraje) {
        this.subTotalKilometraje = subTotalKilometraje;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Kilometraje getKilometrosRuta() {
        return kilometrosRuta;
    }

    public void setKilometrosRuta(Kilometraje kilometrosRuta) {
        this.kilometrosRuta = kilometrosRuta;
    }

    public List<ItemRepuesto> getDetalleRepuestos() {
        return detalleRepuestos;
    }

    public void setDetalleRepuestos(List<ItemRepuesto> detalleRepuestos) {
        this.detalleRepuestos = detalleRepuestos;
    }

    public List<ItemServicio> getDetalleServicios() {
        return detalleServicios;
    }

    public void setDetalleServicios(List<ItemServicio> detalleServicios) {
        this.detalleServicios = detalleServicios;
    }

    public TrabajoFinalEjecutado getTrabajoFinalEjecutado() {
        return trabajoFinalEjecutado;
    }

    public void setTrabajoFinalEjecutado(TrabajoFinalEjecutado trabajoFinalEjecutado) {
        this.trabajoFinalEjecutado = trabajoFinalEjecutado;
    }

    public List<Comentario> getMovimientosInternos() {
        return movimientosInternos;
    }

    public void setMovimientosInternos(List<Comentario> movimientosInternos) {
        this.movimientosInternos = movimientosInternos;
    }

    public Usuario getUsername() {
        return username;
    }

    public void setUsername(Usuario username) {
        this.username = username;
    }
    
        public List<ItemFalla> getListaFallas() {
        return listaFallas;
    }

    public void setListaFallas(List<ItemFalla> listaFallas) {
        this.listaFallas = listaFallas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.codigo);
        hash = 37 * hash + Objects.hashCode(this.numeroOrden);
        hash = 37 * hash + Objects.hashCode(this.numeroTicket);
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
        final Orden other = (Orden) obj;
        if (!Objects.equals(this.numeroOrden, other.numeroOrden)) {
            return false;
        }
        if (!Objects.equals(this.numeroTicket, other.numeroTicket)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Orden{" + "codigo=" + codigo + ", barcode=" + barcode + ", numeroOrden=" + numeroOrden + ", numeroTicket=" + numeroTicket + ", subTotalRepuestos=" + subTotalRepuestos + ", subTotalServicios=" + subTotalServicios + ", subtotalKilometraje=" + subTotalKilometraje + ", url=" + url + ", flag=" + flag + ", ciclo=" + ciclo + ", cliente=" + cliente + ", vista=" + visita + ", producto=" + producto + ", tecnico=" + tecnico + ", kilometrosRuta=" + kilometrosRuta + ", detalleRepuestos=" + detalleRepuestos + ", detalleServicios=" + detalleServicios + ", trabajoFinaEjecutado=" + trabajoFinalEjecutado + ", username=" + username + '}';
    }
}
