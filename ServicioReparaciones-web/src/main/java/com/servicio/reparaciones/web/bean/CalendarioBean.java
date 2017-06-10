/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Visita;
import com.servicio.reparaciones.servicio.VisitaServicio;
import com.servicio.reparaciones.web.util.SessionUtil;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author luis
 */
@Named(value = "calendarioBean")
@ViewScoped
public class CalendarioBean implements Serializable {

    private static final long serialVersionUID = -6967264736779754900L;

    private GregorianCalendar calenadario;
    private ScheduleModel modelCalendar;
    private Visita selected;
    private List<Visita> visitas;

    @Inject
    private VisitaServicio visitaService;

    @PostConstruct
    public void init() {
        this.calenadario = new GregorianCalendar();
        this.selected = null;
        this.visitas = this.visitaService.ObtenerListaVisitas(1);
        this.modelCalendar = new DefaultScheduleModel();
        this.loadVisitas();
    }

    private void loadVisitas() {
        if (this.visitas != null && !this.visitas.isEmpty()) {
            for (Visita evt : this.visitas) {
                DefaultScheduleEvent visita = new DefaultScheduleEvent();
                visita.setId(evt.getCodigo().toString());
                String title = evt.getUsername().getDatosPersonales().getNombres()
                        + " " + evt.getUsername().getDatosPersonales().getApellidos()
                        + " " + evt.getCliente().getNombres() + " " + evt.getCliente().getApellidos()
                        + " " + evt.getProducto().getArtefacto() + " " + evt.getProducto().getMarca();
                visita.setTitle(title);
                visita.setStartDate(evt.getFechaVisitaCliente());
                visita.setEndDate(getEndDate(evt.getFechaVisitaCliente()));
                visita.setDescription(evt.getPosibleFalla() + " " + evt.getObservacionCliente());
                visita.setData(evt);
                visita.setAllDay(Boolean.FALSE);
                visita.setEditable(Boolean.FALSE);
                String username = SessionUtil.sessionVarAlfanumeric("username");
                if (!username.equals(evt.getUsername().getUsername())) {
                    visita.setStyleClass("color");
                }
                this.modelCalendar.addEvent(visita);
            }
        }
    }

    private Date getEndDate(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, 1);  // numero de horas a añadir, o restar en caso de horas<0
        return calendar.getTime(); // 
    }

    public void onEventSelect(SelectEvent selectEvent) {
        ScheduleEvent event = (ScheduleEvent) selectEvent.getObject();
        if(event != null){
            this.selected = (Visita) event.getData();
            if(this.selected == null){
                this.selected = new Visita();
            }
        }
    }

    public GregorianCalendar getCalenadario() {
        return calenadario;
    }

    public void setCalenadario(GregorianCalendar calenadario) {
        this.calenadario = calenadario;
    }

    public ScheduleModel getModelCalendar() {
        return modelCalendar;
    }

    public void setModelCalendar(ScheduleModel modelCalendar) {
        this.modelCalendar = modelCalendar;
    }

    public Visita getSelected() {
        return selected;
    }

    public void setSelected(Visita selected) {
        this.selected = selected;
    }

}
