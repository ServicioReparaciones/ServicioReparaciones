/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.nosql.BiMabeRepuesto;
import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.servicio.I.IbiMabeRepuesto;
import com.servicio.reparaciones.util.MongoPersistence;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
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
public class BiMabeRepuestoService implements IbiMabeRepuesto, Serializable {

    private static final long serialVersionUID = 5296461261280935076L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    @Override
    public Integer generatedCodigo() {
        Integer code = 3000 + 1 * count();
        return code;
    }

    @Override
    public Boolean insert(BiMabeRepuesto biMabeRepuesto) {
        Boolean exito = Boolean.FALSE;
        if (biMabeRepuesto != null) {
            biMabeRepuesto.setCodigo(generatedCodigo());
            this.ds.save(biMabeRepuesto);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    public void transactionalBiMabeRepuesto(Orden orden, int type) {

        BiMabeRepuesto biMabeRepuesto = new BiMabeRepuesto();
    }

    private List<String> convertDate(Date fecha) {
        List<String> strDate = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(fecha);
        StringTokenizer strToken = new StringTokenizer(date, "/");
        while (strToken.hasMoreElements()) {
            strDate.add(strToken.nextToken());
        }
        return strDate;
    }

    @Override
    public BiMabeRepuesto findByCodigo(BiMabeRepuesto biMabeRepuesto) {
        BiMabeRepuesto find = new BiMabeRepuesto();
        Query<BiMabeRepuesto> result = this.ds.find(BiMabeRepuesto.class).
                field("codigo").equal(biMabeRepuesto.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public List<BiMabeRepuesto> list(Integer flag) {
        List<BiMabeRepuesto> list = new ArrayList<>();
        Query<BiMabeRepuesto> result = this.ds.find(BiMabeRepuesto.class).
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
    public List<BiMabeRepuesto> lazy(int first, int pageSize, Integer flag) {
        List<BiMabeRepuesto> list = new ArrayList<>();
        Query<BiMabeRepuesto> result = this.ds.createQuery(BiMabeRepuesto.class).
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
    public List<BiMabeRepuesto> filterd(String filterProperty, String filterValue, Integer flag) {
        List<BiMabeRepuesto> list = new ArrayList<>();
        Query<BiMabeRepuesto> result = this.ds.find(BiMabeRepuesto.class).
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
        Long result = this.ds.find(BiMabeRepuesto.class).count();
        count = new Integer(result.intValue());
        return count;
    }

    @Override
    public Integer count(Integer flag) {
        Integer count = 0;
        Long result = this.ds.find(BiMabeRepuesto.class).field("flag").equal(flag).count();
        count = new Integer(result.intValue());
        return count;
    }

}
