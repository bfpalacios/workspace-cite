package pe.gob.produce.produccion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.dao.InformativoDAO;
import pe.gob.produce.produccion.services.InformativoServices;

@Component("informativoServices")
public class InformativoServicesImpl implements InformativoServices {

	@Autowired
	private InformativoDAO informativoDao;
	
	@Override
	public List<ServicioInformativoBO> listarNoticias() {
		return informativoDao.listarNoticias();
	}

}
