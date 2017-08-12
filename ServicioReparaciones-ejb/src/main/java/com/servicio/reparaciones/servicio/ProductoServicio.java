/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Producto;
import com.servicio.reparaciones.servicio.I.Iproducto;
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
public class ProductoServicio implements Iproducto, Serializable {

    private static final long serialVersionUID = 6657008657706353526L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    @Override
    public Integer generatedCodigo() {
        Integer size = count();
        Integer code = 1000 + 1 * size;
        return code;
    }

    public String generatedBarcode() {
        return "SR-PRO" + RandomStringUtils.randomNumeric(4) + "" + count(1);
    }

    @Override
    public Boolean insert(Producto producto) {
        Boolean exito = Boolean.FALSE;
        if (findBySerie(producto).getCodigo() == null
                && producto != null) {
            producto.setCodigo(generatedCodigo());
            producto.setFlag(1);
            this.ds.save(producto);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Producto producto) {
        Query<Producto> query = this.ds.createQuery(Producto.class);
        query.and(
                query.criteria("codigo").equal(producto.getCodigo())
        );
        UpdateOperations<Producto> update = this.ds.createUpdateOperations(Producto.class);
        update.set("barcode", producto.getBarcode()).
                set("artefacto", producto.getArtefacto()).
                set("marca", producto.getMarca()).
                set("modelo", producto.getModelo()).
                set("serie", producto.getSerie()).
                set("pnc", producto.getPnc()).
                set("placa", producto.getPlaca()).
                set("cliente", producto.getCliente()).
                set("warranty", producto.getWarranty()).
                set("garantia", producto.getGarantia()).
                set("codesWarranty", producto.getCodesWarranty()).
                set("username", producto.getUsername()).
                set("lastChange", producto.getLastChange()).
                set("flag", producto.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public Producto findByCodigo(Producto producto) {
        Producto find = new Producto();
        Query<Producto> result = this.ds.find(Producto.class).
                field("codigo").equal(producto.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public List<Producto> findByCliente(Producto producto) {
        List<Producto> find = new ArrayList<>();
        Query<Producto> result = this.ds.find(Producto.class).
                field("cliente").equal(producto.getCliente()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList();
        }
        return find;
    }

    public Producto findByBarcode(Producto producto) {
        Producto find = new Producto();
        Query<Producto> result = this.ds.find(Producto.class).
                field("barcode").equal(producto.getBarcode()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Producto findBySerie(Producto producto) {
        Producto find = new Producto();
        if (!producto.getSerie().equals("") && producto.getSerie() != null) {
            Query<Producto> result = this.ds.find(Producto.class).
                    field("serie").equal(producto.getSerie()).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList().get(0);
            }
        }
        return find;
    }

    public Producto findByPnc(Producto producto) {
        Producto find = new Producto();
        Query<Producto> result = this.ds.find(Producto.class).
                field("pnc").equal(producto.getPnc()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Producto findByPlaca(Producto producto) {
        Producto find = new Producto();
        Query<Producto> result = this.ds.find(Producto.class).
                field("placa").equal(producto.getPlaca()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public void delete(Producto producto) {
        this.ds.delete(producto);
    }

    @Override
    public Boolean deleteFlag(Producto producto) {
        producto.setFlag(0);
        Query<Producto> query = this.ds.createQuery(Producto.class);
        query.and(
                query.criteria("codigo").equal(producto.getCodigo())
        );
        UpdateOperations<Producto> update = this.ds.createUpdateOperations(Producto.class);
        update.set("flag", producto.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Producto> ObtenerListaProductos(Integer flag) {
        List<Producto> list = new ArrayList<>();
        Query<Producto> result = this.ds.find(Producto.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Producto> ObtenerListaProductos() {
        List<Producto> list = new ArrayList<>();
        Query<Producto> result = this.ds.find(Producto.class);
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
        Long result = this.ds.find(Producto.class).count();
        count = new Integer(result.intValue());
        return count;
    }

    @Override
    public Integer count(Integer flag) {
        Integer count = 0;
        Long result = this.ds.find(Producto.class).field("flag").equal(flag).count();
        count = new Integer(result.intValue());
        return count;
    }

}
