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

/**
 *
 * @author luis
 */
@Entity(value = "BiOrden", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class BiOrden extends BaseEntity {

    private Integer codigo;

    private String barcode;
    private String numeroOrden;
    private String numeroTicket;

    private String cliente;
    private String cedula;
    private String provincia;
    private String canton;
    private String parroquia;
    private String direccion;
    private String referencia;
    private String telefono;
    private String movil;

    private Date fechaVisitaCliente;
    private Date fechaEntregaProducto;
    private Date fechaLlegadaCliente;
    private Date fechaSalidaCliente;
    private String lugarAtencion;
    private String posibleFalla;
    private String observacionCliente;

    private String artefacto;
    private String marca;
    private String modelo;
    private String serie;
    private String pnc;
    private String placa;
    private Boolean warranty;
    private Boolean stock;

    private String almacen;
    private String telefonoAlmacen;
    private String numeroFactura;
    private Date fechaFactura;

    private Integer tecnico_codigo;
    private String tecnico_cargo;
    private String tecnico;

    private Integer usuario_codigo;
    private String usuario;

    private Double kmRecorridos;
    private Double valorPorKmRecorrido;
    private String observacionRuta;

    private String desperfecto;
    private String trabajoRealizado;
    private String observaciones;

    private Double subTotalRepuestos;
    private Double subTotalServicios;
    private Double subTotalKilometraje;

    private String abierta_usuario;
    private Boolean abierta_active;
    private String abierta_alias;
    private String abierta_comentario;
    private String abierta_dd;
    private String abierta_mm;
    private String abierta_yyyy;

    private String cerrada_usuario;
    private Boolean cerrada_active;
    private String cerrada_alias;
    private String cerrada_comentario;
    private String cerrada_dd;
    private String cerrada_mm;
    private String cerrada_yyyy;

    private String pendiente_usuario;
    private Boolean pendiente_active;
    private String pendiente_alias;
    private String pendiente_comentario;
    private String pendiente_dd;
    private String pendiente_mm;
    private String pendiente_yyyy;

    private String cancelada_usuario;
    private Boolean cancelada_active;
    private String cancelada_alias;
    private String cancelada_comentario;
    private String cancelada_dd;
    private String cancelada_mm;
    private String cancelada_yyyy;

    private Integer flag;

    public BiOrden() {
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
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

    public String getPosibleFalla() {
        return posibleFalla;
    }

    public void setPosibleFalla(String posibleFalla) {
        this.posibleFalla = posibleFalla;
    }

    public String getObservacionCliente() {
        return observacionCliente;
    }

    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }

    public String getArtefacto() {
        return artefacto;
    }

    public void setArtefacto(String artefacto) {
        this.artefacto = artefacto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getPnc() {
        return pnc;
    }

    public void setPnc(String pnc) {
        this.pnc = pnc;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Boolean getWarranty() {
        return warranty;
    }

    public void setWarranty(Boolean warranty) {
        this.warranty = warranty;
    }

    public Boolean getStock() {
        return stock;
    }

    public void setStock(Boolean stock) {
        this.stock = stock;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getTelefonoAlmacen() {
        return telefonoAlmacen;
    }

    public void setTelefonoAlmacen(String telefonoAlmacen) {
        this.telefonoAlmacen = telefonoAlmacen;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Integer getTecnico_codigo() {
        return tecnico_codigo;
    }

    public void setTecnico_codigo(Integer tecnico_codigo) {
        this.tecnico_codigo = tecnico_codigo;
    }

    public String getTecnico_cargo() {
        return tecnico_cargo;
    }

    public void setTecnico_cargo(String tecnico_cargo) {
        this.tecnico_cargo = tecnico_cargo;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public Integer getUsuario_codigo() {
        return usuario_codigo;
    }

    public void setUsuario_codigo(Integer usuario_codigo) {
        this.usuario_codigo = usuario_codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Double getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(Double kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public Double getValorPorKmRecorrido() {
        return valorPorKmRecorrido;
    }

    public void setValorPorKmRecorrido(Double valorPorKmRecorrido) {
        this.valorPorKmRecorrido = valorPorKmRecorrido;
    }

    public String getObservacionRuta() {
        return observacionRuta;
    }

    public void setObservacionRuta(String observacionRuta) {
        this.observacionRuta = observacionRuta;
    }

    public String getDesperfecto() {
        return desperfecto;
    }

    public void setDesperfecto(String desperfecto) {
        this.desperfecto = desperfecto;
    }

    public String getTrabajoRealizado() {
        return trabajoRealizado;
    }

    public void setTrabajoRealizado(String trabajoRealizado) {
        this.trabajoRealizado = trabajoRealizado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public String getAbierta_usuario() {
        return abierta_usuario;
    }

    public void setAbierta_usuario(String abierta_usuario) {
        this.abierta_usuario = abierta_usuario;
    }

    public Boolean getAbierta_active() {
        return abierta_active;
    }

    public void setAbierta_active(Boolean abierta_active) {
        this.abierta_active = abierta_active;
    }

    public String getAbierta_alias() {
        return abierta_alias;
    }

    public void setAbierta_alias(String abierta_alias) {
        this.abierta_alias = abierta_alias;
    }

    public String getAbierta_comentario() {
        return abierta_comentario;
    }

    public void setAbierta_comentario(String abierta_comentario) {
        this.abierta_comentario = abierta_comentario;
    }

    public String getAbierta_dd() {
        return abierta_dd;
    }

    public void setAbierta_dd(String abierta_dd) {
        this.abierta_dd = abierta_dd;
    }

    public String getAbierta_mm() {
        return abierta_mm;
    }

    public void setAbierta_mm(String abierta_mm) {
        this.abierta_mm = abierta_mm;
    }

    public String getAbierta_yyyy() {
        return abierta_yyyy;
    }

    public void setAbierta_yyyy(String abierta_yyyy) {
        this.abierta_yyyy = abierta_yyyy;
    }

    public String getCerrada_usuario() {
        return cerrada_usuario;
    }

    public void setCerrada_usuario(String cerrada_usuario) {
        this.cerrada_usuario = cerrada_usuario;
    }

    public Boolean getCerrada_active() {
        return cerrada_active;
    }

    public void setCerrada_active(Boolean cerrada_active) {
        this.cerrada_active = cerrada_active;
    }

    public String getCerrada_alias() {
        return cerrada_alias;
    }

    public void setCerrada_alias(String cerrada_alias) {
        this.cerrada_alias = cerrada_alias;
    }

    public String getCerrada_comentario() {
        return cerrada_comentario;
    }

    public void setCerrada_comentario(String cerrada_comentario) {
        this.cerrada_comentario = cerrada_comentario;
    }

    public String getCerrada_dd() {
        return cerrada_dd;
    }

    public void setCerrada_dd(String cerrada_dd) {
        this.cerrada_dd = cerrada_dd;
    }

    public String getCerrada_mm() {
        return cerrada_mm;
    }

    public void setCerrada_mm(String cerrada_mm) {
        this.cerrada_mm = cerrada_mm;
    }

    public String getCerrada_yyyy() {
        return cerrada_yyyy;
    }

    public void setCerrada_yyyy(String cerrada_yyyy) {
        this.cerrada_yyyy = cerrada_yyyy;
    }

    public String getPendiente_usuario() {
        return pendiente_usuario;
    }

    public void setPendiente_usuario(String pendiente_usuario) {
        this.pendiente_usuario = pendiente_usuario;
    }

    public Boolean getPendiente_active() {
        return pendiente_active;
    }

    public void setPendiente_active(Boolean pendiente_active) {
        this.pendiente_active = pendiente_active;
    }

    public String getPendiente_alias() {
        return pendiente_alias;
    }

    public void setPendiente_alias(String pendiente_alias) {
        this.pendiente_alias = pendiente_alias;
    }

    public String getPendiente_comentario() {
        return pendiente_comentario;
    }

    public void setPendiente_comentario(String pendiente_comentario) {
        this.pendiente_comentario = pendiente_comentario;
    }

    public String getPendiente_dd() {
        return pendiente_dd;
    }

    public void setPendiente_dd(String pendiente_dd) {
        this.pendiente_dd = pendiente_dd;
    }

    public String getPendiente_mm() {
        return pendiente_mm;
    }

    public void setPendiente_mm(String pendiente_mm) {
        this.pendiente_mm = pendiente_mm;
    }

    public String getPendiente_yyyy() {
        return pendiente_yyyy;
    }

    public void setPendiente_yyyy(String pendiente_yyyy) {
        this.pendiente_yyyy = pendiente_yyyy;
    }

    public String getCancelada_usuario() {
        return cancelada_usuario;
    }

    public void setCancelada_usuario(String cancelada_usuario) {
        this.cancelada_usuario = cancelada_usuario;
    }

    public Boolean getCancelada_active() {
        return cancelada_active;
    }

    public void setCancelada_active(Boolean cancelada_active) {
        this.cancelada_active = cancelada_active;
    }

    public String getCancelada_alias() {
        return cancelada_alias;
    }

    public void setCancelada_alias(String cancelada_alias) {
        this.cancelada_alias = cancelada_alias;
    }

    public String getCancelada_comentario() {
        return cancelada_comentario;
    }

    public void setCancelada_comentario(String cancelada_comentario) {
        this.cancelada_comentario = cancelada_comentario;
    }

    public String getCancelada_dd() {
        return cancelada_dd;
    }

    public void setCancelada_dd(String cancelada_dd) {
        this.cancelada_dd = cancelada_dd;
    }

    public String getCancelada_mm() {
        return cancelada_mm;
    }

    public void setCancelada_mm(String cancelada_mm) {
        this.cancelada_mm = cancelada_mm;
    }

    public String getCancelada_yyyy() {
        return cancelada_yyyy;
    }

    public void setCancelada_yyyy(String cancelada_yyyy) {
        this.cancelada_yyyy = cancelada_yyyy;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.codigo);
        hash = 53 * hash + Objects.hashCode(this.barcode);
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
        final BiOrden other = (BiOrden) obj;
        if (!Objects.equals(this.barcode, other.barcode)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BiOrden{" + "codigo=" + codigo + ", barcode=" + barcode + ", numeroOrden=" + numeroOrden + ", numeroTicket=" + numeroTicket + ", cliente=" + cliente + ", cedula=" + cedula + ", provincia=" + provincia + ", canton=" + canton + ", parroquia=" + parroquia + ", direccion=" + direccion + ", referencia=" + referencia + ", telefono=" + telefono + ", movil=" + movil + ", fechaVisitaCliente=" + fechaVisitaCliente + ", fechaEntregaProducto=" + fechaEntregaProducto + ", fechaLlegadaCliente=" + fechaLlegadaCliente + ", fechaSalidaCliente=" + fechaSalidaCliente + ", lugarAtencion=" + lugarAtencion + ", posibleFalla=" + posibleFalla + ", observacionCliente=" + observacionCliente + ", artefacto=" + artefacto + ", marca=" + marca + ", modelo=" + modelo + ", serie=" + serie + ", pnc=" + pnc + ", placa=" + placa + ", warranty=" + warranty + ", stock=" + stock + ", almacen=" + almacen + ", telefonoAlmacen=" + telefonoAlmacen + ", numeroFactura=" + numeroFactura + ", fechaFactura=" + fechaFactura + ", tecnico_codigo=" + tecnico_codigo + ", tecnico_cargo=" + tecnico_cargo + ", tecnico=" + tecnico + ", usuario_codigo=" + usuario_codigo + ", usuario=" + usuario + ", kmRecorridos=" + kmRecorridos + ", valorPorKmRecorrido=" + valorPorKmRecorrido + ", observacionRuta=" + observacionRuta + ", desperfecto=" + desperfecto + ", trabajoRealizado=" + trabajoRealizado + ", observaciones=" + observaciones + ", subTotalRepuestos=" + subTotalRepuestos + ", subTotalServicios=" + subTotalServicios + ", subTotalKilometraje=" + subTotalKilometraje + ", abierta_usuario=" + abierta_usuario + ", abierta_active=" + abierta_active + ", abierta_alias=" + abierta_alias + ", abierta_comentario=" + abierta_comentario + ", abierta_dd=" + abierta_dd + ", abierta_mm=" + abierta_mm + ", abierta_yyyy=" + abierta_yyyy + ", cerrada_usuario=" + cerrada_usuario + ", cerrada_active=" + cerrada_active + ", cerrada_alias=" + cerrada_alias + ", cerrada_comentario=" + cerrada_comentario + ", cerrada_dd=" + cerrada_dd + ", cerrada_mm=" + cerrada_mm + ", cerrada_yyyy=" + cerrada_yyyy + ", pendiente_usuario=" + pendiente_usuario + ", pendiente_active=" + pendiente_active + ", pendiente_alias=" + pendiente_alias + ", pendiente_comentario=" + pendiente_comentario + ", pendiente_dd=" + pendiente_dd + ", pendiente_mm=" + pendiente_mm + ", pendiente_yyyy=" + pendiente_yyyy + ", cancelada_usuario=" + cancelada_usuario + ", cancelada_active=" + cancelada_active + ", cancelada_alias=" + cancelada_alias + ", cancelada_comentario=" + cancelada_comentario + ", cancelada_dd=" + cancelada_dd + ", cancelada_mm=" + cancelada_mm + ", cancelada_yyyy=" + cancelada_yyyy + ", flag=" + flag + '}';
    }

}
