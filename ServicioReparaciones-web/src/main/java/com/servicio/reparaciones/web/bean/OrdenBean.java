/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Cliente;
import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.modelo.nosql.Producto;
import com.servicio.reparaciones.modelo.nosql.Servicio;
import com.servicio.reparaciones.modelo.nosql.Tecnico;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.nosql.Visita;
import com.servicio.reparaciones.servicio.OrdenServicio;
import com.servicio.reparaciones.servicio.ProductoServicio;
import com.servicio.reparaciones.servicio.ServicioServicio;
import com.servicio.reparaciones.servicio.TecnicoServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.servicio.VisitaServicio;
import com.servicio.reparaciones.servicio.util.Calendario;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsBean;
import com.servicio.reparaciones.web.bean.lazy.LazyClienteDataModel;
import com.servicio.reparaciones.web.bean.util.GeneratedHtml;
import com.servicio.reparaciones.web.util.FacesUtil;
import com.servicio.reparaciones.web.util.SessionUtil;
import com.servicio.reparaciones.xml.servidor.OrdenServidorXml;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author luis
 */
@Named(value = "ordenBean")
@ViewScoped
public class OrdenBean implements ImethodsBean, Serializable {

    private static final long serialVersionUID = 3453220622521364881L;
    private Calendario calendario = new Calendario();

    private Orden nuevo;
    private Orden selected;
    private List<Orden> ordenes;
    private List<Orden> filterOrdenes;
    private List<Servicio> servicios;
    private Usuario usuario;

    private LazyDataModel<Cliente> lazyModelCliente;
    private Cliente selectedCliente;
    private String urlPathVaucher;

    private Producto nuevoProducto;
    private Producto selectedProducto;
    private List<Producto> productos;
    private List<Producto> filterProductos;

    @Inject
    private VisitaBean vistaBean;
    @Inject
    private OrdenGeneratedBean ordenGenerate;

    @Inject
    private ProductoServicio productoService;
    @Inject
    private OrdenServicio ordenService;
    @Inject
    private VisitaServicio visitaService;
    @Inject
    private ServicioServicio servicioService;
    @Inject
    private TecnicoServicio tecnicoService;
    @Inject
    private OrdenServidorXml ordenServerXml;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Orden();
        this.nuevo.setBarcode(this.ordenService.generatedBarcode());
        this.nuevoProducto = new Producto();
        this.nuevoProducto.setBarcode(this.productoService.generatedBarcode());
        this.selected = null;
        this.ordenes = this.ordenService.ObtenerListaOrdens(1);
        this.filterOrdenes = null;
        this.lazyModelCliente = new LazyClienteDataModel(1);
        this.selectedCliente = null;
        this.servicios = new ArrayList<>();
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    private void beanInit() {
        this.vistaBean.init();
        this.ordenGenerate.init();
    }

    private void cicloInit() {
        this.nuevo.getCiclo().getAbierta().setUsername(this.usuario);
        this.nuevo.getCiclo().getCerrada().setUsername(this.usuario);
        this.nuevo.getCiclo().getPendiente().setUsername(this.usuario);
        this.nuevo.getCiclo().getCancelada().setUsername(this.usuario);
    }

    public void addProductoWarranty(ActionEvent evt, Boolean warranty) {
        if (warranty != null) {
            this.usuario = this.usarioService.findByCodigo(this.usuario);
            this.nuevoProducto.setUsername(this.usuario);
            this.nuevoProducto.setCliente(this.nuevo.getCliente());
            this.nuevoProducto.setWarranty(warranty);
            if (this.nuevoProducto.getModelo() != null && !this.nuevoProducto.getModelo().equals("")) {
                this.nuevoProducto.setModelo(this.nuevoProducto.getModelo().trim());
                this.nuevoProducto.setModelo(this.nuevoProducto.getModelo().toUpperCase());
            }
            if (this.nuevoProducto.getSerie() != null && !this.nuevoProducto.getSerie().equals("")) {
                this.nuevoProducto.setSerie(this.nuevoProducto.getSerie().trim());
                this.nuevoProducto.setSerie(this.nuevoProducto.getSerie().toUpperCase());
            }
            if (this.nuevoProducto.getPlaca() != null && !this.nuevoProducto.getPlaca().equals("")) {
                this.nuevoProducto.setPlaca(this.nuevoProducto.getPlaca().trim());
                this.nuevoProducto.setPlaca(this.nuevoProducto.getPlaca().toUpperCase());
            }
            if (this.nuevoProducto.getPnc() != null && !this.nuevoProducto.getPnc().equals("")) {
                this.nuevoProducto.setPnc(this.nuevoProducto.getPnc().trim());
                this.nuevoProducto.setPnc(this.nuevoProducto.getPnc().toUpperCase());
            }
            Boolean exito = this.productoService.insert(this.nuevoProducto);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha guardado con exito.");
                this.updateListByCliente();
                this.nuevoProducto = new Producto();
                this.nuevoProducto.setBarcode(this.productoService.generatedBarcode());
            } else {
                FacesUtil.addMessageError(null, "No se ha guardado.");
            }
        }
    }

    public void onRowSelectCliente(SelectEvent event) {
        this.selectedCliente = (Cliente) event.getObject();
        if (this.selectedCliente != null) {
            this.getNuevo().setCliente(this.selectedCliente);
        } else {
            this.selectedCliente = null;
        }
    }

    public void onRowSelectProducto(SelectEvent event) {
        this.selectedProducto = (Producto) event.getObject();
        if (this.selectedProducto != null) {
            this.getNuevo().setProducto(this.selectedProducto);
        } else {
            this.selectedProducto = null;
        }
    }

    @Override
    public void add(ActionEvent evt) {
        this.usuario = this.usarioService.findByCodigo(this.usuario);
        this.nuevo.setUsername(this.usuario);
        String unique = this.vistaBean.getNuevo().getUnique();
        this.vistaBean.add(evt);
        this.nuevo.setVisita(this.visitaService.findByUnique(new Visita(unique)));
        this.nuevo.setTecnico(this.tecnicoService.findByCargo(new Tecnico("blank")));
        this.nuevo.setNumeroOrden(this.nuevo.getProducto().getCodesWarranty().getNumeroOrden());
        this.nuevo.setNumeroTicket(this.nuevo.getProducto().getCodesWarranty().getNumeroTicket());
        this.nuevo.getCiclo().getAbierta().setActive(Boolean.TRUE);
        this.nuevo.getCiclo().getAbierta().setCreationDate(this.calendario.getCalendario().getTime());
        this.cicloInit();
        String barcode = this.nuevo.getBarcode();
        String url = "/var/www/html/pdf/" + barcode + "/";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAdress = request.getLocalAddr();
        String filepath = "http://" + ipAdress + "/pdf/" + barcode + "/" + barcode + ".html";
        this.nuevo.setUrl(filepath);
        this.setUrlPathVaucher(filepath);
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

    private void loadListByCliente(Cliente cliente) {
        Producto producto = new Producto();
        producto.setCliente(cliente);
        this.productos = this.productoService.findByCliente(producto);
        Collections.reverse(this.productos);
    }

    private void updateListByCliente() {
        Producto producto = new Producto();
        if (this.nuevo.getCliente().getCodigo() != null) {
            producto.setCliente(this.nuevo.getCliente());
            this.productos = this.productoService.findByCliente(producto);
            Collections.reverse(this.productos);
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if (this.nuevo.getCliente() != null) {
            loadListByCliente(this.nuevo.getCliente());
        }
        if (this.nuevo.getProducto() != null) {
            this.servicios = this.servicioService.ObtenerListaServiciosMarcaArtefacto(this.nuevo.getProducto().getMarca(), this.nuevo.getProducto().getArtefacto());
            this.vistaBean.getNuevo().setCliente(this.nuevo.getCliente());
            this.vistaBean.getNuevo().setProducto(this.nuevo.getProducto());
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

    public VisitaBean getVistaBean() {
        return vistaBean;
    }

    public void setVistaBean(VisitaBean vistaBean) {
        this.vistaBean = vistaBean;
    }

    public OrdenGeneratedBean getOrdenGenerate() {
        return ordenGenerate;
    }

    public void setOrdenGenerate(OrdenGeneratedBean ordenGenerate) {
        this.ordenGenerate = ordenGenerate;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public LazyDataModel<Cliente> getLazyModelCliente() {
        return lazyModelCliente;
    }

    public void setLazyModelCliente(LazyDataModel<Cliente> lazyModelCliente) {
        this.lazyModelCliente = lazyModelCliente;
    }

    public Cliente getSelectedCliente() {
        return selectedCliente;
    }

    public void setSelectedCliente(Cliente selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    public String getUrlPathVaucher() {
        return urlPathVaucher;
    }

    public void setUrlPathVaucher(String urlPathVaucher) {
        this.urlPathVaucher = urlPathVaucher;
    }

    public Producto getSelectedProducto() {
        return selectedProducto;
    }

    public void setSelectedProducto(Producto selectedProducto) {
        this.selectedProducto = selectedProducto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Producto> getFilterProductos() {
        return filterProductos;
    }

    public void setFilterProductos(List<Producto> filterProductos) {
        this.filterProductos = filterProductos;
    }

    public Producto getNuevoProducto() {
        return nuevoProducto;
    }

    public void setNuevoProducto(Producto nuevoProducto) {
        this.nuevoProducto = nuevoProducto;
    }

}
