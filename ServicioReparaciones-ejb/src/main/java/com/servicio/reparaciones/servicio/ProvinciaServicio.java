/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.sql.Provincia;
import com.servicio.reparaciones.modelo.sql.dao.ProvinciaDAO;
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
public class ProvinciaServicio implements Serializable{
    
    private static final long serialVersionUID = -3089167629416131155L;   
    @Inject
    private ProvinciaDAO provinciaDao;
    
    public List<Provincia> ObtenerListaProvincias(){
        return this.provinciaDao.ListaProvincias();
    }
    
    public Provincia ObtenerProvincia(String provincia){
        return this.provinciaDao.Provincia(provincia);
    }
    
}
