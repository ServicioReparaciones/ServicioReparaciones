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
import com.servicio.reparaciones.servicio.InventarioServicio;
import com.servicio.reparaciones.servicio.SalidaService;
import com.servicio.reparaciones.servicio.TecnicoServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
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
    private Inventario inventario;

    private Usuario usuario;

    @Inject
    private ArticuloService articuloService;
    @Inject
    private BodegaService bodegaService;
    @Inject
    private SalidaService salidaService;
    @Inject
    private InventarioServicio inventarioService;
    @Inject
    private TecnicoServicio tecnicoService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Salida();
        this.selected = new Salida();
        this.inventario = new Inventario();
        this.filtered = null;
        this.salidas = this.salidaService.ObtenerListaSalidas(1);
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
        this.nuevo.setPrecioUnit(this.inventario.getPrecioUnit());
        this.nuevo.setCode("SROUT" + (this.salidaService.generatedCodigo() - 1));
        Boolean exito = this.salidaService.insert(this.nuevo);
        Inventario salida = new Inventario();
        salida.setSigno(this.nuevo.getSigno());
        salida.setCodigoMovimiento(this.nuevo.getCode());
        if (exito) {
            salida.getMovimiento().setSalida(this.nuevo);
            salida.setArticulo(this.nuevo.getArticulo());
            salida.setBodega(this.nuevo.getBodega());
            salida.setCantidad(this.nuevo.getCantidad());
            salida.setUsername(this.nuevo.getUsername());
            if (this.inventarioService.ObtenerListaInventarioBodega(bodega, articulo).isEmpty()) {
                salida.setPrecioUnit(this.nuevo.getPrecioUnit());
                salida.setPrecioTotal(this.nuevo.getPrecioTotal());
                salida.getMovimiento().setEntrada(null);
                this.inventarioService.insert(salida);
            } else {
                this.inventarioService.promediosPonderados(salida);
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
            Tecnico tecnico = this.tecnicoService.findByCodigo(this.selected.getQuienRecibe());
            this.selected.setQuienRecibe(tecnico);
            Articulo articulo = articuloService.findByCode(this.selected.getArticulo());
            this.selected.setArticulo(articulo);
            Bodega bodega = this.bodegaService.findByCodigo(this.selected.getBodega());
            this.selected.setBodega(bodega);
            Boolean exito = this.salidaService.update(this.selected);
            Inventario salida = new Inventario();
            salida.setSigno(this.selected.getSigno());
            salida.setCodigoMovimiento(this.selected.getCode());
            if (exito) {
                salida.getMovimiento().setSalida(this.selected);
                salida.setArticulo(this.selected.getArticulo());
                salida.setBodega(this.selected.getBodega());
                salida.setCantidad(this.selected.getCantidad());
                salida.setUsername(this.selected.getUsername());
                salida.setPrecioUnit(this.selected.getPrecioUnit());
                salida.setPrecioTotal(this.selected.getPrecioTotal());
                this.inventarioService.updatePromediosPonderados(salida);
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
            Boolean exito = this.salidaService.deleteFlag(this.selected);
            Inventario salida = new Inventario();
            salida.setSigno(this.selected.getSigno());
            salida.setCodigoMovimiento(this.selected.getCode());
            if (exito) {
                salida.getMovimiento().setSalida(this.selected);
                salida.setArticulo(this.selected.getArticulo());
                salida.setBodega(this.selected.getBodega());
                salida.setCantidad(this.selected.getCantidad());
                salida.setUsername(this.selected.getUsername());
                salida.setPrecioUnit(this.selected.getPrecioUnit());
                salida.setPrecioTotal(this.selected.getPrecioTotal());
                this.inventarioService.updateDeletePromediosPonderados(salida);
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
            Bodega bodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
            List<Inventario> kardex = this.inventarioService.ObtenerListaInventarioBodega(bodega, art);
            if (kardex != null && !kardex.isEmpty()) {
                int index = kardex.size() - 1;
                this.inventario.setCantidad(kardex.get(index).getCantidad());
                this.inventario.setPrecioUnit(kardex.get(index).getPrecioUnit());
            }
        }
    }

    public void onRowSelectModify(SelectEvent event) {
        Articulo art = (Articulo) event.getObject();
        if (art != null && this.selected != null) {
            this.selected.setArticulo(art);
        }
    }

    public void loadBodega() {
        Bodega bodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
        List<Inventario> inventarioBodega = this.inventarioService.ObtenerListaInventarioBodega(bodega);
        if (inventarioBodega != null && !inventarioBodega.isEmpty()) {
            for (Inventario i : inventarioBodega) {
                this.articulos.add(i.getArticulo());
            }
            HashSet<Articulo> hs = new HashSet<Articulo>();
            hs.addAll(this.articulos);
            this.articulos.clear();
            this.articulos.addAll(hs);
        } else {
            this.articulos = new ArrayList<>();
        }
    }

    public void loadSelectBodega() {
        Bodega bodega = this.bodegaService.findByCodigo(this.selected.getBodega());
        List<Inventario> inventarioBodega = this.inventarioService.ObtenerListaInventarioBodega(bodega);
        if (inventarioBodega != null && !inventarioBodega.isEmpty()) {
            for (Inventario i : inventarioBodega) {
                this.articulos.add(i.getArticulo());
            }
            HashSet<Articulo> hs = new HashSet<Articulo>();
            hs.addAll(this.articulos);
            this.articulos.clear();
            this.articulos.addAll(hs);

        } else {
            this.articulos = new ArrayList<>();

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

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

}
