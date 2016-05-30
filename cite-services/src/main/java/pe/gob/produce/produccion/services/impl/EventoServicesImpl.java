package pe.gob.produce.produccion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pe.gob.produce.cite.bo.EventoBO;
import pe.gob.produce.produccion.dao.EventoDAO;
import pe.gob.produce.produccion.dao.ServicioIDao;
import pe.gob.produce.produccion.services.EventoServices;

public class EventoServicesImpl implements EventoServices {
	@Autowired
	private EventoDAO eventoDao;

	@Override
	public List<EventoBO> listarEventos(String anio, String mes)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void grabarEvento(EventoBO nuevoEvento) throws Exception {
		eventoDao.grabarEvento(nuevoEvento);
	}
	
}
