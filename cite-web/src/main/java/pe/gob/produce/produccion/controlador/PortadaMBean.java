package pe.gob.produce.produccion.controlador;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.EventoBO;
import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.model.EventoModel;
import pe.gob.produce.produccion.model.InformativoModel;
import pe.gob.produce.produccion.services.EventoServices;
import pe.gob.produce.produccion.services.InformativoServices;

@Controller("portadaMBean")
@ViewScoped
public class PortadaMBean {

	@Autowired
	private EventoServices eventoService;

	@Autowired
	private InformativoServices informativoService;

	private List<EventoModel> listaEventos;
	
	private List<InformativoModel> listaNoticias;

	@SuppressWarnings("static-access")
	@PostConstruct
	public void init() {
		try {
			listaEventos = new ArrayList<>();
			for (EventoBO eventoBO : eventoService.listarEventos()) {
				EventoModel eventoModel = new EventoModel();
				eventoModel.setId(eventoBO.getId());
				eventoModel.setTitulo(eventoBO.getTitulo());
				eventoModel.setDescripcion(eventoBO.getDescripcion());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(eventoBO.getFechaInicio());
				SimpleDateFormat formato = new SimpleDateFormat("dd MMM");
				String fecha = formato.format(eventoBO.getFechaInicio());
				eventoModel.setFecha(fecha);
				formato = new SimpleDateFormat("HH:mm");
				String hora = formato.format(eventoBO.getFechaInicio());
				eventoModel.setHora(hora);
				listaEventos.add(eventoModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		cargarNoticias(2);
	}

	// constructor
	public PortadaMBean() {
		System.out.println("::::: LOADING PortadaMBean ::::::::");
		// portadaModel = new PortadaModel();
	}

	public void cargarNoticias(int numNoticias){
		listaNoticias = new ArrayList<>();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy");
		for (ServicioInformativoBO informativo : informativoService.listarNoticias(numNoticias)) {
			InformativoModel noticia = new InformativoModel();
			noticia.setId(informativo.getId().intValue() + "");
			noticia.setTitulo(informativo.getTituloInformativo());
			noticia.setDescripcionCorta(informativo.getDescCortaInformativo());
			noticia.setDescripcion(informativo.getDescInformativo());			
			String fecha = formato.format(informativo.getFecha());
			noticia.setFecha(fecha);
			InputStream imagenDB = new ByteArrayInputStream(informativo.getArchivoInformativo());
			noticia.setImagen(new DefaultStreamedContent(imagenDB, "image/jpg"));
			listaNoticias.add(noticia);
		}
	}
	
	public void cargarNoticiasPorMes(int anio, int mes){
		listaNoticias = new ArrayList<>();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy");
		for (ServicioInformativoBO informativo : informativoService.listarNoticiasPorMes(anio, mes)) {
			InformativoModel noticia = new InformativoModel();
			noticia.setId(informativo.getId().intValue() + "");
			noticia.setTitulo(informativo.getTituloInformativo());
			noticia.setDescripcionCorta(informativo.getDescCortaInformativo());
			noticia.setDescripcion(informativo.getDescInformativo());			
			String fecha = formato.format(informativo.getFecha());
			noticia.setFecha(fecha);
			InputStream imagenDB = new ByteArrayInputStream(informativo.getArchivoInformativo());
			noticia.setImagen(new DefaultStreamedContent(imagenDB, "image/jpg"));
			listaNoticias.add(noticia);
		}
	}
	
	public String cargarBienvenida() {
		cargarNoticias(2);
		return "/paginas/ModuloProduccion/cite/portadaPrincipal/bienvenida.xhtml";
	}

	public String cargarRedNoticias() {
		return "/paginas/ModuloProduccion/cite/portadaPrincipal/redNoticias.xhtml";
	}

	public String cargarCalendarioEventos() {
		return "/paginas/ModuloProduccion/cite/portadaPrincipal/calendarioEventos.xhtml";
	}

	public String cargarPublicacionesInformativas() {
		return "/paginas/ModuloProduccion/cite/portadaPrincipal/publicacionesInformativas.xhtml";
	}

	public List<EventoModel> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(List<EventoModel> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public List<InformativoModel> getListaNoticias() {
		return listaNoticias;
	}

	public void setListaNoticias(List<InformativoModel> listaNoticias) {
		this.listaNoticias = listaNoticias;
	}
}
