/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.sql.Marca;
import com.servicio.reparaciones.modelo.sql.dao.MarcaDAO;
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
public class MarcaServicio implements Serializable{
    
    private static final long serialVersionUID = -8726613576395883774L;
    
    @Inject
    private MarcaDAO marcaDao;
    
    public List<Marca> ObtenerListaMarcas(){
        return this.marcaDao.ListaMarcas();
    }
    
}
