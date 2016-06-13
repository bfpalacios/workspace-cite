package pe.gob.produce.produccion.services;

import java.util.List;

import pe.gob.produce.cite.bo.EventoBO;

public interface EventoServices {
	public List<EventoBO> listarEventos() throws Exception;
	public List<EventoBO> listarEventosPorMes(int anio, int mes) throws Exception;
	public void grabarEvento(EventoBO nuevoEvento) throws Exception;
	public void actualizarEvento(EventoBO nuevoEvento) throws Exception;
}
