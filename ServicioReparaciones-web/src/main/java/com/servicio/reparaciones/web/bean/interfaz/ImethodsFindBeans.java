/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean.interfaz;

import com.servicio.reparaciones.modelo.nosql.Orden;
import java.util.List;

/**
 *
 * @author luis
 */
public interface ImethodsFindBeans {

    public List<Orden> findByCedula();

    public List<Orden> findByTelefono();

    public List<Orden> findByMovil();

    public List<Orden> findBySerie();

    public List<Orden> findByPNC();

    public List<Orden> findByPlaca();

    public List<Orden> findByCodigoORD();

    public List<Orden> findByNuemeroORD();

    public List<Orden> findByNuemeroTicket();
}
