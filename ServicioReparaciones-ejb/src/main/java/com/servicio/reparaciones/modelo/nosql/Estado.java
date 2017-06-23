/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import java.util.Date;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis prueba cambio de datos embedded
 */
@Embedded
public class Estado {

    private Boolean active;
    private String alias;
    private String comentario;
    private Date creationDate;
    
    @Reference
    private Usuario username;

    public Estado() {
        this.active = Boolean.FALSE;
        this.alias = "";
        this.username = new Usuario();
        this.creationDate = new Date();
    }

    public Estado(Boolean active, String alias, String comentario, Usuario username) {
        this.active = active;
        this.alias = alias;
        this.comentario = comentario;
        this.username = username;
        this.creationDate = new Date();
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
