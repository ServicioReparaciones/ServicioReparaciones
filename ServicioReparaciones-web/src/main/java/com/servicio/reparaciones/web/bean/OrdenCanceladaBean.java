/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Comentario;
import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.servicio.OrdenServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.servicio.VisitaServicio;
import com.servicio.reparaciones.servicio.util.Calendario;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsFindBeans;
import com.servicio.reparaciones.web.bean.lazy.LazyOrdenDataModel;
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
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
@Named(value = "ordenCanceladaBean")
@ViewScoped
public class OrdenCanceladaBean implements ImethodsFindBeans, Serializable {

    private static final long serialVersionUID = 8802413554696902210L;

    private LazyDataModel<Orden> lazyModel;
    private LazyDataModel<Orden> lazyModelCanceladas;
    private String pattern;
    private String value;
    private String texto;
    private Orden find;
    private Orden selected;
    private List<Orden> findOrdenes;
    private Boolean active;
    private Usuario usuario;

    @Inject
    private OrdenServicio ordenService;
    @Inject
    private VisitaServicio visitaService;
    @Inject
    private UsuarioServicio usuarioService;

    @PostConstruct
    public void init() {
        this.pattern = "";
        this.value = "";
        this.texto = "";
        this.find = new Orden();
        this.selected = null;
        this.lazyModel = new LazyOrdenDataModel(1);
        this.lazyModelCanceladas = new LazyOrdenDataModel(0);
        this.findOrdenes = new ArrayList<>();
        this.active = Boolean.FALSE;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    public void findOrden(ActionEvent evt) {
        if (this.value != null && !this.value.equals("")) {
            switch (this.pattern) {
                case "CLI01":
                    this.findOrdenes = findByCedula();
                    this.lazyModel = new LazyOrdenDataModel(this.findOrdenes);
                    break;
                case "CLI02":
                    this.findOrdenes = findByTelefono();
                    this.lazyModel = new LazyOrdenDataModel(this.findOrdenes);
                    break;
                case "CLI03":
                    this.findOrdenes = findByMovil();
                    this.lazyModel = new LazyOrdenDataModel(this.findOrdenes);
                    break;
                case "PRO02":
                    this.findOrdenes = findBySerie();
                    this.lazyModel = new LazyOrdenDataModel(this.findOrdenes);
                    break;
                case "PRO03":
                    this.findOrdenes = findByPNC();
                    this.lazyModel = new LazyOrdenDataModel(this.findOrdenes);
                    break;
                case "PRO04":
                    this.findOrdenes = findByPlaca();
                    this.lazyModel = new LazyOrdenDataModel(this.findOrdenes);
                    break;
                case "ORD01":
                    this.findOrdenes = findByCodigoORD();
                    this.lazyModel = new LazyOrdenDataModel(this.findOrdenes);
                    break;
                case "ORD02":
                    this.findOrdenes = findByNuemeroORD();
                    this.lazyModel = new LazyOrdenDataModel(this.findOrdenes);
                    break;
                case "ORD03":
                    this.findOrdenes = findByNuemeroTicket();
                    this.lazyModel = new LazyOrdenDataModel(this.findOrdenes);
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

    public void add(ActionEvent evt) {
        Calendario calendario = new Calendario();
        if (this.texto != null && !this.texto.equals("")) {
            this.usuario = this.usuarioService.findByCodigo(this.usuario);
            this.find.getCiclo().getCancelada().setActive(Boolean.TRUE);
            this.find.getCiclo().getCancelada().setComentario(this.texto.trim());
            this.find.getCiclo().getCancelada().setCreationDate(calendario.getCalendario().getTime());
            this.find.getCiclo().getCancelada().setUsername(this.usuario);
            this.find.setFlag(0);
            this.find.setUsername(this.usuario);
            Boolean exito = this.ordenService.update(this.find);
            if (exito) {
                exito = this.visitaService.deleteFlag(this.find.getVisita());
                FacesUtil.addMessageInfo("Se ha cancelado");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha cancelado.");
            }
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (Orden) event.getObject();
        if (this.selected != null) {
            if (this.selected.getCiclo().getCerrada().getActive()) {
                this.selected = null;
                this.active = Boolean.FALSE;
            } else if (this.selected.getCiclo().getPendiente().getActive()) {
                this.selected = null;
                this.active = Boolean.FALSE;
            } else {
                this.active = Boolean.TRUE;
                if (this.selected.getMovimientosInternos() == null) {
                    this.selected.setMovimientosInternos(new ArrayList<Comentario>());
                }
                this.setFind(this.selected);
            }
        }
    }

    public LazyDataModel<Orden> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Orden> lazyModel) {
        this.lazyModel = lazyModel;
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    public List<Orden> getFindOrdenes() {
        return findOrdenes;
    }

    public void setFindOrdenes(List<Orden> findOrdenes) {
        this.findOrdenes = findOrdenes;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LazyDataModel<Orden> getLazyModelCanceladas() {
        return lazyModelCanceladas;
    }

    public void setLazyModelCanceladas(LazyDataModel<Orden> lazyModelCanceladas) {
        this.lazyModelCanceladas = lazyModelCanceladas;
    }

}
