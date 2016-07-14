package pe.gob.produce.produccion.controlador;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.core.util.TipoInformativo;
import pe.gob.produce.produccion.model.InformativoModel;
import pe.gob.produce.produccion.services.InformativoServices;

@Controller("noticiaMBean")
@ViewScoped
public class NoticiaMBean {
	
	private List<InformativoModel> listaNoticias;
	@Autowired
	private InformativoModel informativoModel;
	private String titulo;
	private Date fecha;
	@Autowired
	private InformativoServices informativoServices;
	
	private List<InformativoModel> datosInformativoModelGrid;
	
	public NoticiaMBean() {		
		informativoModel = new InformativoModel();
	}

	@PostConstruct
	public void init() {
		informativoModel = new InformativoModel();
	}
	
	private void inicializarClases() {
		if (getInformativoModel() != null) {
			setInformativoModel(null);
			setInformativoModel(new InformativoModel());
		}
		this.informativoModel = new InformativoModel();
		setInformativoModel(new InformativoModel());
	}
	
	public void buscarNoticias() throws Exception {
		String titulo = null;
		Date fecha = null;
		titulo = getTitulo();
		fecha = getFecha();

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");	
		//this should be gone in a logger
		System.out.println("DATOS BUSQUEDA DE NOTICIAS " + titulo + "-"	+ fecha);
		listaNoticias = new ArrayList<>();
		List<ServicioInformativoBO> noticiasDB = informativoServices.buscarInformativo(titulo, fecha, TipoInformativo.NOTICIA);
		for (ServicioInformativoBO informativo : noticiasDB) {
			InformativoModel noticia = new InformativoModel();
			noticia.setId(informativo.getId().intValue() + "");
			noticia.setTitulo(informativo.getTituloInformativo());
			noticia.setDescripcionCorta(informativo.getDescCortaInformativo());
			noticia.setDescripcion(informativo.getDescInformativo());
			noticia.setFechaCalendario(informativo.getFecha());
			String fechaOut = formato.format(informativo.getFecha());
			noticia.setFecha(fechaOut);
			InputStream imagenDB = new ByteArrayInputStream(informativo.getArchivoInformativo());
			noticia.setImagen(new DefaultStreamedContent(imagenDB, "image/jpg"));
			listaNoticias.add(noticia);
		}

		setDatosInformativoModelGrid(listaNoticias);
	}
	
	public void buscarPublicaciones() throws Exception {
		String titulo = null;
		Date fecha = null;
		titulo = getTitulo();
		fecha = getFecha();

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");	
		//this should be gone in a logger
		System.out.println("DATOS BUSQUEDA DE PUBLICACIONES " + titulo + "-"	+ fecha);
		listaNoticias = new ArrayList<>();
		List<ServicioInformativoBO> noticiasDB = informativoServices.buscarInformativo(titulo, fecha, TipoInformativo.PUBLICACION);
		for (ServicioInformativoBO informativo : noticiasDB) {
			InformativoModel noticia = new InformativoModel();
			noticia.setId(informativo.getId().intValue() + "");
			noticia.setTitulo(informativo.getTituloInformativo());
			noticia.setDescripcionCorta(informativo.getDescCortaInformativo());
			noticia.setDescripcion(informativo.getDescInformativo());			
			String fechaOut = formato.format(informativo.getFecha());
			noticia.setFecha(fechaOut);
			InputStream archivoDB = new ByteArrayInputStream(informativo.getArchivoInformativo());
			noticia.setImagen(new DefaultStreamedContent(archivoDB));
			listaNoticias.add(noticia);
		}
		setDatosInformativoModelGrid(listaNoticias);
	}
	
	public void actualizarNoticia(InformativoModel informativoModel) throws Exception {		
		if(informativoModel != null){
			ServicioInformativoBO infoBO = new ServicioInformativoBO();
			infoBO.setId(Integer.parseInt(informativoModel.getId()));
			infoBO.setTituloInformativo(informativoModel.getTitulo());
			infoBO.setDescCortaInformativo(informativoModel.getDescripcionCorta());
			infoBO.setDescInformativo(informativoModel.getDescripcion());
			infoBO.setFecha(informativoModel.getFechaCalendario());
			informativoServices.actualizarInformativo(infoBO, TipoInformativo.NOTICIA);
		}
		
		listaNoticias = new ArrayList<>();
		List<ServicioInformativoBO> noticiasDB = informativoServices.buscarInformativo(titulo, fecha, TipoInformativo.NOTICIA);
		for (ServicioInformativoBO informativo : noticiasDB) {
			InformativoModel noticia = new InformativoModel();
			noticia.setId(informativo.getId().intValue() + "");
			noticia.setTitulo(informativo.getTituloInformativo());
			noticia.setDescripcionCorta(informativo.getDescCortaInformativo());
			noticia.setDescripcion(informativo.getDescInformativo());
			InputStream archivoDB = new ByteArrayInputStream(informativo.getArchivoInformativo());
			noticia.setImagen(new DefaultStreamedContent(archivoDB));
			listaNoticias.add(noticia);
		}
		setDatosInformativoModelGrid(listaNoticias);
	}
	
	public void actualizarPublicacion(InformativoModel informativoModel) throws Exception {		
		if(informativoModel != null){
			ServicioInformativoBO infoBO = new ServicioInformativoBO();
			infoBO.setId(Integer.parseInt(informativoModel.getId()));
			infoBO.setTituloInformativo(informativoModel.getTitulo());
			infoBO.setDescCortaInformativo(informativoModel.getDescripcionCorta());
			infoBO.setDescInformativo(informativoModel.getDescripcion());
			infoBO.setFecha(informativoModel.getFechaCalendario());
			informativoServices.actualizarInformativo(infoBO, TipoInformativo.PUBLICACION);
		}
		
		listaNoticias = new ArrayList<>();
		List<ServicioInformativoBO> noticiasDB = informativoServices.buscarInformativo(titulo, fecha, TipoInformativo.PUBLICACION);
		for (ServicioInformativoBO informativo : noticiasDB) {
			InformativoModel noticia = new InformativoModel();
			noticia.setId(informativo.getId().intValue() + "");
			noticia.setTitulo(informativo.getTituloInformativo());
			noticia.setDescripcionCorta(informativo.getDescCortaInformativo());
			noticia.setDescripcion(informativo.getDescInformativo());
			InputStream archivoDB = new ByteArrayInputStream(informativo.getArchivoInformativo());
			noticia.setImagen(new DefaultStreamedContent(archivoDB));
			listaNoticias.add(noticia);
		}
		setDatosInformativoModelGrid(listaNoticias);
	}
	
	public void eliminarNoticia(InformativoModel informativoModel) throws Exception {
		if(informativoModel != null){
			informativoServices.eliminarInformativo(Integer.parseInt(informativoModel.getId()), TipoInformativo.NOTICIA);
		}
	}
	
	public void eliminarPublicacion(InformativoModel informativoModel) throws Exception {
		if(informativoModel != null){
			informativoServices.eliminarInformativo(Integer.parseInt(informativoModel.getId()), TipoInformativo.PUBLICACION);
		}
	}
	
	 public void update(ActionEvent actionEvent) {
	        System.out.println(actionEvent.getSource());
	    }
	
	public String editarServicioNoticias() throws Exception {
		System.out.println("editarServicioNoticias:INICIO");
		
		inicializarClases();
		String pagina = "/paginas/ModuloAdministrador/admin/cite/edicion/EditarNoticias.xhtml";
		System.out.println("editarServicioNoticias:FIN");
		return pagina;
	}
	
	public String editarServicioPublicaciones() throws Exception {
		System.out.println("editarServicioPublicaciones:INICIO");
		String pagina = "/paginas/ModuloAdministrador/admin/cite/edicion/EditarPublicaciones.xhtml";
		System.out.println("editarServicioPublicaciones:FIN");
		return pagina;
	}
	
	public List<InformativoModel> getListaNoticias() {
		return listaNoticias;
	}

	public void setListaNoticias(List<InformativoModel> listaNoticias) {
		this.listaNoticias = listaNoticias;
	}

	public InformativoModel getInformativoModel() {
		return informativoModel;
	}

	public void setInformativoModel(InformativoModel informativoModel) {
		this.informativoModel = informativoModel;
	}

	public List<InformativoModel> getDatosInformativoModelGrid() {
		return datosInformativoModelGrid;
	}

	public void setDatosInformativoModelGrid(
			List<InformativoModel> datosInformativoModelGrid) {
		this.datosInformativoModelGrid = datosInformativoModelGrid;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public InformativoServices getInformativoServices() {
		return informativoServices;
	}

	public void setInformativoServices(InformativoServices informativoServices) {
		this.informativoServices = informativoServices;
	}	
}
