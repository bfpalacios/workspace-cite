package pe.gob.produce.produccion.services;

import java.util.List;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;

public interface CITEServices {

	
	public List<CITEBO> listarCITES() throws Exception;
	public List<SedeBO>   listarSedes()  throws Exception;
	public void grabarNuevaSede(SedeBO sede) throws Exception;
	public void grabarNuevaDependencia(DependenciaBO dependencia) throws Exception;
	
}
