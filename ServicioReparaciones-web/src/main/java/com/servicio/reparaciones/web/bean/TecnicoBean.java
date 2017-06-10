/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Tecnico;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.sql.Provincia;
import com.servicio.reparaciones.servicio.ProvinciaServicio;
import com.servicio.reparaciones.servicio.TecnicoServicio;
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
@Named(value = "tecnicoBean")
@ViewScoped
public class TecnicoBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 3223729348434805959L;

    private Tecnico nuevo;
    private Tecnico selected;
    private List<Tecnico> tecnicos;
    private List<Tecnico> filterTecnicos;
    
    private List<Provincia> provincias;

    private Usuario usuario;

    @Inject
    private TecnicoServicio tecnicoService;
    @Inject
    private ProvinciaServicio provinciaService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Tecnico();
        this.selected = null;
        this.tecnicos = this.tecnicoService.ObtenerListaTecnicos(1);
        this.provincias = this.provinciaService.ObtenerListaProvincias();
        this.filterTecnicos = null;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        Boolean exito = this.tecnicoService.insert(this.nuevo);
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
            Boolean exito = this.tecnicoService.update(this.selected);
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
            Boolean exito = this.tecnicoService.deleteFlag(this.selected);
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

    public Tecnico getNuevo() {
        return nuevo;
    }

    public void setNuevo(Tecnico nuevo) {
        this.nuevo = nuevo;
    }

    public Tecnico getSelected() {
        return selected;
    }

    public void setSelected(Tecnico selected) {
        this.selected = selected;
    }

    public List<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(List<Tecnico> tecnicos) {
        this.tecnicos = tecnicos;
    }

    public List<Tecnico> getFilterTecnicos() {
        return filterTecnicos;
    }

    public void setFilterTecnicos(List<Tecnico> filterTecnicos) {
        this.filterTecnicos = filterTecnicos;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

}
