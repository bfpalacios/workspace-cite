package pe.gob.produce.produccion.controlador;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.ServicioBO;
import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.cite.bo.TipoDocumentoCiteBO;
import pe.gob.produce.cite.bo.UbigeoBO;
import pe.gob.produce.produccion.core.util.Convertidor;
import pe.gob.produce.produccion.core.util.FormateadorFecha;
import pe.gob.produce.produccion.core.util.ObtenerNumeroAleatorio;
import pe.gob.produce.produccion.model.InformativoModel;
import pe.gob.produce.produccion.model.ServicioModel;
import pe.gob.produce.produccion.model.UbigeoModel;
import pe.gob.produce.produccion.model.UsuarioModel;
import pe.gob.produce.produccion.services.CITEServices;
import pe.gob.produce.produccion.services.ComunServices;
import pe.gob.produce.produccion.services.ServicioServices;

@Controller("servicioMBean")
@ViewScoped
public class ServicioMBean {

	@Autowired
	private ServicioModel servicioModel;

	@Autowired
	private UsuarioModel usuarioModel;

	@Autowired
	private CITEServices citeServices;

	@Autowired
	private ComunServices comunServices;

	@Autowired
	private ServicioServices servicioServices;
		
	// datos complementarios de la pantalla
	private Date date;
	private UsuarioModel usuarioModelSelect;
	private String rutaComprobante;
	private String titulo;
	private UploadedFile file;

	// constantes
	private int MODO_USUARIO;
	private static int MODO_ADMIN = 1;
	private static int MODO_EMPLEADO = 2;

	// para la lista de servicios se declara una variable list de tipo
	// ServicioModel
	private List<ServicioModel> datosServiciosModelGrid;
	private List<ServicioModel> selectedServicios;
	
	private ServicioModel servicioModelSelect;
	// constructor
	public ServicioMBean() {
		System.out.println("::::: LOADING ServicioMBean ::::::::");
		inicializarClases();
		new Convertidor();
		new FormateadorFecha();
		date = new Date();

		this.usuarioModel = new UsuarioModel();
		this.usuarioModelSelect = new UsuarioModel();
		this.servicioModel = new ServicioModel();
		this.servicioModelSelect  = new ServicioModel();
		this.setFile(null);
	}

	private void inicializarClases() {
		if (getServicioModel() != null) {
			setServicioModel(null);
			setServicioModel(new ServicioModel());
		}
		
		if (getServicioModelSelect() != null) {
			setServicioModelSelect(null);
			setServicioModelSelect(new ServicioModel());
		}
		this.servicioModelSelect = new ServicioModel();
		setServicioModelSelect(new ServicioModel());
		this.servicioModel = new ServicioModel();
		setServicioModel(new ServicioModel());

	}

	public String selectorNuevoServicio(int modo) throws Exception {
		String pagina = "";

		inicializarClases();

		listarCITE();

		// listarServicios();
		switch (modo) {
		case 1:

			pagina = "/paginas/ModuloProduccion/cliente/servicio/nuevo/nuevoServicio.xhtml";
			break;

		/* @@ESTE ES EL CASO PARA PERFIL CITE */
		case 2:

			// listarMuestras();
			pagina = "/paginas/ModuloProduccion/cite/servicio/nuevo/nuevoServicio.xhtml";
			break;

		/* @@ESTE ES EL CASO PARA PERFIL EMPRESA */
		case 3:

			pagina = "/paginas/ModuloProduccion/empresa/servicio/nuevo/nuevoServicio.xhtml";
			break;

		/* @@ESTE ES EL CASO PARA PERFIL EMPRESA */
		case 4:

			pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoCite.xhtml";
			break;

		}
		return pagina;
	}

	public String selectorNuevoDependencia(int modo) throws Exception {
		System.out.println("ENTRANDO");
		String pagina = "";

		inicializarClases();

		switch (modo) {

		/* @@ESTE ES EL CASO PARA PERFIL CITE */
		case 1:

			pagina = "/paginas/ModuloProduccion/cite/nuevo/nuevaDependencia.xhtml";
			break;

		/* @@ESTE ES EL CASO PARA PERFIL ADMIN */
		case 2:

			pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaDependencia.xhtml";
			break;

		case 3:

			pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaSede.xhtml";
			break;

		}
		return pagina;
	}

	public String selectorNuevoSede(int modo) throws Exception {
		String pagina = "";

		inicializarClases();

		List<UbigeoBO> listarUbigeo = new ArrayList<UbigeoBO>();

		List<UbigeoModel> listaUbigeoModel = new ArrayList<UbigeoModel>();

		try {
			// se llama para cargar al combo de departamento
			listarUbigeo = comunServices.listUbigeo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (UbigeoBO ubigeoBO : listarUbigeo) {

			UbigeoModel ubigeo = new UbigeoModel();
			ubigeo.setIdUbigeo(ubigeoBO.getIdUbigeo());
			ubigeo.setDepartamento(ubigeoBO.getDepartamento());
			listaUbigeoModel.add(ubigeo);
		}

		getUsuarioModel().setListUbigeo(listaUbigeoModel);

		switch (modo) {

		/* @@ESTE ES EL CASO PARA PERFIL CITE */
		case 1:

			pagina = "/paginas/ModuloProduccion/cite/nuevo/nuevaDependencia.xhtml";
			break;

		/* @@ESTE ES EL CASO PARA PERFIL ADMIN */
		case 2:

			pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaSede.xhtml";
			break;

		}
		return pagina;
	}

	public String nuevoServicioNoticias() throws Exception {

		System.out.println("nuevoServicioInformativo:INICIO");
		String pagina = "";

		inicializarClases();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoServicioNoticias.xhtml";
		System.out.println("nuevoServicioInformativo:FIN");
		return pagina;

	}

	public String nuevoServicioPublicaciones() throws Exception {

		System.out.println("nuevoServicioPublicacioness:INICIO");
		String pagina = "";

		inicializarClases();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoServicioPublicaciones.xhtml";
		System.out.println("nuevoServicioPublicaciones:FIN");
		return pagina;

	}	

	public String nuevosManuales() throws Exception {

		System.out.println("nuevosManuales:INICIO");
		String pagina = "";

		inicializarClases();
		listarTiposDocumentosCite();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevosManuales.xhtml";
		System.out.println("nuevosManuales:FIN");
		return pagina;

	}
	
	public String nuevosFormatos() throws Exception {

		System.out.println("nuevosFormatos:INICIO");
		String pagina = "";

		inicializarClases();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevosFormatos.xhtml";
		System.out.println("nuevosFormatos:FIN");
		return pagina;

	}
	
	public String nuevosProcedimientos() throws Exception {

		System.out.println("nuevosProcedimientos:INICIO");
		String pagina = "";

		inicializarClases();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevosProcedimientos.xhtml";
		System.out.println("nuevosProcedimientos:FIN");
		return pagina;

	}
	
	public String nuevosInstructivos() throws Exception {

		System.out.println("nuevosInstructivos:INICIO");
		String pagina = "";

		inicializarClases();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevosInstructivos.xhtml";
		System.out.println("nuevosInstructivos:FIN");
		return pagina;

	}
	
	public String nuevosDocumentos() throws Exception {

		System.out.println("nuevosDocumentos:INICIO");
		String pagina = "";

		inicializarClases();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevosDocumentos.xhtml";
		System.out.println("nuevosDocumentos:FIN");
		return pagina;

	}

	public String documentosITP(int modo) throws Exception {

		System.out.println("documentosITP:INICIO");
		String pagina = "";

		inicializarClases();
		switch (modo) {
		case 1:
			pagina = "/paginas/ModuloAdministrador/admin/cite/documentos/downloadManualesITP.xhtml";break;
		case 2:
			pagina = "/paginas/ModuloProduccion/cite/documentos/downloadManualesITP.xhtml";break;
		
		}
		
		System.out.println("documentosITP:FIN");
		return pagina;

	}

	public void invocarServletDocumentos() throws Exception {
		System.out.println("invocarServletDocumentos:INICIO");
 

		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/ListarDirectorios");

		System.out.println("invocarServletDocumentos:FIN");

	}
	
	public void invocarServletDocumentosFormatos() throws Exception {
		System.out.println("invocarServletDocumentos:INICIO");
		

		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/ListarDirectoriosFormatos");

		System.out.println("invocarServletDocumentos:FIN");

	}
	
	public void invocarServletDocumentosProcedimientos() throws Exception {
		System.out.println("invocarServletDocumentos:INICIO");
 

		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/ListarDirectoriosProcedimientos");

		System.out.println("invocarServletDocumentos:FIN");

	}
	
	public void invocarServletDocumentosInstructivos() throws Exception {
		System.out.println("invocarServletDocumentos:INICIO");
 

		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/ListarDirectoriosInstructivos");

		System.out.println("invocarServletDocumentos:FIN");

	}
	
	public void invocarServletDocumentosGeneral() throws Exception {
		System.out.println("invocarServletDocumentos:INICIO");
 

		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/ListarDirectoriosGeneral");

		System.out.println("invocarServletDocumentos:FIN");

	}
	

	public void actualizarlistProvincia(ValueChangeEvent e) throws Exception {
		String codDepartamento = (String) (e.getNewValue() == null ? "" : e
				.getNewValue());

		codDepartamento = codDepartamento.substring(0, 2);
		System.out.println("codigo de departamento " + codDepartamento);
		List<UbigeoBO> listarProvincia = new ArrayList<UbigeoBO>();
		List<UbigeoModel> listaUbigeoModel = new ArrayList<UbigeoModel>();

		listarProvincia = comunServices.listarProvincia(codDepartamento);

		for (UbigeoBO ubigeoBO : listarProvincia) {

			UbigeoModel ubigeo = new UbigeoModel();
			ubigeo.setIdUbigeo(ubigeoBO.getIdUbigeo());
			// ubigeo.setDepartamento(ubigeoBO.getDepartamento());
			ubigeo.setProvincia(ubigeoBO.getProvincia());
			// ubigeo.setDistrito(ubigeoBO.getDistrito());
			listaUbigeoModel.add(ubigeo);
		}

		getUsuarioModel().setListProvincia(listaUbigeoModel);
	}

	public void actualizarlistDistrito(ValueChangeEvent e) throws Exception {
		String codDepartamento = getUsuarioModelSelect().getCodDepartamento() == null ? ""
				: getUsuarioModelSelect().getCodDepartamento();

		String codProvincia = (String) (e.getNewValue() == null ? "" : e
				.getNewValue());

		codDepartamento = codDepartamento.substring(0, 2);
		codProvincia = codProvincia.substring(2, 4);
		System.out.println("codigo de departamento " + codDepartamento);
		System.out.println("codigo de provincia " + codProvincia);

		List<UbigeoBO> listarProvincia = new ArrayList<UbigeoBO>();
		List<UbigeoModel> listaUbigeoModel = new ArrayList<UbigeoModel>();

		listarProvincia = comunServices.listarDistrito(codDepartamento,
				codProvincia);

		for (UbigeoBO ubigeoBO : listarProvincia) {

			UbigeoModel ubigeo = new UbigeoModel();
			ubigeo.setIdUbigeo(ubigeoBO.getIdUbigeo());
			// ubigeo.setDepartamento(ubigeoBO.getDepartamento());
			// ubigeo.setProvincia(ubigeoBO.getProvincia());
			ubigeo.setDistrito(ubigeoBO.getDistrito());
			listaUbigeoModel.add(ubigeo);
		}

		getUsuarioModel().setListDistrito(listaUbigeoModel);
	}

	public void handleUpload(FileUploadEvent event) {
		UploadedFile uploadedArchivo = event.getFile();

		setFile(uploadedArchivo);
	}

	public void guardarNuevoServicioInformativo() throws IOException {
		
		
		byte[] archivoInformativo = null ;
	
		try {
			if(getFile() != null)
			{	
				UploadedFile archivoPDF = getFile();
				archivoInformativo = IOUtils.toByteArray(archivoPDF
						.getInputstream());
				Date fecha = getDate();
	
				String titulo = getServicioModel().getTituloInformativo() == null
				 ? "" : getServicioModel().getTituloInformativo();
				 
				String descripcionCorta = getServicioModel().getDescripcionCorta() == null ? ""
						: getServicioModel().getDescripcionCorta();
				;
				String descripcion = getServicioModel().getDescripcion();
	
				System.out.println("Datos titulo" + titulo);
				System.out.println("Datos descripcionCorta" + descripcionCorta);
				System.out.println("Datos descripcion" + descripcion);
				System.out.println("Datos archivoInformativo" + archivoInformativo);
				System.out.println("Datos FECHA" + fecha);
				
				if (validarCampos(titulo, descripcionCorta, descripcion)){
					ServicioInformativoBO servicio = new ServicioInformativoBO();
		
						servicio.setTituloInformativo(titulo);
						servicio.setDescCortaInformativo(descripcionCorta);
						servicio.setDescInformativo(descripcion);
						servicio.setArchivoInformativo(archivoInformativo);
						servicio.setFecha(fecha);
					
						citeServices.grabarInformativo(servicio);
						limpiarObjetos();
						RequestContext rc = RequestContext.getCurrentInstance();
						rc.execute("dialogNuevoInformativo.show()");
				}
				
			} else mostrarMensajeDocumento(4);
		} catch (Exception ev) {
			ev.printStackTrace();
			mostrarMensajeDocumento(9);
		}
		

	}
	
	public void guardarNuevoServicioPublicaciones() {
		byte[] archivoInformativo = null ;
		
		try {
			if(getFile() != null)
			{	
				UploadedFile archivoPDF = getFile();
				archivoInformativo = IOUtils.toByteArray(archivoPDF
						.getInputstream());
				Date fecha = getDate();
	
				String titulo = getServicioModel().getTituloInformativo() == null
				 ? "" : getServicioModel().getTituloInformativo();
				 
				String descripcionCorta = getServicioModel().getDescripcionCorta() == null ? ""
						: getServicioModel().getDescripcionCorta();
				;
				String descripcion = getServicioModel().getDescripcion();
	
				System.out.println("Datos titulo" + titulo);
				System.out.println("Datos descripcionCorta" + descripcionCorta);
				System.out.println("Datos descripcion" + descripcion);
				System.out.println("Datos archivoInformativo" + archivoInformativo);
				
				if (validarCampos(titulo, descripcionCorta, descripcion)){
					ServicioInformativoBO servicio = new ServicioInformativoBO();
		
						servicio.setTituloInformativo(titulo);
						servicio.setDescCortaInformativo(descripcionCorta);
						servicio.setDescInformativo(descripcion);
						servicio.setArchivoInformativo(archivoInformativo);
						servicio.setFecha(fecha);
					
						citeServices.grabarPublicaciones(servicio);
						limpiarObjetos();
						RequestContext rc = RequestContext.getCurrentInstance();
						rc.execute("dialogNuevoPublicaciones.show()");
				}
				
			} else mostrarMensajeDocumento(5);
		} catch (Exception ev) {
			ev.printStackTrace();
			mostrarMensajeDocumento(10);
		}
		

	}
	 
	
	public void guardarNuevoDocumentosCite() {
		byte[] archivoInformativo = null ;
		
		try {
			if(getFile() != null)
			{	
				UploadedFile archivoPDF = getFile();
				archivoInformativo = IOUtils.toByteArray(archivoPDF
						.getInputstream());
				Date fecha = getDate();
	
				String titulo = getServicioModel().getTituloInformativo() == null
						 ? "" : getServicioModel().getTituloInformativo();
						 
				String codigotipoDocCite = getServicioModelSelect().getCodigo() == null
						 ? "" : getServicioModelSelect().getCodigo();
						 
						
				System.out.println("Datos titulo" + titulo);
				System.out.println("Datos archivoInformativo" + archivoInformativo);
				
				if (validarCamposDocCites(titulo)){
					ServicioInformativoBO servicio = new ServicioInformativoBO();
		
						servicio.setTituloInformativo(titulo);
						servicio.setArchivoInformativo(archivoInformativo);
						TipoDocumentoCiteBO tipoDoc = new TipoDocumentoCiteBO();
						tipoDoc.setCodigo(codigotipoDocCite);
						servicio.setTipoDocumento(tipoDoc);
						servicio.setFecha(fecha);
					
						citeServices.grabarDocumentosCites(servicio);
						limpiarObjetos();
						listarTiposDocumentosCite();
						RequestContext rc = RequestContext.getCurrentInstance();
						rc.execute("dialogManuales.show()");
				}
				
			} else mostrarMensajeDocumento(11);
		} catch (Exception ev) {
			ev.printStackTrace();
			mostrarMensajeDocumento(10);
		}
		

	}
	 
	
	
	
	public void handleFileUploadFormatos(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println(new File(".").getAbsolutePath());

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		String filePath = "C:/ITP/DOCUMENTOS_CITE/FORMATOS/";

		// String filePath = uploadDirectoryPath;
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils
					.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();

			rutaComprobante = filename;
		}
		RequestContext rc = RequestContext.getCurrentInstance();
		rc.execute("dialogFormatos.show()");
	}

	public void handleFileUploadInstructivos(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println(new File(".").getAbsolutePath());

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		String filePath = "C:/ITP/DOCUMENTOS_CITE/INSTRUCTIVOS/";

		// String filePath = uploadDirectoryPath;
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils
					.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();

			rutaComprobante = filename;
		}
		
		RequestContext rc = RequestContext.getCurrentInstance();
		rc.execute("dialogInstructivos.show()");

	}
	
	public void handleFileUploadDocumentosCite(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println(new File(".").getAbsolutePath());

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		setFile(e.getFile());
		//String filePath = "C:/ITP/TEMPEVENTOS/";

		String filePath = uploadDirectoryPath;
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils
					.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();

			rutaComprobante = filename;
		}
		

	}
	
	public void handleFileUploadProcedimientos(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println(new File(".").getAbsolutePath());

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		String filePath = "C:/ITP/DOCUMENTOS_CITE/PROCEDIMIENTOS/";

		// String filePath = uploadDirectoryPath;
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils
					.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();

			rutaComprobante = filename;
		}
		

		RequestContext rc = RequestContext.getCurrentInstance();
		rc.execute("dialogProcedimientos.show()");
	}
	
	
	public void handleFileUploadManuales(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println(new File(".").getAbsolutePath());

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		String filePath = "C:/ITP/DOCUMENTOS_CITE/MANUALES/";

		// String filePath = uploadDirectoryPath;
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils
					.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();

			rutaComprobante = filename;
		}
		
		RequestContext rc = RequestContext.getCurrentInstance();
		rc.execute("dialogManuales.show()");

	}

	public void handleFileUploadInformativo(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println(new File(".").getAbsolutePath());

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		setFile(e.getFile());
		String filePath = "C:/ITP/TEMPINFORMATIVO/";

		// String filePath = uploadDirectoryPath;
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils
					.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();

			rutaComprobante = filename;
		}
		
		RequestContext rc = RequestContext.getCurrentInstance();
		rc.execute("dialogInformativo.show()");

	}
	
	
	public void handleFileUploadPublicaciones(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println(new File(".").getAbsolutePath());

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		setFile(e.getFile());
		String filePath = "C:/ITP/TEMPEVENTOS/";

		// String filePath = uploadDirectoryPath;
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils
					.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();

			rutaComprobante = filename;
		}

	}
	
	
	public void handleFileUploadDocumentos(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println(new File(".").getAbsolutePath());

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		setFile(e.getFile());
		String filePath = "C:/ITP/TEMPEVENTOS/";

		// String filePath = uploadDirectoryPath;
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils
					.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();

			rutaComprobante = filename;
		}

	}
	
	
	
	
	public String selectorBuscarPersonal() throws Exception {
		String pagina = "";

		inicializarClases();

		pagina = "/paginas/ModuloProduccion/cite/directorio/buscarPersonal.xhtml";
		
		return pagina;
	}
	
	public void buscarPersonal() throws Exception {

		String nombreServicio = getServicioModel().getNombre() == "" ? null
				: getServicioModel().getNombre();
		String codigoServicio = getServicioModel().getCodigo() == "" ? null
				: getServicioModel().getCodigo();
		String codigoCITE = getServicioModel().getCodigoCITE() == "" ? "0"
				: getServicioModel().getCodigoCITE();

		if (getServicioModel().getCodigoCITE() == null) {
			codigoCITE = "0";

		}

		System.out.println("dATOS SERVICIO BUSQUEDA " + nombreServicio + "-"
				+ codigoServicio + "-" + codigoCITE);

		List<ServicioBO> listaServicio = new ArrayList<ServicioBO>();
		// SE ENVIA EL 6 POR DEFAULT
		listaServicio = servicioServices.buscarServicio(codigoServicio,
				nombreServicio, Integer.parseInt(codigoCITE));
		List<ServicioModel> datosServiciosModelGrid = new ArrayList<ServicioModel>();

		for (ServicioBO servicioBO : listaServicio) {
			ServicioModel servicioModel = new ServicioModel();
			servicioModel.setCodigo(servicioBO.getCodigo());
			servicioModel.setNombre(servicioBO.getNombre());
			servicioModel.setUnidad(servicioBO.getUnidad());
			servicioModel.setRequisito(servicioBO.getRequisito());
			servicioModel.setValorDeVenta(servicioBO.getValorDeVenta());
			servicioModel.setPrecioDeVenta(servicioBO.getPrecioDeVenta());

			datosServiciosModelGrid.add(servicioModel);
		}

		setDatosServiciosModelGrid(datosServiciosModelGrid);
		listarCITE();
	}
	
	public void buscarServicio() throws Exception {

		String nombreServicio = getServicioModel().getNombre() == "" ? null
				: getServicioModel().getNombre();
		String codigoServicio = getServicioModel().getCodigo() == "" ? null
				: getServicioModel().getCodigo();
		String codigoCITE = getServicioModel().getCodigoCITE() == "" ? "0"
				: getServicioModel().getCodigoCITE();

		if (getServicioModel().getCodigoCITE() == null) {
			codigoCITE = "0";

		}

		System.out.println("dATOS SERVICIO BUSQUEDA " + nombreServicio + "-"
				+ codigoServicio + "-" + codigoCITE);

		List<ServicioBO> listaServicio = new ArrayList<ServicioBO>();
		// SE ENVIA EL 6 POR DEFAULT
		listaServicio = servicioServices.buscarServicio(codigoServicio,
				nombreServicio, Integer.parseInt(codigoCITE));
		List<ServicioModel> datosServiciosModelGrid = new ArrayList<ServicioModel>();

		for (ServicioBO servicioBO : listaServicio) {
			ServicioModel servicioModel = new ServicioModel();
			servicioModel.setCodigo(servicioBO.getCodigo());
			servicioModel.setNombre(servicioBO.getNombre());
			servicioModel.setUnidad(servicioBO.getUnidad());
			servicioModel.setRequisito(servicioBO.getRequisito());
			servicioModel.setValorDeVenta(servicioBO.getValorDeVenta());
			servicioModel.setPrecioDeVenta(servicioBO.getPrecioDeVenta());

			datosServiciosModelGrid.add(servicioModel);
		}

		setDatosServiciosModelGrid(datosServiciosModelGrid);
		listarCITE();
	}

	public String selectorBuscarServicio(int modo) throws Exception {
		String pagina = "";
		inicializarClases();
		List<ServicioBO> listaServicio = new ArrayList<ServicioBO>();

		List<ServicioModel> datosServiciosModelGrid = new ArrayList<ServicioModel>();

		switch (modo) {
		case 1:
			// SE ENVIA 0 EN EL CODIGO DE CITE PARA QUE NOS OBTENGA TODOS LOS
			// SERVICIOS DE LOS CITES
			listaServicio = servicioServices.buscarServicio(null, null, 0);

			for (ServicioBO servicioBO : listaServicio) {
				ServicioModel servicioModel = new ServicioModel();
				servicioModel.setCodigo(servicioBO.getCodigo());
				servicioModel.setNombre(servicioBO.getNombre());
				servicioModel.setUnidad(servicioBO.getUnidad());
				servicioModel.setRequisito(servicioBO.getRequisito());
				servicioModel.setValorDeVenta(servicioBO.getValorDeVenta());
				servicioModel.setPrecioDeVenta(servicioBO.getPrecioDeVenta());

				datosServiciosModelGrid.add(servicioModel);
			}

			setDatosServiciosModelGrid(datosServiciosModelGrid);
			listarCITE();
			pagina = "/paginas/ModuloProduccion/cliente/servicio/buscar/buscarServicio.xhtml";
			break;
		/* @@ESTE ES EL CASO PARA PERFIL CITE */
		case 2:
			// SE ENVIA 0 EN EL CODIGO DE CITE PARA QUE NOS OBTENGA TODOS LOS
			// SERVICIOS DE LOS CITES
			listaServicio = servicioServices.buscarServicio("", "", 0);

			for (ServicioBO servicioBO : listaServicio) {
				ServicioModel servicioModel = new ServicioModel();
				servicioModel.setCodigo(servicioBO.getCodigo());
				servicioModel.setNombre(servicioBO.getNombre());
				servicioModel.setUnidad(servicioBO.getUnidad());
				servicioModel.setRequisito(servicioBO.getRequisito());
				servicioModel.setValorDeVenta(servicioBO.getValorDeVenta());
				servicioModel.setPrecioDeVenta(servicioBO.getPrecioDeVenta());

				datosServiciosModelGrid.add(servicioModel);
			}

			setDatosServiciosModelGrid(datosServiciosModelGrid);
			listarCITE();
			pagina = "/paginas/ModuloProduccion/cite/servicio/buscar/buscarServicio.xhtml";
			break;

		/* @@ESTE ES EL CASO PARA PERFIL EMPRESA */
		case 3:
			// SE ENVIA 0 EN EL CODIGO DE CITE PARA QUE NOS OBTENGA TODOS LOS
			// SERVICIOS DE LOS CITES
			listaServicio = servicioServices.buscarServicio("", "", 0);

			for (ServicioBO servicioBO : listaServicio) {
				ServicioModel servicioModel = new ServicioModel();
				servicioModel.setCodigo(servicioBO.getCodigo());
				servicioModel.setNombre(servicioBO.getNombre());
				servicioModel.setUnidad(servicioBO.getUnidad());
				servicioModel.setRequisito(servicioBO.getRequisito());
				servicioModel.setValorDeVenta(servicioBO.getValorDeVenta());
				servicioModel.setPrecioDeVenta(servicioBO.getPrecioDeVenta());

				datosServiciosModelGrid.add(servicioModel);
			}

			setDatosServiciosModelGrid(datosServiciosModelGrid);
			listarCITE();
			pagina = "/paginas/ModuloProduccion/empresa/servicio/buscar/buscarServicio.xhtml";
			break;

		}
		return pagina;
	} 

	public void guardarNuevoServicio(int opcion) {
		String pagina = "";
		ObtenerNumeroAleatorio numero = new ObtenerNumeroAleatorio();
		FormateadorFecha fecha = new FormateadorFecha();
		try {
			// if
			// (buscarUsuario(getUsuarioModel().getIdUsuario()==null?"0":getUsuarioModel().getIdUsuario()).equals("")){
			if (true) {
				String nombreServicio = getServicioModel().getNombre() == null ? ""
						: getServicioModel().getNombre();
				String citeID = getServicioModel().getCodigoCITE() == null ? ""
						: getServicioModel().getCodigoCITE();
				String unidad = getServicioModel().getUnidad() == null ? ""
						: getServicioModel().getUnidad();
				String requisito = getServicioModel().getRequisito() == null ? ""
						: getServicioModel().getRequisito();
				String valorDeVenta = getServicioModel().getValorDeVenta() == null ? ""
						: getServicioModel().getValorDeVenta();
				String precioDeVenta = getServicioModel().getPrecioDeVenta() == null ? ""
						: getServicioModel().getPrecioDeVenta();
				String codigoServicio = "";
				String codigoUbigeo = "1";

				if (citeID.equals("1")) {
					codigoServicio = "0001"
							+ fecha.formatoFechaDDMMAAAA2(new Date())
							+ String.valueOf(numero
									.obtenerNumeroAleatorioEntero());
				}

				if (citeID.equals("2")) {
					codigoServicio = "0002"
							+ fecha.formatoFechaDDMMAAAA2(new Date())
							+ String.valueOf(numero
									.obtenerNumeroAleatorioEntero());
				}
				if (citeID.equals("3")) {
					codigoServicio = "0003"
							+ fecha.formatoFechaDDMMAAAA2(new Date())
							+ String.valueOf(numero
									.obtenerNumeroAleatorioEntero());
				}
				if (citeID.equals("4")) {
					codigoServicio = "0004"
							+ fecha.formatoFechaDDMMAAAA2(new Date())
							+ String.valueOf(numero
									.obtenerNumeroAleatorioEntero());
				}

				ServicioBO servicio = new ServicioBO();
				servicio.setCodigo(codigoServicio);

				servicio.setUbigeo(new UbigeoBO());
				servicio.getUbigeo().setIdUbigeo(codigoUbigeo);
				servicio.setCite(new CITEBO());
				servicio.getCite().setCodigo(citeID);
				servicio.setEstado("1");
				servicio.setUnidad(unidad);
				servicio.setNombre(nombreServicio);
				servicio.setRequisito(requisito);
				servicio.setPrecioDeVenta(precioDeVenta);
				servicio.setValorDeVenta(valorDeVenta);

				servicioServices.nuevoServicio(servicio);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(9);
		}
		limpiarObjetos();
		mostrarMensaje(8);
		// llenarRolesObservados();

		switch (opcion) {
		case 1:
			switch (MODO_USUARIO) {
			case 1:
				pagina = "/paginas/ModuloObservados/admin/mantenimiento/usuario/nuevoUsuarioMO.xhtml";
				break;
			case 2:
				pagina = "/paginas/ModuloObservados/ocaa/mantenimiento/usuario/nuevoUsuarioMO.xhtml";
				break;
			}

		case 2:
			try {
				selectorNuevoServicio(opcion);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}

	}

	public boolean validarCampos(String titulo, String descripcionCorta,
			String descripcion) {
		boolean apto = true;

		if (titulo == "invalido" || titulo.equals("")) {
			mostrarMensajeDocumento(1);
			apto = false;
		}

		if (descripcionCorta == "invalido" || descripcionCorta.equals("")) {
			mostrarMensajeDocumento(2);
			apto = false;
		}
		
		if (descripcion == "invalido" || descripcion.equals("")) {
			mostrarMensajeDocumento(3);
			apto = false;
		} 
		return apto;
	}
	
	public boolean validarCamposDocCites(String titulo) {
		boolean apto = true;

		if (titulo == "invalido" || titulo.equals("")) {
			mostrarMensajeDocumento(1);
			apto = false;
		}
		return apto;
	}

	private void limpiarObjetos() {
		setServicioModelbi(null);
		setServicioModelbi(new ServicioModel()); 
		//setServiciomodel	
	}		 

	private void listarCITE() {
		try {

			getServicioModel().setListarCITE(citeServices.listarCITES());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void listarTiposDocumentosCite() {
		try {

			getServicioModel().setListarTipoDocumentoCite(citeServices.listarTipoDocumentoCiteBO());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void listarServicios() {

		try {

			getServicioModel().setListarCITE(citeServices.listarCITES());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String cancelar() throws Exception {
		String pagina = "";

		inicializarClases();

		listarCITE();
		
		pagina = "/paginas/ModuloProduccion/cliente/servicio/nuevo/nuevoServicio.xhtml";

		return pagina;
	}
	
	public String cancelarD() throws Exception {
		String pagina = "";

		inicializarClases();

		listarCITE();
		listarTiposDocumentosCite();
		
		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevosManuales.xhtml";

		return pagina;
	}

	private void mostrarMensaje(int opcionMensaje) {
		FacesMessage message = null;

		switch (opcionMensaje) {
		case 1:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar sólo caracteres en el campo - " + "Nombres");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 2:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar sólo caracteres en el campo - "
							+ "Apellido Paterno");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 3:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar sólo caracteres en el campo - "
							+ "Apellido Materno");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 4:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar un correo válido en el campo - " + "Correo");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 5:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar sólo números en el campo - " + "Teléfono");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 6:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar sólo números en el campo - "
							+ "Código del alumno");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 7:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"El usuario ingresado ya ha sido registrado");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 8:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"El servicio se guardo correctamente");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 9:
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al guardar el usuario");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		}
	}

	private void mostrarMensajeDocumento(int opcionMensaje) {
		FacesMessage message = null;

		switch (opcionMensaje) {
		case 1:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar los caracteres en el campo - " + "Titulo");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 2:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar los caracteres en el campo - "
							+ "Descripcion corta");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 3:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar los caracteres en el campo - "
							+ "Descripcion");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		
		case 4:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe cargar la imagen  primero - "
							+ "Ingrese la imagen");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		case 5:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe cargar la publicacion primero - "
							+ "Ingrese la publicacion");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		case 8:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"El informativo se guardo correctamente");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 9:
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al guardar la noticia");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		
		case 10:
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al guardar la publicacion");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		
		case 11:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe cargar el documento primero - "
							+ "Ingrese el documento");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		
		}
	}

	public void setServicioModelbi(ServicioModel servicioModel) {
		this.servicioModel = servicioModel;
	}

	public ServicioModel getServicioModel() {
		return servicioModel;
	}

	public void setServicioModel(ServicioModel servicioModel) {
		this.servicioModel = servicioModel;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ServicioModel> getSelectedServicios() {
		return selectedServicios;
	}

	public void setSelectedServicios(List<ServicioModel> selectedServicios) {
		this.selectedServicios = selectedServicios;
	}

	public List<ServicioModel> getDatosServiciosModelGrid() {
		return datosServiciosModelGrid;
	}

	public void setDatosServiciosModelGrid(
			List<ServicioModel> datosServiciosModelGrid) {
		this.datosServiciosModelGrid = datosServiciosModelGrid;
	}

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	public UsuarioModel getUsuarioModelSelect() {
		return usuarioModelSelect;
	}

	public void setUsuarioModelSelect(UsuarioModel usuarioModelSelect) {
		this.usuarioModelSelect = usuarioModelSelect;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public ServicioModel getServicioModelSelect() {
		return servicioModelSelect;
	}

	public void setServicioModelSelect(ServicioModel servicioModelSelect) {
		this.servicioModelSelect = servicioModelSelect;
	}

	
}
