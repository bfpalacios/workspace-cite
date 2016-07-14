package pe.gob.produce.produccion.controlador;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.EventoBO;
import pe.gob.produce.produccion.services.EventoServices;
 
@Controller("scheduleView")
@ViewScoped
public class ScheduleView implements Serializable {
 
    private static final long serialVersionUID = 1L;

	private ScheduleModel eventModel;
     
    private ScheduleModel lazyEventModel;
    
    @Autowired
    private EventoServices eventoService;    
 
    private ScheduleEvent event = new DefaultScheduleEvent();
 
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        List<EventoBO> eventos = null;
        try {
        	eventos = eventoService.listarEventos();
		} catch (Exception e) {
			e.printStackTrace();
		}
        for (EventoBO eventoDB : eventos) {
        	DefaultScheduleEvent evento = new DefaultScheduleEvent(eventoDB.getTitulo(), eventoDB.getFechaInicio(), eventoDB.getFechaFin());
        	evento.setId("" + eventoDB.getId().intValue());
        	System.out.println(eventoDB.getId().intValue());
        	evento.setDescription(eventoDB.getDescripcion());
        	evento.setAllDay((eventoDB.getTodoElDia() == 1)?true:false);
        	eventModel.addEvent(evento);
		} 
    }
     
    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
     
    public void addEvent(ActionEvent actionEvent) {
    	EventoBO  nuevoEvento = new EventoBO();
    	nuevoEvento.setTitulo(event.getTitle());
    	nuevoEvento.setDescripcion(event.getDescription());
    	nuevoEvento.setFechaInicio(event.getStartDate());
    	nuevoEvento.setFechaFin(event.getEndDate());
    	nuevoEvento.setTodoElDia((event.isAllDay()) ? 1 : 0);
        if(event.getId() == null){        	
        	try {
				eventoService.grabarEvento(nuevoEvento);
			} catch (Exception e) {
				e.printStackTrace();
			}
            eventModel.addEvent(event);
        }else{
        	/*
        	nuevoEvento.setId(Integer.parseInt(event.getId()));
        	try {
				eventoService.actualizarEvento(nuevoEvento);;
			} catch (Exception e) {
				e.printStackTrace();
			}
        	*/
            eventModel.updateEvent(event);
        }         
        event = new DefaultScheduleEvent();
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
