/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql.dao;

import com.common.dao.DefaultGenericDAOImple;
import com.servicio.reparaciones.modelo.sql.FacturacionMabe;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class FacturacionMabeDAO extends DefaultGenericDAOImple<FacturacionMabe, String> {

    public FacturacionMabeDAO() {
        super(FacturacionMabe.class);
    }

    public List<FacturacionMabe> ListaFacturacionMabees() {
        Query qry = super.getEntityManager().createNativeQuery("SELECT * FROM FacturacionMabe WHERE FLAG = 1", FacturacionMabe.class);
        List<FacturacionMabe> facturacionMabe = qry.getResultList();
        if (facturacionMabe.isEmpty()) {
            return new ArrayList<FacturacionMabe>();
        } else {
            return facturacionMabe;
        }
    }

}
