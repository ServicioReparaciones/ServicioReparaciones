/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql.dao;

import com.common.dao.DefaultGenericDAOImple;
import com.servicio.reparaciones.modelo.sql.Canton;
import com.servicio.reparaciones.modelo.sql.Parroquia;
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
public class CantonDAO extends DefaultGenericDAOImple<Canton, Integer> {

    public CantonDAO() {
        super(Canton.class);
    }

    public List<Canton> ListaCantones() {
        Query qry = super.getEntityManager().createQuery("SELECT obj FROM Canton obj");
        List<Canton> cantones = qry.getResultList();
        if (cantones.isEmpty()) {
            return new ArrayList<Canton>();
        } else {
            return cantones;
        }
    }

    public Canton Canton(String canton) {
        Query qry = super.getEntityManager().createQuery("SELECT obj FROM Canton obj WHERE obj.canton = ?1");
        qry.setParameter(1, canton);
        List<Canton> cantons = qry.getResultList();
        if (cantons.isEmpty()) {
            Canton empty = new Canton();
            empty.setParroquiaList(new ArrayList<Parroquia>());
            return empty;
        } else {
            return cantons.get(0);
        }
    }

}
