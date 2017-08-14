/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.servicio.OrdenServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsFindBeans;
import com.servicio.reparaciones.web.bean.lazy.LazyOrdenDataModel;
import com.servicio.reparaciones.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author luis
 */
@Named(value = "recordOrdenBean")
@ViewScoped
public class RecordOrdenBean implements ImethodsFindBeans, Serializable {

    private static final long serialVersionUID = -2606226957544898764L;

    private LazyDataModel<Orden> lazyModel;
    private String pattern;
    private String value;
    private Orden find;
    private Orden selected;
    private List<Orden> recordOrdenes;
    private Boolean active;

    @Inject
    private OrdenServicio ordenService;

    @PostConstruct
    public void init() {
        this.pattern = "";
        this.value = "";
        this.find = new Orden();
        this.selected = null;
        this.lazyModel = new LazyOrdenDataModel(1);
        this.recordOrdenes = new ArrayList<>();
        this.active = Boolean.FALSE;
    }

    public void findOrden(ActionEvent evt) {
        if (this.value != null && !this.value.equals("")) {
            switch (this.pattern) {
                case "CLI01":
                    this.recordOrdenes = findByCedula();
                    this.lazyModel = new LazyOrdenDataModel(this.recordOrdenes);
                    break;
                case "CLI02":
                    this.recordOrdenes = findByTelefono();
                    this.lazyModel = new LazyOrdenDataModel(this.recordOrdenes);
                    break;
                case "CLI03":
                    this.recordOrdenes = findByMovil();
                    this.lazyModel = new LazyOrdenDataModel(this.recordOrdenes);
                    break;
                case "PRO02":
                    this.recordOrdenes = findBySerie();
                    this.lazyModel = new LazyOrdenDataModel(this.recordOrdenes);
                    break;
                case "PRO03":
                    this.recordOrdenes = findByPNC();
                    this.lazyModel = new LazyOrdenDataModel(this.recordOrdenes);
                    break;
                case "PRO04":
                    this.recordOrdenes = findByPlaca();
                    this.lazyModel = new LazyOrdenDataModel(this.recordOrdenes);
                    break;
                case "ORD01":
                    this.recordOrdenes = findByCodigoORD();
                    this.lazyModel = new LazyOrdenDataModel(this.recordOrdenes);
                    break;
                case "ORD02":
                    this.recordOrdenes = findByNuemeroORD();
                    this.lazyModel = new LazyOrdenDataModel(this.recordOrdenes);
                    break;
                case "ORD03":
                    this.recordOrdenes = findByNuemeroTicket();
                    this.lazyModel = new LazyOrdenDataModel(this.recordOrdenes);
                    break;
                default:
                    FacesUtil.addMessageInfo("Vuelva a intentarlo.");
            }
        }
    }

    @Override
    public List<Orden> findByCedula() {
        Orden find = new Orden();
        find.getCliente().setCedula(this.value.trim());
        return this.ordenService.findByClienteByCedula(find);
    }

    @Override
    public List<Orden> findByTelefono() {
        Orden find = new Orden();
        find.getCliente().setTelefono(this.value.trim());
        return this.ordenService.findByClienteByTelefono(find);
    }

    @Override
    public List<Orden> findByMovil() {
        Orden find = new Orden();
        find.getCliente().setMovil(this.value.trim());
        return this.ordenService.findByClienteByMovil(find);
    }

    @Override
    public List<Orden> findBySerie() {
        Orden find = new Orden();
        find.getProducto().setSerie(this.value.trim());
        return this.ordenService.findByProductoBySerie(find);
    }

    @Override
    public List<Orden> findByPNC() {
        Orden find = new Orden();
        find.getProducto().setPnc(this.value.trim());
        return this.ordenService.findByProductoByPnc(find);
    }

    @Override
    public List<Orden> findByPlaca() {
        Orden find = new Orden();
        find.getProducto().setPlaca(this.value.trim());
        return this.ordenService.findByProductoByPlaca(find);
    }

    @Override
    public List<Orden> findByCodigoORD() {
        Orden find = new Orden();
        String number = this.value.trim();
        if (StringUtils.isNumeric(number)) {
            find.setCodigo(Integer.parseInt(this.value));
            find = this.ordenService.findByCodigo(find);
        }
        List<Orden> list = new ArrayList<>();
        if (find.getId() != null) {
            list.add(find);
        }
        return list;
    }

    @Override
    public List<Orden> findByNuemeroORD() {
        Orden find = new Orden();
        String number = this.value.trim();
        if (StringUtils.isNumeric(number)) {
            find.setNumeroOrden(this.value);
            find = this.ordenService.findByNumeroOrden(find);
        }
        List<Orden> list = new ArrayList<>();
        if (find.getId() != null) {
            list.add(find);
        }
        return list;
    }

    @Override
    public List<Orden> findByNuemeroTicket() {
        Orden find = new Orden();
        String number = this.value.trim();
        if (StringUtils.isNumeric(number)) {
            find.setNumeroTicket(this.value);
            find = this.ordenService.findByNumeroTicket(find);
        }
        List<Orden> list = new ArrayList<>();
        if (find.getId() != null) {
            list.add(find);
        }
        return list;
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (Orden) event.getObject();
        if (this.selected != null) {
            this.active = Boolean.TRUE;
            this.setFind(this.selected);
        }
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Orden getFind() {
        return find;
    }

    public void setFind(Orden find) {
        this.find = find;
    }

    public Orden getSelected() {
        return selected;
    }

    public void setSelected(Orden selected) {
        this.selected = selected;
    }

    public List<Orden> getRecordOrdenes() {
        return recordOrdenes;
    }

    public void setRecordOrdenes(List<Orden> recordOrdenes) {
        this.recordOrdenes = recordOrdenes;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LazyDataModel<Orden> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Orden> lazyModel) {
        this.lazyModel = lazyModel;
    }

}
