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
public class BiOrdenService implements Ibi, Serializable {

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

    public void insertBiOrden(Orden orden, int type) {

        BiOrden biOrden = new BiOrden();

        biOrden.setBarcode(orden.getBarcode());
        biOrden.setNumeroOrden(orden.getNumeroOrden());
        biOrden.setNumeroTicket(orden.getNumeroTicket());

        biOrden.setCliente(orden.getCliente().getNombres() + " " + orden.getCliente().getApellidos());
        biOrden.setTelefono(orden.getCliente().getTelefono());
        biOrden.setMovil(orden.getCliente().getMovil());
        biOrden.setProvincia(orden.getCliente().getProvincia());
        biOrden.setCanton(orden.getCliente().getCanton());
        biOrden.setParroquia(orden.getCliente().getParroquia());
        biOrden.setDireccion(orden.getCliente().getDireccion());

        biOrden.setFechaVisitaCliente(orden.getVisita().getFechaVisitaCliente());
        biOrden.setFechaEntregaProducto(orden.getVisita().getFechaEntregaProducto());
        biOrden.setFechaLlegadaCliente(orden.getVisita().getFechaLlegadaCliente());
        biOrden.setFechaSalidaCliente(orden.getVisita().getFechaSalidaCliente());
        biOrden.setLugarAtencion(orden.getVisita().getLugarAtencion());
        biOrden.setPosibleFalla(orden.getVisita().getPosibleFalla());
        biOrden.setObservacionCliente(orden.getVisita().getObservacionCliente());

        biOrden.setArtefacto(orden.getProducto().getArtefacto());
        biOrden.setMarca(orden.getProducto().getMarca());

        if (orden.getProducto().getModelo() != null && !orden.getProducto().getModelo().equals("")) {
            biOrden.setModelo(orden.getProducto().getModelo().trim());
            biOrden.setModelo(orden.getProducto().getModelo().toUpperCase());
        } else {
            biOrden.setModelo("");
        }

        if (orden.getProducto().getSerie() != null && !orden.getProducto().getSerie().equals("")) {
            biOrden.setSerie(orden.getProducto().getSerie().trim());
            biOrden.setSerie(orden.getProducto().getSerie().toUpperCase());
        } else {
            biOrden.setSerie("");
        }

        if (orden.getProducto().getPnc() != null && !orden.getProducto().getPnc().equals("")) {
            biOrden.setPnc(orden.getProducto().getPnc().trim());
            biOrden.setPnc(orden.getProducto().getPnc().toUpperCase());
        } else {
            biOrden.setPnc("");
        }

        if (orden.getProducto().getPlaca() != null && !orden.getProducto().getPlaca().equals("")) {
            biOrden.setPlaca(orden.getProducto().getPlaca().trim());
            biOrden.setPlaca(orden.getProducto().getPlaca().toUpperCase());
        } else {
            biOrden.setPlaca("");
        }

        biOrden.setWarranty(orden.getProducto().getWarranty());
        biOrden.setStock(orden.getProducto().getStock());

        if (orden.getTecnico().getCargo() != null && !orden.getTecnico().getCargo().equals("blank")) {
            biOrden.setTecnico_codigo(orden.getTecnico().getCodigo());
            biOrden.setTecnico(orden.getTecnico().getDatosPersonales().getNombres() + " " + orden.getTecnico().getDatosPersonales().getApellidos());
        } else {
            biOrden.setTecnico_codigo(0);
            biOrden.setTecnico("");
        }

        biOrden.setKmRecorridos(orden.getKilometrosRuta().getKmRecorridos());
        biOrden.setValorPorKmRecorrido(orden.getKilometrosRuta().getValorPorKmRecorrido());
        biOrden.setSubTotal(orden.getKilometrosRuta().getSubTotal());
        biOrden.setObservacionRuta(orden.getKilometrosRuta().getObservacionRuta());

        biOrden.setDesperfecto(orden.getTrabajoFinalEjecutado().getDesperfecto());
        biOrden.setTrabajoRealizado(orden.getTrabajoFinalEjecutado().getTrabajoRealizado());
        biOrden.setObservaciones(orden.getTrabajoFinalEjecutado().getObservaciones());

        biOrden.setSubTotalRepuestos(orden.getSubTotalRepuestos());
        biOrden.setSubTotalServicios(orden.getSubTotalServicios());
        biOrden.setSubTotalKilometraje(orden.getSubTotalKilometraje());

        biOrden.setUsuario_codigo(orden.getUsername().getCodigo());
        biOrden.setUsuario(orden.getUsername().getDatosPersonales().getNombres() + " " + orden.getUsername().getDatosPersonales().getApellidos());

        biOrden.setAbierta_active(orden.getCiclo().getAbierta().getActive());
        biOrden.setAbierta_alias(orden.getCiclo().getAbierta().getAlias());
        biOrden.setAbierta_comentario(orden.getCiclo().getAbierta().getComentario());
        if (orden.getCiclo().getAbierta().getCreationDate() != null) {
            biOrden.setAbierta_dd(convertDate(orden.getCiclo().getAbierta().getCreationDate()).get(0));
            biOrden.setAbierta_mm(convertDate(orden.getCiclo().getAbierta().getCreationDate()).get(1));
            biOrden.setAbierta_yyyy(convertDate(orden.getCiclo().getAbierta().getCreationDate()).get(2));
        }

        biOrden.setCerrada_active(orden.getCiclo().getCerrada().getActive());
        biOrden.setCerrada_alias(orden.getCiclo().getCerrada().getAlias());
        biOrden.setCerrada_comentario(orden.getCiclo().getCerrada().getComentario());
        if (orden.getCiclo().getCerrada().getCreationDate() != null) {
            biOrden.setCerrada_dd(convertDate(orden.getCiclo().getCerrada().getCreationDate()).get(0));
            biOrden.setCerrada_mm(convertDate(orden.getCiclo().getCerrada().getCreationDate()).get(1));
            biOrden.setCerrada_yyyy(convertDate(orden.getCiclo().getCerrada().getCreationDate()).get(2));
        }

        biOrden.setPendiente_active(orden.getCiclo().getPendiente().getActive());
        biOrden.setPendiente_alias(orden.getCiclo().getPendiente().getAlias());
        biOrden.setPendiente_comentario(orden.getCiclo().getPendiente().getComentario());
        if (orden.getCiclo().getPendiente().getCreationDate() != null) {
            biOrden.setPendiente_dd(convertDate(orden.getCiclo().getPendiente().getCreationDate()).get(0));
            biOrden.setPendiente_mm(convertDate(orden.getCiclo().getPendiente().getCreationDate()).get(1));
            biOrden.setPendiente_yyyy(convertDate(orden.getCiclo().getPendiente().getCreationDate()).get(2));
        }

        biOrden.setCancelada_active(orden.getCiclo().getCancelada().getActive());
        biOrden.setCancelada_alias(orden.getCiclo().getCancelada().getAlias());
        biOrden.setCancelada_comentario(orden.getCiclo().getCancelada().getComentario());
        if (orden.getCiclo().getCancelada().getCreationDate() != null) {
            biOrden.setCancelada_dd(convertDate(orden.getCiclo().getCancelada().getCreationDate()).get(0));
            biOrden.setCancelada_mm(convertDate(orden.getCiclo().getCancelada().getCreationDate()).get(1));
            biOrden.setCancelada_yyyy(convertDate(orden.getCiclo().getCancelada().getCreationDate()).get(2));
        }

        if (type == 0) {
            insert(biOrden);
        } else if (type == 1) {
            update(biOrden);
        }
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
