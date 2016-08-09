package pe.gob.produce.produccion.services;

import java.util.List;

import pe.gob.produce.cite.bo.UbigeoBO;
import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.produccion.core.util.TipoInformativo;

public interface UsuarioServices {
	public String buscarUsuario(String codUsuario) throws Exception;
	public List<UsuarioBO> buscarUsuarioCite(String codUsuario, String nomUsuario) throws Exception;
	public UsuarioBO obtenerUsuario(String usuario) throws Exception;
	public List<UsuarioBO> obtenerRoles(int procesoTutoria) throws Exception;
	public String buscarUsuarioEquivalencia(String codUsuario) throws Exception;
	public void grabarUsuario(UsuarioBO usuarioNuevo) throws Exception;
	public List<UbigeoBO> listUbigeo() throws Exception;
	public Integer eliminarUsuario(Integer id);
	public String recuperarContrasenia(UsuarioBO usuario) throws Exception;
	public int validarDNIUsuario(String dni) throws Exception;
	public int validarCodigoUsuario(String codUsuario) throws Exception;
}
