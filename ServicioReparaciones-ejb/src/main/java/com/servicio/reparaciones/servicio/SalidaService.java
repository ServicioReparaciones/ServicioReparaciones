/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Salida;
import com.servicio.reparaciones.servicio.I.Isalida;
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
public class SalidaService implements Isalida, Serializable {

    private static final long serialVersionUID = -7588891280802943350L;
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaSalidas().size();
        Integer code = 1000 + 1 * size;
        return code;
    }

    @Override
    public Boolean insert(Salida salida) {
        Boolean exito = Boolean.FALSE;
        if (salida != null) {
            salida.setCodigo(generatedCodigo());
            salida.setFlag(1);
            this.ds.save(salida);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Salida salida) {
        Query<Salida> query = this.ds.createQuery(Salida.class);
        query.and(
                query.criteria("codigo").equal(salida.getCodigo())
        );
        UpdateOperations<Salida> update = this.ds.createUpdateOperations(Salida.class);
        update.set("cantidad", salida.getCantidad()).
                set("precioUnit", salida.getPrecioUnit()).
                set("precioTotal", salida.getPrecioTotal()).
                set("articulo", salida.getArticulo()).
                set("bodega", salida.getBodega()).
                set("quienRecibe", salida.getQuienRecibe()).
                set("username", salida.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", salida.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public Salida findByCodigo(Salida salida) {
        Salida find = new Salida();
        Query<Salida> result = this.ds.find(Salida.class).
                field("codigo").equal(salida.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Salida findByConcepto(Salida salida) {
        Salida find = new Salida();
        Query<Salida> result = this.ds.find(Salida.class).
                field("concepto").equal(salida.getConcepto()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public void delete(Salida salida) {
        this.delete(salida);
    }

    @Override
    public Boolean deleteFlag(Salida salida) {
        salida.setFlag(0);
        Query<Salida> query = this.ds.createQuery(Salida.class);
        query.and(
                query.criteria("codigo").equal(salida.getCodigo())
        );
        UpdateOperations<Salida> update = this.ds.createUpdateOperations(Salida.class);
        update.set("flag", salida.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Salida> ObtenerListaSalidas(Integer flag) {
        List<Salida> list = new ArrayList<>();
        Query<Salida> result = this.ds.find(Salida.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Salida> ObtenerListaSalidas() {
        List<Salida> list = new ArrayList<>();
        Query<Salida> result = this.ds.find(Salida.class);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

}
