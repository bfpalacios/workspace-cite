package pe.gob.produce.produccion.controlador;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.CotizacionBO;
import pe.gob.produce.cite.bo.CotizacionDetalleBO;
import pe.gob.produce.cite.bo.ServicioBO;
import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.produccion.core.util.Convertidor;
import pe.gob.produce.produccion.core.util.FormateadorFecha;
import pe.gob.produce.produccion.core.util.ObtenerNumeroAleatorio;
import pe.gob.produce.produccion.model.CotizacionModel;
import pe.gob.produce.produccion.model.EmpresaModel;
import pe.gob.produce.produccion.model.LoginModel;
import pe.gob.produce.produccion.model.ServicioModel;
import pe.gob.produce.produccion.model.UsuarioModel;
import pe.gob.produce.produccion.services.CITEServices;
import pe.gob.produce.produccion.services.ComunServices;
import pe.gob.produce.produccion.services.CotizacionServices;
import pe.gob.produce.produccion.services.ServicioServices;

@Controller("cotizacionMBean")
@ViewScoped
public class CotizacionMBean {

	// ctrl + shit + o importas todas las clases que estan otros paquetes
	@Autowired
	private ServicioModel servicioModel;

	@Autowired
	private CotizacionModel cotizacionModel;
	
	@Autowired
	private CITEServices citeServices;

	@Autowired
	private CotizacionServices cotizacionServices;

	@Autowired
	private ComunServices comunServices;

	@Autowired
	private ServicioServices servicioServices;

	// datos complementarios de la pantalla
	private Date date;

	// constantes
	

	// para la lista de servicios se declara una variable list de tipo
	// ServicioModel
	private List<ServicioModel> datosServiciosModelGrid;
	private List<ServicioModel> selectedServicios;
	private List<ServicioModel> servicioCotizacion;

	private Double totalSuma;
	private Double totalSumaCotizar;

	// Constructor
	public CotizacionMBean() {
		System.out.println("::::: LOADING ServicioMBean ::::::::");
		inicializarClases();
		new Convertidor();
		new FormateadorFecha();
		date = new Date();
		this.totalSuma = 0.0;
		this.totalSumaCotizar = 0.0;
	}

	private void inicializarClases() {
		this.servicioModel = new ServicioModel();
		setServicioModel(new ServicioModel());
		setCotizacionModel(new CotizacionModel());
		
		setSelectedServicios(new ArrayList<ServicioModel>());
		
		this.cotizacionModel = new CotizacionModel();
	}

	public String selectorNuevaCotizacion(int modo) throws Exception {
		String pagina = "";
		ObtenerNumeroAleatorio numero = new ObtenerNumeroAleatorio();
		FormateadorFecha fecha = new FormateadorFecha();
		
		inicializarClases();
		
		List<ServicioBO> listaServicio = new ArrayList<ServicioBO>();

		List<ServicioModel> datosServiciosModelGrid = new ArrayList<ServicioModel>();
		
		int codigo = cotizacionServices.obtenerCodigoCotizacion();
		
		if (codigo == 0){
			codigo = 1;
			getCotizacionModel().setDescripcion("000"+ String.valueOf(codigo) +"-" + fecha.obtenerFechaAnio(new Date()));
		} 
		if (codigo > 0){
			codigo = codigo + 1;
			getCotizacionModel().setDescripcion("000"+ String.valueOf(codigo) +"-" + fecha.obtenerFechaAnio(new Date()));
		} 
		
		switch (modo) {
		case 1:
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
				servicioModel.setDescripcionCITE(servicioBO.getCite().getDescripcion());
				datosServiciosModelGrid.add(servicioModel);
			}

			setDatosServiciosModelGrid(datosServiciosModelGrid);
			listarCITE();

			pagina = "/paginas/ModuloProduccion/cliente/cotizacion/nuevo/nuevaCotizacion.xhtml";
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

				servicioModel.setDescripcionCITE(servicioBO.getCite().getDescripcion());
				servicioModel.setValorDeVenta(servicioBO.getValorDeVenta());
				servicioModel.setPrecioDeVenta(servicioBO.getPrecioDeVenta());

				datosServiciosModelGrid.add(servicioModel);
			}

			setDatosServiciosModelGrid(datosServiciosModelGrid);
			listarCITE();
			pagina = "/paginas/ModuloProduccion/cite/servicio/nuevo/nuevoServicio.xhtml";
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

				servicioModel.setDescripcionCITE(servicioBO.getCite().getDescripcion());
				servicioModel.setValorDeVenta(servicioBO.getValorDeVenta());
				servicioModel.setPrecioDeVenta(servicioBO.getPrecioDeVenta());

				datosServiciosModelGrid.add(servicioModel);
			}

			
			setDatosServiciosModelGrid(datosServiciosModelGrid);
			listarCITE();

			pagina = "/paginas/ModuloProduccion/empresa/cotizacion/nuevo/nuevaCotizacion.xhtml";
			break;

		}
		return pagina;
	}
	
	
	public void enviarCotizacion() throws Exception {
		
		String pagina = "";
		
		//se obtiene el usuario de sesion
		FacesContext facesContext = FacesContext.getCurrentInstance();
		LoginModel login = (LoginModel)
		facesContext.getExternalContext().getSessionMap().get("user");
		
		//se crea la claseoredir para obtener el idUsuario
		UsuarioBO usurio = new UsuarioBO();		
		usurio = comunServices.buscarUsuario(login.getUsuario());
		String idUsuario="";
		idUsuario = usurio.getIdUsuario();
		
		//inicializacion de clases core
		ObtenerNumeroAleatorio numero = new ObtenerNumeroAleatorio();
		FormateadorFecha fecha = new FormateadorFecha();
		
		//inicializacion de variables
		int sede =0;
		String ubigeo = "";
		String citeID = "1";
		String descripcionCITE = getServicioModel().getDescripcionCITE();
		
		if(descripcionCITE.equals("Cite Madera") ){
			citeID = "1";
		}
		if(descripcionCITE.equals("Cite Calzado") ){
			citeID = "2";
		}
		if(descripcionCITE.equals("Cite Agroindustrial") ){
			citeID = "3";
		}
		if(descripcionCITE.equals("Cite Pesquero") ){
			citeID = "4";
		}
		
		
		//esto luego en la tabla cite se debe cambiar en vez de 1 es 10 , 2 es 20 , 3 es 30 , 4 es 40
		int codigoCite = Integer.parseInt(citeID);
		codigoCite = codigoCite*10;
		List<ServicioBO> listaServicio = new ArrayList<ServicioBO>();

		List<CotizacionModel> datosServiciosModelGrid = new ArrayList<CotizacionModel>();
		
		if(citeID.equals("1")){
			ubigeo = "140141";
			
		}
		if(citeID.equals("2")){
			ubigeo = "140122";
			
		}
		if(citeID.equals("3")){
			ubigeo = "100106";
			
		}
		if(citeID.equals("4")){
			ubigeo = "240106";
			
		}
		
		sede = cotizacionServices.obtenerCiteSede(ubigeo, descripcionCITE);
		System.out.println("Sede "+ sede);
		
		int codigo = cotizacionServices.obtenerCodigoCotizacion();
		
		if (codigo == 0){
			codigo = 1;
			getCotizacionModel().setCodigo(codigo);
			
		} 
		if (codigo > 0){
			codigo = codigo + 1;
			getCotizacionModel().setCodigo(codigo);
			
		} 
		
		CotizacionBO cotizacionBO = new CotizacionBO();
		int codigoCotizacion = 0;
		
		cotizacionBO.setCodigo(codigo);	
		//cotizacionBO.setSecuencial(codigoSecuencial + codigoCite + fecha.obtenerFechaAnio(new Date()) + numero.obtenerNumeroAleatorioEntero());
		cotizacionBO.setServicio(new ServicioBO());
		//este dato se va a sacar de la tabla cotizacion
		cotizacionBO.getServicio().setCodigo("00012016123");
		cotizacionBO.setIdCite(codigoCite*10);
		
		cotizacionBO.setUsuario(new UsuarioBO());
		cotizacionBO.getUsuario().setIdUsuario(idUsuario);
		cotizacionBO.setCostoTotal(getTotalSumaCotizar());	
		cotizacionBO.setFecha(getDate());	
		cotizacionBO.setSecuencial(getServicioModel().getDescripcion());
		//Registrada igual 1 , aprobada igual a 2 y archivada igual a 3
		cotizacionBO.setEstado(1);
		cotizacionBO.setSede(sede);
		
		try {
			codigoCotizacion = cotizacionServices.guardarCotizacion(cotizacionBO);
		}catch(Exception e){
			e.printStackTrace();
			mostrarMensaje(9);				
		}	
		
		//se llena la tabla COTIZACION_DETALLE
		System.out.println("Tamanio de la lista de cotizacion " + getServicioCotizacion().size());
		for (ServicioModel servicio : getServicioCotizacion()) {
				CotizacionDetalleBO cotizacionDetalle = new CotizacionDetalleBO();
				
				
				cotizacionDetalle.setCodigo(codigoCotizacion);	
				cotizacionDetalle.setSecuencial(codigoCotizacion + codigoCite + fecha.obtenerFechaAnio(new Date()) + numero.obtenerNumeroAleatorioEntero());
				cotizacionDetalle.setServicio(new ServicioBO());
				cotizacionDetalle.getServicio().setCodigo(servicio.getCodigo());
				
				
				//Registrada igual 1 , aprobada igual a 2 y archivada igual a 3
				cotizacionDetalle.setSede(sede);
				codigo ++;
				try {
					cotizacionServices.guardarCotizacionDetalle(cotizacionDetalle);
					//mostrarMensaje(8);
				}catch(Exception e){
					e.printStackTrace();
					mostrarMensaje(9);				
				}	
		}

			limpiarObjetos();
			listarCITE();
			
			
			/*pagina = "/produccion-web/paginas/ModuloProduccion/cliente/cotizacion/nuevo/nuevaCotizacion.xhtml";
			String url = (pagina);
			
			
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();
			try {	
				
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "La cotizacion se guardo correctamente"));
				
		       	
				ec.redirect(url);
					
					
			} 
			catch (Exception ex) {
			}*/
			
			//return pagina;
	}
	
	public void enviarCotizacionEmpresa() throws Exception {
		
		String pagina = "";
		
		//se obtiene el usuario de sesion
		FacesContext facesContext = FacesContext.getCurrentInstance();
		LoginModel login = (LoginModel)
		facesContext.getExternalContext().getSessionMap().get("user");
		
		//se crea la clase para obtener el idUsuario
		UsuarioBO usurio = new UsuarioBO();		
		usurio = comunServices.buscarUsuario(login.getUsuario());
		String idUsuario="";
		idUsuario = usurio.getIdUsuario();
		
		//inicializacion de clases core
		ObtenerNumeroAleatorio numero = new ObtenerNumeroAleatorio();
		FormateadorFecha fecha = new FormateadorFecha();
		
		//inicializacion de variables
		int sede =0;
		String ubigeo = "";
		String citeID = "1";
		String descripcionCITE = getServicioModel().getDescripcionCITE();
		
		if(descripcionCITE.equals("Cite Madera") ){
			citeID = "1";
		}
		if(descripcionCITE.equals("Cite Calzado") ){
			citeID = "2";
		}
		if(descripcionCITE.equals("Cite Agroindustrial") ){
			citeID = "3";
		}
		if(descripcionCITE.equals("Cite Pesquero") ){
			citeID = "4";
		}
		
		
		//esto luego en la tabla cite se debe cambiar en vez de 1 es 10 , 2 es 20 , 3 es 30 , 4 es 40
		int codigoCite = Integer.parseInt(citeID);
		codigoCite = codigoCite*10;
		List<ServicioBO> listaServicio = new ArrayList<ServicioBO>();

		List<CotizacionModel> datosServiciosModelGrid = new ArrayList<CotizacionModel>();
		
		if(citeID.equals("1")){
			ubigeo = "140141";
			
		}
		if(citeID.equals("2")){
			ubigeo = "140122";
			
		}
		if(citeID.equals("3")){
			ubigeo = "100106";
			
		}
		if(citeID.equals("4")){
			ubigeo = "240106";
			
		}
		
		sede = cotizacionServices.obtenerCiteSede(ubigeo, descripcionCITE);
		System.out.println("Sede "+ sede);
		
		int codigo = cotizacionServices.obtenerCodigoCotizacion();
		
		if (codigo == 0){
			codigo = 1;
			getCotizacionModel().setCodigo(codigo);
			
		} 
		if (codigo > 0){
			codigo = codigo + 1;
			getCotizacionModel().setCodigo(codigo);
			
		} 
		
		CotizacionBO cotizacionBO = new CotizacionBO();
		int codigoCotizacion = 0;
		
		cotizacionBO.setCodigo(codigo);	
		//cotizacionBO.setSecuencial(codigoSecuencial + codigoCite + fecha.obtenerFechaAnio(new Date()) + numero.obtenerNumeroAleatorioEntero());
		cotizacionBO.setServicio(new ServicioBO());
		//este dato se va a sacar de la tabla cotizacion
		cotizacionBO.getServicio().setCodigo("00012016123");
		cotizacionBO.setIdCite(codigoCite*10);
		
		cotizacionBO.setUsuario(new UsuarioBO());
		cotizacionBO.getUsuario().setIdUsuario(idUsuario);
		cotizacionBO.setCostoTotal(getTotalSumaCotizar());	
		
		//Registrada igual 1 , aprobada igual a 2 y archivada igual a 3
		cotizacionBO.setEstado(1);
		cotizacionBO.setSede(sede);
		cotizacionBO.setFecha(getDate());	
		
		try {
			codigoCotizacion = cotizacionServices.guardarCotizacion(cotizacionBO);
		}catch(Exception e){
			e.printStackTrace();
			mostrarMensaje(9);				
		}	
		
		//se llena la tabla COTIZACION_DETALLE
		System.out.println("Tamanio de la lista de cotizacion " + getServicioCotizacion().size());
		for (ServicioModel servicio : getServicioCotizacion()) {
				CotizacionDetalleBO cotizacionDetalle = new CotizacionDetalleBO();
				
				
				cotizacionDetalle.setCodigo(codigoCotizacion);	
				cotizacionDetalle.setSecuencial(codigoCotizacion + codigoCite + fecha.obtenerFechaAnio(new Date()) + numero.obtenerNumeroAleatorioEntero());
				cotizacionDetalle.setServicio(new ServicioBO());
				cotizacionDetalle.getServicio().setCodigo(servicio.getCodigo());
				
				
				//Registrada igual 1 , aprobada igual a 2 y archivada igual a 3
				cotizacionDetalle.setSede(sede);
				codigo ++;
				try {
					cotizacionServices.guardarCotizacionDetalle(cotizacionDetalle);
					//mostrarMensaje(8);
				}catch(Exception e){
					e.printStackTrace();
					mostrarMensaje(9);				
				}	
		}

			limpiarObjetos();
			listarCITE();
			
			
			/*pagina = "/produccion-web/paginas/ModuloProduccion/empresa/cotizacion/nuevo/nuevaCotizacion.xhtml";
			String url = (pagina);
			
			
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();
			try {	
				
				context.getExternalContext().getFlash().setKeepMessages(true);
				//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito en Guardar la cotizacion", "La cotizacion se guardo correctamente"));
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "La cotizacion se guardo correctamente"));
				
		       	
				ec.redirect(url);
					
					
			} 
			catch (Exception ex) {
			}
			
			//return pagina;
			*/
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

			servicioModel.setDescripcionCITE(servicioBO.getCite().getDescripcion());
			servicioModel.setValorDeVenta(servicioBO.getValorDeVenta());
			servicioModel.setPrecioDeVenta(servicioBO.getPrecioDeVenta());

			datosServiciosModelGrid.add(servicioModel);
		}

		setDatosServiciosModelGrid(datosServiciosModelGrid);
		listarCITE();
	}

	public String cancelar() throws Exception {
		String pagina = "";

		inicializarClases();

		listarCITE();
		pagina = "/paginas/ModuloProduccion/cliente/cotizacion/nuevo/nuevaCotizacion.xhtml";

		return pagina;
	}
	
	public String verCotizacionEmpresa() throws Exception {
		String pagina = "";
		if(servicioCotizacion != null  ) {
			setServicioCotizacion(servicioCotizacion);
		}
		else setServicioCotizacion(selectedServicios);
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		LoginModel login = (LoginModel)
		facesContext.getExternalContext().getSessionMap().get("user");
		
		
		UsuarioBO usurio = new UsuarioBO();
		usurio = comunServices.buscarUsuario(login.getUsuario());
		
		getCotizacionModel().setUsuarioModel(new UsuarioModel());
		getCotizacionModel().getUsuarioModel().setNombres(usurio.getNombres());
		getCotizacionModel().getUsuarioModel().setDni(usurio.getDni());
		getCotizacionModel().getUsuarioModel().setDireccion(usurio.getDireccion());
		getCotizacionModel().getUsuarioModel().setTelefono(usurio.getTelefono());
		getCotizacionModel().getUsuarioModel().setEmail1(usurio.getEmailAdmin());
		getCotizacionModel().getUsuarioModel().setEmail2(usurio.getEmailAdmin());
		
		//datos de empresa
		getCotizacionModel().getUsuarioModel().setEmpresaModel(new EmpresaModel());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setRazonSocial(usurio.getEmpresa().getRazonSocial());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setRuc(usurio.getEmpresa().getRuc());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setRepresentante(usurio.getEmpresa().getRepresentante());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setNombreContacto(usurio.getEmpresa().getNombreContacto());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setNombreCargo(usurio.getEmpresa().getNombreCargo());
	
		
		String citeID = getServicioModel().getCodigoCITE();
		
		//SE ASOCIA A LAS CITES LA SEDE Y SU DESCRIPCION 
		if(citeID.equals("1")) 
		{	
			getServicioModel().setDescripcionCITE("Cite Madera");
			getServicioModel().setSede("LIMA");
		}
		
		if(citeID.equals("2")) 
		{	
			getServicioModel().setDescripcionCITE("Cite Calzado");
			getServicioModel().setSede("LIMA");
		}
		if(citeID.equals("3")) 
		{	
			getServicioModel().setDescripcionCITE("Cite Agroindustrial");
			getServicioModel().setSede("ICA");
		}
		if(citeID.equals("4")) 
		{	
			getServicioModel().setDescripcionCITE("Cite Pesquero");
			getServicioModel().setSede("CALLAO");
		}
		
		
		pagina = "/paginas/ModuloProduccion/empresa/cotizacion/nuevo/verCotizacion.xhtml";

		return pagina;
	}
	public String verCotizacion() throws Exception {
		
		//inicializarClases();
		String pagina = "";
		if(servicioCotizacion != null  ) {
			setServicioCotizacion(servicioCotizacion);
		}
		else setServicioCotizacion(selectedServicios);
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		LoginModel login = (LoginModel)
		facesContext.getExternalContext().getSessionMap().get("user");
		
		
		UsuarioBO usurio = new UsuarioBO();
		usurio = comunServices.buscarUsuario(login.getUsuario());
		
		getCotizacionModel().setUsuarioModel(new UsuarioModel());
		getCotizacionModel().getUsuarioModel().setNombres(usurio.getNombres());
		getCotizacionModel().getUsuarioModel().setDni(usurio.getDni());
		getCotizacionModel().getUsuarioModel().setDireccion(usurio.getDireccion());
		getCotizacionModel().getUsuarioModel().setTelefono(usurio.getTelefono());

		getCotizacionModel().getUsuarioModel().setEmail1(usurio.getEmailAdmin());
		getCotizacionModel().getUsuarioModel().setEmail2(usurio.getEmailAdmin());
		
		//datos de empresa
		getCotizacionModel().getUsuarioModel().setEmpresaModel(new EmpresaModel());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setRazonSocial(usurio.getEmpresa().getRazonSocial());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setRuc(usurio.getEmpresa().getRuc());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setRepresentante(usurio.getEmpresa().getRepresentante());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setNombreContacto(usurio.getEmpresa().getNombreContacto());
		getCotizacionModel().getUsuarioModel().getEmpresaModel().setNombreCargo(usurio.getEmpresa().getNombreCargo());
	
		
		String citeID = getServicioModel().getCodigoCITE();
		
		//SE ASOCIA A LAS CITES LA SEDE Y SU DESCRIPCION 
		if(citeID.equals("1")) 
		{	
			getServicioModel().setDescripcionCITE("Cite Madera");
			getServicioModel().setSede("LIMA");
		}
		
		if(citeID.equals("2")) 
		{	
			getServicioModel().setDescripcionCITE("Cite Calzado");
			getServicioModel().setSede("LIMA");
		}
		if(citeID.equals("3")) 
		{	
			getServicioModel().setDescripcionCITE("Cite Agroindustrial");
			getServicioModel().setSede("ICA");
		}
		if(citeID.equals("4")) 
		{	
			getServicioModel().setDescripcionCITE("Cite Pesquero");
			getServicioModel().setSede("CALLAO");
		}
		
		
		pagina = "/paginas/ModuloProduccion/cliente/cotizacion/nuevo/verCotizacion.xhtml";

		return pagina;
	}
	
	public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se guardo correctamente", "La cotizacion se guardo correctamente");
         
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

	public String selectorBuscarCotizacionServicio(int modo) throws Exception {
		String pagina = "";

		switch (modo) {
		case 1:
			
			inicializarClases();

			listarCITE();
			pagina = "/paginas/ModuloProduccion/cliente/cotizacion/buscar/buscarCotizacion.xhtml";
			break;		
			
		case 2:
			
			inicializarClases();

			listarCITE();
			pagina = "/paginas/ModuloProduccion/cite/cotizacion/buscar/buscarCotizacion.xhtml";
			break;
		}
		return pagina;
	}
	
	public String verCotizacionUsuarioCite() throws Exception {
		String pagina = "";		
			
			inicializarClases();
			listarCITE();
			pagina = "/paginas/ModuloProduccion/cite/cotizacion/buscar/verCotizacion.xhtml";
					
		return pagina;
	}

	private void limpiarObjetos() {
		setServicioModel(null);
		
		setServicioModel(new ServicioModel());
		setCotizacionModel(new CotizacionModel());
		//setServicioCotizacion(new ArrayList<ServicioModel>());
		//setSelectedServicios(new ArrayList<ServicioModel>());	
	}

	private void listarCITE() {
		try {

			getServicioModel().setListarCITE(citeServices.listarCITES());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se guardo correctamente.", "La cotizacion se guardo correctamente.");
	         
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
	       	/*
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"La cotizacion se guardo correctamente");
			FacesContext.getCurrentInstance().addMessage(null, message);
			*/
			break;
		case 9:
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al guardar el usuario");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		}
	}

	public void setServicioModel(ServicioModel servicioModel) {
		this.servicioModel = servicioModel;
	}

	public ServicioModel getServicioModel() {
		return servicioModel;
	}

	public CotizacionModel getCotizacionModel() {
		return cotizacionModel;
	}

	public void setCotizacionModel(CotizacionModel cotizacionModel) {
		this.cotizacionModel = cotizacionModel;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ServicioModel> getSelectedServicios() {
		this.totalSuma = DoubleDosDecimales(this.totalSuma);
		return selectedServicios;
	}

	public Double DoubleDosDecimales(Double total){
		total = (double) Math.round(total * 100);
		total = total / 100;
		return total;
	}
	
	public void retirarServicio(ServicioModel servicioModel){
		selectedServicios.remove(servicioModel);
		
		this.totalSuma = this.totalSuma - DoubleDosDecimales(Double.parseDouble(servicioModel.getPrecioDeVenta()) );
		setServicioCotizacion(selectedServicios);
		
	}
	
	public void setSelectedServicios(List<ServicioModel> selectedServicios) {
		this.totalSuma = 0.0;
		for (int i = 0; i < selectedServicios.size(); i++) {
			this.totalSuma = this.totalSuma + DoubleDosDecimales(Double.parseDouble(selectedServicios.get(i).getPrecioDeVenta()));
		}

		this.totalSuma = DoubleDosDecimales(this.totalSuma);
		this.selectedServicios = selectedServicios;
	}
	
	public String redireccionarBusquedaCotizaciones() {
		String pagina = "";
		//pagina = "/paginas/ModuloProduccion/cliente/cotizacion/nuevo/nuevaCotizacion.xhtml";
		limpiarObjetos();
		
		try {
			pagina = selectorNuevaCotizacion(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagina;
	}
	

	public List<ServicioModel> getServicioCotizacion() {
		return servicioCotizacion;
	}

	public void setServicioCotizacion(List<ServicioModel> servicioCotizacion) {
		this.totalSumaCotizar = 0.0;
		for (int i = 0; i < servicioCotizacion.size(); i++) {
			this.totalSumaCotizar = this.totalSumaCotizar + DoubleDosDecimales(Double.parseDouble(servicioCotizacion.get(i).getPrecioDeVenta()));
		}

		this.totalSumaCotizar = DoubleDosDecimales(this.totalSumaCotizar);
		this.servicioCotizacion = servicioCotizacion;
	}

	public List<ServicioModel> getDatosServiciosModelGrid() {
		return datosServiciosModelGrid;
	}

	public void setDatosServiciosModelGrid(
			List<ServicioModel> datosServiciosModelGrid) {
		this.datosServiciosModelGrid = datosServiciosModelGrid;
	}
	
	public Double getTotalSuma() {
		return totalSuma;
	}

	public void setTotalSuma(Double totalSuma) {
		this.totalSuma = totalSuma;
	}

	public Double getTotalSumaCotizar() {
		return totalSumaCotizar;
	}

	public void setTotalSumaCotizar(Double totalSumaCotizar) {
		this.totalSumaCotizar = totalSumaCotizar;
	}
	
	
}
