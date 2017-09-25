/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.ItemFalla;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author luis
 */
@Named(value = "itemFallaBean")
@ViewScoped
public class ItemFallaBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 2716532692607176408L;

    private ItemFalla nuevo;
    private ItemFalla selected;
    private List<ItemFalla> items;

    @PostConstruct
    public void init() {
        this.nuevo = new ItemFalla();
        this.selected = null;
        this.items = new ArrayList<>();
    }

    @Override
    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.nuevo.getFalla().getCodigo() != null) {
            Integer index = this.items.size();
            ItemFalla item = new ItemFalla();
            item.setIndex(index);
            item.setFalla(this.nuevo.getFalla());
            Boolean exito = this.items.add(item);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
            }
        }
    }

    @Override
    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            ItemFalla temp = this.selected;
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
            ItemFalla temp = this.selected;
            Boolean exito = this.items.remove(temp);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
            }
        }
    }

    public ItemFalla getNuevo() {
        return nuevo;
    }

    public void setNuevo(ItemFalla nuevo) {
        this.nuevo = nuevo;
    }

    public ItemFalla getSelected() {
        return selected;
    }

    public void setSelected(ItemFalla selected) {
        this.selected = selected;
    }

    public List<ItemFalla> getItems() {
        return items;
    }

    public void setItems(List<ItemFalla> items) {
        this.items = items;
    }

}
