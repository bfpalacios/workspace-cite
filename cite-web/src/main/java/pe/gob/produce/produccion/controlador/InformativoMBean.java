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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.cite.bo.TipoDocumentoCiteBO;
import pe.gob.produce.produccion.core.util.FormateadorFecha;
import pe.gob.produce.produccion.core.util.TipoInformativo;
import pe.gob.produce.produccion.model.InformativoModel;
import pe.gob.produce.produccion.services.CITEServices;
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
	private String tipoDocumento;
	private ServicioMBean servMBean;
	@Autowired
	private InformativoServices informativoServices;
	private List<InformativoModel> datosInformativoModelGrid;
	
	@Autowired
	private CITEServices citeServices;
	private List<TipoDocumentoCiteBO> listaTipoDocumento;
	private List<InformativoModel> listaDocsDerecha;
	private List<InformativoModel> listaDocsIzquierda;
	
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
		this.informativoModel.setTipoDocumento(new TipoDocumentoCiteBO());
		setInformativoModel(informativoModel);
		this.datosInformativoModelGrid = new ArrayList<>();
	}
	
	public String mostrarDocumentos() throws Exception {
		System.out.println("mostrarDocumentos:INICIO");

		inicializarClases();
		listarTiposDocumentosCite();
		
		String pagina = "/paginas/ModuloProduccion/cite/documentos/mostrarDocumentos.xhtml";
		System.out.println("mostrarDocumentos:FIN");
		return pagina;
	}
	
	
	public String listarCarpetasDocumentos() throws Exception {

		System.out.println("documentosITP:INICIO");
		String pagina = "";

		inicializarClases();
		pagina = "/paginas/ModuloProduccion/cite/documentos/downloadManualesITP.xhtml";
		System.out.println("documentosITP:FIN");
		return pagina;

	}
	
	private void listarTiposDocumentosCite() {
		try {
			this.setListaTipoDocumento(citeServices.listarTipoDocumentoCiteBO());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void separarDocsEnColumnas(){
		int count = 1;
		listaDocsDerecha = new ArrayList<>();
		listaDocsIzquierda = new ArrayList<>();
		for (InformativoModel info : listaInformativos) {
			if(count % 2 ==0){
				listaDocsDerecha.add(info);
			}else{
				listaDocsIzquierda.add(info);
			}
			count++;
		}
	}
	
	public void buscarDocumentos() throws Exception {
		buscarInformativo(getTipoDocumento(), TipoInformativo.DOCUMENTO);
	}

	public void buscarNoticias() throws Exception {
		//no aplica tipoDocumento
		buscarInformativo(null, TipoInformativo.NOTICIA);
	}

	public void buscarPublicaciones() throws Exception {
		//no aplica tipoDocumento
		buscarInformativo(null, TipoInformativo.PUBLICACION);
	}
	
	public void buscarInformativo(String tipoDocumento, TipoInformativo tipo){
		
		FormateadorFecha fechaFormateada = new FormateadorFecha();
		String titulo = getTitulo();
		Date fecha = getFecha();
		
		// this should be gone in a logger
		System.out.println("DATOS BUSQUEDA DE INFORMATIVO " + tipo.toString() + ": " + titulo + " - "
				+ fecha + " - " + tipoDocumento);
		listaInformativos = new ArrayList<>();
		listaInformativosDB = informativoServices.buscarInformativo(titulo,
				fecha, tipoDocumento, tipo);
		for (ServicioInformativoBO informativo : listaInformativosDB) {
			InformativoModel infrtivo = new InformativoModel();
			infrtivo.setId(informativo.getId().intValue() + "");
			infrtivo.setTitulo(informativo.getTituloInformativo());
			infrtivo.setDescripcionCorta(informativo.getDescCortaInformativo());
			infrtivo.setDescripcion(informativo.getDescInformativo());
			infrtivo.setFechaCalendario(informativo.getFecha());
			//String fechaOut = formato.format(informativo.getFecha());
			String fechaOut = fechaFormateada.formatoFechaDDMMAAAA(informativo.getFecha());
			
			
			infrtivo.setFecha(fechaOut);
			InputStream archivoDB = new ByteArrayInputStream(
					informativo.getArchivoInformativo());
			infrtivo.setImagen(new DefaultStreamedContent(archivoDB));
			//noticia.setImagen(new DefaultStreamedContent(imagenDB, "image/jpg"));
			infrtivo.setTipoDocumento(informativo.getTipoDocumento());
			listaInformativos.add(infrtivo);
		}
		if(tipo == TipoInformativo.DOCUMENTO){
			separarDocsEnColumnas();
			setListaInformativos(listaInformativos);
		}else{
			setDatosInformativoModelGrid(listaInformativos);
		}		
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
	
	public void actualizarDocumento(InformativoModel informativoModel)
			throws Exception {
		actualizarInformativo(informativoModel, TipoInformativo.DOCUMENTO);
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
			if(tipo == TipoInformativo.DOCUMENTO){
				servMBean.validarCampos(inforModel.getTitulo(),"test","test");
			}else{
				servMBean.validarCampos(inforModel.getTitulo(),
						inforModel.getDescripcionCorta(),
						inforModel.getDescripcion());
			}
			
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
				infoBO.setTipoDocumento(inforModel.getTipoDocumento());
				informativoServices.actualizarInformativo(infoBO, tipo);
				//no aplica tipoDocumento
				buscarInformativo(null, tipo);
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
	
	public void eliminarDocumento(InformativoModel informativoModel)
			throws Exception {
		if (informativoModel != null) {
			int flatDelete = informativoServices.eliminarInformativo(
					Integer.parseInt(informativoModel.getId()),
					TipoInformativo.DOCUMENTO);
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
	
	public String editarServicioPublicaciones() throws Exception {
		System.out.println("editarServicioPublicaciones:INICIO");
		inicializarClases();
		String pagina = "/paginas/ModuloAdministrador/admin/cite/edicion/EditarPublicaciones.xhtml";
		System.out.println("editarServicioPublicaciones:FIN");
		return pagina;
	}
	
	public String editarDocumentos() throws Exception {
		System.out.println("editarDocumentos:INICIO");
		inicializarClases();
		listarTiposDocumentosCite();
		String pagina = "/paginas/ModuloAdministrador/admin/cite/edicion/EditarDocumentos.xhtml";
		System.out.println("editarDocumentos:FIN");
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

	public List<TipoDocumentoCiteBO> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumentoCiteBO> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<InformativoModel> getListaDocsDerecha() {
		return listaDocsDerecha;
	}

	public void setListaDocsDerecha(List<InformativoModel> listaDocsDerecha) {
		this.listaDocsDerecha = listaDocsDerecha;
	}

	public List<InformativoModel> getListaDocsIzquierda() {
		return listaDocsIzquierda;
	}

	public void setListaDocsIzquierda(List<InformativoModel> listaDocsIzquierda) {
		this.listaDocsIzquierda = listaDocsIzquierda;
	}

	public List<InformativoModel> getListaInformativos() {
		return listaInformativos;
	}

	public void setListaInformativos(List<InformativoModel> listaInformativos) {
		this.listaInformativos = listaInformativos;
	}
	
}
