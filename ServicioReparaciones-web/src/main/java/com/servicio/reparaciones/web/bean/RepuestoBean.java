/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Repuesto;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.sql.Marca;
import com.servicio.reparaciones.servicio.MarcaServicio;
import com.servicio.reparaciones.servicio.RepuestoServicio;
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
@Named(value = "repuestoBean")
@ViewScoped
public class RepuestoBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = -3314963063281209488L;

    private Repuesto nuevo;
    private Repuesto selected;
    private List<Repuesto> repuestos;
    private List<Repuesto> filterRepuestos;

    private List<Marca> marcas;

    private Usuario usuario;

    @Inject
    private RepuestoServicio repuestoService;
    @Inject
    private MarcaServicio marcaService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    private void init() {
        this.nuevo = new Repuesto();
        this.nuevo.setBarcode(this.repuestoService.generatedBarcode());
        this.selected = null;
        this.repuestos = this.repuestoService.ObtenerListaRepuestos(1);
        this.marcas = this.marcaService.ObtenerListaMarcas();
        this.filterRepuestos = null;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        Boolean exito = this.repuestoService.insert(this.nuevo);
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
            Boolean exito = this.repuestoService.update(this.selected);
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
            Boolean exito = this.repuestoService.deleteFlag(this.selected);
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

    public Repuesto getNuevo() {
        return nuevo;
    }

    public void setNuevo(Repuesto nuevo) {
        this.nuevo = nuevo;
    }

    public Repuesto getSelected() {
        return selected;
    }

    public void setSelected(Repuesto selected) {
        this.selected = selected;
    }

    public List<Repuesto> getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(List<Repuesto> repuestos) {
        this.repuestos = repuestos;
    }

    public List<Repuesto> getFilterRepuestos() {
        return filterRepuestos;
    }

    public void setFilterRepuestos(List<Repuesto> filterRepuestos) {
        this.filterRepuestos = filterRepuestos;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

}
