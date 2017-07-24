/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Articulo;
import com.servicio.reparaciones.modelo.nosql.Bodega;
import com.servicio.reparaciones.modelo.nosql.Entrada;
import com.servicio.reparaciones.modelo.nosql.Inventario;
import com.servicio.reparaciones.modelo.nosql.Salida;
import com.servicio.reparaciones.servicio.I.Iinventario;
import com.servicio.reparaciones.servicio.util.Calendario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
public class InventarioServicio implements Iinventario, Serializable {

    private static final long serialVersionUID = 1907924409737023551L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaInventarios().size();
        Integer code = 1000 + 1 * size;
        return code;
    }

    @Override
    public Boolean insert(Inventario inventario) {
        Boolean exito = Boolean.FALSE;
        if (inventario != null) {
            inventario.setCodigo(generatedCodigo());
            inventario.setFlag(1);
            this.ds.save(inventario);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Inventario inventario) {
        Query<Inventario> query = this.ds.createQuery(Inventario.class);
        query.and(
                query.criteria("codigo").equal(inventario.getCodigo())
        );
        UpdateOperations<Inventario> update = this.ds.createUpdateOperations(Inventario.class);
        update.set("signo", inventario.getSigno()).
                set("cantidad", inventario.getCantidad()).
                set("precioUnit", inventario.getPrecioUnit()).
                set("precioTotal", inventario.getPrecioTotal()).
                set("movimiento", inventario.getMovimiento()).
                set("articulo", inventario.getArticulo()).
                set("bodega", inventario.getBodega()).
                set("username", inventario.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", inventario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public Inventario findByCodigo(Inventario inventario) {
        Inventario find = new Inventario();
        Query<Inventario> result = this.ds.find(Inventario.class).
                field("codigo").equal(inventario.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public void delete(Inventario inventario) {
        this.delete(inventario);
    }

    @Override
    public Boolean deleteFlag(Inventario inventario) {
        inventario.setFlag(0);
        Query<Inventario> query = this.ds.createQuery(Inventario.class);
        query.and(
                query.criteria("codigo").equal(inventario.getCodigo())
        );
        UpdateOperations<Inventario> update = this.ds.createUpdateOperations(Inventario.class);
        update.set("flag", inventario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    //hay que mejoarar este codigo
    public void promediosPonderados(Inventario inventario) {
        List<Inventario> inventariosBodega = ObtenerListaInventarioBodega(inventario.getBodega(), inventario.getArticulo());
        int index = inventariosBodega.size() - 1;
        if (inventario.getSigno() == 1.0) {
            Double cantidad = inventariosBodega.get(index).getCantidad() + inventario.getMovimiento().getEntrada().getCantidad() * inventario.getSigno();
            Double precioTotal = inventariosBodega.get(index).getPrecioTotal() + inventario.getMovimiento().getEntrada().getPrecioTotal() * inventario.getSigno();
            Double precioUnit = precioTotal / cantidad;
            inventario.setCantidad(cantidad);
            inventario.setPrecioUnit(precioUnit);
            inventario.setPrecioTotal(precioTotal);
            inventario.getMovimiento().setSalida(null);
            insert(inventario);
        } else if (inventario.getSigno() == -1.0) {
            Double cantidad = inventariosBodega.get(index).getCantidad() + inventario.getMovimiento().getSalida().getCantidad() * inventario.getSigno();
            Double precioTotal = inventariosBodega.get(index).getPrecioTotal() + inventario.getMovimiento().getSalida().getPrecioTotal() * inventario.getSigno();
            Double precioUnit = precioTotal / cantidad;
            inventario.setCantidad(cantidad);
            inventario.setPrecioUnit(precioUnit);
            inventario.setPrecioTotal(precioTotal);
            inventario.getMovimiento().setEntrada(null);
            insert(inventario);
        }
    }

    //hay que mejoarar este codigo
    public void updatePromediosPonderados(Inventario inventario) {
        List<Inventario> inventariosBodega = ObtenerListaInventarioBodega(inventario.getBodega(), inventario.getArticulo());
        inventariosBodega = this.findInventario(inventariosBodega, inventario);
        for (int i = 0; i < inventariosBodega.size() - 1; i++) {
            if (inventario.getSigno() == 1.0) {
                Double cantidad = inventariosBodega.get(i).getCantidad() + inventariosBodega.get(i + 1).getMovimiento().getEntrada().getCantidad() * inventario.getSigno();
                Double precioTotal = inventariosBodega.get(i).getPrecioTotal() + inventariosBodega.get(i + 1).getMovimiento().getEntrada().getPrecioTotal() * inventario.getSigno();
                Double precioUnit = precioTotal / cantidad;
                inventariosBodega.get(i + 1).setCantidad(cantidad);
                inventariosBodega.get(i + 1).setPrecioUnit(precioUnit);
                inventariosBodega.get(i + 1).setPrecioTotal(precioTotal);
                inventariosBodega.get(i + 1).getMovimiento().setSalida(null);
            } else if (inventario.getSigno() == -1.0) {
                Double cantidad = inventariosBodega.get(i).getCantidad() + inventariosBodega.get(i + 1).getMovimiento().getSalida().getCantidad() * inventario.getSigno();
                Double precioTotal = inventariosBodega.get(i).getPrecioTotal() + inventariosBodega.get(i + 1).getMovimiento().getSalida().getPrecioTotal() * inventario.getSigno();
                Double precioUnit = precioTotal / cantidad;
                inventariosBodega.get(i + 1).setCantidad(cantidad);
                inventariosBodega.get(i + 1).setPrecioUnit(precioUnit);
                inventariosBodega.get(i + 1).setPrecioTotal(precioTotal);
                inventariosBodega.get(i + 1).getMovimiento().setEntrada(null);
            }
        }
        if ((inventariosBodega.size() - 1) == 0) {
            inventariosBodega.get(0).setCantidad(inventario.getCantidad());
            inventariosBodega.get(0).setPrecioUnit(inventario.getPrecioUnit());
            inventariosBodega.get(0).setPrecioTotal(inventario.getPrecioTotal());
        }
        for (Inventario in : inventariosBodega) {
            if (inventario.getSigno() == 1.0) {
                in.getMovimiento().setSalida(null);
            } else if (inventario.getSigno() == -1.0) {
                in.getMovimiento().setEntrada(null);
            }
            update(in);
        }
    }

    private List<Inventario> findInventario(List<Inventario> list, Inventario update) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCodigoMovimiento().equals(update.getMovimiento().getEntrada().getCodigo())) {
                list.get(i).getMovimiento().setEntrada(update.getMovimiento().getEntrada());
                if (i == 0) {
                    list.get(i).setCantidad(update.getMovimiento().getEntrada().getCantidad());
                    list.get(i).setPrecioUnit(update.getMovimiento().getEntrada().getPrecioUnit());
                    list.get(i).setPrecioTotal(update.getMovimiento().getEntrada().getPrecioTotal());
                }
            } else if (list.get(i).getCodigoMovimiento().equals(update.getMovimiento().getSalida().getCodigo())) {
                list.get(i).getMovimiento().setSalida(update.getMovimiento().getSalida());
                if (i == 0) {
                    list.get(i).setCantidad(update.getMovimiento().getSalida().getCantidad());
                    list.get(i).setPrecioUnit(update.getMovimiento().getSalida().getPrecioUnit());
                    list.get(i).setPrecioTotal(update.getMovimiento().getSalida().getPrecioTotal());
                }
            }
        }
        return list;
    }

    //hay que mejoarar este codigo
    public void updateDeletePromediosPonderados(Inventario inventario) {
        List<Inventario> inventariosBodega = ObtenerListaInventarioBodega(inventario.getBodega(), inventario.getArticulo());
        inventariosBodega = this.delteInventario(inventariosBodega, inventario);
        for (int i = 0; i < inventariosBodega.size() - 1; i++) {
            if (inventario.getSigno() == 1.0) {
                Double cantidad = inventariosBodega.get(i).getCantidad() + inventariosBodega.get(i + 1).getMovimiento().getEntrada().getCantidad() * inventario.getSigno();
                Double precioTotal = inventariosBodega.get(i).getPrecioTotal() + inventariosBodega.get(i + 1).getMovimiento().getEntrada().getPrecioTotal() * inventario.getSigno();
                Double precioUnit = precioTotal / cantidad;
                inventariosBodega.get(i + 1).setCantidad(cantidad);
                inventariosBodega.get(i + 1).setPrecioUnit(precioUnit);
                inventariosBodega.get(i + 1).setPrecioTotal(precioTotal);
                inventariosBodega.get(i + 1).getMovimiento().setSalida(null);
            } else if (inventario.getSigno() == -1.0) {
                Double cantidad = inventariosBodega.get(i).getCantidad() + inventariosBodega.get(i + 1).getMovimiento().getSalida().getCantidad() * inventario.getSigno();
                Double precioTotal = inventariosBodega.get(i).getPrecioTotal() + inventariosBodega.get(i + 1).getMovimiento().getSalida().getPrecioTotal() * inventario.getSigno();
                Double precioUnit = precioTotal / cantidad;
                inventariosBodega.get(i + 1).setCantidad(cantidad);
                inventariosBodega.get(i + 1).setPrecioUnit(precioUnit);
                inventariosBodega.get(i + 1).setPrecioTotal(precioTotal);
                inventariosBodega.get(i + 1).getMovimiento().setEntrada(null);
            }
        }
        for (Inventario in : inventariosBodega) {
            if (inventario.getSigno() == 1.0) {
                in.getMovimiento().setSalida(null);
            } else if (inventario.getSigno() == -1.0) {
                in.getMovimiento().setEntrada(null);
            }
            update(in);
        }
    }

    private List<Inventario> delteInventario(List<Inventario> list, Inventario update) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCodigoMovimiento().equals(update.getMovimiento().getEntrada().getCodigo())) {
                deleteFlag(list.get(i));
                list.remove(i);
            } else if (list.get(i).getCodigoMovimiento().equals(update.getMovimiento().getSalida().getCodigo())) {
                deleteFlag(list.get(i));
                list.remove(i);
            }
        }
        return list;
    }

    @Override
    public List<Inventario> ObtenerListaInventarios(Integer flag) {
        List<Inventario> list = new ArrayList<>();
        Query<Inventario> result = this.ds.find(Inventario.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Inventario> ObtenerListaInventarios() {
        List<Inventario> list = new ArrayList<>();
        Query<Inventario> result = this.ds.find(Inventario.class);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Inventario> ObtenerListaInventarioBodega(Bodega bodega, Articulo articulo) {
        List<Inventario> list = new ArrayList<>();
        Query<Inventario> result = this.ds.find(Inventario.class).
                field("articulo").equal(articulo).
                field("bodega").equal(bodega).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMovimiento().getSalida() == null) {
                    list.get(i).getMovimiento().setSalida(new Salida());
                } else if (list.get(i).getMovimiento().getEntrada() == null) {
                    list.get(i).getMovimiento().setEntrada(new Entrada());
                }
            }
        }
        return list;
    }

    public List<Inventario> ObtenerListaInventarioBodega(Bodega bodega) {
        List<Inventario> list = new ArrayList<>();
        Query<Inventario> result = this.ds.find(Inventario.class).
                field("bodega").equal(bodega).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
