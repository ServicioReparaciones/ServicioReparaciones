/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean.util;

import com.servicio.reparaciones.pdf.util.HTMLtoPDF;
import com.servicio.reparaciones.pdf.util.XMLtoHtml;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 *
 * @author luis
 */
public class GeneratedHtml extends Thread implements Serializable {

    private static final Logger LOG = Logger.getLogger(GeneratedHtml.class.getName());

    String direccionDestino;
    String direccionDestinoXml;
    String direccionDestinoHtml;
    String direccionDestinoPdf;
    String barcode;
    int Type;
    private Boolean exito;

    @Inject
    private XMLtoHtml xmlToHtmlServer;
    @Inject
    private HTMLtoPDF htmlToPDFServer;

    public GeneratedHtml(String direccionDestino, String direccionDestinoXml, String direccionDestinoHtml, String direccionDestinoPdf, String barcode, int Type) {
        this.direccionDestino = direccionDestino;
        this.direccionDestinoXml = direccionDestinoXml;
        this.direccionDestinoHtml = direccionDestinoHtml;
        this.direccionDestinoPdf = direccionDestinoPdf;
        this.barcode = barcode;
        this.Type = Type;
        this.xmlToHtmlServer = new XMLtoHtml();
        this.htmlToPDFServer = new HTMLtoPDF();
    }

    @Override
    public void run() {

        try {
            Boolean html = this.xmlToHtmlServer.createHTML(direccionDestino, direccionDestinoHtml, direccionDestinoXml, barcode, Type);
            if (html) {
                this.exito = Boolean.TRUE;
            }

        } catch (IOException ex) {
            this.exito = Boolean.FALSE;
            LOG.log(Level.SEVERE, ex.getMessage());
        } catch (TransformerFactoryConfigurationError ex) {
            this.exito = Boolean.FALSE;
            LOG.log(Level.SEVERE, ex.getMessage());
        } catch (TransformerException ex) {
            this.exito = Boolean.FALSE;
            LOG.log(Level.SEVERE, ex.getMessage());
        }

        if (!this.exito) {
            this.interrupt();
        }
    }

    public Boolean getExito() {
        return exito;
    }

    public void setExito(Boolean exito) {
        this.exito = exito;
    }
}
