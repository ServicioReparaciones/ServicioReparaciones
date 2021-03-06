/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.nosql.Cliente;
import com.servicio.reparaciones.modelo.nosql.ItemRepuesto;
import com.servicio.reparaciones.modelo.nosql.ItemServicio;
import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.modelo.nosql.Producto;
import com.servicio.reparaciones.servicio.I.Iorden;
import com.servicio.reparaciones.servicio.util.Calendario;
import com.servicio.reparaciones.util.MongoPersistence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class OrdenServicio implements Iorden, Serializable {

    private static final long serialVersionUID = -4818171587166135541L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Inject
    private ClienteServicio clienteService;
    @Inject
    private ProductoServicio productoService;
    @Inject
    private BiOrdenService biOrdenService;

    @Override
    public Integer generatedCodigo() {
        Integer code = 2000 + 1 * count();
        return code;
    }

    public String generatedBarcode() {
        return "SR-ORD" + RandomStringUtils.randomNumeric(4) + "" + count(1);
    }

    @Override
    public Boolean insert(Orden orden) {
        Boolean exito = Boolean.FALSE;
        if (orden != null) {
            orden.setCodigo(generatedCodigo());
            orden.setDetalleRepuestos(new ArrayList<ItemRepuesto>());
            orden.setDetalleServicios(new ArrayList<ItemServicio>());
            orden.setFlag(1);
            this.ds.save(orden);
            exito = Boolean.TRUE;
        }
        if (exito) {
            this.biOrdenService.transactionalBiOrden(orden, 0);
        }
        return exito;
    }

    @Override
    public Boolean update(Orden orden) {
        Query<Orden> query = this.ds.createQuery(Orden.class);
        query.and(
                query.criteria("codigo").equal(orden.getCodigo())
        );
        UpdateOperations<Orden> update = this.ds.createUpdateOperations(Orden.class);
        update.set("barcode", orden.getBarcode()).
                set("numeroOrden", orden.getNumeroOrden()).
                set("numeroTicket", orden.getNumeroTicket()).
                set("subTotalRepuestos", orden.getSubTotalRepuestos()).
                set("subTotalServicios", orden.getSubTotalServicios()).
                set("subTotalKilometraje", orden.getSubTotalKilometraje()).
                set("ciclo", orden.getCiclo()).
                set("cliente", orden.getCliente()).
                set("visita", orden.getVisita()).
                set("producto", orden.getProducto()).
                set("tecnico", orden.getTecnico()).
                set("kilometrosRuta", orden.getKilometrosRuta()).
                set("detalleRepuestos", orden.getDetalleRepuestos()).
                set("detalleServicios", orden.getDetalleServicios()).
                set("listaFallas", orden.getListaFallas()).
                set("trabajoFinalEjecutado", orden.getTrabajoFinalEjecutado()).
                set("movimientosInternos", orden.getMovimientosInternos()).
                set("url", orden.getUrl()).
                set("username", orden.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", orden.getFlag());
        UpdateResults results = this.ds.update(query, update);
        Boolean exito = results.getUpdatedExisting();
        if (exito) {
            this.biOrdenService.transactionalBiOrden(orden, 1);
        }
        return exito;
    }

    @Override
    public Orden findByCodigo(Orden orden) {
        Orden find = new Orden();
        Query<Orden> result = this.ds.find(Orden.class).
                field("codigo").equal(orden.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Orden findByBarcode(Orden orden) {
        Orden find = new Orden();
        Query<Orden> result = this.ds.find(Orden.class).
                field("barcode").equal(orden.getBarcode()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Orden findByNumeroOrden(Orden orden) {
        Orden find = new Orden();
        Query<Orden> result = this.ds.find(Orden.class).
                field("numeroOrden").equal(orden.getNumeroOrden()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Orden findByNumeroOrden(String numeroOrden) {
        Orden find = new Orden();
        Query<Orden> result = this.ds.find(Orden.class).
                field("numeroOrden").equal(numeroOrden).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Orden findByNumeroTicket(Orden orden) {
        Orden find = new Orden();
        Query<Orden> result = this.ds.find(Orden.class).
                field("numeroTicket").equal(orden.getNumeroTicket()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Orden findByNumeroTicket(String numeroTicket) {
        Orden find = new Orden();
        Query<Orden> result = this.ds.find(Orden.class).
                field("numeroTicket").equal(numeroTicket).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public List<Orden> findByCliente(Orden orden) {
        List<Orden> find = new ArrayList<>();
        Query<Orden> result = this.ds.find(Orden.class).
                field("cliente").equal(orden.getCliente()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList();
        }
        return find;
    }

    public List<Orden> findByClienteByCedula(Orden orden) {
        List<Orden> find = new ArrayList<>();
        Cliente cliente = this.clienteService.findByCedula(orden.getCliente());
        if (cliente.getId() != null) {
            Query<Orden> result = this.ds.find(Orden.class).
                    field("cliente").equal(cliente).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList();
            }
        }
        return find;
    }

    public List<Orden> findByClienteByTelefono(Orden orden) {
        List<Orden> find = new ArrayList<>();
        Cliente cliente = this.clienteService.findByTelefono(orden.getCliente());
        if (cliente.getId() != null) {
            Query<Orden> result = this.ds.find(Orden.class).
                    field("cliente").equal(cliente).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList();
            }
        }
        return find;
    }

    public List<Orden> findByClienteByMovil(Orden orden) {
        List<Orden> find = new ArrayList<>();
        Cliente cliente = this.clienteService.findByMovil(orden.getCliente());
        if (cliente.getId() != null) {
            Query<Orden> result = this.ds.find(Orden.class).
                    field("cliente").equal(cliente).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList();
            }
        }
        return find;
    }

    public List<Orden> findByProducto(Orden orden) {
        List<Orden> find = new ArrayList<>();
        Query<Orden> result = this.ds.find(Orden.class).
                field("producto").equal(orden.getProducto()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList();
        }
        return find;
    }

    public List<Orden> findByProductoByBarcode(Orden orden) {
        List<Orden> find = new ArrayList<>();
        Producto producto = this.productoService.findByBarcode(orden.getProducto());
        if (producto.getId() != null) {
            Query<Orden> result = this.ds.find(Orden.class).
                    field("producto").equal(producto).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList();
            }
        }
        return find;
    }

    public List<Orden> findByProductoBySerie(Orden orden) {
        List<Orden> find = new ArrayList<>();
        Producto producto = this.productoService.findBySerie(orden.getProducto());
        if (producto.getId() != null) {
            Query<Orden> result = this.ds.find(Orden.class).
                    field("producto").equal(producto).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList();
            }
        }
        return find;
    }

    public List<Orden> findByProductoByPnc(Orden orden) {
        List<Orden> find = new ArrayList<>();
        Producto producto = this.productoService.findByPnc(orden.getProducto());
        if (producto.getId() != null) {
            Query<Orden> result = this.ds.find(Orden.class).
                    field("producto").equal(producto).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList();
            }
        }
        return find;
    }

    public List<Orden> findByProductoByPlaca(Orden orden) {
        List<Orden> find = new ArrayList<>();
        Producto producto = this.productoService.findByPlaca(orden.getProducto());
        if (producto.getId() != null) {
            Query<Orden> result = this.ds.find(Orden.class).
                    field("producto").equal(producto).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList();
            }
        }
        return find;
    }

    @Override
    public void delete(Orden orden) {
        this.delete(orden);
    }

    @Override
    public Boolean deleteFlag(Orden orden) {
        orden.setFlag(0);
        Query<Orden> query = this.ds.createQuery(Orden.class);
        query.and(
                query.criteria("codigo").equal(orden.getCodigo())
        );
        UpdateOperations<Orden> update = this.ds.createUpdateOperations(Orden.class);
        update.set("flag", orden.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Orden> ObtenerListaOrdens(Integer flag) {
        List<Orden> list = new ArrayList<>();
        Query<Orden> result = this.ds.find(Orden.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

//    public List<Orden> ObtenerListaOrdens() {
//        List<Orden> list = new ArrayList<>();
//        Query<Orden> result = this.ds.find(Orden.class);
//        if (result.asList() != null && !result.asList().isEmpty()) {
//            list = result.asList();
//        }
//        if (list == null) {
//            list = new ArrayList<>();
//        }
//        return list;
//    }
    @Override
    public Integer count() {
        Integer count = 0;
        Long result = this.ds.find(Orden.class).count();
        count = new Integer(result.intValue());
        return count;
    }

    @Override
    public Integer count(Integer flag) {
        Integer count = 0;
        Long result = this.ds.find(Orden.class).field("flag").equal(flag).count();
        count = new Integer(result.intValue());
        return count;
    }

    //int first, int pageSize => lo = int first, lf = int first + int pageSize
    @Override
    public List<Orden> lazy(int first, int pageSize, Integer flag) {
        List<Orden> list = new ArrayList<>();
        Query<Orden> result = this.ds.createQuery(Orden.class).
                field("flag").equal(flag).
                offset(first).
                limit(first + pageSize);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

}
