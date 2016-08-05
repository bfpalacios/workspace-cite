package pe.gob.produce.produccion.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
import pe.gob.produce.cite.bo.UbigeoBO;
import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.produccion.core.util.FormateadorFecha;
import pe.gob.produce.produccion.model.CITESModel;
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
	
	//declaracion de variables
	private String codigoUsuario;
	private String nombreUsuario;
	private List<UsuarioModel> listaUsuarios;
	private List<UsuarioModel> datosUsuarioModelGrid;
	private List<UsuarioBO> listaUsuariosDB;
	
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PATTERN_STRING = "([a-z]|[A-Z]|\\s)+";

	private boolean esAlumno = true;

	//propiedades de enviar mail
	private Properties properties = null;
	
	//private String password;

	private Session session;
	

	//private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	//private static final String SMTP_HOST_NAME = "outlook.office365.com"; 
	private static final String SMTP_HOST_NAME = "smtp.live.com"; 
	//private static final String SMTP_PORT = "465"; 
	private static final String SMTP_PORT = "587"; 
	private static final String emailMsgTxt = "Test Message Contents"; 
	private static final String emailSubjectTxt = "A test from gmail"; 
	//private static final String emailFromAddress = "ITP.SOPORTEAPP@gmail.com"; 
	private static final String emailFromAddress = "ITP.SOPORTE@outlook.es"; 
	
	//private static final String password = "ITP.SOPORTEAPP@@"; //gmail
	private static final String password = "itpSOPORTE@@2016"; //hotmail
	private static final String SSL_FACTORY = 
	"javax.net.ssl.SSLSocketFactory"; 
	private static final String[] sendTo = {"bfpalacios@gmail.com"}; 
	private void init() {
		properties = new Properties();
		properties.put("mail.smtp.auth", "true"); 
		properties.put("mail.smtp.starttls.enable", "false");
		//properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", SMTP_HOST_NAME);
		properties.put("mail.smtp.port",SMTP_PORT);
		properties.put("mail.smtp.debug", "true");
		
		properties.put("mail.smtp.socketFactory.port", SMTP_PORT); 
		properties.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		properties.put("mail.smtp.socketFactory.fallback", "false"); 
		/*properties.put("mail.transport.protocol","smtp");
		
		properties.put("mail.smtp.socketFactory.port", SMTP_PORT); 
		properties.put("mail.smtp.socketFactory.class", SSL_FACTORY); 
		properties.put("mail.smtp.socketFactory.fallback", "false"); 
	*/
		
		session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailFromAddress, password);
                    }
                });
		
		session.setDebug(true);

		//session = Session.getDefaultInstance(properties);
	}

	public void enviarContraseniaEmail() throws Exception{
		System.out.println("inicio:enviarContraseniaEmail");
		
		init();
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailFromAddress));
			message.setRecipients(Message.RecipientType.TO,
		                InternetAddress.parse("bfpalacios@gmail.com"));
		    //message.addRecipient(Message.RecipientType.TO, new InternetAddress("bfpalacios@gmail.com"));
			message.setSubject("Envio de contrasenia Red de Cites");
			
			/*Transport t = session.getTransport("smtps");
			t.connect((String)properties.get("mail.smtp.host"),587,(String)properties.get("mail.smtp.user"), "ITP.SOPORTEAPP@@");
			t.sendMessage(message, message.getAllRecipients());
			t.close();
			*/
			
			/* MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		        CommandMap.setDefaultCommandMap(mc);
		      */  
			message.setText("Se le envia su contrase馻 : ");
				//message.setText(htmlBody);
            message.setContent("Se le envia su contrase馻 : ", "text/html");
			Transport.send(message);
			System.out.println("Done");
			olvidoContrasenia();
			
		}catch (MessagingException me){
                        //Aqui se deberia o mostrar un mensaje de error o en lugar
                        //de no hacer nada con la excepcion, lanzarla para que el modulo
                        //superior la capture y avise al usuario con un popup, por ejemplo.
			throw new RuntimeException(me);
		}
		
		System.out.println("fin:enviarContraseniaEmail");
		
		
	}
	
	
	public String enviarContrasenia() {
		System.out.println("inicio:enviarContrasenia");
		
		String pagina = "";
		UsuarioBO usuario = new UsuarioBO();

		usuario.setCodUsuario(getUsuarioModel().getCodUsuario()); 
		usuario.setDni(getUsuarioModel().getDni()); 
		usuario.setCodigoUbigeo(getUsuarioModelSelect().getCodDistrito()); 
		usuario.setFechaNac(getUsuarioModel().getFechaNac()); 
		
		String contrasenia;
		try {
			contrasenia = usuarioServices.recuperarContrasenia(usuario);
			
			if(contrasenia != null){
			System.out.println("contrasenia " + contrasenia);
				getUsuarioModel().setConfirmarClave(contrasenia);
				
				pagina ="/admin/nuevo/mostrarContrasenia.xhtml";
				
			
			} else mostrarMensaje(12);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mostrarMensaje(11);
		}
		
		
		
		
		
		
		System.out.println("fin:enviarContrasenia");
		
		return pagina;
	}

	
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
				"formPrincipal13:panelRegistroUsuario");

	}
	
	
	public String buscarUsuarioCite() throws Exception {
		System.out.println("buscarUsuarioCite:INICIO");
		inicializarClases();
		String pagina = "/paginas/ModuloAdministrador/admin/cite/buscar/buscarUsuarioCite.xhtml";
		System.out.println("uscarUsuarioCite:FIN");
		return pagina;
	}
	
	
	public String olvidoContrasenia() throws Exception {
		System.out.println("olvidoContrasenia:INICIO");
		inicializarClases();
		cargarUbigeo();
		cargarCITES();
		String pagina ="/admin/nuevo/olvidoContrasenia.xhtml";
		System.out.println("buscarUsuarioCite:FIN");
		return pagina;
	}
	
	
	
	 
	 
	
	public void actualizarlistSedes(ValueChangeEvent e) throws Exception {
		String codCite = (String) (e.getNewValue() == null ? "" : e
				.getNewValue());
 
		System.out.println("codigo de cite " + codCite);
		List<SedeBO> listarSede = new ArrayList<SedeBO>();
		//List<CITESModel> listaCITESModel = new ArrayList<CITESModel>();

		listarSede = comunServices.listarSedes(codCite); 

		getUsuarioModel().setListSedes(listarSede);
	}
	
	
	public void actualizarlistDependencias(ValueChangeEvent e) throws Exception {
		String codSede = (String) (e.getNewValue() == null ? "" : e
				.getNewValue());
 
		System.out.println("codigo de sede " + codSede);
		List<DependenciaBO> listarDepedencias = new ArrayList<DependenciaBO>();
		
		listarDepedencias = comunServices.listarDependencias(codSede);
 

		getUsuarioModel().setListDependencia(listarDepedencias);
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
			ubigeo.setProvincia(ubigeoBO.getProvincia()); 
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

	public String registraNuevoUsuarioCite() {

		String pagina = "";
		inicializarClases();
		cargarUbigeo();
		cargarCITES();
		pagina = "/paginas/ModuloAdministrador/admin/nuevoUsuario/nuevoUsuarioCite.xhtml";
			
		return pagina;
	}
	
	public void buscarUsuarioCites(){
		
		FormateadorFecha fechaFormateada = new FormateadorFecha();
		String nombre = null;
		String codigo = null;
	 
		codigo = getCodigoUsuario();
		nombre = getNombreUsuario();
		
		// this should be gone in a logger
		System.out.println("DATOS BUSQUEDA DE USUARIO " + codigo + ": " + nombre);
		
		listaUsuarios= new ArrayList<>();
		try {
			listaUsuariosDB = usuarioServices.buscarUsuarioCite(codigo, nombre);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		for (UsuarioBO usuario : listaUsuariosDB) {
			UsuarioModel user = new UsuarioModel();
			user.setIdUsuario(usuario.getIdUsuario());
			user.setCodUsuario(usuario.getCodUsuario());
			user.setNombres(usuario.getNombres()); 
			user.setCargo(usuario.getCargo()); 
			user.setNombreCITE(usuario.getNombreCITE()); 
			user.setNombreDependencia(usuario.getNombreDependencia()); 
			user.setTelefono(usuario.getTelefono()); 
			user.setTelefono2(usuario.getTelefono2()); 
			user.setTelefonoJefe(usuario.getTelefonoJefeInmediato()); 
			 
			listaUsuarios.add(user);
		}
		setDatosUsuarioModelGrid(listaUsuarios);
	}
	
	public void eliminarUsuario(UsuarioModel usuarioModel)
			throws Exception {
		if (usuarioModel != null) {
			int flatDelete = usuarioServices.eliminarUsuario(Integer.parseInt(usuarioModel.getIdUsuario()));
			//actulizarGridDespuesDeEliminar(flatDelete);
		}
	}
	
	public void actualizarUsuario(UsuarioModel usuarioModel) {
		
		
		/*try {
			 
			servMBean.validarCampos(inforModel.getTitulo(),
					inforModel.getDescripcionCorta(),
					inforModel.getDescripcion());
			
			/*if (inforModel != null) {
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
					"Hubo un error al actualizar " + usuarioModel.getCodUsuario());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
*/
	}
	public void actulizarGridDespuesDeEliminar(int flatDelete) {
		if (flatDelete > 0) {
			for (Iterator<UsuarioModel> iter = listaUsuarios
					.listIterator(); iter.hasNext();) {
				UsuarioModel infor = iter.next();
				if (infor.getIdUsuario() == usuarioModel.getIdUsuario()) {
					iter.remove();
				}
			}
			setDatosUsuarioModelGrid(listaUsuarios);
		}else{
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al eliminar");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}


	public String cancelarRegistrarNuevoUsuario() throws Exception {
		String pagina = "";
		inicializarClases();
		cargarUbigeo();
		cargarCITES();
		return pagina = "/paginas/ModuloAdministrador/admin/nuevoUsuario/nuevoUsuarioCite.xhtml";
	}

	public String cancelarRegisttro() throws Exception {
		String pagina = "";

		inicializarClases();

		pagina = "/login.xhtml";

		return pagina;
	}

	private void inicializarClases() {
		this.usuarioModel = new UsuarioModel();
		this.usuarioModelSelect  = new UsuarioModel();

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
			 
			inicializarClases(); 
			pagina = "";
			break;

		case 2:
			inicializarClases(); 
			pagina = "/paginas/ModuloProduccion/cite/registro/buscarClienteEmpresa.xhtml";
			break; 
		}
		return pagina;
	}

	public String selectorBuscarClientePersona(int modo) throws Exception {
		String pagina = "";

		switch (modo) {
		case 1: 
			inicializarClases(); 
			pagina = "";
			break;

		case 2:
			inicializarClases(); 
			pagina = "/paginas/ModuloProduccion/cite/registro/buscarClientePersona.xhtml";
			break; 
		}
		return pagina;
	}

	public String guardarNuevoUsuarioCliente() {
		String pagina = "";
		try {
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
		pagina = "/login.xhtml";
		return pagina;
	}
	
		
	public void guardarNuevoUsuario() {
		
		try {
			String contrasenia = getUsuarioModel().getClave() == null ? ""
					: getUsuarioModel().getClave();
			String confirmaClave = getUsuarioModel().getConfirmarClave() == null ? ""
					: getUsuarioModel().getConfirmarClave();
			String idRol = usuarioModelSelect.getRol() == null ? ""
							: usuarioModelSelect.getRol();
			String codCite = usuarioModelSelect.getCodCite() == null ? ""
					: usuarioModelSelect.getCodCite();
			String codSede = usuarioModelSelect.getCodSede() == null ? ""
					: usuarioModelSelect.getCodSede();
			String codDependencia = usuarioModelSelect.getCodDependencia() == null ? ""
					: usuarioModelSelect.getCodDependencia();
	 
			String nombres = getUsuarioModel().getNombres() == null ? ""
					: validaCadena(getUsuarioModel().getNombres()) == true ? getUsuarioModel()
							.getNombres() : "invalido"; 
											
			String cargo = getUsuarioModel().getCargo() == null ? ""
									: validaCadena(getUsuarioModel().getCargo()) == true ? getUsuarioModel()
											.getCargo() : "invalido"; 
															
			String telefonoJefe = getUsuarioModel().getTelefonoJefe(); 
			String jefe = getUsuarioModel().getJefe(); 
															
			String codigoDpto = getUsuarioModelSelect().getCodDepartamento() == null ? "invalido"
					: getUsuarioModelSelect().getCodDepartamento();
			String codigoProvincia = getUsuarioModelSelect().getCodProvincia() == null ? "invalido"
					: getUsuarioModelSelect().getCodProvincia();
			String codigoDistrito = getUsuarioModelSelect().getCodDistrito() == null ? "invalido"
					: getUsuarioModelSelect().getCodDistrito();
			String codigoUbigeo = codigoDistrito; //codigoDpto + codigoProvincia + codigoDistrito;

			codigoUbigeo = codigoUbigeo.equals("") ? "0" : codigoUbigeo;
										
											
			String emailItp = getUsuarioModel().getEmailAdmin() == null ? ""
					: validaCorreo(getUsuarioModel().getEmailAdmin()) == true ? getUsuarioModel()
							.getEmailAdmin() : "invalido"; 
			
			String emailpersonal = getUsuarioModel().getEmail1() == null ? ""
									: validaCorreo(getUsuarioModel().getEmail1()) == true ? getUsuarioModel()
								.getEmail1() : "invalido"; 
							
			String telefonoITP = getUsuarioModel().getTelefono();
			String telefonoPersonal = getUsuarioModel().getTelefono2();

			String dni = getUsuarioModel().getDni() == null ? ""
					: validaNumero(getUsuarioModel().getDni()) == true ? getUsuarioModel()
							.getDni() : "invalido"; 
 

			
			String idUsuario = getUsuarioModel().getIdUsuario() == null ? ""
					: validaCadena(getUsuarioModel().getIdUsuario()) == true ? getUsuarioModel()
							.getIdUsuario() : "invalido";
			
			Date fechaNacimiento= getUsuarioModel().getFechaNac();
			
					System.out.println("Nombres " + nombres);
					System.out.println("dpto " + codigoDpto);
					System.out.println("provincia " + codigoProvincia);
					System.out.println("distrito " + codigoDistrito);
					System.out.println("ubigeo " + codigoUbigeo);
													
			if (validarCamposUsuarioCite(nombres, contrasenia, confirmaClave, idRol, codCite, codDependencia, dni, idUsuario, emailItp, codSede) == true) {
				UsuarioBO usuarioNuevo = new UsuarioBO();
				usuarioNuevo.setIdUsuario(idUsuario);
				usuarioNuevo.setContrasenia(contrasenia);
				usuarioNuevo.setNombres(nombres);  
				usuarioNuevo.setTelefono(telefonoITP);
				usuarioNuevo.setTelefono2(telefonoPersonal); 
				usuarioNuevo.setCodCITE(codCite);
				usuarioNuevo.setCodSede(codSede); 
				usuarioNuevo.setTelefonoJefeInmediato(telefonoJefe); 
				usuarioNuevo.setJefeInmediato(jefe); 
				usuarioNuevo.setCodDependencia(codDependencia); 
				//falta telefono personal
				usuarioNuevo.setEmail1(emailpersonal); 
				usuarioNuevo.setCodigoUbigeo(codigoUbigeo);
				usuarioNuevo.setEmailAdmin(emailItp);
				usuarioNuevo.setDni(dni);
				usuarioNuevo.setCargo(cargo);
				usuarioNuevo.setIdRol(idRol); 
				usuarioNuevo.setFechaNac(fechaNacimiento);

				usuarioServices.grabarUsuario(usuarioNuevo);
				limpiarCampos();
				inicializarClases();
				cargarUbigeo();
				cargarCITES();
				mostrarMensajeNuevoUsuario(13);
						
				
				RequestContext rc = RequestContext.getCurrentInstance();
				rc.execute("dialogNuevoUsuarioCite.show()");
			}
			// }
			/*
			 * else{ mostrarMensaje(7); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensajeNuevoUsuario(14);
		}
		
		
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

		 
		setEsAlumno(true);
		return apto;
	}
	
	private boolean validarCamposUsuarioCite(String nombres, String contrasenia, String confirmaClave, String idRol, String codCite, 
			String codDependencia, String dni, String idUsuario, String emailItp, String codSede) {
		boolean apto = true;

		if (nombres.equals("") || nombres.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(1);
			apto = false;
		}
		
		if (contrasenia.equals("") || contrasenia.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(2);
			apto = false;
		}
		
		if (confirmaClave.equals("") || confirmaClave.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(3);
			apto = false;
		}
		
		if (idRol.equals("") || idRol.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(4);
			apto = false;
		}
		
		if (codCite.equals("") || codCite.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(5);
			apto = false;
		}
		if (codDependencia.equals("") || codDependencia.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(6);
			apto = false;
		}
		if (dni.equals("") || dni.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(7);
			apto = false;
		}
		
		if (idUsuario.equals("") || idUsuario.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(8);
			apto = false;
		} 
		
		if (emailItp.equals("") || emailItp.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(9);
			apto = false;
		}
		
		if (codSede.equals("") || codSede.equals("invalido") ) {
			mostrarMensajeNuevoUsuario(11);
			apto = false;
		}
		
		if (!confirmaClave.equals(contrasenia) ) {
			mostrarMensajeNuevoUsuario(12);
			apto = false;
		}
		
		return apto;
	}


	private void mostrarMensaje(int opcionMensaje) {
		FacesMessage message = null;

		switch (opcionMensaje) {
		case 1:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar s贸lo caracteres en el campo - " + "Nombres");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 2:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar s贸lo caracteres en el campo - "
							+ "Apellido Paterno");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 3:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar s贸lo caracteres en el campo - "
							+ "Apellido Materno");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 4:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar un correo v谩lido en el campo - " + "Correo");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 5:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar s贸lo n煤meros en el campo - " + "Tel茅fono");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 6:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar s贸lo n煤meros en el campo - "
							+ "C贸digo del alumno");
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
		
		case 11:
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error de conexion de base de datos ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
			
		case 12:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Los datos que ingreso no son correctos ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		}
	}
	
	
	private void mostrarMensajeNuevoUsuario(int opcionMensaje) {
		FacesMessage message = null;

		switch (opcionMensaje) {
		case 1:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar los caracteres en el campo - " + "Nombres");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 2:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar los caracteres en el campo - "
							+ "contrasenia");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 3:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar los caracteres en el campo - "
							+ "confirmaClave");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 4:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar un correo valido en el campo - " + "Rol");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 5:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar el combo de la CITE");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 6:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar el combo dependencia");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 7:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar el DNI");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 8:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Debe ingresar el ID USUARIO");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		case 9:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar un correo valido en el campo - " + "Email ITP");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		
		case 10:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"El usuario ingresado ya ha sido registrado");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		
		case 11:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Debe ingresar en el combo  - " + "Sede");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		
		case 12:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Los campos Password y Confirmar Password no coinciden");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
			
		case 13:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"El usuario se guardo correctamente");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		
		case 14:
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Hubo un error al guardar el usuario");
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		}
	}
	
	private void limpiarObjetos() {
		setUsuarioModel(null);
		setUsuarioModelSelect(null);
		setUsuarioModel(new UsuarioModel());
		setUsuarioModelSelect(new UsuarioModel());
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
	
	public void cargarCITES(){
		
		
		List<CITEBO> listarCITE = new ArrayList<CITEBO>();

		List<CITESModel> listaCiteModel = new ArrayList<CITESModel>();

		try {
			// se llama para cargar al combo de cites
			listarCITE = comunServices.listCITE();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (CITEBO citeBO : listarCITE) {

			CITESModel cites = new CITESModel();
			cites.setCodigoCite(citeBO.getCodigo()); 
			cites.setDescripcion(citeBO.getDescripcion()); 
			listaCiteModel.add(cites);
		}

		getUsuarioModel().setListCite(listaCiteModel);
	}
	
	
	public void cargarUbigeo(){
		
		
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

	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public List<UsuarioModel> getDatosUsuarioModelGrid() {
		return datosUsuarioModelGrid;
	}

	public void setDatosUsuarioModelGrid(List<UsuarioModel> datosUsuarioModelGrid) {
		this.datosUsuarioModelGrid = datosUsuarioModelGrid;
	}
	
	
}
