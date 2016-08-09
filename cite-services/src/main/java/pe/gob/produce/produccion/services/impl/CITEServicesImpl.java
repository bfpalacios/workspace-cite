package pe.gob.produce.produccion.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.cite.bo.TipoDocumentoCiteBO;
import pe.gob.produce.produccion.dao.CITEIDAO;
import pe.gob.produce.produccion.services.CITEServices;

@Service("citeServices")
public class CITEServicesImpl implements CITEServices{
	
	
	@Autowired
	private CITEIDAO citeDAO;
	
	
	@Override
	public List<CITEBO> listarCITES() throws Exception {

		return citeDAO.listarCites();
	}


	@Override
	public void grabarNuevaSede(SedeBO sede) throws Exception {
		citeDAO.grabarNuevaSede(sede);
		
	}


	@Override
	public void grabarNuevaDependencia(DependenciaBO dependencia)
			throws Exception {
		citeDAO.grabarNuevaDependencia(dependencia);
		
	}


	@Override
	public List<SedeBO> listarSedes() throws Exception {
		// TODO Auto-generated method stub
		return citeDAO.listarSedes();
	}


	@Override
	public void grabarNuevaCite(CITEBO cite) throws Exception {
		citeDAO.grabarNuevaCite(cite);
		
	}


	@Override
	public void grabarInformativo(ServicioInformativoBO servicioInformativo)
			throws Exception {
		citeDAO.grabarInformativo(servicioInformativo);
		
	}


	@Override
	public void grabarPublicaciones(ServicioInformativoBO servicioInformativo)
			throws Exception {
		citeDAO.grabarPublicaciones(servicioInformativo);
		
	}


	@Override
	public List<CITEBO> buscarCites(String codigo, String nombre, Date fecha)
			throws Exception {
		return citeDAO.buscarCites(codigo, nombre, fecha);
	}


	@Override
	public Integer actualizarCite(CITEBO cite) throws Exception {
		return citeDAO.actualizarCite(cite);
	}


	@Override
	public Integer eliminarCite(Integer id) throws Exception {
		return citeDAO.eliminarCite(id);
	}


	@Override
	public List<SedeBO> buscarSedes(String codigoUbigeo, String nombre,
			String codigo) throws Exception {
		return citeDAO.buscarSedes(codigoUbigeo, nombre, codigo);
	}


	@Override
	public Integer actualizarSede(SedeBO sede) throws Exception {
		return citeDAO.actualizarSede(sede);
	}


	@Override
	public Integer eliminarSede(Integer id) throws Exception {
		return citeDAO.eliminarSede(id);
	}


	@Override
	public List<DependenciaBO> buscarDependencias(String codigo, String nombre,
			String codigoSede) throws Exception {
		return citeDAO.buscarDependencias(codigo, nombre, codigoSede);
	}


	@Override
	public Integer actualizarDependencia(DependenciaBO dependencia)
			throws Exception {
		return citeDAO.actualizarDependencia(dependencia);
	}


	@Override
	public Integer eliminarDependencia(Integer id) throws Exception {
		return citeDAO.eliminarDependencia(id);
	}


	@Override
	public List<TipoDocumentoCiteBO> listarTipoDocumentoCiteBO()
			throws Exception {
		// TODO Auto-generated method stub
		return citeDAO.listarTipoDocumentoCiteBO();
	}


	@Override
	public void grabarDocumentosCites(ServicioInformativoBO servicioInformativo)
			throws Exception {
		citeDAO.grabarDocumentosCites(servicioInformativo);
		
	}


	@Override
	public int validarDatosCite(String codCite, String codSede,
			String codDepdnencia) throws Exception {
		// TODO Auto-generated method stub
		return citeDAO.validarDatosCite(codCite, codSede, codDepdnencia);
	}
}
