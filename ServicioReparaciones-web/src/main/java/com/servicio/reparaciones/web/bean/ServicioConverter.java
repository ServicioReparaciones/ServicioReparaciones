/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Servicio;
import com.servicio.reparaciones.servicio.ServicioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@FacesConverter("servicioConverter")
public class ServicioConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(ServicioConverter.class.getName());

    @Inject
    private ServicioServicio servicioService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        LOG.log(Level.INFO, "ServicioConverter >>" + value, value);
        if (value != null && value.trim().length() > 0 && !value.equals("Seleccionar")) {
            try {
                return this.servicioService.findByCodigo(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            LOG.log(Level.INFO, "ServicioConverter  NULL", value);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LOG.log(Level.INFO, "ServicioConverter >>" + value.toString(), value);
        if (value != null && !value.toString().equals("") && !value.equals("Seleccionar")) {
            return String.valueOf(((Servicio) value).getCodigo());
        } else {
            LOG.log(Level.INFO, "ServicioConverter  NULL", value);
            return null;
        }
    }

}
