package pe.gob.produce.produccion.dao;

import java.util.Date;
import java.util.List;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.cite.bo.TipoDocumentoCiteBO;

public interface CITEIDAO {

	public List<CITEBO>   listarCites()  throws Exception;
	public List<TipoDocumentoCiteBO> listarTipoDocumentoCiteBO() throws Exception;
	public void grabarDocumentosCites(ServicioInformativoBO servicioInformativo) throws Exception;
	
	public List<SedeBO>   listarSedes()  throws Exception;
	public void grabarNuevaSede(SedeBO sede) throws Exception;
	public void grabarNuevaDependencia(DependenciaBO dependencia) throws Exception;
	public void grabarInformativo(ServicioInformativoBO servicioInformativo) throws Exception;
	public void grabarPublicaciones(ServicioInformativoBO servicioInformativo) throws Exception;
	public void grabarNuevaCite(CITEBO cite) throws Exception;
	List<CITEBO> buscarCites(String codigo,String nombre, Date fecha)  throws Exception;
	Integer actualizarCite(CITEBO cite)  throws Exception;
	Integer eliminarCite(Integer id) throws Exception;
	List<SedeBO> buscarSedes(String codigoUbigeo,String nombre, String codigo)  throws Exception;
	Integer actualizarSede(SedeBO sede)  throws Exception;
	Integer eliminarSede(Integer id) throws Exception;
	List<DependenciaBO> buscarDependencias(String codigo,String nombre, String codigoSede)  throws Exception;
	Integer actualizarDependencia(DependenciaBO dependencia)  throws Exception;
	Integer eliminarDependencia(Integer id) throws Exception;

	public int validarDatosCite(String codCite, String codSede, String codDependencia) throws Exception;
}
