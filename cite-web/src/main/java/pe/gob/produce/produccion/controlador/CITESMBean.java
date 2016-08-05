package pe.gob.produce.produccion.controlador;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
import pe.gob.produce.cite.bo.ServicioBO;
import pe.gob.produce.cite.bo.UbigeoBO;
import pe.gob.produce.produccion.core.util.Convertidor;
import pe.gob.produce.produccion.core.util.FormateadorFecha;
import pe.gob.produce.produccion.core.util.ObtenerNumeroAleatorio;
import pe.gob.produce.produccion.model.ServicioModel;
import pe.gob.produce.produccion.model.UbigeoModel;
import pe.gob.produce.produccion.model.UsuarioModel;
import pe.gob.produce.produccion.services.CITEServices;
import pe.gob.produce.produccion.services.ComunServices;
import pe.gob.produce.produccion.services.ServicioServices;

@Controller("citesMBean")
@ViewScoped
public class CITESMBean {

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

	// constantes
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PATTERN_STRING = "([a-z]|[A-Z]|\\s)+";

	// para la lista de servicios se declara una variable list de tipo
	// ServicioModel
	private List<ServicioModel> datosServiciosModelGrid;
	private List<ServicioModel> selectedServicios;
	
	//Busqueda
	private String nombreBusqueda;
	private String codigoBusqueda; 
	private Date fechaBusqueda;
	private List<CITEBO> listaCites;
	private List<SedeBO> listaSedes;
	private List<DependenciaBO> listaDependencias;
	
	private CITEBO citeActual;
	private SedeBO sedeActual;
	private DependenciaBO depActual;

	// constructor
	public CITESMBean() {
		System.out.println("::::: LOADING ServicioMBean ::::::::");
		inicializarClases();
		new Convertidor();
		new FormateadorFecha();
		date = new Date();

		this.usuarioModel = new UsuarioModel();
		this.usuarioModelSelect = new UsuarioModel();

	}

	private void inicializarClases() {
		this.servicioModel = new ServicioModel();
		this.usuarioModel = new UsuarioModel();
		this.usuarioModelSelect = new UsuarioModel();

	}

	public String selectorNuevoCite() throws Exception {
		String pagina = "";

		inicializarClases();
		// carga el combo para los departamentos, provincia y distrito
		cargarUbigeo();
		 
		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoCite.xhtml";

		return pagina;
	}

	public String selectorNuevoDependencia() throws Exception {
		System.out.println("ENTRANDO");
		String pagina = "";

		inicializarClases();

		listarSedes(); 
		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaDependencia.xhtml";
			 
		return pagina;
	}

	public String selectorNuevoSede() throws Exception {
		String pagina = "";

		// inicializacion de clases
		inicializarClases();
		// carga las cites de la BD
		listarCITE();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaSede.xhtml";

		return pagina;
	}

	public void cargarUbigeo() {

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
	}

	public String nuevoServicioInformativo() throws Exception {

		System.out.println("nuevoServicioInformativo:INICIO");
		String pagina = "";

		inicializarClases();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoServicioInformativo.xhtml";
		System.out.println("nuevoServicioInformativo:FIN");
		return pagina;

	}
	
	public String editarCites() throws Exception {
		System.out.println("editarCites:INICIO");

		inicializarClases();
		// carga el combo para los departamentos, provincia y distrito
		cargarUbigeo();
		
		String pagina = "/paginas/ModuloAdministrador/admin/cite/edicion/EditarCites.xhtml";
		System.out.println("editarCites:FIN");
		return pagina;
	}
	
	public String editarSedes() throws Exception {
		System.out.println("editarSedes:INICIO");

		inicializarClases();
		// carga las cites de la BD
		listarCITE();
		String pagina = "/paginas/ModuloAdministrador/admin/cite/edicion/EditarSedes.xhtml";
		System.out.println("editarSedes:FIN");
		return pagina;
	}
	
	public String editarDependencias() throws Exception {
		System.out.println("editarDependencias:INICIO");

		inicializarClases();
		// carga las sedes de la BD
		listarSedes();
		String pagina = "/paginas/ModuloAdministrador/admin/cite/edicion/EditarDependencias.xhtml";
		System.out.println("editarDependencias:FIN");
		return pagina;
	}

	public void actualizarlistProvincia(ValueChangeEvent e) throws Exception {
		String codDepartamento = (String) (e.getNewValue() == null ? "" : e
				.getNewValue());
		if (!codDepartamento.equals("")){
			codDepartamento = codDepartamento.substring(0, 2);
		}	
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
		if (StringUtils.isNotEmpty(codDepartamento)){
			codDepartamento = codDepartamento.substring(0, 2);
		}
		
		String codProvincia = (String) (e.getNewValue() == null ? "" : e
				.getNewValue());
		if (!codProvincia.equals("") ){
			codProvincia = codProvincia.substring(2, 4);
		}
		//codDepartamento = codDepartamento.substring(0, 2);
		
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
	
	
	
	public void actualizarSede(ValueChangeEvent e) throws Exception {
		
		String codigoUbigeo = (String) (e.getNewValue() == null ? "" : e
				.getNewValue());

		System.out.println("codigo de ubigeo " + codigoUbigeo); 

		citeActual.setCodigoUbigeo(codigoUbigeo);
 
	}
	
	public void handleFileUpload(FileUploadEvent e) throws IOException {

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
		// String filePath = "D:/ITP/";

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

	public void buscarServicio() throws Exception {

		/*
		 * FacesContext facesContext = FacesContext.getCurrentInstance();
		 * LoginModel login = (LoginModel)
		 * facesContext.getExternalContext().getSessionMap().get("user");
		 * System.out.println("login user " + login.getUsuario() + "hola" +
		 * getServicioModel().getCodigoCITE() + "que tal");
		 */
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

	}

	public void guardarNuevaSede() {
		try {
			String codigoSede = getServicioModel().getCodigo() == null ? "invalido"
					: getServicioModel().getCodigo();
			String jefaturaSede = getServicioModel().getJefaturaSede() == null ? "invalido"
					: getServicioModel().getJefaturaSede();
			String emailSede = getServicioModel().getEmailSede() == null ? ""
					: validaCorreo(getServicioModel().getEmailSede()) == true ? getServicioModel()
							.getEmailSede() : "invalido";
			String nombreSede = getServicioModel().getNombre() == null ? "invalido"
					: getServicioModel().getNombre();
			String telefonoSede = getServicioModel().getTelefono() == null ? "invalido"
					: getServicioModel().getTelefono();
			String celularSede = getServicioModel().getCelular() == null ? "invalido"
					: getServicioModel().getNombre();
			String codigoCite = getUsuarioModelSelect().getCodCite() == null ? "invalido"
					: getUsuarioModelSelect().getCodCite();
			
			String direccionSede = getServicioModel().getDireccion() == null ? "invalido"
					: getServicioModel().getDireccion();
			System.out.println("codigoSede" + codigoSede);
			System.out.println("nombreSede" + nombreSede);
			System.out.println("jefaturaSede" + jefaturaSede);
			System.out.println("emailSede" + emailSede);
			System.out.println("telefonoSede" + telefonoSede);
			System.out.println("celularSede" + celularSede);
			System.out.println("direccionSede" + direccionSede);

			System.out.println("Codigo cite" + codigoCite);
			
			if (validarCamposSede(codigoSede, nombreSede,
					jefaturaSede, emailSede, celularSede, codigoCite))

			{
				SedeBO sede = new SedeBO();

				sede.setCodigo(codigoSede);
				sede.setCodigoCite(codigoCite);
				sede.setDescripcion(nombreSede);
				sede.setEmail(emailSede);
				sede.setTelefono(telefonoSede);
				sede.setJefatura(jefaturaSede);
				sede.setCelular(celularSede);
				sede.setDireccion(direccionSede);
				citeServices.grabarNuevaSede(sede);

				limpiarObjetos();
				cargarUbigeo();
				RequestContext rc = RequestContext.getCurrentInstance();
				rc.execute("dialogNuevaSede.show()");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(11);
		}

	}

	public void guardarNuevaCite() {
		try {

			Date fechaCite = getDate();
			String codigoCite = getServicioModel().getCodigo() == null ? ""
					: getServicioModel().getCodigo();
			String nombreCite = getServicioModel().getNombre() == null ? ""
					: getServicioModel().getNombre();			

			String codigoDpto = getUsuarioModelSelect().getCodDepartamento() == null ? "invalido"
					: getUsuarioModelSelect().getCodDepartamento();
			String codigoProvincia = getUsuarioModelSelect().getCodProvincia() == null ? "invalido"
					: getUsuarioModelSelect().getCodProvincia();
			String codigoDistrito = getUsuarioModelSelect().getCodDistrito() == null ? "invalido"
					: getUsuarioModelSelect().getCodDistrito();
			String codigoUbigeo = codigoDpto + codigoProvincia + codigoDistrito;

			codigoUbigeo = codigoUbigeo.equals("") ? "0" : codigoUbigeo;

			System.out.println("codigoCite " + codigoCite);
			System.out.println("nombreCite " + nombreCite);
			System.out.println("codigo ubigeo " + codigoUbigeo);
			System.out.println("fechaCite" + fechaCite);

			if (validarCamposCite(codigoCite, nombreCite, codigoDpto,
					codigoProvincia, codigoDistrito, codigoUbigeo))

			{

				CITEBO cite = new CITEBO();
				cite.setCodigo(codigoCite);
				cite.setDescripcion(nombreCite);
				cite.setFecha(fechaCite);
				cite.setEstado("A");
				cite.setCodigoUbigeo(codigoUbigeo);
				citeServices.grabarNuevaCite(cite);

				limpiarObjetos();
				//listarSedes();
				cargarUbigeo();
				RequestContext rc = RequestContext.getCurrentInstance();
				rc.execute("dialogNuevaCite.show()");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(11);
		}
	}
	
	public void actualizarCite(CITEBO cite) {
		try {
			int contador=0;
			UbigeoBO ubigeoCite = new UbigeoBO();
			System.out.println("ACTUALIZAR CITE: ");
			System.out.println("codigoCite " + cite.getCodigo());
			System.out.println("nombreCite " + cite.getDescripcion());
			System.out.println("codigo ubigeo " + cite.getCodigoUbigeo());
			System.out.println("fechaCite" + cite.getFecha());

			if (validarCamposCite(cite.getCodigo(), cite.getDescripcion(), "test",
					"test", "test", cite.getCodigoUbigeo())){

				//cite.setCodigoUbigeo(codigoUbigeo);
				int rs = citeServices.actualizarCite(cite);
				
				for(CITEBO citebo: getListaCites()){
					
					if(cite.getCodigoUbigeo().equals(citebo.getCodigoUbigeo())){
					
						ubigeoCite = comunServices.buscarUbigeo(cite.getCodigoUbigeo());
						listaCites.get(contador).setUbigeo(ubigeoCite); 
					}
					contador ++;
				}
				// carga el combo para los departamentos, provincia y distrito
				cargarUbigeo();
				//mostrarMensaje(18);
				if(rs < 1){
					mostrarMensaje(16);
				}
				//citeActual.setCodigoUbigeo(codigoUbigeo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(16);
		}
	}
	
	public void eliminarCite(CITEBO cite) {
		try {

			int rs = citeServices.eliminarCite(cite.getId());
			if(rs < 1){
				mostrarMensaje(17);
			}
			for (Iterator<CITEBO> iter = listaCites.listIterator(); iter.hasNext(); ) {
				CITEBO sd = iter.next();
			    if (sd.getId() == cite.getId()) {
			        iter.remove();
			        break;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(17);
		}
	}
	
	public void actualizarSede(SedeBO sede) {
		try {
			System.out.println("ACTUALIZAR SEDE: ");
			System.out.println("codigo sede " + sede.getCodigo());
			System.out.println("nombre sede " + sede.getDescripcion());
			System.out.println("codigo ubigeo " + sede.getCodigoCite());
			System.out.println("Telefono " + sede.getTelefono());
			System.out.println("Celular " + sede.getCelular());
			System.out.println("Jefatura " + sede.getJefatura());
			System.out.println("Email " + sede.getEmail());

			if (validarCamposCite(sede.getCodigo(), sede.getDescripcion(), "test",
					"test", "test", sede.getCodigoCite())){
				int rs = citeServices.actualizarSede(sede);
				if(rs < 1){
					mostrarMensaje(19);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(19);
		}
	}
	
	public void eliminarSede(SedeBO sede) {
		try {

			int rs = citeServices.eliminarSede(sede.getId());
			if(rs < 1){
				mostrarMensaje(20);
			}
			for (Iterator<SedeBO> iter = listaSedes.listIterator(); iter.hasNext(); ) {
				SedeBO sd = iter.next();
			    if (sd.getId() == sede.getId()) {
			        iter.remove();
			        break;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(20);
		}
	}
	
	public void actualizarDependencia(DependenciaBO dep) {
		try {
			System.out.println("ACTUALIZAR DEPENDENCIA: ");
			String codigoUTT = getUsuarioModelSelect().getCodigoDependencia() == null ? "invalido"
					: getUsuarioModelSelect().getCodigoDependencia();

			System.out.println("codigoDependencia " + dep.getCodigo());
			System.out.println("nombreDependencia " + dep.getDescripcion());
			System.out.println("Codigo UTT" + codigoUTT);
			
			if (validarCamposDepedencia(dep.getCodigo(), dep.getDescripcion(), codigoUTT)){
				dep.setSede(new SedeBO());
				dep.getSede().setCodigo(codigoUTT);
				int rs = citeServices.actualizarDependencia(dep);				
				if(rs < 1){
					mostrarMensaje(21);
				}else{
					for (DependenciaBO depen : listaDependencias) {
						if(dep.getId() == depen.getId()){
							String desc = "";
							for (SedeBO sede : getUsuarioModel().getListarSedes()) {
								if(sede.getCodigo().equals(codigoUTT)){
									desc = sede.getDescripcion();
									break;
								}
							}
							depen.getSede().setDescripcion(desc);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(21);
		}
	}
	
	public void eliminarDependencia(DependenciaBO dep) {
		try {

			int rs = citeServices.eliminarDependencia(dep.getId());
			if(rs < 1){
				mostrarMensaje(22);
			}
			for (Iterator<DependenciaBO> iter = listaDependencias.listIterator(); iter.hasNext(); ) {
				DependenciaBO depen = iter.next();
			    if (depen.getId() == dep.getId()) {
			        iter.remove();
			        break;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(22);
		}
	}
	
	public void buscarCites(){
		
		String nombre = getNombreBusqueda();
		String codigo = getCodigoBusqueda();
		Date fecha = getFechaBusqueda();
		UbigeoBO ubigeoCite = new UbigeoBO(); 
		int contador =0;
		// carga el combo para los departamentos, provincia y distrito
		cargarUbigeo();
		getUsuarioModelSelect().setCodDepartamento("25");	 
		
		// this should be gone in a logger
		System.out.println("DATOS BUSQUEDA DE CITES: " + nombre + " " + codigo + " " + fecha);
		listaCites = new ArrayList<>();
		try {
			listaCites = citeServices.buscarCites(codigo, nombre, fecha);			
			for(CITEBO cite: listaCites){				
				ubigeoCite = comunServices.buscarUbigeo(cite.getCodigoUbigeo());
				listaCites.get(contador).setUbigeo(ubigeoCite); 				
				contador ++;
			}			
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error en la busqueda de Cites "  + nombre + " " + codigo + " " + fecha);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}		
		this.setListaCites(listaCites);
	}
	
	public void buscarSedes(){
		try {			
			String codigoSede = getServicioModel().getCodigo() == null ? "invalido"
					: getServicioModel().getCodigo();
			String nombreSede = getServicioModel().getNombre() == null ? "invalido"
					: getServicioModel().getNombre();
			String codigoCite = getUsuarioModelSelect().getCodCite() == null ? "invalido"
					: getUsuarioModelSelect().getCodCite();
			// this should be gone in a logger
			System.out.println("DATOS BUSQUEDA DE SEDES: " + codigoCite + " " + nombreSede + " " + codigoSede);
			System.out.println("codigoSede" + codigoSede);
			System.out.println("nombreSede" + nombreSede);
			System.out.println("Codigo cite" + codigoCite);
			
			if(StringUtils.isNotEmpty(codigoCite)){
				listaCites = citeServices.buscarCites(codigoCite, "", null);
			}
			String codSede_cite = "";
			
			if(listaCites != null){
			
				if(listaCites.size() > 0 || listaCites != null){
					codSede_cite = listaCites.get(0).getCodigoUbigeo();
					codigoCite = "";
				}
			}
			listaSedes = citeServices.buscarSedes(codigoCite, nombreSede, codSede_cite);
			
			for (SedeBO sede : listaSedes) {
				if(listaCites != null){
					if(listaCites.size() > 0){
						sede.setDescripcionCite(listaCites.get(0).getDescripcion());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error en la busqueda de Sedes " );
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		this.setListaSedes(listaSedes);
	}
	
	public void buscarDependencias(){
		try {			
			String codigoDep = getServicioModel().getCodigo() == null ? "invalido"
					: getServicioModel().getCodigo();
			String nombreDep = getServicioModel().getNombre() == null ? "invalido"
					: getServicioModel().getNombre();
			String codigoSede = getUsuarioModelSelect().getCodigoDependencia() == null ? "invalido"
					: getUsuarioModelSelect().getCodigoDependencia();
			// this should be gone in a logger
			System.out.println("DATOS BUSQUEDA DE DEPENDENCIAS: " + codigoDep + " " + nombreDep + " " + codigoSede);
			System.out.println("codigoSede" + codigoSede);
			System.out.println("nombre Dependenia" + nombreDep);
			System.out.println("Codigo Dependencia" + codigoDep);
			
			listaDependencias = citeServices.buscarDependencias(codigoDep, nombreDep, codigoSede);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error en la busqueda de Dependencias " );
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		this.setListaDependencias(listaDependencias);
	}

	public void guardarNuevaDependencia() {
		try {

			String codigoDependencia = getServicioModel().getCodigo() == null ? ""
					: getServicioModel().getCodigo();
			String nombreDependencia = getServicioModel().getNombre() == null ? ""
					: getServicioModel().getNombre();
			String codigoUTT = getUsuarioModelSelect().getCodigoDependencia() == null ? "invalido"
					: getUsuarioModelSelect().getCodigoDependencia();

			System.out.println("codigoDependencia " + codigoDependencia);
			System.out.println("nombreDependencia " + nombreDependencia);
			System.out.println("Codigo UTT" + codigoUTT);

			if (validarCamposDepedencia(codigoDependencia, nombreDependencia, codigoUTT))

			{

				DependenciaBO dependencia = new DependenciaBO();
				dependencia.setCodigo(codigoDependencia);
				dependencia.setDescripcion(nombreDependencia);
				dependencia.setSede(new SedeBO());
				dependencia.getSede().setCodigo(codigoUTT);

				citeServices.grabarNuevaDependencia(dependencia);

				limpiarObjetos();
				listarSedes();
				RequestContext rc = RequestContext.getCurrentInstance();
				rc.execute("dialogNuevaDependencia.show()");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(11);
		}

	}

	private void limpiarObjetos() {
		setServicioModelbi(null);
		setServicioModelbi(new ServicioModel());
		setUsuarioModelSelect(new UsuarioModel());
		
		setUsuarioModel(new UsuarioModel());
		// reset();
	}

	public void reset() {
		RequestContext.getCurrentInstance().reset(
				"form:formPrincipal:pnlGridNuevaSede");

	}

	private void listarCITE() {
		try {
			inicializarClases();

			getServicioModel().setListarCITE(citeServices.listarCITES());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validaCorreo(String correo) {
		boolean correoValido = false;
		try {
			Pattern pattern = Pattern.compile(PATTERN_EMAIL);
			Matcher matcher = pattern.matcher(correo);
			correoValido = matcher.matches();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return correoValido;
	}

	private boolean validarCamposCite(String codigoCite, String nombreCite,
			String codigoDepartamento, String codigoProvincia,
			String codigoDistrito, String codigoUbigeo) {
		boolean apto = true;

		if (codigoCite == "invalido" || codigoCite.equals("")) {
			mostrarMensaje(1);
			apto = false;
		}

		if (nombreCite == "invalido" || nombreCite.equals("")) {
			mostrarMensaje(2);
			apto = false;
		}
		if (codigoDepartamento == "invalido" || codigoDepartamento.equals("")) {
			mostrarMensaje(3);
			apto = false;
		}
		if (codigoProvincia == "invalido" || codigoProvincia.equals("")) {
			mostrarMensaje(4);
			apto = false;
		}
		if (codigoDistrito == "invalido" || codigoDistrito.equals("")) {
			mostrarMensaje(5);
			apto = false;
		} 
		if (codigoUbigeo == "invalido" || codigoUbigeo.equals("")
				|| codigoUbigeo.trim().equals("invalido")) {
			mostrarMensaje(10);
			apto = false;
		} 
		return apto;
	}
	 
	private boolean validarCamposDepedencia(String codigoDependencia, String nombreDependencia, String codigoUTT) {
			boolean apto = true;

			if (codigoDependencia == "invalido" || codigoDependencia.equals("")) {
				mostrarMensaje(13);
				apto = false;
			}

			if (nombreDependencia == "invalido" || nombreDependencia.equals("")) {
				mostrarMensaje(14);
				apto = false;
			}  
			if (codigoUTT == "invalido" || codigoUTT.equals("")) {
				mostrarMensaje(15);
				apto = false;
			} 
			return apto;
		}	
	private boolean validarCamposSede(String codigoSede, String nombreSede, String jefaturaSede,
			String emailSede, String celularSede, String codigoCite) {
		boolean apto = true;

		if (codigoSede == "invalido" || codigoSede.equals("")) {
			mostrarMensaje(6);
			apto = false;
		}

		if (nombreSede == "invalido" || nombreSede.equals("")) {
			mostrarMensaje(7);
			apto = false;
		}  
		if (jefaturaSede == "invalido" || jefaturaSede.equals("")) {
			mostrarMensaje(8);
			apto = false;
		}
		if (emailSede == "invalido" || emailSede.equals("")) {
			mostrarMensaje(9);
			apto = false;
		} 
		if (codigoCite == "invalido" || codigoCite.equals("")) {
			mostrarMensaje(10);
			apto = false;
		}
		return apto;
	}
	
	private boolean validarCampos(String codigo, String descripcion,
			String codigoDepartamento, String codigoProvincia,
			String codigoDistrito, String codigoUTT, String jefaturaSede,
			String emailSede, String codigoUbigeo, String codigoCite) {
		boolean apto = true;

		if (codigo == "invalido" || codigo.equals("")) {
			mostrarMensaje(6);
			apto = false;
		}

		if (descripcion == "invalido" || descripcion.equals("")) {
			mostrarMensaje(7);
			apto = false;
		}
		if (codigoDepartamento == "invalido" || codigoDepartamento.equals("")) {
			mostrarMensaje(8);
			apto = false;
		}
		if (codigoProvincia == "invalido" || codigoProvincia.equals("")) {
			mostrarMensaje(9);
			apto = false;
		}
		if (codigoDistrito == "invalido" || codigoDistrito.equals("")) {
			mostrarMensaje(5);
			apto = false;
		}
		if (codigoUTT == "invalido" || codigoUTT.equals("")) {
			mostrarMensaje(6);
			apto = false;
		}
		if (jefaturaSede == "invalido" || jefaturaSede.equals("")) {
			mostrarMensaje(7);
			apto = false;
		}
		if (emailSede == "invalido" || emailSede.equals("")) {
			mostrarMensaje(8);
			apto = false;
		}
		if (codigoUbigeo == "invalido" || codigoUbigeo.equals("")
				|| codigoUbigeo.trim().equals("invalido")) {
			mostrarMensaje(10);
			apto = false;
		}
		if (codigoCite == "invalido" || codigoCite.equals("")) {
			mostrarMensaje(11);
			apto = false;
		}
		return apto;
	}

	private void listarSedes() {
		try {

			getUsuarioModel().setListarSedes(citeServices.listarSedes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String cancelarCite() throws Exception {
		String pagina = "";

		inicializarClases();
		// carga el combo para los departamentos, provincia y distrito
		cargarUbigeo();
		 
		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoCite.xhtml";

		return pagina;
	}
	
	public String cancelarSede() throws Exception {
		String pagina = "";

		// inicializacion de clases
		inicializarClases();
		// carga las cites de la BD
		listarCITE();

		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaSede.xhtml";

		return pagina;
	}
	
	public String cancelarDependencia() throws Exception {
		String pagina = "";

		inicializarClases();

		listarSedes(); 
		pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaDependencia.xhtml";
			 
		return pagina;
	}

	private void mostrarMensaje(int opcionMensaje) {
		FacesMessage message = null;

		switch (opcionMensaje) {
		case 1:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar los caracteres en el campo - " + "Codigo Cite");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 2:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar los caracteres en el campo - "
							+ "Nombre Cite");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 3:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe elegir el combo - " + "Departamento");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 4:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe elegir el combo - " + "Provincia");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 5:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe elegir el combo - " + "Distrito");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 6:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar los caracteres en el campo - " + "Codigo Sede");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		case 7:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar los caracteres en el campo - " + "Nombre Sede");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
 

		case 8:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar los caracteres en el campo - " + "Jefe de la Sede");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 9:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"El email no tiene el formato correcto");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		case 10:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe elegir el combo de la cite ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 11:
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al guardar en la base de datos");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		case 12:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Se registro correctamente ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		
		case 13:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar en el campo - " + "Codigo de Dependencia");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		
		case 14:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar en el campo - " + "Descripcion de la Dependencia");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
			
		case 15:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar en el campo - " + "Codigo de la UTT ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 16:
			message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al actualizar CITE");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 17:
			message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al eliminar CITE");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
			
		case 18:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Se actualizo correctamente ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 19:
			message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al actualizar Sede");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 20:
			message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al eliminar Sede");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 21:
			message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al actualizar Dependencia");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 22:
			message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al eliminar Dependencia");
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

	public String getNombreBusqueda() {
		return nombreBusqueda;
	}

	public void setNombreBusqueda(String nombreBusqueda) {
		this.nombreBusqueda = nombreBusqueda;
	}

	public String getCodigoBusqueda() {
		return codigoBusqueda;
	}

	public void setCodigoBusqueda(String codigoBusqueda) {
		this.codigoBusqueda = codigoBusqueda;
	}

	public Date getFechaBusqueda() {
		return fechaBusqueda;
	}

	public void setFechaBusqueda(Date fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}

	public List<CITEBO> getListaCites() {
		return listaCites;
	}

	public void setListaCites(List<CITEBO> listaCites) {
		this.listaCites = listaCites;
	}

	public CITEBO getCiteActual() {
		return citeActual;
	}

	public void setCiteActual(CITEBO citeActual) {
		this.citeActual = citeActual;
	}

	public List<SedeBO> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<SedeBO> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public SedeBO getSedeActual() {
		return sedeActual;
	}

	public void setSedeActual(SedeBO sedeActual) {
		this.sedeActual = sedeActual;
	}

	public List<DependenciaBO> getListaDependencias() {
		return listaDependencias;
	}

	public void setListaDependencias(List<DependenciaBO> listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	public DependenciaBO getDepActual() {
		return depActual;
	}

	public void setDepActual(DependenciaBO depActual) {
		this.depActual = depActual;
	}
	
}
