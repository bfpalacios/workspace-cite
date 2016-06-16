package pe.gob.produce.produccion.controlador;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.model.InformativoModel;
import pe.gob.produce.produccion.model.Mes;
import pe.gob.produce.produccion.services.InformativoServices;

@Controller("publicInformativaMBean")
@ViewScoped
public class PublicInformativaMBean {
	private int anio;
	private List<Integer> anios;
	private String mes;
	private List<String> meses;

	private List<InformativoModel> listaPublicaciones;
	private List<InformativoModel> listaPublicacionesDerecha;
	private List<InformativoModel> listaPublicacionesIzquierda;
	
	@Autowired
	private InformativoServices informativoService;

	
	public PublicInformativaMBean(){
		
	}
	@SuppressWarnings("static-access")
	@PostConstruct
	public void init() {
		cargarPublicaciones(4);	
		anios = new ArrayList<Integer>();		
		Calendar calendar = Calendar.getInstance();
		int actual = calendar.get(calendar.YEAR);
		for(int i = 0; i<5; i++){
			anios.add(actual--);
		}
		meses = new ArrayList<String>();
		for (Mes mes : Mes.values()) {
			meses.add(mes.toString());
		}
	}
	public int guardarAnio(){
		return this.anio; 
	}
	
	public void separar2columnas(){
		int count = 1;
		listaPublicacionesDerecha = new ArrayList<>();
		listaPublicacionesIzquierda = new ArrayList<>();
		for (InformativoModel info : listaPublicaciones) {
			if(count % 2 ==0){
				listaPublicacionesDerecha.add(info);
			}else{
				listaPublicacionesIzquierda.add(info);
			}
			count++;
		}
	}
	
	public void buscar(){
		int m = 0;
		for (Mes mesEnum : Mes.values()) {
			if(mesEnum.toString().equals(mes)){
				m = mesEnum.getValor() - 1;
				break;
			}
		}
		if(m==0){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalido", "Mes no es seleccionado.");             
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			if(anio <= 0){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalido", "Año no es seleccionado.");             
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
			cargarPublicacionesPorMes(anio, m);
			}
		}
		//return "/paginas/ModuloProduccion/cite/portadaPrincipal/publicacionesInformativas.xhtml";
	}
	
	public void cargarPublicaciones(int numPublicaciones){
		listaPublicaciones = new ArrayList<>();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy");
		for (ServicioInformativoBO informativo : informativoService.listarPublicaciones(numPublicaciones)) {
			InformativoModel noticia = new InformativoModel();
			noticia.setId(informativo.getId().intValue() + "");
			noticia.setTitulo(informativo.getTituloInformativo());
			noticia.setDescripcionCorta(informativo.getDescCortaInformativo());
			noticia.setDescripcion(informativo.getDescInformativo());			
			String fecha = formato.format(informativo.getFecha());
			noticia.setFecha(fecha);
			InputStream imagenDB = new ByteArrayInputStream(informativo.getArchivoInformativo());
			noticia.setImagen(new DefaultStreamedContent(imagenDB, "image/jpg"));
			listaPublicaciones.add(noticia);
		}
		separar2columnas();
	}

	public void cargarPublicacionesPorMes(int anio, int mes){
		listaPublicaciones = new ArrayList<>();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy");
		for (ServicioInformativoBO informativo : informativoService.listarPublicacionesPorMes(anio, mes)) {
			InformativoModel noticia = new InformativoModel();
			noticia.setId(informativo.getId().intValue() + "");
			noticia.setTitulo(informativo.getTituloInformativo());
			noticia.setDescripcionCorta(informativo.getDescCortaInformativo());
			noticia.setDescripcion(informativo.getDescInformativo());			
			String fecha = formato.format(informativo.getFecha());
			noticia.setFecha(fecha);
			InputStream imagenDB = new ByteArrayInputStream(informativo.getArchivoInformativo());
			noticia.setImagen(new DefaultStreamedContent(imagenDB, "image/jpg"));
			listaPublicaciones.add(noticia);
		}
		separar2columnas();
	}
	
	public PublicInformativaMBean(int anio, List<Integer> anios, String mes,
			List<String> meses) {
		super();
		this.anio = anio;
		this.anios = anios;
		this.mes = mes;
		this.meses = meses;
	}
	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public List<Integer> getAnios() {
		return anios;
	}

	public void setAnios(List<Integer> anios) {
		this.anios = anios;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<String> getMeses() {
		return meses;
	}
	

	public List<InformativoModel> getListaPublicacionesDerecha() {
		return listaPublicacionesDerecha;
	}
	public void setListaPublicacionesDerecha(List<InformativoModel> listaPublicacionesDerecha) {
		this.listaPublicacionesDerecha = listaPublicacionesDerecha;
	}
	public List<InformativoModel> getListaPublicacionesIzquierda() {
		return listaPublicacionesIzquierda;
	}
	public void setListaPublicacionesIzquierda(
			List<InformativoModel> listaPublicacionesIzquierda) {
		this.listaPublicacionesIzquierda = listaPublicacionesIzquierda;
	}
	public void setMeses(List<String> meses) {
		this.meses = meses;
	}
	public List<InformativoModel> getListaPublicaciones() {
		return listaPublicaciones;
	}
	public void setListaPublicaciones(List<InformativoModel> listaPublicaciones) {
		this.listaPublicaciones = listaPublicaciones;
	}

	
}
