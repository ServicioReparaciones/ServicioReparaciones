/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.xml.servidor.i;

import com.servicio.reparaciones.modelo.nosql.Salida;
import com.servicio.reparaciones.xml.modelo.SalidaXml;

/**
 *
 * @author luis
 */
public interface IsalidaServidorXml {

    public void generatedXML(String barcode, String url, String filename, Salida salida);

    public void createXML(String url, String filename, SalidaXml salidaXml);
}
