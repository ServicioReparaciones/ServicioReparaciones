/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Repuesto;
import com.servicio.reparaciones.servicio.RepuestoServicio;
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
@FacesConverter("repuestoConverter")
public class RepuestoConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(RepuestoConverter.class.getName());

    @Inject
    private RepuestoServicio repuestoService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0 && !value.equals("Seleccionar")) {
            LOG.log(Level.INFO, "RepuestoConverter >>" + value, value);
            try {
                return this.repuestoService.findByCodigo(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            LOG.log(Level.INFO, "RepuestoConverter  NULL", value);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LOG.log(Level.INFO, "RepuestoConverter >>" + value.toString(), value);
        if (value != null && !value.toString().equals("") && !value.equals("Seleccionar")) {
            return String.valueOf(((Repuesto) value).getCodigo());
        } else {
            LOG.log(Level.INFO, "RepuestoConverter  NULL", value);
            return null;
        }
    }

}
