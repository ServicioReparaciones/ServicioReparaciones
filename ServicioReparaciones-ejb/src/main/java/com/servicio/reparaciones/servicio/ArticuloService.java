/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.nosql.Articulo;
import com.servicio.reparaciones.servicio.I.Iarticulo;
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
public class ArticuloService implements Iarticulo, Serializable {
    
    private static final long serialVersionUID = 2974432804247935222L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();
    
    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaArticulos().size();
        Integer code = 1000 + 1 * size;
        return code;
    }
    
    public String generatedBarcode() {
        return "SR-ART-REP" + RandomStringUtils.randomNumeric(4) + "" + ObtenerListaArticulos().size();
    }
    
    @Override
    public Boolean insert(Articulo articulo) {
        Boolean exito = Boolean.FALSE;
        if (articulo != null) {
            articulo.setCodigo(generatedCodigo());
            articulo.setUnidadMedicion(articulo.getUnidadMedicion().trim());
            articulo.setUnidadMedicion(articulo.getUnidadMedicion().toUpperCase());
            articulo.setFlag(1);
            this.ds.save(articulo);
            exito = Boolean.TRUE;
        }
        return exito;
    }
    
    @Override
    public Boolean update(Articulo articulo) {
        Query<Articulo> query = this.ds.createQuery(Articulo.class);
        query.and(
                query.criteria("codigo").equal(articulo.getCodigo())
        );
        UpdateOperations<Articulo> update = this.ds.createUpdateOperations(Articulo.class);
        update.set("barcode", articulo.getBarcode()).
                set("nombre", articulo.getNombre()).
                set("modelo", articulo.getModelo()).
                set("code", articulo.getCode()).
                set("marca", articulo.getMarca()).
                set("artefacto", articulo.getArtefacto()).
                set("numeroParte", articulo.getNumeroParte()).
                set("unidadMedicion", articulo.getUnidadMedicion()).
                set("username", articulo.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", articulo.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
    public Articulo findByNombre(Articulo articulo) {
        Articulo find = new Articulo();
        Query<Articulo> result = this.ds.find(Articulo.class).
                field("nombre").equal(articulo.getNombre()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    @Override
    public Articulo findByCodigo(Articulo articulo) {
        Articulo find = new Articulo();
        Query<Articulo> result = this.ds.find(Articulo.class).
                field("codigo").equal(articulo.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public Articulo findByBarcode(Articulo articulo) {
        Articulo find = new Articulo();
        Query<Articulo> result = this.ds.find(Articulo.class).
                field("barcode").equal(articulo.getBarcode()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public Articulo findByModelo(Articulo articulo) {
        Articulo find = new Articulo();
        Query<Articulo> result = this.ds.find(Articulo.class).
                field("modelo").equal(articulo.getModelo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public Articulo findByCode(Articulo articulo) {
        Articulo find = new Articulo();
        Query<Articulo> result = this.ds.find(Articulo.class).
                field("code").equal(articulo.getCode()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public Articulo findByNumeroParte(Articulo articulo) {
        Articulo find = new Articulo();
        Query<Articulo> result = this.ds.find(Articulo.class).
                field("numeroParte").equal(articulo.getNumeroParte()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    @Override
    public void delete(Articulo articulo) {
        this.delete(articulo);
    }
    
    @Override
    public Boolean deleteFlag(Articulo articulo) {
        articulo.setFlag(0);
        Query<Articulo> query = this.ds.createQuery(Articulo.class);
        query.and(
                query.criteria("codigo").equal(articulo.getCodigo())
        );
        UpdateOperations<Articulo> update = this.ds.createUpdateOperations(Articulo.class);
        update.set("flag", articulo.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
    @Override
    public List<Articulo> ObtenerListaArticulos(Integer flag) {
        List<Articulo> list = new ArrayList<>();
        Query<Articulo> result = this.ds.find(Articulo.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
    
    public List<Articulo> ObtenerListaArticulos() {
        List<Articulo> list = new ArrayList<>();
        Query<Articulo> result = this.ds.find(Articulo.class);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
