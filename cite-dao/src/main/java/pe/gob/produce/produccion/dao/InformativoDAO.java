package pe.gob.produce.produccion.dao;

import java.util.Date;
import java.util.List;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.core.util.TipoInformativo;

public interface InformativoDAO {
	List<ServicioInformativoBO> listarNoticias(int numNoticias);
	List<ServicioInformativoBO> listarNoticiasPorMes(int anio, int mes);
	List<ServicioInformativoBO> listarPublicaciones(int numNoticias);
	List<ServicioInformativoBO> listarPublicacionesPorMes(int anio, int mes);
	ServicioInformativoBO obtenerInformativo(int id, TipoInformativo tipo);
	List<ServicioInformativoBO> buscarInformativo(String titulo, Date fecha, TipoInformativo tipo);
	Integer actualizarInformativo(ServicioInformativoBO informativo, TipoInformativo tipo) throws Exception;
	Integer eliminarInformativo(Integer id, TipoInformativo tipo);
}
