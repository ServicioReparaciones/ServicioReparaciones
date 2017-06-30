/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "Inventario", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Inventario {
    
    private Integer codigo;
    private Entrada entrada;
    private Salida  salida;
    private Double stockUND; //total actual en unidades
    private Double stockUSD; //total actual en dinero
    private Integer flag;

    @Reference
    private Articulo articulo;
    @Reference
    private Usuario username;
    
    public Inventario() {
        this.stockUND = 0.00;
        this.stockUSD = 0.00;
        this.articulo = new Articulo();
        this.username = new Usuario();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Salida getSalida() {
        return salida;
    }

    public void setSalida(Salida salida) {
        this.salida = salida;
    }

    public Double getStockUND() {
        return stockUND;
    }

    public void setStockUND(Double stockUND) {
        this.stockUND = stockUND;
    }

    public Double getStockUSD() {
        return stockUSD;
    }

    public void setStockUSD(Double stockUSD) {
        this.stockUSD = stockUSD;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Usuario getUsername() {
        return username;
    }

    public void setUsername(Usuario username) {
        this.username = username;
    }
    
    
}
