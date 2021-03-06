/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.nosql.Visita;
import com.servicio.reparaciones.servicio.I.Ivisita;
import com.servicio.reparaciones.servicio.util.Calendario;
import com.servicio.reparaciones.util.MongoPersistence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class VisitaServicio implements Ivisita, Serializable {

    private static final long serialVersionUID = -6924728630090161394L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Integer generatedCodigo() {
        Integer size = count();
        Integer code = 1000 + 1 * size;
        return code;
    }

    public String generatedUnique() {
        return "SR-VIS" + RandomStringUtils.randomNumeric(4) + "" + count();
    }

    @Override
    public Boolean insert(Visita visita) {
        Boolean exito = Boolean.FALSE;
        if (visita != null) {
            visita.setCodigo(generatedCodigo());
            visita.setFechaLlegadaCliente(new Date());
            visita.setFechaSalidaCliente(new Date());
            visita.setFechaEntregaProducto(new Date());
            visita.setFlag(1);
            this.ds.save(visita);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Visita visita) {
        Query<Visita> query = this.ds.createQuery(Visita.class);
        query.and(
                query.criteria("codigo").equal(visita.getCodigo())
        );
        UpdateOperations<Visita> update = this.ds.createUpdateOperations(Visita.class);
        update.set("unique", visita.getUnique()).
                set("cliente", visita.getCliente()).
                set("producto", visita.getProducto()).
                set("fechaVisitaCliente", visita.getFechaVisitaCliente()).
                set("fechaEntregaProducto", visita.getFechaEntregaProducto()).
                set("fechaLlegadaCliente", visita.getFechaLlegadaCliente()).
                set("fechaSalidaCliente", visita.getFechaSalidaCliente()).
                set("lugarAtencion", visita.getLugarAtencion()).
                set("observacionCliente", visita.getObservacionCliente()).
                set("posibleFalla", visita.getPosibleFalla()).
                set("username", visita.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", visita.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public Visita findByCodigo(Visita visita) {
        Visita find = new Visita();
        Query<Visita> result = this.ds.find(Visita.class).
                field("codigo").equal(visita.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Visita findByUnique(Visita visita) {
        Visita find = new Visita();
        Query<Visita> result = this.ds.find(Visita.class).
                field("unique").equal(visita.getUnique()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Visita findByFechaVisitaCliente(Visita visita) {
        Visita find = new Visita();
        Query<Visita> result = this.ds.find(Visita.class).
                field("fechaVisitaCliente").equal(visita.getFechaVisitaCliente()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public void delete(Visita visita) {
        this.delete(visita);
    }

    @Override
    public Boolean deleteFlag(Visita visita) {
        visita.setFlag(0);
        Query<Visita> query = this.ds.createQuery(Visita.class);
        query.and(
                query.criteria("codigo").equal(visita.getCodigo())
        );
        UpdateOperations<Visita> update = this.ds.createUpdateOperations(Visita.class);
        update.set("flag", visita.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Visita> ObtenerListaVisitas(Integer flag) {
        List<Visita> list = new ArrayList<>();
        Query<Visita> result = this.ds.find(Visita.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Visita> ObtenerListaVisitas() {
        List<Visita> list = new ArrayList<>();
        Query<Visita> result = this.ds.find(Visita.class);
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
        Long result = this.ds.find(Visita.class).count();
        count = new Integer(result.intValue());
        return count;
    }

    @Override
    public Integer count(Integer flag) {
        Integer count = 0;
        Long result = this.ds.find(Visita.class).field("flag").equal(flag).count();
        count = new Integer(result.intValue());
        return count;
    }

}
