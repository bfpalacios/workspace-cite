package pe.gob.produce.produccion.services;

import java.util.List;

import pe.gob.produce.cite.bo.ServicioInformativoBO;

public interface InformativoServices {
	public List<ServicioInformativoBO> listarNoticias(int numNoticias);
	public List<ServicioInformativoBO> listarNoticiasPorMes(int anio, int mes);
	public List<ServicioInformativoBO> listarPublicaciones(int numNoticias);
	public List<ServicioInformativoBO> listarPublicacionesPorMes(int anio, int mes);
}
