/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.sql.Canton;
import com.servicio.reparaciones.modelo.sql.dao.CantonDAO;
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
public class CantonServicio implements Serializable {

    private static final long serialVersionUID = -8600993701026019191L;
    @Inject
    private CantonDAO cantonDao;

    public List<Canton> ObtenerListaCantones() {
        return this.cantonDao.ListaCantones();
    }

    public Canton ObtenerCanton(String canton) {
        return this.cantonDao.Canton(canton);
    }
}
