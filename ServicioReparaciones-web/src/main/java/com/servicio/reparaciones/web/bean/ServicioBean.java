/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Servicio;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.servicio.ServicioServicio;
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
@Named(value = "servicioBean")
@ViewScoped
public class ServicioBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = -3672667383377005969L;

    private Servicio nuevo;
    private Servicio selected;
    private List<Servicio> servicios;
    private List<Servicio> filterServicios;

    private Usuario usuario;

    @Inject
    private ServicioServicio servicioService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Servicio();
        this.selected = null;
        this.servicios = this.servicioService.ObtenerListaServicios(1);
        this.filterServicios = null;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        Boolean exito = this.servicioService.insert(this.nuevo);
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
            Boolean exito = this.servicioService.update(this.selected);
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
            Boolean exito = this.servicioService.deleteFlag(this.selected);
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

    public Servicio getNuevo() {
        return nuevo;
    }

    public void setNuevo(Servicio nuevo) {
        this.nuevo = nuevo;
    }

    public Servicio getSelected() {
        return selected;
    }

    public void setSelected(Servicio selected) {
        this.selected = selected;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public List<Servicio> getFilterServicios() {
        return filterServicios;
    }

    public void setFilterServicios(List<Servicio> filterServicios) {
        this.filterServicios = filterServicios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
