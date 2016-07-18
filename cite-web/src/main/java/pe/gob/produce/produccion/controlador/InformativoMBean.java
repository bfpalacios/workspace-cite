package pe.gob.produce.produccion.controlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.core.util.FormateadorFecha;
import pe.gob.produce.produccion.core.util.TipoInformativo;
import pe.gob.produce.produccion.model.InformativoModel;
import pe.gob.produce.produccion.services.InformativoServices;

@Controller("informativoMBean")
@ViewScoped
public class InformativoMBean {

	private List<InformativoModel> listaInformativos;
	private List<ServicioInformativoBO> listaInformativosDB;
	@Autowired
	private InformativoModel informativoModel;
	private String titulo;
	private Date fecha;
	private ServicioMBean servMBean;
	@Autowired
	private InformativoServices informativoServices;
	private List<InformativoModel> datosInformativoModelGrid;
	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

	public InformativoMBean() {
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
		buscarInformativo(TipoInformativo.NOTICIA);
	}

	public void buscarPublicaciones() throws Exception {
		buscarInformativo(TipoInformativo.PUBLICACION);
	}
	
	public void buscarInformativo(TipoInformativo tipo){
		
		FormateadorFecha fechaFormateada = new FormateadorFecha();
		String titulo = null;
		
		Date fecha = null;
		titulo = getTitulo();
		fecha = getFecha();
		
		// this should be gone in a logger
		System.out.println("DATOS BUSQUEDA DE INFORMATIVO " + tipo.toString() + ": " + titulo + "-"
				+ fecha);
		listaInformativos = new ArrayList<>();
		listaInformativosDB = informativoServices.buscarInformativo(titulo,
				fecha, tipo);
		for (ServicioInformativoBO informativo : listaInformativosDB) {
			InformativoModel noticia = new InformativoModel();
			noticia.setId(informativo.getId().intValue() + "");
			noticia.setTitulo(informativo.getTituloInformativo());
			noticia.setDescripcionCorta(informativo.getDescCortaInformativo());
			noticia.setDescripcion(informativo.getDescInformativo());
			noticia.setFechaCalendario(informativo.getFecha());
			//String fechaOut = formato.format(informativo.getFecha());
			String fechaOut = fechaFormateada.formatoFechaDDMMAAAA(informativo.getFecha());
			
			
			noticia.setFecha(fechaOut);
			InputStream archivoDB = new ByteArrayInputStream(
					informativo.getArchivoInformativo());
			noticia.setImagen(new DefaultStreamedContent(archivoDB));
			//noticia.setImagen(new DefaultStreamedContent(imagenDB, "image/jpg"));
			listaInformativos.add(noticia);
		}
		setDatosInformativoModelGrid(listaInformativos);
	}

	public void handleFileUploadInformativo(FileUploadEvent e)
			throws IOException {
		servMBean = new ServicioMBean();
		servMBean.handleFileUploadInformativo(e);
	}

	public void actualizarNoticia(InformativoModel informativoModel)
			throws Exception {
		actualizarInformativo(informativoModel, TipoInformativo.NOTICIA);
	}

	public void actualizarPublicacion(InformativoModel informativoModel)
			throws Exception {
		actualizarInformativo(informativoModel, TipoInformativo.PUBLICACION);
	}

	public void actualizarInformativo(InformativoModel inforModel,
			TipoInformativo tipo) {
		byte[] archivoInformativo = null;
		try {
			try {
				if (servMBean != null && servMBean.getFile() != null) {
					UploadedFile archivoPDF = servMBean.getFile();
					archivoInformativo = IOUtils.toByteArray(archivoPDF
							.getInputstream());
				} else {
					servMBean = new ServicioMBean();
					archivoInformativo = IOUtils.toByteArray(inforModel.getImagen().getStream());					
				}
			} catch (Exception ev) {
				throw new Exception(ev);
			}
			servMBean.validarCampos(inforModel.getTitulo(),
					inforModel.getDescripcionCorta(),
					inforModel.getDescripcion());
			if (inforModel != null) {
				ServicioInformativoBO infoBO = new ServicioInformativoBO();
				infoBO.setId(Integer.parseInt(inforModel.getId()));				
				infoBO.setTituloInformativo((inforModel.getTitulo()!=null)?inforModel.getTitulo():"");
				infoBO.setDescCortaInformativo((inforModel.getDescripcionCorta()!=null)?inforModel.getDescripcionCorta():"");
				infoBO.setDescInformativo((inforModel.getDescripcion()!=null)?inforModel.getDescripcion():"");
				if(inforModel.getFechaCalendario()!=null){
					infoBO.setFecha(inforModel.getFechaCalendario());
				}else{
					try {
						Date fecha = formato.parse(inforModel.getFecha());
						infoBO.setFecha(fecha);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}				
				infoBO.setArchivoInformativo(archivoInformativo);
				informativoServices.actualizarInformativo(infoBO, tipo);
				buscarInformativo(tipo);
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al actualizar " + tipo.toString() + ": "
							+ inforModel.getTitulo());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	public void eliminarNoticia(InformativoModel informativoModel)
			throws Exception {
		if (informativoModel != null) {
			int flatDelete = informativoServices.eliminarInformativo(
					Integer.parseInt(informativoModel.getId()),
					TipoInformativo.NOTICIA);
			actulizarGridDespuesDeEliminar(flatDelete);
		}
	}

	public void actulizarGridDespuesDeEliminar(int flatDelete) {
		if (flatDelete > 0) {
			for (Iterator<InformativoModel> iter = listaInformativos
					.listIterator(); iter.hasNext();) {
				InformativoModel infor = iter.next();
				if (infor.getId() == informativoModel.getId()) {
					iter.remove();
				}
			}
			setDatosInformativoModelGrid(listaInformativos);
		}else{
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al eliminar");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void eliminarPublicacion(InformativoModel informativoModel)
			throws Exception {
		if (informativoModel != null) {
			int flatDelete = informativoServices.eliminarInformativo(
					Integer.parseInt(informativoModel.getId()),
					TipoInformativo.PUBLICACION);
			actulizarGridDespuesDeEliminar(flatDelete);
		}
	}

	public String editarServicioNoticias() throws Exception {
		System.out.println("editarServicioNoticias:INICIO");

		inicializarClases();
		//reset();
		String pagina = "/paginas/ModuloAdministrador/admin/cite/edicion/EditarNoticias.xhtml";
		System.out.println("editarServicioNoticias:FIN");
		return pagina;
	}
	
	public void reset() {
		RequestContext.getCurrentInstance().reset(
				"formPrincipal:edicionDetalle");

	}
	public String editarServicioPublicaciones() throws Exception {
		System.out.println("editarServicioPublicaciones:INICIO");
		inicializarClases();
		String pagina = "/paginas/ModuloAdministrador/admin/cite/edicion/EditarPublicaciones.xhtml";
		System.out.println("editarServicioPublicaciones:FIN");
		return pagina;
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
