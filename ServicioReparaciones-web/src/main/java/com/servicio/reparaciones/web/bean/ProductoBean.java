/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Cliente;
import com.servicio.reparaciones.modelo.nosql.Producto;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.sql.Almacen;
import com.servicio.reparaciones.modelo.sql.Artefacto;
import com.servicio.reparaciones.modelo.sql.Marca;
import com.servicio.reparaciones.servicio.AlmacenServicio;
import com.servicio.reparaciones.servicio.ArtefactoServicio;
import com.servicio.reparaciones.servicio.MarcaServicio;
import com.servicio.reparaciones.servicio.ProductoServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "productoBean")
@ViewScoped
public class ProductoBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 8212433807349133095L;

    private Producto nuevo;
    private Producto selected;
    private List<Producto> productos;
    private List<Producto> filterProductos;

    private Cliente cliente;
    private List<Almacen> almacenes;
    private List<Artefacto> artefactos;
    private List<Marca> marcas;
    private Boolean garantia;

    private Usuario usuario;

    @Inject
    private ProductoServicio productoService;
    @Inject
    private AlmacenServicio almacenService;
    @Inject
    private ArtefactoServicio artefactoService;
    @Inject
    private MarcaServicio marcaService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Producto();
        this.nuevo.setBarcode(this.productoService.generatedBarcode());
        this.selected = null;
        this.productos = this.productoService.ObtenerListaProductos(1);
        Collections.reverse(this.productos);
        this.almacenes = this.almacenService.ObtenerListaAlmacenes();
        this.artefactos = this.artefactoService.ObtenerListaArtefactos();
        this.marcas = this.marcaService.ObtenerListaMarcas();
        this.garantia = Boolean.FALSE;
        this.filterProductos = null;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    public void initCliente(Producto producto) {
        this.nuevo = new Producto();
        this.nuevo.setBarcode(this.productoService.generatedBarcode());
        this.selected = null;
        this.productos = this.productoService.findByCliente(producto);
        Collections.reverse(this.productos);
        this.almacenes = this.almacenService.ObtenerListaAlmacenes();
        this.artefactos = this.artefactoService.ObtenerListaArtefactos();
        this.marcas = this.marcaService.ObtenerListaMarcas();
        this.garantia = Boolean.FALSE;
        this.filterProductos = null;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    public void initProducto(ActionEvent evt) {
        this.nuevo = new Producto();
        this.nuevo.setBarcode(this.productoService.generatedBarcode());
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        this.nuevo.setCliente(this.cliente);
        Boolean exito = this.productoService.insert(this.nuevo);
        if (exito) {
            FacesUtil.addMessageInfo("Se ha guardado con exito.");
            this.init();
        } else {
            FacesUtil.addMessageError(null, "No se ha guardado.");
            this.init();
        }
    }

    public void addProductoWarranty(ActionEvent evt, Boolean warranty) {
        if (warranty != null) {
            this.usuario = this.usarioService.findByCodigo(this.usuario);
            this.nuevo.setUsername(this.usuario);
            this.nuevo.setCliente(this.cliente);
            this.nuevo.setWarranty(warranty);
            Boolean exito = this.productoService.insert(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha guardado con exito.");
                this.initCliente(this.nuevo);
            } else {
                FacesUtil.addMessageError(null, "No se ha guardado.");
                this.initCliente(this.nuevo);
            }
        }
    }

    @Override
    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            this.usuario = this.usarioService.findByCodigo(this.usuario);
            this.selected.setUsername(this.usuario);
            Boolean exito = this.productoService.update(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    @Override
    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            this.usuario = this.usarioService.findByCodigo(this.usuario);
            this.selected.setUsername(this.usuario);
            Boolean exito = this.productoService.deleteFlag(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (Producto) event.getObject();
        if (this.selected != null) {
            this.setNuevo(this.selected);
        }
    }

    public void loadListByCliente(Cliente cliente) {
        Producto producto = new Producto();
        producto.setCliente(cliente);
        this.productos = this.productoService.findByCliente(producto);
        Collections.reverse(this.productos);
    }

    public void startWarranty() {
        this.nuevo.setWarranty(Boolean.TRUE);
    }

    public void loadGarantia() {

    }

    public Producto getNuevo() {
        return nuevo;
    }

    public void setNuevo(Producto nuevo) {
        this.nuevo = nuevo;
    }

    public Producto getSelected() {
        return selected;
    }

    public void setSelected(Producto selected) {
        this.selected = selected;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Producto> getFilterProductos() {
        return filterProductos;
    }

    public void setFilterProductos(List<Producto> filterProductos) {
        this.filterProductos = filterProductos;
    }

    public List<Artefacto> getArtefactos() {
        return artefactos;
    }

    public void setArtefactos(List<Artefacto> artefactos) {
        this.artefactos = artefactos;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public Boolean getGarantia() {
        return garantia;
    }

    public void setGarantia(Boolean garantia) {
        this.garantia = garantia;
    }

    public List<Almacen> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(List<Almacen> almacenes) {
        this.almacenes = almacenes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
