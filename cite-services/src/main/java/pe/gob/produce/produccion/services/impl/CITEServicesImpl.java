package pe.gob.produce.produccion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
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
	
	

}
