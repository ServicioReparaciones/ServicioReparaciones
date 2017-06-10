/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.nosql.Visita;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.servicio.VisitaServicio;
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
@Named(value = "visitaBean")
@ViewScoped
public class VisitaBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 4368370393804958282L;

    private Visita nuevo;
    private Visita selected;
    private List<Visita> Visitas;
    private List<Visita> filterVisitas;

    private Usuario usuario;

    @Inject
    private VisitaServicio visitaService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Visita();
        this.nuevo.setUnique(this.visitaService.generatedUnique());
        this.selected = null;
        this.Visitas = this.visitaService.ObtenerListaVisitas(1);
        this.filterVisitas = null;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        Boolean exito = this.visitaService.insert(this.nuevo);
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
            Boolean exito = this.visitaService.update(this.selected);
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
            Boolean exito = this.visitaService.deleteFlag(this.selected);
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

    public Visita getNuevo() {
        return nuevo;
    }

    public void setNuevo(Visita nuevo) {
        this.nuevo = nuevo;
    }

    public Visita getSelected() {
        return selected;
    }

    public void setSelected(Visita selected) {
        this.selected = selected;
    }

    public List<Visita> getVisitas() {
        return Visitas;
    }

    public void setVisitas(List<Visita> Visitas) {
        this.Visitas = Visitas;
    }

    public List<Visita> getFilterVisitas() {
        return filterVisitas;
    }

    public void setFilterVisitas(List<Visita> filterVisitas) {
        this.filterVisitas = filterVisitas;
    }

}
