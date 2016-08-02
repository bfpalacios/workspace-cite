package pe.gob.produce.produccion.controlador;

import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.RolBO;
import pe.gob.produce.produccion.model.LoginModel;
import pe.gob.produce.produccion.services.RolServices;
import pe.gob.produce.produccion.services.UsuarioServices;

@Controller("loginMBean")
@ViewScoped
public class LoginMBean extends GenericoController{

	@Autowired
	private LoginModel loginModel;
	
	@Autowired
	private ShaPasswordEncoder shaPasswordEncoder;

	@Autowired
	private UsuarioServices usuarioServices;

	private String rol = "0";
	private String[] roles = null;

	@Autowired
	private RolServices rolServices;

	public LoginMBean() {
		System.out.println(":::::::INICIO :::::::::");
		rol = "0";
	}
	
	@SuppressWarnings("unused")
	private String nuevoUsuario(){
		System.out.println("nuevo usuario");
		return "/paginas/ModuloProduccion/cliente/nuevo/usuario/nuevoUsuario.xhtml";
		
	}

	public String obtieneRol() {
		System.out.println("::::::::::::::: ENTRO ACA1 ::::::::::::::::");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("::::::::::::::: ENTRO ACA ::::::::::::::::");

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = (User) auth.getPrincipal();
			String name = user.getUsername();
			System.out.println("USER INGRESADO: " + name);
			Object[] roles = user.getAuthorities().toArray();

			for (Object rol : roles) {
				System.out.println("rol: " + rol.toString());
			}

			System.out.println("USUARIO " + name + "  CON ROL " + roles[0].toString());
			return roles[0].toString();
		}
		
		
		
		
		return "0";
		
		//return "/paginas/ModuloProduccion/cliente/nuevo/usuario/nuevoUsuario.xhtml";
	}
	
	
	public String[] obtieneRoles() {
		System.out.println("::::::::::::::: ENTRA EN GET ROLES ::::::::::::::::");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("::::::::::::::: SE OBTUVO EL AUTH ::::::::::::::::");

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = (User) auth.getPrincipal();
			String name = user.getUsername();
			
			System.out.println("USER INGRESADO: " + name);
			Object[] roles = user.getAuthorities().toArray();
			
			String[] sroles = new String[roles.length];
			for(int i=0; i<roles.length; i++) {
				sroles[i] = roles[i].toString();
				System.out.println("rol: " + sroles[i]);
			}

		System.out.println("USUARIO " + name + "  con #" + sroles.length + " roles");
			return sroles;
		}

		return null;
	}
	

	public String entrar() {
		try {

			ExternalContext context = getFacesContext().getExternalContext();
			String password = shaPasswordEncoder.encodePassword(loginModel.getClave(),null);
			//String password = loginModel.getClave(),null);
			RequestDispatcher requestDispatcher = ((ServletRequest)context.getRequest()).getRequestDispatcher("/j_spring_security_check?j_username="+
					loginModel.getUsuario()+"&j_password="+password);
			try {
				requestDispatcher.forward((ServletRequest)context.getRequest(),(ServletResponse)context.getResponse());
				getFacesContext().responseComplete();
			}catch (Exception e) {
				mostrarError(e.getMessage());
			}
			
			//SE GUARDA EN SESSION EL USER QUE SERA UTILIZADO PARA LA BUSQUEDA DE COTIZACIONES
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().getSessionMap().put("user", loginModel);
			return "";

		} catch (Exception e) {
			System.out.println("" + e.toString());
		}
		return "/login.xhtml";
	}
	
	

	public List<RolBO> getlistaRoles() {
		return this.rolServices.listarRoles();
	}

	public LoginModel getLoginModel() {
		return loginModel;
	}

	public String getRol() {
		rol = obtieneRol();
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String[] getRoles() {
		this.roles = obtieneRoles();
		return this.roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

}