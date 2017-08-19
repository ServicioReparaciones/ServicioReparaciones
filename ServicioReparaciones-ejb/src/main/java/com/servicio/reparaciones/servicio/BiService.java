/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.BiOrden;
import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.modelo.nosql.Producto;
import com.servicio.reparaciones.servicio.I.Ibi;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class BiService implements Ibi, Serializable {

    private static final long serialVersionUID = 5296461261280935076L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    @Override
    public Integer generatedCodigo() {
        Integer code = 1000 + 1 * count();
        return code;
    }

    @Override
    public Boolean insert(BiOrden biOrden) {
        Boolean exito = Boolean.FALSE;
        if (biOrden != null) {
            biOrden.setCodigo(generatedCodigo());
            biOrden.setFlag(1);
            this.ds.save(biOrden);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    public void insertBiOrden(Orden orden) {
        BiOrden biOrden = new BiOrden();
        biOrden.setBarcode(orden.getBarcode());
        
        insert(biOrden);
    }

    @Override
    public Boolean update(BiOrden biOrden) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BiOrden findByCodigo(BiOrden biOrden) {
        BiOrden find = new BiOrden();
        Query<BiOrden> result = this.ds.find(BiOrden.class).
                field("codigo").equal(biOrden.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public BiOrden findByBarcode(BiOrden biOrden) {
        BiOrden find = new BiOrden();
        Query<BiOrden> result = this.ds.find(BiOrden.class).
                field("barcode").equal(biOrden.getBarcode()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public List<BiOrden> list(Integer flag) {
        List<BiOrden> list = new ArrayList<>();
        Query<BiOrden> result = this.ds.find(BiOrden.class).
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
    public List<BiOrden> lazy(int first, int pageSize, Integer flag) {
        List<BiOrden> list = new ArrayList<>();
        Query<BiOrden> result = this.ds.createQuery(BiOrden.class).
                field("flag").equal(flag).
                offset(first).
                limit(first + pageSize);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<BiOrden> filterd(String filterProperty, String filterValue, Integer flag) {
        List<BiOrden> list = new ArrayList<>();
        Query<BiOrden> result = this.ds.find(BiOrden.class).
                field(filterProperty).equal(filterValue).
                field("flag").equal(1);
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
        Long result = this.ds.find(BiOrden.class).field("flag").equal(flag).count();
        count = new Integer(result.intValue());
        return count;
    }

}
