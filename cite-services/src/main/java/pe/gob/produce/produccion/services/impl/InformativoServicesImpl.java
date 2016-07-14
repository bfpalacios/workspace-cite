package pe.gob.produce.produccion.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.core.util.TipoInformativo;
import pe.gob.produce.produccion.dao.InformativoDAO;
import pe.gob.produce.produccion.services.InformativoServices;

@Component("informativoServices")
public class InformativoServicesImpl implements InformativoServices {

	@Autowired
	private InformativoDAO informativoDao;
	
	public InformativoServicesImpl(){		
	}
	
	@Override
	public List<ServicioInformativoBO> listarNoticias(int numNoticias) {
		return informativoDao.listarNoticias(numNoticias);
	}

	@Override
	public List<ServicioInformativoBO> listarNoticiasPorMes(int anio, int mes) {
		return informativoDao.listarNoticiasPorMes(anio, mes);
	}

	@Override
	public List<ServicioInformativoBO> listarPublicaciones(int numNoticias) {
		return informativoDao.listarPublicaciones(numNoticias);
	}

	@Override
	public List<ServicioInformativoBO> listarPublicacionesPorMes(int anio,
			int mes) {
		return informativoDao.listarPublicacionesPorMes(anio, mes);
	}

	@Override
	public ServicioInformativoBO obtenerInformativo(int id, TipoInformativo tipo) {
		return informativoDao.obtenerInformativo(id, tipo);
	}

	@Override
	public List<ServicioInformativoBO> buscarInformativo(String titulo,
			Date fecha, TipoInformativo tipo) {
		return informativoDao.buscarInformativo(titulo, fecha, tipo);
	}

	@Override
	public Integer actualizarInformativo(ServicioInformativoBO informativo,
			TipoInformativo tipo) {
		return informativoDao.actualizarInformativo(informativo, tipo);
	}

	@Override
	public Integer eliminarInformativo(Integer id, TipoInformativo tipo) {
		return informativoDao.eliminarInformativo(id, tipo);
	}

}
