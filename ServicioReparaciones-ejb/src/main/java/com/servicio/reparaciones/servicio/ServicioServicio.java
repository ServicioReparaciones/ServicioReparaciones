/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.nosql.Servicio;
import com.servicio.reparaciones.servicio.I.Iservicio;
import com.servicio.reparaciones.servicio.util.Calendario;
import com.servicio.reparaciones.util.MongoPersistence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ServicioServicio implements Iservicio, Serializable {

    private static final long serialVersionUID = 9135048042951372429L;
    private static final Logger LOG = Logger.getLogger(ServicioServicio.class.getName());

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaServicios().size();
        Integer code = 1000 + 1 * size;
        return code;
    }

    @Override
    public Boolean insert(Servicio servicio) {
        Boolean exito = Boolean.FALSE;
        if (findByCodigo(servicio).getCodigo() == null
                && servicio != null) {
            servicio.setCodigo(generatedCodigo());
            servicio.setFlag(1);
            this.ds.save(servicio);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Servicio servicio) {
        Query<Servicio> query = this.ds.createQuery(Servicio.class);
        query.and(
                query.criteria("codigo").equal(servicio.getCodigo())
        );
        UpdateOperations<Servicio> update = this.ds.createUpdateOperations(Servicio.class);
        update.set("descripcion", servicio.getDescripcion()).
                set("artefacto", servicio.getArtefacto()).
                set("marca", servicio.getMarca()).
                set("precio", servicio.getPrecio()).
                set("username", servicio.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", servicio.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public Servicio findByCodigo(Servicio servicio) {
        Servicio find = new Servicio();
        Query<Servicio> result = this.ds.find(Servicio.class).
                field("codigo").equal(servicio.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Servicio findByCodigo(Integer codigo) {
        Servicio find = new Servicio();
        Query<Servicio> result = this.ds.find(Servicio.class).
                field("codigo").equal(codigo).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
            LOG.log(Level.INFO, "findByCodigo(Integer codigo) >>" + find.getCodigo()+" "+find.getDescripcion(), codigo);
        }
        return find;
    }

    public Servicio findByDescripcion(Servicio servicio) {
        Servicio find = new Servicio();
        Query<Servicio> result = this.ds.find(Servicio.class).
                field("descripcion").equal(servicio.getDescripcion()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public void delete(Servicio servicio) {
        this.ds.delete(servicio);
    }

    @Override
    public Boolean deleteFlag(Servicio servicio) {
        servicio.setFlag(0);
        Query<Servicio> query = this.ds.createQuery(Servicio.class);
        query.and(
                query.criteria("codigo").equal(servicio.getCodigo())
        );
        UpdateOperations<Servicio> update = this.ds.createUpdateOperations(Servicio.class);
        update.set("flag", servicio.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Servicio> ObtenerListaServicios(Integer flag) {
        List<Servicio> list = new ArrayList<>();
        Query<Servicio> result = this.ds.find(Servicio.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Servicio> ObtenerListaServiciosMarcaArtefacto(String Marca, String Artefacto) {
        List<Servicio> list = new ArrayList<>();
        Query<Servicio> result = this.ds.find(Servicio.class).
                field("artefacto").equal(Artefacto).
                field("marca").equal(Marca);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        } else {
            Servicio blank = findByDescripcion(new Servicio("blank"));
            if (blank.getCodigo() != null) {
                list.add(blank);
            }
        }
        return list;
    }

    public List<Servicio> ObtenerListaServicios() {
        List<Servicio> list = new ArrayList<>();
        Query<Servicio> result = this.ds.find(Servicio.class);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
