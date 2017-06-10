/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.modelo.sql.dao;

import com.common.dao.DefaultGenericDAOImple;
import com.servicio.reparaciones.modelo.sql.Artefacto;
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
public class ArtefactoDAO extends DefaultGenericDAOImple<Artefacto, Integer> {
    
    public ArtefactoDAO() {
        super(Artefacto.class);
    }
    
    public List<Artefacto> ListaArtefactos() {
        Query qry = super.getEntityManager().createQuery("SELECT obj FROM Artefacto obj");
        List<Artefacto> artefactos = qry.getResultList();
        if (artefactos.isEmpty()) {
            return new ArrayList<Artefacto>();
        } else {
            return artefactos;
        }
    }
    
}
