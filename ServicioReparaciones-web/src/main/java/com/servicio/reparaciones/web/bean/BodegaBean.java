/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Bodega;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.sql.Canton;
import com.servicio.reparaciones.modelo.sql.Provincia;
import com.servicio.reparaciones.servicio.BodegaService;
import com.servicio.reparaciones.servicio.CantonServicio;
import com.servicio.reparaciones.servicio.ProvinciaServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.util.SessionUtil;
import com.sun.imageio.plugins.common.BogusColorSpace;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "bodegaBean")
@ViewScoped
public class BodegaBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 7189990853296772760L;

    private Bodega nuevo;
    private Bodega selected;
    private List<Bodega> bodegas;
    private List<Provincia> provincias;
    private List<Canton> cantones;

    private Usuario usuario;
    
    @Inject
    private BodegaService bodegaService;
    @Inject
    private ProvinciaServicio provinciaService;
    @Inject
    private CantonServicio cantonService;
    @Inject
    private UsuarioServicio usarioService;
    
    @PostConstruct
    public void init() {
        this.nuevo = new Bodega();
        this.selected = null;
        this.bodegas = this.bodegaService.ObtenerListaBodegas(1);
        this.provincias = this.provinciaService.ObtenerListaProvincias();
        this.cantones = new ArrayList<>();
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modify(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Bodega getNuevo() {
        return nuevo;
    }

    public void setNuevo(Bodega nuevo) {
        this.nuevo = nuevo;
    }

    public Bodega getSelected() {
        return selected;
    }

    public void setSelected(Bodega selected) {
        this.selected = selected;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public List<Canton> getCantones() {
        return cantones;
    }

    public void setCantones(List<Canton> cantones) {
        this.cantones = cantones;
    }

}
