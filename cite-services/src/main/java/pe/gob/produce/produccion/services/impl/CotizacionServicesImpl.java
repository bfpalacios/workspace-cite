package pe.gob.produce.produccion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.CotizacionBO;
import pe.gob.produce.cite.bo.CotizacionDetalleBO;
import pe.gob.produce.produccion.dao.CotizacionIDAO;
import pe.gob.produce.produccion.services.CotizacionServices;


@Component("cotizacionServices")
public class CotizacionServicesImpl implements CotizacionServices{
	
	
	@Autowired
	private CotizacionIDAO cotizacionDAO;
	
	
	
	@Override
	public int obtenerCodigoCotizacion() throws Exception {
		// TODO Auto-generated method stub
		return cotizacionDAO.obtenerCodigoCotizacion();
	}

	@Override
	public int guardarCotizacion(CotizacionBO cotizacion)
	{
		int idCotizacion = 0;
		// TODO Auto-generated method stub
		try {
			idCotizacion =  cotizacionDAO.guardarCotizacion(cotizacion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idCotizacion;
	}

	@Override
	public int obtenerCiteSede(String ubigeo, String nombreCite)
			throws Exception {
		// TODO Auto-generated method stub
		return cotizacionDAO.obtenerCiteSede(ubigeo, nombreCite);
	}

	@Override
	public void guardarCotizacionDetalle(CotizacionDetalleBO cotizacionDetalle) {
		try {
			cotizacionDAO.guardarCotizacionDetalle(cotizacionDetalle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
