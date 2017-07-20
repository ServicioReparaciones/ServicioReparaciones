/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Inventario;
import com.servicio.reparaciones.servicio.I.Iinventario;
import com.servicio.reparaciones.servicio.util.Calendario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
public class InventarioService implements Iinventario, Serializable {

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

    public Integer generatedIndex() {
        Integer number = ObtenerListaInventarios(1).size() + 1;
        return number;
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
        update.set("index", inventario.getIndex()).
                set("signo", inventario.getSigno()).
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
