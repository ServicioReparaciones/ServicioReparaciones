/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Bodega;
import com.servicio.reparaciones.servicio.I.Ibodega;
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
public class BodegaService implements Ibodega, Serializable {

    private static final long serialVersionUID = 5629220083619316205L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaBodegas().size();
        Integer code = 1000 + 1 * size;
        return code;
    }

    @Override
    public Boolean insert(Bodega bodega) {
        Boolean exito = Boolean.FALSE;
        if (findByCodigo(bodega).getCodigo() == null
                && bodega != null) {
            bodega.setCodigo(generatedCodigo());
            bodega.setFlag(1);
            this.ds.save(bodega);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Bodega bodega) {
        Query<Bodega> query = this.ds.createQuery(Bodega.class);
        query.and(
                query.criteria("codigo").equal(bodega.getCodigo())
        );
        UpdateOperations<Bodega> update = this.ds.createUpdateOperations(Bodega.class);
        update.set("nombre", bodega.getNombre()).
                set("provincia", bodega.getProvincia()).
                set("canton", bodega.getCanton()).
                set("responsable", bodega.getResponsable()).
                set("username", bodega.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", bodega.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public Bodega findByCodigo(Bodega bodega) {
        Bodega find = new Bodega();
        Query<Bodega> result = this.ds.find(Bodega.class).
                field("codigo").equal(bodega.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public Bodega findByNombre(Bodega bodega) {
        Bodega find = new Bodega();
        Query<Bodega> result = this.ds.find(Bodega.class).
                field("nombre").equal(bodega.getNombre()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public void delete(Bodega bodega) {
        this.ds.delete(bodega);
    }

    @Override
    public Boolean deleteFlag(Bodega bodega) {
        bodega.setFlag(0);
        Query<Bodega> query = this.ds.createQuery(Bodega.class);
        query.and(
                query.criteria("codigo").equal(bodega.getCodigo())
        );
        UpdateOperations<Bodega> update = this.ds.createUpdateOperations(Bodega.class);
        update.set("flag", bodega.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Bodega> ObtenerListaBodegas(Integer flag) {
        List<Bodega> list = new ArrayList<>();
        Query<Bodega> result = this.ds.find(Bodega.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Bodega> ObtenerListaBodegas() {
        List<Bodega> list = new ArrayList<>();
        Query<Bodega> result = this.ds.find(Bodega.class);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

}
