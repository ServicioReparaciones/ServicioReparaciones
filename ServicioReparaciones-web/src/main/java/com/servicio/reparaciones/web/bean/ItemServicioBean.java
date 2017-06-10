/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.ItemServicio;
import com.servicio.reparaciones.modelo.nosql.Servicio;
import com.servicio.reparaciones.servicio.ServicioServicio;
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
@Named(value = "itemServicioBean")
@ViewScoped
public class ItemServicioBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 5759033909616232570L;

    private ItemServicio nuevo;
    private ItemServicio selected;
    private List<ItemServicio> items;

    @Inject
    private ServicioServicio serviService;

    @PostConstruct
    public void init() {
        this.nuevo = new ItemServicio();
        this.selected = null;
        this.items = new ArrayList<>();
    }

    @Override
    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.nuevo.getCantidad() != 0
                && this.nuevo.getServicio().getCodigo() != null) {
            Integer index = this.items.size() + 1;
            Servicio sercicio = this.serviService.findByCodigo(this.nuevo.getServicio());
            this.nuevo.setIndex(index);
            this.nuevo.setServicio(sercicio);
            Boolean exito = this.items.add(this.nuevo);
            if (exito) {
                this.nuevo = new ItemServicio();
                FacesUtil.addMessageInfo("Se ha agregado.");
            }
        }
    }

    @Override
    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            ItemServicio temp = this.selected;
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
            ItemServicio temp = this.selected;
            Boolean exito = this.items.remove(temp);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
            }
        }
    }

    public Double calcularSubTotal() {
        Double subTotal = 0d;
        if (this.items != null && !this.items.isEmpty()) {
            for (ItemServicio e : this.items) {
                subTotal = subTotal + e.getSubTotal();
            }
        }
        return subTotal;
    }

    public ItemServicio getNuevo() {
        return nuevo;
    }

    public void setNuevo(ItemServicio nuevo) {
        this.nuevo = nuevo;
    }

    public ItemServicio getSelected() {
        return selected;
    }

    public void setSelected(ItemServicio selected) {
        this.selected = selected;
    }

    public List<ItemServicio> getItems() {
        return items;
    }

    public void setItems(List<ItemServicio> items) {
        this.items = items;
    }

}
