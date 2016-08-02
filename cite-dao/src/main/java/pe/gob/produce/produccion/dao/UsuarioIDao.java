package pe.gob.produce.produccion.dao;


import java.util.List;

import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.produccion.dao.dominio.Usuario;



public interface UsuarioIDao {
	public Usuario obtenerUsuario(String usuario) throws Exception;
	public String buscarUsuario(String codUsuario) throws Exception;
	public String buscarUsuarioEquivalencia(String codUsuario) throws Exception;
	public void grabarUsuario(UsuarioBO usuarioNuevo) throws Exception;
	public List<UsuarioBO> buscarUsuarioCite(String codUsuario, String nomUsuario) throws Exception;
	public Integer eliminarUsuario(Integer id);
	public String recuperarContrasenia(UsuarioBO usuario) throws Exception;
	
}
