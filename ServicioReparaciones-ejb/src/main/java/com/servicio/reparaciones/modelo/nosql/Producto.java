/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.servicio.reparaciones.util.entity.BaseEntity;
import java.util.Objects;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "Producto", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Producto extends BaseEntity {

    private Integer codigo;
    private String barcode;
    private String artefacto;
    private String marca;
    private String modelo;
    private String serie;
    private String pnc;
    private String placa;
    private Boolean warranty;
    private Boolean stock;
    private Integer flag;

    @Reference
    private Cliente cliente;
    @Embedded
    private Garantia garantia;
    @Embedded
    private CodesWarranty codesWarranty;
    @Reference
    private Usuario username;

    public Producto() {
        this.placa = "";
        this.pnc = "";
        this.warranty = Boolean.FALSE;
        this.stock = Boolean.FALSE;
        this.garantia = new Garantia();
        this.codesWarranty = new CodesWarranty();
        this.username = new Usuario();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getArtefacto() {
        return artefacto;
    }

    public void setArtefacto(String artefacto) {
        this.artefacto = artefacto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getPnc() {
        return pnc;
    }

    public void setPnc(String pnc) {
        this.pnc = pnc;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Boolean getWarranty() {
        return warranty;
    }

    public void setWarranty(Boolean warranty) {
        this.warranty = warranty;
    }

    public Boolean getStock() {
        return stock;
    }

    public void setStock(Boolean stock) {
        this.stock = stock;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Garantia getGarantia() {
        return garantia;
    }

    public void setGarantia(Garantia garantia) {
        this.garantia = garantia;
    }

    public CodesWarranty getCodesWarranty() {
        return codesWarranty;
    }

    public void setCodesWarranty(CodesWarranty codesWarranty) {
        this.codesWarranty = codesWarranty;
    }

    public Usuario getUsername() {
        return username;
    }

    public void setUsername(Usuario username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.codigo);
        hash = 83 * hash + Objects.hashCode(this.barcode);
        hash = 83 * hash + Objects.hashCode(this.serie);
        hash = 83 * hash + Objects.hashCode(this.pnc);
        hash = 83 * hash + Objects.hashCode(this.placa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (!Objects.equals(this.barcode, other.barcode)) {
            return false;
        }
        if (!Objects.equals(this.serie, other.serie)) {
            return false;
        }
        if (!Objects.equals(this.pnc, other.pnc)) {
            return false;
        }
        if (!Objects.equals(this.placa, other.placa)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Producto{" + "codigo=" + codigo + ", barcode=" + barcode + ", artefacto=" + artefacto + ", marca=" + marca + ", modelo=" + modelo + ", serie=" + serie + ", pnc=" + pnc + ", placa=" + placa + ", warranty=" + warranty + ", flag=" + flag + ", cliente=" + cliente + ", garantia=" + garantia + ", codesWarranty=" + codesWarranty + ", username=" + username + '}';
    }
    
}
