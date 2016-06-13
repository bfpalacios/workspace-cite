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

	private List<InformativoModel> listaNoticias;
	private List<InformativoModel> listaNoticiasDerecha;
	private List<InformativoModel> listaNoticiasIzquierda;
	
	@Autowired
	private InformativoServices informativoService;

	
	public PublicInformativaMBean(){
		
	}
	@SuppressWarnings("static-access")
	@PostConstruct
	public void init() {
		cargarNoticias(4);	
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
		listaNoticiasDerecha = new ArrayList<>();
		listaNoticiasIzquierda = new ArrayList<>();
		for (InformativoModel info : listaNoticias) {
			if(count % 2 ==0){
				listaNoticiasDerecha.add(info);
			}else{
				listaNoticiasIzquierda.add(info);
			}
			count++;
		}
	}
	
	public String buscar(){
		int m = 0;
		for (Mes mesEnum : Mes.values()) {
			if(mesEnum.toString().equals(mes)){
				m = mesEnum.getValor();
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
			cargarNoticiasPorMes(anio, m);
			}
		}
		return "/paginas/ModuloProduccion/cite/portadaPrincipal/publicacionesInformativas.xhtml";
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
		separar2columnas();
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
	

	public List<InformativoModel> getListaNoticiasDerecha() {
		return listaNoticiasDerecha;
	}
	public void setListaNoticiasDerecha(List<InformativoModel> listaNoticiasDerecha) {
		this.listaNoticiasDerecha = listaNoticiasDerecha;
	}
	public List<InformativoModel> getListaNoticiasIzquierda() {
		return listaNoticiasIzquierda;
	}
	public void setListaNoticiasIzquierda(
			List<InformativoModel> listaNoticiasIzquierda) {
		this.listaNoticiasIzquierda = listaNoticiasIzquierda;
	}
	public void setMeses(List<String> meses) {
		this.meses = meses;
	}
	public List<InformativoModel> getListaNoticias() {
		return listaNoticias;
	}
	public void setListaNoticias(List<InformativoModel> listaNoticias) {
		this.listaNoticias = listaNoticias;
	}

	
}
