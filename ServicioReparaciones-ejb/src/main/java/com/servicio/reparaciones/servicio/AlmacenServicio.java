/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.sql.Almacen;
import com.servicio.reparaciones.modelo.sql.dao.AlmacenDAO;
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
public class AlmacenServicio implements Serializable{
    
    private static final long serialVersionUID = -4006253193403651431L;
    
    @Inject
    private AlmacenDAO almacenDao;
    
    public List<Almacen> ObtenerListaAlmacenes(){
        return this.almacenDao.ListaAlmacenes();
    }
    
}
