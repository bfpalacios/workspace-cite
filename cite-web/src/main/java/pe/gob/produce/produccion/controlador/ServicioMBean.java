package pe.gob.produce.produccion.controlador;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
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
import pe.gob.produce.cite.bo.ServicioBO;
import pe.gob.produce.cite.bo.InformativoBO;
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
	
	//datos complementarios de la pantalla
	private Date date;
	private UsuarioModel usuarioModelSelect;
	private String rutaComprobante;
	
	//constantes
	private int MODO_USUARIO;
	private static int MODO_ADMIN = 1;
	private static int MODO_EMPLEADO = 2;
	
	
	//para la lista de servicios se declara una variable list de tipo ServicioModel
	private List<ServicioModel>  datosServiciosModelGrid;
	private List<ServicioModel>  selectedServicios;
	
	
	
	//constructor
	public ServicioMBean(){
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
		
	}
	
	
	public String selectorNuevoServicio(int modo) throws Exception{
		 String pagina = "";
			
		 inicializarClases();

		 listarCITE();

		 //listarServicios();
		 switch(modo){ 
		 case 1: 	
			
			pagina = "/paginas/ModuloProduccion/cliente/servicio/nuevo/nuevoServicio.xhtml"; break;
		
		 /*@@ESTE ES EL CASO PARA PERFIL CITE */
		 case 2:  						
			 
			//listarMuestras(); 
			pagina = "/paginas/ModuloProduccion/cite/servicio/nuevo/nuevoServicio.xhtml"; break;
		 
		/*@@ESTE ES EL CASO PARA PERFIL EMPRESA */
		 case 3:  									
			
		 	pagina = "/paginas/ModuloProduccion/empresa/servicio/nuevo/nuevoServicio.xhtml"; break;
	 
		 	/*@@ESTE ES EL CASO PARA PERFIL EMPRESA */
		 case 4:  									
			
		 	pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoCite.xhtml"; break;
	 
		 
		 }
		return pagina;		
	}
	
	
	public String selectorNuevoDependencia(int modo) throws Exception{
		System.out.println("ENTRANDO"); 
		String pagina = "";
			
		 inicializarClases();

		 switch(modo){ 
		
		 /*@@ESTE ES EL CASO PARA PERFIL CITE */
		 case 1: 	
			
			 pagina = "/paginas/ModuloProduccion/cite/nuevo/nuevaDependencia.xhtml"; break;
		
		 
		/*@@ESTE ES EL CASO PARA PERFIL ADMIN */
		 case 2:  									
			
		 	pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaDependencia.xhtml"; break;
			
		 case 3:  									
				
			 pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaSede.xhtml"; break;
			
		 }
		return pagina;		
	}
	
	
	public String selectorNuevoSede(int modo) throws Exception{
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
				// ubigeo.setProvincia(ubigeoBO.getProvincia());
				// ubigeo.setDistrito(ubigeoBO.getDistrito());
				listaUbigeoModel.add(ubigeo);
			}

		getUsuarioModel().setListUbigeo(listaUbigeoModel);

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
	
	public String nuevoServicioInformativo() throws Exception{
		 
		System.out.println("nuevoServicioInformativo:INICIO");
		String pagina = "";
			
		 inicializarClases();									
			
		 pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevoServicioInformativo.xhtml"; 
		 System.out.println("nuevoServicioInformativo:FIN");
		 return pagina;		
	
	}
	
	public String nuevosManuales() throws Exception{
		 
		System.out.println("nuevosManuales:INICIO");
		String pagina = "";
			
		 inicializarClases();									
			
		 pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevosManuales.xhtml"; 
		 System.out.println("nuevosManuales:FIN");
		 return pagina;		
	
	}
	
	public String documentosITP() throws Exception{
		 
		System.out.println("documentosITP:INICIO");
		String pagina = "";
			
		 inicializarClases();									
			
		 pagina = "/paginas/ModuloAdministrador/admin/cite/documentos/downloadManualesITP.xhtml"; 
		 System.out.println("documentosITP:FIN");
		 return pagina;		
	
	}
	
	public void invocarServletDocumentos() throws Exception{
		System.out.println("invocarServletDocumentos:INICIO");
		
		/*FacesContext context = FacesContext.getCurrentInstance();
	    try {
	       context.getExternalContext().dispatch("ListarDirectorios");
	    }catch (Exception e) {
	       e.printStackTrace();
	    }
	    finally{
	       context.responseComplete();
	    }	*/
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(ec.getRequestContextPath() + "/ListarDirectorios");
	    
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
	
	
	
	public void handleFileUploadManuales(FileUploadEvent e) throws IOException {

		System.out.println("RUTA DEL PROYECTO");
		System.out.println (new File (".").getAbsolutePath ());
		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String deploymentDirectoryPath = servletContext.getRealPath("/");
		String uploadDirectoryPath = deploymentDirectoryPath + "upload/";
		System.out.println("RUTA: " + uploadDirectoryPath);
		/*		
		*/
		UploadedFile uploadedPhoto = e.getFile();
		String filePath = "C:/ITP/DOCUMENTOS_CITE/MANUALES/";
		
		//String filePath = uploadDirectoryPath;
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
		String filePath = "C:/ITP/";
		
		//String filePath = uploadDirectoryPath;
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
	
	
	


	
	
	
	
	public void guardarNuevoServicioInformativo() {
		String pagina = "";
		try{
				Date fecha = getDate();
				String titulo = getServicioModel().getTituloInformativo()==null?"":getServicioModel().getTituloInformativo();
				String descripcionCorta = getServicioModel().getDescripcionCorta()==null?"":getServicioModel().getDescripcionCorta();;
				String descrcipcion = getServicioModel().getDescripcion();
				
				InformativoBO servicio = new InformativoBO();
				
				
				
							
				//citeServices.grabarNuevaDependencia(dependencia);
		}
		catch(Exception e){
			e.printStackTrace();
			mostrarMensaje(9);				
		}	
		limpiarObjetos();
		RequestContext rc = RequestContext.getCurrentInstance();
		rc.execute("dialogNuevoInformativo.show()");
		
		
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
		//llenarRolesObservados();
		
		switch(opcion){
			case 1: switch(MODO_USUARIO){
						case 1: pagina = "/paginas/ModuloObservados/admin/mantenimiento/usuario/nuevoUsuarioMO.xhtml"; break;
						case 2: pagina = "/paginas/ModuloObservados/ocaa/mantenimiento/usuario/nuevoUsuarioMO.xhtml"; break;
					}
			
			case 2: 
			try {
				selectorNuevoServicio(opcion);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} break;
						
									
		}	
		
	}
	
	
	private void limpiarObjetos(){
		setServicioModelbi(null);
		setServicioModelbi(new ServicioModel());
	}

	private void listarCITE(){
		try{
			
		
			getServicioModel().setListarCITE(citeServices.listarCITES());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
		
	private void listarServicios(){
		
		try{
				
			getServicioModel().setListarCITE(citeServices.listarCITES());
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
			case 1: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar sólo caracteres en el campo - " + "Nombres");
	        		FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 2: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar sólo caracteres en el campo - " + "Apellido Paterno");
	        		FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 3: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar sólo caracteres en el campo - " + "Apellido Materno");
    				FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 4: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar un correo válido en el campo - " + "Correo");
    				FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 5: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar sólo números en el campo - " + "Teléfono");
					FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 6: message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Debe ingresar sólo números en el campo - " + "Código del alumno");
					FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 7: message = new FacesMessage(FacesMessage.SEVERITY_WARN,"", "El usuario ingresado ya ha sido registrado");
					FacesContext.getCurrentInstance().addMessage(null, message); break;	
			case 8: message = new FacesMessage(FacesMessage.SEVERITY_INFO,"", "El servicio se guardo correctamente");
					FacesContext.getCurrentInstance().addMessage(null, message); break;
			case 9: message = new FacesMessage(FacesMessage.SEVERITY_FATAL,"", "Hubo un error al guardar el usuario");
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
