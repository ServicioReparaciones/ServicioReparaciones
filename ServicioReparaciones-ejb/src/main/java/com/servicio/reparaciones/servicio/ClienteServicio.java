/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Cliente;
import com.servicio.reparaciones.servicio.I.Icliente;
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
public class ClienteServicio implements Icliente, Serializable {

    private static final long serialVersionUID = -2170910774582687218L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    private Calendario calendario = new Calendario();

    @Override
    public Integer generatedCodigo() {
        Integer size = count();
        Integer code = 1000 + 1 * size;
        return code;
    }

    @Override
    public Boolean insert(Cliente cliente) {
        Boolean exito = Boolean.FALSE;
        if (findByCedula(cliente).getCodigo() == null
                && findByTelefono(cliente).getCodigo() == null
                && findByMovil(cliente).getCodigo() == null
                && cliente != null) {
            cliente.setCodigo(generatedCodigo());
            cliente.setFlag(1);
            this.ds.save(cliente);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Cliente cliente) {
        Query<Cliente> query = this.ds.createQuery(Cliente.class);
        query.and(
                query.criteria("codigo").equal(cliente.getCodigo())
        );
        UpdateOperations<Cliente> update = this.ds.createUpdateOperations(Cliente.class);
        update.set("apellidos", cliente.getApellidos()).
                set("nombres", cliente.getNombres()).
                set("cedula", cliente.getCedula()).
                set("telefono", cliente.getTelefono()).
                set("movil", cliente.getMovil()).
                set("provincia", cliente.getProvincia()).
                set("canton", cliente.getCanton()).
                set("parroquia", cliente.getParroquia()).
                set("direccion", cliente.getDireccion()).
                set("referencia", cliente.getReferencia()).
                set("username", cliente.getUsername()).
                set("lastChange", this.calendario.getCalendario().getTime()).
                set("flag", cliente.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public Cliente findByCodigo(Cliente cliente) {
        Cliente find = new Cliente();
        Query<Cliente> result = this.ds.find(Cliente.class).
                field("codigo").equal(cliente.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Cliente findByCedula(Cliente cliente) {
        Cliente find = new Cliente();
        if (!cliente.getCedula().equals("0000000000")) {
            Query<Cliente> result = this.ds.find(Cliente.class).
                    field("cedula").equal(cliente.getCedula()).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList().get(0);
            }
        }
        return find;
    }

    public Cliente findByTelefono(Cliente cliente) {
        Cliente find = new Cliente();
        if (!cliente.getTelefono().equals("999999999")) {
            Query<Cliente> result = this.ds.find(Cliente.class).
                    field("telefono").equal(cliente.getTelefono()).
                    field("flag").equal(1);
            if (result.asList() != null && !result.asList().isEmpty()) {
                find = result.asList().get(0);
            }
        }
        return find;
    }

    public Cliente findByMovil(Cliente cliente) {
        Cliente find = new Cliente();
        Query<Cliente> result = this.ds.find(Cliente.class).
                field("movil").equal(cliente.getMovil()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public void delete(Cliente cliente) {
        this.ds.delete(cliente);
    }

    @Override
    public Boolean deleteFlag(Cliente cliente) {
        cliente.setFlag(0);
        Query<Cliente> query = this.ds.createQuery(Cliente.class);
        query.and(
                query.criteria("codigo").equal(cliente.getCodigo())
        );
        UpdateOperations<Cliente> update = this.ds.createUpdateOperations(Cliente.class);
        update.set("flag", cliente.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Cliente> ObtenerListaClientes(Integer flag) {
        List<Cliente> list = new ArrayList<>();
        Query<Cliente> result = this.ds.find(Cliente.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<Cliente> ObtenerListaClientes() {
        List<Cliente> list = new ArrayList<>();
        Query<Cliente> result = this.ds.find(Cliente.class);
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
        Long result = this.ds.find(Cliente.class).count();
        count = new Integer(result.intValue());
        return count;
    }

    @Override
    public Integer count(Integer flag) {
        Integer count = 0;
        Long result = this.ds.find(Cliente.class).field("flag").equal(flag).count();
        count = new Integer(result.intValue());
        return count;
    }

}
