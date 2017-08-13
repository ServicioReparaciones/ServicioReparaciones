/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.xml.servidor;

import com.servicio.reparaciones.modelo.nosql.Salida;
import com.servicio.reparaciones.xml.modelo.SalidaXml;
import com.servicio.reparaciones.xml.servidor.i.IsalidaServidorXml;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class SalidaServidorXml implements IsalidaServidorXml, Serializable {

    private static final long serialVersionUID = -4766890267586729633L;

    private static final Logger log = Logger.getLogger(SalidaServidorXml.class.getName());

    @Override
    public void generatedXML(String barcode, String url, String filename, Salida salida) {

        Path path = Paths.get(url);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                log.log(Level.INFO, "Directory is created!");
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to create directory! " + e.getMessage());
            }
        }

        File fileDelete = new File(url + filename + "." + "xml");
        if (fileDelete.delete()) {
            log.log(Level.INFO, "Se elimino el archivo: " + url + filename + "." + "xml");
        } else {
            log.log(Level.INFO, "No se elimino el archivo: " + url + filename + "." + "xml");
        }
        
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        SalidaXml xmlSalida = new SalidaXml();
        
        createXML(url, filename + ".xml", xmlSalida);
    }

    @Override
    public void createXML(String url, String filename, SalidaXml salidaXml) {
        try {

            File file = new File(url + filename);
            JAXBContext jaxbContext = JAXBContext.newInstance(SalidaXml.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");

            jaxbMarshaller.marshal(salidaXml, file);
            jaxbMarshaller.marshal(salidaXml, System.out);

        } catch (JAXBException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

}
