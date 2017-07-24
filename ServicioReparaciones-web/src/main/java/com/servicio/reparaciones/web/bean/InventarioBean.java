/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Articulo;
import com.servicio.reparaciones.modelo.nosql.Bodega;
import com.servicio.reparaciones.modelo.nosql.Inventario;
import com.servicio.reparaciones.servicio.ArticuloService;
import com.servicio.reparaciones.servicio.BodegaService;
import com.servicio.reparaciones.servicio.InventarioServicio;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "inventarioBean")
@ViewScoped
public class InventarioBean implements Serializable {

    private static final long serialVersionUID = -6383375925619416605L;

    private Articulo articulo;
    private Bodega bodega;
    private List<Inventario> invetarios;

    @Inject
    private ArticuloService articuloService;
    @Inject
    private BodegaService bodegaService;
    @Inject
    private InventarioServicio inventarioService;

    @PostConstruct
    public void init() {
        this.articulo = new Articulo();
        this.bodega = new Bodega();
        this.invetarios = new ArrayList<>();
    }

    public void findInventario(ActionEvent evt) {
        Articulo mArticulo = this.articuloService.findByCodigo(this.articulo);
        Bodega mBodega = this.bodegaService.findByCodigo(this.bodega);
        this.invetarios = this.inventarioService.ObtenerListaInventarioBodega(mBodega, mArticulo);
    }
    
    public void onRowSelect(SelectEvent event) {
        Articulo art = (Articulo) event.getObject();
        if (art != null) {
            this.setArticulo(art);
        }
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

    public List<Inventario> getInvetarios() {
        return invetarios;
    }

    public void setInvetarios(List<Inventario> invetarios) {
        this.invetarios = invetarios;
    }
}
