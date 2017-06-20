/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.xml.servidor;

import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.xml.modelo.ClienteXml;
import com.servicio.reparaciones.xml.modelo.OrdenXml;
import com.servicio.reparaciones.xml.modelo.ProductoXml;
import com.servicio.reparaciones.xml.modelo.VisitaXml;
import com.servicio.reparaciones.xml.servidor.interfaz.IordenServidorXml;
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
public class OrdenServidorXml implements IordenServidorXml, Serializable {

    private static final long serialVersionUID = -4766890267586729633L;

    private static final Logger log = Logger.getLogger(OrdenServidorXml.class.getName());

    @Override
    public void generatedXML(String barcode, String url, String filename, Orden orden) {

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
        OrdenXml xmlOrden = new OrdenXml();
        xmlOrden.setCreationDate(format.format(orden.getCreationDate()));
        xmlOrden.setCodigo(orden.getCodigo().toString());
        xmlOrden.setBarcode(orden.getBarcode());
        xmlOrden.setNumeroOrden(orden.getNumeroOrden());
        xmlOrden.setNumeroTicket(orden.getNumeroTicket());
        xmlOrden.setUrl(orden.getUrl());
        VisitaXml xmlVista = new VisitaXml();
        xmlVista.setFechaVisitaCliente(format.format(orden.getVisita().getFechaVisitaCliente()));
        xmlVista.setPosibleFalla(orden.getVisita().getPosibleFalla());
        xmlVista.setObservacionCliente(orden.getVisita().getObservacionCliente());
        xmlVista.setServicio(orden.getVisita().getServicio().toString());
        ClienteXml xmlCliente = new ClienteXml();
        xmlCliente.setApellidos(orden.getCliente().getApellidos());
        xmlCliente.setNombres(orden.getCliente().getNombres());
        xmlCliente.setCedula(orden.getCliente().getCedula());
        xmlCliente.setTelefono(orden.getCliente().getTelefono());
        xmlCliente.setMovil(orden.getCliente().getMovil());
        xmlCliente.setProvincia(orden.getCliente().getProvincia());
        xmlCliente.setCanton(orden.getCliente().getCanton());
        xmlCliente.setParroquia(orden.getCliente().getParroquia());
        xmlCliente.setDireccion(orden.getCliente().getDireccion());
        xmlCliente.setReferencia(orden.getCliente().getReferencia());
        ProductoXml xmlProducto = new ProductoXml();
        xmlProducto.setArtefacto(orden.getProducto().getArtefacto());
        xmlProducto.setMarca(orden.getProducto().getMarca());
        xmlProducto.setModelo(orden.getProducto().getModelo());
        xmlProducto.setSerie(orden.getProducto().getSerie());
        
        xmlOrden.setVisita(xmlVista);
        xmlOrden.setCliente(xmlCliente);
        xmlOrden.setProducto(xmlProducto);
        
        createXML(url, filename + ".xml", xmlOrden);
    }

    @Override
    public void createXML(String url, String filename, OrdenXml ordenXml) {
        try {

            File file = new File(url + filename);
            JAXBContext jaxbContext = JAXBContext.newInstance(OrdenXml.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");

            jaxbMarshaller.marshal(ordenXml, file);
            jaxbMarshaller.marshal(ordenXml, System.out);

        } catch (JAXBException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

}
