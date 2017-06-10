/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql.dao;

import com.common.dao.DefaultGenericDAOImple;
import com.servicio.reparaciones.modelo.sql.Parroquia;
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
public class ParroquiaDAO extends DefaultGenericDAOImple<Parroquia, Integer> {
    
    public ParroquiaDAO() {
        super(Parroquia.class);
    }
    
    public List<Parroquia> ListaParroquias() {
        Query qry = super.getEntityManager().createQuery("SELECT obj FROM Parroquia obj");
        List<Parroquia> parroquias = qry.getResultList();
        if (parroquias.isEmpty()) {
            return null;
        } else {
            return parroquias;
        }
    }
    
}
