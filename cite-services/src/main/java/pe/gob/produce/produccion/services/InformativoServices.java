package pe.gob.produce.produccion.services;

import java.util.Date;
import java.util.List;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.core.util.TipoInformativo;

public interface InformativoServices {
	public List<ServicioInformativoBO> listarNoticias(int numNoticias);
	public List<ServicioInformativoBO> listarNoticiasPorMes(int anio, int mes);
	public List<ServicioInformativoBO> listarPublicaciones(int numNoticias);
	public List<ServicioInformativoBO> listarPublicacionesPorMes(int anio, int mes);
	ServicioInformativoBO obtenerInformativo(int id, TipoInformativo tipo);
	List<ServicioInformativoBO> buscarInformativo(String titulo, Date fecha, String tipoDocumento, TipoInformativo tipo);
	Integer actualizarInformativo(ServicioInformativoBO informativo, TipoInformativo tipo) throws Exception;
	Integer eliminarInformativo(Integer id, TipoInformativo tipo);
}
