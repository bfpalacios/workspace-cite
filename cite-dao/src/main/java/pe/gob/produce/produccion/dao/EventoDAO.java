package pe.gob.produce.produccion.dao;

import java.util.List;

import pe.gob.produce.cite.bo.EventoBO;

public interface EventoDAO {
	public List<EventoBO> listarEventos(String anio, String mes) throws Exception;
	public void grabarEvento(EventoBO nuevoEvento) throws Exception;

}
