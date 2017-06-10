/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Visita;
import com.servicio.reparaciones.servicio.Interfaz.Ivisita;
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

    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaVisitas().size();
        Integer code = 1000 + 1 * size;
        return code;
    }

    public String generatedUnique() {
        return "SR-VIS" + RandomStringUtils.randomNumeric(4) + "" + ObtenerListaVisitas().size();
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
    public Boolean update(Visita Visita) {
        Query<Visita> query = this.ds.createQuery(Visita.class);
        query.and(
                query.criteria("codigo").equal(Visita.getCodigo())
        );
        UpdateOperations<Visita> update = this.ds.createUpdateOperations(Visita.class);
        update.set("unique", Visita.getUnique()).
                set("cliente", Visita.getCliente()).
                set("producto", Visita.getProducto()).
                set("fechaVisitaCliente", Visita.getFechaVisitaCliente()).
                set("fechaEntregaProducto", Visita.getFechaEntregaProducto()).
                set("fechaLlegadaCliente", Visita.getFechaLlegadaCliente()).
                set("fechaSalidaCliente", Visita.getFechaSalidaCliente()).
                set("lugarAtencion", Visita.getLugarAtencion()).
                set("observacionCliente", Visita.getObservacionCliente()).
                set("posibleFalla", Visita.getPosibleFalla()).
                set("username", Visita.getUsername()).
                set("flag", Visita.getFlag());
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

}
