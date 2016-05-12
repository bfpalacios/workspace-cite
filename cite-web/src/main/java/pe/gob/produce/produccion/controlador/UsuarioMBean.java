package pe.gob.produce.produccion.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.gob.produce.cite.bo.UbigeoBO;
import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.produccion.model.UbigeoModel;
import pe.gob.produce.produccion.model.UsuarioModel;
import pe.gob.produce.produccion.services.ComunServices;
import pe.gob.produce.produccion.services.UsuarioServices;

@Controller("usuarioMBean")
@ViewScoped
public class UsuarioMBean extends GenericoController {

	@Autowired
	private UsuarioModel usuarioModel;

	@Autowired
	private UsuarioServices usuarioServices;

	@Autowired
	private ComunServices comunServices;

	private UIComponent btnGuardar;
	private UsuarioModel usuarioModelSelect;

	private int MODO_USUARIO;
	private static int MODO_ADMIN = 1;
	private static int MODO_OCAA = 2;
	private int PROCESO;
	private static int PROCESO_OBSERVADOS = 1;
	private static int PROCESO_REGULARES = 2;
	private static int ROL_ALUMNO_REGULAR = 11;

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PATTERN_STRING = "([a-z]|[A-Z]|\\s)+";

	private boolean esAlumno = true;

	public UsuarioMBean() {
		this.usuarioModel = new UsuarioModel();

		this.usuarioModelSelect = new UsuarioModel();
		
	}

	private void limpiarCampos() {

		System.out.println("limpiar campos");
		if (getUsuarioModel() != null) {
			setUsuarioModel(null);
			System.out.println("limpiar campos getUsuarioModel");

			setUsuarioModel(new UsuarioModel());
			System.out.println("id " + this.usuarioModel.getCodAlumno());
			System.out.println("id " + this.usuarioModel.getIdUsuario());
			System.out.println("pwd" + this.usuarioModel.getClave());

		}

		reset();
	}

	public void reset() {
		// RequestContext.getCurrentInstance().reset("formLoginPrincipal:panelRegistroUsuario");
		RequestContext.getCurrentInstance().reset(
				"formPrincipal:panelRegistroUsuario");

	}

	public String registrarNuevoUsuario() {

		String pagina = "";
		System.out.println("nuevo usuario");
		pagina = "/admin/nuevo/registrarNuevoUsuario.xhtml";

		return pagina;
	}

	public String registraNuevaEmpresa(int modo) {

		String pagina = "";
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

		switch (modo) {
		/* @@ESTE ES EL CASO PARA PERFIL usuario nuevo */
		case 1:

			pagina = "/admin/nuevo/nuevoUsuarioEmpresa.xhtml";
			break;

		/* @@ESTE ES EL CASO PARA PERFIL CITE */
		case 2:

			pagina = "/paginas/ModuloProduccion/cite/registro/nuevoUsuarioEmpresa.xhtml";
			break;

		}

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

	public String registraNuevoCliente(int modo) {

		String pagina = "";
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

		switch (modo) {

		/* @@ESTE ES EL CASO PARA PERFIL CITE */
		case 1:

			pagina = "/paginas/ModuloAdministrador/admin/nuevoUsuario/nuevoUsuarioCite.xhtml";
			break;

		/* @@ESTE ES EL CASO PARA PERFIL usuario nuevo */
		/*
		 * case 1: pagina ="/admin/nuevo/nuevoUsuarioCliente.xhtml";break;
		 */

		/* @@ESTE ES EL CASO PARA PERFIL ADMIN CITE */
		case 2:

			// pagina =
			// "/paginas/ModuloProduccion/cite/registro/nuevoUsuarioCliente.xhtml";
			// break;
			pagina = "/paginas/ModuloAdministrador/admin/nuevoUsuario/nuevoUsuarioAdmin.xhtml";
			break;

		}

		return pagina;
	}

	public String cancelarRegistrarNuevoUsuario(int modo) throws Exception {
		String pagina = "";

		inicializarClases();
		switch (modo) {
		/* @@ESTE ES EL CASO PARA PERFIL usuario nuevo */
		case 1:
			pagina = "/admin/nuevo/registrarNuevoUsuario.xhtml";
			break;

		/* @@ESTE ES EL CASO PARA PERFIL CITE */
		case 2:
			pagina = "/paginas/ModuloProduccion/principal_cite.xhtml";
			break;

		}

		return pagina;
	}

	public String cancelarRegisttro() throws Exception {
		String pagina = "";

		inicializarClases();

		pagina = "/login.xhtml";

		return pagina;
	}

	private void inicializarClases() {
		this.usuarioModel = new UsuarioModel();

	}

	private void llenarRolesObservados() {
		List<UsuarioBO> usuarioRoles = new ArrayList<UsuarioBO>();
		try {
			usuarioRoles = usuarioServices.obtenerRoles(PROCESO_OBSERVADOS);
			getUsuarioModel().setRolesUsuario(usuarioRoles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void llenarRolesRegulares() {
		List<UsuarioBO> usuarioRoles = new ArrayList<UsuarioBO>();
		try {
			usuarioRoles = usuarioServices.obtenerRoles(PROCESO_REGULARES);
			getUsuarioModel().setRolesUsuario(usuarioRoles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validaNumero(String valor) {
		boolean esNumerico = false;
		try {
			Integer.parseInt(valor);
			esNumerico = true;
		} catch (NumberFormatException nfe) {
			esNumerico = false;
		}
		return esNumerico;
	}

	public boolean validaCadena(String valor) {
		boolean esCadena = false;
		try {
			if (valor.matches(PATTERN_STRING)) {
				esCadena = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return esCadena;
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

	public void activarAlumno(ValueChangeEvent e) throws Exception {
		int rolUsuario = Integer
				.parseInt((String) (e.getNewValue() == null ? "0" : e
						.getNewValue()));
		if (rolUsuario == ROL_ALUMNO_REGULAR) {
			setEsAlumno(false);
		} else {
			setEsAlumno(true);
		}
	}

	private String buscarUsuario(String usuario) {
		String codUsuario = "";
		try {
			codUsuario = usuarioServices.buscarUsuario(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codUsuario;
	}

	public String selectorBuscarClienteEmpresa(int modo) throws Exception {
		String pagina = "";

		switch (modo) {
		case 1:
			MODO_USUARIO = MODO_ADMIN;
			inicializarClases();

			// listarCITE();
			pagina = "";
			break;

		case 2:
			inicializarClases();

			// listarCITE();
			pagina = "/paginas/ModuloProduccion/cite/registro/buscarClienteEmpresa.xhtml";
			break;

		/*
		 * @@ESTE ES EL CASO PARA PERFIL EMPLEADO case 2: MODO_USUARIO =
		 * MODO_OCAA; inicializarClases(); if(getDatosAlumnoExcelModelGrid() !=
		 * null){
		 * getDatosAlumnoExcelModelGrid().removeAll(getDatosAlumnoExcelModelGrid
		 * ()); } pagina =
		 * "/paginas/ModuloObservados/ocaa/cargar/cargarDatosAlumnosObs.xhtml";
		 * break;
		 */
		}
		return pagina;
	}

	public String selectorBuscarClientePersona(int modo) throws Exception {
		String pagina = "";

		switch (modo) {
		case 1:
			MODO_USUARIO = MODO_ADMIN;
			inicializarClases();

			// listarCITE();
			pagina = "";
			break;

		case 2:
			inicializarClases();

			// listarCITE();
			pagina = "/paginas/ModuloProduccion/cite/registro/buscarClientePersona.xhtml";
			break;

		/*
		 * @@ESTE ES EL CASO PARA PERFIL EMPLEADO case 2: MODO_USUARIO =
		 * MODO_OCAA; inicializarClases(); if(getDatosAlumnoExcelModelGrid() !=
		 * null){
		 * getDatosAlumnoExcelModelGrid().removeAll(getDatosAlumnoExcelModelGrid
		 * ()); } pagina =
		 * "/paginas/ModuloObservados/ocaa/cargar/cargarDatosAlumnosObs.xhtml";
		 * break;
		 */
		}
		return pagina;
	}

	public String guardarNuevoUsuarioCliente() {
		String pagina = "";
		try {
			// if
			// (buscarUsuario(getUsuarioModel().getIdUsuario()==null?"0":getUsuarioModel().getIdUsuario()).equals("")){
			// String nuevoUsuario =
			// getUsuarioModel().getIdUsuario()==null?"0":getUsuarioModel().getIdUsuario();
			String contrasenia = getUsuarioModel().getClave() == null ? "0"
					: getUsuarioModel().getClave();
			String confirmaClave = getUsuarioModel().getConfirmarClave() == null ? "0"
					: getUsuarioModel().getConfirmarClave();
			int idRol = Integer
					.parseInt(usuarioModelSelect.getRol() == null ? "0"
							: usuarioModelSelect.getRol());
			String nombres = getUsuarioModel().getNombres() == null ? ""
					: validaCadena(getUsuarioModel().getNombres()) == true ? getUsuarioModel()
							.getNombres() : "invalido";
			String apellidoPaterno = getUsuarioModel().getPaterno() == null ? ""
					: validaCadena(getUsuarioModel().getPaterno()) == true ? getUsuarioModel()
							.getPaterno() : "invalido";
			String apellidoMaterno = getUsuarioModel().getMaterno() == null ? ""
					: validaCadena(getUsuarioModel().getMaterno()) == true ? getUsuarioModel()
							.getMaterno() : "invalido";
			String correo = getUsuarioModel().getCorreo() == null ? ""
					: validaCorreo(getUsuarioModel().getCorreo()) == true ? getUsuarioModel()
							.getCorreo() : "invalido";
			String direccion = getUsuarioModel().getDireccion() == null ? ""
					: getUsuarioModel().getDireccion();
			String telefono = getUsuarioModel().getTelefono();// ==null?"";validaNumero(getUsuarioModel().getTelefono())==true?getUsuarioModel().getTelefono():"invalido";
			String telefonoUsuario = getUsuarioModel().getTelefono2() == null ? ""
					: validaNumero(getUsuarioModel().getTelefono2()) == true ? getUsuarioModel()
							.getTelefono2() : "invalido";

			/*
			 * String razonSocial =
			 * getUsuarioModel().getEmpresaModel().getRazonSocial
			 * ()==null?"":validaCadena
			 * (getUsuarioModel().getEmpresaModel().getRazonSocial
			 * ())==true?getUsuarioModel
			 * ().getEmpresaModel().getRazonSocial():"invalido"; String ruc =
			 * getUsuarioModel
			 * ().getEmpresaModel().getRuc()==null?"":validaNumero
			 * (getUsuarioModel
			 * ().getEmpresaModel().getRuc())==true?getUsuarioModel
			 * ().getEmpresaModel().getRuc():"invalido"; String representante =
			 * getUsuarioModel
			 * ().getEmpresaModel().getRepresentante()==null?"":validaCadena
			 * (getUsuarioModel
			 * ().getEmpresaModel().getRepresentante())==true?getUsuarioModel
			 * ().getEmpresaModel().getRepresentante():"invalido";
			 */
			String ruc = "";

			String rubro = getUsuarioModel().getRubro() == null ? ""
					: validaCadena(getUsuarioModel().getRubro()) == true ? getUsuarioModel()
							.getRubro() : "invalido";

			String dni = getUsuarioModel().getDni() == null ? ""
					: validaNumero(getUsuarioModel().getDni()) == true ? getUsuarioModel()
							.getDni() : "invalido";

			String ubigeo = getUsuarioModelSelect().getCodDistrito();

			String email1 = getUsuarioModel().getEmail1() == null ? ""
					: validaCorreo(getUsuarioModel().getEmail1()) == true ? getUsuarioModel()
							.getEmail1() : "invalido";
			String email2 = getUsuarioModel().getEmail2() == null ? ""
					: validaCorreo(getUsuarioModel().getEmail2()) == true ? getUsuarioModel()
							.getEmail2() : "invalido";
			String emailAdmin = getUsuarioModel().getEmailAdmin() == null ? ""
					: validaCorreo(getUsuarioModel().getEmailAdmin()) == true ? getUsuarioModel()
							.getEmailAdmin() : "invalido";

			String portalWeb = getUsuarioModel().getEmpresaModel()
					.getPortalWeb();// ==null?"":getUsuarioModel().getEmpresaModel().getPortalWeb()?getUsuarioModel().getEmpresaModel().getPortalWeb():"invalido";
			String nombreContacto = getUsuarioModel().getEmpresaModel()
					.getNombreContacto() == null ? ""
					: validaCadena(getUsuarioModel().getEmpresaModel()
							.getNombreContacto()) == true ? getUsuarioModel()
							.getEmpresaModel().getNombreContacto() : "invalido";
			String nombreCargo = getUsuarioModel().getEmpresaModel()
					.getNombreCargo() == null ? ""
					: validaCadena(getUsuarioModel().getEmpresaModel()
							.getNombreCargo()) == true ? getUsuarioModel()
							.getEmpresaModel().getNombreCargo() : "invalido";

			String idUsuario = getUsuarioModel().getIdUsuario() == null ? ""
					: validaCadena(getUsuarioModel().getIdUsuario()) == true ? getUsuarioModel()
							.getIdUsuario() : "invalido";

			if (validarCampos(nombres, apellidoPaterno, apellidoMaterno,
					correo, telefono, "", 0) == true) {
				UsuarioBO usuarioNuevo = new UsuarioBO();
				usuarioNuevo.setIdUsuario(idUsuario);
				usuarioNuevo.setContrasenia(contrasenia);
				usuarioNuevo.setNombres(nombres);
				usuarioNuevo.setApellidoPaterno(apellidoPaterno);
				usuarioNuevo.setApellidoMaterno(apellidoMaterno);
				usuarioNuevo.setCorreo(correo);
				usuarioNuevo.setDireccion(direccion);
				usuarioNuevo.setTelefono(telefono);
				usuarioNuevo.setEmail1(email1);
				usuarioNuevo.setEmail2(email2);
				usuarioNuevo.setEmailAdmin(emailAdmin);
				usuarioNuevo.setRubro(rubro);
				usuarioNuevo.setDni(dni);
				usuarioNuevo.setIdRol(String.valueOf(idRol));

				System.out.println("Ubigeo " + ubigeo);
				usuarioNuevo.setUbigeo(new UbigeoBO());
				usuarioNuevo.getUbigeo().setIdUbigeo(ubigeo);

				usuarioServices.grabarUsuario(usuarioNuevo);
				limpiarCampos();
				mostrarMensaje(8);
			}
			// }
			/*
			 * else{ mostrarMensaje(7); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensaje(9);
		}
		limpiarObjetos();
		// llenarRolesObservados();

		pagina = "/login.xhtml";
		return pagina;
	}

	
	private boolean validarCampos(String nombres, String apellidoPaterno,
			String apellidoMaterno, String correo, String telefono,
			String codAlumno, int idRol) {
		boolean apto = true;

		if (nombres == "invalido") {
			mostrarMensaje(1);
			apto = false;
		}

		if (apellidoPaterno == "invalido") {
			mostrarMensaje(2);
			apto = false;
		}

		if (apellidoMaterno == "invalido") {
			mostrarMensaje(3);
			apto = false;
		}

		if (correo == "invalido") {
			mostrarMensaje(4);
			apto = false;
		}

		if (telefono == "invalido") {
			mostrarMensaje(5);
			apto = false;
		}

		if (idRol == ROL_ALUMNO_REGULAR) {
			if (codAlumno == "invalido") {
				mostrarMensaje(6);
				apto = false;
			}
		}
		setEsAlumno(true);
		return apto;
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
					"El usuario se guardo correctamente");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 9:
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al guardar el usuario");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		}
	}

	private void limpiarObjetos() {
		setUsuarioModel(null);
		setUsuarioModel(new UsuarioModel());
	}

	public String selectorNuevoSede(int modo) throws Exception {
		System.out.println("Entro selectorNuevoSede");
		String pagina = "";

		//inicializarClases();
		List<UbigeoBO> listarUbigeo = new ArrayList<UbigeoBO>();

		List<UbigeoModel> listaUbigeoModel = new ArrayList<UbigeoModel>();

		try {
			// se llama para cargar al combo de departamento
			listarUbigeo = comunServices.listUbigeo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Entro selectorNuevoSede departamento");
		
		for (UbigeoBO ubigeoBO : listarUbigeo) {

			UbigeoModel ubigeo = new UbigeoModel();
			ubigeo.setIdUbigeo(ubigeoBO.getIdUbigeo());
			ubigeo.setDepartamento(ubigeoBO.getDepartamento());
			listaUbigeoModel.add(ubigeo);
		}
		System.out.println("Entro selectorNuevoSede 3");
		
		switch (modo) {

			/* @@ESTE ES EL CASO PARA PERFIL CITE */
			case 1:
	
				pagina = "/paginas/ModuloProduccion/cite/nuevo/nuevaDependencia.xhtml";
				break;
	
			/* @@ESTE ES EL CASO PARA PERFIL ADMIN */
			case 2:
				System.out.println("Entro selectorNuevoSede opcion " +modo);
				//pagina = "/paginas/ModuloAdministrador/admin/nuevoUsuario/nuevaSede.xhtml";
				//break; 
				pagina = "/paginas/ModuloAdministrador/admin/cite/nuevo/nuevaSede.xhtml";
				break;

		}
		return pagina;
	}

	public String selectorRegistroUsuarios(int proceso, int modo)
			throws Exception {
		String pagina = "";
		limpiarCampos();
		setUsuarioModelSelect(new UsuarioModel());
		switch (proceso) {
		case 1:
			switch (modo) {
			case 1:
				PROCESO = PROCESO_OBSERVADOS;
				MODO_USUARIO = MODO_ADMIN;
				llenarRolesObservados();
				pagina = "/paginas/ModuloObservados/admin/mantenimiento/usuario/nuevoUsuarioMO.xhtml";
				break;

			case 2:
				PROCESO = PROCESO_OBSERVADOS;
				MODO_USUARIO = MODO_OCAA;
				llenarRolesObservados();
				pagina = "/paginas/ModuloObservados/ocaa/mantenimiento/usuario/nuevoUsuarioMO.xhtml";
				break;
			}
			break;
		case 2:
			switch (modo) {
			case 1:
				PROCESO = PROCESO_REGULARES;
				MODO_USUARIO = MODO_ADMIN;
				llenarRolesRegulares();
				// listarPlanes();
				setEsAlumno(true);
				pagina = "/paginas/ModuloRegulares/admin/mantenimiento/usuario/nuevoUsuarioMR.xhtml";
				break;

			case 2:
				MODO_USUARIO = MODO_OCAA;
				llenarRolesRegulares();
				// listarPlanes();
				setEsAlumno(true);
				pagina = "/paginas/ModuloRegulares/ocaa/mantenimiento/usuario/nuevoUsuarioMR.xhtml";
				break;
			}
			break;
		}
		return pagina;
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

	public boolean isEsAlumno() {
		return esAlumno;
	}

	public void setEsAlumno(boolean esAlumno) {
		this.esAlumno = esAlumno;
	}

	public UIComponent getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(UIComponent btnGuardar) {
		this.btnGuardar = btnGuardar;
	}
}
