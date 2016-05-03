package pe.gob.produce.produccion.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.ServicioBO;
import pe.gob.produce.produccion.core.dao.jdbc.BaseDAO;
import pe.gob.produce.produccion.core.dao.jdbc.Conexion;
import pe.gob.produce.produccion.dao.ServicioIDao;


@Repository("servicioDAO")
@Transactional
public class ServicioDAO extends BaseDAO implements ServicioIDao  {

	@Override
	public List<ServicioBO> buscarServicio(String codigo, String nombre,
			int idCite) {
		
		Connection con = null;
		CallableStatement cstm = null;
		ResultSet rs = null;

		List<ServicioBO> listaServicio = new ArrayList<ServicioBO>();

		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con.prepareStatement("{call dbo.SP_Buscar_Servicios(?,?,?)}");
			pstmt.setString(1, nombre);
			pstmt.setString(2, codigo);
			pstmt.setInt(3, idCite);
		    rs = pstmt.executeQuery();

		    while (rs.next()) {
		        ServicioBO servicioBO = new ServicioBO(); 
		        System.out.println("SERVICIO " + rs.getString("IDSERVICIO"));
		        servicioBO.setCodigo(rs.getString("IDSERVICIO"));
		        servicioBO.setNombre(rs.getString("SERVICIO_DESC"));
		        servicioBO.setUnidad(rs.getString("UNIDAD_DESC"));
		        servicioBO.setRequisito(rs.getString("REQUISITO"));
		        servicioBO.setCite(new CITEBO());
		        servicioBO.getCite().setDescripcion(rs.getString("NOMBRE_CITE"));
		        servicioBO.setValorDeVenta(Float.toString(rs.getFloat("VALOR_VENTA")));
		        servicioBO.setPrecioDeVenta(Float.toString(rs.getFloat("PRECIO_VENTA")));
		        
		        System.out.println("VALOR DE VENTA " + servicioBO.getValorDeVenta());
		        System.out.println("PRECIO DE VENTA " + servicioBO.getPrecioDeVenta());
		        
		        listaServicio.add(servicioBO);
		    }
		      
		     
			return listaServicio;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarStatement(cstm);
			this.cerrarConexion(con);
		}
		
	}
	

	@Override
	public void nuevoServicio(ServicioBO servicio) {
		
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = Conexion.obtenerConexion();
			cstm = con.prepareCall("{call SP_Insertar_Servicio(?,?,?,?,?,?,?,?,?)}");		
			cstm.setQueryTimeout(3);
			cstm.setString(1, servicio.getCodigo());		
			cstm.setString(2, servicio.getUbigeo().getIdUbigeo());
			cstm.setInt(3, Integer.parseInt(servicio.getCite().getCodigo()));
			cstm.setInt(4, Integer.parseInt(servicio.getEstado()));
			cstm.setInt(5, Integer.parseInt(servicio.getUnidad()));
	
			cstm.setString(6, servicio.getNombre());
			cstm.setString(7, servicio.getRequisito());
			cstm.setFloat(8, Float.parseFloat(servicio.getValorDeVenta()));
			cstm.setFloat(9, Float.parseFloat(servicio.getPrecioDeVenta()));
			
			
			cstm.execute();
		
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			this.cerrarStatement(cstm);
			this.cerrarConexion(con);
		}

		
		
	}
	
	
	

}
