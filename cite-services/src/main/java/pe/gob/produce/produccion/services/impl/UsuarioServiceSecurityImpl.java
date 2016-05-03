package pe.gob.produce.produccion.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.RolBO;
import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.produccion.services.UsuarioServiceSecurity;
import pe.gob.produce.produccion.services.UsuarioServices;

@SuppressWarnings("deprecation")
@Service("usuarioServiceSecurity")
@Transactional(value="transactionManager")
public class UsuarioServiceSecurityImpl implements UsuarioServiceSecurity {

	@Autowired
	private UsuarioServices usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String usuario)
			throws UsernameNotFoundException {
		UsuarioBO usuarioBO = null;
		try {
			usuarioBO = usuarioService.obtenerUsuario(usuario);
			if(usuarioBO==null){
				throw new UsernameNotFoundException("Usuario no encontrado");
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage()); 
		}
		return  makeUser(usuarioBO);
	}

	private User makeUser(UsuarioBO usuario){
		return new User(usuario.getIdUsuario(), usuario.getContrasenia(),
                true, true, true, true, makeGrantedAuthorities(usuario));
	}

	private Collection<GrantedAuthority> makeGrantedAuthorities(UsuarioBO usuario){
		Collection<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		List<RolBO> listRol = usuario.getListRol();
		for (RolBO rolBO : listRol) {
			result.add(new GrantedAuthorityImpl(rolBO.getNombre()));
		}
        return result;
	}
	
}
