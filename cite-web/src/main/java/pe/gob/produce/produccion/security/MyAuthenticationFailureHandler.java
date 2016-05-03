package pe.gob.produce.produccion.security;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		if(exception instanceof BadCredentialsException){
			 FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_WARN,"Password incorrecto.", "");
		     FacesContext.getCurrentInstance().addMessage("", msj);
		     setDefaultFailureUrl("/login.xhtml");
		     redirectStrategy.sendRedirect(request, response, "/login.xhtml");
		}
	}
}
