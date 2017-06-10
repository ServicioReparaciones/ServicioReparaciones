/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.modelo.nosql.Tecnico;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.nosql.Visita;
import com.servicio.reparaciones.servicio.OrdenServicio;
import com.servicio.reparaciones.servicio.ServicioServicio;
import com.servicio.reparaciones.servicio.TecnicoServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.servicio.VisitaServicio;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.bean.util.GeneratedHtml;
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
import com.servicio.reparaciones.xml.servidor.OrdenServidorXml;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author luis
 */
@Named(value = "ordenBean")
@Dependent
public class OrdenBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 3453220622521364881L;

    private Orden nuevo;
    private Orden selected;
    private List<Orden> ordenes;
    private List<Orden> filterOrdenes;

    private Usuario usuario;

    @Inject
    private OrdenServicio ordenService;
    @Inject
    private ClienteBean clienteBean;
    @Inject
    private ProductoBean productoBean;
    @Inject
    private VisitaBean vistaBean;
    @Inject
    private VisitaServicio visitaService;
    @Inject
    private ServicioServicio servicioService;
    @Inject
    private TecnicoServicio tecnicoService;
    @Inject
    private OrdenServidorXml ordenServerXml;
    @Inject
    private OrdenGeneratedBean ordenGenerate;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Orden();
        this.nuevo.setBarcode(this.ordenService.generatedBarcode());
        this.selected = null;
        this.ordenes = this.ordenService.ObtenerListaOrdens(1);
        this.filterOrdenes = null;
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    private void beanInit() {
        this.clienteBean.init();
        this.productoBean.init();
        this.vistaBean.init();
    }

    private void cicloInit() {
        this.nuevo.getCiclo().getAbierta().setUsername(this.usuario);
        this.nuevo.getCiclo().getCerrada().setUsername(this.usuario);
        this.nuevo.getCiclo().getPendiente().setUsername(this.usuario);
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        this.nuevo.setCliente(this.clienteBean.getNuevo());
        this.nuevo.setProducto(this.productoBean.getNuevo());
        String unique = this.vistaBean.getNuevo().getUnique();
        this.vistaBean.add(evt);
        this.nuevo.setVisita(this.visitaService.findByUnique(new Visita(unique)));
        this.nuevo.setTecnico(this.tecnicoService.findByCargo(new Tecnico("blank")));
        this.nuevo.setNumeroOrden(this.productoBean.getNuevo().getCodesWarranty().getNumeroOrden());
        this.nuevo.setNumeroTicket(this.productoBean.getNuevo().getCodesWarranty().getNumeroTicket());
        this.nuevo.getCiclo().getAbierta().setActive(Boolean.TRUE);
        this.cicloInit();
        String barcode = this.nuevo.getBarcode();
        String url = "/var/www/html/pdf/" + barcode + "/";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAdress = request.getLocalAddr();
        String filepath = "http://" + ipAdress + "/pdf/" + barcode + "/" + barcode + ".html";
        this.nuevo.setUrl(filepath);
        this.ordenGenerate.setAbierta(this.nuevo);
        Boolean exito = this.ordenService.insert(this.nuevo);
        if (exito) {
            this.ordenGenerate.setAbierta(this.ordenService.findByBarcode(new Orden(0, barcode, "", "")));
            this.ordenServerXml.generatedXML(barcode, url, barcode, this.nuevo);
            GeneratedHtml runHtml = new GeneratedHtml(url, url + barcode + ".xml", url + barcode + ".html", url + barcode + ".pdf", barcode, 0);
            runHtml.run();
            exito = runHtml.getExito();
            if (exito) {
                this.init();
                this.beanInit();
                RequestContext.getCurrentInstance().execute("PF('dlgOrdenGenerada').show();");
            }
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
            Boolean exito = this.ordenService.update(this.selected);
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
            Boolean exito = this.ordenService.deleteFlag(this.selected);
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

    public String onFlowProcess(FlowEvent event) {
        if (this.clienteBean.getNuevo() != null) {
            this.nuevo.setCliente(this.clienteBean.getNuevo());
            this.productoBean.loadListByCliente(this.nuevo.getCliente());
            this.productoBean.setCliente(this.clienteBean.getNuevo());
        }
        if (this.productoBean.getNuevo() != null) {
            this.nuevo.setProducto(this.productoBean.getNuevo());
            this.vistaBean.getNuevo().setCliente(this.clienteBean.getNuevo());
            this.vistaBean.getNuevo().setProducto(this.productoBean.getNuevo());
        }
        if (this.vistaBean.getNuevo() != null) {
            this.vistaBean.getNuevo().setServicio(this.servicioService.findByCodigo(this.vistaBean.getNuevo().getServicio()));
            this.vistaBean.getNuevo().getServicio().setUsername(this.usuario);
        }
        return event.getNewStep();
    }

    public Orden getNuevo() {
        return nuevo;
    }

    public void setNuevo(Orden nuevo) {
        this.nuevo = nuevo;
    }

    public Orden getSelected() {
        return selected;
    }

    public void setSelected(Orden selected) {
        this.selected = selected;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    public List<Orden> getFilterOrdenes() {
        return filterOrdenes;
    }

    public void setFilterOrdenes(List<Orden> filterOrdenes) {
        this.filterOrdenes = filterOrdenes;
    }

    public ClienteBean getClienteBean() {
        return clienteBean;
    }

    public void setClienteBean(ClienteBean clienteBean) {
        this.clienteBean = clienteBean;
    }

    public VisitaBean getVistaBean() {
        return vistaBean;
    }

    public void setVistaBean(VisitaBean vistaBean) {
        this.vistaBean = vistaBean;
    }

    public ProductoBean getProductoBean() {
        return productoBean;
    }

    public void setProductoBean(ProductoBean productoBean) {
        this.productoBean = productoBean;
    }

    public OrdenGeneratedBean getOrdenGenerate() {
        return ordenGenerate;
    }

    public void setOrdenGenerate(OrdenGeneratedBean ordenGenerate) {
        this.ordenGenerate = ordenGenerate;
    }
}