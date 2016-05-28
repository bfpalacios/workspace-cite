package pe.gob.produce.produccion.dao;

import java.util.List;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
import pe.gob.produce.cite.bo.UbigeoBO;
import pe.gob.produce.cite.bo.UsuarioBO;

public interface ComunIDAO {
	public List<UsuarioBO> obtenerRoles(int proceso) throws Exception;
	public List<CITEBO> listCITE() throws Exception;
	public List<UbigeoBO> listUbigeo() throws Exception;
	public List<SedeBO> listarSedes(String codCite) throws Exception;
	public List<UbigeoBO> listarProvincia(String codDepartamento) throws Exception;
	public List<UbigeoBO> listarDistrito(String codDepartamento, String codProvincia) throws Exception;
	public UsuarioBO buscarUsuario(String codUsuario)  throws Exception;
	public List<DependenciaBO> listarDependencias(String codigoSede) throws Exception;


}
