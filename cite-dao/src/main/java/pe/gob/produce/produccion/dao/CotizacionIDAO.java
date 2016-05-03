package pe.gob.produce.produccion.dao;


import pe.gob.produce.cite.bo.CotizacionBO;
import pe.gob.produce.cite.bo.CotizacionDetalleBO;

public interface CotizacionIDAO {

	
	public int obtenerCodigoCotizacion()  throws Exception;
	public int guardarCotizacion(CotizacionBO cotizacion) throws Exception;
	public void guardarCotizacionDetalle(CotizacionDetalleBO cotizacionDetalle) throws Exception;
	public int obtenerCiteSede(String ubigeo, String nombreCite) throws Exception;

}
