package pe.gob.produce.produccion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.ServicioBO;
import pe.gob.produce.produccion.dao.ServicioIDao;
import pe.gob.produce.produccion.services.ServicioServices;

@Component("servicioServices")
public class ServicioServicesImpl implements ServicioServices{
	
	
	@Autowired
	private ServicioIDao servicioDao;

	
	@Override
	public List<ServicioBO> buscarServicio(String codigo, String nombre,
			int idCite) {
		// TODO Auto-generated method stub
		return servicioDao.buscarServicio(codigo, nombre, idCite);
	}

	@Override
	public void nuevoServicio(ServicioBO servicio) {
		servicioDao.nuevoServicio(servicio);
		
	}

	

}
