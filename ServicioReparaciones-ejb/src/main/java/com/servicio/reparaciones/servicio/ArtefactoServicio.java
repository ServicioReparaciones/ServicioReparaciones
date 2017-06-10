/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.sql.Artefacto;
import com.servicio.reparaciones.modelo.sql.dao.ArtefactoDAO;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class ArtefactoServicio implements Serializable{
    
    private static final long serialVersionUID = -5997488575285629444L;
    
    @Inject
    private ArtefactoDAO artefactoDao;
    
    public List<Artefacto> ObtenerListaArtefactos(){
        return this.artefactoDao.ListaArtefactos();
    }
    
}
