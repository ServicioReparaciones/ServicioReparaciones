/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql.dao;

import com.common.dao.DefaultGenericDAOImple;
import com.servicio.reparaciones.modelo.sql.Almacen;
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
public class AlmacenDAO extends DefaultGenericDAOImple<Almacen, Integer> {
    
    public AlmacenDAO() {
        super(Almacen.class);
    }
    
    public List<Almacen> ListaAlmacenes() {
        Query qry = super.getEntityManager().createQuery("SELECT obj FROM Almacen obj");
        List<Almacen> almacenes = qry.getResultList();
        if (almacenes.isEmpty()) {
            return new ArrayList<Almacen>();
        } else {
            return almacenes;
        }
    }
}
