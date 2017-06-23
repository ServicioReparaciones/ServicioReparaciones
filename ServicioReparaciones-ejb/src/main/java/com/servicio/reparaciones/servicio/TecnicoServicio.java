/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Tecnico;
import com.servicio.reparaciones.servicio.Interfaz.Itecnico;
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
public class TecnicoServicio implements Itecnico, Serializable {

    private static final long serialVersionUID = -159213916689276716L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaTecnicos().size();
        Integer code = 1000 + 1 * size;
        return code;
    }

    @Override
    public Boolean insert(Tecnico tecnico) {
    Boolean exito = Boolean.FALSE;
        if (findByCodigo(tecnico).getCodigo() == null
                && tecnico != null) {
            tecnico.setCodigo(generatedCodigo());
            tecnico.setFlag(1);
            this.ds.save(tecnico);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Tecnico tecnico) {
    Query<Tecnico> query = this.ds.createQuery(Tecnico.class);
        query.and(
                query.criteria("codigo").equal(tecnico.getCodigo())
        );
        UpdateOperations<Tecnico> update = this.ds.createUpdateOperations(Tecnico.class);
        update.set("cargo", tecnico.getCargo()).
                set("datosPersonales", tecnico.getDatosPersonales()).
                set("username", tecnico.getUsername()).
                set("lastChange",this.calendario.getCalendario().getTime()).
                set("flag", tecnico.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();    
    }

    @Override
    public Tecnico findByCodigo(Tecnico tecnico) {
    Tecnico find = new Tecnico();
        Query<Tecnico> result = this.ds.find(Tecnico.class).
                field("codigo").equal(tecnico.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;    
    }
    
    public Tecnico findByCargo(Tecnico tecnico) {
    Tecnico find = new Tecnico();
        Query<Tecnico> result = this.ds.find(Tecnico.class).
                field("cargo").equal(tecnico.getCargo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;    
    }

    @Override
    public void delete(Tecnico tecnico) {
        this.ds.delete(tecnico);
    }

    @Override
    public Boolean deleteFlag(Tecnico tecnico) {
        tecnico.setFlag(0);
        Query<Tecnico> query = this.ds.createQuery(Tecnico.class);
        query.and(
                query.criteria("codigo").equal(tecnico.getCodigo())
        );
        UpdateOperations<Tecnico> update = this.ds.createUpdateOperations(Tecnico.class);
        update.set("flag", tecnico.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Tecnico> ObtenerListaTecnicos(Integer flag) {
        List<Tecnico> list = new ArrayList<>();
        Query<Tecnico> result = this.ds.find(Tecnico.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

     public List<Tecnico> ObtenerListaTecnicos() {
        List<Tecnico> list = new ArrayList<>();
        Query<Tecnico> result = this.ds.find(Tecnico.class);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
