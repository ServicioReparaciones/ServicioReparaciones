/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Articulo;
import com.servicio.reparaciones.modelo.nosql.Bodega;
import com.servicio.reparaciones.modelo.nosql.Entrada;
import com.servicio.reparaciones.modelo.nosql.Inventario;
import com.servicio.reparaciones.servicio.I.Ientrada;
import com.servicio.reparaciones.servicio.util.Calendario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
public class EntradaService implements Ientrada, Serializable {

    private static final long serialVersionUID = 268517689219163886L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Inject
    private InventarioService inventarioService;

    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaEntradas().size();
        Integer code = 1000 + 1 * size;
        return code;
    }

    @Override
    public Boolean insert(Entrada entrada) {
        Boolean exito = Boolean.FALSE;
        if (entrada != null) {
            entrada.setCodigo(generatedCodigo());
            entrada.setFlag(1);
            this.ds.save(entrada);
            this.inventarioService.insert(new Inventario(entrada.getCode(),
                    entrada.getCantidad() * entrada.getSigno(),
                    entrada.getBodega(),
                    entrada.getArticulo()));
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Entrada entrada) {
        Query<Entrada> query = this.ds.createQuery(Entrada.class);
        query.and(
                query.criteria("codigo").equal(entrada.getCodigo())
        );
        UpdateOperations<Entrada> update = this.ds.createUpdateOperations(Entrada.class);
        update.set("signo", entrada.getSigno()).
                set("concepto", entrada.getConcepto()).
                set("numeroFactura", entrada.getNumeroFactura()).
                set("cantidad", entrada.getCantidad()).
                set("precioUnit", entrada.getPrecioUnit()).
                set("precioTotal", entrada.getPrecioTotal()).
                set("articulo", entrada.getArticulo()).
                set("bodega", entrada.getBodega()).
                set("username", entrada.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", entrada.getFlag());
        UpdateResults results = this.ds.update(query, update);
        Inventario in = this.inventarioService.findByCodigo(entrada.getCode());
        in.setBodega(entrada.getBodega());
        in.setArticulo(entrada.getArticulo());
        in.setCantidad(entrada.getCantidad() * entrada.getSigno());
        this.inventarioService.update(in);
        return results.getUpdatedExisting();
    }

    @Override
    public Entrada findByCodigo(Entrada entrada) {
        Entrada find = new Entrada();
        Query<Entrada> result = this.ds.find(Entrada.class).
                field("codigo").equal(entrada.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Entrada findByConcepto(Entrada entrada) {
        Entrada find = new Entrada();
        Query<Entrada> result = this.ds.find(Entrada.class).
                field("concepto").equal(entrada.getConcepto()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public List<Entrada> findByBodegaArticulo(Bodega bodega, Articulo articulo) {
        List<Entrada> find = new ArrayList<>();
        Query<Entrada> result = this.ds.find(Entrada.class).
                field("articulo").equal(articulo).
                field("bodega").equal(bodega).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList();
        }
        return find;
    }

    @Override
    public void delete(Entrada entrada) {
        this.delete(entrada);
    }

    @Override
    public Boolean deleteFlag(Entrada entrada) {
        entrada.setFlag(0);
        Query<Entrada> query = this.ds.createQuery(Entrada.class);
        query.and(
                query.criteria("codigo").equal(entrada.getCodigo())
        );
        UpdateOperations<Entrada> update = this.ds.createUpdateOperations(Entrada.class);
        update.set("flag", entrada.getFlag());
        UpdateResults results = this.ds.update(query, update);
        Inventario in = this.inventarioService.findByCodigo(entrada.getCode());
        this.inventarioService.deleteFlag(in);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Entrada> ObtenerListaEntradas(Integer flag) {
        List<Entrada> list = new ArrayList<>();
        Query<Entrada> result = this.ds.find(Entrada.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Entrada> ObtenerListaEntradas() {
        List<Entrada> list = new ArrayList<>();
        Query<Entrada> result = this.ds.find(Entrada.class);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
