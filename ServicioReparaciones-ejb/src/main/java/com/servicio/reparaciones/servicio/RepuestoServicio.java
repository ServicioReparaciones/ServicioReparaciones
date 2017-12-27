/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.nosql.Repuesto;
import com.servicio.reparaciones.servicio.I.Irepuesto;
import com.servicio.reparaciones.servicio.util.Calendario;
import com.servicio.reparaciones.util.MongoPersistence;
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
public class RepuestoServicio implements Irepuesto, Serializable {

    private static final long serialVersionUID = 2967784457584776669L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaRepuestos().size();
        Integer code = 1000 + 1 * size;
        return code;
    }

    public String generatedBarcode() {
        return "SR-REP" + RandomStringUtils.randomNumeric(4) + "" + ObtenerListaRepuestos(1).size();
    }

    @Override
    public Boolean insert(Repuesto repuesto) {
        Boolean exito = Boolean.FALSE;
        if (repuesto != null) {
            repuesto.setCodigo(generatedCodigo());
            repuesto.setFlag(1);
            this.ds.save(repuesto);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Repuesto repuesto) {
        Query<Repuesto> query = this.ds.createQuery(Repuesto.class);
        query.and(
                query.criteria("codigo").equal(repuesto.getCodigo())
        );
        UpdateOperations<Repuesto> update = this.ds.createUpdateOperations(Repuesto.class);
        update.set("barcode", repuesto.getBarcode()).
                set("modelo", repuesto.getModelo()).
                set("code", repuesto.getCode()).
                set("marca", repuesto.getMarca()).
                set("nombre", repuesto.getNombre()).
                set("descripcion", repuesto.getDescripcion()).
                set("numeroParte", repuesto.getNumeroParte()).
                set("precio", repuesto.getPrecio()).
                set("username", repuesto.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", repuesto.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public Repuesto findByCodigo(Repuesto repuesto) {
        Repuesto find = new Repuesto();
        Query<Repuesto> result = this.ds.find(Repuesto.class).
                field("codigo").equal(repuesto.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Repuesto findByCodigo(Integer codigo) {
        Repuesto find = new Repuesto();
        Query<Repuesto> result = this.ds.find(Repuesto.class).
                field("codigo").equal(codigo).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Repuesto findByBarcode(Repuesto repuesto) {
        Repuesto find = new Repuesto();
        Query<Repuesto> result = this.ds.find(Repuesto.class).
                field("barcode").equal(repuesto.getBarcode()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Repuesto findByModelo(Repuesto repuesto) {
        Repuesto find = new Repuesto();
        Query<Repuesto> result = this.ds.find(Repuesto.class).
                field("modelo").equal(repuesto.getModelo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Repuesto findByCode(Repuesto repuesto) {
        Repuesto find = new Repuesto();
        Query<Repuesto> result = this.ds.find(Repuesto.class).
                field("code").equal(repuesto.getCode()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Repuesto findByNumeroParte(Repuesto repuesto) {
        Repuesto find = new Repuesto();
        Query<Repuesto> result = this.ds.find(Repuesto.class).
                field("numeroParte").equal(repuesto.getNumeroParte()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public void delete(Repuesto repuesto) {
        this.delete(repuesto);
    }

    @Override
    public Boolean deleteFlag(Repuesto repuesto) {
        repuesto.setFlag(0);
        Query<Repuesto> query = this.ds.createQuery(Repuesto.class);
        query.and(
                query.criteria("codigo").equal(repuesto.getCodigo())
        );
        UpdateOperations<Repuesto> update = this.ds.createUpdateOperations(Repuesto.class);
        update.set("flag", repuesto.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Repuesto> ObtenerListaRepuestos(Integer flag) {
        List<Repuesto> list = new ArrayList<>();
        Query<Repuesto> result = this.ds.find(Repuesto.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Repuesto> ObtenerListaRepuestosMarcaArtefacto(String Marca, String Artefacto) {
        List<Repuesto> list = new ArrayList<>();
        Query<Repuesto> result = this.ds.find(Repuesto.class).
                field("artefacto").equal(Artefacto).
                field("marca").equal(Marca);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Repuesto> ObtenerListaRepuestos() {
        List<Repuesto> list = new ArrayList<>();
        Query<Repuesto> result = this.ds.find(Repuesto.class);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
