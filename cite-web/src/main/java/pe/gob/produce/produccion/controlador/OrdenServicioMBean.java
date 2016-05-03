package pe.gob.produce.produccion.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.produccion.core.util.Convertidor;
import pe.gob.produce.produccion.core.util.FormateadorFecha;
import pe.gob.produce.produccion.model.ServicioModel;




@Controller("ordenServicioMBean")
@ViewScoped
public class OrdenServicioMBean {

	
	@Autowired
	private ServicioModel servicioModel;
	/*@Autowired
	private ServicioServices servicioServices;*/
	
	//datos complementarios de la pantalla
	private Date date;
	
	//constantes
	private int MODO_USUARIO;
	private static int MODO_ADMIN = 1;
	private static int MODO_EMPLEADO = 2;
	
	
	//para la lista de servicios se declara una variable list de tipo ServicioModel
	private List<ServicioModel>  datosServiciosModelGrid;
	private List<ServicioModel>  selectedServicios;
	
	
	
	//constructor
	public OrdenServicioMBean(){
		System.out.println("::::: LOADING OrdenServicioMBean ::::::::");		
		inicializarClases();		
		new Convertidor();
		new FormateadorFecha();
		date=new Date();
		
		
	}
	
	private void inicializarClases(){
		this.servicioModel = new ServicioModel();		
		
		List<ServicioModel> listaServicio = new ArrayList<ServicioModel>();
		
		ServicioModel servicioModel = new ServicioModel();
		servicioModel.setCodigo("0001 - CITE MADERA");
		servicioModel.setNombre("Asistencia Tecnica");
		servicioModel.setUnidad("Hora");
		servicioModel.setRequisito("Constancia de Pago");
		servicioModel.setValorDeVenta("37.47");
		servicioModel.setPrecioDeVenta("44.21");
		listaServicio.add(servicioModel);
		
		ServicioModel servicioModel2 = new ServicioModel();
		servicioModel2.setCodigo("0001 - CITE MADERA");
		servicioModel2.setNombre("Secado de madera en horno experimental");
		servicioModel2.setUnidad("Lote");
		servicioModel2.setRequisito("Constancia de Pago");
		servicioModel2.setValorDeVenta("130.56");
		servicioModel2.setPrecioDeVenta("855.90");
		listaServicio.add(servicioModel2);
		
		ServicioModel servicioModel3 = new ServicioModel();
		servicioModel3.setCodigo("0003 - CITE AGROINDUSTRIAL");
		servicioModel3.setNombre("Asistencia Tecnica");
		servicioModel3.setUnidad("Muestra");
		servicioModel3.setRequisito("Constancia de Pago");
		servicioModel3.setValorDeVenta("37.47");
		servicioModel3.setPrecioDeVenta("44.21");
		listaServicio.add(servicioModel3);
		
		ServicioModel servicioModel4 = new ServicioModel();
		servicioModel4.setCodigo("0004 - CITE PESQUERO");
		servicioModel4.setNombre("Deflexion de las repisas");
		servicioModel4.setUnidad("Muestra");
		servicioModel4.setRequisito("Constancia de Pago");
		servicioModel4.setValorDeVenta("137.47");
		servicioModel4.setPrecioDeVenta("544.21");		
		
		//servicioModel4.setTotalPrecioDeVenta("123");
		
		listaServicio.add(servicioModel4);
		
		
		setDatosServiciosModelGrid(listaServicio);
			
		
	}
	
	
	public String selectorNuevaOrdenPago(int modo) throws Exception{
		 String pagina = "";
		 
		 switch(modo){ 
			case 1: MODO_USUARIO = MODO_ADMIN;									
					inicializarClases();

					//listarCITE();
					pagina = "/paginas/ModuloProduccion/cliente/ordenPago/nuevaOrdenPago.xhtml"; break;
			/*@@ESTE ES EL CASO PARA PERFIL EMPLEADO
			 * case 2: MODO_USUARIO = MODO_OCAA;
					inicializarClases();									
					if(getDatosAlumnoExcelModelGrid() != null){
						getDatosAlumnoExcelModelGrid().removeAll(getDatosAlumnoExcelModelGrid());
					}
					pagina = "/paginas/ModuloObservados/ocaa/cargar/cargarDatosAlumnosObs.xhtml"; break;*/
		}
		return pagina;		
	}
	
	public String cancelar() throws Exception{
		 String pagina = "";
		 
		 	inicializarClases();

		 	//listarCITE();
			pagina = "/paginas/ModuloProduccion/cliente/servicio/nuevo/nuevoServicio.xhtml"; 
			
		return pagina;		
	}
	
	public String selectorBuscarOrdenServicio(int modo) throws Exception{
		 String pagina = "";
		 
		 switch(modo){ 
			case 1: MODO_USUARIO = MODO_ADMIN;									
					inicializarClases();

					//listarCITE();
					pagina = "/paginas/ModuloProduccion/cliente/ordenServicio/buscarOrdenServicio.xhtml"; break;
			/*@@ESTE ES EL CASO PARA PERFIL EMPLEADO */
			 case 2: MODO_USUARIO = MODO_ADMIN;									
					inicializarClases();

					//listarCITE();
					pagina = "/paginas/ModuloProduccion/cite/ordenServicio/buscarOrdenServicio.xhtml"; break;
		}
		return pagina;		
	 }
	
	/* Vista envío de orden de pago */
	
	public String selectorEstadoOrdenServicio(int modo) throws Exception{
		 String pagina = "";
		 
		 switch(modo){ 
			case 1: MODO_USUARIO = MODO_ADMIN;									
					inicializarClases();

					//listarCITE();
					pagina = "/paginas/ModuloProduccion/cliente/ordenServicio/estadoOrdenServicio.xhtml"; break;
			/*@@ESTE ES EL CASO PARA PERFIL EMPLEADO
			 * case 2: MODO_USUARIO = MODO_OCAA;
					inicializarClases();									
					if(getDatosAlumnoExcelModelGrid() != null){
						getDatosAlumnoExcelModelGrid().removeAll(getDatosAlumnoExcelModelGrid());
					}
					pagina = "/paginas/ModuloObservados/ocaa/cargar/cargarDatosAlumnosObs.xhtml"; break;*/
		}
		return pagina;		
	 }
	
	
	


	public String guardarNuevoServicio() {
		String pagina = "";
		try{
			//if (buscarUsuario(getUsuarioModel().getIdUsuario()==null?"0":getUsuarioModel().getIdUsuario()).equals("")){
			if (true){
				String nuevoServicio = getServicioModel().getNombre()==null?"blanco":getServicioModel().getNombre();
				
				System.out.println("Nombre de servicio " + nuevoServicio);
				/*String contrasenia = getUsuarioModel().getClave()==null?"0":getUsuarioModel().getClave();
				int idRol = Integer.parseInt(usuarioModelSelect.getRol()==null?"0":usuarioModelSelect.getRol());
				String nombres = getUsuarioModel().getNombres()==null?"":validaCadena(getUsuarioModel().getNombres())==true?getUsuarioModel().getNombres():"invalido";
				String apellidoPaterno = getUsuarioModel().getPaterno()==null?"":validaCadena(getUsuarioModel().getPaterno())==true?getUsuarioModel().getPaterno():"invalido";
				String apellidoMaterno = getUsuarioModel().getMaterno()==null?"":validaCadena(getUsuarioModel().getMaterno())==true?getUsuarioModel().getMaterno():"invalido";
				String correo = getUsuarioModel().getCorreo()==null?"":validaCorreo(getUsuarioModel().getCorreo())==true?getUsuarioModel().getCorreo():"invalido";
				String direccion = getUsuarioModel().getDireccion()==null?"":getUsuarioModel().getDireccion();
				String telefono = getUsuarioModel().getTelefono()==null?"":validaNumero(getUsuarioModel().getTelefono())==true?getUsuarioModel().getTelefono():"invalido";*/
				
				/*if(validarCampos(nombres,apellidoPaterno,apellidoMaterno,correo,telefono, "", 0)==true){
					ServicioBO usuarioNuevo = new UsuarioBO();
					usuarioNuevo.setIdUsuario(nuevoUsuario);
					usuarioNuevo.setContrasenia(contrasenia);
					usuarioNuevo.setNombres(nombres);
					usuarioNuevo.setApellidoPaterno(apellidoPaterno);
					usuarioNuevo.setApellidoMaterno(apellidoMaterno);
					usuarioNuevo.setCorreo(correo);
					usuarioNuevo.setDireccion(direccion);
					usuarioNuevo.setTelefono(telefono);
					usuarioNuevo.setIdRol(String.valueOf(idRol));
					
					servicioServices.grabarNuevoServicio(usuarioNuevo);*/
					//limpiarCampos();
					//mostrarMensaje(8);	
				//}
			}
			else{
				//mostrarMensaje(7);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			//mostrarMensaje(9);				
		}		
		limpiarObjetos();
		//llenarRolesObservados();
		
		/*switch(PROCESO){
			case 1: switch(MODO_USUARIO){
						case 1: pagina = "/paginas/ModuloObservados/admin/mantenimiento/usuario/nuevoUsuarioMO.xhtml"; break;
						case 2: pagina = "/paginas/ModuloObservados/ocaa/mantenimiento/usuario/nuevoUsuarioMO.xhtml"; break;
					}
			
			case 2: switch(MODO_USUARIO){
						case 1: pagina = "/paginas/ModuloRegulares/admin/mantenimiento/usuario/nuevoUsuarioMR.xhtml"; break;
						case 2: pagina = "/paginas/ModuloRegulares/ocaa/mantenimiento/usuario/nuevoUsuarioMR.xhtml"; break;
					}				
		}*/	
		
		pagina = "/paginas/ModuloProduccion/admin/nuevo/nuevoServicio.xhtml";
		return pagina;
	}
	
	
	private void limpiarObjetos(){
		setServicioModel(null);
		setServicioModel(new ServicioModel());
	}

	/*private void listarCITE(){
		List<CITEIDAO> listCITE = new ArrayList<CITEBO>();
		try{
			CITEBO aCITEBO = new CITEBO();
			aCITEBO.setCodigo("0001");
			aCITEBO.setDescripcion("CITE MADERA");
			listCITE.add(aCITEBO);
			
			CITEBO bCITEBO = new CITEBO();
			bCITEBO.setCodigo("0002");
			bCITEBO.setDescripcion("CITE CALZADO");
			listCITE.add(bCITEBO);
			
			CITEBO cCITEBO = new CITEBO();
			cCITEBO.setCodigo("0003");
			cCITEBO.setDescripcion("CITE AGROINDUSTRIAL");
			listCITE.add(cCITEBO);
			
			CITEBO dCITEBO = new CITEBO();
			dCITEBO.setCodigo("0004");
			dCITEBO.setDescripcion("CITE PESQUERO");
			listCITE.add(dCITEBO);
			
			getServicioModel().setListarCITE(listCITE);
			/*usuarioRoles = usuarioServices.obtenerRoles(PROC
		}
		catch(Exception e){
			e.printStackTrace();
		}
	} 
*/

	public void setServicioModel(ServicioModel servicioModel) {
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
}
