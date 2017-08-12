/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Articulo;
import com.servicio.reparaciones.modelo.nosql.Bodega;
import com.servicio.reparaciones.modelo.nosql.Inventario;
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
public class InventarioService implements Iinventario, Serializable {

    private static final long serialVersionUID = -7882981766997942573L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Boolean insert(Inventario inventario) {
        Boolean exito = Boolean.FALSE;
        if (inventario != null) {
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
        update.set("cantidad", inventario.getCantidad()).
                set("articulo", inventario.getArticulo()).
                set("bodega", inventario.getBodega()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", inventario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
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

    @Override
    public Inventario findByCodigo(String codigo) {
        Inventario find = new Inventario();
        Query<Inventario> result = this.ds.find(Inventario.class).
                field("codigo").equal(codigo).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public List<Inventario> findByBodega(Bodega bodega) {
        List<Inventario> find = new ArrayList<>();
        Query<Inventario> result = this.ds.find(Inventario.class).
                field("bodega").equal(bodega).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList();
        }
        return find;
    }

    @Override
    public List<Inventario> findByBodegaArticulo(Bodega bodega, Articulo articulo) {
        List<Inventario> find = new ArrayList<>();
        Query<Inventario> result = this.ds.find(Inventario.class).
                field("articulo").equal(articulo).
                field("bodega").equal(bodega).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList();
        }
        return find;
    }

    public Double CalcularExistenciasBodegaArticulo(Bodega bodega, Articulo articulo) {
        Double total = 0.0;
        List<Inventario> lista = findByBodegaArticulo(bodega, articulo);
        if (lista != null && !lista.isEmpty()) {
            for (Inventario in : lista) {
                total = total + in.getCantidad();
            }
        }
        return total;
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
}
