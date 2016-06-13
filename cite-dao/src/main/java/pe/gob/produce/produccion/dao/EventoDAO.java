package pe.gob.produce.produccion.dao;

import java.util.List;

import pe.gob.produce.cite.bo.EventoBO;

public interface EventoDAO {
	public List<EventoBO> listarEventos() throws Exception;
	public List<EventoBO> listarEventosPorMes(int anio, int mes) throws Exception;
	public void grabarEvento(EventoBO nuevoEvento) throws Exception;
	public void actualizarEvento(EventoBO nuevoEvento) throws Exception;
}
