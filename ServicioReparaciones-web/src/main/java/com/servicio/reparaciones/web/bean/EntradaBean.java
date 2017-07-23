/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Articulo;
import com.servicio.reparaciones.modelo.nosql.Bodega;
import com.servicio.reparaciones.modelo.nosql.Entrada;
import com.servicio.reparaciones.modelo.nosql.Inventario;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.servicio.ArticuloService;
import com.servicio.reparaciones.servicio.BodegaService;
import com.servicio.reparaciones.servicio.EntradaService;
import com.servicio.reparaciones.servicio.InventarioServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
import java.io.Serializable;
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
@Named(value = "entradaBean")
@ViewScoped
public class EntradaBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 5300101900815373900L;

    private Entrada nuevo;
    private Entrada selected;
    private List<Entrada> entradas;
    private List<Entrada> filtered;

    private Usuario usuario;

    @Inject
    private ArticuloService articuloService;
    @Inject
    private BodegaService bodegaService;
    @Inject
    private InventarioServicio inventarioService;
    @Inject
    private EntradaService entradaService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Entrada();
        this.selected = new Entrada();
        this.filtered = null;
        this.entradas = this.entradaService.ObtenerListaEntradas(1);
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        Articulo articulo = articuloService.findByCode(this.nuevo.getArticulo());
        this.nuevo.setArticulo(articulo);
        Bodega bodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
        this.nuevo.setBodega(bodega);
        Boolean exito = this.entradaService.insert(this.nuevo);
        Inventario entrada = new Inventario();
        entrada.setSigno(this.nuevo.getSigno());
        entrada.setCodigoMovimiento(this.entradaService.generatedCodigo());
        entrada.getMovimiento().setEntrada(this.nuevo);
        if (exito) {
            entrada.setArticulo(this.nuevo.getArticulo());
            entrada.setBodega(this.nuevo.getBodega());
            entrada.setCantidad(this.nuevo.getCantidad());
            entrada.setUsername(this.nuevo.getUsername());
            if (this.inventarioService.ObtenerListaInventarios(1).isEmpty()) {
                entrada.setPrecioUnit(this.nuevo.getPrecioUnit());
                entrada.setPrecioTotal(this.nuevo.getPrecioTotal());
                entrada.getMovimiento().setSalida(null);
                this.inventarioService.insert(entrada);
            } else {
                this.inventarioService.promediosPonderados(entrada);
            }
            FacesUtil.addMessageInfo("Se ha guardado con exito.");
            this.init();
        } else {
            FacesUtil.addMessageError(null, "No se ha guardado.");
            this.init();
        }
    }

    @Override
    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            this.usuario = this.usarioService.findByCodigo(this.usuario);
            this.selected.setUsername(this.usuario);
            Articulo articulo = articuloService.findByCode(this.selected.getArticulo());
            this.selected.setArticulo(articulo);
            Bodega bodega = this.bodegaService.findByCodigo(this.selected.getBodega());
            this.selected.setBodega(bodega);
            Boolean exito = this.entradaService.update(this.selected);
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
            Boolean exito = this.entradaService.deleteFlag(this.selected);
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
        Articulo art = (Articulo) event.getObject();
        if (art != null) {
            this.nuevo.setArticulo(art);
        }
    }

    public void onRowSelectModify(SelectEvent event) {
        Articulo art = (Articulo) event.getObject();
        if (art != null && this.selected != null) {
            this.selected.setArticulo(art);
        }
    }

    public Entrada getNuevo() {
        return nuevo;
    }

    public void setNuevo(Entrada nuevo) {
        this.nuevo = nuevo;
    }

    public Entrada getSelected() {
        return selected;
    }

    public void setSelected(Entrada selected) {
        this.selected = selected;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public List<Entrada> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<Entrada> filtered) {
        this.filtered = filtered;
    }

}
