package pe.gob.produce.produccion.controlador;


import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Controller;

@Controller("portadaMBean")
@ViewScoped
public class PortadaMBean {

	//constructor
		public PortadaMBean(){
			System.out.println("::::: LOADING PortadaMBean ::::::::");
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
