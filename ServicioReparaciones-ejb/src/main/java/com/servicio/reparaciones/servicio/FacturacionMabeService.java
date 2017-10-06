/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio;

import com.servicio.reparaciones.modelo.sql.FacturacionMabe;
import com.servicio.reparaciones.modelo.sql.dao.FacturacionMabeDAO;
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
public class FacturacionMabeService implements Serializable {

    private static final long serialVersionUID = 9112717667209606571L;
    @Inject
    private FacturacionMabeDAO almacenDao;

    public List<FacturacionMabe> ObtenerListaFacturacionMabees() {
        return this.almacenDao.ListaFacturacionMabees();
    }

}
