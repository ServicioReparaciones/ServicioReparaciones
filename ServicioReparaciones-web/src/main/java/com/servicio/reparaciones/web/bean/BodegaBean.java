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
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
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

    private Usuario selectedResponsable;
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
        this.selectedResponsable = new Usuario();
        this.selected = null;
        this.bodegas = this.bodegaService.ObtenerListaBodegas(1);
        this.provincias = this.provinciaService.ObtenerListaProvincias();
        this.cantones = new ArrayList<>();
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        Usuario responsable = this.usarioService.findByCodigo(this.nuevo.getResponsable());
        this.nuevo.setResponsable(responsable);
        this.nuevo.setUsername(this.usuario);
        Boolean exito = this.bodegaService.insert(this.nuevo);
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
            Usuario responsable = this.usarioService.findByCodigo(this.selectedResponsable);
            this.selected.setResponsable(responsable);
            this.selected.setUsername(this.usuario);
            Boolean exito = this.bodegaService.update(this.selected);
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
            Boolean exito = this.bodegaService.deleteFlag(this.selected);
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

    public void loadCantones() {
        if (this.nuevo.getProvincia() != null && !this.nuevo.getProvincia().equals("")) {
            this.cantones = this.provinciaService.ObtenerProvincia(this.nuevo.getProvincia()).getCantonList();
        }
    }
    
     public void selectedloadCantones() {
        if (this.selected.getProvincia() != null && !this.selected.getProvincia().equals("")) {
            this.cantones = this.provinciaService.ObtenerProvincia(this.selected.getProvincia()).getCantonList();
        }
    }

    public void loadModifyCantones(String provincia) {
        if (provincia != null && !provincia.equals("")) {
            this.cantones = this.provinciaService.ObtenerProvincia(provincia).getCantonList();
        }
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

    public Usuario getSelectedResponsable() {
        return selectedResponsable;
    }

    public void setSelectedResponsable(Usuario selectedResponsable) {
        this.selectedResponsable = selectedResponsable;
    }

}
