/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class Estado {

    private Boolean active;
    private String alias;
    private String comentario;
    
    @Reference
    private Usuario username;

    public Estado() {
        this.active = Boolean.FALSE;
        this.alias = "";
        this.username = new Usuario();
    }

    public Estado(Boolean active, String alias, String comentario, Usuario username) {
        this.active = active;
        this.alias = alias;
        this.comentario = comentario;
        this.username = username;
    }
    
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsername() {
        return username;
    }

    public void setUsername(Usuario username) {
        this.username = username;
    }
}
