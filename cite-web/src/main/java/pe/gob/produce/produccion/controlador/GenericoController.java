package pe.gob.produce.produccion.controlador;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public abstract class GenericoController {

	public static RequestContext getRequestContext(){
    	return RequestContext.getCurrentInstance();
    }
    
    public static FacesContext getFacesContext(){
    	return FacesContext.getCurrentInstance();
    }
    
    public static void mostrarError(String msjResumen) {
        FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, msjResumen,
                "Credenciales no válidas");
        FacesContext.getCurrentInstance().addMessage("", msj);
    }
}
