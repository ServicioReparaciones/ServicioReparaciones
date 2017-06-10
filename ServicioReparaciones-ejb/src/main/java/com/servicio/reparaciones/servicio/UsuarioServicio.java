/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.mongo.persistance.MongoPersistence;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.servicio.Interfaz.Iusuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.codec.digest.DigestUtils;
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
public class UsuarioServicio implements Iusuario, Serializable {

    private static final long serialVersionUID = 3682042499879383913L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    @Override
    public Integer generatedCodigo() {
        Integer size = ObtenerListaUsuarios().size();
        Integer code = 1000 + 1 * size;
        return code;
    }

    @Override
    public Boolean insert(Usuario usuario) {
        Boolean exito = Boolean.FALSE;
        if (findByUsername(usuario).getCodigo() == null
                && usuario != null) {
            usuario.setCodigo(generatedCodigo());
            usuario.setPassword(DigestUtils.md5Hex(usuario.getPassword()));
            usuario.setFlag(1);
            this.ds.save(usuario);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    @Override
    public Boolean update(Usuario usuario) {
        Query<Usuario> query = this.ds.createQuery(Usuario.class);
        query.and(
                query.criteria("codigo").equal(usuario.getCodigo())
        );
        UpdateOperations<Usuario> update = this.ds.createUpdateOperations(Usuario.class);
        update.set("username", usuario.getUsername()).
                set("password", usuario.getPassword()).
                set("estado", usuario.getEstado()).
                set("admin", usuario.getAdmin()).
                set("datosPersonales", usuario.getDatosPersonales()).
                set("flag", usuario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean updatePassword(Usuario usuario) {
        Query<Usuario> query = this.ds.createQuery(Usuario.class);
        usuario.setPassword(DigestUtils.md5Hex(usuario.getPassword()));
        query.and(
                query.criteria("codigo").equal(usuario.getCodigo())
        );
        UpdateOperations<Usuario> update = this.ds.createUpdateOperations(Usuario.class);
        update.set("password", usuario.getPassword());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean existsUsername(Usuario usuario) {
        Boolean find = Boolean.FALSE;
        Usuario user = this.findByUsername(usuario);
        if (user.getId() != null) {
            find = Boolean.TRUE;
        }
        return find;
    }

    public Boolean stateUsername(Usuario usuario) {
        Boolean state = Boolean.FALSE;
        Usuario user = this.findByUsername(usuario);
        if (user.getId() != null) {
            state = user.getEstado();
        }
        return state;
    }

    public Boolean checkPassword(Usuario usuario) {
        Boolean password = Boolean.FALSE;
        Usuario user = this.findByUsername(usuario);
        if (user.getId() != null) {
            String inputPasswordUser = DigestUtils.md5Hex(usuario.getPassword());
            if (user.getPassword().equals(inputPasswordUser)) {
                password = Boolean.TRUE;
            }
        }
        return password;
    }

    @Override
    public Usuario findByCodigo(Usuario usuario) {
        Usuario find = new Usuario();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("codigo").equal(usuario.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Usuario findByUsername(Usuario usuario) {
        Usuario find = new Usuario();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("username").equal(usuario.getUsername()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Usuario findByEmail(Usuario usuario) {
        Usuario find = new Usuario();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("datosPersonales.email").equal(usuario.getDatosPersonales().getEmail()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    @Override
    public void delete(Usuario usuario) {
        this.ds.delete(usuario);
    }

    @Override
    public Boolean deleteFlag(Usuario usuario) {
        usuario.setFlag(0);
        Query<Usuario> query = this.ds.createQuery(Usuario.class);
        query.and(
                query.criteria("codigo").equal(usuario.getCodigo())
        );
        UpdateOperations<Usuario> update = this.ds.createUpdateOperations(Usuario.class);
        update.set("flag", usuario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    @Override
    public List<Usuario> ObtenerListaUsuarios(Integer flag) {
        List<Usuario> list = new ArrayList<>();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
    
    public List<Usuario> ObtenerListaUsuarios() {
        List<Usuario> list = new ArrayList<>();
        Query<Usuario> result = this.ds.find(Usuario.class);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

}
