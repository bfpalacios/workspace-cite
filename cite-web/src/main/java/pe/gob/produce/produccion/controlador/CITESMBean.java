package pe.gob.produce.produccion.controlador;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
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
	
	//datos complementarios de la pantalla
	private Date date;
	private UsuarioModel usuarioModelSelect;
	private String rutaComprobante;
	
	//constantes
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PATTERN_STRING = "([a-z]|[A-Z]|\\s)+";

	
	
	//para la lista de servicios se declara una variable list de tipo ServicioModel
	private List<ServicioModel>  datosServiciosModelGrid;
	private List<ServicioModel>  selectedServicios;
	
	
	
	//constructor
	public CITESMBean(){
		System.out.println("::::: LOADING ServicioMBean ::::::::");		
		inicializarClases();		
		new Convertidor();
		new FormateadorFecha();
		date=new Date();
		
		this.usuarioModel = new UsuarioModel();
		this.usuarioModelSelect = new UsuarioModel();
		
		
	}
	

	
	
	private void inicializarClases(){
		this.servicioModel = new ServicioModel();	
		this.usuarioModel = new UsuarioModel();
		this.usuarioModelSelect = new UsuarioModel();
		
	}
	
	
	public String selectorNuevoServicio() throws Exception{
		 String pagina = "";
			
		 inicializarClases();
		//carga el combo para los departamentos, provincia y distrito
		 cargarUbigeo();
		 //listarCITE();
		 //listarSedes();
		 
		 
		 pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoCite.xhtml";  
		 
		return pagina;		
	}
	
	
	public String selectorNuevoDependencia(int modo) throws Exception{
		System.out.println("ENTRANDO"); 
		String pagina = "";
			
		 inicializarClases();
		 
		 listarSedes();
		 
		 switch(modo){ 
		
		 /*@@ESTE ES EL CASO PARA PERFIL CITE */
		 case 1: 	
			
			 pagina = "/paginas/ModuloProduccion/cite/nuevo/nuevaDependencia.xhtml"; break;
		
		 
		/*@@ESTE ES EL CASO PARA PERFIL ADMIN */
		 case 2:  									
			
		 	pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaDependencia.xhtml"; break;
			
			
		 }
		return pagina;		
	}
	
	
	public String selectorNuevoSede(int modo) throws Exception{
		 String pagina = "";
		
		 //inicializacion de clases
		 inicializarClases();
		 
		 //carga el combo para los departamentos, provincia y distrito
		 cargarUbigeo();

		 switch(modo){ 
		
		 /*@@ESTE ES EL CASO PARA PERFIL CITE */
		 case 1: 	
			
			 pagina = "/paginas/ModuloProduccion/cite/nuevo/nuevaDependencia.xhtml"; break;
		
		 
		 /*@@ESTE ES EL CASO PARA PERFIL ADMIN */
		 case 2:  									
			
		 	pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaSede.xhtml"; break;
			
		 
		}
		return pagina;		
	}
	
	public void cargarUbigeo(){
		
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
	
	public String nuevoServicioInformativo() throws Exception{
		 
		System.out.println("nuevoServicioInformativo:INICIO");
		String pagina = "";
			
		 inicializarClases();									
			
		 pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoServicioInformativo.xhtml"; 
		 System.out.println("nuevoServicioInformativo:FIN");
		 return pagina;		
	
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
	
	public void handleFileUpload(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println (new File (".").getAbsolutePath ());
		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		//String filePath = "D:/ITP/";
		
		String filePath = uploadDirectoryPath;
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();

			rutaComprobante = filename;
		}

	}
	public void buscarServicio() throws Exception{
		
		/*FacesContext facesContext = FacesContext.getCurrentInstance();
		LoginModel login = (LoginModel) facesContext.getExternalContext().getSessionMap().get("user");
		System.out.println("login user " + login.getUsuario() + "hola" + getServicioModel().getCodigoCITE() + "que tal");
		*/
		String nombreServicio = getServicioModel().getNombre()==""?null:getServicioModel().getNombre();
		String codigoServicio = getServicioModel().getCodigo()==""?null:getServicioModel().getCodigo();
		String codigoCITE = getServicioModel().getCodigoCITE()==""?"0":getServicioModel().getCodigoCITE();
		
		if (getServicioModel().getCodigoCITE() == null){
			codigoCITE= "0";
			
		}
		
		
		
		System.out.println("dATOS SERVICIO BUSQUEDA " + nombreServicio + "-" + codigoServicio + "-" + codigoCITE);
		
		List<ServicioBO> listaServicio = new ArrayList<ServicioBO>();
		//SE ENVIA EL 6 POR DEFAULT
		listaServicio = servicioServices.buscarServicio(codigoServicio,nombreServicio, Integer.parseInt(codigoCITE));
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
	
	
	public String selectorBuscarServicio(int modo) throws Exception{
		String pagina = "";
		inicializarClases();
		List<ServicioBO> listaServicio = new ArrayList<ServicioBO>();
			
		List<ServicioModel> datosServiciosModelGrid = new ArrayList<ServicioModel>();
			
		switch(modo){ 
			case 1:  								
					//SE ENVIA 0 EN EL CODIGO DE CITE PARA QUE NOS OBTENGA TODOS LOS SERVICIOS DE LOS CITES
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
					pagina = "/paginas/ModuloProduccion/cliente/servicio/buscar/buscarServicio.xhtml"; break;
			/*@@ESTE ES EL CASO PARA PERFIL CITE */
			case 2:  
				 	//SE ENVIA 0 EN EL CODIGO DE CITE PARA QUE NOS OBTENGA TODOS LOS SERVICIOS DE LOS CITES
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
					pagina = "/paginas/ModuloProduccion/cite/servicio/buscar/buscarServicio.xhtml"; break;
					
			/*@@ESTE ES EL CASO PARA PERFIL EMPRESA */
			case 3:  
					//SE ENVIA 0 EN EL CODIGO DE CITE PARA QUE NOS OBTENGA TODOS LOS SERVICIOS DE LOS CITES
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
					pagina = "/paginas/ModuloProduccion/empresa/servicio/buscar/buscarServicio.xhtml"; break;
					
			
		}
		return pagina;		
	 }
	
	
	


	public void guardarNuevoServicio(int opcion) {
		String pagina = "";
		ObtenerNumeroAleatorio numero = new ObtenerNumeroAleatorio();
		FormateadorFecha fecha = new FormateadorFecha();
		try{
			//if (buscarUsuario(getUsuarioModel().getIdUsuario()==null?"0":getUsuarioModel().getIdUsuario()).equals("")){
			if (true){
				String nombreServicio = getServicioModel().getNombre()==null?"":getServicioModel().getNombre();
				String citeID = getServicioModel().getCodigoCITE()==null?"":getServicioModel().getCodigoCITE();
				String unidad = getServicioModel().getUnidad()==null?"":getServicioModel().getUnidad();
				String requisito = getServicioModel().getRequisito()==null?"":getServicioModel().getRequisito();
				String valorDeVenta = getServicioModel().getValorDeVenta()==null?"":getServicioModel().getValorDeVenta();
				String precioDeVenta = getServicioModel().getPrecioDeVenta()==null?"":getServicioModel().getPrecioDeVenta();
				String codigoServicio ="";
				String codigoUbigeo = "1";
				
				if(citeID.equals("1")) 
				{
					codigoServicio = "0001" + fecha.formatoFechaDDMMAAAA2(new Date()) + String.valueOf(numero.obtenerNumeroAleatorioEntero());
				}
				
				if(citeID.equals("2")) 
				{
					codigoServicio = "0002" + fecha.formatoFechaDDMMAAAA2(new Date()) + String.valueOf(numero.obtenerNumeroAleatorioEntero());
				}
				if(citeID.equals("3")) 
				{
					codigoServicio = "0003" + fecha.formatoFechaDDMMAAAA2(new Date()) + String.valueOf(numero.obtenerNumeroAleatorioEntero());
				}
				if(citeID.equals("4")) 
				{
					codigoServicio = "0004" + fecha.formatoFechaDDMMAAAA2(new Date()) + String.valueOf(numero.obtenerNumeroAleatorioEntero());
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
		}
		catch(Exception e){
			e.printStackTrace();
			mostrarMensaje(9);				
		}		
		limpiarObjetos();
		mostrarMensaje(8);	 
		
		 
		
	}
	
	
	public void guardarNuevaSede() {
		try{
			String codigoSede 		= getServicioModel().getCodigo()==null?"invalido":getServicioModel().getCodigo();
			String jefaturaSede 	= getServicioModel().getJefaturaSede()==null?"invalido":getServicioModel().getJefaturaSede();
			String emailSede = getServicioModel().getEmailSede() == null ? ""
					: validaCorreo(getServicioModel().getEmailSede()) == true ? getServicioModel()
							.getEmailSede() : "invalido";
			String nombreSede 		= getServicioModel().getNombre()==null?"invalido":getServicioModel().getNombre();
			String telefonoSede 	= getServicioModel().getTelefono()==null?"invalido":getServicioModel().getTelefono();
			String celularSede 		= getServicioModel().getCelular()==null?"invalido":getServicioModel().getNombre();
			String codigoDpto 		= getUsuarioModelSelect().getCodDepartamento()==null?"invalido":getUsuarioModelSelect().getCodDepartamento();
			String codigoProvincia 	= getUsuarioModelSelect().getCodProvincia()==null?"invalido":getUsuarioModelSelect().getCodProvincia() ;
			String codigoDistrito 	= getUsuarioModelSelect().getCodDistrito()==null?"invalido":getUsuarioModelSelect().getCodDistrito();
			String codigoUbigeo =  codigoDpto + codigoProvincia + codigoDistrito;
				
			codigoUbigeo = codigoUbigeo.equals("")?"0":codigoUbigeo;
				
				
			System.out.println("codigoSede" + codigoSede);
			System.out.println("nombreSede" + nombreSede);
			System.out.println("jefaturaSede" + jefaturaSede);
			System.out.println("emailSede" + emailSede);
			System.out.println("telefonoSede" + telefonoSede);
			System.out.println("celularSede" + celularSede);
				
			System.out.println("Codigo dpto" + codigoDpto);
			System.out.println("Codigo prov" + codigoProvincia);
			System.out.println("Codigo distrito" + codigoDistrito);
			System.out.println("Codigo ubigeo" + codigoUbigeo);
				
				
			if(validarCampos(codigoSede, nombreSede,  codigoDpto,  codigoProvincia,  codigoDistrito, "00",jefaturaSede, emailSede, "00"))
				
			{
				SedeBO sede = new SedeBO();
				
					sede.setCodigo(codigoSede);
					sede.setDescripcion(nombreSede);
					sede.setUbigeo(new UbigeoBO());
					sede.getUbigeo().setIdUbigeo(codigoUbigeo);					
					sede.setEmail(emailSede);			
					sede.setTelefono(telefonoSede);			
					sede.setJefatura(jefaturaSede);			
					sede.setCelular(celularSede);			
					
					citeServices.grabarNuevaSede(sede);
				
				limpiarObjetos();
				cargarUbigeo();
				RequestContext rc = RequestContext.getCurrentInstance();
				rc.execute("dialogNuevaSede.show()");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			mostrarMensaje(9);				
		}	 
		
		
	}
	
	 
	
	public void guardarNuevaCite() {
		try{
			 
			
			String codigoCite = getServicioModel().getCodigo()==null?"":getServicioModel().getCodigo();
			String nombreCite = getServicioModel().getNombre()==null?"":getServicioModel().getNombre();
			//String codigoSede = getUsuarioModelSelect().getCodigoDependencia()==null?"invalido":getUsuarioModelSelect().getCodigoDependencia();
			Date fechaCite = getDate();
			
			String codigoDpto 		= getUsuarioModelSelect().getCodDepartamento()==null?"invalido":getUsuarioModelSelect().getCodDepartamento();
			String codigoProvincia 	= getUsuarioModelSelect().getCodProvincia()==null?"invalido":getUsuarioModelSelect().getCodProvincia() ;
			String codigoDistrito 	= getUsuarioModelSelect().getCodDistrito()==null?"invalido":getUsuarioModelSelect().getCodDistrito();
			String codigoUbigeo =  codigoDpto + codigoProvincia + codigoDistrito;
				
			codigoUbigeo = codigoUbigeo.equals("")?"0":codigoUbigeo;
			
			System.out.println("codigoCite " + codigoCite);
			System.out.println("nombreCite " + nombreCite);
			System.out.println("codigo ubigeo " + codigoUbigeo);
			System.out.println("fechaCite"  + fechaCite);

			if(validarCampos(codigoCite, nombreCite,  "00",  "00",  "00", "00","00","00",codigoUbigeo))
			
			{
					
				
				CITEBO cite = new CITEBO();
					cite.setCodigo(codigoCite);
					cite.setDescripcion(nombreCite);  
					cite.setFecha(fechaCite);
					cite.setEstado("A");
					cite.setCodigoUbigeo(codigoUbigeo);			
					citeServices.grabarNuevaCite(cite);
				
				limpiarObjetos(); 
				listarSedes();
				RequestContext rc = RequestContext.getCurrentInstance();
				rc.execute("dialogNuevaCite.show()");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			mostrarMensaje(9);				
		}	
		
		
		
	}
	
	
	public void guardarNuevaDependencia() {
		try{
			 
			
			String codigoDependencia = getServicioModel().getCodigo()==null?"":getServicioModel().getCodigo();
			String nombreDependencia = getServicioModel().getNombre()==null?"":getServicioModel().getNombre();
			String codigoUTT = getUsuarioModelSelect().getCodigoDependencia()==null?"invalido":getUsuarioModelSelect().getCodigoDependencia();
			
			System.out.println("codigoDependencia " + codigoDependencia);
			System.out.println("nombreDependencia " + nombreDependencia);
			System.out.println("Codigo UTT" + codigoUTT);

			if(validarCampos(codigoDependencia, nombreDependencia,  "00",  "00",  "00", codigoUTT,"00","00", "00"))
			
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
		}
		catch(Exception e){
			e.printStackTrace();
			mostrarMensaje(9);				
		}	
		
		
		
	}
	
	
	private void limpiarObjetos(){
		setServicioModelbi(null);
		setServicioModelbi(new ServicioModel());
		setUsuarioModelSelect(new UsuarioModel());
		//reset();
	}
	
	public void reset() {
		RequestContext.getCurrentInstance().reset("formPrincipal:pnlGridNuevaSede");
		
	}
	
	private void listarCITE(){
		try{
			
		
			getServicioModel().setListarCITE(citeServices.listarCITES());
		}
		catch(Exception e){
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
 
	private boolean validarCampos(String codigo, String descripcion, String codigoDepartamento, String codigoProvincia, 
					String codigoDistrito, String codigoUTT, String jefaturaSede, String emailSede, String codigoUbigeo ) {
		boolean apto = true;

		if (codigo == "invalido" || codigo.equals("")) {
			mostrarMensaje(1);
			apto = false;
		}

		if (descripcion == "invalido" || descripcion.equals("")) {
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
		if (codigoUbigeo == "invalido" || codigoUbigeo.equals("")) {
			mostrarMensaje(10);
			apto = false;
		}
		 
		return apto;
	}

	private void listarSedes(){
		try{
			
		
			getUsuarioModel().setListarSedes(citeServices.listarSedes());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	 
	
	public String cancelar() throws Exception{
		 String pagina = "";
		 
		 	inicializarClases();

			listarCITE();
			pagina = "/paginas/ModuloProduccion/cliente/servicio/nuevo/nuevoServicio.xhtml"; 
			
		return pagina;		
	}
	
	private void mostrarMensaje(int opcionMensaje){
		FacesMessage message = null;		
		
		switch(opcionMensaje){
			case 1: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar los caracteres en el campo - " + "Codigo");
	        		FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 2: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar los caracteres en el campo - " + "Descripcion");
	        		FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 3: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar en el campo - " + "Departamento");
    				FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 4: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar en el campo - " + "Provincia");
    				FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 5: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar en el campo - " + "Distrito");
					FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 6: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar en el campo - " + "Sede");
					FacesContext.getCurrentInstance().addMessage(null, message); break;

			
			case 7: message = new FacesMessage(FacesMessage.SEVERITY_WARN,"", "Debe ingresar en el campo - " + "Jefatura");
					FacesContext.getCurrentInstance().addMessage(null, message); break;	
			case 8:message = new FacesMessage(FacesMessage.SEVERITY_INFO,"", "El email no tiene el formato correcto");
					FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 9:message = new FacesMessage(FacesMessage.SEVERITY_FATAL,"", "Hubo un error al guardar en la base de datos");
			FacesContext.getCurrentInstance().addMessage(null, message); break;
			
			case 10:message = new FacesMessage(FacesMessage.SEVERITY_FATAL,"", "Debe ingresar el codigo de Ubigeo ");
			FacesContext.getCurrentInstance().addMessage(null, message); break;
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
	
	
	
	
	
	

}
