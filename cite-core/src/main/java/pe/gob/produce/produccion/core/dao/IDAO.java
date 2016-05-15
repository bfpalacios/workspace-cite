package pe.gob.produce.produccion.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IDAO<Entidad, Id> {

	public void insertar(Entidad entidad) throws Exception;
	public void actualizar(Entidad entidad) throws Exception;
	public void eliminar(Serializable id) throws Exception;
	public Entidad obtenerEntidadPorId(Class<Entidad> clase, Serializable id) throws Exception;
	public List<Entidad> obtenerListEntidadPorParametro(Class<Entidad> clase, Map<String,Object> parametro) throws Exception;
	public List<Entidad> listarTodos(Class<Entidad> clase) throws Exception;
	
}
