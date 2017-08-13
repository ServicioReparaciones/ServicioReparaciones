/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.xml.servidor.i;

import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.xml.modelo.OrdenXml;

/**
 *
 * @author luis
 */
public interface IordenServidorXml {

    public void generatedXML(String barcode, String url, String filename, Orden orden);

    public void createXML(String url, String filename, OrdenXml ordenXml);
}
