package pe.gob.produce.produccion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.RolBO;
import pe.gob.produce.produccion.dao.jdbc.RolDAO;
import pe.gob.produce.produccion.services.RolServices;

@Component("rolServices")
public class RolServicesImpl implements RolServices {

	@Autowired
	private RolDAO rolDao;

	@Override
	public List<RolBO> listarRoles() {
		return rolDao.listarRoles();
	}

}
