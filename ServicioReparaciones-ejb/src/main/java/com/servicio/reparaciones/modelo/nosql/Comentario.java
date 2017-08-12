/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.mongodb.internal.thread.DaemonThreadFactory;
import java.util.Date;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class Comentario {

    private int index;
    private Date creationDate;
    private String texto;

    @Reference
    private Usuario username;

    public Comentario() {
        this.index = 0;
        this.creationDate = new Date();
        this.texto = "";
        this.username = new Usuario();
    }

    public Comentario(int index, Date creationDate, String texto, Usuario username) {
        this.index = index;
        this.creationDate = creationDate;
        this.texto = texto;
        this.username = username;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Usuario getUsername() {
        return username;
    }

    public void setUsername(Usuario username) {
        this.username = username;
    }
}
