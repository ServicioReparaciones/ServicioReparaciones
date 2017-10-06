/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.BiMabe;
import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.modelo.sql.FacturacionMabe;
import com.servicio.reparaciones.servicio.I.IbiMabe;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class BiMabeService implements IbiMabe, Serializable {

    private static final long serialVersionUID = 5296461261280935076L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    @Inject
    private FacturacionMabeService facturacionMabeService;
    @Inject
    private OrdenServicio ordenService;

    @Override
    public Integer generatedCodigo() {
        Integer code = 4000 + 1 * count();
        return code;
    }

    @Override
    public Boolean insert(BiMabe biMabe) {
        Boolean exito = Boolean.FALSE;
        if (biMabe != null) {
            biMabe.setCodigo(generatedCodigo());
            this.ds.save(biMabe);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private static final Logger LOG = Logger.getLogger(BiMabeService.class.getName());

    public void transactionalBiMabe() {

        List<FacturacionMabe> listfact = this.facturacionMabeService.ObtenerListaFacturacionMabees();
        List<BiMabe> mabeList = new ArrayList<>();
        if (listfact != null && !listfact.isEmpty()) {
            LOG.log(Level.INFO, ">> " + listfact.size());
            for (int i = 0; i < listfact.size(); i++) {
                mabeList.add(new BiMabe());
            }
            for (FacturacionMabe fact : listfact) {
                Orden orden = this.ordenService.findByNumeroTicket(fact.getNotificacion());
                if (orden.getBarcode() != null && orden.getId() != null) {
                    LOG.log(Level.INFO, ">> " + orden.getNumeroOrden());
                    mabeList.get(fact.getIndex()).setCIUDAD(orden.getCliente().getProvincia());
                    mabeList.get(fact.getIndex()).setORDEN_DE_SERVICIO_PADRE(orden.getNumeroOrden());
                    mabeList.get(fact.getIndex()).setNOTIFICACION(orden.getNumeroTicket());
                    mabeList.get(fact.getIndex()).setVALOR_FIJO(Double.valueOf(fact.getValorFijo()));
                    mabeList.get(fact.getIndex()).setMODELO(orden.getProducto().getModelo());
                    mabeList.get(fact.getIndex()).setSERIE(orden.getProducto().getSerie());
                    mabeList.get(fact.getIndex()).setFECHA_DE_COMPRA(convertDate(orden.getProducto().getGarantia().getFechaFactura()));
                    if (orden.getListaFallas() != null && !orden.getListaFallas().isEmpty()) {
                        mabeList.get(fact.getIndex()).setCODIGO_DE_FALLA(orden.getListaFallas().get(0).getFalla().getCode());
                    }
                    if (orden.getDetalleRepuestos() != null && !orden.getDetalleRepuestos().isEmpty()) {
                        mabeList.get(fact.getIndex()).setCON_RESPUESTO("X");
                    } else {
                        mabeList.get(fact.getIndex()).setSIN_RESPUESTO("X");
                    }
                }else{
                    LOG.log(Level.INFO, ">> no find "+fact.getNotificacion() );
                }
            }

            for (BiMabe mabe : mabeList) {
                insert(mabe);
                LOG.log(Level.INFO, ">> " + mabe.getNOTIFICACION());
            }

        }
    }

    private String convertDate(Date fecha) {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = "";
        if (fecha != null) {
            date = format.format(fecha);
            LOG.log(Level.INFO, ">> " + date);
        } else {
            date = format.format(new Date());
            LOG.log(Level.INFO, ">>  null" + date);
        }
        return date;
    }

    @Override
    public BiMabe findByCodigo(BiMabe biMabe) {
        BiMabe find = new BiMabe();
        Query<BiMabe> result = this.ds.find(BiMabe.class).
                field("codigo").equal(biMabe.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public List<BiMabe> list(Integer flag) {
        List<BiMabe> list = new ArrayList<>();
        Query<BiMabe> result = this.ds.find(BiMabe.class).
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
    public List<BiMabe> lazy(int first, int pageSize, Integer flag) {
        List<BiMabe> list = new ArrayList<>();
        Query<BiMabe> result = this.ds.createQuery(BiMabe.class).
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
    public List<BiMabe> filterd(String filterProperty, String filterValue, Integer flag) {
        List<BiMabe> list = new ArrayList<>();
        Query<BiMabe> result = this.ds.find(BiMabe.class).
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
        Long result = this.ds.find(BiMabe.class).count();
        count = new Integer(result.intValue());
        return count;
    }

    @Override
    public Integer count(Integer flag) {
        Integer count = 0;
        Long result = this.ds.find(BiMabe.class).field("flag").equal(flag).count();
        count = new Integer(result.intValue());
        return count;
    }

}
