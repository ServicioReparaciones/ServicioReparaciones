/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql.dao;

import com.common.dao.DefaultGenericDAOImple;
import com.servicio.reparaciones.modelo.sql.Canton;
import com.servicio.reparaciones.modelo.sql.Provincia;
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
public class ProvinciaDAO extends DefaultGenericDAOImple<Provincia, Integer> {

    public ProvinciaDAO() {
        super(Provincia.class);
    }

    public List<Provincia> ListaProvincias() {
        Query qry = super.getEntityManager().createQuery("SELECT obj FROM Provincia obj");
        List<Provincia> provincias = qry.getResultList();
        if (provincias.isEmpty()) {
            return new ArrayList<Provincia>();
        } else {
            return provincias;
        }
    }

    public Provincia Provincia(String provincia) {
        Query qry = super.getEntityManager().createQuery("SELECT obj FROM Provincia obj WHERE obj.provincia = ?1");
        qry.setParameter(1, provincia);
        List<Provincia> provincias = qry.getResultList();
        if (provincias.isEmpty()) {
            Provincia empty = new Provincia();
            empty.setCantonList(new ArrayList<Canton>());
            return empty;
        } else {
            return provincias.get(0);
        }
    }

}
