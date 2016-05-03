package pe.gob.produce.produccion.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.CotizacionBO;
import pe.gob.produce.cite.bo.CotizacionDetalleBO;
import pe.gob.produce.produccion.core.dao.jdbc.BaseDAO;
import pe.gob.produce.produccion.core.dao.jdbc.Conexion;
import pe.gob.produce.produccion.dao.CotizacionIDAO;


@Component("cotizacionDao")
public class CotizacionDAOimpl extends BaseDAO implements CotizacionIDAO{

	@Override
	public int obtenerCodigoCotizacion() throws Exception {
		
		int codigoCotizacion =-1;
		Connection con = null;
		CallableStatement cstm = null;
		
		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call SP_Obtener_Nro_Cotizacion(?)}");		
		cstm.setQueryTimeout(3);
		cstm.registerOutParameter(1, java.sql.Types.INTEGER);
		
		cstm.execute();
		codigoCotizacion = cstm.getInt(1);
		System.out.println("codigo de cotizacion " + codigoCotizacion);
		return codigoCotizacion;
			
	
	}
	
	@Override
	public int obtenerCiteSede(String ubigeo, String nombreCite) throws Exception {
		
		int codigoCiteSede =-1;
		Connection con = null;
		CallableStatement cstm = null;
		
		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call SP_Obtener_CiteSede(?,?,?)}");		
		cstm.setQueryTimeout(3);
		cstm.setString(1, ubigeo);		
		cstm.setString(2, nombreCite);		
		cstm.registerOutParameter(3, java.sql.Types.INTEGER);
		
		cstm.execute();
		codigoCiteSede = cstm.getInt(3);
		System.out.println("codigo de cite sede " + codigoCiteSede);
		return codigoCiteSede;
			
	
	}

	@Override
	public int guardarCotizacion(CotizacionBO cotizacion)
			throws Exception {
		int idCotizacion = 0;
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = Conexion.obtenerConexion();
			//cstm = con.prepareCall("{call SP_Nueva_Cotizacion(?,?,?,?,?,?,?,?)}");	
			cstm = con.prepareCall("{call SP_Nueva_Cotizacion(?,?,?,?,?,?,?)}");	
			System.out.println("ID DE USUARIO " + cotizacion.getUsuario().getIdUsuario());
			System.out.println("codigo de servicio " + cotizacion.getServicio().getCodigo());
			System.out.println("cotizacion sede " + cotizacion.getSede());
			System.out.println("SECUENCIAL " + cotizacion.getSecuencial());
			System.out.println("COSTO TOTAL " + cotizacion.getCostoTotal());
			System.out.println("estado " + cotizacion.getEstado());
			//System.out.println("fecha " + cotizacion.getFecha());
			cstm.setQueryTimeout(3);	
			cstm.setInt(1, Integer.parseInt(cotizacion.getUsuario().getIdUsuario()));		
			cstm.setString(2, cotizacion.getServicio().getCodigo());		
			cstm.setInt(3, cotizacion.getSede());		
			cstm.setString(4, cotizacion.getSecuencial());
			cstm.setDouble(5, cotizacion.getCostoTotal());
			cstm.setInt(6, cotizacion.getEstado());		
			//cstm.setDate(7, (Date) cotizacion.getFecha());		
			
			cstm.registerOutParameter(7, java.sql.Types.INTEGER);
			
			cstm.execute();
			

			idCotizacion = cstm.getInt(7);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			this.cerrarStatement(cstm);
			this.cerrarConexion(con);
		}
	
		
		return idCotizacion;
		
	} 
	
	@Override
	public void guardarCotizacionDetalle(CotizacionDetalleBO cotizacionDetalle)
			throws Exception {
		
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = Conexion.obtenerConexion();
			cstm = con.prepareCall("{call SP_Nueva_Cotizacion_Detalle(?,?,?)}");	
			System.out.println("codigo de COTIZACION " + cotizacionDetalle.getCodigo());
			System.out.println("codigo de servicio " + cotizacionDetalle.getServicio().getCodigo());
			System.out.println("cotizacion sede " + cotizacionDetalle.getSede());
			cstm.setQueryTimeout(3);
			cstm.setInt(1, cotizacionDetalle.getCodigo());				
			cstm.setString(2, cotizacionDetalle.getServicio().getCodigo());		
			cstm.setInt(3, cotizacionDetalle.getSede());	
			
			cstm.execute();
		
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			this.cerrarStatement(cstm);
			this.cerrarConexion(con);
		}

		
	} 
	
	

}
