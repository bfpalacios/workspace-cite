package pe.gob.produce.produccion.controlador;


import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.produccion.model.PortadaModel;
import pe.gob.produce.produccion.model.ServicioModel;

@Controller("portadaMBean")
@ViewScoped
public class PortadaMBean {
	
	//@Autowired
	//private PortadaModel portadaModel;

	//constructor
		public PortadaMBean(){
			System.out.println("::::: LOADING PortadaMBean ::::::::");
			//portadaModel = new PortadaModel();
		}
		
		public String cargarBienvenida(){
			return "/paginas/ModuloProduccion/cite/portadaPrincipal/bienvenida.xhtml";
		}
		
		public String cargarRedNoticias(){
			return "/paginas/ModuloProduccion/cite/portadaPrincipal/redNoticias.xhtml";
		}
		
		public String cargarCalendarioEventos(){
			return "/paginas/ModuloProduccion/cite/portadaPrincipal/calendarioEventos.xhtml";
		}
		
		public String cargarPublicacionesInformativas(){
			return "/paginas/ModuloProduccion/cite/portadaPrincipal/publicacionesInformativas.xhtml";
		}

				
}
