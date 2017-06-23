/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Cliente;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.sql.Canton;
import com.servicio.reparaciones.modelo.sql.Parroquia;
import com.servicio.reparaciones.modelo.sql.Provincia;
import com.servicio.reparaciones.servicio.CantonServicio;
import com.servicio.reparaciones.servicio.ClienteServicio;
import com.servicio.reparaciones.servicio.ProvinciaServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
@Named(value = "clienteBean")
@ViewScoped
public class ClienteBean implements ImethodsBean, Serializable {
    
    private static final long serialVersionUID = 4541654846232891662L;
    
    private Cliente nuevo;
    private Cliente selected;
    private List<Cliente> clientes;
    private List<Cliente> filterClientes;
    
    private List<Provincia> provincias;
    private List<Canton> cantones;
    private List<Parroquia> paroquias;
    
    private Usuario usuario;
    
    @Inject
    private ClienteServicio clienteService;
    @Inject
    private ProvinciaServicio provinciaService;
    @Inject
    private CantonServicio cantonService;
    @Inject
    private UsuarioServicio usarioService;
    
    @PostConstruct
    public void init() {
        this.nuevo = new Cliente();
        this.selected = null;
        this.clientes = this.clienteService.ObtenerListaClientes(1);
        Collections.reverse(this.clientes);
        this.filterClientes = null;
        this.provincias = this.provinciaService.ObtenerListaProvincias();
        this.cantones = new ArrayList<>();
        this.paroquias = new ArrayList<>();
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }
    
    @Override
    public void add(ActionEvent evt) {
        this.nuevo.setMovil(this.nuevo.getMovil().replace("-", ""));
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        Boolean exito = this.clienteService.insert(this.nuevo);
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
            this.selected.setMovil(this.selected.getMovil().replace("-", ""));
            this.usuario = this.usarioService.findByCodigo(this.usuario);
            this.selected.setUsername(this.usuario);
            Boolean exito = this.clienteService.update(this.selected);
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
            Boolean exito = this.clienteService.deleteFlag(this.selected);
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
            this.paroquias = new ArrayList<>();
        }
    }
    
    public void loadParroquias() {
        if (this.nuevo.getCanton() != null && !this.nuevo.getCanton().equals("")) {
            this.paroquias = this.cantonService.ObtenerCanton(this.nuevo.getCanton()).getParroquiaList();
        }
    }
    
    public void loadModifyCantones(String provincia) {
        if (provincia != null && !provincia.equals("")) {
            this.cantones = this.provinciaService.ObtenerProvincia(provincia).getCantonList();
            this.paroquias = new ArrayList<>();
        }
    }
    
    public void loadModifyParroquias(String canton) {
        if (canton != null && !canton.equals("")) {
            this.paroquias = this.cantonService.ObtenerCanton(canton).getParroquiaList();
        }
    }
    
    public void loadModifyCantones() {
        if (this.selected.getProvincia() != null && !this.selected.getProvincia().equals("")) {
            this.cantones = this.provinciaService.ObtenerProvincia(this.selected.getProvincia()).getCantonList();
            this.paroquias = new ArrayList<>();
        }
    }
    
    public void loadModifyParroquias() {
        if (this.selected.getCanton() != null && !this.selected.getCanton().equals("")) {
            this.paroquias = this.cantonService.ObtenerCanton(this.selected.getCanton()).getParroquiaList();
        }
    }
    
    public void onRowSelect(SelectEvent event) {
        this.selected = (Cliente) event.getObject();
        if (this.selected != null) {
            this.setNuevo(this.selected);
        }
    }
    
    public Cliente getNuevo() {
        return nuevo;
    }
    
    public void setNuevo(Cliente nuevo) {
        this.nuevo = nuevo;
    }
    
    public Cliente getSelected() {
        return selected;
    }
    
    public void setSelected(Cliente selected) {
        this.selected = selected;
    }
    
    public List<Cliente> getClientes() {
        return clientes;
    }
    
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public List<Cliente> getFilterClientes() {
        return filterClientes;
    }
    
    public void setFilterClientes(List<Cliente> filterClientes) {
        this.filterClientes = filterClientes;
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
    
    public List<Parroquia> getParoquias() {
        return paroquias;
    }
    
    public void setParoquias(List<Parroquia> paroquias) {
        this.paroquias = paroquias;
    }
    
}
