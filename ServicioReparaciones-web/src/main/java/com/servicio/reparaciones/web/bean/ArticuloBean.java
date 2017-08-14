/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Articulo;
import com.servicio.reparaciones.modelo.nosql.Bodega;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.sql.Marca;
import com.servicio.reparaciones.servicio.ArticuloService;
import com.servicio.reparaciones.servicio.MarcaServicio;
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

/**
 *
 * @author luis
 */
@Named(value = "articuloBean")
@ViewScoped
public class ArticuloBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 3332933907637335844L;

    private Articulo nuevo;
    private Articulo selected;
    private List<Articulo> articulos;
    private List<Articulo> filtered;

    private List<Marca> marcas;
    private Bodega selectedBodega;
    private Usuario usuario;

    @Inject
    private ArticuloService articuloService;
    @Inject
    private MarcaServicio marcaService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Articulo();
        this.nuevo.setBarcode(this.articuloService.generatedBarcode());
        this.selectedBodega = new Bodega();
        this.selected = null;
        this.articulos = this.articuloService.ObtenerListaArticulos(1);
        this.marcas = this.marcaService.ObtenerListaMarcas();
        this.filtered = null;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        Boolean exito = this.articuloService.insert(this.nuevo);
        if (exito) {
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
            Boolean exito = this.articuloService.update(this.selected);
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
            Boolean exito = this.articuloService.deleteFlag(this.selected);
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

    public Articulo getNuevo() {
        return nuevo;
    }

    public void setNuevo(Articulo nuevo) {
        this.nuevo = nuevo;
    }

    public Articulo getSelected() {
        return selected;
    }

    public void setSelected(Articulo selected) {
        this.selected = selected;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public List<Articulo> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<Articulo> filtered) {
        this.filtered = filtered;
    }

    public Bodega getSelectedBodega() {
        return selectedBodega;
    }

    public void setSelectedBodega(Bodega selectedBodega) {
        this.selectedBodega = selectedBodega;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

}
