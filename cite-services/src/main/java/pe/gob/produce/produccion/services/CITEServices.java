package pe.gob.produce.produccion.services;

import java.util.Date;
import java.util.List;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
import pe.gob.produce.cite.bo.ServicioInformativoBO;

public interface CITEServices {

	
	public List<CITEBO> listarCITES() throws Exception;
	public List<SedeBO>   listarSedes()  throws Exception;
	public void grabarNuevaCite(CITEBO cite) throws Exception;
	public void grabarNuevaSede(SedeBO sede) throws Exception;
	public void grabarNuevaDependencia(DependenciaBO dependencia) throws Exception;
	public void grabarInformativo(ServicioInformativoBO servicioInformativo) throws Exception;
	public void grabarPublicaciones(ServicioInformativoBO servicioInformativo) throws Exception;
	List<CITEBO> buscarCites(String codigo,String nombre, Date fecha)  throws Exception;
}
