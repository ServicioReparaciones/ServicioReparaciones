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
@Entity(value = "BiMabeRepuesto", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class BiMabeRepuesto extends BaseEntity {

    private Integer codigo;
    private String ORDEN_DE_SERVICIO_PADRE;
    private String CODIGO_REPUESTO;
    private String DESCRIPCION;
    private String CANTIDAD;
    private String CAUSA_DE_DEVOLUCION;
    private String MODELO_EXACTO;
    private String SERIE;
    private String COMENTARIO;

    public BiMabeRepuesto() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getORDEN_DE_SERVICIO_PADRE() {
        return ORDEN_DE_SERVICIO_PADRE;
    }

    public void setORDEN_DE_SERVICIO_PADRE(String ORDEN_DE_SERVICIO_PADRE) {
        this.ORDEN_DE_SERVICIO_PADRE = ORDEN_DE_SERVICIO_PADRE;
    }

    public String getCODIGO_REPUESTO() {
        return CODIGO_REPUESTO;
    }

    public void setCODIGO_REPUESTO(String CODIGO_REPUESTO) {
        this.CODIGO_REPUESTO = CODIGO_REPUESTO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public String getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(String CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public String getCAUSA_DE_DEVOLUCION() {
        return CAUSA_DE_DEVOLUCION;
    }

    public void setCAUSA_DE_DEVOLUCION(String CAUSA_DE_DEVOLUCION) {
        this.CAUSA_DE_DEVOLUCION = CAUSA_DE_DEVOLUCION;
    }

    public String getMODELO_EXACTO() {
        return MODELO_EXACTO;
    }

    public void setMODELO_EXACTO(String MODELO_EXACTO) {
        this.MODELO_EXACTO = MODELO_EXACTO;
    }

    public String getSERIE() {
        return SERIE;
    }

    public void setSERIE(String SERIE) {
        this.SERIE = SERIE;
    }

    public String getCOMENTARIO() {
        return COMENTARIO;
    }

    public void setCOMENTARIO(String COMENTARIO) {
        this.COMENTARIO = COMENTARIO;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.codigo);
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
        final BiMabeRepuesto other = (BiMabeRepuesto) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BiMabeRepuesto{" + "codigo=" + codigo + ", ORDEN_DE_SERVICIO_PADRE=" + ORDEN_DE_SERVICIO_PADRE + ", CODIGO_REPUESTO=" + CODIGO_REPUESTO + ", DESCRIPCION=" + DESCRIPCION + ", CANTIDAD=" + CANTIDAD + ", CAUSA_DE_DEVOLUCION=" + CAUSA_DE_DEVOLUCION + ", MODELO_EXACTO=" + MODELO_EXACTO + ", SERIE=" + SERIE + ", COMENTARIO=" + COMENTARIO + '}';
    }
}
