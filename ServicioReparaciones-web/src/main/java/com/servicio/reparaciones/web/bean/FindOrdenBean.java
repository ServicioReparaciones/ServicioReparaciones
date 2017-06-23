/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.ItemServicio;
import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.modelo.nosql.Repuesto;
import com.servicio.reparaciones.modelo.nosql.Servicio;
import com.servicio.reparaciones.modelo.nosql.Tecnico;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.modelo.nosql.Visita;
import com.servicio.reparaciones.servicio.ClienteServicio;
import com.servicio.reparaciones.servicio.OrdenServicio;
import com.servicio.reparaciones.servicio.ProductoServicio;
import com.servicio.reparaciones.servicio.RepuestoServicio;
import com.servicio.reparaciones.servicio.ServicioServicio;
import com.servicio.reparaciones.servicio.TecnicoServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.servicio.VisitaServicio;
import com.servicio.reparaciones.servicio.util.Calendario;
import com.servicio.reparaciones.web.bean.interfaz.ImethodsFindBeans;
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
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "findOrden")
@ViewScoped
public class FindOrdenBean implements ImethodsFindBeans, Serializable {

    private static final long serialVersionUID = 4230889177898245141L;
    private Calendario calendario = new Calendario();

    private String pattern;
    private String value;
    private Orden find;
    private Orden selected;
    private List<Orden> findOrdenes;
    private Boolean active;
    private List<Servicio> servicios;
    private List<Repuesto> repuestos;
    private Usuario usuario;

    @Inject
    private ClienteServicio clienteService;
    @Inject
    private VisitaServicio visitaService;
    @Inject
    private OrdenServicio ordenService;
    @Inject
    private TecnicoServicio tecnicoService;
    @Inject
    private ProductoServicio productoService;
    @Inject
    private ItemServicioBean itemsServicio;
    @Inject
    private ItemRepuestoBean itemsRepuesto;
    @Inject
    private ServicioServicio servicioService;
    @Inject
    private RepuestoServicio repuestoService;
    @Inject
    private UsuarioServicio usarioService;

    @PostConstruct
    public void init() {
        this.pattern = "";
        this.value = "";
        this.find = new Orden();
        this.selected = null;
        this.findOrdenes = this.ordenService.ObtenerListaOrdens(1);
        Collections.reverse(this.findOrdenes);
        this.active = Boolean.FALSE;
        this.servicios = new ArrayList<>();
        this.repuestos = new ArrayList<>();
        this.usuario = new Usuario();
        this.usuario.setCodigo(SessionUtil.sessionVarNumeric("codigo"));
    }

    private void beanInit() {
        this.itemsRepuesto.init();
        this.itemsServicio.init();
    }

    public void modifyCloseOrden(ActionEvent evt) {
        if (this.selected != null) {
            this.usuario = this.usarioService.findByCodigo(this.usuario);
            this.find.getCliente().setUsername(this.usuario);
            this.clienteService.update(this.find.getCliente());
            this.selected.setUsername(this.usuario);
            this.find.getVisita().setUsername(this.usuario);
            this.visitaService.update(this.find.getVisita());
            String unique = this.find.getVisita().getUnique();
            this.find.setVisita(this.visitaService.findByUnique(new Visita(unique)));
            this.productoService.update(this.find.getProducto());
            this.find.setTecnico(this.tecnicoService.findByCodigo(this.find.getTecnico()));
            this.find.setDetalleServicios(this.itemsServicio.getItems());
            this.find.setDetalleRepuestos(this.itemsRepuesto.getItems());
            this.find.setSubTotalServicios(this.itemsServicio.calcularSubTotal());
            this.find.setSubTotalRepuestos(this.itemsRepuesto.calcularSubTotal());
            this.find.setSubTotalKilometraje(this.find.getKilometrosRuta().getSubTotal());
            this.find.getCiclo().getAbierta().setActive(Boolean.FALSE);
            this.find.getCiclo().getPendiente().setActive(Boolean.FALSE);
            this.find.getCiclo().getCerrada().setActive(Boolean.TRUE);
            this.find.getCiclo().getCerrada().setCreationDate(this.calendario.getCalendario().getTime());
            this.find.getCiclo().getCerrada().setUsername(this.usuario);
            this.find.getCiclo().setFlag(0);
            Boolean exito = this.ordenService.update(this.selected);
            if (exito) {
                this.find.getVisita().setFlag(0);
                this.visitaService.update(this.find.getVisita());
                this.init();
                this.beanInit();
                RequestContext.getCurrentInstance().execute("PF('dlgOrdenCerrada').show();");
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void modifyPendyOrden(ActionEvent evt) {
        if (this.selected != null) {
            this.usuario = this.usarioService.findByCodigo(this.usuario);
            this.selected.setUsername(this.usuario);
            this.find.getVisita().setUsername(this.usuario);
            this.visitaService.update(this.find.getVisita());
            String unique = this.find.getVisita().getUnique();
            this.find.setVisita(this.visitaService.findByUnique(new Visita(unique)));
            this.productoService.update(this.find.getProducto());
            this.find.setTecnico(this.tecnicoService.findByCodigo(this.find.getTecnico()));
            this.find.setDetalleServicios(this.itemsServicio.getItems());
            this.find.setDetalleRepuestos(this.itemsRepuesto.getItems());
            this.find.setSubTotalServicios(this.itemsServicio.calcularSubTotal());
            this.find.setSubTotalRepuestos(this.itemsRepuesto.calcularSubTotal());
            this.find.setSubTotalKilometraje(this.find.getKilometrosRuta().getSubTotal());
            this.find.getCiclo().getAbierta().setActive(Boolean.FALSE);
            this.find.getCiclo().getPendiente().setActive(Boolean.TRUE);
            this.find.getCiclo().getPendiente().setCreationDate(this.calendario.getCalendario().getTime());
            this.find.getCiclo().getPendiente().setUsername(this.usuario);
            Boolean exito = this.ordenService.update(this.selected);
            if (exito) {
                this.init();
                this.beanInit();
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void findOrden(ActionEvent evt) {
        if (this.value != null && !this.value.equals("")) {
            switch (this.pattern) {
                case "CLI01":
                    this.findOrdenes = findByCedula();
                    break;
                case "CLI02":
                    this.findOrdenes = findByTelefono();
                    break;
                case "CLI03":
                    this.findOrdenes = findByMovil();
                    break;
                case "PRO02":
                    this.findOrdenes = findBySerie();
                    break;
                case "PRO03":
                    this.findOrdenes = findByPNC();
                    break;
                case "PRO04":
                    this.findOrdenes = findByPlaca();
                    break;
                case "ORD01":
                    this.findOrdenes = findByCodigoORD();
                    break;
                case "ORD02":
                    this.findOrdenes = findByNuemeroORD();
                    break;
                case "ORD03":
                    this.findOrdenes = findByNuemeroTicket();
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

    public String onFlowProcess(FlowEvent event) {
        if (this.find.getCodigo() != null) {
            if (this.find.getProducto().getCodigo() != null) {
                this.servicios = this.servicioService.ObtenerListaServiciosMarcaArtefacto(this.find.getProducto().getMarca(), this.find.getProducto().getArtefacto());
                this.repuestos = this.repuestoService.ObtenerListaRepuestosMarcaArtefacto(this.find.getProducto().getMarca(), this.find.getProducto().getArtefacto());
            }
            if (this.find.getVisita().getServicio().getCodigo() != null
                    && this.find.getCiclo().getAbierta().getActive()) {
                if (this.itemsServicio.getItems().isEmpty()) {
                    ItemServicio e = new ItemServicio();
                    e.setCantidad(1);
                    e.setIndex(0);
                    e.setServicio(this.find.getVisita().getServicio());
                    this.itemsServicio.getItems().add(e);
                }
            }
            if (this.find.getTecnico().getCodigo() != null) {
                this.find.setTecnico(this.tecnicoService.findByCodigo(this.find.getTecnico()));
            }
        }
        return event.getNewStep();
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (Orden) event.getObject();
        if (this.selected != null) {
            if (this.selected.getCiclo().getCerrada().getActive()) {
                this.selected = null;
                this.active = Boolean.FALSE;
            } else {
                this.active = Boolean.TRUE;
                Visita v = new Visita();
                v.setCodigo(this.selected.getVisita().getCodigo());
                v.setUnique(this.selected.getVisita().getUnique());
                v.setCliente(this.selected.getVisita().getCliente());
                v.setProducto(this.selected.getVisita().getProducto());
                v.setServicio(this.selected.getVisita().getServicio());
                v.setFechaVisitaCliente(this.selected.getVisita().getFechaVisitaCliente());
                v.setPosibleFalla(this.selected.getVisita().getPosibleFalla());
                v.setLugarAtencion(this.selected.getVisita().getLugarAtencion());
                v.setObservacionCliente(this.selected.getVisita().getObservacionCliente());
                v.setFlag(this.selected.getVisita().getFlag());
                v.setUsername(this.selected.getVisita().getUsername());
                this.setFind(this.selected);
                this.getFind().setVisita(v);
                this.getFind().setTecnico(new Tecnico());
                if (this.selected.getDetalleRepuestos() != null
                        && !this.selected.getDetalleRepuestos().isEmpty()) {
                    this.itemsRepuesto.setItems(this.selected.getDetalleRepuestos());
                }
                if (this.selected.getDetalleServicios() != null
                        && !this.selected.getDetalleServicios().isEmpty()) {
                    this.itemsServicio.setItems(this.selected.getDetalleServicios());
                }
            }
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

    public ItemServicioBean getItemsServicio() {
        return itemsServicio;
    }

    public void setItemsServicio(ItemServicioBean itemsServicio) {
        this.itemsServicio = itemsServicio;
    }

    public ItemRepuestoBean getItemsRepuesto() {
        return itemsRepuesto;
    }

    public void setItemsRepuesto(ItemRepuestoBean itemsRepuesto) {
        this.itemsRepuesto = itemsRepuesto;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public List<Repuesto> getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(List<Repuesto> repuestos) {
        this.repuestos = repuestos;
    }

}
