package pe.gob.produce.produccion.services;

import java.util.List;

import pe.gob.produce.cite.bo.UbigeoBO;
import pe.gob.produce.cite.bo.UsuarioBO;

public interface UsuarioServices {
	public String buscarUsuario(String codUsuario) throws Exception;
	public UsuarioBO obtenerUsuario(String usuario) throws Exception;
	public List<UsuarioBO> obtenerRoles(int procesoTutoria) throws Exception;
	public String buscarUsuarioEquivalencia(String codUsuario) throws Exception;
	public void grabarUsuarioRegulares(UsuarioBO usuarioNuevo) throws Exception;
	public void grabarUsuarioObservados(UsuarioBO usuarioNuevo) throws Exception;
	public void grabarUsuario(UsuarioBO usuarioNuevo) throws Exception;
	public List<UbigeoBO> listUbigeo() throws Exception;
	
}
