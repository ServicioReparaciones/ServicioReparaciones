/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql.dao;

import com.common.dao.DefaultGenericDAOImple;
import com.servicio.reparaciones.modelo.sql.Marca;
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
public class MarcaDAO extends DefaultGenericDAOImple<Marca, Integer> {
    
    public MarcaDAO() {
        super(Marca.class);
    }
    
    public List<Marca> ListaMarcas() {
        Query qry = super.getEntityManager().createQuery("SELECT obj FROM Marca obj");
        List<Marca> marcas = qry.getResultList();
        if (marcas.isEmpty()) {
            return null;
        } else {
            return marcas;
        }
    }
    
}
