/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.ItemRepuesto;
import com.servicio.reparaciones.modelo.nosql.ItemServicio;
import com.servicio.reparaciones.modelo.nosql.Repuesto;
import com.servicio.reparaciones.servicio.RepuestoServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.util.FacesUtil;
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
@Named(value = "itemRepuestoBean")
@ViewScoped
public class ItemRepuestoBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 2716532692607176408L;

    private ItemRepuesto nuevo;
    private ItemRepuesto selected;
    private List<ItemRepuesto> items;

    @Inject
    private RepuestoServicio respuestoService;

    @PostConstruct
    public void init() {
        this.nuevo = new ItemRepuesto();
        this.selected = null;
        this.items = new ArrayList<>();
    }

    @Override
    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.nuevo.getCantidad() != 0
                && this.nuevo.getRepuesto().getCodigo() != null) {
            Integer index = this.items.size() + 1;
            Repuesto repuesto = this.respuestoService.findByCodigo(this.nuevo.getRepuesto());
            this.nuevo.setRepuesto(repuesto);
            this.nuevo.setIndex(index);
            Boolean exito = this.items.add(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
            }
        }
    }

    @Override
    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            ItemRepuesto temp = this.selected;
            Boolean exito = this.items.remove(temp);
            if (exito) {
                exito = this.items.add(this.selected);
                if (exito) {
                    FacesUtil.addMessageInfo("Se ha modificado.");
                }
            }
        }
    }

    @Override
    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            ItemRepuesto temp = this.selected;
            Boolean exito = this.items.remove(temp);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
            }
        }
    }

    public Double calcularSubTotal() {
        Double subTotal = 0d;
        if (this.items != null && !this.items.isEmpty()) {
            for (ItemRepuesto e : this.items) {
                subTotal = subTotal + e.getSubTotal();
            }
        }
        return subTotal;
    }

    public ItemRepuesto getNuevo() {
        return nuevo;
    }

    public void setNuevo(ItemRepuesto nuevo) {
        this.nuevo = nuevo;
    }

    public ItemRepuesto getSelected() {
        return selected;
    }

    public void setSelected(ItemRepuesto selected) {
        this.selected = selected;
    }

    public List<ItemRepuesto> getItems() {
        return items;
    }

    public void setItems(List<ItemRepuesto> items) {
        this.items = items;
    }

}
