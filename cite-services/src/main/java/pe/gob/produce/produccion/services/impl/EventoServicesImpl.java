package pe.gob.produce.produccion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.EventoBO;
import pe.gob.produce.produccion.dao.EventoDAO;
import pe.gob.produce.produccion.services.EventoServices;

@Component("eventoServices")
public class EventoServicesImpl implements EventoServices {
	@Autowired
	private EventoDAO eventoDao;	
	
	
	public EventoServicesImpl() {
	}

	@Override
	public List<EventoBO> listarEventosPorMes(int anio, int mes)
			throws Exception {
		return eventoDao.listarEventosPorMes(anio, mes);
	}

	@Override
	public void grabarEvento(EventoBO nuevoEvento) throws Exception {
		eventoDao.grabarEvento(nuevoEvento);
	}

	@Override
	public List<EventoBO> listarEventos() throws Exception {
		return eventoDao.listarEventos();
	}
	
}
