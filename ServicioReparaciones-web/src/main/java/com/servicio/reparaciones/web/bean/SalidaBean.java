/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Articulo;
import com.servicio.reparaciones.modelo.nosql.Bodega;
import com.servicio.reparaciones.modelo.nosql.Inventario;
import com.servicio.reparaciones.modelo.nosql.Salida;
import com.servicio.reparaciones.modelo.nosql.Tecnico;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.servicio.ArticuloService;
import com.servicio.reparaciones.servicio.BodegaService;
import com.servicio.reparaciones.servicio.InventarioService;
import com.servicio.reparaciones.servicio.SalidaService;
import com.servicio.reparaciones.servicio.TecnicoServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
@Named(value = "salidaBean")
@ViewScoped
public class SalidaBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = -1702663857877640897L;

    private Salida nuevo;
    private Salida selected;
    private List<Salida> salidas;
    private List<Salida> filtered;
    private List<Articulo> articulos;
    private List<Articulo> filteredArticulos;
    private Double cantidadExistente;

    private Usuario usuario;

    @Inject
    private ArticuloService articuloService;
    @Inject
    private BodegaService bodegaService;
    @Inject
    private SalidaService salidaService;
    @Inject
    private InventarioService inventarioService;
    @Inject
    private TecnicoServicio tecnicoService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Salida();
        this.selected = new Salida();
        this.cantidadExistente = 0.0;
        this.filtered = null;
        this.salidas = this.salidaService.ObtenerListaSalidas(1);
        Collections.reverse(this.salidas);
        this.articulos = new ArrayList<>();
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        Tecnico tecnico = this.tecnicoService.findByCodigo(this.nuevo.getQuienRecibe());
        this.nuevo.setQuienRecibe(tecnico);
        Articulo articulo = articuloService.findByCode(this.nuevo.getArticulo());
        this.nuevo.setArticulo(articulo);
        Bodega bodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
        this.nuevo.setBodega(bodega);
        this.nuevo.setCode("SROUT" + (this.salidaService.generatedCodigo() - 1));
        if (this.nuevo.getBodega().getUsername().equals(this.nuevo.getUsername())) {
            this.cantidadExistente = this.inventarioService.
                    CalcularExistenciasBodegaArticulo(
                            this.nuevo.getBodega(), this.nuevo.getArticulo());
            if (this.cantidadExistente >= this.nuevo.getCantidad()) {
                Boolean exito = this.salidaService.insert(this.nuevo);
                if (exito) {
                    FacesUtil.addMessageInfo("Se ha guardado con exito.");
                    this.init();
                } else {
                    FacesUtil.addMessageError(null, "No se ha guardado.");
                    this.init();
                }
            } else {
                FacesUtil.addMessageInfo("No hay unidades disponibles en bodega.");
            }
        } else {
            FacesUtil.addMessageInfo("Usted no está autorizado");
        }
    }

    @Override
    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            this.usuario = this.usarioService.findByCodigo(this.usuario);
            this.selected.setUsername(this.usuario);
            Tecnico tecnico = this.tecnicoService.findByCodigo(this.selected.getQuienRecibe());
            this.selected.setQuienRecibe(tecnico);
            Articulo articulo = articuloService.findByCode(this.selected.getArticulo());
            this.selected.setArticulo(articulo);
            Bodega bodega = this.bodegaService.findByCodigo(this.selected.getBodega());
            this.selected.setBodega(bodega);
            if (this.nuevo.getBodega().getUsername().equals(this.nuevo.getUsername())) {
                this.cantidadExistente = this.inventarioService.
                        CalcularExistenciasBodegaArticulo(
                                this.nuevo.getBodega(), this.nuevo.getArticulo());
                if (this.cantidadExistente >= this.nuevo.getCantidad()) {
                    Boolean exito = this.salidaService.update(this.selected);
                    if (exito) {
                        FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                        this.init();
                    } else {
                        FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                        this.init();
                    }
                } else {
                    FacesUtil.addMessageInfo("No hay unidades disponibles en bodega.");
                }
            } else {
                FacesUtil.addMessageInfo("Usted no está autorizado.");
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
            if (this.nuevo.getBodega().getUsername().equals(this.nuevo.getUsername())) {
                Boolean exito = this.salidaService.deleteFlag(this.selected);
                if (exito) {
                    FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                    this.init();
                } else {
                    FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
                    this.init();
                }
            } else {
                FacesUtil.addMessageInfo("Usted no está autorizado.");
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void onRowSelect(SelectEvent event) {
        Articulo art = (Articulo) event.getObject();
        if (art != null) {
            this.nuevo.setArticulo(art);
            this.cantidadExistente = this.inventarioService.
                    CalcularExistenciasBodegaArticulo(
                            this.nuevo.getBodega(), this.nuevo.getArticulo());
        }
    }

    public void onRowSelectModify(SelectEvent event) {
        Articulo art = (Articulo) event.getObject();
        if (art != null && this.selected != null) {
            this.selected.setArticulo(art);
            this.cantidadExistente = this.inventarioService.
                    CalcularExistenciasBodegaArticulo(
                            this.selected.getBodega(), this.selected.getArticulo());
        }
    }

    public void loadArticulos() {
        Bodega bodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
        this.nuevo.setBodega(bodega);
        List<Inventario> narticulos = this.inventarioService.findByBodega(bodega);
        this.articulos = new ArrayList<>();
        if (narticulos != null && !narticulos.isEmpty()) {
            for (Inventario in : narticulos) {
                this.articulos.add(in.getArticulo());
            }
            HashSet<Articulo> hashSet = new HashSet<Articulo>(this.articulos);
            this.articulos.clear();
            this.articulos.addAll(hashSet);
        }
    }

    public void loadSelectArticulos() {
        Bodega bodega = this.bodegaService.findByCodigo(this.selected.getBodega());
        this.selected.setBodega(bodega);
        List<Inventario> narticulos = this.inventarioService.findByBodega(bodega);
        if (narticulos != null && !narticulos.isEmpty()) {
            this.articulos = new ArrayList<>();
            for (Inventario in : narticulos) {
                this.articulos.add(in.getArticulo());
            }
            HashSet<Articulo> hashSet = new HashSet<Articulo>(this.articulos);
            this.articulos.clear();
            this.articulos.addAll(hashSet);
            this.cantidadExistente = this.inventarioService.
                    CalcularExistenciasBodegaArticulo(
                            this.selected.getBodega(), this.selected.getArticulo());
        }
    }

    public void loadSelectArticulos(Salida selected) {
        this.selected = selected;
        Bodega bodega = this.bodegaService.findByCodigo(this.selected.getBodega());
        this.selected.setBodega(bodega);
        List<Inventario> narticulos = this.inventarioService.findByBodega(bodega);
        if (narticulos != null && !narticulos.isEmpty()) {
            this.articulos = new ArrayList<>();
            for (Inventario in : narticulos) {
                this.articulos.add(in.getArticulo());
            }
            HashSet<Articulo> hashSet = new HashSet<Articulo>(this.articulos);
            this.articulos.clear();
            this.articulos.addAll(hashSet);
            this.cantidadExistente = this.inventarioService.
                    CalcularExistenciasBodegaArticulo(
                            this.selected.getBodega(), this.selected.getArticulo());
        }
    }

    public Salida getNuevo() {
        return nuevo;
    }

    public void setNuevo(Salida nuevo) {
        this.nuevo = nuevo;
    }

    public Salida getSelected() {
        return selected;
    }

    public void setSelected(Salida selected) {
        this.selected = selected;
    }

    public List<Salida> getSalidas() {
        return salidas;
    }

    public void setSalidas(List<Salida> salidas) {
        this.salidas = salidas;
    }

    public List<Salida> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<Salida> filtered) {
        this.filtered = filtered;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public List<Articulo> getFilteredArticulos() {
        return filteredArticulos;
    }

    public void setFilteredArticulos(List<Articulo> filteredArticulos) {
        this.filteredArticulos = filteredArticulos;
    }

    public Double getCantidadExistente() {
        return cantidadExistente;
    }

    public void setCantidadExistente(Double cantidadExistente) {
        this.cantidadExistente = cantidadExistente;
    }

}
