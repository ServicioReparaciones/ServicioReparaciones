/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Falla;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.sql.Marca;
import com.servicio.reparaciones.servicio.FallaServicio;
import com.servicio.reparaciones.servicio.MarcaServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author luis
 */
@Named(value = "fallaBean")
@ViewScoped
public class FallaBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 7742862935038028200L;

    private Falla nuevo;
    private Falla selected;
    private List<Falla> fallas;
    private List<Falla> filterFallas;

    private List<Marca> marcas;

    private Usuario usuario;

    @Inject
    private FallaServicio fallaService;
    @Inject
    private MarcaServicio marcaService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Falla();
        this.selected = null;
        this.fallas = this.fallaService.ObtenerListaFallas(1);
        this.marcas = this.marcaService.ObtenerListaMarcas();
        this.filterFallas = null;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        Boolean exito = this.fallaService.insert(this.nuevo);
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
            Boolean exito = this.fallaService.update(this.selected);
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
            Boolean exito = this.fallaService.deleteFlag(this.selected);
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

    public Falla getNuevo() {
        return nuevo;
    }

    public void setNuevo(Falla nuevo) {
        this.nuevo = nuevo;
    }

    public Falla getSelected() {
        return selected;
    }

    public void setSelected(Falla selected) {
        this.selected = selected;
    }

    public List<Falla> getFallas() {
        return fallas;
    }

    public void setFallas(List<Falla> fallas) {
        this.fallas = fallas;
    }

    public List<Falla> getFilterFallas() {
        return filterFallas;
    }

    public void setFilterFallas(List<Falla> filterFallas) {
        this.filterFallas = filterFallas;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

}
