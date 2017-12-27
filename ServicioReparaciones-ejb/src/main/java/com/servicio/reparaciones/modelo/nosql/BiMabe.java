/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.nosql;

import com.servicio.reparaciones.util.entity.BaseEntity;
import java.util.Objects;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/**
 *
 * @author luis
 */
@Entity(value = "BiMabe", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class BiMabe extends BaseEntity {

    private Integer codigo;
    private String INTERLOCUTOR_COMERCIAL;
    private String TALLER_AUTORIZADO;
    private String CIUDAD;
    private String LIQUIDACION;
    private String ORDEN_DE_SERVICIO_PADRE;
    private String ORDEN_DE_SERVICIO_HIJA;
    private String NOTIFICACION;
    private Integer CANTIDAD;
    private Double VALOR_FIJO;
    private Double VALOR_EXTRA;
    private Double VALOR_KM;
    private Double VALOR_PEAJES;
    private Double VALOR_ADICIONAL;
    private String MODELO;
    private String SERIE;
    private String FECHA_DE_COMPRA;
    private String CODIGO_DE_FALLA;
    private String CON_RESPUESTO;
    private String SIN_RESPUESTO;

    public BiMabe() {
        this.INTERLOCUTOR_COMERCIAL = "-";
        this.TALLER_AUTORIZADO = "-";
        //CIUDAD
        this.LIQUIDACION = "-";
        //ORDEN DE SERVICIO PADRE
        this.ORDEN_DE_SERVICIO_HIJA = "-";
        //NOTIFICACION
        this.CANTIDAD = 1;
        //VALOR FIJO
        this.VALOR_EXTRA = 0.0;
        this.VALOR_KM = 0.0;
        this.VALOR_PEAJES = 0.0;
        this.VALOR_ADICIONAL = 0.0;
        //MODELO
        //SERIE
        //FECHA DE COMPRA
        //CODIGO DE FALLA
        this.CON_RESPUESTO = "";
        this.SIN_RESPUESTO = "";
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getINTERLOCUTOR_COMERCIAL() {
        return INTERLOCUTOR_COMERCIAL;
    }

    public void setINTERLOCUTOR_COMERCIAL(String INTERLOCUTOR_COMERCIAL) {
        this.INTERLOCUTOR_COMERCIAL = INTERLOCUTOR_COMERCIAL;
    }

    public String getTALLER_AUTORIZADO() {
        return TALLER_AUTORIZADO;
    }

    public void setTALLER_AUTORIZADO(String TALLER_AUTORIZADO) {
        this.TALLER_AUTORIZADO = TALLER_AUTORIZADO;
    }

    public String getCIUDAD() {
        return CIUDAD;
    }

    public void setCIUDAD(String CIUDAD) {
        this.CIUDAD = CIUDAD;
    }

    public String getLIQUIDACION() {
        return LIQUIDACION;
    }

    public void setLIQUIDACION(String LIQUIDACION) {
        this.LIQUIDACION = LIQUIDACION;
    }

    public String getORDEN_DE_SERVICIO_PADRE() {
        return ORDEN_DE_SERVICIO_PADRE;
    }

    public void setORDEN_DE_SERVICIO_PADRE(String ORDEN_DE_SERVICIO_PADRE) {
        this.ORDEN_DE_SERVICIO_PADRE = ORDEN_DE_SERVICIO_PADRE;
    }

    public String getORDEN_DE_SERVICIO_HIJA() {
        return ORDEN_DE_SERVICIO_HIJA;
    }

    public void setORDEN_DE_SERVICIO_HIJA(String ORDEN_DE_SERVICIO_HIJA) {
        this.ORDEN_DE_SERVICIO_HIJA = ORDEN_DE_SERVICIO_HIJA;
    }

    public String getNOTIFICACION() {
        return NOTIFICACION;
    }

    public void setNOTIFICACION(String NOTIFICACION) {
        this.NOTIFICACION = NOTIFICACION;
    }

    public Integer getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(Integer CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public Double getVALOR_FIJO() {
        return VALOR_FIJO;
    }

    public void setVALOR_FIJO(Double VALOR_FIJO) {
        this.VALOR_FIJO = VALOR_FIJO;
    }

    public Double getVALOR_EXTRA() {
        return VALOR_EXTRA;
    }

    public void setVALOR_EXTRA(Double VALOR_EXTRA) {
        this.VALOR_EXTRA = VALOR_EXTRA;
    }

    public Double getVALOR_KM() {
        return VALOR_KM;
    }

    public void setVALOR_KM(Double VALOR_KM) {
        this.VALOR_KM = VALOR_KM;
    }

    public Double getVALOR_PEAJES() {
        return VALOR_PEAJES;
    }

    public void setVALOR_PEAJES(Double VALOR_PEAJES) {
        this.VALOR_PEAJES = VALOR_PEAJES;
    }

    public Double getVALOR_ADICIONAL() {
        return VALOR_ADICIONAL;
    }

    public void setVALOR_ADICIONAL(Double VALOR_ADICIONAL) {
        this.VALOR_ADICIONAL = VALOR_ADICIONAL;
    }

    public String getMODELO() {
        return MODELO;
    }

    public void setMODELO(String MODELO) {
        this.MODELO = MODELO;
    }

    public String getSERIE() {
        return SERIE;
    }

    public void setSERIE(String SERIE) {
        this.SERIE = SERIE;
    }

    public String getFECHA_DE_COMPRA() {
        return FECHA_DE_COMPRA;
    }

    public void setFECHA_DE_COMPRA(String FECHA_DE_COMPRA) {
        this.FECHA_DE_COMPRA = FECHA_DE_COMPRA;
    }

    public String getCODIGO_DE_FALLA() {
        return CODIGO_DE_FALLA;
    }

    public void setCODIGO_DE_FALLA(String CODIGO_DE_FALLA) {
        this.CODIGO_DE_FALLA = CODIGO_DE_FALLA;
    }

    public String getCON_RESPUESTO() {
        return CON_RESPUESTO;
    }

    public void setCON_RESPUESTO(String CON_RESPUESTO) {
        this.CON_RESPUESTO = CON_RESPUESTO;
    }

    public String getSIN_RESPUESTO() {
        return SIN_RESPUESTO;
    }

    public void setSIN_RESPUESTO(String SIN_RESPUESTO) {
        this.SIN_RESPUESTO = SIN_RESPUESTO;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.codigo);
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
        final BiMabe other = (BiMabe) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BiMabe{" + "codigo=" + codigo + ", INTERLOCUTOR_COMERCIAL=" + INTERLOCUTOR_COMERCIAL + ", TALLER_AUTORIZADO=" + TALLER_AUTORIZADO + ", CIUDAD=" + CIUDAD + ", LIQUIDACION=" + LIQUIDACION + ", ORDEN_DE_SERVICIO_PADRE=" + ORDEN_DE_SERVICIO_PADRE + ", ORDEN_DE_SERVICIO_HIJA=" + ORDEN_DE_SERVICIO_HIJA + ", NOTIFICACION=" + NOTIFICACION + ", CANTIDAD=" + CANTIDAD + ", VALOR_FIJO=" + VALOR_FIJO + ", VALOR_EXTRA=" + VALOR_EXTRA + ", VALOR_KM=" + VALOR_KM + ", VALOR_PEAJES=" + VALOR_PEAJES + ", VALOR_ADICIONAL=" + VALOR_ADICIONAL + ", MODELO=" + MODELO + ", SERIE=" + SERIE + ", FECHA_DE_COMPRA=" + FECHA_DE_COMPRA + ", CODIGO_DE_FALLA=" + CODIGO_DE_FALLA + ", CON_RESPUESTO=" + CON_RESPUESTO + ", SIN_RESPUESTO=" + SIN_RESPUESTO + '}';
    }

}
