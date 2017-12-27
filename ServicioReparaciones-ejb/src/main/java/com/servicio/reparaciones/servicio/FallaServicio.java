/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.nosql.Falla;
import com.servicio.reparaciones.servicio.I.Ifalla;
import com.servicio.reparaciones.servicio.util.Calendario;
import com.servicio.reparaciones.util.MongoPersistence;
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
public class FallaServicio implements Ifalla, Serializable {

    private static final long serialVersionUID = -6376940601228944905L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Integer generatedCodigo() {
        Integer size = count();
        return 1000 + 1 * size;
    }

    @Override
    public Boolean insert(Falla falla) {
        Boolean exito = Boolean.FALSE;
        if (falla != null) {
            falla.setCodigo(generatedCodigo());
            falla.setFlag(1);
            this.ds.save(falla);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Falla falla) {
        Query<Falla> query = this.ds.createQuery(Falla.class);
        query.and(
                query.criteria("codigo").equal(falla.getCodigo())
        );
        UpdateOperations<Falla> update = this.ds.createUpdateOperations(Falla.class);
        update.set("code", falla.getCode()).
                set("marca", falla.getMarca()).
                set("descripcion", falla.getDescripcion()).
                set("username", falla.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", falla.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public Falla findByCodigo(Falla falla) {
        Falla find = new Falla();
        Query<Falla> result = this.ds.find(Falla.class).
                field("codigo").equal(falla.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Falla findByCodigo(Integer codigo) {
        Falla find = new Falla();
        Query<Falla> result = this.ds.find(Falla.class).
                field("codigo").equal(codigo).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Boolean deleteFlag(Falla falla) {
        falla.setFlag(0);
        Query<Falla> query = this.ds.createQuery(Falla.class);
        query.and(
                query.criteria("codigo").equal(falla.getCodigo())
        );
        UpdateOperations<Falla> update = this.ds.createUpdateOperations(Falla.class);
        update.set("flag", falla.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Falla> ObtenerListaFallas(Integer flag) {
        List<Falla> list = new ArrayList<>();
        Query<Falla> result = this.ds.find(Falla.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public Integer count() {
        Integer count = 0;
        Long result = this.ds.find(Falla.class).count();
        count = new Integer(result.intValue());
        return count;
    }

    @Override
    public Integer count(Integer flag) {
        Integer count = 0;
        Long result = this.ds.find(Falla.class).field("flag").equal(flag).count();
        count = new Integer(result.intValue());
        return count;
    }

}
